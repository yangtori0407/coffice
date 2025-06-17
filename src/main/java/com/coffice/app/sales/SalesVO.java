package com.coffice.app.sales;

import java.sql.Date;

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
	private Integer totalSale;
	private MenuVO menuVO;
}
