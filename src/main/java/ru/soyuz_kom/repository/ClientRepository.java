package ru.soyuz_kom.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>, JpaSpecificationExecutor<Client> {
    List<Client> findAll();

    Iterable<Client> findAllByOrderByIdDesc();

    List<Client> findAll(Specification<Client> spec);

    Optional<Client> findById(Integer id);

    Optional<Client> findByContract(String field);

    Optional<Client> findByLogin(String field);

    Optional<Client> findByIp(String field);
}
