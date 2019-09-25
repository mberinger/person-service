package uk.co.mikebelringer.person.client;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.mikebelringer.person.types.Person;

public class PersonServiceClient {

    ObjectMapper objectMapper = new ObjectMapper();

    private String protocol = "http";
    private String host = "127.0.0.1";
    private int port = 8182;

    public Person getPerson() {
        ClientResource cr = new ClientResource(protocol + "://" + host + ":" + port + "/person");
        Representation representation= cr.get();
        try {
            return objectMapper.readValue(representation.getStream(), Person.class);
        } catch ( IOException e ) {
            return null;
        }
    }
}
