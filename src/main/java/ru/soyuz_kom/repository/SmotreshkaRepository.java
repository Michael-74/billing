package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Smotreshka;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmotreshkaRepository extends CrudRepository<Smotreshka, Integer> {

    List<Smotreshka> findAll();

    Optional<Smotreshka> findById(Integer id);
}
