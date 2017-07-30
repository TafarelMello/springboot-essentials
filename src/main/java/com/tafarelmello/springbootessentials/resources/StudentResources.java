package com.tafarelmello.springbootessentials.resources;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tafarelmello.springbootessentials.domain.Student;
import com.tafarelmello.springbootessentials.error.CustomErrorType;
import com.tafarelmello.springbootessentials.error.ResourceNotFoundException;
import com.tafarelmello.springbootessentials.repository.StudentRepository;

@RestController
@RequestMapping("/v1")
public class StudentResources {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentResources(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping("protected/students")
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(studentRepository.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("protected/students/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
		System.out.println(userDetails);

		verifyIfStudentExists(id);
		return new ResponseEntity<>(studentRepository.findOne(id), HttpStatus.OK);
	}

	@GetMapping("protected/students/findByName/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		List<Student> students = studentRepository.findByNameIgnoreCaseContaining(name);

		if (students == null) {
			return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(students, HttpStatus.OK);
		}
	}

	@PostMapping("admin/students")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Student student) {
		return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
	}

	@DeleteMapping("admin/students/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		verifyIfStudentExists(id);
		studentRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("admin/students")
	public ResponseEntity<?> update(@RequestBody Student student) {
		verifyIfStudentExists(student.getId());
		studentRepository.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void verifyIfStudentExists(Long id) {
		if (!studentRepository.exists(id)) {
			throw new ResourceNotFoundException("Student not found for ID: " + id);
		}
	}
}
