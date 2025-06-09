package com.coffice.app.ingredients;

import java.sql.Date;

import lombok.Data;

@Data
public class IngredientsVO {

	private Integer ingredientsID;
	private String ingredientsName;
	private Integer ingredientsStock;
	private Integer ingredientsPrice;
	private Date ingredientsDate;
	private Integer historyId;
}
