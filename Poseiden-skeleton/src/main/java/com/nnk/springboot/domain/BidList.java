package com.nnk.springboot.domain;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "bidlist")
public class BidList {

	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;

	  private String account;
	  
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
