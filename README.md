# spring-mvc

This Project we have created a basic spring boot app that display the list  of items refered as products.

1) We created Domain, pojo Product
2) We created the ProductService and ProductServiceImpl which handle load of map with products objects and restriving of list of products.
3) We created another controller named as ProductController and injected the service impl 
    1) it get the data from service and add in the attribute and return to View layer

4) Thyme engine in the product.html interates on the products objects using th:each and prints inside the table.
5) Voila! all the products are displayed in the table when you make localhost:8080\product call from browser (make sure springboot app is running!)

 
