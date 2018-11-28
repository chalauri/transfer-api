package ge.chalauri.transferapi.repository;

import ge.chalauri.transferapi.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

}
