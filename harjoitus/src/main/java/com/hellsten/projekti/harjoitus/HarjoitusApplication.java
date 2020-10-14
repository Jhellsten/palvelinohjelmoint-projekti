package com.hellsten.projekti.harjoitus;

import com.hellsten.projekti.harjoitus.domain.Category;
import com.hellsten.projekti.harjoitus.domain.CategoryRepo;
import com.hellsten.projekti.harjoitus.domain.Item;
import com.hellsten.projekti.harjoitus.domain.ItemRepo;
import com.hellsten.projekti.harjoitus.domain.User;
import com.hellsten.projekti.harjoitus.domain.UserRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class HarjoitusApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarjoitusApplication.class, args);
	}

	@Bean
	public CommandLineRunner itemsDemo(ItemRepo items, CategoryRepo categories, UserRepo users) {
		return (args) -> {
			String pw1 = BCrypt.hashpw("user", BCrypt.gensalt(13));
			String pw2 = BCrypt.hashpw("admin", BCrypt.gensalt(13));

			User user1 = new User("user", pw1, "USER");
			User user2 = new User("admin",pw2, "ADMIN");

			System.out.println(user1);
			System.out.println(user2);
			users.save(user1);
			users.save(user2);
			System.out.println("save a couple of categories");
			categories.save(new Category("Home"));
			categories.save(new Category("Sports"));
			categories.save(new Category("Games"));

			items.save(new Item("Microwave", "Almost new Microwave for sale", 125.00, categories.findByName("Home").get(0), users.findByUsername("user")));
        	items.save(new Item("FIFA 2021", "Brand new!", 69.00, categories.findByName("Games").get(0), users.findByUsername("user")));
			
			System.out.println("fetch all items");
			for (Item Item : items.findAll()) {
				System.out.println(Item.toString());
			}

		};
	}

}
