package org.archisoft.test;

import java.util.HashMap;
import java.util.Map;

public class NavigationBean {

	private String arrow = "";
	private String categoryId = "";
	private String categoryName = "";
	private String productId = "";
	private String brandId = "";
	private String supplierId = "";
	private Map<String, Integer> favoritesMap;
	private Map<String, Integer> searchTypeMap;
	private Map<String, String> searchListMap;
	private Map<String, String> compareListMap;
	public NavigationBean() {
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getArrow() {
		return arrow;
	}

	public void setArrow(String arrow) {
		this.arrow = arrow;
	}

	public Map<String, Integer> getFavoritesMap() {
		if (favoritesMap == null) {
			favoritesMap = new HashMap<String, Integer>();
		}
		return favoritesMap;
	}

	public void setFavoritesMap(Map<String, Integer> favoritesMap) {
		this.favoritesMap = favoritesMap;
	}

	public Map<String, Integer> getSearchTypeMap() {
		if (searchTypeMap == null) {
			searchTypeMap = new HashMap<String, Integer>();
		}
		return searchTypeMap;
	}

	public void setSearchTypeMap(Map<String, Integer> searchTypeMap) {
		this.searchTypeMap = searchTypeMap;
	}

	public Map<String, String> getSearchListMap() {
		if (searchListMap == null) {
			searchListMap = new HashMap<String, String>();
		}
		return searchListMap;
	}

	public void setSearchListMap(Map<String, String> searchListMap) {
		this.searchListMap = searchListMap;
	}

	public Map<String, String> getCompareListMap() {
		if (compareListMap == null) {
			compareListMap = new HashMap<String, String>();
		}
		return compareListMap;
	}

	public void setCompareListMap(Map<String, String> compareListMap) {
		this.compareListMap = compareListMap;
	}

}
