package ares.remoting.framework.serialization.serializer;

/**
 * 定义统一的序列化和反序列化接口
 */
public interface ISerializer {

    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data,Class<T> clazz);
}
