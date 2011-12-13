package org.archisoft.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.Dropzone;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

import com.cdc.ep.service.client.Brand;
import com.cdc.ep.service.client.Category;
import com.cdc.ep.service.client.Favorites;
import com.cdc.ep.service.client.Itemspec;
import com.cdc.ep.service.client.ProductFilter;
import com.cdc.ep.service.client.Supplier;

public class ProductImpl extends Principal implements DropListener {

	public static final String CATALOGUE_FIRST_UI = "catalogueFirstUI";
	public static final String CATALOGUE_UI = "catalogueUI";
	public static final String PRODUCT_LIST_UI = "productListUI";
	public static final String PRODUCT_UI = "productUI";
	public static final String FAVORITES_MGT_UI = "favoritesMgtUI";
	public static final String SHOPPING_CART_UI = "shoppingCartUI";
	public static final String COMPARE_UI = "compareUI";
	public static final String SHOPS_GOF_UI = "shopsGOFUI";

	BaseUtil baseUtil = new BaseUtil();
	BpmUtil bpmUtil = new BpmUtil();
	ServiceUtil serviceUtil = new ServiceUtil();
	SearchUtil searchUtil = new SearchUtil();
	FaceMessages faceMessages = new FaceMessages();

	private ProductBean product;
	private NavigationBean navigationBean;
	private RequisitionBean requisition;

	private List<ProductBean> compareList;
	private List<Itemspec> compareTitleList;
	private List<Brand> brandList;
	private List<Supplier> supplierList;
	private List<ListingBean> favoritesMgtList;
	private List<FavoritesBean> favoritesProductList;
	private List<FavoritesBean> favoritesSupplierList;
	private List<Category> categoryList;
	// private List<Category> categoryFirstList;
	private List<ProductBean> productList;
	private List<ProductBean> relatedProductList;
	private List<ProductBean> shoppingCartList;
	private List<NavigationBean> navigationList;
	private List<String> ministrycode;
	private List<String> jabatancode;
	private List<String> ptjcode;
	private List<String> searchTypeList;
	private List<String> searchList;
	private List<String> favoritesList;
	private List<String> deliveryAddressList;

	private String favoritesProductHeight;
	private String favoritesSupplierHeight;
	private String favoritesActionType;
	private String favoritesTypeValue;
	private String searchTypeValue;
	private String searchListValue;
	private String outcome;
	private String compareProductId;

	private int defaultSize = 12;
	private int qtyOrder;
	private int defaultQtyOrder = 1;

	private double fromValue;
	private double toValue;
	private double totalPurchase;

	private Map<String, String> cartMap;
	private Map<String, String> favoritesMap;

	private boolean checkAll = false;
	private boolean showProductLists = false;
	private boolean firstPage = true;

	String[] searchType = { "Category", "Brand", "Supplier", "Product" };
	String[] favoritesType = { "Add Product To Favorites",
			"Add Company To Favorites" };

	public ProductImpl() {
		super();
	}

	public boolean isFirstPage() {
		System.out.println("############ firstPage() ##############");
		System.out.println("############ first[" + firstPage
				+ "] ##############");
		if (firstPage) {
			favoritesMap = null;
			favoritesProductList = null;
			favoritesSupplierList = null;
			cleanSearchList();
			cleanCompareList();
			if (navigationBean == null) {
				navigationBean = new NavigationBean();
			}
			navigationBean.setCategoryId("0");
			navigationBean.setSupplierId("");
			categoryList = null;
			setFirstPage(false);
		}
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public String getContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestContextPath();
	}

	// public String navList() {
	// return CATALOGUE_FIRST_UI;
	// }

	public String navCartBar() {
		return SHOPPING_CART_UI;
	}

	public String navProductList() {
		productList = null;
		showProductLists = true;
		return PRODUCT_LIST_UI;
	}

	public String navCompare() {
		return COMPARE_UI;
	}

	public String navProduct() {
		product = null;
		showProductLists = true;
		return PRODUCT_UI;
	}

	public String cleanList() {
		brandList = null;
		supplierList = null;
		productList = null;
		navigationList = null;
		navigationBean.setProductId("");
		showProductLists = true;
		return PRODUCT_LIST_UI;
	}

	public String cleanProduct() {
		product = null;
		relatedProductList = null;
		navigationList = null;
		return PRODUCT_UI;
	}

	public void cleanSearchList() {
		searchList = null;
		searchListValue = "";
	}

	public String navCategory() {
		showProductLists = false;
		navigationList = null;
		return CATALOGUE_UI;
	}

	public void cleanCompareList() {
		productList = null;
		compareList = null;
		if (navigationBean != null) {
			navigationBean.getCompareListMap().clear();
		}
	}

	public void clearBean() {
		shoppingCartList = null;
		totalPurchase = 0;
		shoppingCartList = null;
		navigationList = null;
		cartMap = new HashMap<String, String>();
	}

	public String refine() {
		navigationList = null;

		if (fromValue == 0) {
			faceMessages
					.setValidationMessage(
							messageManager
									.getMessage("error.validation.from.value.must.more.then.zero"),
							FacesMessage.SEVERITY_ERROR);
		} else if (toValue < fromValue) {
			faceMessages
					.setValidationMessage(
							messageManager
									.getMessage("error.validation.to.value.must.more.then.from.value"),
							FacesMessage.SEVERITY_ERROR);
		}
		return cleanList();
	}

	public String reset() {
		toValue = 0;
		fromValue = 0;
		return cleanList();
	}

	public String cleanFavorites() {
		showProductLists = true;
		return PRODUCT_LIST_UI;
	}

	public List<ProductBean> getRelatedProductList() {

		String productId = navigationBean.getProductId();
		if (relatedProductList == null) {
			relatedProductList = new ArrayList<ProductBean>();

			relatedProductList = baseUtil
					.getRelatedProductListByProductId(productId);
			int balance = 0;
			int totalSize = 0;
			if (relatedProductList.size() > 4) {
				totalSize = relatedProductList.size() / 4;
			} else {
				totalSize = relatedProductList.size();
			}
			if (totalSize > 0) {
				balance = 4 - totalSize;
				if (balance != 0 || balance != 4) {
					for (int i = 0; i < balance; i++) {
						ProductBean productBean = new ProductBean();
						productBean.setProductName("");
						relatedProductList.add(productBean);
					}
				}
			}

		}

		return relatedProductList;
	}

	//
	// public List<Category> getCategoryFirstList() {
	// favoritesMap = new HashMap<String, String>();
	// favoritesProductList = null;
	// favoritesSupplierList = null;
	// if (categoryFirstList == null) {
	// if (navigationBean == null) {
	// clearBean();
	// }
	// cleanSearchList();
	// cleanCompareList();
	//
	// categoryFirstList = new ArrayList<Category>();
	// categoryFirstList = baseUtil.getCategoryListBySubCategoryId(0);
	// if (categoryFirstList.size() < 4 && categoryList.size() != 0) {
	// for (int i = 0; i < 5 - categoryList.size(); i++) {
	// Category bean = new Category();
	// bean.setCategoryName("");
	// categoryFirstList.add(bean);
	// }
	// }
	// getNavigationList();
	// }
	// return categoryFirstList;
	// }

	public List<Category> getCategoryList() {
		try {

			if (categoryList == null) {
				if (navigationBean == null) {
					clearBean();
				}

				cleanSearchList();
				cleanCompareList();
				categoryList = new ArrayList<Category>();
				navigationList = null;
				if (navigationBean == null) {
					navigationBean = new NavigationBean();
					navigationBean.setCategoryId("0");
				}
				int subCategoryId = Integer.parseInt(navigationBean
						.getCategoryId());

				categoryList = baseUtil
						.getCategoryListBySubCategoryId(subCategoryId);

				if (categoryList.size() < 4 && categoryList.size() != 0) {
					for (int i = 0; i < 5 - categoryList.size(); i++) {
						Category bean = new Category();
						bean.setCategoryName("");
						categoryList.add(bean);
					}
				}
				getNavigationList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	public List<ProductBean> getProductList() {
		if (productList == null) {
			productList = new ArrayList<ProductBean>();
			ProductFilter productFilterBean = new ProductFilter();
			productFilterBean.setProductId(navigationBean.getProductId());
			productFilterBean.setBrandId(navigationBean.getBrandId());
			productFilterBean.setSupplierId(navigationBean.getSupplierId());
			productFilterBean.setCategoryId(navigationBean.getCategoryId());
			productFilterBean.setFromValue(fromValue);
			productFilterBean.setToValue(toValue);
			productList = baseUtil.getProductList(productFilterBean,
					navigationBean.getCompareListMap());
		}
		return productList;
	}

	public List<Brand> getBrandList() {
		if (brandList == null) {
			brandList = new ArrayList<Brand>();
			HashMap<String, String> brandMap = new HashMap<String, String>();
			getProductList();
			for (int i = 0; i < productList.size(); i++) {
				ProductBean product = productList.get(i);
				Brand brandBean = new Brand();
				brandBean.setBrandId(product.getBrandId());
				brandBean.setBrandName(product.getBrandName());
				String brand = brandBean.getBrandName();

				// Brand
				if (!brandMap.containsKey(brand)) {
					brandList.add(brandBean);
					brandMap.put(brand, brand);
				}
			}
		}
		return brandList;
	}

	public List<Supplier> getSupplierList() {
		if (supplierList == null) {
			supplierList = new ArrayList<Supplier>();
			HashMap<String, String> shopMap = new HashMap<String, String>();

			for (int i = 0; i < productList.size(); i++) {
				ProductBean product = productList.get(i);
				Supplier supplierBean = new Supplier();
				supplierBean.setSupplierId(product.getSupplierId());
				supplierBean.setSupplierName(product.getSupplierName());
				String shop = supplierBean.getSupplierName();

				// Store
				if (!shopMap.containsKey(shop)) {

					supplierList.add(supplierBean);
					shopMap.put(shop, shop);
				}
			}
		}
		return supplierList;
	}

	public ProductBean getProduct() {
		String productId = navigationBean.getProductId();
		if (product == null) {
			product = new ProductBean();
			ProductFilter productFilterBean = new ProductFilter();
			productFilterBean.setProductId(productId);
			product = baseUtil.getProduct(productFilterBean);
			// product.setSpecList(baseUtil.getProductSpecDetailsList(product
			// .getProductId()));
		}
		return product;
	}

	public List<ListingBean> getFavoritesMgtList() {

		favoritesMgtList = new ArrayList<ListingBean>();

		favoritesProductList = baseUtil.getFavoritesByFilter("Product");
		for (int i = 0; i < favoritesProductList.size(); i++) {
			FavoritesBean favoritesBean = favoritesProductList.get(i);
			ListingBean bean = new ListingBean();
			bean.setChecked(checkAll);
			bean.setId(Integer.toString(favoritesBean.getFavoritesId()));
			bean.setType(favoritesBean.getType());
			bean.setCode(favoritesBean.getProductId());
			bean.setName(favoritesBean.getProductName());
			favoritesMgtList.add(bean);
		}
		favoritesSupplierList = baseUtil.getFavoritesByFilter("Supplier");
		for (int i = 0; i < favoritesSupplierList.size(); i++) {
			FavoritesBean favoritesBean = favoritesSupplierList.get(i);
			ListingBean bean = new ListingBean();
			bean.setChecked(checkAll);
			bean.setId(Integer.toString(favoritesBean.getFavoritesId()));
			bean.setType(favoritesBean.getType());
			bean.setCode(favoritesBean.getSupplierId());
			bean.setName(favoritesBean.getSupplierName());
			favoritesMgtList.add(bean);
		}
		return favoritesMgtList;
	}

	public List<FavoritesBean> getFavoritesProductList() {
		if (favoritesProductList == null) {

			favoritesProductList = new ArrayList<FavoritesBean>();
			favoritesProductList = baseUtil.getFavoritesByFilter("Product");

			if (favoritesMap == null) {
				favoritesMap = new HashMap<String, String>();
			}

			for (int i = 0; i < favoritesProductList.size(); i++) {
				FavoritesBean favoritesBean = favoritesProductList.get(i);
				String keys = "Product" + "_" + favoritesBean.getProductId();
				if (!favoritesMap.containsKey(keys)) {
					favoritesMap.put(keys,
							Integer.toString(favoritesBean.getFavoritesId()));
				}
			}
		}
		return favoritesProductList;
	}

	public List<FavoritesBean> getFavoritesSupplierList() {
		if (favoritesSupplierList == null) {

			favoritesSupplierList = new ArrayList<FavoritesBean>();
			favoritesSupplierList = baseUtil.getFavoritesByFilter("Supplier");

			if (favoritesMap == null) {
				favoritesMap = new HashMap<String, String>();
			}

			for (int i = 0; i < favoritesSupplierList.size(); i++) {
				FavoritesBean favoritesBean = favoritesSupplierList.get(i);
				String keys = "Supplier" + "_" + favoritesBean.getSupplierId();
				if (!favoritesMap.containsKey(keys)) {
					favoritesMap.put(keys,
							Integer.toString(favoritesBean.getFavoritesId()));
				}
			}
		}
		return favoritesSupplierList;
	}

	public List<String> getFavoritesList() {
		favoritesList = new ArrayList<String>();
		int l = favoritesType.length;
		for (int i = 0; i < l; i++) {
			navigationBean.getFavoritesMap().put(favoritesType[i], i + 1);
			favoritesList.add(favoritesType[i]);
		}
		return favoritesList;
	}

	public void changeFavoritesValue(ValueChangeEvent e) {
		favoritesTypeValue = e.getNewValue().toString();
	}

	public void changeComparList() {
		if (compareList == null) {
			compareList = new ArrayList<ProductBean>();
		}
		if (compareProductId != null) {
			ProductBean sBean = new ProductBean();
			for (int i = 0; i < productList.size(); i++) {
				ProductBean bean = productList.get(i);
				if (bean.getProductId().equalsIgnoreCase(compareProductId)) {
					sBean = bean;
					i = productList.size();
				}
			}

			if (sBean.isCompare() == true) {
				List<ListingBean> specList = baseUtil
						.getProductSpecDetailsList(sBean.getProductId());
				if (specList.size() > 0) {
					sBean.setSpecList(specList);
				}
				compareList.add(sBean);
				if (!navigationBean.getCompareListMap().containsKey(
						sBean.getProductId())) {
					navigationBean.getCompareListMap().put(
							sBean.getProductId(), sBean.getProductId());
				}
			} else {
				setCompareProductId(sBean.getProductId());
				removeCompareItem();
			}
		}
	}

	public String FavoritesAction() {
		if (favoritesTypeValue.equalsIgnoreCase("")) {
			faceMessages.setValidationMessage(messageManager
					.getMessage("error.validation.select.favorites.value"),
					FacesMessage.SEVERITY_ERROR);
		}
		int sValue = 0;
		if (navigationBean.getFavoritesMap().containsKey(favoritesTypeValue)) {
			sValue = navigationBean.getFavoritesMap().get(favoritesTypeValue);
		}
		String sType = "";
		String sTypeValue = "";
		if (product.getProductId() != null) {
			if (sValue == 1) {
				sType = "Product";
				sTypeValue = product.getProductId();
			} else {
				sType = "Supplier";
				sTypeValue = product.getSupplierId();
			}
		}
		String keys = sType + "_" + sTypeValue;
		if (!favoritesMap.containsKey(keys)) {
			Favorites favorites = new Favorites();
			favorites.setActive(true);
			favorites.setType(sType);
			if (sValue == 1) {
				favorites.setProductId(product.getProductId());
				favorites.setSupplierId("");
				baseUtil.FavoritesAction(favorites, "SAVE");
				favoritesProductList = null;
			} else if (sValue == 2) {
				favorites.setSupplierId(product.getSupplierId());
				favorites.setProductId("");
				baseUtil.FavoritesAction(favorites, "SAVE");
				favoritesSupplierList = null;
			}
			faceMessages.setValidationMessage(messageManager
					.getMessage("infor.validation.save.successful"),
					FacesMessage.SEVERITY_INFO);
		} else {
			faceMessages.setValidationMessage(
					messageManager.getMessage("infor.validation.record.exits"),
					FacesMessage.SEVERITY_INFO);
		}

		return FAVORITES_MGT_UI;
	}

	public String getFavoritesTypeValue() {
		return favoritesTypeValue;
	}

	public void setFavoritesTypeValue(String favoritesTypeValue) {
		this.favoritesTypeValue = favoritesTypeValue;
	}

	public String deleteFavoritesCart() {

		for (int i = 0; i < favoritesMgtList.size(); i++) {
			ListingBean bean = (ListingBean) favoritesMgtList.get(i);
			if (bean.isChecked() == true) {
				Favorites favorites = new Favorites();
				favorites.setId(Integer.parseInt(bean.getId()));
				favorites.setActive(false);
				baseUtil.FavoritesAction(favorites, "DELETE");
				favoritesSupplierList = null;
				favoritesProductList = null;
				String keys = bean.getType() + "_" + bean.getCode();
				favoritesMap.remove(keys);
			}
		}
		checkAll = false;
		return FAVORITES_MGT_UI;
	}

	public String addFavoritesProductToCart() {
		for (int i = 0; i < favoritesMgtList.size(); i++) {
			ListingBean bean = (ListingBean) favoritesMgtList.get(i);
			if (bean.isChecked() == true
					&& bean.getType().equalsIgnoreCase("Supplier")) {
				faceMessages
						.setValidationMessage(
								messageManager
										.getMessage("error.validation.supplier.cannot.add.to.shopping.cart"),
								FacesMessage.SEVERITY_ERROR);
				return FAVORITES_MGT_UI;
			}
		}

		for (int i = 0; i < favoritesMgtList.size(); i++) {
			ListingBean bean = (ListingBean) favoritesMgtList.get(i);
			if (bean.isChecked() == true) {
				addProductToCart(bean.getCode());
			}
		}
		checkAll = false;
		return SHOPPING_CART_UI;
	}

	public String favoritesMgtUI() {
		return FAVORITES_MGT_UI;
	}

	public List<ProductBean> getCompareList() {
		if (compareList == null) {
			compareList = new ArrayList<ProductBean>();
		}
		return compareList;
	}

	public void setCompareList(List<ProductBean> compareList) {
		this.compareList = compareList;
	}

	public List<Itemspec> getCompareTitleList() {
		if (compareTitleList == null) {
			compareTitleList = new ArrayList<Itemspec>();
		}
		String sValue = navigationBean.getCategoryId();
		if (!sValue.equalsIgnoreCase("")) {
			int categoryId = Integer.parseInt(sValue);
			compareTitleList = baseUtil.getItemSpecListByCategoryId(categoryId);
		}

		return compareTitleList;
	}

	public String removeCompareList() {
		productList = null;
		return COMPARE_UI;
	}

	public void removeCompareItem() {
		List<ProductBean> newCompareList = new ArrayList<ProductBean>();
		for (int i = 0; i < compareList.size(); i++) {
			ProductBean bean = compareList.get(i);
			if (!bean.getProductId().equalsIgnoreCase(compareProductId)) {
				newCompareList.add(bean);
			}
		}
		navigationBean.getCompareListMap().remove(compareProductId);
		compareList = newCompareList;
	}

	public String checkProduct() {
		product = null;
		return PRODUCT_UI;
	}

	public Map<String, String> getFavoritesMap() {
		if (favoritesMap == null) {
			favoritesMap = new HashMap<String, String>();
		}
		return favoritesMap;
	}

	public void setFavoritesMap(Map<String, String> favoritesMap) {
		this.favoritesMap = favoritesMap;
	}

	public String checkCategory() {
		List<Category> resultList = new ArrayList<Category>();
		try {
			String categoryId = navigationBean.getCategoryId();

			if (Integer.parseInt(categoryId) > 0) {
				resultList = baseUtil.getCategoryListBySubCategoryId(Integer
						.parseInt(categoryId));
			}
			navigationList = null;
			getNavigationList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resultList.size() > 0) {
			categoryList = null;
			getCategoryList();
			showProductLists = false;
			return CATALOGUE_UI;
		} else {
			return cleanList();
		}
	}

	public List<NavigationBean> getNavigationList() {

		String categoryId = "0";
		if (navigationList == null) {
			navigationList = new ArrayList<NavigationBean>();
			if (navigationBean != null) {
				categoryId = navigationBean.getCategoryId();
			}
			if (categoryId != null && !categoryId.equalsIgnoreCase("")) {
				int categoryIdNew = Integer.parseInt(categoryId);
				List<NavigationBean> resultList = baseUtil
						.getNavigationCategoryId(categoryIdNew);
				if (resultList.size() > 0) {
					navigationList = resultList;
				}
			}
		}
		return navigationList;
	}

	public void updateCategoryParam(ActionEvent evt) {
		String categoryId = (String) evt.getComponent().getAttributes()
				.get("categoryId").toString();
		if (navigationBean == null) {
			navigationBean = new NavigationBean();
		}
		navigationBean.setCategoryId(categoryId == null ? "" : categoryId);
	}

	public void updateParam(ActionEvent evt) {
		String paramType = (String) evt.getComponent().getAttributes()
				.get("paramType");
		String productId = (String) evt.getComponent().getAttributes()
				.get("productId");
		String categoryId = (String) evt.getComponent().getAttributes()
				.get("categoryId");
		String supplierId = (String) evt.getComponent().getAttributes()
				.get("supplierId");
		String brandId = (String) evt.getComponent().getAttributes()
				.get("brandId");
		String compareProductId = (String) evt.getComponent().getAttributes()
				.get("compareProductId");
		String outcome = (String) evt.getComponent().getAttributes()
				.get("outcome");

		if (navigationBean == null) {
			navigationBean = new NavigationBean();
		}

		if (product == null) {
			product = new ProductBean();
		}
		if (serviceUtil.CheckValidValue(1, paramType)) {
			product.setProductId(productId == null ? "" : productId);
			navigationBean.setProductId(productId == null ? "" : productId);
			if (product == null) {
				product = new ProductBean();
			}
		}
		if (serviceUtil.CheckValidValue(2, paramType)) {
			navigationBean.setCategoryId(categoryId == null ? "" : categoryId);
		}
		if (serviceUtil.CheckValidValue(3, paramType)) {
			navigationBean.setSupplierId(supplierId == null ? "" : supplierId);
		}
		if (serviceUtil.CheckValidValue(4, paramType)) {
			navigationBean.setBrandId(brandId == null ? "" : brandId);
		}
		if (serviceUtil.CheckValidValue(5, paramType)) {
			setCompareProductId(compareProductId == null ? ""
					: compareProductId);
		}
		if (serviceUtil.CheckValidValue(6, paramType)) {
			this.setOutcome(outcome == null ? "" : outcome);
		}
	}

	public void updateOrderQty(ValueChangeEvent e) {
		qtyOrder = Integer.parseInt(e.getNewValue().toString());
	}

	public String addToCart() {
		String productSelected = navigationBean.getProductId();
		productSelected = productSelected == null ? "" : productSelected;
		addProductToCart(productSelected);
		return SHOPPING_CART_UI;
	}

	public void addProductToCart(String productId) {
		if (qtyOrder == 0) {
			qtyOrder = 1;
		}
		if (shoppingCartList == null) {
			shoppingCartList = new ArrayList<ProductBean>();
			totalPurchase = 0;
		}
		if (cartMap == null) {
			cartMap = new HashMap<String, String>();
		}
		if (!productId.equals("")) {
			if (cartMap.containsKey(productId)) {
				for (int i = 0; i < shoppingCartList.size(); i++) {
					ProductBean product = shoppingCartList.get(i);
					if (productId.equalsIgnoreCase(product.getProductId())) {
						totalPurchase -= product.getTotal();
						product.setQtyOrder(product.getQtyOrder() + qtyOrder);
					}
					totalPurchase += product.getTotal();
				}
			} else {
				ProductFilter productFilterBean = new ProductFilter();
				productFilterBean.setProductId(productId);
				ProductBean bean = baseUtil.getProduct(productFilterBean);
				if (bean != null) {
					ProductBean product = new ProductBean();
					product.setProductId(bean.getProductId());
					product.setProductName(bean.getProductName());
					product.setProductCode(bean.getProductCode());
					product.setUnitPrice(bean.getUnitPrice());
					product.setCategoryId(bean.getCategoryId());
					product.setSupplierId(bean.getSupplierId());
					product.setUomId(bean.getUomId());
					product.setQtyOrder(qtyOrder);
					shoppingCartList.add(product);
					totalPurchase += product.getTotal();
				}
				cartMap.put(productId, productId);
			}
			qtyOrder = 1;
		}

	}

	public String deleteCart() {

		totalPurchase = 0;
		List<ProductBean> shoppingCartNewList = new ArrayList<ProductBean>();
		for (int i = 0; i < shoppingCartList.size(); i++) {
			ProductBean bean = (ProductBean) shoppingCartList.get(i);
			if (bean.isChecked() == false) {
				shoppingCartNewList.add(bean);
				totalPurchase += bean.getUnitPrice() * bean.getQtyOrder();
			} else {
				cartMap.remove(bean.getProductId());
			}
		}
		checkAll = false;
		shoppingCartList = shoppingCartNewList;
		return SHOPPING_CART_UI;
	}

	public List<ProductBean> getShoppingCartList() {
		if (shoppingCartList != null) {
			for (int i = 0; i < shoppingCartList.size(); i++) {
				ProductBean bean = (ProductBean) shoppingCartList.get(i);
				bean.setChecked(checkAll);
			}
		}
		return shoppingCartList;
	}

	public void setShoppingCartList(List<ProductBean> shoppingCartList) {
		this.shoppingCartList = shoppingCartList;
	}

	public List<String> getMinistrycode() {
		ministrycode = new ArrayList<String>();
		ministrycode.add("10001");
		ministrycode.add("10002");
		ministrycode.add("20001");
		ministrycode.add("20002");
		ministrycode.add("30001");
		ministrycode.add("30002");

		return ministrycode;
	}

	public List<String> getJabatancode() {
		jabatancode = new ArrayList<String>();
		jabatancode.add("11001");
		jabatancode.add("11002");
		jabatancode.add("21001");
		jabatancode.add("21002");
		jabatancode.add("31001");
		jabatancode.add("31002");

		return jabatancode;
	}

	public List<String> getPtjcode() {
		ptjcode = new ArrayList<String>();
		ptjcode.add("11101");
		ptjcode.add("11102");
		ptjcode.add("21101");
		ptjcode.add("21102");
		ptjcode.add("31101");
		ptjcode.add("31102");

		return ptjcode;
	}

	public List<String> getSearchTypeList() {
		if (navigationBean != null) {
			navigationBean.getSearchTypeMap().clear();
		} else {
			navigationBean = new NavigationBean();
		}
		searchTypeList = new ArrayList<String>();
		int l = searchType.length;
		for (int i = 0; i < l; i++) {
			navigationBean.getSearchTypeMap().put(searchType[i], i + 1);
			searchTypeList.add(searchType[i]);
		}
		return searchTypeList;
	}

	public List<String> getSearchList() {
		int sValue = 1;
		if (navigationBean == null) {
			navigationBean = new NavigationBean();
		}
		if (navigationBean.getSearchTypeMap().containsKey(searchTypeValue)) {
			sValue = navigationBean.getSearchTypeMap().get(searchTypeValue);
		}

		if (searchList == null) {
			searchList = searchUtil.getSearchCatalogueList(navigationBean,
					sValue);
		}
		return searchList;
	}

	public String SearchResult() {
		int sValue = 0;
		String searchResult = "";
		String redirectPage = "";
		if (navigationBean.getSearchTypeMap().containsKey(searchTypeValue)) {
			sValue = navigationBean.getSearchTypeMap().get(searchTypeValue);
		}
		if (navigationBean.getSearchListMap().containsKey(searchListValue)) {
			searchResult = navigationBean.getSearchListMap().get(
					searchListValue);
		}
		if (sValue == 0) {
			faceMessages.setValidationMessage(messageManager
					.getMessage("error.validation.invalid.search.type.value"),
					FacesMessage.SEVERITY_ERROR);
		}
		if (searchResult.equalsIgnoreCase("")) {
			navigationBean.setCategoryId("0");
			navigationBean.setProductId("");
			categoryList = new ArrayList<Category>();
			navigationList = new ArrayList<NavigationBean>();
			faceMessages.setValidationMessage(messageManager
					.getMessage("error.validation.invalid.search.value"),
					FacesMessage.SEVERITY_ERROR);
		}
		ClearNavigationBean();
		if (sValue == 1) {
			navigationBean.setCategoryId(searchResult);
			redirectPage = checkCategory();
		} else if (sValue == 2) {
			navigationBean.setBrandId(searchResult);
			redirectPage = cleanList();
		} else if (sValue == 3) {
			navigationBean.setSupplierId(searchResult);
			redirectPage = cleanList();
		} else if (sValue == 4) {
			navigationBean.setProductId(searchResult);
			redirectPage = cleanProduct();
		}
		return redirectPage;
	}

	public void ClearNavigationBean() {
		navigationBean.setCategoryId("");
		navigationBean.setProductId("");
		navigationBean.setBrandId("");
		navigationBean.setSupplierId("");
	}

	public int getQtyOrder() {
		return qtyOrder;
	}

	public void setQtyOrder(int qtyOrder) {
		this.qtyOrder = qtyOrder;
	}

	public double getTotalPurchase() {
		totalPurchase = 0;
		if (shoppingCartList != null) {
			for (int i = 0; i < shoppingCartList.size(); i++) {
				ProductBean product = shoppingCartList.get(i);
				totalPurchase += product.getTotal();
			}
		}
		return totalPurchase;
	}

	public void setTotalPurchase(double totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public NavigationBean getNavigationBean() {
		if (navigationBean == null) {
			navigationBean = new NavigationBean();
		}
		return navigationBean;
	}

	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

	public double getFromValue() {
		return fromValue;
	}

	public void setFromValue(double fromValue) {
		this.fromValue = fromValue;
	}

	public double getToValue() {
		return toValue;
	}

	public void setToValue(double toValue) {
		this.toValue = toValue;
	}

	public int getDefaultQtyOrder() {
		return defaultQtyOrder;
	}

	public void setDefaultQtyOrder(int defaultQtyOrder) {
		this.defaultQtyOrder = defaultQtyOrder;
	}

	public String getSearchTypeValue() {
		if (searchTypeValue == null) {
			searchTypeValue = "Category";
			searchList = getSearchList();
		}
		return searchTypeValue;
	}

	public void setSearchTypeValue(String searchTypeValue) {
		this.searchTypeValue = searchTypeValue;
	}

	public String getSearchListValue() {
		if (searchListValue == null) {
			searchListValue = "";
		}
		return searchListValue;
	}

	public void setSearchListValue(String searchListValue) {
		this.searchListValue = searchListValue;
	}

	public String putTaskItemDetails() {
		List<ItemBean> taskItemList = new ArrayList<ItemBean>();
		String defaultDeliveryAddress = "";
		for (int i = 0; i < shoppingCartList.size(); i++) {
			ProductBean product = shoppingCartList.get(i);
			Category categoryBean = baseUtil.getCategoryListById(Integer
					.parseInt(product.getCategoryId()));
			ItemBean itemBean = new ItemBean();
			itemBean.setNo(Integer.toString(i + 1));
			itemBean.setContractNo("N/A");
			itemBean.setePProductNo(product.getProductCode());
			itemBean.setProductName(product.getProductName());
			itemBean.setProductDescription(categoryBean.getCategoryName());
			itemBean.setProductSpecification(categoryBean.getCategoryName());
			itemBean.setSupplierId(product.getSupplierId());
			itemBean.setSupplierName(product.getSupplierName());
			itemBean.setSupplierAddress("LOT 1.8, KULIM MALL, KILANG LAMA");
			itemBean.setSupplierSKU("N/A");
			itemBean.setContactPersonName("Umar Bin Ali");
			itemBean.setPhoneNumber("+6044906700");
			itemBean.setManufacturer("N/A");
			itemBean.setManufacturerPartNo("N/A");
			itemBean.setUnitOfMeasurement(product.getUomId());
			itemBean.setUnitPrice(product.getUnitPrice());
			itemBean.setMinimumOrderQuantity(10000);
			itemBean.setCountryOfOrigin("Malaysia");
			itemBean.setEdDate(new Date());
			itemBean.setDeliveryPeriod("60 Days");
			itemBean.setExpectedTimeFrom("09:00");
			itemBean.setExpectedTimeTo("16:00");
			itemBean.setDeliveryAddressName("JABATAN PENERANAGAN MALAYSIA");
			itemBean.setAddress("TK 7,10, 11, 12, 14 DAN 15 WISMA SIME DARBY JALAN RAJA LAUT, 50612, kUALA LUMPUR");
			itemBean.setZonePrice(product.getUnitPrice());
			itemBean.setOrderQuantity(product.getQtyOrder());
			itemBean.setTotal(product.getTotal());
			itemBean.setOthersAddress(false);
			taskItemList.add(itemBean);
			totalPurchase += itemBean.getTotal();
			defaultDeliveryAddress = itemBean.getAddress();
		}

		requisition = new RequisitionBean();
		RequisitionImpl service = new RequisitionImpl();
		requisition = service.preRequisitionResult();
		requisition.setTaskItemList(taskItemList);
		requisition.setDefaultDeliveryAddress(defaultDeliveryAddress);
		categoryList = null;
		navigationBean.setCategoryId("0");
		navigationBean.setProductId("");
		return SHOPS_GOF_UI;
	}

	public Map<String, String> getCartMap() {
		if (cartMap == null) {
			cartMap = new HashMap<String, String>();
		}
		return cartMap;
	}

	public void setCartMap(Map<String, String> cartMap) {
		this.cartMap = cartMap;
	}

	public String getFavoritesProductHeight() {
		favoritesProductHeight = "";
		if (favoritesProductList == null) {
			getFavoritesProductList();
		}
		if (favoritesProductList.size() > 5) {
			favoritesProductHeight = "100px";
		}
		return favoritesProductHeight;
	}

	public void setFavoritesProductHeight(String favoritesProductHeight) {
		this.favoritesProductHeight = favoritesProductHeight;
	}

	public String getFavoritesSupplierHeight() {
		favoritesSupplierHeight = "";
		if (favoritesSupplierList == null) {
			getFavoritesSupplierList();
		}
		if (favoritesSupplierList.size() > 5) {
			favoritesSupplierHeight = "100px";
		}
		return favoritesSupplierHeight;
	}

	public void setFavoritesSupplierHeight(String favoritesSupplierHeight) {
		this.favoritesSupplierHeight = favoritesSupplierHeight;
	}

	public int getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(int defaultSize) {
		this.defaultSize = defaultSize;
	}

	public void addToFavorites(String ids, String family) {
		String keys = family + "_" + ids;
		if (!favoritesMap.containsKey(keys)) {
			Favorites favorites = new Favorites();
			favorites.setActive(true);
			if ("Product".equals(family)) {
				favorites.setType("Product");
				favorites.setProductId(ids);
				favorites.setSupplierId("");
				baseUtil.FavoritesAction(favorites, "SAVE");
				favoritesProductList = null;

			} else if ("Supplier".equals(family)) {
				favorites.setType("Supplier");
				favorites.setSupplierId(ids);
				favorites.setProductId("");
				baseUtil.FavoritesAction(favorites, "SAVE");
				favoritesSupplierList = null;
			}
		} else {
			faceMessages.setValidationMessage(
					messageManager.getMessage("infor.validation.record.exits"),
					FacesMessage.SEVERITY_INFO);
		}

	}

	public BaseUtil getBaseUtil() {
		return baseUtil;
	}

	public void setBaseUtil(BaseUtil baseUtil) {
		this.baseUtil = baseUtil;
	}

	public void processDrop(DropEvent dropEvent) {
		Dropzone dropzone = (Dropzone) dropEvent.getComponent();
		String sType = dropzone.getDropValue().toString();
		if (sType.equalsIgnoreCase("Item")) {
			addProductToCart(dropEvent.getDragValue().toString());
		} else {
			addToFavorites(dropEvent.getDragValue().toString(), dropzone
					.getDropValue().toString());
		}
	}

	public String backToShop() {
		return SHOPPING_CART_UI;
	}

	public String initiatechangeoutcome() {
		bpmUtil.initiatechangeoutcome(outcome, requisition);
		return CATALOGUE_UI;
	}

	public void addTaskItemDetails() {
		requisition = serviceUtil.addTaskItemDetails(requisition);
		totalPurchase = requisition.getTotalPurchase();
	}

	public void removeTaskItemDetails() {
		requisition = serviceUtil.removeTaskItemDetails(requisition);
		totalPurchase = requisition.getTotalPurchase();
	}

	public void changeDeliveryAddress() {
		String productNo = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("recordNo");
		requisition = serviceUtil.changeDeliveryAddress(requisition, productNo);
	}

	public List<String> getDeliveryAddressList() {
		deliveryAddressList = serviceUtil.getDeliveryAddressList(requisition);
		return deliveryAddressList;
	}

	public String getFavoritesActionType() {
		return favoritesActionType;
	}

	public void setFavoritesActionType(String favoritesActionType) {
		this.favoritesActionType = favoritesActionType;
	}

	public String getCompareProductId() {
		return compareProductId;
	}

	public void setCompareProductId(String compareProductId) {
		this.compareProductId = compareProductId;
	}

	public void selectAllComponents(ValueChangeEvent event) {
		checkAll = (Boolean) event.getNewValue();
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public boolean isShowProductLists() {
		return showProductLists;
	}

	public void setShowProductLists(boolean showProductLists) {
		this.showProductLists = showProductLists;
	}

	public RequisitionBean getRequisition() {
		return requisition;
	}

	public void setRequisition(RequisitionBean requisition) {
		this.requisition = requisition;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
}
