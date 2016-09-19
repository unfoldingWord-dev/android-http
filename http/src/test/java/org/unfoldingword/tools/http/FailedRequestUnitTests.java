package org.unfoldingword.tools.http;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

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
public class FailedRequestUnitTests {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

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
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("my response")));

        GetRequest request = new GetRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/get"));
        String response = null;
        try {
            response = request.read();
        } catch (Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertEquals(response, null);
        assertNotNull(request.getResponseMessage());

        verify(getRequestedFor(urlMatching("/read/get")));
    }

    @Test
    public void downloadGET() throws Exception {
        String body = "my response";
        stubFor(get(urlEqualTo("/download/get"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        GetRequest request = new GetRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/get"));
        File dest = File.createTempFile("failed.download.get", "txt");
        try {
            request.download(dest);
        } catch(Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertNotEquals(readFileAsString(dest), body);
        assertNotNull(request.getResponseMessage());

        verify(getRequestedFor(urlMatching("/download/get")));
    }

    @Test
    public void readPOST() throws Exception {
        String body = "my post response";
        stubFor(post(urlEqualTo("/read/post"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "some data";
        PostRequest request = new PostRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/post"), data);
        String response = null;
        try {
            response = request.read();
        } catch (Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertEquals(response, null);
        assertNotNull(request.getResponseMessage());

        verify(postRequestedFor(urlMatching("/read/post")).withRequestBody(equalTo(data)));
    }

    @Test
    public void downloadPOST() throws Exception {
        String body = "my post response";
        stubFor(post(urlEqualTo("/download/post"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "some data";
        PostRequest request = new PostRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/post"), data);
        File dest = File.createTempFile("failed.download.post", "txt");
        try {
            request.download(dest);
        } catch(Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertNotEquals(readFileAsString(dest), body);
        assertNotNull(request.getResponseMessage());

        verify(postRequestedFor(urlMatching("/download/post")).withRequestBody(equalTo(data)));
    }

    @Test
    public void readDELETE() throws Exception {
        String body = "my delete response";
        stubFor(delete(urlEqualTo("/read/delete"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        DeleteRequest request = new DeleteRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/delete"));
        String response = null;
        try {
            response = request.read();
        } catch (Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertEquals(response, null);
        assertNotNull(request.getResponseMessage());

        verify(deleteRequestedFor(urlMatching("/read/delete")));
    }

    @Test
    public void downloadDELETE() throws Exception {
        String body = "my delete response";
        stubFor(delete(urlEqualTo("/download/delete"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        DeleteRequest request = new DeleteRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/delete"));
        File dest = File.createTempFile("failed.download.delete", "txt");
        try {
            request.download(dest);
        } catch(Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertNotEquals(readFileAsString(dest), body);
        assertNotNull(request.getResponseMessage());

        verify(deleteRequestedFor(urlMatching("/download/delete")));
    }

    @Test
    public void readPUT() throws Exception {
        String body = "my put response";
        stubFor(put(urlEqualTo("/read/put"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "my data";
        PutRequest request = new PutRequest(new URL("http://localhost:" + wireMockRule.port() + "/read/put"), data);
        String response = null;
        try {
            response = request.read();
        } catch (Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertEquals(response, null);
        assertNotNull(request.getResponseMessage());

        verify(putRequestedFor(urlMatching("/read/put")));
    }

    @Test
    public void downloadPUT() throws Exception {
        String body = "my put response";
        stubFor(put(urlEqualTo("/download/put"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)));

        String data = "my data";
        PutRequest request = new PutRequest(new URL("http://localhost:" + wireMockRule.port() + "/download/put"), data);
        File dest = File.createTempFile("failed.download.delete", "txt");
        try {
            request.download(dest);
        } catch(Exception e) {
            assertNotNull(e);
        }

        assertEquals(request.getResponseCode(), 400);
        assertNotEquals(readFileAsString(dest), body);
        assertNotNull(request.getResponseMessage());

        verify(putRequestedFor(urlMatching("/download/put")));
    }
}