package uk.co.mikebelringer.person.server;

import java.util.ArrayList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import uk.co.mikebelringer.person.types.Person;
import uk.co.mikebelringer.person.types.PersonWithDependencies;

public class PersonServerResource extends ServerResource {

    @Get("json")
    public Person getPerson() {
        return new Person("John", "Smith");
    }

    @Get(PersonWithDependencies.TYPE)
    public PersonWithDependencies getPersonWithDependencies() {
        List<Person> dependencies = new ArrayList<>();
        dependencies.add(new Person("Little", "Jimmy"));
        return new PersonWithDependencies("John", "Smith", dependencies);
    }
}
