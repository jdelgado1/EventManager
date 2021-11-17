package controller;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.UpdateBuilder;
import model.Event;
import model.Individual;
import model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.jws.WebParam;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static controller.DaoController.getIndividualsORMLiteDao;

public class IndividualController {

  public static ModelAndView individualGet() throws SQLException {
    List<User> ls = getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls);
    return new ModelAndView(model, "public/individuals.vm");
  }

  public static ModelAndView individualPageGet() throws SQLException {
    List<Individual> ls = getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls);
    return new ModelAndView(model, "public/individualPage.vm");
  }

  public static ModelAndView addIndividualsGet() {
    Map<String, Object> model = new HashMap<String, Object>();
    return new ModelAndView(model, "public/addindividuals.vm");
  }

  public static ModelAndView editIndividualsGet() {
    Map<String, Object> model = new HashMap<String, Object>();
    return new ModelAndView(model, "public/editindividuals.vm");
  }

  public static ModelAndView recommendedEventsGet() throws SQLException {
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("users", ls1);
    return new ModelAndView(model, "public/recommendedEvents.vm");
  }

  public static ModelAndView recommendedEventsPost(Request req, Response res) throws SQLException {
    String name = req.queryParams("user");
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Individual user = new Individual();
    for (Individual individual : ls1) {
      if (name.equals(individual.getName())) {
        user = individual;
      }
    }
    List<Event> ls = new ArrayList<>();
    List<Event> allEvents = DaoController.getEventsORMLiteDao().queryForAll();
    for (Event event : allEvents) {
      String eventTag = event.getTag();
      if (eventTag.contains(user.getTag())) {
        ls.add(event);
      }
    }

    res.status(201);
    Map<String, Object> model = new HashMap<>();
    if(ls.size() == 0) {
      ls.add(allEvents.get(0));
      model.put("events", ls);
      return new ModelAndView(model, "public/homepageR.vm");
    }

    List<Individual> allIndividuals = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", allIndividuals);

    model.put("events", ls);
    return new ModelAndView(model, "public/homepageR.vm");
  }


  public static String individualsPost(Request req, Response res) throws SQLException {
    String name = req.queryParams("name");
    String tags = req.queryParams("tags");
    String email = req.queryParams("email");

    Individual i = new Individual(name, tags, email);
    getIndividualsORMLiteDao().create(i);
    res.status(201);
    res.type("application/json");
    res.redirect("/individuals");
    return new Gson().toJson(i.toString());
  }

  public static String individualUpdate(Request req, Response res) throws SQLException, ParseException {
    String name = req.queryParams("name");
    String tags = req.queryParams("tags");
    String email = req.queryParams("email");

    List<Individual> inds = DaoController.getIndividualsORMLiteDao().queryForEq("email", email);
    Individual indiv = inds.get(0);
    Integer ID = indiv.getID();
    String tableName = DaoController.getIndividualsORMLiteDao().getTableName();
    indiv.setTag(tags);
    /*
    int del = 0;
    if (inds != null  && !inds.isEmpty()) {
      System.out.println("found ev to del");
      del = DaoController.getIndividualsORMLiteDao().deleteById(inds.get(0).getID());
    }
    res.status(200);
    res.type("application/json");

    Individual i = new Individual(name, tags, email);
    getIndividualsORMLiteDao().create(i);
    res.status(201);
    res.type("application/json");
    res.redirect("/individuals");
    return new Gson().toJson(i.toString());

     */

    //DaoController.getIndividualsORMLiteDao().update(indiv);


    int upd = 0;
    if (inds != null  && !inds.isEmpty()) {
      System.out.println("found indiv to update");
      //upd = DaoController.getIndividualsORMLiteDao().updateRaw("UPDATE individuals SET tag = " + tags + " WHERE ID = " + inds.get(0).getID());
      //upd = DaoController.getIndividualsORMLiteDao().updateRaw("UPDATE " + tableName + " SET tag = " + tags + " WHERE ID = " + ID);
      upd = DaoController.getIndividualsORMLiteDao().update(indiv);
    }
    res.status(200);
    res.type("application/json");
    if (upd > 0) {
      return new Gson().toJson(inds.get(0).toString());
    }
    return new Gson().toJson("{}");


  }
}
