package controller;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.*;

public class JoinController {

  public static ModelAndView invitationsGet() throws SQLException {
    List<Invitation> ls = DaoController.getInvitationsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("invitations", ls);
    return new ModelAndView(model, "public/invitations.vm");
  }

  public static String invitationsPost(Request req, Response res) throws SQLException {
    Event event = (Event) DaoController.getEventsORMLiteDao().queryForId(req.queryParams("event"));
    Individual individual = (Individual) DaoController.getIndividualsORMLiteDao().queryForId(req.queryParams("individual"));
    Invitation invitation = new Invitation(event, individual);
    DaoController.getInvitationsORMLiteDao().create(invitation);
    res.redirect("/");
    return new Gson().toJson(invitation.toString());
  }

  public Integer howManyPeopleAreAtAnEvent(Event e) throws SQLException {
    Dao cicoDao = DaoController.getCheckInCheckOutORMLiteDao();
    List<CheckInCheckOut> checkedInList = cicoDao.queryForEq("event_id", e.getID());
    Integer numPeople = new Integer(0);
    for (CheckInCheckOut cico : checkedInList) {
      if (cico.getCheckOutTime() == null) {
        numPeople ++;
      }
    }
    return numPeople;
  }

  public static ModelAndView membershipsGet() throws SQLException {
    List<Membership> ls = DaoController.getMembershipsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("memberships", ls);
    return new ModelAndView(model, "public/memberships");
  }

  public static ModelAndView inviteToEventGet() throws SQLException {
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls1);
    List<Event> ls2 = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", ls2);
    return new ModelAndView(model, "public/invite.vm");
  }

  public static ModelAndView checkInEventGet() throws SQLException {
    List<Individual> ls1 = DaoController.getIndividualsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("individuals", ls1);
    List<Event> ls2 = DaoController.getEventsORMLiteDao().queryForAll();
    model.put("events", ls2);
    return new ModelAndView(model, "public/checkInEvent.vm");
  }

  public static String membershipPost(Request req) throws SQLException {
    Group group = (Group) DaoController.getGroupsORMLiteDao().queryForId(req.queryParams("group"));
    Individual individual = (Individual) DaoController.getIndividualsORMLiteDao().queryForId(req.queryParams("individual"));
    Membership membership = new Membership(group, individual);
    DaoController.getMembershipsORMLiteDao().create(membership);
    return new Gson().toJson(membership.toString());
  }

  public static ModelAndView inviteGet() throws SQLException {
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("events", ls);
    List<User> ls2 = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", ls2);
    return new ModelAndView(model, "public/inviteindividual.vm");
  }

  public static ModelAndView addMemberGet() throws SQLException {
    Map<String, Object> model = new HashMap<>();
    List<User> ls2 = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", ls2);
    List<Group> ls = DaoController.getGroupsORMLiteDao().queryForAll();
    model.put("groups", ls);
    return new ModelAndView(model, "public/addmember.vm");
  }

  public static ModelAndView checkInGet() throws SQLException {
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("events", ls);
    List<User> ls2 = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", ls2);
    return new ModelAndView(model, "public/checkin.vm");
  }

  public static ModelAndView checkInPost(Request req, Response res) throws SQLException {
    Event event = (Event) DaoController.getEventsORMLiteDao().queryForId(req.queryParams("event"));
    Individual individual = (Individual) DaoController.getIndividualsORMLiteDao().queryForId(req.queryParams("individual"));
    Date now = new Date(System.currentTimeMillis());
    CheckInCheckOut CICO = new CheckInCheckOut(event, individual, now);
    DaoController.getCheckInCheckOutORMLiteDao().create(CICO);
//    return new Gson().toJson(CICO.toString());
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

  public static ModelAndView checkOutGet() throws SQLException {
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("events", ls);
    List<User> ls2 = DaoController.getIndividualsORMLiteDao().queryForAll();
    model.put("individuals", ls2);
    return new ModelAndView(model, "public/checkout.vm");
  }

  public static String checkOutPost(Request req, Response res) throws SQLException {
    Event event = (Event) DaoController.getEventsORMLiteDao().queryForId(req.queryParams("event"));
    Individual individual = (Individual) DaoController.getIndividualsORMLiteDao().queryForId(req.queryParams("individual"));
    Date now = new Date(System.currentTimeMillis());
    Dao CICODao = DaoController.getCheckInCheckOutORMLiteDao();
    List<CheckInCheckOut> cicoLs = CICODao.queryForEq("individual_id", individual.getID());
    CheckInCheckOut toUpdate = null;
    for (CheckInCheckOut c : cicoLs) {
      if (c.getEvent().getID() == event.getID()) {
        toUpdate = c;
        break;
      }
    }
    if (toUpdate != null) {
      toUpdate.setCheckOutTime(now);
      CICODao.update(toUpdate);
    }
    res.redirect("/");
    return new Gson().toJson(toUpdate.toString());
  }

  public static ModelAndView invitedListGet() throws SQLException {
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("events", ls);
    return new ModelAndView(model, "public/findwhoscheckedin.vm");
  }

  public static String invitedListPost(Request req) throws SQLException {
    String eventID = req.queryParams("event");
    Dao cicoDao = DaoController.getCheckInCheckOutORMLiteDao();
    List<CheckInCheckOut> cicoList = cicoDao.queryForEq("event_id", eventID);
    List<Individual> indList = new ArrayList<>();
    for (CheckInCheckOut c : cicoList) {
      if (c.getCheckOutTime() == null) {
        indList.add(c.getIndividual());
      }
    }
    return new Gson().toJson(indList);
  }

  public static ModelAndView invitedSearchGet() throws SQLException {
    List<Event> ls = DaoController.getEventsORMLiteDao().queryForAll();
    Map<String, Object> model = new HashMap<>();
    model.put("events", ls);
    return new ModelAndView(model, "public/findwhosinvited.vm");
  }

  public static String invitedSearchPost(Request req) throws SQLException {
    String eventID = req.queryParams("event");
    Dao invDao = DaoController.getInvitationsORMLiteDao();
    List<Invitation> invlist = invDao.queryForEq("event_id", eventID);
    List<Individual> indList = new ArrayList<>();
    for (Invitation i : invlist) {
      indList.add(i.getIndividual());
    }
    return new Gson().toJson(indList);
  }

}
