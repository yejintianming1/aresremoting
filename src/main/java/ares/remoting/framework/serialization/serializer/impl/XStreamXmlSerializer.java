package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XStream序列化XML格式工具
 * 核心：xStream
 */
public class XStreamXmlSerializer implements ISerializer {

    private static final XStream xStream = new XStream(new DomDriver());

    @Override
    public <T> byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String xml  = new String(data);
        return (T) xStream.fromXML(xml);
    }
}
