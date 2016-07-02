package org.bakku.yodatalks.network.request;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by christian on 20/06/16.
 */
public class Request {
    private HttpURLConnection connection;

    public Request(String stringUrl) throws RequestException {
        try {
            URL url = new URL(stringUrl);
            connection = (HttpURLConnection) url.openConnection();
        }
        catch (IOException e) {
            connection.disconnect();

            throw new RequestException(e);
        }
    }

    public Request header(String field, String value) {
        connection.addRequestProperty(field, value);
        return this;
    }

    public String execute() throws RequestException {
        try {
            InputStream is = connection.getInputStream();

            String response = IOUtils.toString(is);

            connection.disconnect();

            return response;
        }
        catch (IOException e) {
            connection.disconnect();

            throw new RequestException(e);
        }
    }


}
