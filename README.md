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

