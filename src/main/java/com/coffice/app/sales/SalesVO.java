package com.coffice.app.sales;

import java.sql.Date;

import com.coffice.app.ingredients.IngredientsVO;

import lombok.Data;

@Data
public class SalesVO {

	private Integer salesId;
	private boolean salesType;
	private Integer salesProfit;
	private Date salesDate;
	private Integer salesQuantity;
	private Integer branchId;
	private Integer menuId;
	private Integer ingredientsId;
	private Integer totalSale;
	private MenuVO menuVO;
}
