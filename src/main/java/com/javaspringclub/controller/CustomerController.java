package com.javaspringclub.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaspringclub.resource.CustomerResource;
import com.javaspringclub.service.CustomerService;

@RestController
@RequestMapping("/restws")
public class CustomerController {

	private CustomerService service;

	public CustomerController() {

	}

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	// URI - http://localhost:8080/restws/customers
	// RequestMapping name at the top of this file is "/restws"
	// GetMapping below is "/customers"
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerResource>> retrieveAllCustomers() {
		// read from database
		List<CustomerResource> customers = service.findAll();
		if (customers.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(customers); // return 200, with json body
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerResource> getCustomer(@PathVariable long id) {
		CustomerResource customer = service.findById(id);
		if (customer != null) {
			return ResponseEntity.ok(customer); // return 200, with json body
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	// URI - http://localhost:8080/restws/customers
	@PostMapping("/customers")
	public ResponseEntity<Object> createCustomer(@RequestBody CustomerResource customer) {
		// save to database
		CustomerResource newCustomer = service.save(customer);
		if (newCustomer != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(newCustomer.getId()).toUri();
			return ResponseEntity.created(location).build();
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}

	}

	// URI - http://localhost:8080/restws/customers/{id}
	@PutMapping("/customers/{id}")
	public ResponseEntity<Object> updateCustomer(@RequestBody CustomerResource customer, @PathVariable long id) {
		service.update(customer, id);
		return ResponseEntity.noContent().build();

	}

	// URI - http://localhost:8080/restws/customers/{id}
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();

	}

}
