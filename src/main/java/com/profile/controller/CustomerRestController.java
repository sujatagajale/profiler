package com.profile.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.profile.dao.CustomerDAO;
import com.profile.model.Customer;


@RestController
public class CustomerRestController {

	private static final Logger logger = LogManager.getLogger(CustomerRestController.class);
	
	@Autowired
	private CustomerDAO customerDAO;

	
	@GetMapping("/customers")
	public List getCustomers() throws JsonProcessingException {
		logger.info("Get all customer information.");
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(customerDAO.list());
		
		logger.debug("All customer data : "+json);
		return customerDAO.list();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity getCustomer(@PathVariable("id") Long id) {

		logger.info("Get customer information of : "+id);
		Customer customer = customerDAO.get(id);
		if (customer == null) {
			logger.info("customer not found");
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(customer);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("Customer data for : "+id+" , Data : "+json);
		
		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {

		logger.info("Adding new customer in application.");
		
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(customer);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("New customer data : "+json);
		
		customerDAO.create(customer);
		logger.info("Added new customer in application.");
		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity deleteCustomer(@PathVariable Long id) {

		logger.info("Deleting customer : "+id);
		if (null == customerDAO.delete(id)) {
			logger.info("Customer not found for deletaton: "+id);
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}
		logger.info("Deleted customer : "+id);
		return new ResponseEntity(id, HttpStatus.OK);

	}

	@PutMapping("/customers/{id}")
	public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

		logger.info("Updating customer data : "+id);
		customer = customerDAO.update(id, customer);

		if (null == customer) {
			logger.info("customer not found for updation : "+id);
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}
		logger.info("Updated customer data : "+id);
		return new ResponseEntity(customer, HttpStatus.OK);
	}

}