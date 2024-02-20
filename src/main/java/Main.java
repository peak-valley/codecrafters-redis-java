
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args){
    final ExecutorService executorService = Executors.newFixedThreadPool(2);

    Integer p = resolve(args);
    int port = 6379;
    if (p != null) {
      port = p;
    }
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
      serverSocket.setReuseAddress(true);
      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("client conn：IP:" + clientSocket.getInetAddress() + ", port:" + clientSocket.getPort());
        executorService.submit(() -> {
          Handler handler = new Handler(clientSocket);
          handler.handle();
        });
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }

  public static Integer resolve(String[] args) {
    if (args.length == 0) {
      return null;
    }
    String port = args[0];
    if (port == null || port.length() < 8) {
      return null;
    }

    port = port.replaceFirst("--port=", "");
    return Integer.parseInt(port);
  }
}
