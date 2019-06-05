package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;

/**
 * thrift序列化工具
 * 需要编写 .thrift IDL文件
 * 核心：TSerializer
 *       TDeserializer
 * new TBinaryProtocol.Factory()：二进制序列化协议
 * new TCompactProtocol.Factory(): 二进制带压缩序列化协议
 * new TJSONProtocol.Factory(): json数据格式序列化协议
 * 支持多种序列化协议
 *
 */
public class ThriftSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        if (!(obj instanceof TBase)) {
            throw new UnsupportedOperationException("not supported obj type");
        }
        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
        try {
            return serializer.serialize((TBase) obj);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            if (!TBase.class.isAssignableFrom(clazz)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            TBase o = (TBase) clazz.newInstance();
            TDeserializer tDeserializer = new TDeserializer();
            tDeserializer.deserialize(o,data);
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
