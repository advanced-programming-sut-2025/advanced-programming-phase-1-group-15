package com.example.server;

import com.example.common.JSONUtils;
import com.example.common.Message;
import com.example.server.controllers.ServerLoginController;
import com.example.common.Result;
import com.example.common.enums.Gender;
import com.example.server.models.ServerApp;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    private static final int PORT = 54555;
    private static final AtomicInteger nextClientId = new AtomicInteger(1);
    private static final Map<Integer, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server listening on port " + PORT);
        System.out.println("--------------------------------------------------------");
        while (true) {
            Socket socket = server.accept();
            new Thread(new ClientHandler(socket)).start();
            System.out.println("New client connected with address: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
            System.out.println("--------------------------------------------------------");
        }
    }

    private static class ClientHandler implements Runnable {
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
                    String receivedStr = in.readUTF();

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
                        case "signup" -> handleSignup(msg, respBody);
                        case "login"  -> handleLogin(msg, respBody);
                        case "get_user" -> getUser(msg, respBody);
                        case "security_question" -> handleSecurityQuestion(msg, respBody);
                        case "get_user_by_username" -> getUserByUsername(msg, respBody);
                        case "change_password" -> handleChangePassword(msg, respBody);
                        default -> {
                            respBody.put("success", false);
                            respBody.put("message", "Unknown action: " + action);
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
            }  catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }

        private void handleSignup(Message req, Map<String,Object> respBody) {
            String username = req.getFromBody("username");
            String pass = req.getFromBody("password");
            String passC = req.getFromBody("passwordConfirm");
            String nick = req.getFromBody("nickname");
            String email = req.getFromBody("email");
            Gender gender = req.getFromBody("gender", Gender.class);

            Result r = ServerLoginController.registerUser(IP + ":" + port, username, pass, passC, nick, email, gender);
            respBody.put("success", r.success());
            respBody.put("message", r.message());
        }

        private void handleLogin(Message req, Map<String,Object> respBody) {
            String username = req.getFromBody("username");
            String pass = req.getFromBody("password");
            Result r = ServerLoginController.loginUser(IP + ":" + port, username, pass, false);

            respBody.put("success", r.success());
            respBody.put("message", r.message());
        }

        private void getUser(Message req, Map<String,Object> respBody) {
            String IP = req.getFromBody("ip");
            int port = req.getIntFromBody("port");

            respBody.put("user", ServerApp.getUserByAddress(IP + ":" + port));
        }

        private void handleSecurityQuestion(Message req, Map<String,Object> respBody) {
            String username = req.getFromBody("username");
            String question = req.getFromBody("question");
            String answer = req.getFromBody("answer");
            Result r = ServerLoginController.pickQuestion(username, question, answer);

            respBody.put("success", r.success());
            respBody.put("message", r.message());
        }

        private void getUserByUsername(Message req, Map<String,Object> respBody) {
            String username = req.getFromBody("username");

            respBody.put("user", ServerApp.getUserByUsername(username));
        }

        private void handleChangePassword(Message req, Map<String,Object> respBody) {
            String username = req.getFromBody("username");
            String newPassword = req.getFromBody("new_password");
            Result r = ServerLoginController.forgetPassword(username, newPassword);

            respBody.put("success", r.success());
            respBody.put("message", r.message());
        }

        public void sendMessage(Message message) {
            String JSONString = JSONUtils.toJson(message);

            try {
                out.writeUTF(JSONString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void disconnect() {
            end.set(true);
            if (clientId != null) {
                clients.remove(clientId);
            }
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
