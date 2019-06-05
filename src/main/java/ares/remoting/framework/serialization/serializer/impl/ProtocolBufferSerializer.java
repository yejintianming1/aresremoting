package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * protobuf序列化工具
 * google跨平台，跨语言
 * 需要编写.proto IDL文件
 * 生成java代码命令 ./protoc --java_out=./ ./**.proto
 * 核心：利用
 *
 */
public class ProtocolBufferSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        if (!(obj instanceof GeneratedMessageV3) ) {
            throw new UnsupportedOperationException("not supported obj type");
        }
        try {
            return (byte[]) MethodUtils.invokeMethod(obj, "toByteArray");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            if (!GeneratedMessageV3.class.isAssignableFrom(clazz)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            Object o = MethodUtils.invokeStaticMethod(clazz, "getDefaultInstance");
            return (T) MethodUtils.invokeMethod(o, "parseFrom",new Object[]{data});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
