package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	@GetMapping("/request")
	public List<MarketData> request() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<MarketData> md = null;
		WebClient webClient = WebClient.builder().baseUrl("https://exchange.matraining.com").defaultHeader("Content-Type", "application/json").build();
		try {
			md = objectMapper.readValue(webClient.get().uri("/md").retrieve().bodyToMono(String.class).block(), new TypeReference<List<MarketData>>() {});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(md);
		return md;
	}

	@GetMapping("/req")
	public MarketData[] req() {
		MarketData[] md = null;

		WebClient webClient = WebClient.builder().baseUrl("https://exchange.matraining.com").defaultHeader("Content-Type", "application/json").build();
		md = webClient.get().uri("/md").retrieve().bodyToMono(MarketData[].class).block();

		System.out.println(md);
		return md;
	}
	@GetMapping("/req2")
	public Mono<MarketData[]> req2() {
		Mono<MarketData[]> md = null;

		WebClient webClient = WebClient.builder().baseUrl("https://exchange.matraining.com").defaultHeader("Content-Type", "application/json").build();
		md = webClient.get().uri("/md").retrieve().bodyToMono(MarketData[].class);

		System.out.println(md);
		return md;
	}

	@GetMapping("/jackson")
	public Person jackson() {
		ObjectMapper objectMapper = new ObjectMapper();
		Person person = null;
		String data = "{\"name\":\"Francis\",\"age\":12}";
		try {
			person = objectMapper.readValue(data, Person.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(person);
		return person;
	}

}

//