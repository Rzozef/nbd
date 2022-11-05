package org.pl.codecs;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.pl.databaseModel.HardwareTypeMgd;

public class HardwareTypeCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == HardwareTypeMgd.class) {
            return (Codec<T>) new HardwareTypeCodec(codecRegistry);
        }
        return null;
    }
}
