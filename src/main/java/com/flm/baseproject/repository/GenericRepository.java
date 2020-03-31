package com.flm.baseproject.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flm.baseproject.model.Generic;

@Repository
public interface GenericRepository extends JpaRepository<Generic, Long> {
	
	@Query("SELECT g FROM Generic g WHERE g.user.id = :userId ORDER BY g.name")
	List<Generic> findAllByUserIdAndOrderByNameAsc(@Param("userId") long userId);
	
	@Query("SELECT g FROM Generic g WHERE g.id = :id AND g.user.id = :userId")
	Optional<Generic> findByIdAndUserId(@Param("id") long id, @Param("userId") long userId);
	
	@Query("SELECT g FROM Generic g WHERE g.user.id = :userId " + 
			"AND (UPPER(g.name) LIKE CONCAT('%',UPPER(:search),'%'))")
	List<Generic> searchByUserIdAndOrderByNameAsc(@Param("userId") long userId, @Param("search") String search);
	
}
