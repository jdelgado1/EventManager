package controller;

import com.google.gson.Gson;
import model.Event;
import spark.ModelAndView;
import spark.Request;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatController {

  public static ModelAndView heatGet() throws SQLException {
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("events", ls);
    return new ModelAndView(model, "public/geteventheat.vm");
  }

  public static String heatPost(Request req) throws SQLException {
    String eventID = req.queryParams("event");
    Double score = DaoController.eventHeat(eventID);
    return new Gson().toJson(score);
  }

  public static ModelAndView hottestGet() throws SQLException {
    Map<String, Object> model = new HashMap<>();
    model.put("topevent", DaoController.getHottestEvent());
    return new ModelAndView(model, "public/gethottestevent.vm");
  }

  public static String hottestPost() throws SQLException {
    Map<String, Object> model = new HashMap<>();
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    if (ls.size() > 0) {
      Event max = ls.get(0);
      double maxScore = 0.0;
      for (Event e : ls) {
        double hScore = DaoController.eventHeat(Integer.toString(e.getID()));
        if (hScore > maxScore) {
          max = e;
          maxScore = hScore;
        }
      }
      model.put("topevent", max);
      return new Gson().toJson(max);
    } else {
      model.put("topevent", "no events");
      return new Gson().toJson("no events");
    }
  }

  /*
        Spark.get("/hottestevent", (req, res) -> {

            Map<String, Object> model = new HashMap<>();
            List<SocialEvent> allSoc = getSocialEventsORMLiteDao().queryForAll();

            SocialEvent hottestEvent = new SocialEvent();
            List<SocialEvent> hottest = new ArrayList<>();

            int numAttendees = 0;
            for (SocialEvent i : allSoc) {

                String eventID = i.getTitle();
                Dao cicoDao = getCheckInCheckOutORMLiteDao();
                List<CheckInCheckOut> cicoList = cicoDao.queryForEq("event_id", eventID);
                List<Individual> indList = new ArrayList<>();

                for (CheckInCheckOut c : cicoList) {
                    if (c.getCheckOutTime() == null) {
                        indList.add(c.getIndividual());
                    }
                }

                int numAttendees1 = indList.size();
                if (numAttendees1 >= numAttendees) {
                    hottestEvent = i;
                }

                numAttendees = numAttendees1;
            }

            hottest.add(hottestEvent);

            model.put("socialevents", hottest);
            return new ModelAndView(model, "public/homepage.vm");
        }, new VelocityTemplateEngine());
    */


}
