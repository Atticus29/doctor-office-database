import org.sql2o.*;

public class Doctor {
  private String doctorName;
  private String doctorSpecialty;


  public Doctor(String doctorName, String doctorSpecialty) {
    this.doctorName = doctorName;
    this.doctorSpecialty = doctorSpecialty;
  }

  public String getdoctorName(){
    return doctorName;
  }

  public String getDoctorSpecialty(){
    return doctorSpecialty;
  }

}
