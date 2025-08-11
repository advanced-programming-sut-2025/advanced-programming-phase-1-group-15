package com.example.client;

import com.badlogic.gdx.Gdx;
import com.example.common.JSONUtils;
import com.example.common.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class NetworkClient {
    private static NetworkClient instance;
    private Socket socket;
    private String IP;
    private int port;
    private DataInputStream in;
    private DataOutputStream out;
    private final AtomicBoolean end = new AtomicBoolean(false);
    private Thread readerThread;
    private final List<Consumer<Message>> listeners = new CopyOnWriteArrayList<>();

    private final Map<String, CompletableFuture<Message>> pending = new ConcurrentHashMap<>();

    private NetworkClient() {}

    public static NetworkClient get() {
        if (instance == null) instance = new NetworkClient();
        return instance;
    }

    public void connect(String host, int port) throws IOException {
        if (socket != null && socket.isConnected()) return;
        socket = new Socket(host, port);
        IP = socket.getLocalAddress().getHostAddress();
        this.port = socket.getLocalPort();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        startReading();
    }

    public void disconnect() {
        end.set(true);
        try {
            if (readerThread != null) readerThread.interrupt();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(Message message) {
        message.addToBody("ip", IP);
        message.addToBody("port", port);
        String JSONString = JSONUtils.toJson(message);
        byte[] data = JSONString.getBytes(StandardCharsets.UTF_8);

        try {
            out.writeInt(data.length);
            out.write(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            end.set(true);
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    public Message sendAndWaitForResponse(Message msg, long timeoutMs)
        throws InterruptedException, TimeoutException, ExecutionException {
        String reqId = UUID.randomUUID().toString();
        msg.addToBody("requestId", reqId);

        var future = new CompletableFuture<Message>();
        pending.put(reqId, future);

        sendMessage(msg);

        try {
            return future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } finally {
            pending.remove(reqId);
        }
    }

    public void addListener(Consumer<Message> listener) {
        listeners.add(listener);
    }
    public void removeListener(Consumer<Message> listener) {
        listeners.remove(listener);
    }

    private void startReading() {
        readerThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted() && !end.get()) {
                    try {
                        int len = in.readInt();
                        if (len <= 0) {
                            System.err.println("Invalid length: " + len);
                            continue;
                        }
                        byte[] buf = new byte[len];
                        in.readFully(buf);
                        String receivedStr = new String(buf, StandardCharsets.UTF_8);
                        System.out.println("RECV len=" + len + " json=" + receivedStr);

                        Message msg;
                        try {
                            msg = JSONUtils.fromJson(receivedStr);
                        } catch (Exception je) {
                            System.err.println("Failed to parse JSON: " + je.getMessage());
                            System.err.println(receivedStr);
                            continue;
                        }

                        String respId = msg.getFromBody("requestId");
                        if (respId != null && pending.containsKey(respId)) {
                            pending.get(respId).complete(msg);
                        }

                        Gdx.app.postRunnable(() -> {
                            for (var l : listeners) {
                                try {
                                    l.accept(msg);
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            }
                        });
                    } catch (EOFException eof) {
                        System.out.println("Remote closed connection (EOF).");
                        break;
                    } catch (IOException ioe) {
                        System.err.println("IO error while reading: " + ioe.getMessage());
                        break;
                    }
                }
            } finally {
                end.set(true);
                try { if (in != null) in.close(); } catch (IOException ignored) {}
                try { if (out != null) out.close(); } catch (IOException ignored) {}
                try { if (socket != null) socket.close(); } catch (IOException ignored) {}

                var iter = pending.entrySet().iterator();
                while (iter.hasNext()) {
                    var e = iter.next();
                    e.getValue().completeExceptionally(new IOException("Disconnected"));
                    iter.remove();
                }
                System.out.println("Network reader thread ended.");
            }
        }, "Network-Reader");
        readerThread.setDaemon(true);
        readerThread.start();
    }
}

