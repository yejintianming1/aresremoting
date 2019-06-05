package ares.remoting.framework.serialization.serializer.impl;

import ares.remoting.framework.serialization.serializer.ISerializer;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Avro序列化工具
 * 需要编写 .avdl IDL文件
 * 同时需要编写 .avsc的Schema
 * 核心：SpecificDatumWriter
 *
 */
public class AvroSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof SpecificRecordBase)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            DatumWriter userDatumWriter = new SpecificDatumWriter(obj.getClass());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);
            userDatumWriter.write(obj, binaryEncoder);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        if (!SpecificRecordBase.class.isAssignableFrom(clazz)) {
            throw new UnsupportedOperationException("not supported obj type");
        }
        DatumReader userDatumReader = new SpecificDatumReader(clazz);
        BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(data), null);
        try {
            return (T) userDatumReader.read(clazz.newInstance(), binaryDecoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
