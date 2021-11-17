package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "invitations")
public class Invitation {
    @DatabaseField(generatedId = true)
    Integer ID;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnDefinition = "int references events(ID)")
    Event event;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnDefinition = "int references individuals(ID)")
    Individual individual;

    public Invitation(Event event, Individual individual) {
        this.event = event;
        this.individual = individual;
    }

    public Invitation() {

    }

    public Individual getIndividual() {
        return this.individual;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "ID=" + ID +
                ", event=" + event +
                ", individual=" + individual +
                '}';
    }
}
