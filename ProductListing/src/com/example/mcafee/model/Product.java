package com.example.mcafee.model;

public class Product {

	String name, image, type, price, rating, users, last_update,description, url;

	public Product(String name, String image, String type, String price,
			String rating, String users, String last_update,
			String description, String url) {
		super();
		this.name = name;
		this.image = image;
		this.type = type;
		this.price = price;
		this.rating = rating;
		this.users = users;
		this.last_update = last_update;
		this.description = description;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", image=" + image + ", type=" + type
				+ ", price=" + price + ", rating=" + rating + ", users="
				+ users + ", last_update=" + last_update + ", description="
				+ description + ", url=" + url + "]";
	}
	
}
