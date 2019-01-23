package ru.soyuz_kom.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Tv;

import java.util.List;
import java.util.Optional;

@Repository
public interface TvRepository extends CrudRepository<Tv, Integer>, JpaSpecificationExecutor<Tv> {
    List<Tv> findAll();

    Iterable<Tv> findAllByOrderByIdDesc();

    List<Tv> findAll(Specification<Tv> spec);

    Optional<Tv> findById(Integer id);

    Optional<Tv> findByName(String name);
}
