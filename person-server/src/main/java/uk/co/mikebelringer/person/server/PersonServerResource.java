package uk.co.mikebelringer.person.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import uk.co.mikebelringer.person.types.Person;
import uk.co.mikebelringer.person.types.PersonWithTitle;

public class PersonServerResource extends ServerResource {

    @Get("json")
    public Person getPerson() {
        return new Person("John", "Smith");
    }

    @Get(PersonWithTitle.TYPE)
    public PersonWithTitle getPersonWithTitle() {
        return new PersonWithTitle("John", "Smith", "Mr");
    }
}
