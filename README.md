# spring-mvc

This Project we have created a basic spring boot app that display the list  of items refered as products.

1) We created Domain, pojo Product
2) We created the ProductService and ProductServiceImpl which handle load of map with products objects and restriving of list of products.
3) We created another controller named as ProductController and injected the service impl 
    1) it get the data from service and add in the attribute and return to View layer

4) Thyme engine in the product.html interates on the products objects using th:each and prints inside the table.
5) Voila! all the products are displayed in the table when you make localhost:8080\product call from browser (make sure springboot app is running!)



# Branch: JPA
1) in this branch we have configured our POJO class as an Database entity so that, when you kick of the spring boot application it will do below steps
      1) Read the maven packages for JPA and msql
      2) load the Spring context with all the spring beans from those packages as well.
      3) Read the application properties file for database configuration and using mssql driver connect to database
      4) read the pojo annotated with entity and create the table in msql with the name and properties of pojo. (Product.java in our case).
      
      
      
# Branch: JpaOptimisticLocking
1) I would start off explaning this with two senarios.

Senario 1: lets say youve have developed an web application where user can buy or sell stocks.
           but the citeria here is once the buyer declares a price the user must approve and accept that price before buying it.  

![Jpa Optimitic locking senario 1](https://user-images.githubusercontent.com/42677426/96957111-6a7dea00-1517-11eb-9b42-ea4930419820.png)

Problem: In the above example you can see the user1 is reading the price $7999$ but the user2 (who is a potential seller) is updating the price at the same time.
         Now what will happen is user1 who is a buyer has approved the price of stocks as 7999$ as he has reviewed that price and approved it he plans to proceed with    
         procurement of that stock, but now in the database new price has been updated for the same stock, and it has not been reviewed by buyer. This is an race condition  
         issue. We wantevery price that is updated by seller supposed to be reviewed and approved by buyer.
         


Senario 2: in order to avoid the concurrency race issue, we create add a property in side our pojo class version and anotate it as @version,
           Whenever commit will happen it will increment the value of property version. 

![Jpa Optimitic locking senario 2 Solution](https://user-images.githubusercontent.com/42677426/96958297-a1093400-151a-11eb-8239-462028b3d757.png)

Solution: In the above pic you can see the version value is 1000, when the read transection done by user1 the value of price is 7999$ and version property is 1000.
          as soon as the user2 update the price the version value is incremented and while making commit the read/write msql check the version and founds if the version value is 
          changed it throw OptimisticlockExpection notifying the program the version value is changed while read was happening


# Branch: JpaDaoServicePattern
In this Branch we have replace or data layer with Mysql databases and performed all the CRUD operation to database using DAO Design pattern.
1) added a new service implementation for talking with database "ProductServiceJspDaoImpl.java".

        1) Created entityManagerFactory and anotated it with @PersistenceUnit.
           Here the object of entityManagerFactory is populated by spring itself
           annotating it with @PersistanceUnit makes it more generic to work with.
           variety of other databases frameworks like hibernate, H2 etc.
           
        2) Anotated this class as @Service and @Profile(jpaDao) so that we can set profile as jpaDao if we want to use Database.
        
        3) added one more line in productform.html 
           <input type="hidden" th:field="*{version}"/> this also supply the version with the object in the case of updating/editing the object databases rely on version to
           to identify whether the object is new or updated, if the object is new then the version will be null, if old object then it will already have some value.
        
        4) Added spring.datasource.password=${Rdbpassword} in application.properties to read the password from vm args.
           and also added spring.datasource.hikari.maximum-pool-size=30 so that you doesn't run into connection problem and timeouts.
        
        
# Branch: BootStrapData
1) In this branch we have added code for initail loading of data so that when you first time run this code it will add some default products and show you on view layer


# Branch: JpaIntegrationTests
1) In this Branch we have written an Integration Test case for our ProductServiceJspDaoImpl.java.

This branch we will talk about:

    1) How to setup spring boot for test purpose?
    2) How to configure our own configuration class?
    3) How to load test specific properties?
    3) How to set test specific active profile?
    4) How to Kickoff Springboot for testing Integration test without starting Tomcat?
    
    1)  Steps a) Create ProductServiceJpaDaoImplTest.java file this file will have all of your test cases.
        Annotate this file with below annotation.
        @RunWith(SpringJUnit4ClassRunner.class)                  // This will bring up the spring context.
        @SpringBootTest(classes=JpaIntegrationConfig.class)      // This will tell what configuartion we want to use.
        @ActiveProfiles("jpaDao")                                // This will tell spring boot to use this as active profile for test.
        @TestPropertySource(locations="classpath:testapplication.properties")   // This will tell spring boot to load the test properties from here. (Note Properties file stored in /test/resources)
        this answers you're Question 1), 3), and 4)
    
    2) Create another package name it as config and create a JpaIntegrationConfig.java class.
       Annotate this file with below annotation.
       
       /* @Configuartion tells the spring boot that this class is the configuaration class
        * @EnableAutoConfiguration ask the the spring boot to auto configure it self
        * @ComponentScan tell springboot to load the beans from heirarchy of all the packages inside "guru.springframework"
        *
        */
       @Configuration
       @EnableAutoConfiguration
       @ComponentScan("guru.springframework")
       this answers you're Question 2).
       


# Branch: One2oneEntityReleationUnidirection

In this branch we have Introduced new entits Customer and Users
The Relationship between them us one to one and undirection, this means every customer ---> is as user But every user might or might not be a customer.
Hence we have provide the reference of user entity in Customer class and annotated it with cascade.


    Class Customer
    /*  This will attach user entity to customer (representing every customer ---> is as user But every user might or might not be a customer)
     *  It a unidirectional relation ship hence we have user reference only in Customer class
     *  and any operation done no customer it will propogate to user as well (since customer is parent and User is child)
     *  This will also save you from detached entity exception.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    
Along with this we have also added on Security feature to keep users passwords encrpted.
Hence in User class we have annotated password feild as @Trasient so that it doesn't store raw password instead it should store the encrpted password
The logic for password encrption comes from Third party libraries... and as we know whenever we want to inject third party libraries object we have to create @Confguation anotated class.
Hence we have created 2 packages
    1) CommonConfig: it will handle the creation of bean of 3rd party libraries
    2) services/security: this will define method for encrpting password and checking passwords etc.
    
    
    
#Branch: BidirectionalOne2oneEntityRelationship

In this branch have created bidirection association between Customer and Users (assuming Customer and users are in same business)
The Relationship between them us one to one and bidirection,

    Class Customer
    /*  This will attach user entity to customer and customers entity to the user (Considering the user and costumer are in same business case)
     *  It a bidirectional relation ship hence we have user reference in Customer class
     *  and Customer reference in user class.
     *
     * The objective here is: to provide all cascade operation permit to customer
     *                        and provide only save and update permit to user
     *
     *  This will also save you from detached entity exception.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
   
We have made small change with the User entity this will enable User to keep the reference of customer object, this make it bidirectional

    class User
    /* Now we are doing birerection asssociation where a user can also Save or update, we do this using below cascade  type
     * CascadeType PERSIST: propagates the persist operation from a parent to a child entity. When we save the User entity, the Customer entity will also get saved.
     * CascadeType.MERGE: propagates the merge operation from a parent to a child entity. this update the state of given object (That would be basically an update in parent or child object) into database persistane object.
     */
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    Customer customer;
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.setUser(this);
    }

above setter setCustomer in user class is saving the user object in customer 
and saving the customer object in user. 
Here binding is happening on both the side.


# Branch: Many2Manyrelationship

In this branch we have implemented many 2 many releationship example
The idea over here is a products has multiple likers and a customers can like multiple products this creates many 2 many association.

In order to implement this we have make few simple changes in our pojo entites classes.


    1) Customer class: 
       @ManyToMany(cascade = CascadeType.ALL)   // this will define many 2 many relation ship with cascade type
       @JoinTable(name = "product_liked",       // This will define the name of joined table which will hold ids of both the entities to represent there asociation
               joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),   // This define column name in join table refering customer (our source table)
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")  // this difine column name in join table refering Products (our target table)
       )
       private Set<Product> productset;         // this will hold object of all products liked by this costumer                                          
      
       
    2) Product class:
           @ManyToMany(mappedBy = "productset")  // This defines mapping between pruductset and customerset Notice the "productset" is the set property in the Customer column
           private Set<Customer> customerset;
    
    
More verification details regarding its woking is discussed in test class in many2manytest() 
make sure to check that our, I also talks above the most mischevious problem in many2many JPA mapping regarding duplicated updates and also give the solution for it.
    
    
