package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Moodys is mandatory")
	private String moodysRating;

	@NotBlank(message = "Sandp is mandatory")
	private String sandpRating;

	@NotBlank(message = "Fitch is mandatory")
	private String fitchRating;

	@NotNull(message = "Order is mandatory")
	private Integer orderNumber;

}
