package uk.co.mikebelringer.person.server;

import java.io.IOException;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ExtJacksonConverter extends JacksonConverter {

    private static final VariantInfo VARIANT_JSON = new VariantInfo(MediaType.APPLICATION_JSON);
    private static final VariantInfo VARIANT_JSON_ALL = new VariantInfo(MediaType.valueOf("application/*+json"));

    @Override
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        return new JacksonRepresentation<T>(mediaType, source) {

            @Override
            protected ObjectMapper createObjectMapper() {
                ObjectMapper mapper = super.createObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                mapper.setSerializationInclusion(Include.NON_NULL);
                mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                mapper.setDateFormat(new ISO8601DateFormat());
                return mapper;
            }

        };
    }

    @Override
    public List<Class<?>> getObjectClasses(Variant source) {
        List<Class<?>> result = null;

        if (VARIANT_JSON.isCompatible(source) || VARIANT_JSON_ALL.isCompatible(source)) {
            result = addObjectClass(result, Object.class);
            result = addObjectClass(result, JacksonRepresentation.class);
        }

        return result;
    }

    @Override
    public List<VariantInfo> getVariants(Class<?> source) {
        List<VariantInfo> result = null;

        if (source != null) {
            result = addVariant(result, VARIANT_JSON);
            result = addVariant(result, VARIANT_JSON_ALL);
        }

        return result;
    }

    @Override
    public float score(Object source, Variant target, Resource resource) {
        float result = -1.0F;

        if (source instanceof JacksonRepresentation<?>) {
            result = 1.0F;
        } else {
            if (target == null) {
                result = 0.5F;
            } else if (VARIANT_JSON.isCompatible(target) || VARIANT_JSON_ALL.isCompatible(target)) {
                result = 0.8F;
            } else {
                result = 0.5F;
            }
        }

        return result;
    }

    @Override
    public <T> float score(Representation source, Class<T> target, Resource resource) {
        float result = -1.0F;

        if (source instanceof JacksonRepresentation<?>) {
            result = 1.0F;
        } else if ((target != null) && JacksonRepresentation.class.isAssignableFrom(target)) {
            result = 1.0F;
        } else if (VARIANT_JSON.isCompatible(source) || VARIANT_JSON_ALL.isCompatible(source)) {
            result = 0.8F;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T toObject(Representation source, Class<T> target, Resource resource) throws IOException {
        Object result = null;

        // The source for the Jackson conversion
        JacksonRepresentation<?> jacksonSource = null;

        if (source instanceof JacksonRepresentation) {
            jacksonSource = (JacksonRepresentation<?>) source;
        } else if (VARIANT_JSON.isCompatible(source) || VARIANT_JSON_ALL.isCompatible(source)) {
            jacksonSource = create(source, target);
        }

        if (jacksonSource != null) {
            // Handle the conversion
            if ((target != null) && JacksonRepresentation.class.isAssignableFrom(target)) {
                result = jacksonSource;
            } else {
                result = jacksonSource.getObject();
            }
        }

        return (T) result;
    }

    @Override
    public Representation toRepresentation(Object source, Variant target, Resource resource) {
        Representation result = null;

        if (source instanceof JacksonRepresentation) {
            result = (JacksonRepresentation<?>) source;
        } else {
            if (target.getMediaType() == null) {
                target.setMediaType(MediaType.APPLICATION_JSON);
            }

            if (VARIANT_JSON.isCompatible(target) || VARIANT_JSON_ALL.isCompatible(target)) {
                JacksonRepresentation<Object> jacksonRepresentation = create(target.getMediaType(), source);
                result = jacksonRepresentation;
            }
        }

        return result;
    }

}