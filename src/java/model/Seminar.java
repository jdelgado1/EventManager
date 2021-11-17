package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDateTime;
import java.util.Date;

@DatabaseTable(tableName = "seminars")
public class Seminar extends Event {

    @DatabaseField
    String speaker;
    @DatabaseField
    String school;
    @DatabaseField
    String field;

    public Seminar(String title, String description, Address address, Date date_time, Individual host, boolean _private, String speaker, String school, String field, String tag, Integer capacity) {
        super(title, description, address, date_time, host, _private, tag, capacity);
        this.speaker = speaker;
        this.school = school;
        this.field = field;
    }
    public Seminar() {
    }


    public String getSpeaker() { return speaker; }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getSchool() { return school; }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getField() { return field; }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + super.getTitle() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", date_time=" + super.getDate_time().toString()+
                ", private=" + super.is_private() +
                ", speaker=" + speaker +
                ", school=" + school +
                ", field=" + field +
                '}';
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }
}
