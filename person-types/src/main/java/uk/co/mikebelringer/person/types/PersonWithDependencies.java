package uk.co.mikebelringer.person.types;

import java.util.List;

public class PersonWithDependencies extends Person {

    public static final String TYPE = "personWithDependencies";

    private List<Person> dependencies;

    public PersonWithDependencies() { }

    public PersonWithDependencies(String firstName, String lastName, List<Person> dependencies) {
        super(firstName, lastName);
        this.dependencies = dependencies;
    }

    public List<Person> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Person> dependencies) {
        this.dependencies = dependencies;
    }
}
