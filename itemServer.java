package itemapi;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class itemServer {

    public static void main(String[] args) throws Exception {

        // Create HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Map /items URL to handler
        server.createContext("/items", itemServer::handleRequest);

        server.start();
        System.out.println("Server started at http://localhost:8080");
    }

    // Decide which request to handle
    private static void handleRequest(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("POST")) {
            handlePost(exchange);
        } else if (method.equalsIgnoreCase("GET")) {
            handleGet(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    // ADD ITEM (POST /items)
    private static void handlePost(HttpExchange exchange) throws IOException {

        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // Basic validation
        if (!body.contains("name") || !body.contains("description")) {
            sendResponse(exchange, "Invalid input");
            return;
        }

        int id = itemStore.items.size() + 1;
        String name = extract(body, "name");
        String description = extract(body, "description");
        double price = Double.parseDouble(extract(body, "price"));

        item item = new item(id, name, description, price);
        itemStore.items.add(item);

        sendResponse(exchange, "Item added successfully");
    }

    // GET ITEM (GET /items/{id})
    private static void handleGet(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath(); // /items/1
        String[] parts = path.split("/");

        if (parts.length != 3) {
            sendResponse(exchange, "Invalid URL");
            return;
        }

        int id = Integer.parseInt(parts[2]);
        item item = itemStore.findById(id);

        if (item == null) {
            sendResponse(exchange, "Item not found");
        } else {
            sendJson(exchange, item.toJson());
        }
    }

    // Send normal text response
    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        byte[] bytes = response.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    // Send JSON response
    private static void sendJson(HttpExchange exchange, String json) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        sendResponse(exchange, json);
    }

    // Extract value from JSON (simple parsing)
    private static String extract(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf(",", start);
        if (end == -1) {
            end = json.indexOf("}", start);
        }
        return json.substring(start, end).replace("\"", "").trim();
    }
}
