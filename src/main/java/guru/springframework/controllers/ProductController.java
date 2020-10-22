package guru.springframework.controllers;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {


    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    public String displayProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @RequestMapping("/product/{id}")
    public String displaySingleProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "product";
    }

    @RequestMapping("/product/deleteform")
    public String deleteForm() {
        return "deleteproductform";
    }

    @RequestMapping("/edit/{id}")
    public String editProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "productform";
    }

    @RequestMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "productform";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveOrupdate(Product product) {
        Product savedProduct = productService.saveOrUpdateProduct(product);
        return "redirect:/product/" + savedProduct.getId();
    }

    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("pid") int id, Model model) {
        return deleteHelper(id, model);
    }

    @RequestMapping("/delete/{id}")
    public String deleteProductDirectly(@PathVariable int id, Model model) {
        return deleteHelper(id, model);
    }

    public String deleteHelper(int id, Model model) {
        String message = productService.deleteProduct(id);
        model.addAttribute("deleteProduct", message);
        return "delproduct";
    }

}
