package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDateTime;
import java.util.Date;

@DatabaseTable(tableName = "socialevents")
public class SocialEvent extends Event {

    @DatabaseField
    String organization;

    public SocialEvent(String title, String description, Address address, Date date_time, Individual host, boolean _private, String organization, String tag, Integer capacity) {
        super(title, description, address, date_time, host, _private, tag, capacity);
        this.organization = organization;
    }
    public SocialEvent() {
    }

    public String getOrganization() { return organization; }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


    @Override
    public String toString() {
        return "Event{" +
                "Title='" + super.getTitle() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", date_time=" + super.getDate_time().toString()+
                ", private=" + super.is_private() +
                ", organization= " + organization +
                '}';
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }
}
