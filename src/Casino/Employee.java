package Casino;

public class Employee extends Users {
  private int employeeNo;
  private Session session;

  public Employee(String fullname, String cpr, int employeeNo, String password) {
    super(fullname, cpr);
    this.employeeNo = employeeNo;
    this.session = createSession(password);
  }

  private Session createSession(String password){
    String[] arr = fullname.split(" ");
    return new Session(arr[0], password);
  }

  public int getEmployeeNo() {
    return employeeNo;
  }

  public void setEmployeeNo(int employeeNo) {
    this.employeeNo = employeeNo;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  @Override
  public String toString() {
    return String.format("employeeNo: %s session: %s fullname: %s cpr: %s Age: %s "
      , this.employeeNo, this.session, this.fullname, this.cpr, getAge());
  }
}
