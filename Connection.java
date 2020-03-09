import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
  private final DataInputStream in;
  private final DataOutputStream out;
  private final Socket socket;

  public Connection(Socket socket) throws IOException {
    this.socket = socket;
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
  }

  public int readInt() throws IOException {
    return this.in.readInt();
  }

  public String readUTF() throws IOException {
    return this.in.readUTF();
  }

  public void writeInt(int i) throws IOException {
    this.out.writeInt(i);
    this.out.flush();
  }

  public void writeUTF(String s) throws IOException {
    this.out.writeUTF(s);
    this.out.flush();
  }


  public void close() {
    try {
      this.in.close();
    } catch (Exception e) {
    }

    try {
      this.out.close();
    } catch (Exception e) {
    }

    try {
      this.socket.close();
    } catch (Exception e) {
    }
  }
}
