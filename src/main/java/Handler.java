import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Handler {

    private final Socket clientSocket;

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {
        try(
                InputStream inputStream = clientSocket.getInputStream();
                final OutputStream outputStream = clientSocket.getOutputStream()
        ) {
            byte[] bs = new byte[1024];
            int read;
            while ((read = inputStream.read(bs)) >= 0) {
                byte[] bytes = Arrays.copyOf(bs, read);
                final String content = new String(bytes);
                String response = "+PONG\r\n";
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
