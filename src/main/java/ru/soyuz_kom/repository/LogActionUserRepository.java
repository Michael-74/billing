package ru.soyuz_kom.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.LogActionUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogActionUserRepository extends CrudRepository<LogActionUser, Long>, JpaSpecificationExecutor<LogActionUser> {

    List<LogActionUser> findAll(Specification<LogActionUser> spec);

    Optional<LogActionUser> findById(Long id);
}
