package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import ares.remoting.test.UserTest;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * kryo进行序列化和反序列化，不是跨语言的，同时很少用于多个系统直接的数据交换
 */
public class KryoSerializer implements ISerializer {

    //由于kryo不是线程安全的，所以每一个线程都使用独立的kryo
    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            /**
             * 不要轻易改变这里的配置！更改之后,序列化的格式就会发生变化，
             */
            //支持对象循环引用（否则会栈溢出）
            kryo.setReferences(true);//默认值就是true,添加此行目的时为了提醒
            //不请之要求注册类（注册行为不发保证多个jvm内同一个类的注册编号相同；而且业务系统中大量的Class也难以一一注册）
            kryo.setRegistrationRequired(false);

            //fix the NPE bug when deserializing Collecionts.
            ((Kryo.DefaultInstantiatorStrategy)kryo.getInstantiatorStrategy())
                    .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
         return kryo;
        }
    };

    /**
     * 获取当前线程的Kryo实例
     * @return
     */
    public static Kryo getInstance() {
        return kryoLocal.get();
    }

    /**
     * 将对象【及类型】序列化为字节数组
     * @param obj
     * @param <T>
     * @return
     */
    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);

        Kryo kryo = getInstance();
        kryo.writeClassAndObject(output, obj);
        output.flush();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 将字节数组反序列化为原对象；其实此处不用指定对象类型，因为字节数组中存有对象类型信息，不过为了引擎的一执行，此处依然传入了类型参数
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        Input input = new Input(byteArrayInputStream);

        Kryo kryo = getInstance();
        return (T) kryo.readClassAndObject(input);
    }

    public static void main(String[] args) {
        UserTest userTest = new UserTest();
        userTest.setName("wuyangsheng");
        userTest.setSex("男");

        ISerializer serializer = new KryoSerializer();
        byte[] userBytes = serializer.serialize(userTest);
        UserTest test = serializer.deserialize(userBytes, UserTest.class);
        System.out.println(test);
    }
}
