package ge.chalauri.transferapi.service;

import ge.chalauri.transferapi.entity.Transfer;
import ge.chalauri.transferapi.entity.Account;
import ge.chalauri.transferapi.repository.AccountRepository;
import ge.chalauri.transferapi.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService {

    private TransferRepository transferRepository;
    private AccountRepository accountRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public Transfer makeTransfer(Transfer transfer) {

        Optional<Account> sourceAccountNumberOptional =
                accountRepository.findByAccountNumber(transfer.getSourceAccountNumber());
        Optional<Account> destinationAccountNumberOptional =
                accountRepository.findByAccountNumber(transfer.getDestinationAccountNumber());

        validateAccounts(sourceAccountNumberOptional, destinationAccountNumberOptional);

        updateAccounts(transfer, sourceAccountNumberOptional, destinationAccountNumberOptional);

        return transferRepository.save(transfer);
    }

    private void updateAccounts(Transfer transfer, Optional<Account> sourceAccountNumberOptional,
                                Optional<Account> destinationAccountNumberOptional) {

        Account sourceAccount = sourceAccountNumberOptional.get();
        Account destinationAccount = destinationAccountNumberOptional.get();
        Double transferAmount = transfer.getAmount();

        verifyBalance(sourceAccount, transferAmount);

        sourceAccount.setBalance(sourceAccount.getBalance() - transferAmount);
        destinationAccount.setBalance(destinationAccount.getBalance() + transferAmount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }

    private void verifyBalance(Account sourceAccount, Double transferAmount) {
        if (sourceAccount.getBalance() < transferAmount) {
            throw new IllegalArgumentException("Transfer amount is more than balance on source account");
        }
    }

    private void validateAccounts(Optional<Account> sourceAccountNumberOptional, Optional<Account> destinationAccountNumberOptional) {
        if (!sourceAccountNumberOptional.isPresent()) {
            throw new IllegalArgumentException("Source Account Number is not valid");
        }

        if (!destinationAccountNumberOptional.isPresent()) {
            throw new IllegalArgumentException("Source Account Number is not valid");
        }
    }
}
