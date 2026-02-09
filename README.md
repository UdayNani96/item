Simple Java Item API

This project is a simple backend application built using core Java.
It provides REST-like APIs to add and retrieve items.

Technologies Used:
- Core Java
- Built-in HttpServer
- ArrayList for in-memory storage

Endpoints:

1. Add Item
POST /items
Request Body (JSON):
{
  "name": "Laptop",
  "description": "Gaming laptop",
  "price": 60000
}

2. Get Item by ID
GET /items/{id}

Example:
GET /items/1

How to Run:
1. Open the project in Eclipse
2. Run ItemServer.java as Java Application
3. Server starts at http://localhost:8080
4. Use Postman or browser to test APIs

Note:
- Data is stored in memory and resets when the server restarts.
