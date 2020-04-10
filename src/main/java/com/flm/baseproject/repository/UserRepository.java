package com.flm.baseproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flm.baseproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.id = :id")
	public User findById(@Param("id") long id);
	
	@Query("SELECT u FROM User u WHERE UPPER(u.login) = UPPER(:loginOrEmail) OR UPPER(u.email) = UPPER(:loginOrEmail)")
	public User findByLoginOrEmail(@Param("loginOrEmail") String loginOrEmail);

	@Query("SELECT u FROM User u ORDER BY u.name")
    public List<User> findAllOrderByNameAsc();
    
    @Query("SELECT u FROM User u WHERE UPPER(u.login) = UPPER(:login)")
    public User findByLogin(@Param("login") String login);
    
    @Query("SELECT u FROM User u WHERE UPPER(u.email) = UPPER(:email)")
	public User findByEmail(@Param("email") String email);
}
