package web.servers;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import database.services.LoginService;
import java.net.URLDecoder;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// URL: http://localhost:8080/mcc-web/login
public class LoginServer {
    public static void main(String[] args) throws IOException {
        // Create an HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8088), 0);

        // Define a URL path and attach a handler
        server.createContext("/login", new LoginHandler());

        server.start();
        System.out.println("Server started on port 8088");
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader inputReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader bufferReader = new BufferedReader(inputReader);
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = bufferReader.readLine()) != null) {
                    requestBody.append(line);
                }

                Map<String, String> formData = parseFormData(requestBody.toString());

                try {
                    if (new LoginService(formData.get("username"), URLDecoder.decode(formData.get("password")), formData.get("user-type").equalsIgnoreCase("Customer")).validate()){
                        System.out.println(formData.get("user-type"));

                        exchange.getResponseHeaders().add("Location", "http://localhost/mcc-web/account.html");
                        exchange.sendResponseHeaders(302, -1);

                        new CookieHandler.write(); // Cookie Handler Stub
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static Map<String, String> parseFormData(String data) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = data.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }
}