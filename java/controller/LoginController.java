package controller;

import spark.ModelAndView;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

public class LoginController {

  public static ModelAndView loginGet(Request req) {
    Map<String, Object> model = new HashMap<>();
    if (req.cookie("username") != null)
      model.put("username", req.cookie("username"));
    return new ModelAndView(model, "public/login.vm");
  }
}
