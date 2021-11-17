package model;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "individuals")
public class Individual extends User {

    @DatabaseField
    private String tag;
    @DatabaseField(canBeNull = false)
    private String email;

    public Individual() {
        super("");
        tag = "";
        email = "";
    }

    /**
     * This is a class used to represent an individual, which is an
     * extension of the user class.
     * @param name string of name of the user (ID is already created)
     */
    public Individual(String name, String tag, String email) {
        super(name);
        this.tag = tag;
        this.email = email;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
