package com.project.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.project.dao.AccountDAO;
import com.project.domain.Account;
import com.project.exception.ExceptionAccountExists;
import com.project.exception.ExceptionAccountNotFound;
import com.project.exception.ExceptionInvalidInput;
import com.project.response.ResponseMessage;
import java.util.Optional;


/**
 * service layer is responsible for business logic
 */
public class AccountService {

    private static final Gson gson = new Gson();

    private AccountDAO accountDAO = AccountDAO.getAccountDao(); //initialize dependency

    private static AccountService accountService = new AccountService();//initialize dependency

    private AccountService() {
    }

    public static AccountService getAccountService() {
        return accountService;
    }

    /**
     * @param firstName first of an account entity
     * @return account entity or throw ExceptionAccountNotFound if entity not found
     */
    public Account getAccountByName(String firstName) {
        validateFirstName(firstName);
        Optional<Account> optionalAccount = Optional.ofNullable(accountDAO.findByName(firstName));
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }
        throw new ExceptionAccountNotFound(firstName);
    }

    /**
     * @param account entity
     * @return saved account or throw exception if an account with the same first name in DB
     */
    public Account saveAccount(Account account) {
        validateFirstName(account.getFirstName());
        validateLastName(account.getLastName());
        Optional<Account> optionalAccount = Optional.ofNullable(accountDAO.findByName(account.getFirstName()));
        if (optionalAccount.isPresent()) {
            throw new ExceptionAccountExists(account.getFirstName());
        }
        return accountDAO.save(account);
    }

    /**
     * update an account if it has correct properties and saved in DB
     *
     * @param firstName first name of an entity
     * @param lastName last name of an entity
     * @return an entity account with updated last name
     */
    public Account updateAccount(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        getAccountByName(firstName);
        return accountDAO.updateLastName(firstName, lastName);
    }

    /**
     * delete an account if it's in DB
     *
     * @param firstName first name of an account
     * @return message
     */
    public ResponseMessage deleteAccount(String firstName) {
        validateFirstName(firstName);
        getAccountByName(firstName);
        accountDAO.deleteAccount(firstName);
        return new ResponseMessage(String.format("Account with first name %s has been deleted", firstName));
    }

    /**
     * validate first name
     *
     * @param firstName first name of an accoutn
     */
    void validateFirstName(String firstName) {
        if (firstName == null) {
            throw new ExceptionInvalidInput("First name cannot be null");
        }
    }

    /**
     * validate last name
     *
     * @param lastName last name of an entity
     */
    void validateLastName(String lastName) {
        if (lastName == null) {
            throw new ExceptionInvalidInput("Last name cannot be null");
        }
    }

    /**
     * validate JSON
     *
     *
     * @param jsonInString JSON format of an entity
     * @return result of validation
     */
    public boolean isJSONValid(String jsonInString) {
        try {
            gson.fromJson(jsonInString, Account.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }
}
