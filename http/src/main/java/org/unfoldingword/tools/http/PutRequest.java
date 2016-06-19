package org.unfoldingword.tools.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joel on 6/18/16.
 */
public class PutRequest extends Request {
    private final String data;

    private PutRequest(URL url, String data) {
        super(url, "PUT");
        this.data = data;
    }

    /**
     * Creates a new put request
     * @param uri the url receiving the put request
     * @param data the put data
     * @return a new put request
     * @throws MalformedURLException
     */
    public static PutRequest newInstance(String uri, String data) throws MalformedURLException {
        URL url = new URL(uri);
        return new PutRequest(url, data);
    }

    @Override
    protected String onSubmit(HttpURLConnection conn) throws IOException {
        sendData(data);
        return readResponse();
    }
}
