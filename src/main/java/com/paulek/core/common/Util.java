package com.paulek.core.common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

    public static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readWebsite(String url) {

        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();

            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.addRequestProperty("User-Agent", "clCore");

            httpURLConnection.setConnectTimeout(5000);

            httpURLConnection.setDoOutput(true);

            String line;
            StringBuilder output = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            while ((line = in.readLine()) != null)
                output.append(line);
            in.close();

            return output.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
    }

}
