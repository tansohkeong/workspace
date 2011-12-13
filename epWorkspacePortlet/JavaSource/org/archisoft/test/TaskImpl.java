package org.archisoft.test;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class TaskImpl extends Principal {

	public static final String TASKS_UI = "tasksUI";
	public static final String TASKS_FORM_UI = "tasksFormUI";

	private List<TaskBean> taskList;
	private List<String> statusList;
	private List<String> deliveryAddressList;

	private String taskId = "";
	private String taskNo = "";
	private String taskType = "";
	private String taskFilterType;
	private String taskFilterValue;
	private String taskFilterSubValue;

	private RequisitionBean requisition;
	private double totalPurchase;
	private String init;

	String[] deliveryAddress = { "Address 1", "Address 2", "Address 3",
			"Address 4" };

	BpmUtil bpmUtil = new BpmUtil();
	ServiceUtil serviceUtil = new ServiceUtil();

	public String getInit() {
		taskList = null;
		taskFilterType = "STATUS";
		taskFilterValue = "ASSIGNED";
		taskFilterSubValue = "";
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public List<String> getStatusList() {
		statusList = new ArrayList<String>();
		statusList.add("ASSIGNED");
		statusList.add("COMPLETED");
		return statusList;
	}

	public void changeFilterValue(ValueChangeEvent e) {
		this.setTaskFilterValue(e.getNewValue().toString());
		taskList = null;
		getTaskList();
	}

	public void changeDeliveryAddress() {
		String productNo = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("recordNo");

		requisition = serviceUtil.changeDeliveryAddress(requisition, productNo);
	}

	public String CleanList() {
		taskList = null;
		taskFilterType = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("taskFilterType");
		taskFilterSubValue = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("taskFilterSubValue");
		return TASKS_UI;
	}

	public List<TaskBean> getTaskList() {

		if (taskList == null) {
			taskType = "";
			taskList = new ArrayList<TaskBean>();
			if (taskFilterType == null || taskFilterType.equalsIgnoreCase("")) {
				taskFilterType = "STATUS";
			}
			taskList = bpmUtil.getWorkListByFilter(taskFilterType,
					taskFilterValue, taskFilterSubValue);
		}
		return taskList;
	}

	public void changeTaskItem(String sTaskType) {
		if (sTaskType != null && !sTaskType.equals("")) {
			taskType = sTaskType;
		}
		requisition = null;
	}

	public void addTaskItemDetails() {
		requisition = serviceUtil.addTaskItemDetails(requisition);
		totalPurchase = requisition.getTotalPurchase();
	}

	public void removeTaskItemDetails() {
		requisition = serviceUtil.removeTaskItemDetails(requisition);
		totalPurchase = requisition.getTotalPurchase();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getTaskType() {
		if (taskType.equalsIgnoreCase("")) {
			taskList = null;
		}
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskFilterValue() {
		if (taskFilterValue == null) {
			taskFilterValue = "ASSIGNED";
		}
		return taskFilterValue;
	}

	public void setTaskFilterValue(String taskFilterValue) {
		this.taskFilterValue = taskFilterValue;
	}

	public String getTaskFilterType() {
		if (taskFilterType == null) {
			taskFilterType = "STATUS";
		}
		return taskFilterType;
	}

	public void setTaskFilterType(String taskFilterType) {
		this.taskFilterType = taskFilterType;
	}

	public String getTaskFilterSubValue() {
		return taskFilterSubValue;
	}

	public void setTaskFilterSubValue(String taskFilterSubValue) {
		this.taskFilterSubValue = taskFilterSubValue;
	}

	public RequisitionBean getRequisition() {
		if (requisition == null) {
			requisition = new RequisitionBean();
			if (taskId != "") {
				requisition = bpmUtil.getRequisitionDetail(taskId);
			}
		}
		return requisition;
	}

	public void setRequisition(RequisitionBean requisition) {
		this.requisition = requisition;
	}

	public double getTotalPurchase() {
		totalPurchase = 0;
		List<ItemBean> taskItemList = requisition.getTaskItemList();
		if (taskItemList != null) {
			for (int i = 0; i < taskItemList.size(); i++) {
				ItemBean bean = taskItemList.get(i);
				totalPurchase += bean.getTotal();
			}
		}
		return totalPurchase;
	}

	public void setTotalPurchase(double totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public List<String> getDeliveryAddressList() {
		deliveryAddressList = serviceUtil.getDeliveryAddressList(requisition);
		return deliveryAddressList;
	}

	public String changeOutCome() {
		String outcome = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("outcome");
		bpmUtil.changeOutCome(taskId, outcome, requisition);
		taskType = "";
		taskList = null;
		return TASKS_UI;
	}

	public String navTaskUI() {
		taskType = "";
		return TASKS_UI;
	}

	public String navTaskFormUI() {
		taskNo = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("taskNo");
		taskId = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("taskId");
		taskType = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("taskType");
		requisition = null;
		return TASKS_FORM_UI;
	}
}
