
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
  public static void main(String[] args){
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    int port = 6379;
    try {
      serverSocket = new ServerSocket(port);
      serverSocket.setReuseAddress(true);
      // Wait for connection from client.
      clientSocket = serverSocket.accept();

      try(
              InputStream inputStream = clientSocket.getInputStream();
              final OutputStream outputStream = clientSocket.getOutputStream()
      ) {
        byte[] bs = new byte[1024];

        final int read = inputStream.read(bs);
        byte[] bytes = Arrays.copyOf(bs, read);
        final String content = new String(bytes);
        String response = "PONG\r\n";
        if (content.equals("PING")) {
          outputStream.write(response.getBytes(StandardCharsets.UTF_8));
          outputStream.flush();
        }
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }
}
