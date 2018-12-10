package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Preset;

import java.util.Optional;

@Repository
public interface PresetRepository extends CrudRepository<Preset, Integer> {
    Iterable<Preset> findAll();

    Iterable<Preset> findByUrl(String url);

    Optional<Preset> findById(Integer id);
}
