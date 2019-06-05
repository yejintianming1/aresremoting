package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * java自带的XMl序列化工具
 * 核心：XMLEncoder
 *       XMLDecoder
 */
public class DefaultJavaXmlSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XMLEncoder xmlEncoder = new XMLEncoder(byteArrayOutputStream);
        xmlEncoder.writeObject(obj);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        XMLDecoder xmlDecoder = new XMLDecoder(new ByteArrayInputStream(data));
        return (T)xmlDecoder.readObject();
    }
}
