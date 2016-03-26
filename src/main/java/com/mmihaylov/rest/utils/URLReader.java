package com.mmihaylov.rest.utils;

import com.mmihaylov.rest.RestServicesException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {

    public InputStream readURL(String urlAddress) throws RestServicesException {
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            InputStream input = urlConnection.getInputStream();
            return input;
        } catch (MalformedURLException me) {
            throw new RestServicesException("Invalid URL address: " + urlAddress, me);
        } catch (IOException ioe) {
            throw new RestServicesException("Invalid open connection with URL address: " + urlAddress, ioe);
        }
    }

    public String readURLAsString(String urlAddress) throws RestServicesException {
        try {
            InputStream stream = readURL(urlAddress);
            byte[] bytes = IOUtils.getBytes(stream);
            return new String(bytes);
        } catch (IOException ioe) {
            throw new RestServicesException(ioe);
        }
    }
}