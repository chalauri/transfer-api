package ge.chalauri.transferapi.service;

import ge.chalauri.transferapi.entity.Account;
import ge.chalauri.transferapi.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService service;

    @Before
    public void init() {
        service = new AccountService(accountRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_should_throw_IllegalArgumentException_when_account_with_given_number_already_exists() {
        Account account = mockAccount();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account));

        service.create(account);
    }

    @Test
    public void test_create_should_call_repository_method_when_account_with_given_number_does_not_exist() {
        Account account = mockAccount();
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = service.create(account);

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
