import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatroomServer {
  public final static int PORT = 46464;

  private Chatroom chatroom;

  public ChatroomServer(Chatroom chatroom) {
    this.chatroom = chatroom;
  }

  /**
   * loops constantly taking sockets and initiating them as threads when connected so they can become either ListenClients or SpeakClients
   *
   * @throws IOException
   */

  public void startServer() throws IOException {
    try (ServerSocket ss = new ServerSocket(ChatroomServer.PORT)) {
      while (true) {
        Socket s = ss.accept();
        ChatroomWorker worker = new ChatroomWorker(new Connection(s), this.chatroom);
        Thread t = new Thread(worker);
        t.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    new ChatroomServer(new Chatroom()).startServer();
  }
}
