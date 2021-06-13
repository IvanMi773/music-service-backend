package com.network.social_network.repository;

import com.network.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
