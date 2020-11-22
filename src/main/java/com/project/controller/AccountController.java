package com.project.controller;

import com.google.gson.Gson;
import com.project.domain.Account;
import com.project.exception.ExceptionAccountExists;
import com.project.exception.ExceptionAccountNotFound;
import com.project.exception.ExceptionInvalidInput;
import com.project.response.ResponseMessage;
import com.project.service.AccountService;

import static spark.Spark.*;

public class AccountController {

    private static final String ENDPOINT = "/api/account";

    public AccountController(final AccountService accountService) {
        post(ENDPOINT, (request, response) -> {
            if (!accountService.isJSONValid(request.body())) {
                throw new ExceptionInvalidInput("Invalid JSON format");
            }
            return new Gson()
                    .toJson(accountService.saveAccount(new Gson().fromJson(request.body(), Account.class)));
        });

        get(ENDPOINT, (request, response) -> new Gson()
                .toJson(accountService.getAccountByName(request.queryParams("firstName"))));

        put(ENDPOINT, (request, response) -> new Gson()
                .toJson(accountService.updateAccount(request.queryParams("firstName"), request.queryParams("lastName"))));

        delete(ENDPOINT, (request, response) -> new Gson()
                .toJson(accountService.deleteAccount(request.queryParams("firstName"))));

        exception(ExceptionAccountNotFound.class, (e, request, response) -> {
            response.status(404);
            response.body(new Gson().toJson(new ResponseMessage(e.getMessage())));
        });

        exception(ExceptionAccountExists.class, (e, request, response) -> {
            response.status(400);
            response.body(new Gson().toJson(new ResponseMessage(e.getMessage())));
        });

        exception(ExceptionInvalidInput.class, (e, request, response) -> {
            response.status(400);
            response.body(new Gson().toJson(new ResponseMessage(e.getMessage())));
        });
    }
}
