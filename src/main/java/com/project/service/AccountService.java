package com.project.service;
import com.google.gson.Gson;
import com.project.dao.AccountDAO;
import com.project.domain.Account;
import com.project.exception.ExceptionAccountExists;
import com.project.exception.ExceptionAccountNotFound;
import com.project.exception.ExceptionInvalidInput;
import com.project.response.ResponseMessage;
import java.util.Optional;


public class AccountService {
    private static final Gson gson = new Gson();
    private final AccountDAO accountDAO = AccountDAO.getAccountDao();

    public Account getAccountByName(String firstName) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountDAO.findByName(firstName));
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }
        throw new ExceptionAccountNotFound(firstName);
    }

    public Account saveAccount(Account account) {
        validateFirstName(account.getFirstName());
        validateLastName(account.getLastName());
        Optional<Account> optionalAccount = Optional.ofNullable(accountDAO.findByName(account.getFirstName()));
        if (optionalAccount.isPresent()) {
            throw new ExceptionAccountExists(account.getFirstName());
        }
        return accountDAO.save(account);
    }

    public Account updateAccount(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        getAccountByName(firstName);
        return accountDAO.updateLastName(firstName, lastName);
    }

    public ResponseMessage deleteAccount(String firstName) {
        if (firstName == null) {
            throw new ExceptionInvalidInput("First name cannot be null");
        }
        getAccountByName(firstName);
        accountDAO.deleteAccount(firstName);
        return new ResponseMessage(String.format("Account with first name %s has been deleted", firstName));
    }

    private void validateFirstName(String firstName) {
        if (firstName == null) {
            throw new ExceptionInvalidInput("First name cannot be null");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null) {
            throw new ExceptionInvalidInput("Last name cannot be null");
        }
    }

    public boolean isJSONValid(String jsonInString) {
        try {
            gson.fromJson(jsonInString, Account.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }
}
