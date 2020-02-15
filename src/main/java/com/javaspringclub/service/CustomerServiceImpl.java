package com.javaspringclub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaspringclub.exception.ResourceAlreadyExistException;
import com.javaspringclub.exception.ResourceNotFoundException;
import com.javaspringclub.repository.CustomerRepository;
import com.javaspringclub.resource.CustomerResource;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository repository;
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public CustomerServiceImpl() {

	}

	@Autowired
	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<CustomerResource> findAll() {
		List<CustomerResource> list = new ArrayList<CustomerResource>();
		repository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public CustomerResource findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public CustomerResource findByUserName(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public CustomerResource save(CustomerResource customer) {
		CustomerResource duplicateCustomer = this.findByUserName(customer.getUsername());
		if (duplicateCustomer != null) { // Customer with same username is already in the database
			log.info("Customer with username ={} found in database", customer.getUsername());
			throw new ResourceAlreadyExistException(customer.getUsername());
		}
		return repository.save(customer);

	}

	@Override
	public void update(CustomerResource customer, Long id) {
		CustomerResource customerFromDb = this.findById(id);

		if (customerFromDb != null) {
			// Customer found, update the id
			customer.setId(id);
			repository.save(customer);

		} else {
			// Customer not found, cannot update database
			log.info("Customer with id={} cannot found in the database", customer.getId());
			throw new ResourceNotFoundException(id);
		}

	}

	@Override
	public void deleteById(Long id) {
		CustomerResource c = this.findById(id);
		if (c != null) {
			repository.delete(c);
		} else {
			throw new ResourceNotFoundException(id);
		}
	}

}
