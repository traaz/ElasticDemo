package com.example.demo;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserElasticRepository extends ElasticsearchRepository<User, Integer> {


    @Query("""
    {
      "match": {
        "userName": "?0"
      }
    }
""")
    Iterable<User> getUserFromName(String name);

    @Query("""
          {
            "match_phrase_prefix" : {
                "userName" : "?0"
            }
          }
          """)
    Iterable<User> getUserStartsWithKeyword(String keyword);
}
