package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import com.google.gson.Gson;

/**
 * gson序列化json格式工具
 * 核心：Gson
 */
public class GsonJsonSerializer implements ISerializer {

    private static final Gson gson = new Gson();

    @Override
    public <T> byte[] serialize(T obj) {
        return gson.toJson(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return gson.fromJson(new String(data),clazz);
    }
}
