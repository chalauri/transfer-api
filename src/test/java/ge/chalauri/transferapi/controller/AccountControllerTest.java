package ge.chalauri.transferapi.controller;

import ge.chalauri.transferapi.entity.Account;
import ge.chalauri.transferapi.repository.AccountRepository;
import ge.chalauri.transferapi.service.AccountService;
import ge.chalauri.transferapi.service.AccountServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private AccountController controller;

    @Before
    public void init() {
        this.accountService = new AccountService(accountRepository);
        this.controller = new AccountController(accountService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_should_throw_IllegalArgumentException_when_account_with_given_number_already_exists() {
        Account account = mockAccount();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        controller.create(account);
    }

    @Test
    public void test_create_should_call_repository_method_when_account_with_given_number_does_not_exist() {
        Account account = mockAccount();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = controller.create(account);

        assertNotNull(result);
        verify(accountRepository, times(1)).save(account);
    }


    private Account mockAccount() {
        Account account = new Account();
        account.setAccountNumber("acc0123");
        account.setBalance(0.0D);
        account.setOwnerName("Giga");
        account.setOwnerSurname("Chalauri");
        account.setOwnerPersonalId("123456789");

        return account;
    }
}
