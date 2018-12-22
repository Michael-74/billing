package ru.soyuz_kom.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Schedule;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {

    @Cacheable("schedule")
    List<Schedule> findAll();

    Optional<Schedule> findById(Integer id);
}
