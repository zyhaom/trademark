package com.vo;

import java.io.Serializable;

public class PicPropVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String productName = "";// 图片代表的产品名称
	private String brandName = "";// '图片代表的品牌名称',
	private String companyName = "";// '图片代表的产品公司名称',
	private String originAddr = "";// '图片代表的产品的出厂地',
	private String originTime = "";// '图片代表的产品的出厂时间',
	private float price = 0.0f;// 图片代表的产品的价格

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOriginAddr() {
		return originAddr;
	}

	public void setOriginAddr(String originAddr) {
		this.originAddr = originAddr;
	}

	public String getOriginTime() {
		return originTime;
	}

	public void setOriginTime(String originTime) {
		this.originTime = originTime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "PicPropVO [productName=" + productName + ", brandName=" + brandName + ", companyName=" + companyName + ", originAddr=" + originAddr + ", originTime=" + originTime + ", price=" + price
				+ "]";
	}

}
