package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpaDao")
@TestPropertySource(locations = "classpath:testapplication.properties")
public class CostumerServiceDaoImplTest {

    private CustomerService customerService;
    private UserService userService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void getAllCustomerTest() {
        List<Customer> customers = (List<Customer>) customerService.listAll();
        assert customers.stream().count() >= 1;
    }

    @Test
    public void saveOrmodifyusingCustomerTest () {

        List<Customer> customers = (List<Customer>) customerService.listAll();
        User user = new User();
        user.setUserName("rsaroj");
        user.setPassword("MyAwesomePassword");
        Customer customer = customers.get(0);
        customer.setUser(user);
        customerService.saveOrUpdate(customer);
        List<Customer> customers1 = (List<Customer>) customerService.listAll();
        System.out.println(customers1.get(0));

    }

    @Test
    public void saveORmodifyUsingUser() {
        Customer customer = new Customer();
        customer.setFirstName("Jenny");
        customer.setLastName("lopez");

        User user = new User();
        user.setUserName("JLo@gmail.com");
        user.setPassword("herAwsomepasword");
        user.setCustomer(customer);

        userService.saveOrUpdate(user);
        List<User> users = (List<User>) userService.listAll();

        users.forEach(System.out::println);

    }



}
