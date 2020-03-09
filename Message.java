public class Message {
  private final String who;
  private final String message;

  /**
   * method of storing information of the messages being sent "User -> message"
   *
   * @param who person sending message
   * @param message the message
   */

  public Message(String who, String message) {
    this.who = who;
    this.message = message;
  }

  public String toString() {
    return this.who + " -> " + this.message;
  }

}
