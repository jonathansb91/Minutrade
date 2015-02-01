package com.example.minutrade;

public class Product {
	private String name, description, price;

	public Product(String name, String price, String description) {
		this.setName(name);
		this.setPrice(price);
		this.setDescription(description);
	}

	public Product() {
		this("", "", "");
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getDescription() {
		return description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	String getPrice() {
		return price;
	}

	void setPrice(String price) {
		this.price = price;
	}

}
