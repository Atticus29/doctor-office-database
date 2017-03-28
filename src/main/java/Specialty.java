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
      return this.getSpecialtyName().equals(newSpecialty.getSpecialtyName());
    }
  }

  public String getSpecialtyName(){
    return specialtyName;
  }

  // public int getSpecialtyId(){
  //   return specialtyId;
  // }

  public static List<Specialty> all() {
    String sql = "SELECT specialtyid, specialtyname FROM specialties";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sqlCommand = "INSERT INTO specialties (specialtyname) VALUES (:specialtyname);";
      con.createQuery(sqlCommand)
        .addParameter("specialtyname", specialtyName)
        .executeUpdate();
    }
  }
}
