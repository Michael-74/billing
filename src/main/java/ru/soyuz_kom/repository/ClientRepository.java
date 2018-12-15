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
    Iterable<Client> findAll();

    Iterable<Client> findAllByOrderByIdDesc();

    List<Client> findAll(Specification<Client> spec);

    Optional<Client> findById(Integer id);
}
