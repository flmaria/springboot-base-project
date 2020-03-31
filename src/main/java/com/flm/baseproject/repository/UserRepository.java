package com.flm.baseproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flm.baseproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	
	Optional<User> findByLoginOrEmail(String login, String email);

    List<User> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
    
    List<User> findByOrderByNameAsc();
    
	List<User> findByIdIn(List<Long> userIds);

	Optional<User> findByLogin(String login);

	Boolean existsByLogin(String login);

	Boolean existsByEmail(String email);

}
