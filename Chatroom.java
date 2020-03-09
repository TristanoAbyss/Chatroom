import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chatroom {
  private ArrayList<Message> messages;
  private int messageIndex = 0;
  private ArrayList<Connection> listeners;
  private Lock mLock = new ReentrantLock();
  private Lock lLock = new ReentrantLock();

  public Chatroom() {
    this.messages = new ArrayList<>();
    this.listeners = new ArrayList<>();
  }

  /**
   * Adds the message to the Arraylist of messages and increments the current messageIndex number
   *
   * @param message message that will be stored "name -> message"
   */

  public void addMessage(Message message) {
    mLock.lock();
    this.messages.add(message);
    messageIndex ++;
    mLock.unlock();
  }

  /**
   * gets the arraylist of messages that has stored the message log since creation
   *
   * @return arraylist of messages on the server
   */

  public ArrayList<Message> getMessages() {
    mLock.lock();
    ArrayList<Message> tempMessageList = (ArrayList<Message>) this.messages.clone();
    mLock.unlock();
    return tempMessageList;
  }

  /**
   * adds a ListenClient socket connection so that when a message is sent from SpeakClient all ListenClients are also given the message
   *
   * @param s the connection (or socket) that will stored
   */

  public void attachListener(Connection s) {
    lLock.lock();
    this.listeners.add(s);
    lLock.unlock();
  }

  /**
   * Sends all messages to all current ListenClients in the active list. If the client does not exist, connection is stored in another arraylist
   * so that once looping through the connections is done, if the removeError arraylist is larger than 0 it a loop will play to remove all of
   * occurrences of a non-existing (bad socket) cases
   *
   */

  public void sendToListeners(){
    mLock.lock();
    ArrayList<Connection> removeError = new ArrayList<>();
      for (Connection c : listeners){
        try{
          String message = messages.get(messageIndex-1).toString();
          c.writeUTF(message);
        } catch (Exception e) {
          removeError.add(c);
        }
      }
    if(removeError.size() > 0)
      for(Connection d : removeError)
        listeners.remove(d);

    mLock.unlock();
  }
}
