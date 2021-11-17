package controller;

import model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IndexController {

  public static ModelAndView indexController() throws SQLException {
    Map<String, Object> model = new HashMap<>();
    List<SocialEvent> ls1 = DaoController.getSocialEventsORMLiteDao().queryForAll();
    model.put("socialevents", ls1);
    List<Seminar> ls2 = DaoController.getSeminarsORMLiteDao().queryForAll();
    model.put("seminars", ls2);
    List<Event> ls4 = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", ls4);
    List<Individual> ls3 = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", ls3);
    List<Group> groupLs = DaoController.getGroupsORMLiteDao().queryForAll();
    model.put("groups", groupLs);
    model.put("topevent", DaoController.getHottestEvent());
    return new ModelAndView(model, "public/homepage.vm");
  }

  public static ModelAndView loginGetController(Request req) {
    Map<String, Object> model = new HashMap<>();
    if (req.cookie("username") != null)
      model.put("username", req.cookie("username"));
    return new ModelAndView(model, "public/index.vm");
  }

  public static Response loginPostController(Request req, Response res) {
    String username = req.queryParams("username");
    res.cookie("username", username);
    res.redirect("/index");
    return res;
  }
}
