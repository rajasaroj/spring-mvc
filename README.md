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