package ru.soyuz_kom.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Internet;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmotreshkaSettingRepository extends CrudRepository<Smotreshka, Integer> {

    List<Internet> findAll();

    Optional<Internet> findById(Integer id);
}
