package com.project.controller;

import com.project.exception.ExceptionAccountExists;
import com.project.exception.ExceptionAccountNotFound;
import com.project.service.AccountService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

/**
 * The class that defines a Spark Web Framework route
 */
class TestController {

    private AccountService accountService = AccountService.getAccountService();

    TestController() {
        get("/api/account", this::testGetMethod);
        put("/api/account", this::testPutMethod);
        post("/api/account", this::testPostMethod);
        delete("/api/account", this::testDeleteMethod);
    }



    private String testGetMethod(Request request, Response response) {
        if (request.queryParams("firstName") != null) {
            response.status(200);
            return "{\"firstName\":John\",\"lastName\":\"Black\"}";
        }
        response.status(400);
        return "First name cannot be null";
    }

    private String testPutMethod(Request request, Response response) {
        if (request.queryParams("firstName") == null || request.queryParams("lastName") == null) {
            response.status(400);
            return "First name or last name null";
        }
        response.status(200);
        return String.format("{\"firstName\":%s\",\"lastName\":\"%s\"}", request.queryParams("firstName"),
                request.queryParams("lastName"));
    }

    private String testPostMethod(Request request, Response response) {
        System.out.println(request.body());
        if (accountService.isJSONValid(request.body())) {
            response.status(200);
            return request.body();
        }
        response.status(400);
        return "Incorrect JSON format";
    }

    private String testDeleteMethod(Request request, Response response) {
        if (request.queryParams("firstName") != null) {
            response.status(200);
            return String.format("Account with name %s deleted", request.queryParams("firstName"));
        }
        response.status(400);
        return "First name cannot be null";
    }
}
