package com.nnk.springboot.domain;

import javax.persistence.*;

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
    
    String username;
    
    String password;
    
    String fullname;
    
    String role;

    public Boolean isAdmin() {
    	if(role == "ADMIN") {
    		return true;
    	}else{
    		return false;
    	}
    }

}
