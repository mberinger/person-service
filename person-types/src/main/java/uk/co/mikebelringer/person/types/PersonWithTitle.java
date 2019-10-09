package uk.co.mikebelringer.person.types;

public class PersonWithTitle extends Person {

    public static final String TYPE = "personWithTitle";

    private String title;

    public PersonWithTitle() { }

    public PersonWithTitle(String firstName, String lastName, String title) {
        super(firstName, lastName);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
