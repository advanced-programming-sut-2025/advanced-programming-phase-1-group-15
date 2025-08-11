package com.example.server;

import com.example.common.JSONUtils;
import com.example.common.Message;
import com.example.server.controllers.ServerController;
import com.example.server.controllers.ServerGameController;
import com.example.server.controllers.ServerLobbyController;
import com.example.server.models.ServerApp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.server.controllers.ServerController.*;

public class GameServer {
    private static final int PORT = 54556;
    private static final AtomicInteger nextClientId = new AtomicInteger(1);
    private static final Map<Integer, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server listening on port " + PORT);
            System.out.println("--------------------------------------------------------");
            while (true) {
                Socket socket = server.accept();
                new Thread(new ClientHandler(socket)).start();
                System.out.println("New client connected with address: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                System.out.println("--------------------------------------------------------");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static class ClientHandler implements Runnable {
        private final Socket socket;
        private final String IP;
        private final int port;
        private final DataInputStream in;
        private final DataOutputStream out;
        private Integer clientId;
        private final AtomicBoolean end = new AtomicBoolean(false);

        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.IP = socket.getInetAddress().getHostAddress();
            this.port = socket.getPort();
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            clientId = nextClientId.getAndIncrement();
            clients.put(clientId, this);
        }

        @Override
        public void run() {
            try {
                while (!end.get()) {
                    // parse incoming JSON
                    int len = in.readInt();
                    byte[] buf = new byte[len];
                    in.readFully(buf);
                    String receivedStr = new String(buf, StandardCharsets.UTF_8);
                    Message msg = JSONUtils.fromJson(receivedStr);

                    System.out.println("Received message from " + msg.getFromBody("ip") + ":" + msg.getIntFromBody("port"));
                    System.out.println(msg);

                    if (msg.getType() != Message.Type.COMMAND) continue;

                    String action = msg.getFromBody("action");
                    HashMap<String,Object> respBody = new HashMap<>();
                    String reqId = msg.getFromBody("requestId");
                    respBody.put("action", action);
                    if (reqId != null) {
                        respBody.put("requestId", reqId);
                    }

                    switch (action) {
                        case "signup" -> handleSignup(msg, respBody, IP, port);
                        case "login"  -> handleLogin(msg, respBody, IP, port);
                        case "logout" -> handleLogout(msg, respBody, IP, port);
                        case "get_user" -> getUser(msg, respBody);
                        case "security_question" -> handleSecurityQuestion(msg, respBody);
                        case "get_user_by_username" -> getUserByUsername(msg, respBody);
                        case "change_password" -> handleChangePassword(msg, respBody);
                        case "change_username" -> handleChangeUsername(msg, respBody);
                        case "change_nickname" -> handleChangeNickname(msg, respBody);
                        case "change_email" -> handleChangeEmail(msg, respBody);
                        case "change_avatar_key" -> handleChangeAvatarKey(msg, respBody);
                        case "create_lobby" -> handleCreateLobby(msg, respBody);
                        case "get_lobbies" -> getLobbies(msg, respBody);
                        case "get_usernames" -> getUsernames(msg, respBody);
                        case "is_online" -> checkOnline(msg, respBody);
                        case "join_lobby" -> ServerLobbyController.handleJoinLobby(msg, respBody);
                        case "leave_lobby" -> ServerLobbyController.handleLeaveLobby(msg, respBody);
                        case "set_map_number" -> ServerLobbyController.handleSetMapNumber(msg, respBody);
                        case "start_game" -> ServerLobbyController.handleStartGame(msg, respBody);
                        default -> {
                            ServerGameController.handleCommand(msg, respBody);
                        }
                    }

                    Message resp = new Message(respBody, Message.Type.RESPONSE);

                    System.out.println("Response to " + msg.getFromBody("ip") + ":" + msg.getIntFromBody("port"));
                    System.out.println(resp);
                    System.out.println("--------------------------------------------------------");

                    sendMessage(resp);
                }
            } catch (EOFException | SocketException e) {
                System.out.println("Client disconnected: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                System.out.println("--------------------------------------------------------");
                disconnect();
            }  catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }

        public void sendMessage(Message message) {
            String JSONString = JSONUtils.toJson(message);
            byte[] data = JSONString.getBytes(StandardCharsets.UTF_8);

            try {
                out.writeInt(data.length);
                out.write(data);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getAddress() {
            return IP + ":" + port;
        }

        private void disconnect() {
            end.set(true);
            String addr = getAddress();
            ServerApp.onlineAddresses.remove(addr);
            ServerController.informNewLogout(ServerApp.getUserByAddress(getAddress()).getUsername());

            if (clientId != null) {
                clients.remove(clientId);
            }
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    public static List<ClientHandler> getClientHandlers() {
        return new ArrayList<>(clients.values());
    }
}
