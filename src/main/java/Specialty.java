import org.sql2o.*;
import java.util.List;

public class Specialty {
  private String specialtyName;
  private int specialtyid;

  public Specialty(String specialtyName) {
    this.specialtyName = specialtyName;
  }

  @Override
  public boolean equals(Object specialty2) {
    if (!(specialty2 instanceof Specialty)) {
      return false;
    } else {
      Specialty newSpecialty = (Specialty) specialty2;
      return this.getSpecialtyName().equals(newSpecialty.getSpecialtyName())

      && this.getSpecialtyId() == newSpecialty.getSpecialtyId();
    }
  }

  public String getSpecialtyName(){
    return specialtyName;
  }

  public int getSpecialtyId(){
    return specialtyid;
  }

  public static List<Specialty> all() {
    String sql = "SELECT specialtyid, specialtyname FROM specialties;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sqlCommand = "INSERT INTO specialties (specialtyname) VALUES (:specialtyname);";
      this.specialtyid = (int) con.createQuery(sqlCommand, true)
      .addParameter("specialtyname", this.specialtyName)
      .executeUpdate()
      .getKey();
    }
  }

  public static Specialty find(int specialtyid) {
    try(Connection con = DB.sql2o.open()) {
      String sqlCommand = "SELECT * FROM specialties where specialtyid=:specialtyid";
      Specialty specialty = con.createQuery(sqlCommand)
      .addParameter("specialtyid", specialtyid)
      .executeAndFetchFirst(Specialty.class);
      return specialty;
    }
  }

  public List<Doctor> getDoctors(){
    try(Connection con = DB.sql2o.open()){
      String sqlCommand = "SELECT * FROM doctors WHERE specialtyid=:specialtyid;";
      return
      con.createQuery(sqlCommand)
      .addParameter("specialtyid", this.specialtyid)
      .executeAndFetch(Doctor.class);
    }
  }

}
