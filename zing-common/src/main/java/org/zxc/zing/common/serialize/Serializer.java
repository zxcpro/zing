package org.zxc.zing.common.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by xuanchen.zhao on 15-12-13.
 */
public class Serializer {

    private static final Logger log = LoggerFactory.getLogger(Serializer.class);


    public static byte[] serialize(Object obj) throws IOException {
        log.info("serializing:"+obj);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(obj);
        log.info("serialize object written");
        out.close();
        log.info("serialize result:"+bos.toByteArray());
        return bos.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        Hessian2Input in = new Hessian2Input(bis);
        return in.readObject();
    }
}
