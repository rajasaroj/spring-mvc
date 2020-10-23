package guru.springframework.services;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)                  // This will bring up the spring context.
@SpringBootTest(classes=JpaIntegrationConfig.class)      // This will tell what configuartion we want to use.
@ActiveProfiles("jpaDao")                                // This will tell spring boot to use this as active profile for test.
@TestPropertySource(locations="classpath:testapplication.properties")   // This will tell spring boot to load the test properties from here.
public class ProductServiceJpaDaoImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void getAllProductTest() {
        List<Product> productServices = productService.getAllProducts();
        assert productServices.stream().count() == 9;
    }

}
