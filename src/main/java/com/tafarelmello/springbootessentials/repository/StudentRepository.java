package com.tafarelmello.springbootessentials.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tafarelmello.springbootessentials.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByNameIgnoreCaseContaining(String name);
}
