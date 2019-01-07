package ru.soyuz_kom.repository;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.view.Views;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternetRepository extends CrudRepository<Internet, Integer>, JpaSpecificationExecutor<Internet> {
    Iterable<Internet> findAllByOrderByIdDesc();

    List<Internet> findAll(Specification<Internet> spec);

    List<Internet> findAll();

    Optional<Internet> findById(Integer id);
}
