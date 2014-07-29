package com.bluemix.bankacct.front;

import java.io.Serializable;
/*
 * Licensed Materials - Property of IBM
 * 
 * @ Copyright IBM Corp. 2013 All Rights Reserved
 *
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP 
 * Schedule Contract with IBM Corp.
 *
 */
import java.text.NumberFormat;

public class CustomerAcct implements Serializable {
	
	private static final long serialVersionUID= 2716055670746597905L;

	private int customerAcct; // this variable belongs to the primary key
	private String customerName;
	private double customerMoney;

	public CustomerAcct(){
		
	}
	
	public CustomerAcct(int customerAcct,String customerName,double customerMoney){
		this.customerAcct = customerAcct;
		this.customerName = customerName;
		this.customerMoney = customerMoney;
	}
	
	public int getCustomerAcct() {
		return customerAcct;
	}

	public void setCustomerAcct(int customerAcct) {
		this.customerAcct = customerAcct;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getCustomerMoney() {
		return customerMoney;
	}

	public void setCustomerMoney(double customerMoney) {
		this.customerMoney = customerMoney;
	}
	
	public void incrementAcct(){
		customerAcct += 1;
	}
	
	public String toString(){
		
		return "ID: " + customerAcct + " Name: " + customerName + " Balance: " +  NumberFormat.getCurrencyInstance().format(customerMoney);
	}
	}


