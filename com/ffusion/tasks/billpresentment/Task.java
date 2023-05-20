package com.ffusion.tasks.billpresentment;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final int TASKERROR = -1;
  public static final int SERVICEERROR = -2;
  public static final String BILLER = "Biller";
  public static final String BILLERS = "Billers";
  public static final String BILLSUMMARY = "BillSummary";
  public static final String BILLSUMMARIES = "BillSummaries";
  public static final String CONSUMER = "Consumer";
  public static final String CONSUMERS = "Consumers";
  public static final String CONSUMERSTATUS = "ConsumerStatus";
  public static final String EBILLACCOUNT = "EBillAccount";
  public static final String EBILLACCOUNTS = "EBillAccounts";
  public static final String PAYMENTINFOS = "PaymentInfos";
  public static final String PUBLISHER = "Publisher";
  public static final String PUBLISHERS = "Publishers";
  public static final String TCURL = "TCURL";
  public static final String BILLSTOPAY = "BillsToPay";
  public static final String BILLPAYERRORS = "BillPayErrors";
  public static final String FILTEREDBILLSUMMARIES = "FilteredBillSummaries";
  public static final String PAYMENTSTOPAY = "PaymentsToPay";
  public static final String ACTIVATEEBILLACCOUNT = "ActivateEBillAccount";
  public static final String ADDBILLSTOPAY = "AddBillsToPay";
  public static final String ADDTCURL = "AddTCURL";
  public static final String DEACTIVATEEBILLACOUNT = "DeactivateEBillAccount";
  public static final String DELETETCURL = "DeleteTCURL";
  public static final String ENROLLCONSUMER = "EnrollConsumer";
  public static final String GETBILLERS = "GetBillers";
  public static final String GETBILLSTOPAY = "GetBillsToPay";
  public static final String GETBILLSUMMARIES = "GetBillSummaries";
  public static final String GETCONSUMERS = "GetConsumers";
  public static final String GETEBILLACCOUNTS = "GetEBillAccounts";
  public static final String GETPUBLISHERS = "GetPublishers";
  public static final String GETTCURL = "GetTCURL";
  public static final String MODIFYBILLER = "ModifyBiller";
  public static final String MODIFYBILLSUMMARY = "ModifyBillSummary";
  public static final String MODIFYCONSUMER = "ModifyConsumer";
  public static final String MODIFYCONSUMERSTATUS = "ModifyConsumerStatus";
  public static final String MODIFYEBILLACCOUNT = "ModifyEBillAccount";
  public static final String MODIFYTCURL = "ModifyTCURL";
  public static final int ERROR_NO_BILLPAY_SERVICE = 6501;
  public static final int ERROR_NO_BILLER = 6502;
  public static final int ERROR_NO_BILLERS = 6503;
  public static final int ERROR_NO_BILLSUMMARY = 6504;
  public static final int ERROR_NO_BILLSUMMARIES = 6505;
  public static final int ERROR_NO_CONSUMER = 6506;
  public static final int ERROR_NO_CONSUMERS = 6507;
  public static final int ERROR_NO_CONSUMERSTATUS = 6508;
  public static final int ERROR_NO_EBILLACCOUNT = 6509;
  public static final int ERROR_NO_EBILLACCOUNTS = 6510;
  public static final int ERROR_NO_PUBLISHER = 6511;
  public static final int ERROR_NO_PUBLISHERS = 6512;
  public static final int ERROR_NO_TCURL = 6513;
  public static final int ERROR_NO_MISSINGPAYEEACCOUNTS = 6514;
  public static final int ERROR_NO_CONSUMERPAYEELIST = 6515;
  public static final int ERROR_NO_BILLSTOPAY = 6516;
  public static final int ERROR_NO_PAYMENTSTOPAY = 6517;
  public static final int ERROR_NO_BILLPAY_ACCOUNTS = 6518;
  public static final int ERROR_MISSING_PAYMENTDATE = 6600;
  public static final int ERROR_PAYMENTDATE_TOO_EARLY = 6601;
  public static final int ERROR_PAYMENTACCOUNT_NOT_SPECIFIED = 6602;
  public static final int ERROR_PAYMENTACCOUNT_DOES_NOT_EXIST = 6603;
  public static final int ERROR_AMOUNTPAID = 6604;
  public static final int ERROR_AMOUNTPAID_TOO_SMALL = 6605;
  public static final int ERROR_AMOUNTPAID_TOO_LARGE = 6606;
  public static final int ERROR_ERROR_INVALID_MODORDEL_EBILLACCOUNT = 6607;
  public static final int ERROR_INVALID_ACCOUNTNUMBER = 6608;
  public static final int ERROR_INVALID_ACCOUNTHOLDERSNAME = 6609;
  public static final int ERROR_INVALID_STATUSCODE = 6610;
  public static final int ERROR_INVALID_NICKNAME = 6611;
  public static final int ERROR_INVALID_STREET = 6612;
  public static final int ERROR_INVALID_STREET2 = 6613;
  public static final int ERROR_INVALID_CITY = 6614;
  public static final int ERROR_INVALID_STATE = 6615;
  public static final int ERROR_INVALID_COUNTRY = 6616;
  public static final int ERROR_INVALID_ZIPCODE = 6617;
  public static final int ERROR_INVALID_PHONE = 6618;
  public static final int ERROR_INVALID_PHONE2 = 6619;
  public static final int ERROR_INVALID_EMAIL = 6620;
  public static final int ERROR_MISSING_TCACCEPTED = 6621;
  public static final int ERROR_INVALID_TCACCEPTED = 6622;
  public static final int ERROR_MISSING_BILLDUENOTIFYDESIRED = 6623;
  public static final int ERROR_INVALID_BILLDUENOTIFYDESIRED = 6624;
  public static final int ERROR_MISSING_NEWBILLNOTIFYDESIRED = 6625;
  public static final int ERROR_INVALID_NEWBILLNOTIFYDESIRED = 6626;
  public static final int ERROR_MISSING_ACCOUNTINFONOTIFYDESIRED = 6627;
  public static final int ERROR_INVALID_ACCOUNTINFONOTIFYDESIRED = 6628;
  public static final int ERROR_MISSING_NEWBILLERNOTIFYDESIRED = 6629;
  public static final int ERROR_INVALID_NEWBILLERNOTIFYDESIRED = 6630;
  public static final int ERROR_MISSING_NEWBILLERNOTIFIEDDATE = 6631;
  public static final int ERROR_FINDING_BILLSUMMARY = 6700;
  public static final int ERROR_FINDING_CONSUMER = 6701;
  public static final int ERROR_FINDING_EBILLACCOUNT = 6702;
  public static final int ERROR_FINDING_PAYEE = 6703;
  public static final int ERROR_FINDING_BILLER = 6704;
  public static final int ERROR_INVALID_CONSUMERID = 6800;
  public static final int ERROR_NO_PAYMENT_TO_MAKE = 6900;
  public static final int ERROR_SENDING_PAYMENT = 6901;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.Task
 * JD-Core Version:    0.7.0.1
 */