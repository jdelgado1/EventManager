package controller;

import model.*;
import spark.ModelAndView;
import spark.Request;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchController {

  public static ModelAndView filterTagGet(Request req) throws SQLException {
    Map<String, Object> model = new HashMap<>();

    String tag = req.queryParams("tag");
    System.out.println("Tag: " + tag);
    if (tag == null) {
      List<SocialEvent> allSoc = DaoController.getSocialEventsORMLiteDao().queryForAll();
      List<Seminar> allSem = DaoController.getSeminarsORMLiteDao().queryForAll();
      model.put("socialevents", allSoc);
      model.put("seminars", allSem);

    } else {
      //NOTE: we need to do .contains() because the tags are stored as CSV strings with multiple tag values
      // i.e. cannot just do queryForEq
      tag = tag.toLowerCase();
      List<SocialEvent> allSoc = DaoController.getSocialEventsORMLiteDao().queryForAll();
      List<SocialEvent> socMatch = new ArrayList<>();
      for (SocialEvent s : allSoc) {
        String tags = s.getTag();
        System.out.println(tags);
        if (tags != null) {
          if (tags.contains(tag)) {
            System.out.println("contains tag");
            socMatch.add(s);
          }
        }
      }

      List<Seminar> allSem = DaoController.getSeminarsORMLiteDao().queryForAll();
      List<Seminar> semMatch = new ArrayList<>();
      for (Seminar s : allSem) {
        String tags = s.getTag();
        System.out.println(tags);
        if (tags != null) {
         if (tags.contains(tag)) {
           System.out.println("contains tag");
           semMatch.add(s);
         }
       }
      }
      model.put("socialevents", socMatch);
      model.put("seminars", semMatch);
     /* List<SocialEvent> socMatch = DaoController.getSocialEventsORMLiteDao().queryForEq("tag", tag);
      List<Seminar> semMatch = DaoController.getSeminarsORMLiteDao().queryForEq("tag", tag);
      System.out.println("Social Events: " + socMatch.size());
      model.put("socialevents", socMatch);
      model.put("seminars", semMatch);*/
    }
    List<Individual> allIndiv = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", allIndiv);
    List<Event> allEvents = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", allEvents);
    List<Group> allGroups = DaoController.getGroupsORMLiteDao().queryForAll();
    model.put("groups", allGroups);
    return new ModelAndView(model, "public/homepageFilterByTag.vm");
  }

  public static ModelAndView filterText(Request req) throws SQLException {
    Map<String, Object> model = new HashMap<>();

    String searchString = req.queryParams("searchString");

    System.out.println("in filter text");
    System.out.println(searchString);

    if (searchString == null) {
      List<SocialEvent> allSoc = DaoController.getSocialEventsORMLiteDao().queryForAll();
      List<Seminar> allSem = DaoController.getSeminarsORMLiteDao().queryForAll();
      model.put("socialevents", allSoc);
      model.put("seminars", allSem);

    } else {
      searchString = searchString.toLowerCase();

      List<SocialEvent> allSoc = DaoController.getSocialEventsORMLiteDao().queryForAll();
      List<SocialEvent> socMatch = new ArrayList<>();
      for (SocialEvent i : allSoc) {
        String i_title = i.getTitle().toLowerCase();
        String i_description = i.getDescription().toLowerCase();
        if (i_title.contains(searchString) || i_description.contains(searchString)) {
          socMatch.add(i);
        }
      }

      List<Seminar> allSem = DaoController.getSeminarsORMLiteDao().queryForAll();
      System.out.println(allSem.size());
      List<Seminar> semMatch = new ArrayList<>();
      for (Seminar i : allSem) {
        String i_title = i.getTitle().toLowerCase();
        String i_description = i.getDescription().toLowerCase();
        if (i_title.contains(searchString) || i_description.contains(searchString)) {
          System.out.println("found a match");
          semMatch.add(i);
        }
      }

      model.put("socialevents", socMatch);
      model.put("seminars", semMatch);
    }

    //figure out what to put for topEvent
    //if this is an empty or null search, just put all hottest events
    if (searchString == null || searchString.isEmpty()) {
      model.put("topevent", DaoController.getHottestEvent());
    } else {
      //otherwise, put only the hottest events that match this search string
      List<Event> allHottest = DaoController.getHottestEvent();
      List<Event> hottestMatch = new ArrayList<>();
      for (Event e : allHottest) {
        String e_title = e.getTitle().toLowerCase();
        String e_description = e.getDescription().toLowerCase();
        if (e_title.contains(searchString) || e_description.contains(searchString)) {
          hottestMatch.add(e);
        }
      }
      model.put("topevent", hottestMatch);
    }

    List<Individual> allIndiv = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", allIndiv);
    List<Event> allEvents = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", allEvents);
    List<Group> allGroups = DaoController.getGroupsORMLiteDao().queryForAll();
    model.put("groups", allGroups);
    return new ModelAndView(model, "public/homepage.vm");
  }
}
