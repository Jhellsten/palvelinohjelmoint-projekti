package com.hellsten.projekti.harjoitus.web;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hellsten.projekti.harjoitus.domain.CategoryRepo;
import com.hellsten.projekti.harjoitus.domain.Item;
import com.hellsten.projekti.harjoitus.domain.ItemRepo;
import com.hellsten.projekti.harjoitus.domain.User;
import com.hellsten.projekti.harjoitus.domain.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemsController {

    @Autowired
    private ItemRepo items;
    @Autowired
    private CategoryRepo categories;
    @Autowired
    private UserRepo users;
    
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "error";
    }

    // REST API for all items
    @RequestMapping(value="/items", method = RequestMethod.GET)
    public @ResponseBody List<Item> itemListRest() {
    return (List<Item>) items.findAll();
    }

    // Get all items
    @GetMapping("/index")
    public String getItems(Model model) {
        List<Item> allItems = items.findAll();
        model.addAttribute("items", allItems);
        return "items";
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
        public String home(Model model) {
            List<Item> allItems = items.findAll();
            model.addAttribute("items", allItems);
            return "items";
    }

    // REST API for delete item
    @RequestMapping(value="/items/{id}", method = RequestMethod.DELETE)
        public @ResponseBody String deleteItemById() {
        return "deleted";
    }

    // Delete item by id
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("id") Long itemId, Model model) {
    items.deleteById(itemId);
    return "items";
    }   

    // Get values for new item
    @RequestMapping(value = "/newItem", method = RequestMethod.GET)
    public String newItem(Model model) {
    model.addAttribute("item", new Item());
    model.addAttribute("categories", categories.findAll());
    return "newitem";
    }   

    // REST API for get item by id
    @RequestMapping(value="/items/{id}", method = RequestMethod.GET)
    public @ResponseBody Item getItemById(@PathVariable("id") Long itemId) {
        return items.findById(itemId).orElse(null);
    }

    // REST API for Save new item
    @JsonBackReference(value = "category")
    @RequestMapping(value="/items",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    Item saveApiItem(@RequestBody Item item) {
    return items.save(item);
    }   

    // REST API for edit existing item
    @JsonBackReference(value = "category")
    @RequestMapping(value="/items/{id}",method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    Item saveApiItem(@RequestBody Item item, @PathVariable("id") Long itemId) {
        return items.findById(itemId)
        .map(existingItem -> {
        existingItem.setDescription(item.getDescription());;
        existingItem.setTitle(item.getTitle());;
        existingItem.setPrice(item.getPrice());;
        existingItem.setUser(item.getUser());;
        existingItem.setCategory(item.getCategory());;
        return items.save(existingItem);
      })
      .orElseGet(() -> {
        item.setId(itemId);
        return items.save(item);
      });
    }   

    // Edit existing item
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editItem(@PathVariable("id") Long itemId, Model model) {
    model.addAttribute("item", items.findById(itemId));
    model.addAttribute("categories", categories.findAll());
    return "editItem";
    }   

    // Save new item
    @RequestMapping(value = "/newitem", method = RequestMethod.POST)
    public String saveItem(@Valid @ModelAttribute("item") Item item, BindingResult bindingResults, @CurrentSecurityContext(expression="authentication.name")
    String username) {
        if (bindingResults.hasErrors()) {
			return "newitem";
        }
        User user = users.findByUsername(username);
        System.out.println(username);
        if(user == null) {
            return "items";
        }
        item.setUser(user);
        items.save(item);
        return "items";
    }   

    // Save new item
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateItem(@PathVariable("id") Long itemId, @Valid @ModelAttribute("item") Item item, BindingResult bindingResults, @CurrentSecurityContext(expression="authentication.name")
    String username) {
        if (bindingResults.hasErrors()) {
			return "/edit/" + itemId;
        }
        User user = users.findByUsername(username);
        System.out.println(username);
        if(user == null) {
            return "items";
        }
        item.setUser(user);
        items.save(item);
        return "items";
    }   

    // Get signup
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User newUser = new User();
        newUser.setRole("USER");
        model.addAttribute("user", newUser);
        return "signup";
    }   

    // Save new user
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResults) {
        if (bindingResults.hasErrors()) {
			return "signup";
        }
    if(users.findByUsername(user.getUsername()) == null) {
        user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(13)));
        user.setRole("USER");
        users.save(user);
    }
    
    return "login";
    }   

}
