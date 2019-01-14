package ru.soyuz_kom.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Rent;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends CrudRepository<Rent, Integer>, JpaSpecificationExecutor<Rent> {
    List<Rent> findAll();

    Iterable<Rent> findAllByOrderByIdDesc();

    List<Rent> findAll(Specification<Rent> spec);

    Optional<Rent> findById(Integer id);
}
