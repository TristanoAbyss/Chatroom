import java.io.IOException;
import java.net.Socket;

/**
 *
 * connects the two clients (ListenClient and SpeakClient) to the server
 */

public class Client {
  protected Socket connect() throws IOException {
    return new Socket("localhost", ChatroomServer.PORT);
  }
}
