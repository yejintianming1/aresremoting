package ares.remoting.framework.serialization.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 序列化工具类型
 */
public enum SerializeType {

    DefaultJavaSerializer("DefaultJavaSerializer"),
    HessianSerializer("HessianSerializer"),
    JacksonJsonSerializer("JacksonJsonSerializer"),
    FastJsonSerializer("FastJsonSerializer"),
    GsonJsonSerializer("GsonJsonSerializer"),
    ProtoStuffSerializer("ProtoStuffSerializer"),
    DefaultJavaXmlSerializer("DefaultJavaXmlSerializer"),
    XStreamXmlSerializer("XStreamXmlSerializer"),
    MarshallingSerializer("MarshallingSerializer"),

    /**下面者三种需要编写 IDL文件*/
    AvroSerializer("AvroSerializer"),
    ProtocolBufferSerializer("ProtocolBufferSerializer"),
    ThriftSerializer("ThriftSerializer");


    private String serializeType;

    SerializeType(String serializeType) {
        this.serializeType = serializeType;
    }

    public static SerializeType queryByType(String serializeType) {
        if (StringUtils.isBlank(serializeType)) {
            return null;
        }
        for (SerializeType serialize: SerializeType.values()) {
            if (StringUtils.equals(serializeType, serialize.getSerializeType())) {
                return serialize;
            }
        }
        return null;
    }

    public String getSerializeType() {
        return serializeType;
    }
}
