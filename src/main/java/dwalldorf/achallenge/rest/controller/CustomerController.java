package dwalldorf.achallenge.rest.controller;

import dwalldorf.achallenge.model.Customer;
import dwalldorf.achallenge.rest.exception.NotFoundException;
import dwalldorf.achallenge.service.CustomerService;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        customer = customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable final Integer customerId) throws NotFoundException {
        Optional<Customer> customer = customerService.findById(customerId);

        if (!customer.isPresent()) {
            throw new NotFoundException();
        }
        return customer.get();
    }
}
