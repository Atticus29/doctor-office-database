import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

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
      String sql = "DELETE FROM specialties *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void getSpecialtyName_returnsName_true(){
    assertEquals("Radiology", testSpecialty.getSpecialtyName());
  }

  @Test
  public void getSpecialtyId_returnsId_int(){
    System.out.println("ID is " + testSpecialty.getSpecialtyId());
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
    Specialty testSpecialty2 = new Specialty("Radiology");
    assertTrue(testSpecialty.equals(testSpecialty2));
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

}
