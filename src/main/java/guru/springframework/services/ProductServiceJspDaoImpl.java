package guru.springframework.services;

import guru.springframework.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Profile("jpaDao")
public class ProductServiceJspDaoImpl implements ProductService {

    private EntityManagerFactory entityManagerFactory;

    /** Here the object of entityManagerFactory is populated by spring itself
     * annotating it with @PersistanceUnit makes it more generic to work with.
     * variety of other databases frameworks like hibernate, H2 etc.
     *
     * @param entityManagerFactory
     */
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Product> getAllProducts() {
        EntityManager entityManager  = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product getProduct(int id) {
        EntityManager entityManager  = entityManagerFactory.createEntityManager();
        return entityManager.find(Product.class, id);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        EntityManager entityManager  = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        /**
         *  the reason behind calling merge here is we need 2 behaviour
         *  1) if new product arrive it should add it in db (Jpa will take care of generating new)
         *  2) updated product arrives it should update that particular product in the database instead of adding it as new product.
         *
         */
        Product savedProduct = entityManager.merge(product);
        entityManager.getTransaction().commit();
        return savedProduct;
    }

    @Override
    public String deleteProduct(int id) {

        EntityManager entityManager  = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Product.class,id));
        entityManager.getTransaction().commit();

        Boolean isPresent = entityManager.contains(entityManager.find(Product.class,id));



        if (!isPresent)
            return "product deleted";
        else
            return "product doesn't exist!";
    }
}
