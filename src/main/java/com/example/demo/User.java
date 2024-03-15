package com.example.demo;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class User {

	@Id
	String id;
	String userDetails;
}
