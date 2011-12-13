package org.archisoft.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceUtil {

	String[] deliveryAddress = { "Address 1", "Address 2", "Address 3",
			"Address 4" };

	public RequisitionBean addTaskItemDetails(RequisitionBean requisition) {
		List<ItemBean> taskItemNewList = new ArrayList<ItemBean>();
		double totalPurchase = 0;
		List<ItemBean> taskItemList = requisition.getTaskItemList();
		for (int i = 0; i < taskItemList.size(); i++) {
			ItemBean bean = taskItemList.get(i);
			if (bean.isChecked()) {
				ItemBean itemBean = (ItemBean) bean.clone();
				itemBean.setNo(Integer.toString(taskItemList.size() + 1));
				itemBean.setChecked(false);
				itemBean.setOthersAddress(false);
				taskItemNewList.add(itemBean);
				totalPurchase += bean.getTotal();
			}
			bean.setChecked(false);
			taskItemNewList.add(bean);
		}
		requisition.setTotalPurchase(totalPurchase);
		requisition.setTaskItemList(taskItemNewList);

		return requisition;
	}

	public RequisitionBean removeTaskItemDetails(RequisitionBean requisition) {
		List<ItemBean> taskItemNewList = new ArrayList<ItemBean>();
		double totalPurchase = 0;
		List<ItemBean> taskItemList = requisition.getTaskItemList();
		for (int i = 0; i < taskItemList.size(); i++) {
			ItemBean bean = taskItemList.get(i);
			if (!bean.isChecked()) {
				bean.setChecked(false);
				taskItemNewList.add(bean);
				totalPurchase += bean.getTotal();
			}
		}
		requisition.setTotalPurchase(totalPurchase);
		requisition.setTaskItemList(taskItemNewList);

		return requisition;
	}

	public RequisitionBean changeDeliveryAddress(RequisitionBean requisition,
			String productNo) {

		for (int i = 0; i < requisition.getTaskItemList().size(); i++) {
			ItemBean itemBean = requisition.getTaskItemList().get(i);
			if (itemBean.getNo().equalsIgnoreCase(productNo)) {
				itemBean.setAddress(requisition.getNewDeliveryAddress());
			}
		}
		return requisition;
	}

	public List<String> getDeliveryAddressList(RequisitionBean requisition) {
		List<String> deliveryAddressList = new ArrayList<String>();
		deliveryAddressList.add(requisition.getDefaultDeliveryAddress());
		int l = deliveryAddress.length;
		for (int i = 0; i < l; i++) {
			deliveryAddressList.add(deliveryAddress[i]);
		}
		return deliveryAddressList;
	}
	
	public boolean CheckValidValue(int value, String value2) {
		String[] newValue = value2.split(",");
		List<String> list = Arrays.asList(newValue);
		for (int i = 0; i < list.size(); i++) {
			Integer value3 = Integer.parseInt(list.get(i));
			if (value == value3) {
				return true;
			}
		}
		return false;
	}

}
