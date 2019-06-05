package ares.remoting.framework.serialization.engine;

import ares.remoting.framework.serialization.common.SerializeType;
import ares.remoting.framework.serialization.serializer.ISerializer;
import ares.remoting.framework.serialization.serializer.impl.*;
import avro.shaded.com.google.common.collect.Maps;

import java.util.Map;

public class SerializerEngine {

    public static final Map<SerializeType, ISerializer> serializerMap = Maps.newConcurrentMap();

    static {
        serializerMap.put(SerializeType.DefaultJavaSerializer, new DefaultJavaSerializer());
        serializerMap.put(SerializeType.HessianSerializer, new HessianSerializer());
        serializerMap.put(SerializeType.JacksonJsonSerializer, new JacksonJsonSerializer());
        serializerMap.put(SerializeType.FastJsonSerializer, new FastJsonSerializer());
        serializerMap.put(SerializeType.GsonJsonSerializer, new GsonJsonSerializer());
        serializerMap.put(SerializeType.ProtoStuffSerializer, new ProtocolBufferSerializer());
        serializerMap.put(SerializeType.MarshallingSerializer, new MarshallingSerializer());

        //以下三类不能使用普通的java bean
        serializerMap.put(SerializeType.AvroSerializer, new AvroSerializer());
        serializerMap.put(SerializeType.ThriftSerializer, new ThriftSerializer());
        serializerMap.put(SerializeType.ProtocolBufferSerializer, new ProtocolBufferSerializer());
    }

    public static <T> byte[] serialize(T obj, String serializeType) {
        SerializeType serialize = SerializeType.queryByType(serializeType);
        if (serialize == null) {
            throw new RuntimeException("serialize is null");
        }
        ISerializer serializer = serializerMap.get(serialize);
        if (serialize == null) {
            throw new RuntimeException("serialize error");
        }
        return serializer.serialize(obj);
    }

    public static <T> T deserialize(byte[] data, Class<T> clazz, String serializeType) {
        SerializeType serialize = SerializeType.queryByType(serializeType);
        if (serialize == null) {
            throw new RuntimeException("serialize is null");
        }
        ISerializer serializer = serializerMap.get(serialize);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }
        return serializer.deserialize(data, clazz);
    }

    public static <T> byte[] serialize(T obj, SerializeType serializeType) {
        ISerializer serializer = serializerMap.get(serializeType);
        return serializer.serialize(obj);
    }

    public static <T> T deserialize(byte[] data, Class<T> clazz, SerializeType serializeType) {
        ISerializer serializer = serializerMap.get(serializeType);
        return  serializer.deserialize(data,clazz);
    }
}
