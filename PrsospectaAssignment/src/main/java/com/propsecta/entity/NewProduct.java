package com.propsecta.entity;

import lombok.Data;

@Data
public class NewProduct {
	
	private String title;
    private double price;
    private String description;
    private String category;
    private String image;

}
