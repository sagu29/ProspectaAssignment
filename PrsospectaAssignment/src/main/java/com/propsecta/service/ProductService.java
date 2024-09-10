package com.propsecta.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.propsecta.entity.NewProduct;
import com.propsecta.entity.Product;
import com.prospecta.exception.ProductException;

@Service
public class ProductService {
	
	private final RestTemplate restTemplate = new RestTemplate();
    
    public Product getProductCategory() {
    	Product response= restTemplate.getForObject("https://fakestoreapi.com/products/category/", Product.class);
    	if (response != null) {
            return response;
        } else {
            throw new ProductException("Products not found in category: ");
        }
    }
    
    public ResponseEntity<String> addNewProduct(NewProduct newProduct) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<NewProduct> entity = new HttpEntity<>(newProduct, headers);
        
        if (entity== null) {
        	throw new ProductException("Failed to add new product.");
        	}
        else {
        return restTemplate.exchange("https://fakestoreapi.com/products", HttpMethod.POST, entity, String.class);
        }
    }
    
    

}
