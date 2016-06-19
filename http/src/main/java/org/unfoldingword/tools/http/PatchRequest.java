package org.unfoldingword.tools.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joel on 6/18/16.
 */
public class PatchRequest extends Request {
    private final String data;

    private PatchRequest(URL url, String data) {
        super(url, "PATCH");
        this.data = data;
    }

    /**
     * Creates a new patch request
     * @param uri the url receiving the patch request
     * @param data the patch data
     * @return a new patch request
     * @throws MalformedURLException
     */
    public static PatchRequest newInstance(String uri, String data) throws MalformedURLException {
        URL url = new URL(uri);
        return new PatchRequest(url, data);
    }

    @Override
    protected String onSubmit(HttpURLConnection conn) throws IOException {
        sendData(data);
        return readResponse();
    }
}
