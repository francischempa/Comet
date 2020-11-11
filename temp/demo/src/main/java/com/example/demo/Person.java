package com.example.demo;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Person {
    private String name;
    private int age;
    public Person(){}
    public Person(String name,int age){
        this.age=age;
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
//    bodyToMono(new ParameterizedTypeReference<List<AccountOrder>>() {})
//https://stackoverflow.com/questions/48598233/deserialize-a-json-array-to-objects-using-jackson-and-webclient