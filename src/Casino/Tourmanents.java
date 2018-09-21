package Casino;

import java.io.Serializable;

public class Tourmanents implements Serializable {
  private String name;
  private int prize;
  private int enter;

  public Tourmanents(String name, int prize) {
    this.name = name;
    this.prize = prize;
    this.enter = (int) (prize * 0.1);
  }
}
