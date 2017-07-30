package com.tafarelmello.springbootessentials.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tafarelmello.springbootessentials.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
