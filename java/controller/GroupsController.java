package controller;

import com.google.gson.Gson;
import model.Group;
import model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupsController {

  public static ModelAndView groupsGet() throws SQLException {
    List<User> ls = DaoController.getGroupsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("groups", ls);
    return new ModelAndView(model, "public/groups.vm");
  }

  public static ModelAndView addGroupGet() throws SQLException {
    List<Group> ls = DaoController.getGroupsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("groups", ls);
    return new ModelAndView(model, "public/addGroup.vm");
  }

  public static ModelAndView addGroupsGet() {
    Map<String, Object> model = new HashMap<String, Object>();
    return new ModelAndView(model, "public/addgroups.vm");
  }

  public static String groupsPost(Request req, Response res) throws SQLException {
    String name = req.queryParams("name");
    System.out.println("Group name: " + name);
    Group g = new Group(name);
    DaoController.getGroupsORMLiteDao().create(g);
    res.status(201);
    res.type("application/json");
    res.redirect("/groups");
    return new Gson().toJson(g.toString());
  }
}
