package com.propsecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.propsecta.entity.NewProduct;
import com.propsecta.entity.Product;
import com.propsecta.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	public ProductService ps;
	
	@GetMapping("Products/category/")
	public ResponseEntity<Product> getProductsCategory(){
		return new ResponseEntity<>(ps.getProductCategory(), HttpStatus.OK);
	}
	
	
	@PostMapping("Products/add")
    public ResponseEntity<String> addProduct(@RequestBody NewProduct newProduct) {
        ResponseEntity<String> response = ps.addNewProduct(newProduct);
        return ResponseEntity.ok(response.getBody());
    }
	
	
	

}
