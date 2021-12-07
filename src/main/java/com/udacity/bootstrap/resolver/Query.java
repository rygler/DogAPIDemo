package com.udacity.bootstrap.resolver;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.exception.DogNotFoundGraphQLException;
import com.udacity.bootstrap.repository.DogRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Query implements GraphQLQueryResolver {
    private DogRepository dogRepository;

    public Query(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Iterable<Dog> findAllDogs() {
        return dogRepository.findAll();
    }

    public Dog findDogById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            return optionalDog.get();
        } else {
            throw new DogNotFoundGraphQLException("Dog Not Found", id);
        }
    }
}