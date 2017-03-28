import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("specialties", Specialty.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String specialtyName = request.queryParams("specialtyName");
      Specialty newSpecialty = new Specialty(specialtyName);
      newSpecialty.save();
      response.redirect("/");
      // model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/specialties/:specialtyId", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      // int specialtyId = Integer.parseInt(request.params(":specialtyId"));
      // Specialty targetSpecialty = Specialty.find(specialtyId);
      Specialty targetSpecialty = Specialty.find(Integer.parseInt(request.params(":specialtyId")));
      model.put("specialty", targetSpecialty);
      model.put("template", "templates/specialty.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
