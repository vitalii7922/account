package com.project;

import com.project.controller.AccountController;
import com.project.service.AccountService;

import static spark.Spark.port;

public class AccountApp {

    public static void main(String[] args) {
        port(8080);
        new AccountController(new AccountService());
    }
}
