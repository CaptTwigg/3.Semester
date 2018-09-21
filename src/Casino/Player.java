package Casino;

public class Player extends Users{
  private Integer firstPlace;
  private int secondPlace;
  private int thirdPlace;

  public Player(String fullname, String cpr) {
    super(fullname, cpr);
  }


  @Override
  public String toString() {
    return String.format("firstPlace: %s secondPlace: %s thirdPlace: %s fullname: %s cpr: %s Age: %s"
      , this.firstPlace, this.secondPlace, this.thirdPlace, this.fullname, this.cpr, getAge());
  }

  public int getFirstPlace() {
    return firstPlace;
  }

  public void setFirstPlace(int firstPlace) {
    this.firstPlace = firstPlace;
  }

  public int getSecondPlace() {
    return secondPlace;
  }

  public void setSecondPlace(int secondPlace) {
    this.secondPlace = secondPlace;
  }

  public int getThirdPlace() {
    return thirdPlace;
  }

  public void setThirdPlace(int thirdPlace) {
    this.thirdPlace = thirdPlace;
  }

  public int compareTo(Player o) {
    return this.firstPlace.compareTo(o.firstPlace);
  }
}
