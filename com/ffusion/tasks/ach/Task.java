package com.ffusion.tasks.ach;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String ACHENTRY = "ACHEntry";
  public static final String ACHBATCH = "ACHBatch";
  public static final String ACHBATCHES = "ACHBatches";
  public static final String RECACHBATCH = "RecACHBatch";
  public static final String RECACHBATCHES = "RecACHBatches";
  public static final String REPORT_DATA = "ReportData";
  public static final String ACHCOMPANIES = "ACHCOMPANIES";
  public static final String ACHCOMPANY = "ACHCOMPANY";
  public static final String ACHPAYEE = "ACHPAYEE";
  public static final String ACHPAYEES = "ACHPAYEES";
  public static final String FILTERED_ACHCOMPANIES = "FILTERED_ACHCOMPANIES";
  public static final String FILTERED_ACHPAYEES = "FILTERED_ACHPAYEES";
  public static final String ACHOFFSETACCOUNTS = "ACHOffsetAccounts";
  public static final String ACHOFFSETACCOUNTSADD = "ACHOffsetAccountsAdd";
  public static final String ACHOFFSETACCOUNTSDEL = "ACHOffsetAccountsDel";
  public static final String ACHOFFSETACCOUNT = "ACHOffsetAccount";
  public static final String FILTERED_ACHOFFSETACCOUNTS = "FilteredACHOffsetAccounts";
  public static final String ACCOUNTS = "Accounts";
  public static final String ACHCOMPANYMODEADD = "ADD";
  public static final String ACHCOMPANYMODEMODIFY = "MODIFY";
  public static final String TAXPAYMENT = "TaxPayment";
  public static final String CHILDSUPPORTPAYMENT = "ChildSupportPayment";
  public static final int MAX_BATCHNAME_LENGTH = 128;
  public static final int ERROR_NO_ACHENTRY = 16001;
  public static final int ERROR_NO_ACHBATCH = 16002;
  public static final int ERROR_NO_RECACHBATCH = 16004;
  public static final int ERROR_NO_ACCOUNTS = 16009;
  public static final int ERROR_NO_ACHBATCHES = 16010;
  public static final int ERROR_NO_RECACHBATCHES = 16011;
  public static final int ERROR_NO_REPORT_CRITERIA = 16012;
  public static final int ERROR_NO_ACHOFFSETACCOUNTS = 16013;
  public static final int ERROR_NO_ACHOFFSETACCOUNT = 16014;
  public static final int ERROR_NO_ACHENTRIES = 16015;
  public static final int ERROR_CANNOT_REVERSE_BATCH = 16016;
  public static final int ERROR_BANKNAME = 16100;
  public static final int ERROR_ROUTINGNUMBER = 16101;
  public static final int ERROR_PAYEENAME = 16102;
  public static final int ERROR_PAYEENAMEEXISTS = 16103;
  public static final int ERROR_PAYEEACCOUNTNUMBER = 16104;
  public static final int ERROR_PAYEEACCOUNTTYPE = 16105;
  public static final int ERROR_PAYEEDESCRIPTION = 16106;
  public static final int ERROR_FROMACCOUNT = 16107;
  public static final int ERROR_BATCHNAME = 16108;
  public static final int ERROR_USERID = 16109;
  public static final int ERROR_ACHCOMPANY_CUSTOMER_ID = 16110;
  public static final int ERROR_ACHCOMPANY_COMPANY_ID = 16111;
  public static final int ERROR_ACHCOMPANY_COMPANY_NAME = 16112;
  public static final int ERROR_DUPLICATEPAYEEEXISTS = 16113;
  public static final int ERROR_DUPLICATE_ACHCOMPANY_COMPANY_NAME = 16114;
  public static final int ERROR_AMOUNT = 16117;
  public static final int ERROR_DATE = 16118;
  public static final int ERROR_FREQUENCY = 16119;
  public static final int ERROR_NUMBER_OF_TRANSFERS = 16120;
  public static final int ERROR_STATUSCODE = 16121;
  public static final int ERROR_MIN_AMOUNT = 16122;
  public static final int ERROR_MAX_AMOUNT = 16123;
  public static final int ERROR_AMOUNT_TOO_SMALL = 16124;
  public static final int ERROR_AMOUNT_TOO_LARGE = 16125;
  public static final int ERROR_DATE_TOO_EARLY = 16126;
  public static final int ERROR_INVALID_START_DATE = 16127;
  public static final int ERROR_INVALID_END_DATE = 16128;
  public static final int ERROR_END_BEFORE_START_DATE = 16129;
  public static final int ERROR_ACHTYPE = 16130;
  public static final int ERROR_BANKID_TOO_SHORT = 16133;
  public static final int ERROR_BANKID_TOO_LONG = 16134;
  public static final int ERROR_BATCHSIZE = 16135;
  public static final int ERROR_TRANSACTIONCODE = 16136;
  public static final int ERROR_CODISCRETIONARYDATA = 16137;
  public static final int ERROR_COID = 16138;
  public static final int ERROR_STDENTRYCLASSCODE = 16139;
  public static final int ERROR_COENTRYDESC = 16140;
  public static final int ERROR_ORIGFIID = 16141;
  public static final int ERROR_TAX_FIELD_TOO_SHORT = 16142;
  public static final int ERROR_TAX_FIELD_TOO_LARGE = 16143;
  public static final int ERROR_TAX_FIELD_INVALID = 16144;
  public static final int ERROR_REQUIRED_TAX_FIELD_MISSING = 16145;
  public static final int ERROR_ACCOUNTNUMBER = 16146;
  public static final int ERROR_AMOUNTZERO = 16147;
  public static final int ERROR_AMOUNTZEROADDENDA = 16148;
  public static final int ERROR_AMOUNTZEROPRENOTE = 16149;
  public static final int ERROR_NO_ACHPAYEES = 16150;
  public static final int ERROR_INVALID_ACHPAYEE = 16151;
  public static final int ERROR_ACHPAYEE_NOT_FOUND = 16152;
  public static final int ERROR_ACHENTRY_CHECK_SER_NUM = 16153;
  public static final int ERROR_ACHENTRY_TERMINAL_CITY = 16154;
  public static final int ERROR_ACHENTRY_TERMINAL_STATE = 16155;
  public static final int ERROR_ACHENTRY_PROCESS_CTRL = 16156;
  public static final int ERROR_ACHENTRY_ITEM_RESEARCH = 16157;
  public static final int ERROR_COENTRYDESC_REQUIRED = 16158;
  public static final int ERROR_VALIDATION_ISSUES = 16159;
  public static final int ERROR_TAXFORM_NOT_FOUND = 16160;
  public static final int ERROR_TAXFORM_ALREADY_ADDED = 16161;
  public static final int ERROR_TAX_FIELD_REPEATED = 16162;
  public static final int ERROR_CANT_MODIFY_SECC = 16165;
  public static final int ERROR_CANT_MODIFY_RECURRING = 16166;
  public static final int ERROR_TAX_FIELD_LENGTH_5 = 16167;
  public static final int ERROR_TAX_FIELD_LENGTH_6 = 16168;
  public static final int ERROR_TAX_FIELD_LENGTH_9 = 16169;
  public static final int ERROR_TAX_FIELD_LENGTH_10 = 16170;
  public static final int ERROR_TAX_FIELD_LENGTH_15 = 16171;
  public static final int ERROR_TAX_FIELD_MISSING_TXPID = 16172;
  public static final int ERROR_TAX_FIELD_MISSING_TXPMTCODE = 16173;
  public static final int ERROR_TAX_FIELD_MISSING_TXPERDATE = 16174;
  public static final int ERROR_TAX_FIELD_MISSING_TXAMT = 16175;
  public static final int ERROR_TAX_FIELD_LENGTH_9SSN = 16176;
  public static final int ERROR_TAX_FIELD_INVALID_CHARACTERS = 16177;
  public static final int ERROR_ACH_SECURE_PAYEE = 16180;
  public static final int ERROR_DATE_TOO_LATE = 16181;
  public static final int ERROR_SMART_CALENDAR = 16182;
  public static final int ERROR_BANK_LOOKUP_ROUTINGNUMBER = 16183;
  public static final int ERROR_ZERO_NUMBER_PAYMENTS = 16184;
  public static final int ERROR_CAN_NOT_CHANGE_ACH_COMPANY = 16185;
  public static final int ERROR_CAN_NOT_CHANGE_TAX_FORM = 16186;
  public static final int ERROR_NO_DEFAULT_TAX_FORM = 16187;
  public static final int ERROR_ENTRY_TAX_FORM_NOT_COMPATIBLE = 16188;
  public static final int ERROR_EFFECTIVE_ENTRY_DATE_WEEKEND = 16189;
  public static final int ERROR_EFFECTIVE_ENTRY_DATE_HOLIDAY = 16190;
  public static final int ERROR_EFFECTIVE_ENTRY_DATE_BEFORE_EARLIEST = 16191;
  public static final int ERROR_ENTRIES_TURNED_TO_HOLD = 16192;
  public static final int ERROR_FINDING_ACHENTRY = 16300;
  public static final int ERROR_FINDING_RECACHENTRY = 16301;
  public static final int ERROR_FINDING_FROMACCOUNT = 16304;
  public static final int ERROR_NO_FI = 16330;
  public static final int ERROR_NO_FIS = 16331;
  public static final int ERROR_NO_ACH_PAYEE = 16332;
  public static final int ERROR_UNABLE_TO_SEND_ACHBATCHES = 16402;
  public static final int ERROR_ALREADY_PROCESSING = 16500;
  public static final int ERROR_INVALID_ACH_COMPANY_ID = 16501;
  public static final int ERROR_INVALID_COMPANY_NAME = 16502;
  public static final int ERROR_INVALID_FI_ID = 16503;
  public static final int ERROR_INVALID_COMP_ID = 16504;
  public static final int ERROR_NO_ACH_COMPANIES = 16505;
  public static final int ERROR_NO_ACH_COMPANY = 16506;
  public static final int ERROR_INVALID_OFFSET_ACCT_NUMBER = 16508;
  public static final int ERROR_OFFSET_ACCT_NUMBER_TOO_BIG = 16509;
  public static final int ERROR_OFFSET_ACCT_NUMBER_TOO_SMALL = 16510;
  public static final int ERROR_INVALID_OFFSET_ACCT_NAME = 16511;
  public static final int ERROR_OFFSET_ACCT_NAME_TOO_BIG = 16512;
  public static final int ERROR_OFFSET_ACCT_NAME_TOO_SMALL = 16513;
  public static final int ERROR_INVALID_OFFSET_ACCT_RTN_NUM = 16514;
  public static final int ERROR_INVALID_OFFSET_ACCT_RTN_NUM_REQUIRED = 16515;
  public static final int ERROR_INVALID_OFFSET_ACCT_TYPE = 16516;
  public static final int ERROR_INVALID_ACCTS_COLLECTION_TYPE = 16517;
  public static final int ERROR_BATCHNAME_TOO_LONG = 16518;
  public static final int ERROR_UNABLE_TO_ADD_ACHTEMPLATE = 16519;
  public static final int ERROR_UNABLE_TO_MODIFY_ACHTEMPLATE = 16520;
  public static final int ERROR_UNABLE_TO_DELETE_PAYEE = 16521;
  public static final int ERROR_BUSINESS_TEMPLATE_USES_USER_SCOPE_PAYEES = 16522;
  public static final int ERROR_CANT_USE_BUSINESS_TEMPLATE = 16523;
  public static final int ERROR_BALANCED_BATCH_COMPANY_REQUIRES_OFFSET_ACCOUNT = 16530;
  public static final int ERROR_HEADERCOMPNAME_ORIGPAYEE = 16531;
  public static final int ERROR_COENTRYDESC_ISREVERSAL = 16532;
  public static final int ERROR_IDENTIFICATION_NUMBER = 16533;
  public static final int ERROR_INVALID_CHARS_IDENTIFICATION_NUMBER = 16534;
  public static final int ERROR_HEADERCOMPNAME_CIE = 16535;
  public static final int ERROR_HEADERCOMPNAME_POP = 16536;
  public static final int ERROR_HEADERCOMPNAME_XCK = 16537;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.Task
 * JD-Core Version:    0.7.0.1
 */