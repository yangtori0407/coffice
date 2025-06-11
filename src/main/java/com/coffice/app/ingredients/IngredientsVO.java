package com.coffice.app.ingredients;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class IngredientsVO {

	private Integer ingredientsID;
	@NotEmpty(message = "재료를입력하세요")
	private String ingredientsName;
	private Integer ingredientsStock;
	private Integer ingredientsPrice;
	private Date ingredientsDate;
	private List<History> history;
}
