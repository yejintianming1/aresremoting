package ares.remoting.framework.serialization.common;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.IOException;
import java.util.Date;

/**
 * jackson日期格式序列化工具
 */
public class FDateJsonSerializer extends JsonSerializer<Date> {

    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Date date, com.fasterxml.jackson.core.JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
        jsonGenerator.writeString(date != null ? DATE_FORMAT.format(date) : "null");
    }
}
