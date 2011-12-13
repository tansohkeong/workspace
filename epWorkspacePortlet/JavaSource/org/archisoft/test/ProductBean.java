package org.archisoft.test;

import java.util.List;

public class ProductBean {

	private boolean checked;
	private String productId;
	private String productCode;
	private String productName;
	private String categoryId;
	private String brandId;
	private String brandName;
	private String supplierId;
	private String supplierName;
	private Double unitPrice;
	private int qtyOrder;
	private Double total;
	private String img;
	private String uomId;
	private String remarks;
	private boolean compare;
	private List<ListingBean> specList;
	private List<String> galleryList;

	public ProductBean() {
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQtyOrder() {
		return qtyOrder;
	}

	public void setQtyOrder(int qtyOrder) {
		this.qtyOrder = qtyOrder;
	}

	public Double getTotal() {
		total = qtyOrder * unitPrice;
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<ListingBean> getSpecList() {
		return specList;
	}

	public void setSpecList(List<ListingBean> specList) {
		this.specList = specList;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUomId() {
		return uomId;
	}

	public void setUomId(String uomId) {
		this.uomId = uomId;
	}

	public boolean isCompare() {
		return compare;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setCompare(boolean compare) {
		this.compare = compare;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<String> getGalleryList() {
		return galleryList;
	}

	public void setGalleryList(List<String> galleryList) {
		this.galleryList = galleryList;
	}

}
