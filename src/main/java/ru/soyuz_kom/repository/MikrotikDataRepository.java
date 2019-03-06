package ru.soyuz_kom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.soyuz_kom.entity.Mikrotik;
import ru.soyuz_kom.entity.MikrotikData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MikrotikDataRepository extends CrudRepository<MikrotikData, Integer> {

    List<MikrotikData> findAll();

    Optional<MikrotikData> findById(Integer id);

    Set<MikrotikData> findByClientId(Integer id);
}
