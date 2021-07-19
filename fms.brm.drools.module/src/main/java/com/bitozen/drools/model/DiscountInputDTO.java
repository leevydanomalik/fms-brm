package com.bitozen.drools.model;

import com.bitozen.drools.model.Customer.CustomerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiscountInputDTO {

	private CustomerType type;
	private int years;
}
