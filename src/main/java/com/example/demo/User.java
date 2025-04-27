package com.example.demo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int id;
    @Field(type = FieldType.Text, name = "userName")
    private String name;
    @Field(type = FieldType.Text, name = "userSurname")
    private String surname;
    @Field(type = FieldType.Integer, name = "userAge")
    private int age;



}
