# ProspectaAssignment

# FakeStoreAPI Integration with Spring Boot

## Overview

This project demonstrates the integration of Spring Boot with [FakeStoreAPI](https://fakestoreapi.com/) to implement two API functionalities:
1. Retrieve product details by category.
2. Add a new product to the store.

## Features

- **List Products by Category**: Fetches product details based on the category provided as a path parameter.
- **Add a New Product**: Allows adding a new product entry via a POST request.

## Technologies Used

- **Java 11+**
- **Spring Boot**
- **RestTemplate (for external API calls)**
- **Postman (for testing APIs)**

## API Endpoints

### 1. List Products by Category

**Endpoint**: `GET products/category/{category}`

This API retrieves all products for a given category from FakeStoreAPI.

#### Example Request

##### http
GET http://localhost:8080/products/category/jewelery

###### Expected Output 

  {
    "id": 5,
    "title": "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
    "price": 695,
    "description": "From our Legends Collection, the Naga was inspired by the mythical water dragon that protects the ocean's pearl.",
    "category": "jewelery",
    "image": "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
    "rating": {
      "rate": 4.6,
      "count": 400
    }
  },
  {
    "id": 6,
    "title": "Solid Gold Petite Micropave",
    "price": 168,
    "description": "Satisfaction Guaranteed. Return or exchange any order within 30 days.",
    "category": "jewelery",
    "image": "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg",
    "rating": {
      "rate": 3.9,
      "count": 70
    }
  }

##### http

###### Expected Output 

POST http://localhost:8080/products/

{
  "id": 21,
  "title": "Laptop",
  "price": 199999.99,
  "description": "This is a brand new laptop.",
  "category": "electronics",
  "image": "[(https://www.energy.gov/sites/default/files/styles/full_article_width/public/computer%20tablet%20phone.jpg?itok=LZwHFtY_)]",
  "rating": {
    "rate": 0,
    "count": 0
  }
}



# CSV Formula Evaluator

This Java program reads a CSV file containing both numeric values and Excel-like formulas, evaluates the formulas, and writes the computed values back into a new CSV file.

## Features

- **Value Parsing**: Reads values directly from the CSV file.
- **Formula Evaluation**: Evaluates formulas that reference other cells and performs basic arithmetic operations like `+`, `-`, `*`, and `/`.
- **Output**: Writes the final computed values to a new CSV file.

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven (optional, for dependency management)

### How to Use

1. **Clone the repository** (if applicable) or download the source files.
2. **Compile the code** using a Java compiler or your favorite IDE.
3. **Replace file paths** in the `main` method with your actual CSV file locations:
   ```java
   CSVFormulaEvaluator evaluator = new CSVFormulaEvaluator("path/to/your/input.csv");
   evaluator.evaluateSheet();
   evaluator.writeCSV("path/to/your/output.csv");


### Input 
A1,B1,C1
5,3,=5+A1
7,8,=A2+B2
9,=4+5,=C2+B3

### Expected Output 

A1,B1,C1
5,3,10
7,8,15
9,9,24



### Steps:
1. Replace the file paths in the  section with the actual locations where you plan to store your CSV files.
2. Modify the  section if you want to specify a particular license for your project.
3. You can add additional sections for dependencies (e.g., Maven dependencies for `javax.script` if you're using it).



