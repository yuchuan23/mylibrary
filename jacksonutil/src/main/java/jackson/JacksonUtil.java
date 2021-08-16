package mylib.jackson;

import mylib.jackson.exception.JacksonExceptions.JacksonException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import org.apache.commons.lang3.StringUtils;

public class JacksonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    public static final ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static <T> T jsonToObject(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        if (cls.isInstance("")) {
            return (T) json;
        }

        try {
            return MAPPER.readValue(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JacksonException("cannot convert to object");
        }
    }

    public static <T> T jsonToObject(String json, TypeReference typeReference) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return (T) MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JacksonException("cannot convert to object");
        }
    }

    public static String objectToJson(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JacksonException("cannot convert to json string");
        }
    }
}

