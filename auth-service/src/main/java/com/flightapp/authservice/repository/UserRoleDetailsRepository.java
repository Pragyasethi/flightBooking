package com.flightapp.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.authservice.model.UserRoleDetails;

@Repository
public interface UserRoleDetailsRepository extends JpaRepository<UserRoleDetails, Long>{
	
	Optional<UserRoleDetails> findByUsername(String username);
	

}
