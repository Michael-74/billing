package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Mikrotik;

import java.util.List;
import java.util.Optional;

@Repository
public interface MikrotikRepository extends CrudRepository<Mikrotik, Integer> {

    List<Mikrotik> findAll();

    Optional<Mikrotik> findById(Integer id);
}
