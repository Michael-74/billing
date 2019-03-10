package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.LogActionUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogActionUserRepository extends CrudRepository<LogActionUser, Long> {

    List<LogActionUser> findAll();

    Optional<LogActionUser> findById(Long id);
}
