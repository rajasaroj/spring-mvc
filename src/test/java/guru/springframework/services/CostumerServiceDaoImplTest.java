package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Customer;
import guru.springframework.domain.PaymentGateway;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JpaIntegrationConfig.class)
@ActiveProfiles("jpaDao")
@TestPropertySource(locations = "classpath:testapplication.properties")
public class CostumerServiceDaoImplTest {

    private CustomerService customerService;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

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

    @Test
    public void saveOrmodifyusingPaymentCustomerTest () {

        PaymentGateway paymentGateway = new PaymentGateway();
        paymentGateway.setGateWay("Amazon pay");

        Customer customer = new Customer();
        customer.setFirstName("Arjen");
        customer.setLastName("Wkaoiz");

        User user = new User();
        user.setUserName("Arjen.Wkaoiz@gmail.com");
        user.setPassword("Wkaoiz123");

        customer.setUser(user);
        customer.setPaymentGateway(paymentGateway);
        customerService.saveOrUpdate(customer);

        List<Customer> customers1 = (List<Customer>) customerService.listAll();

        for (Customer cs: customers1) {
            cs.setPaymentGateway(paymentGateway);
            customerService.saveOrUpdate(cs);
        }
    }



    // Test Case for many 2 many Senario
//      Raja: Tooth brush,
//             Phone
//             keyboard
//             laptop
//
//      Arjun: Phone
//             laptop
//             headphone
//
//      kasim: Grapler
//             phone
//             headphone
//
//      Product wise:
//      Phone: Raja
//             Arjun
//             kasim
//
//      keyboard: raja
//
//      laptop: Raja
//              Arjun
//
//      headphone: kasim,
//                 Arjun
//
//      Tooth brush: Raja
//      grapler: kasim





    @Test
    public void many2manyTest() {

        Customer rajaCus = new Customer();
        rajaCus.setFirstName("raj");
        Customer arjunCus = new Customer();
        arjunCus.setFirstName("arjun");

        Customer kasimCus = new Customer();
        kasimCus.setFirstName("kasim");



        Set<Product> rajaCusSet = new HashSet<>();
        Set<Product> arjunCusSet = new HashSet<>();
        Set<Product> kasimCusSet = new HashSet<>();

        Product Tbrush = new Product("Tooth bursh");
        Product Phone = new Product("Phone");
        Product Keyboard = new Product("keyboard");
        Product Laptop = new Product("laptop");
        Product Headphone = new Product("Headphone");
        Product Grapler = new Product("Grapler");


        // Add these prducts into database
        productService.saveOrUpdateProduct(Tbrush);
        productService.saveOrUpdateProduct(Phone);
        productService.saveOrUpdateProduct(Keyboard);
        productService.saveOrUpdateProduct(Laptop);
        productService.saveOrUpdateProduct(Headphone);
        productService.saveOrUpdateProduct(Grapler);





        /* In order to implement many 2 many mapping correctly the objects should should persistent in database
           and when there ids are generated by database then those objects should be used to plug in the set of Customer class
           This will represent that a customer can buy multiple brand products and multiple products have ther own constumers etc.

           Below in this code
           1) we have stored the products in database
           2) After they became persistent we downloaded those products and then we associated them with the customers
           3) above 1) and 2) help to avoid recreation of duplicated products Specially when you save these customer association with product you have to make sure they dont get recreated again
           4) The best way to avoid this is map the product if they already exist or else you create new one
         */


        List<Product> productList = productService.getAllProducts();


        // Setting set for each user based on above descrption
        rajaCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Tooth bursh")).findAny().orElse(null));
        rajaCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Phone")).findAny().orElse(null));
        rajaCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("keyboard")).findAny().orElse(null));
        rajaCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("laptop")).findAny().orElse(null));
        rajaCus.setProductset(rajaCusSet);
        customerService.saveOrUpdate(rajaCus);


        arjunCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Phone")).findAny().orElse(null)  );
        arjunCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("laptop")).findAny().orElse(null)  );
        arjunCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Headphone")).findAny().orElse(null)   );

        arjunCus.setProductset(arjunCusSet);
        customerService.saveOrUpdate(arjunCus);

        kasimCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Grapler")).findAny().orElse(null)   );
        kasimCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Phone")).findAny().orElse(null)   );
        kasimCusSet.add((Product) productList.stream().filter(x -> x.getDescription().contentEquals("Headphone")).findAny().orElse(null)   );

        kasimCus.setProductset(kasimCusSet);
        customerService.saveOrUpdate(kasimCus);



    }


}
