package step.java.web1.models;

// ORM for table Pictures
public class Picture {
    private String Id;
    private String Name;
    private String Description;
    private String Moment;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMoment() {
        return Moment;
    }

    public void setMoment(String moment) {
        Moment = moment;
    }

    public Picture(String name, String description) {
        Name = name;
        Description = description;
    }
}
