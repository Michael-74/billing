package ru.soyuz_kom.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Tasks;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Tasks, Integer> {

    @Cacheable("schedule")
    List<Tasks> findAll();

    Optional<Tasks> findById(Integer id);
}
