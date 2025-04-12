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
    private final ElasticFilterRepository elasticFilterRepository;

    public UserController(UserElasticRepository userElasticRepository, ElasticFilterRepository elasticFilterRepository) {
        this.userElasticRepository = userElasticRepository;
        this.elasticFilterRepository = elasticFilterRepository;
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
    @GetMapping("/search")
    public Iterable<User> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer age
            ) {
        return elasticFilterRepository.searchByFilter(name, surname,age);
    }

}
