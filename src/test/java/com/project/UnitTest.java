package com.project;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import com.project.controller.AccountController;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import spark.servlet.SparkApplication;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
public class UnitTest {

    public static class BoardBoxControllerTestSparkApplication implements SparkApplication {

        @Override
        public void init() {
            new AccountController();
        }
    }

    @ClassRule
    public static final SparkServer<BoardBoxControllerTestSparkApplication> testServer =
            new SparkServer<>(BoardBoxControllerTestSparkApplication.class, 8181);

    @Test
    public void getResponseWithAccountJSON() throws HttpClientException {


        GetMethod get = testServer.get("/api/account?firstName=Petr", false);
        HttpResponse httpResponse = testServer.execute(get);
        String result = new String(httpResponse.body(), StandardCharsets.UTF_8);
        String expected = "{\"firstName\":\"Petr\",\"lastName\":\"Petrovich\"}";
        Assert.assertEquals(expected, result);
    }
}
