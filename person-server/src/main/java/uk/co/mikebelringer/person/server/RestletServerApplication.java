package uk.co.mikebelringer.person.server;

import java.util.Iterator;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.routing.Router;

public class RestletServerApplication extends Application {

    public static void main(String[] args) throws Exception {
        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 8182);
        c.getDefaultHost().attach(new RestletServerApplication());

        c.start();
    }

    public RestletServerApplication() {
        getMetadataService().setDefaultMediaType(MediaType.APPLICATION_ALL_JSON);

        replaceConverter(JacksonConverter.class, new ExtJacksonConverter());
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/person", PersonServerResource.class);

        return router;
    }

    protected static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {
        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        Iterator var4 = converters.iterator();

        while(var4.hasNext()) {
            ConverterHelper converter = (ConverterHelper)var4.next();
            if (converter.getClass().equals(converterClass)) {
                converters.remove(converter);
                break;
            }
        }

        converters.add(newConverter);
    }
}
