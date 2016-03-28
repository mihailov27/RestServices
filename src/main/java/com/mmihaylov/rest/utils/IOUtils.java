package com.mmihaylov.rest.utils;

import java.io.*;

public class IOUtils {

    public static byte[] getBytes(InputStream stream) throws IOException {
        byte[] bytes = new byte[50];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nRead;
        try {
            while ((nRead = stream.read(bytes, 0, bytes.length)) != -1) {
                baos.write(bytes, 0, nRead);
            }
            baos.flush();
        } finally {
            close(stream);
            close(baos);
        }
        return baos.toByteArray();
    }

    private static void close(Closeable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (IOException e) {
            // log the error
        }
    }
}
