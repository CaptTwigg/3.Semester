package Casino;

import java.io.Serializable;

public class Session implements Serializable {
  private String username = "Alice";
  private String password = "1234";

  public Session() {
  }

  public Session(String username, String password) {
    this.username = username;
    this.password = password;
  }


  public boolean check(String username, String password){
    return this.username.equals(username) && this.password.equals(password);
  }

  @Override
  public String toString() {
    return String.format("username: %s password: %s "
      , this.username, this.password);
  }
}
