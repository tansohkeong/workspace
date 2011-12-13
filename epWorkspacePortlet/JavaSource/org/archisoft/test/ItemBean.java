package org.archisoft.test;

import java.util.Date;

public class ItemBean implements Cloneable {

	private boolean checked;
	private String no;
	private String contractNo;
	private String selectProduct;
	private String productName;
	private String productDescription;
	private String productSpecification;
	private String supplierId;
	private String supplierName;
	private String supplierAddress;
	private String contactPersonName;
	private String phoneNumber;
	private String manufacturer;
	private String manufacturerPartNo;
	private String ePProductNo;
	private String supplierSKU;
	private String unitOfMeasurement;
	private Double unitPrice;
	private int minimumOrderQuantity;
	private String countryOfOrigin;
	private String deliveryPeriod;
	private Date edDate;
	private String expectedTimeFrom;
	private String expectedTimeTo;
	private String deliveryAddress;
	private String deliveryAddressName;
	private String address;
	private Double zonePrice;
	private int orderQuantity;
	private Double total;
	private boolean othersAddress;

	public ItemBean() {
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getSelectProduct() {
		return selectProduct;
	}

	public void setSelectProduct(String selectProduct) {
		this.selectProduct = selectProduct;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturerPartNo() {
		return manufacturerPartNo;
	}

	public void setManufacturerPartNo(String manufacturerPartNo) {
		this.manufacturerPartNo = manufacturerPartNo;
	}

	public String getePProductNo() {
		return ePProductNo;
	}

	public void setePProductNo(String ePProductNo) {
		this.ePProductNo = ePProductNo;
	}

	public String getSupplierSKU() {
		return supplierSKU;
	}

	public void setSupplierSKU(String supplierSKU) {
		this.supplierSKU = supplierSKU;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getMinimumOrderQuantity() {
		return minimumOrderQuantity;
	}

	public void setMinimumOrderQuantity(int minimumOrderQuantity) {
		this.minimumOrderQuantity = minimumOrderQuantity;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getExpectedTimeFrom() {
		return expectedTimeFrom;
	}

	public void setExpectedTimeFrom(String expectedTimeFrom) {
		this.expectedTimeFrom = expectedTimeFrom;
	}

	public String getExpectedTimeTo() {
		return expectedTimeTo;
	}

	public void setExpectedTimeTo(String expectedTimeTo) {
		this.expectedTimeTo = expectedTimeTo;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryAddressName() {
		return deliveryAddressName;
	}

	public void setDeliveryAddressName(String deliveryAddressName) {
		this.deliveryAddressName = deliveryAddressName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getZonePrice() {
		return zonePrice;
	}

	public void setZonePrice(Double zonePrice) {
		this.zonePrice = zonePrice;
	}

	public int getOrderQuantity() {
		total = orderQuantity * unitPrice;
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		total = orderQuantity * unitPrice;
		this.total = total;
	}

	public boolean isOthersAddress() {
		return othersAddress;
	}

	public void setOthersAddress(boolean othersAddress) {
		this.othersAddress = othersAddress;
	}

	public String getDeliveryPeriod() {
		return deliveryPeriod;
	}

	public void setDeliveryPeriod(String deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
	
	public Date getEdDate() {
		return edDate;
	}

	public void setEdDate(Date edDate) {
		this.edDate = edDate;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
