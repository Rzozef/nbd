package org.pl.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.pl.model.Condition;

import java.lang.reflect.Method;

public class ConditionCodec implements Codec<Condition> {

    @Override
    public Condition decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Condition.fromValue(bsonReader.readString());
    }

    @Override
    public void encode(BsonWriter bsonWriter, Condition condition, EncoderContext encoderContext) {
        bsonWriter.writeString(condition.name());
    }

    @Override
    public Class<Condition> getEncoderClass() {
        return Condition.class;
    }
}
