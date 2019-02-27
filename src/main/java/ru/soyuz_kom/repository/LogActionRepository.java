package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.LogAction;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogActionRepository extends CrudRepository<LogAction, Long> {

    List<LogAction> findAll();

    Optional<LogAction> findById(Long id);
}
