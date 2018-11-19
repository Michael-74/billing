package ru.soyuz_kom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.soyuz_kom.entity.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {

}
