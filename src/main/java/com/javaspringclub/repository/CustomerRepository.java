package com.javaspringclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaspringclub.resource.CustomerResource;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerResource, Long> {
	 Optional<CustomerResource> findByUsername(String username);
}
