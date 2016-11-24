package dwalldorf.achallenge.service;

import dwalldorf.achallenge.model.Customer;
import dwalldorf.achallenge.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    public Optional<Customer> findById(final Integer id) {
        Customer customer = customerRepository.findOne(id);
        return Optional.ofNullable(customer);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
