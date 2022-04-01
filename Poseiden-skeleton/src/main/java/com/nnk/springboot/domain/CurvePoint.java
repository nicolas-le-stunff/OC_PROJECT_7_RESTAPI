package com.nnk.springboot.domain;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {


	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;


	  private Integer curveId;

	  private Timestamp asOfDate;

	  private double term;

	  private double value;

	  private Timestamp creationDate;

}
