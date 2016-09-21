package org.unfoldingword.tools.http;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RequestUnitTests {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());
    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    private static String readStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private String readFileAsString(File f) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            String contents = readStreamToString(fis);
            fis.close();
            return contents;
        } finally {
            if(fis != null) {
                fis.close();
            }
        }
    }

    @Test
    public void readGET() throws Exception {
        stubFor(get(urlEqualTo("/read/get"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/plain")
                .withBody("my response")));

        GetRequest request = new GetRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/get"));
        String response = request.read();

        assertEquals(request.getResponseCode(), 200);
        assertEquals(response, "my response");
        assertNotNull(request.getResponseMessage());

        verify(getRequestedFor(urlMatching("/read/get")));
    }

    @Test
    public void downloadGET() throws Exception {
        String body = "my response";
        stubFor(get(urlEqualTo("/download/get"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        GetRequest request = new GetRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/get"));
        File dest = new File(tempDir.getRoot(), "download.get.txt");
        request.download(dest);

        assertTrue(dest.exists());
        assertEquals(request.getResponseCode(), 200);
        assertEquals(readFileAsString(dest).trim(), body);
        assertNotNull(request.getResponseMessage());

        verify(getRequestedFor(urlMatching("/download/get")));
    }

    @Test
    public void readPOST() throws Exception {
        String body = "my post response";
        stubFor(post(urlEqualTo("/read/post"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "some data";
        PostRequest request = new PostRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/post"), data);
        String response = request.read();

        assertEquals(request.getResponseCode(), 200);
        assertEquals(response, body);
        assertNotNull(request.getResponseMessage());

        verify(postRequestedFor(urlMatching("/read/post")).withRequestBody(equalTo(data)));
    }

    @Test
    public void downloadPOST() throws Exception {
        String body = "my post response";
        stubFor(post(urlEqualTo("/download/post"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "some data";
        PostRequest request = new PostRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/post"), data);
        File dest = new File(tempDir.getRoot(), "download.post.txt");
        request.download(dest);

        assertTrue(dest.exists());
        assertEquals(request.getResponseCode(), 200);
        assertEquals(readFileAsString(dest).trim(), body);
        assertNotNull(request.getResponseMessage());

        verify(postRequestedFor(urlMatching("/download/post")).withRequestBody(equalTo(data)));
    }

    @Test
    public void readDELETE() throws Exception {
        String body = "my delete response";
        stubFor(delete(urlEqualTo("/read/delete"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        DeleteRequest request = new DeleteRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/delete"));
        String response = request.read();

        assertEquals(request.getResponseCode(), 200);
        assertEquals(response, body);
        assertNotNull(request.getResponseMessage());

        verify(deleteRequestedFor(urlMatching("/read/delete")));
    }

    @Test
    public void downloadDELETE() throws Exception {
        String body = "my delete response";
        stubFor(delete(urlEqualTo("/download/delete"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        DeleteRequest request = new DeleteRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/delete"));
        File dest = new File(tempDir.getRoot(), "download.delete.txt");
        request.download(dest);

        assertTrue(dest.exists());
        assertEquals(request.getResponseCode(), 200);
        assertEquals(readFileAsString(dest).trim(), body);
        assertNotNull(request.getResponseMessage());

        verify(deleteRequestedFor(urlMatching("/download/delete")));
    }

    @Test
    public void readPUT() throws Exception {
        String body = "my put response";
        stubFor(put(urlEqualTo("/read/put"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "my data";
        PutRequest request = new PutRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/put"), data);
        String response = request.read();

        assertEquals(request.getResponseCode(), 200);
        assertEquals(response, body);
        assertNotNull(request.getResponseMessage());

        verify(putRequestedFor(urlMatching("/read/put")));
    }

    @Test
    public void downloadPUT() throws Exception {
        String body = "my put response";
        stubFor(put(urlEqualTo("/download/put"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "my data";
        PutRequest request = new PutRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/put"), data);
        File dest = new File(tempDir.getRoot(), "download.delete.txt");
        request.download(dest);

        assertTrue(dest.exists());
        assertEquals(request.getResponseCode(), 200);
        assertEquals(readFileAsString(dest).trim(), body);
        assertNotNull(request.getResponseMessage());

        verify(putRequestedFor(urlMatching("/download/put")));
    }
}