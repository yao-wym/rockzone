package com.rock.common;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wym on 2014/10/29.
 */
public class JsonParse {
    /**
     * 解析网络数据
     * by wym
     * json
     * @return array
     */
    public static JSONObject parse_json(InputStream stream) throws IOException, JSONException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        while((count = stream.read(data,0,4096)) != -1)
            outStream.write(data, 0, count);

        data = null;
        String response = new String(outStream.toByteArray(),"ISO-8859-1");
        return new JSONObject(response);
    }
}
