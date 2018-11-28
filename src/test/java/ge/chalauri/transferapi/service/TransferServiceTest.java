package ge.chalauri.transferapi.service;

import ge.chalauri.transferapi.entity.Transfer;
import ge.chalauri.transferapi.entity.Account;
import ge.chalauri.transferapi.repository.AccountRepository;
import ge.chalauri.transferapi.repository.TransferRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferRepository transferRepository;

    private TransferService transferService;

    @Before
    public void init() {
        transferService = new TransferService(transferRepository, accountRepository);
    }

    @Test
    public void test_makeTransfer_should_return_transfer_entity_and_vall_repository_method_when_validation_passes() {
        Account sourceAccount = mockAccount("src123", 100D, "0000");
        Account destinationAccount = mockAccount("dest111", 0D, "0000");
        Transfer transfer = mockTransfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber());

        when(accountRepository.findByAccountNumber(sourceAccount.getAccountNumber())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByAccountNumber(destinationAccount.getAccountNumber())).thenReturn(Optional.of(destinationAccount));
        when(transferRepository.save(transfer)).thenReturn(transfer);

        Transfer result = transferService.makeTransfer(transfer);

        assertNotNull(result);
        verify(transferRepository, times(1)).save(transfer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_makeTransfer_throw_IllegalArgumentException_when_source_account_is_not_found() {
        Account sourceAccount = mockAccount("src123", 100D, "0000");
        Account destinationAccount = mockAccount("dest111", 0D, "0000");
        Transfer transfer = mockTransfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber());

        when(accountRepository.findByAccountNumber(sourceAccount.getAccountNumber())).thenReturn(Optional.empty());

        transferService.makeTransfer(transfer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_makeTransfer_throw_IllegalArgumentException_when_destination_account_is_not_found() {
        Account sourceAccount = mockAccount("src123", 100D, "0000");
        Account destinationAccount = mockAccount("dest111", 0D, "0000");
        Transfer transfer = mockTransfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber());

        when(accountRepository.findByAccountNumber(sourceAccount.getAccountNumber())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByAccountNumber(destinationAccount.getAccountNumber())).thenReturn(Optional.empty());

        transferService.makeTransfer(transfer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_makeTransfer_throw_IllegalArgumentException_when_transfer_amount_is_more_than_amount_on_source_account() {
        Account sourceAccount = mockAccount("src123", 0D, "0000");
        Account destinationAccount = mockAccount("dest111", 0D, "0000");
        Transfer transfer = mockTransfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber());

        when(accountRepository.findByAccountNumber(sourceAccount.getAccountNumber())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findByAccountNumber(destinationAccount.getAccountNumber())).thenReturn(Optional.of(destinationAccount));

        transferService.makeTransfer(transfer);
    }

    private Transfer mockTransfer(String source, String destionation) {
        Transfer transfer = new Transfer();
        transfer.setAmount(100.0D);
        transfer.setSourceAccountNumber(source);
        transfer.setDestinationAccountNumber(destionation);
        transfer.setDescription("Monthly payment");

        return transfer;
    }

    private Account mockAccount(String accountNumber, Double balance, String personalId) {
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setOwnerName("Test");
        account.setOwnerSurname("Test");
        account.setOwnerPersonalId(personalId);

        return account;
    }

}
