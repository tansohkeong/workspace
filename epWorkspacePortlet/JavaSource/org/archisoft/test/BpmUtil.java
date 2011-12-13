package org.archisoft.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customworklist.wsproxy.BpmServicePortClient;
import customworklist.wsproxy.InitiableTask;
import customworklist.wsproxy.Param;
import customworklist.wsproxy.TaskDetail;
import customworklist.wsproxy.WorklistItem;

public class BpmUtil extends Principal {

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	String[] field_list = { "Ministry", "Department", "Division",
			"Requisitioner", "BillToAddress", "DateCreated", "RequisitionNo",
			"Status", "ReferenceNo", "ReceiveAccount", "ChargeAccount",
			"GoodsReceivedOfficerName", "GrandTotal", "SelectProduct",
			"ProductName", "ProductDescription", "ProductSpecification",
			"SupplierId", "SupplierName", "SupplierAddress",
			"ContactPersonName", "PhoneNumber", "Manufacturer",
			"ManufacturerPartNo", "EPProductNo", "SupplierSKU",
			"UnitOfMeasurement", "UnitPrice", "MinimumOrderQuantity",
			"CountryOfOrigin", "ExpectedTimeFrom", "ExpectedTimeTo",
			"DeliveryAddress", "DeliveryAddressName", "Address", "ZonePrice",
			"OrderQuantity" };

	public BpmUtil() {
		addPrincipal();
	}

	public List<TaskBean> getWorkListByFilter(String sType, String sValue,
			String sSubValue) {

		List<TaskBean> taskList = new ArrayList<TaskBean>();
		try {
			List<TaskBean> workList = getWorkList();
			for (TaskBean taskBean : workList) {
				String status = taskBean.getState();
				if (sType.equals("STATUS")) {
					if (status.equals(sValue)) {
						taskList.add(taskBean);
					}
				} else if (sType.equals("DATE")) {
					Calendar calendar = Calendar.getInstance();
					if (sSubValue.equals("Past_Week")) {
						calendar.add(Calendar.DAY_OF_YEAR, -7);
					} else if (sSubValue.equals("Past_Month")) {
						calendar.add(Calendar.MONTH, -1);
					}
					Date newDate = calendar.getTime();

					if (taskBean.getCreatedDate().before(newDate)
							&& status.equals(sValue)) {
						taskList.add(taskBean);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return taskList;
	}

	public List<TaskBean> getWorkList() {
		List<TaskBean> taskList = new ArrayList<TaskBean>();
		try {
			System.out.println("getWorkList Start Time : "
					+ new Date(System.currentTimeMillis()));
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"MM/dd/yy hh:mm a");
			List<WorklistItem> workList = BpmServicePortClient
					.getWorklistItems(this.getName(), this.getPassword());
			for (WorklistItem workItem : workList) {
				if (!workItem.getState().equals("STALE")) {
					TaskBean taskBean = new TaskBean();
					taskBean.setTaskId(workItem.getTaskId());
					taskBean.setTaskNumber(workItem.getTaskNumber());
					taskBean.setTitle(workItem.getTitle());
					taskBean.setPriority(workItem.getPriority());
					taskBean.setAssigneeUsers(workItem.getAssigneeUsers());
					taskBean.setCreatedDate(dateFormat.parse(workItem
							.getCreateDate()));
					taskBean.setUpdatedDate(dateFormat.parse(workItem
							.getCreateDate()));
					taskBean.setUpdatedBy(workItem.getUpdatedBy());
					taskBean.setState(workItem.getState());
					taskBean.setOutcome(workItem.getOutcome());
					taskList.add(taskBean);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("getWorkList End Time : "
				+ new Date(System.currentTimeMillis()));
		return taskList;
	}

	public RequisitionBean getRequisitionDetail(String taskId) {
		RequisitionBean requisitionBean = new RequisitionBean();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			TaskDetail taskDetailBean = BpmServicePortClient.getTaskDetail(
					this.getName(), this.getPassword(), taskId);

			List<String> actionList = taskDetailBean.getPossibleActions();
			requisitionBean.setActionList(actionList);

			List<Param> params = taskDetailBean.getParams();
			for (Param param : params) {
				paramMap.put(param.getName(), param.getValue());
			}
			if (params.size() > 0) {
				requisitionBean.setTasksId(taskId);
				requisitionBean.setMinistry(paramMap.get("Ministry"));
				requisitionBean.setDepartment(paramMap.get("Department"));
				requisitionBean.setDivision(paramMap.get("Division"));
				requisitionBean.setRequisitioner(paramMap.get("Requisitioner"));
				String[] fullAddress = paramMap.get("Address").split(",", 3);
				requisitionBean.setAddress1(fullAddress[0]);
				requisitionBean.setAddress2(fullAddress[1]);
				requisitionBean.setAddress3(fullAddress[2]);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
				Date convertedDate = dateFormat.parse(paramMap
						.get("DateCreated"));
				requisitionBean.setDateCreated(convertedDate);
				requisitionBean.setRequisitionNO(paramMap.get("RequisitionNo"));
				requisitionBean.setStatus(paramMap.get("Status"));
				requisitionBean.setReferenceNo(paramMap.get("ReferenceNo"));
				String[] receiveAcc = paramMap.get("ReceiveAccount")
						.split("\n");
				// requisitionBean.setRecJabatanNo(param.getValue());
				if (receiveAcc.length > 0) {
					requisitionBean.setRecJabatanName(receiveAcc[0].trim());
				}
				// requisitionBean.setRecPtjCode(param.getValue());
				if (receiveAcc.length > 1) {
					requisitionBean.setRecPtjName(receiveAcc[1].trim());
				}
				String[] chargeAccount = paramMap.get("ChargeAccount").split(
						"\n");
				// requisitionBean.setChargeMaksudCode(param.getValue());
				requisitionBean.setChargeMaksudName(chargeAccount[1].trim());
				// requisitionBean.setChargeJabatanCode(param.getValue());
				requisitionBean.setChargeJabatanName(chargeAccount[2].trim());
				// requisitionBean.setChargePtjCode(param.getValue());
				requisitionBean.setChargePtjName(chargeAccount[3].trim());
				requisitionBean.setChargePAAPS(chargeAccount[4].trim());
				requisitionBean.setChargeFinancingLoan(chargeAccount[5].trim());
				// requisitionBean.setChargeAGCCode(param.getValue());
				requisitionBean.setChargeAGCName(chargeAccount[6].trim());
				requisitionBean.setChargeFinancialYear(chargeAccount[7].trim());

				// Add Item Detail
				List<ItemBean> taskItemList = new ArrayList<ItemBean>();
				ItemBean itemBean = new ItemBean();
				itemBean.setNo("1");
				itemBean.setePProductNo(paramMap.get("EPProductNo"));
				itemBean.setProductName(paramMap.get("ProductName"));
				itemBean.setProductDescription(paramMap
						.get("ProductDescription"));
				itemBean.setProductSpecification(paramMap
						.get("ProductSpecification"));
				itemBean.setSupplierId(paramMap.get("SupplierId"));
				itemBean.setSupplierName(paramMap.get("SupplierName"));
				itemBean.setSupplierAddress(paramMap.get("SupplierAddress"));
				itemBean.setSupplierSKU(paramMap.get("SupplierSKU"));
				itemBean.setContactPersonName(paramMap.get("ContactPersonName"));
				itemBean.setPhoneNumber(paramMap.get("PhoneNumber"));
				itemBean.setManufacturer(paramMap.get("Manufacturer"));
				itemBean.setManufacturerPartNo(paramMap
						.get("ManufacturerPartNo"));
				itemBean.setUnitOfMeasurement(paramMap.get("UnitOfMeasurement"));
				itemBean.setUnitPrice(Double.valueOf(paramMap.get("UnitPrice")));
				itemBean.setMinimumOrderQuantity(Integer.parseInt(paramMap
						.get("MinimumOrderQuantity")));
				itemBean.setCountryOfOrigin(paramMap.get("CountryOfOrigin"));
				itemBean.setExpectedTimeFrom(paramMap.get("ExpectedTimeFrom"));
				itemBean.setExpectedTimeTo(paramMap.get("ExpectedTimeTo"));
				itemBean.setDeliveryAddressName(paramMap
						.get("DeliveryAddressName"));
				itemBean.setAddress(paramMap.get("Address"));
				itemBean.setZonePrice(Double.valueOf(paramMap.get("ZonePrice")));
				double qtyOrder = Double.valueOf(paramMap.get("OrderQuantity"));
				itemBean.setOrderQuantity((int) qtyOrder);
				itemBean.setTotal(itemBean.getUnitPrice()
						* itemBean.getOrderQuantity());
				itemBean.setOthersAddress(false);
				itemBean.setContractNo("N/A");
				itemBean.setEdDate(new Date());
				itemBean.setDeliveryPeriod("60 Days");
				taskItemList.add(itemBean);
				requisitionBean.setTaskItemList(taskItemList);
				requisitionBean.setDefaultDeliveryAddress(paramMap
						.get("Address"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return requisitionBean;
	}

	public Map<String, String> getItemMap(RequisitionBean requisition) {
		Map<String, String> objMap = new HashMap<String, String>();

		objMap.put("Ministry", requisition.getMinistry());
		objMap.put("Department", requisition.getDepartment());
		objMap.put("Division", requisition.getDivision());
		objMap.put("Requisitioner", requisition.getRequisitioner());
		objMap.put("Address",
				requisition.getAddress1() + "," + requisition.getAddress2()
						+ "," + requisition.getAddress3());
		objMap.put("DateCreated", sdf.format(requisition.getDateCreated()));
		objMap.put("RequisitionNo", requisition.getRequisitionNO());
		objMap.put("Status", requisition.getStatus());
		objMap.put("ReferenceNo", requisition.getReferenceNo());
		objMap.put("ReceiveAccount", "\n" + requisition.getRecJabatanName()
				+ "\n" + requisition.getRecPtjName());
		objMap.put(
				"ChargeAccount",
				"\n" + requisition.getChargeMaksudName() + "\n"
						+ requisition.getChargeJabatanName() + "\n"
						+ requisition.getChargePtjName() + "\n"
						+ requisition.getChargePAAPS() + "\n"
						+ requisition.getChargeFinancingLoan() + "\n"
						+ requisition.getChargeAGCName() + "\n"
						+ requisition.getChargeFinancialYear());
		for (int i = 0; i < requisition.getTaskItemList().size(); i++) {
			ItemBean itemBean = requisition.getTaskItemList().get(i);
			objMap.put("EPProductNo", itemBean.getePProductNo());
			objMap.put("ProductName", itemBean.getProductName());
			objMap.put("ProductDescription", itemBean.getProductDescription());
			objMap.put("ProductSpecification",
					itemBean.getProductSpecification());
			objMap.put("SupplierId", itemBean.getSupplierId());
			objMap.put("SupplierName", itemBean.getSupplierName());
			objMap.put("SupplierSKU", itemBean.getSupplierSKU());
			objMap.put("SupplierAddress", itemBean.getSupplierAddress());
			objMap.put("SupplierSKU", itemBean.getSupplierSKU());
			objMap.put("ContactPersonName", itemBean.getContactPersonName());
			objMap.put("PhoneNumber", itemBean.getPhoneNumber());
			objMap.put("Manufacturer", itemBean.getManufacturer());
			objMap.put("ManufacturerPartNo", itemBean.getManufacturerPartNo());
			objMap.put("UnitOfMeasurement", itemBean.getUnitOfMeasurement());
			objMap.put("UnitPrice", itemBean.getUnitPrice().toString());
			objMap.put("MinimumOrderQuantity",
					Integer.toString(itemBean.getMinimumOrderQuantity()));
			objMap.put("CountryOfOrigin", itemBean.getCountryOfOrigin());
			objMap.put("ExpectedTimeFrom", itemBean.getExpectedTimeFrom());
			objMap.put("ExpectedTimeTo", itemBean.getExpectedTimeTo());
			objMap.put("DeliveryAddressName", itemBean.getDeliveryAddressName());
			objMap.put("Address", itemBean.getAddress());
			objMap.put("ZonePrice", itemBean.getZonePrice().toString());
			objMap.put("OrderQuantity",
					Integer.toString(itemBean.getOrderQuantity()));
		}
		return objMap;
	}

	public void changeOutCome(String taskId, String outcome,
			RequisitionBean requisition) {
		try {
			if (taskId != null && !"".equals(taskId.trim()) && outcome != null
					&& !"".equals(outcome.trim())) {
				Map<String, String> itemMap = getItemMap(requisition);
				TaskDetail taskdetails = BpmServicePortClient.getTaskDetail(
						this.getName(), this.getPassword(), taskId);
				List<Param> params = taskdetails.getParams();

				for (Param param : params)
					param.setValue(itemMap.get(param.getName()));

				BpmServicePortClient.updateTask(this.getName(),
						this.getPassword(), taskId, params, outcome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initiatechangeoutcome(String outcome,
			RequisitionBean requisition) {
		try {
			String processId = "";
			List<InitiableTask> result = BpmServicePortClient.getInitiableTask(
					this.getName(), this.getPassword());
			for (InitiableTask task : result) {
				processId = task.getProcessId();
			}
			Map<String, String> itemMap = getItemMap(requisition);
			List<Param> params = new ArrayList<Param>();
			for (String field : field_list) {
				Param param = new Param();
				param.setName(field);
				param.setValue(itemMap.get(param.getName()));
				params.add(param);
			}

			customworklist.wsproxy.BpmServicePortClient.initiateAndUpdateTask(
					this.getName(), this.getPassword(), processId, params,
					outcome);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getInitiableTask() {
		System.out.println("getInitiableTask Start Time : "
				+ new Date(System.currentTimeMillis()));
		List<String> initiableTaskList = new ArrayList<String>();
		List<InitiableTask> result = BpmServicePortClient.getInitiableTask(
				this.getName(), this.getPassword());
		for (InitiableTask task : result) {
			initiableTaskList.add(task.getProcessName());
		}
		System.out.println("getInitiableTask End Time : "
				+ new Date(System.currentTimeMillis()));
		return initiableTaskList;
	}
}
