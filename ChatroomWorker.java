import java.io.IOException;
import java.util.ArrayList;

public class ChatroomWorker implements Runnable {
    private final Connection connection;
    private final Chatroom chatroom;

    /**
     * when a chatroomworker is created it accepts a connection and a reference to the chatroom that will store all information
     *
     * @param connection connection from the ChatServer
     * @param chatroom a chatroom instance variable from the Chatserver
     */

    public ChatroomWorker(Connection connection, Chatroom chatroom) {
        this.connection = connection;
        this.chatroom = chatroom;
    }

    /**
     * the worker will run and take commands of either listen/speak, if it is a speak command it will look and constantly listen for MESSAGE protocol.
     * If the worker is a listenClient, the worker will create a new listenClient variable in chatroom, then obtain all message history from the message arraylist
     * and sends all messages to the newly created client
     */

  public void run() {
      try {
          int command = connection.readInt();
          switch(command){
              case ChatroomProtocol.LISTEN:
                  handleListen();
                  break;
              case ChatroomProtocol.SPEAK:
                  handleSpeak();
                  break;
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

    /**
     * accepts MESSAGE protocol to print obtain a message from the client and send it to all clients on the server
     */

    private void handleSpeak() {
        try{
            while(true){
                int command = connection.readInt();
                if(command == ChatroomProtocol.MESSAGE){
                    chatroom.addMessage(new Message(connection.readUTF(), connection.readUTF()));
                    chatroom.sendToListeners();
                }
            }
        }catch (Exception e){
        }
    }

    /**
     * if the worker is a listenClient will store it in the chatroom and feed it all of the messages in the arraylist of messages held in chatroom
     *
     * @throws IOException
     */

    private void handleListen() throws IOException {
        chatroom.attachListener(this.connection);
        ArrayList<Message> messages = chatroom.getMessages();
        for(Message m : messages){
            String dummyString = m.toString();
            this.connection.writeUTF(dummyString);
        }
    }
}
