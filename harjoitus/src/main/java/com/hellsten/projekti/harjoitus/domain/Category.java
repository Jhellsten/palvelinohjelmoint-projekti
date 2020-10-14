package com.hellsten.projekti.harjoitus.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryid;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Item> items;

    public Category() {
    }
    
    public Category(String name) {
        this.name = name;
    }

    public long getId() {
        return categoryid;
    }

    public void setId(long categoryid) {
        this.categoryid = categoryid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getBooks() {
		return items;
	}

	public void setBooks(List<Item> items) {
		this.items = items;
    }
    
    @Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", name=" + name + "]";
	}


}

