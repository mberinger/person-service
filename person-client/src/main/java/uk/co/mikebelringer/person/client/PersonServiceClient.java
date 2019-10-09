package uk.co.mikebelringer.person.client;

import java.io.IOException;

import org.restlet.data.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.mikebelringer.person.types.Person;
import uk.co.mikebelringer.person.types.PersonWithTitle;

public class PersonServiceClient {

    ObjectMapper objectMapper = new ObjectMapper();

    private String protocol = "http";
    private String host = "127.0.0.1";
    private int port = 8182;

    public Person getPerson() {
        return getPerson(Person.class);
    }

    public PersonWithTitle getPersonWithTitle() {
        return getPerson(PersonWithTitle.class, "application/vnd." + PersonWithTitle.TYPE + "+json");
    }

    private <T extends Person> T getPerson(Class<T> personClass) {
        return getPerson(personClass, null);
    }

    private <T extends Person> T getPerson(Class<T> personClass, String acceptType) {
        ClientResource cr = new ClientResource(protocol + "://" + host + ":" + port + "/person");

        if(acceptType != null) {
            Series<Header> headerValue = new Series<>(Header.class);
            headerValue.add("Accept", acceptType);
            cr.getRequest().getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headerValue);
        }

        Representation representation = cr.get();
        try {
            return objectMapper.readValue(representation.getStream(), personClass);
        } catch ( IOException e ) {
            return null;
        }
    }
}