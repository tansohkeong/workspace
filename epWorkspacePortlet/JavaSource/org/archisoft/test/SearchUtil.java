package org.archisoft.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cdc.ep.service.client.Brand;
import com.cdc.ep.service.client.Category;
import com.cdc.ep.service.client.ProductFilter;
import com.cdc.ep.service.client.Supplier;

public class SearchUtil {

	BaseUtil baseUtil = new BaseUtil();

	public List<String> getSearchCatalogueList(NavigationBean navigationBean,
			int sValue) {
		List<String> searchList = new ArrayList<String>();
		navigationBean.setSearchListMap(new HashMap<String, String>());
		if (sValue == 1) {
			List<Category> resultList = baseUtil
					.getCategoryListBySubCategoryId(9999);
			for (int i = 0; i < resultList.size(); i++) {
				Category bean = resultList.get(i);
				searchList.add(bean.getCategoryName());
				if (!navigationBean.getSearchListMap().containsKey(
						bean.getCategoryName())) {
					navigationBean.getSearchListMap().put(
							bean.getCategoryName(),
							bean.getCategoryId().toString());
				}
			}
		} else if (sValue == 2) {
			List<Brand> resultList = baseUtil.getBrandList("");
			for (int i = 0; i < resultList.size(); i++) {
				Brand bean = resultList.get(i);
				searchList.add(bean.getBrandName());
				if (!navigationBean.getSearchListMap().containsKey(
						bean.getBrandName())) {
					navigationBean.getSearchListMap().put(bean.getBrandName(),
							bean.getBrandId());
				}
			}
		} else if (sValue == 3) {
			List<Supplier> resultList = baseUtil.getSupplierList("");
			for (int i = 0; i < resultList.size(); i++) {
				Supplier bean = resultList.get(i);
				searchList.add(bean.getSupplierName());
				if (!navigationBean.getSearchListMap().containsKey(
						bean.getSupplierName())) {
					navigationBean.getSearchListMap().put(
							bean.getSupplierName(), bean.getSupplierId());
				}
			}
		} else if (sValue == 4) {
			ProductFilter productFilterBean = new ProductFilter();
			List<ProductBean> resultList = baseUtil
					.getProductList(productFilterBean);
			for (int i = 0; i < resultList.size(); i++) {
				ProductBean bean = resultList.get(i);
				searchList.add(bean.getProductName());
				if (!navigationBean.getSearchListMap().containsKey(
						bean.getProductName())) {
					navigationBean.getSearchListMap().put(
							bean.getProductName(), bean.getProductId());
				}
			}
		}
		return searchList;
	}

}
