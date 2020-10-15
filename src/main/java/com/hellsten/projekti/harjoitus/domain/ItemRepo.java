package com.hellsten.projekti.harjoitus.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ItemRepo extends CrudRepository<Item, Long>  {

    List<Item> findByTitle(@Param("title") String title);
    List<Item> findByCategory(@Param("category") String category);
    List<Item> findAll();

    
}
