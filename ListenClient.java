import java.io.IOException;
import java.net.Socket;

public class ListenClient extends Client {
  private Connection connection;

  /**
   * begins the listen client that will show all messages being sent to the server
   *
   * @throws IOException
   */
  public void startClient() throws IOException {
    Client client = new Client();
    try (Socket s = client.connect()) {
      this.connection = new Connection(s);
      this.connection.writeInt(ChatroomProtocol.LISTEN);
      while (true) {
        String line = this.connection.readUTF();
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    new ListenClient().startClient();
  }
}
