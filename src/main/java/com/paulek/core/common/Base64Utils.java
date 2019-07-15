package com.paulek.core.common;

import com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64InputStream;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64OutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Base64Utils {

    public static String serialize(Object object) throws IOException {
        ByteArrayOutputStream byteaOut = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = null;
        try {
            gzipOut = new GZIPOutputStream(new Base64OutputStream(byteaOut));
            gzipOut.write(new Gson().toJson(object).getBytes(StandardCharsets.UTF_8));
        } finally {
            if (gzipOut != null) try { gzipOut.close(); } catch (IOException logOrIgnore) {}
        }
        return new String(byteaOut.toByteArray());
    }

    public static <T> T deserialize(String string, Type type) throws IOException {
        ByteArrayOutputStream byteaOut = new ByteArrayOutputStream();
        GZIPInputStream gzipIn = null;
        try {
            gzipIn = new GZIPInputStream(new Base64InputStream(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))));
            for (int data; (data = gzipIn.read()) > -1;) {
                byteaOut.write(data);
            }
        } finally {
            if (gzipIn != null) try { gzipIn.close(); } catch (IOException logOrIgnore) {}
        }
        return new Gson().fromJson(new String(byteaOut.toByteArray()), type);
    }

}
