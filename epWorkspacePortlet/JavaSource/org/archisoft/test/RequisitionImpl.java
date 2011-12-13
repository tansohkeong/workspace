package org.archisoft.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequisitionImpl {

	BpmUtil bpmUtil;

	public List<RequisitionBean> getRequsitionList(String docId, String docNo) {
		List<RequisitionBean> list = new ArrayList<RequisitionBean>();
		RequisitionBean bean = new RequisitionBean();
		bean = getRequsitionResult(docId, docNo);
		list.add(bean);
		return list;
	}

	public RequisitionBean getRequsitionResult(String docId, String docNo) {
		RequisitionBean bean = new RequisitionBean();
		 bean = bpmUtil.getRequisitionDetail(docId);
		return bean;
	}
	
	public RequisitionBean preRequisitionResult(){
		RequisitionBean bean = new RequisitionBean();
		
		bean.setMinistry("KEM PENERANGAN");
		bean.setDepartment("KEM PENERANGAN, KOMUNIKASI AND KEBUDAYAAN");
		bean.setDivision("JAB PENERANGAN I/PEJ - PENTABIRAN AND KEW");
		bean.setAddress1("JABATAN PENERANAGAN MALAYSIA, ");
		bean.setAddress2("TK 7,10, 11, 12, 14 DAN 15 WISMA SIME DARBY JALAN RAJA LAUT, ");
		bean.setAddress3("50612, kUALA LUMPUR");
		bean.setRequisitioner("MASNOM BINTI PUNGOT");

		bean.setStatus("Pending Submission");
		bean.setDateCreated(new Date());
		bean.setRequisitionNO("");
		bean.setTasksId("");

		bean.setRecJabatanNo("0281");
		bean.setRecJabatanName("KEM PENERANGAN, KOMUNIKASI - KEBUDAYAAN");
		bean.setRecPtjCode("301010");
		bean.setRecPtjName("JAB PENERANGAN I/PEJ-PENTABIRAN - KEW");

		bean.setChargeJabatanCode("0281");
		bean.setChargeJabatanName("KEM PENERANGAN, KOMUNIKASI - KEBUDAYAAN");
		bean.setChargePtjCode("301010");
		bean.setChargePtjName("JAB PENERANGAN I/PEJ-PENTABIRAN - KEW");
		bean.setChargeMaksudCode("B47");
		bean.setChargeMaksudName("2004_02811B47");
		bean.setChargeAGCCode("108");
		bean.setChargeAGCName("KEMENTERIAN PENERANGAN");
		bean.setChargeFinancingLoan("NO");
		bean.setChargeFinancialYear("2011");

		bean.setRequisitionDescription("");
		bean.setReferenceNo("");
		bean.setGoodReceivedOfficer("");
		bean.setProcumentType("");
		bean.setApprover("");
		bean.setRemarks("");
		return bean;
	}

}
