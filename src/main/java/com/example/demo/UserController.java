package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")

public class UserController {

    private final UserElasticRepository userElasticRepository;

    public UserController(UserElasticRepository userElasticRepository) {
        this.userElasticRepository = userElasticRepository;
    }

    @PostMapping
    public void save(@RequestBody User user){
        userElasticRepository.save(user);
    }
    @GetMapping
    public Iterable<User> getAll(){

        return userElasticRepository.findAll();

    }
    @GetMapping("/{name}")
    public Iterable<User> getUserFromName(@PathVariable String name){
        return userElasticRepository.getUserFromName(name);
    }
    @GetMapping("startwith/{keyword}")
    public Iterable<User> getUserStartWithKeyword(@PathVariable String keyword){
        return userElasticRepository.getUserStartsWithKeyword(keyword);
    }

}
