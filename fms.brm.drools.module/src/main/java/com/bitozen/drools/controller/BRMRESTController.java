package com.bitozen.drools.controller;

import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bitozen.drools.config.DroolsBeanFactory;
import com.bitozen.drools.model.Customer;
import com.bitozen.drools.model.Customer.CustomerType;
import com.bitozen.drools.service.ProductService;
import com.bitozen.drools.model.DiscountInputDTO;
import com.bitozen.drools.model.Product;
import com.bitozen.drools.model.ProductInputDTO;

@RestController
public class BRMRESTController {

	private KieSession kSession;
	
	private ProductService productService;
	
	//Test using Discount.xls
	@RequestMapping(
			value = "/get.brm.discount", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String getBRMDiscount(@RequestBody DiscountInputDTO dto) {
		//Setup
		Resource resource = ResourceFactory.newClassPathResource("com/bitozen/drools/rules/Discount.xls", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
        
        //Get Discount
		Customer customer = new Customer(dto.getType(), dto.getYears());
        kSession.insert(customer);

        kSession.fireAllRules();
        
        return "Diskon yang diterima customer: " + customer.getDiscount();
	}
	
	//
	@RequestMapping(
			value = "/get.brm.product", 
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String getBRPMProduct(@RequestBody ProductInputDTO dto) {
		//Setup
		productService = new ProductService();
		
		//Get Label
		Product product = new Product(dto.getName(), dto.getType());
        product = productService.applyLabelToProduct(product);
        return "Label: " + product.getLabel();
	}
	
}
