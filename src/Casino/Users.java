package Casino;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Users implements Serializable {
  protected String fullname;
  protected String cpr;
  public static final int dealer = 1;
  public static final int player = 2;

  public Users(String fullname, String cpr) {
    this.fullname = fullname;
    this.cpr = cpr;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getCpr() {
    return cpr;
  }

  public void setCpr(String cpr) {
    this.cpr = cpr;
  }

  public int getAge() {
    String[] cprs = cpr.split("", 8);
    int year = Integer.parseInt(cprs[4] + cprs[5]);
    int seven = Integer.parseInt(cprs[6]);
    int birthCentury = 19;
    if (seven == 0 || seven == 1 || seven == 2 || seven == 3)
      birthCentury = 19;

    if (seven == 4 || seven == 9) {
      if (year <= 36)
        birthCentury = 20;
      else
        birthCentury = 19;
    }
    if (seven == 5 || seven == 6 || seven == 7 || seven == 8) {
      if (year <= 36)
        birthCentury = 20;
      if (year >= 56)
        birthCentury = 18;
    }
    LocalDate date = LocalDate.of(Integer.parseInt(birthCentury + cprs[4] + cprs[5]),
      Integer.parseInt(cprs[2] + cprs[3]),
      Integer.parseInt(cprs[0] + cprs[1]));

    return Period.between(date, LocalDate.now()).getYears();
  }

  public static int getAge(String cpr) {
    String[] cprs = cpr.split("", 8);
    int year = Integer.parseInt(cprs[4] + cprs[5]);
    int seven = Integer.parseInt(cprs[6]);
    int birthCentury = 19;
    if (seven == 0 || seven == 1 || seven == 2 || seven == 3)
      birthCentury = 19;

    if (seven == 4 || seven == 9) {
      if (year <= 36)
        birthCentury = 20;
      else
        birthCentury = 19;
    }
    if (seven == 5 || seven == 6 || seven == 7 || seven == 8) {
      if (year <= 36)
        birthCentury = 20;
      if (year >= 56)
        birthCentury = 18;
    }
    LocalDate date = LocalDate.of(Integer.parseInt(birthCentury + cprs[4] + cprs[5]),
      Integer.parseInt(cprs[2] + cprs[3]),
      Integer.parseInt(cprs[0] + cprs[1]));

    return Period.between(date, LocalDate.now()).getYears();
  }

}
