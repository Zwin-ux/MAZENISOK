package server;

import handler.CreateDepositHandler;
import handler.GetTransactionsHandler;
import handler.CreateUserHandler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import request.CustomParser;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(1299);
            System.out.println("Opened socket " + 1299);
            while (true) {
                // keeps listening for new clients, one at a time
                try {
                    socket = serverSocket.accept(); // waits for client here
                } catch (IOException e) {
                    System.out.println("Error opening socket");
                    System.exit(1);
                }

                InputStream stream = socket.getInputStream();
                byte[] b = new byte[1024 * 20];
                stream.read(b);
                String input = new String(b).trim();
                System.out.println(input);

                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                PrintWriter writer = new PrintWriter(out, true);  // char output to the client

                // HTTP Response
                if (!input.isEmpty()) {
                    CustomHttpResponse response = processRequest(input);
                    writer.println(response.toString());
                } else {
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Server: TEST");
                    writer.println("Connection: close");
                    writer.println("Content-type: text/html");
                    writer.println("");
                }

                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error opening socket");
            System.exit(1);
        }
    }

    // Assume the http server feeds the entire raw http request here
    // Response is a raw http response string
    public static CustomHttpResponse processRequest(String requestString) {
        ParsedRequest request = CustomParser.parse(requestString);
        ResponseBuilder responseBuilder;

        String path = request.getPath();
        String method = request.getMethod();

        if (method.equals("POST") && path.equals("/api/users")) {
            responseBuilder = new CreateUserHandler().handleRequest(request);
        } else if (method.equals("POST") && path.equals("/api/transactions/deposit")) {
            responseBuilder = new CreateDepositHandler().handleRequest(request);
        } else if (method.equals("GET") && path.equals("/api/transactions")) {
            responseBuilder = new GetTransactionsHandler().handleRequest(request);
        } else {
            responseBuilder = new ResponseBuilder().setStatus(404).setBody("Not Found");
        }

        CustomHttpResponse response = responseBuilder.build();
        if (response.body != null) {
            response.headers.put("Content-type", "application/json");
        }
        return response;
    }

}
