package com.project;

import com.project.controller.AccountController;

import static spark.Spark.port;

public class AccountApp {

    /**
     * Entry point of the application
     *
     * @param args null
     */
    public static void main(String[] args) {
        port(8080); //start Spark server with specified port
        new AccountController(); //initialize controller layer
    }
}
