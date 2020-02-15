package com.javaspringclub.service;

import java.util.List;

import com.javaspringclub.resource.CustomerResource;

public interface CustomerService {

	public List<CustomerResource> findAll();
	public CustomerResource findById(Long id);
	public CustomerResource findByUserName(String userName);
	public CustomerResource save(CustomerResource customer);
	public void update(CustomerResource customer, Long id);
	public void deleteById(Long id);
}
