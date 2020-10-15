package com.hellsten.projekti.harjoitus.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepo extends CrudRepository<Category, Long>  {

    List<Category> findByName(@Param("name") String name);
    List<Category> findAll();
    
}
