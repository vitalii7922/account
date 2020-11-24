package com.project.controller;

import com.despegar.http.client.*;
import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import spark.servlet.SparkApplication;

import static junit.framework.TestCase.assertEquals;


/**
 * test controller layer checking routes
 *
 * use special spark server with the alternative port for testing
 */
public class AccountTestController {

    public static class BoardBoxControllerTestSparkApplication implements SparkApplication {

        @Override
        public void init() {
            new TestController();
        }

        @Override
        public void destroy() {
            System.out.println("Test application stopped");
        }
    }

    @ClassRule
    public static final SparkServer<BoardBoxControllerTestSparkApplication> testServer =
            new SparkServer<>(BoardBoxControllerTestSparkApplication.class, 8181);

    @Test
    public void testGetMethodExpected200() throws Exception {
        GetMethod get = testServer.get("/api/account?firstName=John", false);
        HttpResponse httpResponse = testServer.execute(get);
        assertEquals(200, httpResponse.code());
        assertEquals("{\"firstName\":John\",\"lastName\":\"Black\"}", new String(httpResponse.body()));
    }

    @Test
    public void testGetMethodExpected400() throws Exception {
        GetMethod get = testServer.get("/api/account", false);
        HttpResponse httpResponse = testServer.execute(get);
        assertEquals(400, httpResponse.code());
        assertEquals("First name cannot be null", new String(httpResponse.body()));
    }

    @Test
    public void testPutMethodExpected200() throws Exception {
        PutMethod put = testServer.put("/api/account?firstName=John&lastName=Smith", "{}", false);
        HttpResponse httpResponse = testServer.execute(put);
        assertEquals(200, httpResponse.code());
        assertEquals("{\"firstName\":John\",\"lastName\":\"Smith\"}", new String(httpResponse.body()));
    }

    @Test
    public void testPutMethodExpected400() throws Exception {
        PutMethod put = testServer.put("/api/account?lastName=Smith", "{}", false);
        HttpResponse httpResponse = testServer.execute(put);
        assertEquals(400, httpResponse.code());
        assertEquals("First name or last name null", new String(httpResponse.body()));
    }

    @Test
    public void testPostMethodExpected200() throws Exception {
        PostMethod post = testServer.post("/api/account",
                "{\"firstName\":\"John\",\"lastName\":\"Black\"}", false);
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(200, httpResponse.code());
        assertEquals("{\"firstName\":\"John\",\"lastName\":\"Black\"}", new String(httpResponse.body()));
    }

    @Test
    public void testPostMethodExpected400() throws Exception {
        PostMethod post = testServer.post("/api/account",
                "{\"firstName\":\"John\",:\"Black\"}", false);
        HttpResponse httpResponse = testServer.execute(post);
        assertEquals(400, httpResponse.code());
        assertEquals("Incorrect JSON format", new String(httpResponse.body()));
    }

    @Test
    public void testDeleteMethodExpected200() throws Exception {
        DeleteMethod delete = testServer.delete("/api/account?firstName=John", false);
        HttpResponse httpResponse = testServer.execute(delete);
        assertEquals(200, httpResponse.code());
        assertEquals("Account with name John deleted", new String(httpResponse.body()));
    }

    @Test
    public void testDeleteMethodExpected400() throws Exception {
        DeleteMethod delete = testServer.delete("/api/account?", false);
        HttpResponse httpResponse = testServer.execute(delete);
        assertEquals(400, httpResponse.code());
        assertEquals("First name cannot be null", new String(httpResponse.body()));
    }
}
