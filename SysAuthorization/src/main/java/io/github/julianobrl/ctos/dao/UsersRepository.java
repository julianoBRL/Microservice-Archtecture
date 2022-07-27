package io.github.julianobrl.archtecture.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.julianobrl.archtecture.model.Users;

import java.util.HashSet;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUsername(String username);
	Users findByUsernameAndPassword(String username, String password);
	HashSet<Users> findByUsernameContaining(String username); 
	HashSet<Users> findByFullnameContaining(String fullname); 
	
}