package ge.chalauri.transferapi.repository;

import ge.chalauri.transferapi.entity.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
