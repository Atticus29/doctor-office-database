import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class SpecialtyTest {
  private Specialty testSpecialty;

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/dr_office_mfss_test", null, null);
    testSpecialty = new Specialty("Radiology");
    testSpecialty.save();
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
  public void getSpecialtyName_returnsName_true(){
    assertEquals("Radiology", testSpecialty.getSpecialtyName());
  }

  @Test
  public void getSpecialtyId_returnsId_int(){
    // System.out.println("ID is " + testSpecialty.getSpecialtyId());
    assertTrue(testSpecialty.getSpecialtyId()>0);
  }

  @Test
  public void getsAllSpecialties_true() {
    Specialty testSpecialty2 = new Specialty("Dermatology");
    testSpecialty2.save();
    assertEquals(true, Specialty.all().get(0).equals(testSpecialty));
    assertEquals(true, Specialty.all().get(1).equals(testSpecialty2));
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Specialty testSpecialty3 = new Specialty("Radiology");
    Specialty testSpecialty2 = new Specialty("Radiology");
    assertTrue(testSpecialty3.equals(testSpecialty2));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame_true(){
    Specialty retrievedSpecialty = Specialty.all().get(0);
    assertEquals(retrievedSpecialty, testSpecialty);
  }

  @Test
  public void save_assignsIdToObject(){
    Specialty retrievedObject = Specialty.all().get(0);
    assertEquals(testSpecialty.getSpecialtyId(),retrievedObject.getSpecialtyId());
  }

  @Test
  public void find_returnsSpecialtyWithSameId_secondSpecialty() {
    Specialty testSpecialty2 = new Specialty("Dermatology");
    testSpecialty2.save();
    assertEquals(Specialty.find(testSpecialty2.getSpecialtyId()), testSpecialty2);
  }

  @Test
  public void getDoctors_retrievesAllDoctorsFromDatabase_DoctorList(){
    System.out.println(testSpecialty.getSpecialtyId());
    Doctor testDoctor1 = new Doctor("Dr.Tall",testSpecialty.getSpecialtyId());
    testDoctor1.save();
    Doctor testDoctor2 = new Doctor("Dr.Dark",testSpecialty.getSpecialtyId());
    testDoctor2.save();
    Doctor testDoctor3 = new Doctor("Dr.Handsome",testSpecialty.getSpecialtyId());
    testDoctor3.save();
    Doctor [] theDoctors = new Doctor[] {testDoctor1, testDoctor2, testDoctor3};

    assertTrue(testSpecialty.getDoctors().containsAll(Arrays.asList(theDoctors)));
  }

}
