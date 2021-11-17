package controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaoController {
  final static String URI = "jdbc:sqlite:./EventApp.db";

  public static Dao getUsersORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, User.class);
    return DaoManager.createDao(connectionSource, User.class);
  }

  public static Dao getEventsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Event.class);
    return DaoManager.createDao(connectionSource, Event.class);
  }

  public static Dao getIndividualsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Individual.class);
    return DaoManager.createDao(connectionSource, Individual.class);
  }

  public static Dao getGroupsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Group.class);
    return DaoManager.createDao(connectionSource, Group.class);
  }

  public static Dao getAddressesORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Address.class);
    return DaoManager.createDao(connectionSource, Address.class);
  }

  public static Dao getInvitationsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Invitation.class);
    return DaoManager.createDao(connectionSource, Invitation.class);
  }

  public static Dao getSocialEventsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, SocialEvent.class);
    return DaoManager.createDao(connectionSource, SocialEvent.class);
  }

  public static Dao getSeminarsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Seminar.class);
    return DaoManager.createDao(connectionSource, Seminar.class);
  }

  public static Dao getMembershipsORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, Membership.class);
    return DaoManager.createDao(connectionSource, Membership.class);
  }

  public static Dao getCheckInCheckOutORMLiteDao() throws SQLException {
    ConnectionSource connectionSource = new JdbcConnectionSource(URI);
    TableUtils.createTableIfNotExists(connectionSource, CheckInCheckOut.class);
    return DaoManager.createDao(connectionSource, CheckInCheckOut.class);
  }

  public static Event createEvent(Address address, String title, String description,
                                    Date datetime, Individual host, boolean priv, String tag, Integer capacity) {
    Event event = new Event(title, description, address, datetime, host, priv, tag, capacity);
    try {
      getEventsORMLiteDao().create(event);
      return event;
    } catch (java.sql.SQLException e) {
      return null;
    }
  }

  public static double eventHeat(String eventID) throws SQLException {
    Dao cicoDao = getCheckInCheckOutORMLiteDao();
    List<CheckInCheckOut> cicoList = cicoDao.queryForEq("event_id", eventID);
    long cum_time_diff = 0;
    Date now = new Date(System.currentTimeMillis());
    for (CheckInCheckOut c : cicoList) {
      if (c.getCheckOutTime() == null) {
        cum_time_diff += now.getTime() - c.getCheckInTime().getTime();
      }
    }
    cum_time_diff = cum_time_diff / 36000;
    double score = 0.0;
    if (cum_time_diff != 0) {
      score = Math.pow(cicoList.size(), 2) / Math.sqrt((double) cum_time_diff);
    }
    return score;
  }

  public static List<Event> getHottestEvent() throws SQLException {
    List<Event> ls = getEventsORMLiteDao().queryForAll();

    if (ls.size() > 0) {
      List<Event> outputList = new ArrayList<>();
      outputList.add(ls.get(0));
      double maxScore = 0.0;
      for (Event e : ls) {
        double hScore = eventHeat(Integer.toString(e.getID()));
        if (hScore > maxScore) {
          outputList.remove(0);
          outputList.add(e);
          maxScore = hScore;
        }
      }

      return outputList;
    } else {
      return ls;
    }
  }
}
