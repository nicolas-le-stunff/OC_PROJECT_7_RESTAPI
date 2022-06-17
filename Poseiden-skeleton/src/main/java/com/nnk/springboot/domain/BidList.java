package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@Data
@Entity
@Table(name = "bidlist")
public class BidList {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;

	  @NotBlank(message = "Account is mandatory")
	  private String account;
	  
	  @NotBlank(message = "Type is mandatory")
	  private String type;

	  private double bidQuantity;

	  private double askQuantity;

	  private double bid;

	  private double ask;

	  private String benchmark;

	  private Timestamp bidListDate;

	  private String commentary;

	  private String security;

	  private String status;

	  private String trader;

	  private String book;

	  private String creationName;

	  private Timestamp creationDate;

	  private String revisionName;

	  private Timestamp revisionDate;

	  private String dealName;

	  private String dealType;

	  private String sourceId;

	  private String side;

}
