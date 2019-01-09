package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Package;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends CrudRepository<Package, Integer> {
    List<Package> findAll();

    Optional<Package> findById(Integer id);
}
