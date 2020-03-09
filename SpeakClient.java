import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SpeakClient extends Client {
  private Connection connection;

  private void printPrompt(String name) {
    System.out.print(name + " -> ");
  }

  /**
   * allows the user to type messages to the server, the messages will then be sent to all clients on the server that are listening
   *
   * @param name name of the user sending messages
   * @throws IOException
   */

  public void startClient(String name) throws IOException {
    Client client = new Client();
    try (Socket s = client.connect()) {
      this.connection = new Connection(s);
      this.connection.writeInt(ChatroomProtocol.SPEAK);
      Scanner in = new Scanner(System.in);
      printPrompt(name);
      while (in.hasNextLine()) {
        printPrompt(name);
        String line = in.nextLine();
        connection.writeInt(ChatroomProtocol.MESSAGE);
        connection.writeUTF(name);
        connection.writeUTF(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    new SpeakClient().startClient(args[0]);
  }
}
