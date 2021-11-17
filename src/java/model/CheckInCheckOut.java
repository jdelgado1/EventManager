package model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Objects;

@DatabaseTable(tableName = "checkincheckout")
public class CheckInCheckOut {
    @DatabaseField(generatedId = true)
    Integer ID;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnDefinition = "int references events(ID)")
    Event event;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnDefinition = "int references individuals(ID)")
    Individual individual;
    @DatabaseField(canBeNull = false)
    Date checkInTime;
    @DatabaseField(canBeNull = true)
    Date checkOutTime;

    public CheckInCheckOut() {

    }

    public Event getEvent() {
        return event;
    }

    public Individual getIndividual() {
        return individual;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public CheckInCheckOut(Event event, Individual individual, Date checkInTime) {
        this.event = event;
        this.individual = individual;
        this.checkInTime = checkInTime;
    }

    public void checkOut(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    @Override
    public String toString() {
        return "CheckInCheckOut{" +
                "ID=" + ID +
                ", event=" + event +
                ", individual=" + individual +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInCheckOut that = (CheckInCheckOut) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(event, that.event) &&
                Objects.equals(individual, that.individual) &&
                Objects.equals(checkInTime, that.checkInTime) &&
                Objects.equals(checkOutTime, that.checkOutTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, event, individual, checkInTime, checkOutTime);
    }
}
