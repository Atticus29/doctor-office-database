import org.sql2o.*;
import java.util.List;

public class Doctor {
  private String doctorName;
  private int doctorid;
  private int specialtyid;

  public Doctor(String doctorName, int specialtyid) {
    this.doctorName = doctorName;
    this.specialtyid = specialtyid;
  }

  public String getDoctorName(){
    return doctorName;
  }

  public int getSpecialtyId() {
    return specialtyid;
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getDoctorName().equals(newDoctor.getDoctorName());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sqlCommand = "INSERT INTO doctors (doctorName, specialtyid) VALUES (:doctorName, :specialtyid);";
      this.doctorid = (int)
        con.createQuery(sqlCommand, true)
          .addParameter("doctorName", this.doctorName).addParameter("specialtyid", this.specialtyid)
          .executeUpdate()
          .getKey();
    }
  }

  public static List<Doctor> all(){
    String sqlCommand = "SELECT * FROM doctors;";
    try(Connection con = DB.sql2o.open()){
      return
      con.createQuery(sqlCommand)
      .executeAndFetch(Doctor.class);
    }
  }

}
