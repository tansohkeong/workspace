package org.archisoft.test;

import java.util.Date;
import java.util.List;

public class RequisitionBean {

	private String Ministry;
	private String Department;
	private String Division;
	private String Address1;
	private String Address2;
	private String Address3;
	private String Requisitioner;

	private String Status;
	private Date DateCreated;
	private String RequisitionNO;
	private String tasksId;

	private String RecJabatanNo;
	private String RecJabatanName;
	private String RecPtjCode;
	private String RecPtjName;

	private String ChargePAAPS;
	private String ChargeJabatanCode;
	private String ChargeJabatanName;
	private String ChargePtjCode;
	private String ChargePtjName;
	private String ChargeMaksudCode;
	private String ChargeMaksudName;
	private String ChargeAGCCode;
	private String ChargeAGCName;
	private String ChargeFinancingLoan;
	private String ChargeFinancialYear;

	private String RequisitionDescription;
	private String ReferenceNo;
	private String GoodReceivedOfficer;
	private String ProcumentType;
	private String Approver;
	private String Remarks;
	private List<ItemBean> taskItemList;
	private String defaultDeliveryAddress;
	private String newDeliveryAddress;
	private List<String> actionList;
	private double totalPurchase;

	public String getMinistry() {
		return Ministry;
	}

	public void setMinistry(String ministry) {
		Ministry = ministry;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getDivision() {
		return Division;
	}

	public void setDivision(String division) {
		Division = division;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getAddress3() {
		return Address3;
	}

	public void setAddress3(String address3) {
		Address3 = address3;
	}

	public String getRequisitioner() {
		return Requisitioner;
	}

	public void setRequisitioner(String requisitioner) {
		Requisitioner = requisitioner;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	public String getRequisitionNO() {
		return RequisitionNO;
	}

	public void setRequisitionNO(String requisitionNO) {
		RequisitionNO = requisitionNO;
	}

	public String getRecJabatanNo() {
		return RecJabatanNo;
	}

	public void setRecJabatanNo(String recJabatanNo) {
		RecJabatanNo = recJabatanNo;
	}

	public String getRecJabatanName() {
		return RecJabatanName;
	}

	public void setRecJabatanName(String recJabatanName) {
		RecJabatanName = recJabatanName;
	}

	public String getRecPtjCode() {
		return RecPtjCode;
	}

	public void setRecPtjCode(String recPtjCode) {
		RecPtjCode = recPtjCode;
	}

	public String getRecPtjName() {
		return RecPtjName;
	}

	public void setRecPtjName(String recPtjName) {
		RecPtjName = recPtjName;
	}

	public String getChargeJabatanCode() {
		return ChargeJabatanCode;
	}

	public void setChargeJabatanCode(String chargeJabatanCode) {
		ChargeJabatanCode = chargeJabatanCode;
	}

	public String getChargeJabatanName() {
		return ChargeJabatanName;
	}

	public void setChargeJabatanName(String chargeJabatanName) {
		ChargeJabatanName = chargeJabatanName;
	}

	public String getChargePtjCode() {
		return ChargePtjCode;
	}

	public void setChargePtjCode(String chargePtjCode) {
		ChargePtjCode = chargePtjCode;
	}

	public String getChargePtjName() {
		return ChargePtjName;
	}

	public void setChargePtjName(String chargePtjName) {
		ChargePtjName = chargePtjName;
	}

	public String getChargeMaksudCode() {
		return ChargeMaksudCode;
	}

	public void setChargeMaksudCode(String chargeMaksudCode) {
		ChargeMaksudCode = chargeMaksudCode;
	}

	public String getChargeMaksudName() {
		return ChargeMaksudName;
	}

	public void setChargeMaksudName(String chargeMaksudName) {
		ChargeMaksudName = chargeMaksudName;
	}

	public String getChargeAGCCode() {
		return ChargeAGCCode;
	}

	public void setChargeAGCCode(String chargeAGCCode) {
		ChargeAGCCode = chargeAGCCode;
	}

	public String getChargeAGCName() {
		return ChargeAGCName;
	}

	public void setChargeAGCName(String chargeAGCName) {
		ChargeAGCName = chargeAGCName;
	}

	public String getChargeFinancingLoan() {
		return ChargeFinancingLoan;
	}

	public void setChargeFinancingLoan(String chargeFinancingLoan) {
		ChargeFinancingLoan = chargeFinancingLoan;
	}

	public String getChargeFinancialYear() {
		return ChargeFinancialYear;
	}

	public void setChargeFinancialYear(String chargeFinancialYear) {
		ChargeFinancialYear = chargeFinancialYear;
	}

	public String getRequisitionDescription() {
		return RequisitionDescription;
	}

	public void setRequisitionDescription(String requisitionDescription) {
		RequisitionDescription = requisitionDescription;
	}

	public String getReferenceNo() {
		return ReferenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		ReferenceNo = referenceNo;
	}

	public String getGoodReceivedOfficer() {
		return GoodReceivedOfficer;
	}

	public void setGoodReceivedOfficer(String goodReceivedOfficer) {
		GoodReceivedOfficer = goodReceivedOfficer;
	}

	public String getProcumentType() {
		return ProcumentType;
	}

	public void setProcumentType(String procumentType) {
		ProcumentType = procumentType;
	}

	public String getApprover() {
		return Approver;
	}

	public void setApprover(String approver) {
		Approver = approver;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getTasksId() {
		return tasksId;
	}

	public void setTasksId(String tasksId) {
		this.tasksId = tasksId;
	}
		
	public List<ItemBean> getTaskItemList() {
		return taskItemList;
	}

	public void setTaskItemList(List<ItemBean> taskItemList) {
		this.taskItemList = taskItemList;
	}

	public String getNewDeliveryAddress() {
		return newDeliveryAddress;
	}

	public void setNewDeliveryAddress(String newDeliveryAddress) {
		this.newDeliveryAddress = newDeliveryAddress;
	}

	public String getDefaultDeliveryAddress() {
		return defaultDeliveryAddress;
	}

	public void setDefaultDeliveryAddress(String defaultDeliveryAddress) {
		this.defaultDeliveryAddress = defaultDeliveryAddress;
	}

	public String getChargePAAPS() {
		return ChargePAAPS;
	}

	public void setChargePAAPS(String chargePAAPS) {
		ChargePAAPS = chargePAAPS;
	}

	public List<String> getActionList() {
		return actionList;
	}

	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}

	public double getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(double totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

}
