package com.hellsten.projekti.harjoitus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellsten.projekti.harjoitus.domain.Category;
import com.hellsten.projekti.harjoitus.domain.CategoryRepo;
import com.hellsten.projekti.harjoitus.domain.Item;
import com.hellsten.projekti.harjoitus.domain.ItemRepo;
import com.hellsten.projekti.harjoitus.domain.User;
import com.hellsten.projekti.harjoitus.domain.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
public class HarjoitusApplication {

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(HarjoitusApplication.class, args);
	}

	@Bean
	public CommandLineRunner itemsDemo(ItemRepo items, CategoryRepo categories, UserRepo users) {
		return (args) -> {
			// Read data from JSON and seed database according to that
			Random random = new Random();
			ObjectMapper mapper = new ObjectMapper();
			Resource usersfile = resourceLoader.getResource("classpath:users.json");
			TypeReference<List<User>> userTypeReference = new TypeReference<List<User>>(){};
			InputStream userInputStream = usersfile.getInputStream();
			Resource categoriesfile = resourceLoader.getResource("classpath:categories.json");
			TypeReference<List<Category>> categoryTypeReference = new TypeReference<List<Category>>(){};
			InputStream categoryInputStream = categoriesfile.getInputStream();
			Resource itemsfile = resourceLoader.getResource("classpath:items.json");
			TypeReference<List<Item>> itemTypeReference = new TypeReference<List<Item>>(){};
			InputStream itemInputStream = itemsfile.getInputStream();
			try {
				List<User> mappedUsers = mapper.readValue(userInputStream,userTypeReference);
				for (User user : mappedUsers) {
					user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(13)));
					users.save(user);
					System.out.println("User Saved!");
				}		
				List<Category> mappedCategories = mapper.readValue(categoryInputStream,categoryTypeReference);
				for (Category category : mappedCategories) {
					categories.save(category);
					System.out.println("Category Saved!");
				}		
				List<Item> mappedItems = mapper.readValue(itemInputStream,itemTypeReference);
				for (Item item : mappedItems) {
					item.setUser(users.findAll().get(random.nextInt(4 - 1) + 1));
					item.setCategory(categories.findAll().get(random.nextInt(4 - 1) + 1));
					items.save(item);
					System.out.println("Item Saved!");
				}		
			} catch (IOException e){
				System.out.println("Unable to save users: " + e.getMessage());
			}

		};
	}

}
