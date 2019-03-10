package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.LogSmotreshka;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogSmotreshkaRepository extends CrudRepository<LogSmotreshka, Long> {

    List<LogSmotreshka> findAll();

    Optional<LogSmotreshka> findById(Long id);
}
