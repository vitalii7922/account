package com.project;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import com.project.controller.AccountController;
import com.project.domain.Account;
import com.project.service.AccountService;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import spark.servlet.SparkApplication;

import java.nio.charset.StandardCharsets;

//import org.junit.ClassRule;

@RunWith(MockitoJUnitRunner.class)
public class UnitTest {

    public static class BoardBoxControllerTestSparkApplication implements SparkApplication {

        @Override
        public void init() {
            new AccountController(new AccountService());
        }
    }

    @ClassRule
    public static final SparkServer<BoardBoxControllerTestSparkApplication> testServer =
            new SparkServer<>(BoardBoxControllerTestSparkApplication.class, 8181);

    @Test
    public void getResponseWithAccountJSON() throws HttpClientException {
        Account account = Account.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .build();

        GetMethod get = testServer.get("/api/account?firstName=Petr", false);
        HttpResponse httpResponse = testServer.execute(get);
        String result = new String(httpResponse.body(), StandardCharsets.UTF_8);
        String expected = "{\"firstName\":\"Petr\",\"lastName\":\"Petrovich\"}";
        Assert.assertEquals(expected, result);
    }

/*    @Test
    public void addNewAccount() throws HttpClientException {
        String entity = "{\"firstName\":\"Valerii\",\"lastName\":\"Valerianov\"}";
        PostMethod postMethod = testServer.post("/api/account", entity, false);
        HttpResponse httpResponse = testServer.execute(postMethod);
        String result = new String(httpResponse.body(), StandardCharsets.UTF_8);
        String expected = "{\"firstName\":\"Valerii\",\"lastName\":\"Valerianov\"}";
        Assert.assertEquals(expected, result);
    }*/




}
