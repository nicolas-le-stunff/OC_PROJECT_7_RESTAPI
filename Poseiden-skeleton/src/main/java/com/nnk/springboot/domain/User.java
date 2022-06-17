package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

	@NotBlank(message = "UserName is mandatory")
    String username;
    
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "The password must contain at least one capital letter, one number, one symbol and be at least 8 characters long")
    String password;

	@NotBlank(message = "FullName is mandatory")
    String fullname;
    
    String role;

}
