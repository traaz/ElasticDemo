package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        Iterable<User> users = userElasticRepository.findAll();
        List<User> userList = new ArrayList<>();
        users.forEach(userList::add); //  //sadece content içeriğini alıyoz
        return userList;

    }
    @GetMapping("/{name}")
    public List<User> getUserFromName(@PathVariable String name){
        return userElasticRepository.getUserFromName(name);
    }
    @GetMapping("startwith/{keyword}")
    public List<User> getUserStartWithKeyword(@PathVariable String keyword){
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
