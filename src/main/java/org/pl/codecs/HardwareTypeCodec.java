package org.pl.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.pl.databaseModel.*;
import org.pl.model.Condition;

public class HardwareTypeCodec implements Codec<HardwareTypeMgd> {
    private Codec<Condition> conditionCodec;

    public HardwareTypeCodec(CodecRegistry registry) {
        this.conditionCodec = registry.get(Condition.class);
    }
    @Override
    public HardwareTypeMgd decode(BsonReader bsonReader, DecoderContext decoderContext) {
        HardwareTypeMgd hardwareTypeMgd = new ComputerMgd();
        bsonReader.readStartDocument();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = bsonReader.readName();
            if (fieldName.equals("_clazz")) {
                switch (bsonReader.readString()) {
                    case "computer":
                        hardwareTypeMgd = new ComputerMgd();
                        break;
                    case "console":
                        hardwareTypeMgd = new ConsoleMgd();
                        break;
                    case "phone":
                        hardwareTypeMgd = new PhoneMgd();
                        break;
                    case "monitor":
                        hardwareTypeMgd = new MonitorMgd();
                        break;
                    default:
                        return null;
                }
            } else if (fieldName.equals("condition")) {
                hardwareTypeMgd.setCondition(conditionCodec.decode(bsonReader, decoderContext));
            }
        }
        bsonReader.readEndDocument();
        return hardwareTypeMgd;
    }

    @Override
    public void encode(BsonWriter bsonWriter, HardwareTypeMgd hardwareTypeMgd, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeName("condition");
        conditionCodec.encode(bsonWriter, hardwareTypeMgd.getCondition(), encoderContext);
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<HardwareTypeMgd> getEncoderClass() {
        return HardwareTypeMgd.class;
    }
}
