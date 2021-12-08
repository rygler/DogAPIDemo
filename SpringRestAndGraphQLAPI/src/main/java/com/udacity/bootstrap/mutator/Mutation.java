package com.udacity.bootstrap.mutator;

import com.udacity.bootstrap.entity.Dog;
import com.udacity.bootstrap.exception.DogNotFoundGraphQLException;
import com.udacity.bootstrap.repository.DogRepository;
import com.udacity.bootstrap.exception.BreedNotFoundGraphQLException;
import com.udacity.bootstrap.exception.DogNotFoundException;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {
    private DogRepository dogRepository;

    public Mutation(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public boolean deleteDogBreed(String breed) {
        boolean deleted = false;
        Iterable<Dog> allDogs = dogRepository.findAll();
        // Loop through all dogs to check their breed
        for (Dog d:allDogs) {
            if (d.getBreed().equals(breed)) {
                // Delete if the breed is found
                dogRepository.delete(d);
                deleted = true;
            }
        }
        // Throw an exception if the breed doesn't exist
        if (!deleted) {
            throw new BreedNotFoundGraphQLException("Breed Not Found", breed);
        }
        return deleted;
    }

    public Dog updateDogName(String newName, Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);

        if (optionalDog.isPresent()) {
            Dog dog = optionalDog.get();
            // Set the new name and save the updated dog
            dog.setName(newName);
            dogRepository.save(dog);
            return dog;
        } else {
            throw new DogNotFoundGraphQLException("Dog Not Found", id);
        }
    }
}