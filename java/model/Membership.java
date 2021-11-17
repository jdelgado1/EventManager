package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "memberships")
public class Membership {
    @DatabaseField(generatedId = true)
    Integer ID;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnDefinition = "int references groups(ID)")
    Group group;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnDefinition = "int references individuals(ID)")
    Individual individual;

    public Membership() {

    }

    public Group getGroup() {
        return this.group;
    }

    public Individual getIndividual() {
        return individual;
    }

    public Membership(Group group, Individual individual) {
        this.group = group;
        this.individual = individual;
    }
}
