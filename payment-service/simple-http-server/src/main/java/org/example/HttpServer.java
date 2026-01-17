package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8080);
    System.out.println("Server started at http://localhost:8080");

    URL staticUrl = HttpServer.class.getClassLoader().getResource("static");
    Path staticDir;

    if (staticUrl != null && "file".equals(staticUrl.getProtocol())) {
      try {
        staticDir = Paths.get(staticUrl.toURI());
      } catch (URISyntaxException e) {
        staticDir = Paths.get(staticUrl.getPath());
      }
    } else {
      Path classLocation = null;
      try {
        classLocation = Paths.get(HttpServer.class.getProtectionDomain()
            .getCodeSource().getLocation().toURI());
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }

      if (Files.isDirectory(classLocation)) {
        staticDir = classLocation.getParent().getParent().resolve("static");
      } else {
        staticDir = classLocation.getParent().resolve("static");
      }
    }
    System.out.println("Serving from: " + staticDir);

    while (true) {
      Socket clientSocket = serverSocket.accept();

      try (InputStream in = clientSocket.getInputStream();
          OutputStream out = clientSocket.getOutputStream()) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String requestLine = reader.readLine();
        if (requestLine == null) {
          continue;
        }

        System.out.println("Request: " + requestLine);

        String requestedFile = "/index.html";
        if (requestLine.startsWith("GET")) {
          String[] parts = requestLine.split("\\s+");
          if (parts.length >= 2) {
            requestedFile = parts[1];
            if (requestedFile.equals("/")) {
              requestedFile = "/index.html";
            }
          }
        }

//        String line;
//        while ((line = reader.readLine()) != null && !line.isEmpty()) {
//          System.out.println(line);
//        }

        String filePath = requestedFile.startsWith("/")
            ? requestedFile.substring(1)
            : requestedFile;

        Path file = staticDir.resolve(filePath).normalize();

        if (!Files.exists(file) || !Files.isRegularFile(file)) {
          sendError(out, 404, "Not Found");
          System.out.println("File not found: " + file);
          continue;
        }

        byte[] fileContent = Files.readAllBytes(file);
        long fileSize = Files.size(file);
        String contentType = getContentType(filePath);

        sendResponse(out, fileContent, contentType, fileSize);
        System.out.println("Sent file: " + requestedFile + " (" + fileSize + " bytes)");

      } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
      } finally {
        clientSocket.close();
      }
    }
  }

  private static void sendResponse(OutputStream out, byte[] content,
      String contentType, long contentLength) throws IOException {
    String headers = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: " + contentType + "\r\n" +
        "Content-Length: " + contentLength + "\r\n" +
        "\r\n";

    out.write(headers.getBytes(StandardCharsets.UTF_8));
    out.write(content);
    out.flush();
  }

  private static void sendError(OutputStream out, int statusCode, String message)
      throws IOException {
    String html = "<html><body><h1>" + statusCode + " " + message + "</h1></body></html>";
    byte[] content = html.getBytes(StandardCharsets.UTF_8);

    String headers = "HTTP/1.1 " + statusCode + " " + message + "\r\n" +
        "Content-Type: text/html\r\n" +
        "Content-Length: " + content.length + "\r\n" +
        "\r\n";

    out.write(headers.getBytes(StandardCharsets.UTF_8));
    out.write(content);
    out.flush();
  }

  private static String getContentType(String fileName) {
    if (fileName.endsWith(".html")) {
      return "text/html";
    }
    if (fileName.endsWith(".css")) {
      return "text/css";
    }
    return "text/plain";
  }
}