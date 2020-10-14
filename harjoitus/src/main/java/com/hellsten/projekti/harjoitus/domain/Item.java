package com.hellsten.projekti.harjoitus.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String title;
    private String description;
    private int year;
    private double price;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public Item(String title, String description, int year, double price, Category category) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.price = price;
        this.category = category;
    }

    public Item() {
        
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Override
	public String toString() {
		return "Item [title=" + title + " description=" + description +  " year=" + year + " price=" + price + " category=" + category.getName() + " user=" + user.getUsername() + "]";
	}

}
