package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.jboss.marshalling.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * jboss marshalling序列化工具
 * 核心：Marshaller
 *
 */
public class MarshallingSerializer implements ISerializer {

    static final MarshallingConfiguration configuration = new MarshallingConfiguration();
    //获取序列化工厂对象，参数serial标识创建的是java序列化工厂对象
    static final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

    static {
        configuration.setVersion(5);
    }
    @Override
    public <T> byte[] serialize(T obj) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
            marshaller.start(Marshalling.createByteOutput(byteArrayOutputStream));
            marshaller.writeObject(obj);
            marshaller.finish();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
            unmarshaller.start(Marshalling.createByteInput(byteArrayInputStream));
            Object o = unmarshaller.readObject();
            unmarshaller.finish();
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
