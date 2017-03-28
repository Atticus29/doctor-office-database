import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {
  private Doctor testDoctor;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/dr_office_mfss_test", null, null);
    testDoctor = new Doctor("Dr.Sexy",1);
    testDoctor.save();
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String deleteSpecialties = "DELETE FROM specialties *;";
      String deleteDoctors = "DELETE FROM doctors *;";
      con.createQuery(deleteSpecialties).executeUpdate();
      con.createQuery(deleteDoctors).executeUpdate();
    }
  }

  @Test
  public void save_correctlySavesDoctor_true(){
    Doctor retrievedDoctor = Doctor.all().get(0);
    assertTrue(testDoctor.equals(retrievedDoctor));
  }

  @Test
  public void equals_returnsTrueIfDescriptionsSame_true() {
    Doctor testDoctor2 = new Doctor("Dr.Mark",1);
    Doctor testDoctor3 = new Doctor("Dr.Mark",1);
    assertTrue(testDoctor2.equals(testDoctor3));
  }

}
