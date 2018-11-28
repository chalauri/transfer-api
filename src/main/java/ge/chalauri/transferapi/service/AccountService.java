package ge.chalauri.transferapi.service;

import ge.chalauri.transferapi.entity.Account;
import ge.chalauri.transferapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account create(Account account) {

        Optional<Account> accountOptional = accountRepository.findByAccountNumber(account.getAccountNumber());

        if (accountOptional.isPresent()) {
            throw new IllegalArgumentException("Account with this account number already exists");
        }

        return accountRepository.save(account);
    }
}
