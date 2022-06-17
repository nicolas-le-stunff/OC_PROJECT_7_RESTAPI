package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {


	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;

	  @NotNull(message = "must not be null")
	  private Integer curveId;

	  private Timestamp asOfDate;

	  private double term;

	  private double value;

	  private Timestamp creationDate;

}
