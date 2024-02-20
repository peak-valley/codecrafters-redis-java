
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

    int port = resolve(args);
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
    if (args.length < 2) {
      return 6379;
    }

    if ("--port".equals(args[0])) {
      try {
        return Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.out.printf("The second parameter is not a number,args[1]:" + args[1]);
        return 6379;
      }
    }
    return 6379;
  }
}
