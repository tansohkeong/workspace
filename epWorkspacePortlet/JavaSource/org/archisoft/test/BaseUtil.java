package org.archisoft.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cdc.ep.base.service.EPBaseServiceClient;
import com.cdc.ep.service.client.Brand;
import com.cdc.ep.service.client.Category;
import com.cdc.ep.service.client.Favorites;
import com.cdc.ep.service.client.FavoritesXSD;
import com.cdc.ep.service.client.Itemspec;
import com.cdc.ep.service.client.Navigation;
import com.cdc.ep.service.client.ProductFilter;
import com.cdc.ep.service.client.ProductXSD;
import com.cdc.ep.service.client.Productgallery;
import com.cdc.ep.service.client.RelatedProduct;
import com.cdc.ep.service.client.Specdetails;
import com.cdc.ep.service.client.Supplier;

public class BaseUtil extends Principal {

	EPBaseServiceClient client = new EPBaseServiceClient();

	public BaseUtil() {
		addPrincipal();
	}

	public List<FavoritesBean> getFavoritesByFilter(String type) {
		// System.out.println("getFavoritesByFilter Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<FavoritesBean> favoritesBeanList = new ArrayList<FavoritesBean>();
		List<FavoritesXSD> favoritesList = client.getFavoritesList(getName(),
				type);
		for (int i = 0; i < favoritesList.size(); i++) {
			FavoritesXSD bean = favoritesList.get(i);
			FavoritesBean favoritesBean = new FavoritesBean();
			favoritesBean.setUserId(getName());

			if (type.equalsIgnoreCase("Product")) {
				favoritesBean.setType(bean.getType());
				favoritesBean.setFavoritesId(bean.getId());
				favoritesBean.setProductId(bean.getProductId());
				favoritesBean.setProductName(bean.getDescription());
				favoritesBean.setCategoryId(bean.getCategoryId());

			} else {
				favoritesBean.setType("Supplier");
				favoritesBean.setFavoritesId(bean.getId());
				favoritesBean.setSupplierId(bean.getSupplierId());
				favoritesBean.setSupplierName(bean.getDescription());
				favoritesBean.setCategoryId("");
			}
			favoritesBeanList.add(favoritesBean);
		}
		// System.out.println("getFavoritesByFilter End Time : "
		// + new Date(System.currentTimeMillis()));
		return favoritesBeanList;

	}

	public void FavoritesAction(Favorites favorites, String action) {
		// System.out.println("FavoritesAction Start Time : "
		// + new Date(System.currentTimeMillis()));
		favorites.setUserId(getName());
		client.favoritesAction(favorites, action);
		// System.out.println("FavoritesAction End Time : "
		// + new Date(System.currentTimeMillis()));
	}

	public Favorites getFavorites(int favoritesId) {
		// System.out.println("getFavorites Start Time : "
		// + new Date(System.currentTimeMillis()));
		Favorites favorites = client.getFavoritesListById(favoritesId);
		// System.out.println("getFavorites End Time : "
		// + new Date(System.currentTimeMillis()));
		return favorites;
	}

	public Brand getBrand(String brandId) {
		// System.out.println("getBrand Start Time : "
		// + new Date(System.currentTimeMillis()));
		Brand bean = new Brand();
		List<Brand> brandList = client.getBrandList(brandId);
		for (Brand beanNew : brandList) {
			bean = beanNew;
		}
		// System.out.println("getBrand End Time : "
		// + new Date(System.currentTimeMillis()));
		return bean;

	}

	public List<Brand> getBrandList(String brandId) {
		// System.out.println("getBrandList Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<Brand> brandList = client.getBrandList(brandId);
		// System.out.println("getBrandList End Time : "
		// + new Date(System.currentTimeMillis()));
		return brandList;
	}

	public Supplier getSupplier(String supplierId) {
		// System.out.println("getSupplier Start Time : "
		// + new Date(System.currentTimeMillis()));
		Supplier bean = new Supplier();
		List<Supplier> supplierList = client.getSupplierList(supplierId);
		for (Supplier beanNew : supplierList) {
			bean = beanNew;
		}
		// System.out.println("getSupplier End Time : "
		// + new Date(System.currentTimeMillis()));
		return bean;

	}

	public List<Supplier> getSupplierList(String supplierId) {
		// System.out.println("getSupplierList Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<Supplier> supplierList = client.getSupplierList(supplierId);
		// System.out.println("getSupplierList End Time : "
		// + new Date(System.currentTimeMillis()));
		return supplierList;
	}

	public ProductBean getProduct(ProductFilter productFilter) {
		// System.out.println("getProduct Start Time : "
		// + new Date(System.currentTimeMillis()));
		ProductBean bean = new ProductBean();
		List<ProductXSD> productList = client
				.getProductListByFilter(productFilter);
		for (ProductXSD beanNew : productList) {
			bean.setProductId(beanNew.getProductId());
			bean.setProductCode(beanNew.getProductCode());
			bean.setProductName(beanNew.getProductName());
			bean.setBrandId(beanNew.getBrandId());
			bean.setBrandName(beanNew.getBrandName());
			bean.setSupplierId(beanNew.getSupplierId());
			bean.setSupplierName(beanNew.getSupplierName());
			bean.setCategoryId(beanNew.getCategoryId());
			bean.setUnitPrice(beanNew.getUnitPrice());
			bean.setUomId(beanNew.getUomId());
			bean.setImg(beanNew.getImg());
			bean.setRemarks(beanNew.getRemarks());
			if (beanNew.getSpecDetailsList().size() > 0) {
				bean.setSpecList(getSpecDetail(beanNew.getSpecDetailsList(),
						Integer.parseInt(beanNew.getCategoryId())));
			}
			if (beanNew.getGalleryList().size() > 0) {
				List<String> galleryList = new ArrayList<String>();
				galleryList.add(beanNew.getImg());
				for (int i = 0; i < beanNew.getGalleryList().size(); i++) {
					Productgallery productGallery = beanNew.getGalleryList()
							.get(i);
					galleryList.add(productGallery.getImg());
				}
				bean.setGalleryList(galleryList);
			}
		}
		// System.out.println("getProduct End Time : "
		// + new Date(System.currentTimeMillis()));
		return bean;

	}

	public List<ListingBean> getSpecDetail(List<Specdetails> specDetailsList,
			int categoryId) {
		// System.out.println("getSpecDetail Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<ListingBean> resultList = new ArrayList<ListingBean>();
		List<Itemspec> itemList = getItemSpecListByCategoryId(categoryId);
		if (specDetailsList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				Itemspec itemspec = itemList.get(i);
				Specdetails specdetails = specDetailsList.get(i);
				ListingBean listingBean = new ListingBean();
				listingBean.setCode(itemspec.getItemName());
				listingBean.setName(specdetails.getDetailsDesc());

				resultList.add(listingBean);
			}
		}
		// System.out.println("getSpecDetail End Time : "
		// + new Date(System.currentTimeMillis()));
		return resultList;

	}

	public Category getCategoryListById(int subCategoryid) {
		// System.out.println("getCategoryListById Start Time : "
		// + new Date(System.currentTimeMillis()));
		Category category = client.getCategoryListByCategoryId(subCategoryid);
		// System.out.println("getCategoryListById End Time : "
		// + new Date(System.currentTimeMillis()));
		return category;
	}

	public List<Category> getCategoryListBySubCategoryId(int subCategoryid) {
		// System.out.println("getCategoryListBySubCategoryId Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<Category> categoryList = client
				.getCategoryListBySubCategoryId(subCategoryid);
		// System.out.println("getCategoryListBySubCategoryId End Time : "
		// + new Date(System.currentTimeMillis()));
		return categoryList;
	}

	public List<ProductBean> getProductList(ProductFilter productFilter) {
		// System.out.println("getProductList Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<ProductBean> resultList = new ArrayList<ProductBean>();
		List<ProductXSD> productList = client
				.getProductListByFilter(productFilter);
		for (ProductXSD beanNew : productList) {
			ProductBean bean = new ProductBean();
			bean.setProductId(beanNew.getProductId());
			bean.setProductCode(beanNew.getProductCode());
			bean.setProductName(beanNew.getProductName());
			bean.setBrandId(beanNew.getBrandId());
			bean.setBrandName(beanNew.getBrandName());
			bean.setSupplierId(beanNew.getSupplierId());
			bean.setSupplierName(beanNew.getSupplierName());
			bean.setCategoryId(beanNew.getCategoryId());
			bean.setUnitPrice(beanNew.getUnitPrice());
			bean.setImg(beanNew.getImg());
			bean.setRemarks(beanNew.getRemarks());
			resultList.add(bean);
		}
		// System.out.println("getProductList End Time : "
		// + new Date(System.currentTimeMillis()));
		return resultList;

	}

	public List<ProductBean> getProductList(ProductFilter productFilter,
			Map<String, String> compareListMap) {
		// System.out.println("getProductList1 Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<ProductBean> resultList = new ArrayList<ProductBean>();
		List<ProductXSD> productList = client
				.getProductListByFilter(productFilter);
		for (ProductXSD beanNew : productList) {
			ProductBean bean = new ProductBean();
			bean.setProductId(beanNew.getProductId());
			bean.setProductCode(beanNew.getProductCode());
			bean.setProductName(beanNew.getProductName());
			bean.setBrandId(beanNew.getBrandId());
			bean.setBrandName(beanNew.getBrandName());
			bean.setSupplierId(beanNew.getSupplierId());
			bean.setSupplierName(beanNew.getSupplierName());
			bean.setCategoryId(beanNew.getCategoryId());
			bean.setUnitPrice(beanNew.getUnitPrice());
			bean.setImg(beanNew.getImg());
			bean.setRemarks(beanNew.getRemarks());
			if (compareListMap.containsKey(beanNew.getProductId())) {
				bean.setCompare(true);
			}
			resultList.add(bean);
		}
		// System.out.println("getProductList1 End Time : "
		// + new Date(System.currentTimeMillis()));
		return resultList;

	}

	public List<Itemspec> getItemSpecListByCategoryId(int categoryid) {
		// System.out.println("getItemSpecListByCategoryId Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<Itemspec> itemList = client
				.getItemSpecListByCategoryId(categoryid);
		// System.out.println("getItemSpecListByCategoryId End Time : "
		// + new Date(System.currentTimeMillis()));
		return itemList;
	}

	public List<ListingBean> getProductSpecDetailsList(String productId) {
		// System.out.println("getProductSpecDetailsList Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<ListingBean> resultList = new ArrayList<ListingBean>();
		ProductFilter productFilter = new ProductFilter();
		productFilter.setProductId(productId);
		ProductBean productBean = getProduct(productFilter);
		List<Specdetails> specList = client
				.getSpecDetailListByProductId(productId);

		resultList = getSpecDetail(specList,
				Integer.parseInt(productBean.getCategoryId()));
		// System.out.println("getProductSpecDetailsList End Time : "
		// + new Date(System.currentTimeMillis()));
		return resultList;

	}

	public List<ProductBean> getRelatedProductListByProductId(String productId) {
		// System.out.println("getRelatedProductListByProductId Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<ProductBean> resultList = new ArrayList<ProductBean>();
		List<RelatedProduct> relatedProductList = client
				.getRelatedProductListByProductId(productId);
		for (RelatedProduct beanNew : relatedProductList) {

			ProductBean bean = new ProductBean();
			bean.setProductId(beanNew.getParentProductId());
			bean.setProductCode(beanNew.getParentProductCode());
			bean.setProductName(beanNew.getParentProductName());
			bean.setUnitPrice(beanNew.getParentProductUnitPrice());
			bean.setImg(beanNew.getImg());
			resultList.add(bean);
		}
		// System.out.println("getRelatedProductListByProductId End Time : "
		// + new Date(System.currentTimeMillis()));
		return resultList;
	}

	public List<NavigationBean> getNavigationCategoryId(int categoryId) {
		// System.out.println("getNavigationCategoryId Start Time : "
		// + new Date(System.currentTimeMillis()));
		List<NavigationBean> navigationList = new ArrayList<NavigationBean>();
		List<Navigation> resultList = client.getNavigationList(categoryId);
		for (int i = 0; i < resultList.size(); i++) {
			Navigation navigation = resultList.get(i);
			NavigationBean navigationBean = new NavigationBean();
			navigationBean.setArrow(navigation.getArrow());
			navigationBean.setCategoryId(navigation.getCategoryId());
			navigationBean.setCategoryName(navigation.getCategoryName());
			navigationList.add(navigationBean);
		}
		// System.out.println("getNavigationCategoryId Start Time : "
		// + new Date(System.currentTimeMillis()));
		return navigationList;
	}

}
