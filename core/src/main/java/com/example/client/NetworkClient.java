package com.example.client;

import com.badlogic.gdx.Gdx;
import com.example.common.JSONUtils;
import com.example.common.Message;

import java.io.*;
import java.net.Socket;
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

    public void sendMessage(Message message) {
        message.addToBody("ip", IP);
        message.addToBody("port", port);

        String JSONString = JSONUtils.toJson(message);

        try {
            out.writeUTF(JSONString);
        } catch (IOException e) {
            e.printStackTrace();
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
                    String receivedStr = in.readUTF();
                    Message msg = JSONUtils.fromJson(receivedStr);

                    String respId = msg.getFromBody("requestId");
                    if (respId != null && pending.containsKey(respId)) {
                        pending.get(respId).complete(msg);
                    }

                    Gdx.app.postRunnable(() -> {
                        for (var l : listeners) {
                            l.accept(msg);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readerThread.setDaemon(true);
        readerThread.start();
    }
}

