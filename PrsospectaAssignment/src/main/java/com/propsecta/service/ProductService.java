package com.propsecta.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.propsecta.entity.NewProduct;
import com.propsecta.entity.Product;

@Service
public class ProductService {
	
	private final RestTemplate restTemplate = new RestTemplate();
    
    public Product getProductCategory() {
    	return restTemplate.getForObject("https://fakestoreapi.com/products/category/", Product.class);
    }
    
    public ResponseEntity<String> addNewProduct(NewProduct newProduct) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<NewProduct> entity = new HttpEntity<>(newProduct, headers);
        return restTemplate.exchange("https://fakestoreapi.com/products", HttpMethod.POST, entity, String.class);
    }
    
    

}
