package com.ecommerce.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.ecommerce.ecommerce.entities.LocalUser;

public interface LocalUserRepo extends CrudRepository<LocalUser, Long> {

    Optional<LocalUser> findByUsernameIgnoreCase(String username);

    Optional<LocalUser> findByEmailIgnoreCase(String email);
}
