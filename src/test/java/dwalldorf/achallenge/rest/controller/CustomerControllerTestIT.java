package dwalldorf.achallenge.rest.controller;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dwalldorf.achallenge.Application;
import dwalldorf.achallenge.model.Customer;
import org.apache.catalina.filters.CorsFilter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, ExceptionController.class})
@WebAppConfiguration
public class CustomerControllerTestIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                                      .addFilter(new CorsFilter())
                                      .build();
    }

    @Test
    public void testGetCustomers_EmptyListReturns200() throws Exception {
        mockMvc.perform(get("/customers"))
               .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomers_ListWithResults() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(customer)))
               .andExpect(status().isCreated());

        mockMvc.perform(get("/customers"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].id", is(Matchers.greaterThan(0))))
               .andExpect(jsonPath("$[0].firstName", is(customer.getFirstName())));
    }

    @Test
    public void testCreateCustomer_Success() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(customer)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id", is(Matchers.greaterThan(0))))
               .andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
               .andExpect(jsonPath("$.lastName", is(customer.getLastName())));
    }

    @Test
    public void testCreateCustomer_NoFirstName() throws Exception {
        Customer customer = new Customer();
        customer.setLastName("Mustermann");

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(customer)))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCustomerById_Returns404IfNotFound() throws Exception {
        final Integer customerId = 1421;

        mockMvc.perform(get("/customers/{customerId}", customerId))
               .andExpect(status().isNotFound());
    }

    private static String toJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}