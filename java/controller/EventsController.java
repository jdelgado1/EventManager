package controller;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventsController {

  public static Address formatAddress(Request req) {
    String addLine1 = req.queryParams("addLine1");
    String addLine2 = req.queryParams("addLine2");
    String city = req.queryParams("city");
    String state = req.queryParams("state");
    String zip = req.queryParams("zip");

    return new Address(addLine1, addLine2, city, state, zip);
  }


  public static ModelAndView eventsGet() throws SQLException {
    Map<String, Object> model = new HashMap<>();

    List<Event> ls_all = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", ls_all);

    return new ModelAndView(model, "public/events.vm");
  }

  public static void inviteAllPublicEvent(Event e) throws SQLException{
    Dao indDao = DaoController.getIndividualsORMLiteDao();
    Dao invDao = DaoController.getInvitationsORMLiteDao();
    List<Individual> indList = indDao.queryForAll();
    for (Individual i : indList) {
      Invitation invitation= new Invitation(e, i);
      invDao.create(invitation);
    }
  }

  public static ModelAndView addEventsGet() {
    Map<String, Object> model = new HashMap<String, Object>();
    return new ModelAndView(model, "public/addevents.vm");
  }

  public static ModelAndView socialGet() throws SQLException {
    List<User> ls = DaoController.getSocialEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("socialevents", ls);
    return new ModelAndView(model, "public/socialevents.vm");
  }

  public static ModelAndView addSocialGet() throws SQLException {
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls1);
    return new ModelAndView(model, "public/addsocialevents.vm");
  }

  public static String socialPost(Request req, Response res) throws SQLException, ParseException {
    String title = req.queryParams("title");
    String description = req.queryParams("description");
    String organization = req.queryParams("organization");

    String capacityStr = req.queryParams("capacity");
    int capacity = Integer.MAX_VALUE;
    if (!capacityStr.isEmpty()) {
      capacity = Integer.parseInt(capacityStr);
    }

    String tags = req.queryParams("tags");

    boolean priv = (req.queryParams("priv").equals("true"));

    Individual host = (Individual) DaoController.getIndividualsORMLiteDao().queryForId(req.queryParams("individual"));

    Address address = formatAddress(req);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date datetime = format.parse(req.queryParams("datetime"));

    DaoController.getAddressesORMLiteDao().create(address);
    Event createdEvent = DaoController.createEvent(address, title, description, datetime, host, priv, tags, capacity);

    SocialEvent socialEvent = new SocialEvent(title, description, address, datetime, host, priv, organization, tags, capacity);
    DaoController.getSocialEventsORMLiteDao().create(socialEvent);

    if (createdEvent == null) {
      System.out.println("ERROR: COULD NOT CREATE EVENT");
    } else {
      if (!priv) {
        inviteAllPublicEvent(createdEvent);
      }
    }

    res.status(201);
    res.type("application/json");
    res.redirect("/");
    return new Gson().toJson(socialEvent.toString());
  }

  public static ModelAndView seminarsGet() throws SQLException {
    List<User> ls = DaoController.getSeminarsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("seminars", ls);
    return new ModelAndView(model, "public/seminars.vm");
  }

  public static ModelAndView addSeminarsGet() throws SQLException {
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls1);
    return new ModelAndView(model, "public/addseminars.vm");
  }

  public static String seminarPost(Request req, Response res) throws SQLException, ParseException {
    String title = req.queryParams("title");
    String description = req.queryParams("description");
    String speaker = req.queryParams("speaker");
    String school = req.queryParams("school");
    String field = req.queryParams("field");

    String capacityStr = req.queryParams("capacity");
    int capacity = Integer.MAX_VALUE;
    if (!capacityStr.isEmpty()) {
      capacity = Integer.parseInt(capacityStr);
    }

    String date = req.queryParams("datetime");

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date datetime = format.parse(req.queryParams("datetime"));

    boolean priv = (req.queryParams("priv").equals("true"));

    Individual host = (Individual) DaoController.getIndividualsORMLiteDao().queryForId(req.queryParams("individual"));

    Address address = formatAddress(req);

    String tag = req.queryParams("tags");

    DaoController.getAddressesORMLiteDao().create(address);
    DaoController.createEvent(address, title, description, datetime, host, priv, tag, capacity);

    Seminar seminar = new Seminar(title, description, address, datetime, host, priv, speaker, school, field, tag, capacity);
    DaoController.getSeminarsORMLiteDao().create(seminar);

    res.status(201);
    res.type("application/json");
    res.redirect("/");
    return new Gson().toJson(seminar.toString());
  }

  public static String socialEventDelete(Request req, Response res) throws SQLException, ParseException {
    String socEventID = req.queryParams("ID");
    List<SocialEvent> socEvents = DaoController.getSocialEventsORMLiteDao().queryForEq("ID", Integer.parseInt(socEventID));
    int del = 0;
    if (socEvents != null  && !socEvents.isEmpty()) {
      del = DaoController.getSocialEventsORMLiteDao().deleteById(socEvents.get(0).getID());
    }
    res.status(200);
    res.type("application/json");
    if (del > 0) {
      return new Gson().toJson(socEvents.get(0).toString());
    }
    return new Gson().toJson("{}");
  }

  public static String seminarDelete(Request req, Response res) throws SQLException, ParseException {
    String semID = req.queryParams("ID");
    List<Seminar> sems = DaoController.getSeminarsORMLiteDao().queryForEq("ID", Integer.parseInt(semID));
    int del = 0;
    if (sems != null  && !sems.isEmpty()) {
      del = DaoController.getSeminarsORMLiteDao().deleteById(sems.get(0).getID());
    }
    res.status(200);
    res.type("application/json");
    if (del > 0) {
      return new Gson().toJson(sems.get(0).toString());
    }
    return new Gson().toJson("{}");
  }

  public static String eventDelete(Request req, Response res) throws SQLException, ParseException {
    System.out.println("in del ev");
    String eventTitle = req.queryParams("title");
    System.out.println(eventTitle);
    List<Event> evs = DaoController.getEventsORMLiteDao().queryForEq("title", eventTitle);
    int del = 0;
    if (evs != null  && !evs.isEmpty()) {
      System.out.println("found ev to del");
      del = DaoController.getEventsORMLiteDao().deleteById(evs.get(0).getID());
    }
    res.status(200);
    res.type("application/json");
    if (del > 0) {
      return new Gson().toJson(evs.get(0).toString());
    }
    return new Gson().toJson("{}");
  }

  public static ModelAndView createEventGet() throws SQLException {
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls1);
    List<Event> ls2 = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", ls2);
    return new ModelAndView(model, "public/createEvent.vm");
  }



}
