package Casino;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  private static String playerFile = "src/Casino/files/player.ser";
  private static String employeeFile = "src/Casino/files/employee.ser";
  private static String tournamentsFile = "src/Casino/files/tourmanents.ser";

  private static FileHandler<Player> playerFileHandler = new FileHandler<>(playerFile);
  private static FileHandler<Employee> employeeFileHandler = new FileHandler<>(employeeFile);
  private static FileHandler<Tourmanents> tournamentsFileHandler = new FileHandler<>(tournamentsFile);
  private static ArrayList<Player> playerArrayList = playerFileHandler.loadSerFile();
  private static ArrayList<Employee> employeeArrayList = employeeFileHandler.loadSerFile();
  private static ArrayList<Tourmanents> tournaments = tournamentsFileHandler.loadSerFile();

  public static void main(String[] args) {

    login();
    enterface();


  }


  public static void enterface() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("1 to show employee list, 2 to add new emp, 3 to create tournament, 4 Create player");
    switch (scanner.nextLine()) {
      case "1":
        System.out.println(employeeFileHandler.loadSerFile());
        break;
      case "2":
        addEmp();
        break;
      case "3":
        createTournament();
        break;
      case "4":
        createPlayer();
        break;
      default:
        System.out.println("TWAT");
    }
    System.out.println(playerArrayList);
  }

  public static void addEmp() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter full name: ");
    String name = scanner.nextLine();
    System.out.print("Enter cpr: ");
    String cpr = scanner.nextLine();
    System.out.print("Enter new Password: ");
    String password = scanner.nextLine();

    int empNo = 0;
    if (!employeeArrayList.isEmpty())
      empNo = employeeArrayList.get(employeeArrayList.size() - 1).getEmployeeNo();
    employeeArrayList.add(new Employee(name, cpr, empNo + 1, password));
    employeeFileHandler.saveSerFile(employeeArrayList);
  }

  public static void createPlayer() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter full name: ");
    String name = scanner.nextLine();
    System.out.print("Enter cpr: ");
    String cpr = scanner.nextLine();
    playerArrayList.add(new Player(name, cpr));
    playerFileHandler.saveSerFile(playerArrayList);
  }

  public static void createTournament() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter tournament name: ");
    String name = scanner.nextLine();
    System.out.print("Enter winning prize: ");
    int prize = scanner.nextInt();
    tournaments.add(new Tourmanents(name, prize));
    tournamentsFileHandler.saveSerFile(tournaments);
  }

  public static void createGame(){
    Scanner scanner = new Scanner(System.in);




  }

  public static void login() {
    Scanner scanner = new Scanner(System.in);
    boolean Ok = false;
    do {
      System.out.print("Enter username: ");
      String username = scanner.next();
      System.out.print("Enter password: ");
      String password = scanner.next();
      for (Employee e : employeeArrayList)
        if (e.getSession().check(username, password))
          Ok = true;

      if (employeeArrayList.size() == 0)
        Ok = true;
    } while (!Ok);

  }
}
