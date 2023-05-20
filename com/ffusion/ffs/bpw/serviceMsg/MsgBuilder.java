package com.ffusion.ffs.bpw.serviceMsg;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import java.util.HashMap;

public class MsgBuilder
  implements DBConsts
{
  private static HashMap jdField_if;
  private static HashMap jdField_do;
  private static String a = "INFO";
  private static String ERROR = "ERROR";
  public static final String MESSAGE_0 = "Success";
  public static final String MESSAGE_86 = "Service provider is unavailable";
  public static final String MESSAGE_888 = "No record found for this transfer";
  public static final String MESSAGE_2000 = "General error";
  public static final String MESSAGE_2002 = "General account error";
  public static final String MESSAGE_2006 = "Source account not found";
  public static final String MESSAGE_2007 = "Source account closed";
  public static final String MESSAGE_2008 = "Source account not authorized";
  public static final String MESSAGE_2009 = "Destination account not found";
  public static final String MESSAGE_2010 = "Destination account closed";
  public static final String MESSAGE_2011 = "Destination account not authorized";
  public static final String MESSAGE_2012 = "Invalid amount";
  public static final String MESSAGE_2014 = "Date too soon";
  public static final String MESSAGE_2015 = "Date too far in future";
  public static final String MESSAGE_2016 = "Already committed";
  public static final String MESSAGE_2017 = "Already canceled";
  public static final String MESSAGE_2018 = "Unknown server ID";
  public static final String MESSAGE_2019 = "Duplicate request";
  public static final String MESSAGE_10501 = "Invalid payee";
  public static final String MESSAGE_10503 = "Invalid payee account number";
  public static final String MESSAGE_10504 = "Insufficient funds";
  public static final String MESSAGE_10508 = "Invalid Frequency";
  public static final String MESSAGE_10510 = "Invalid payee ID";
  public static final String MESSAGE_10514 = "Transaction already processed";
  public static final String MESSAGE_10515 = "Payee not modifiable by client";
  public static final String MESSAGE_10517 = "Invalid payee name";
  public static final String MESSAGE_10519 = "Invalid payee list ID";
  public static final String MESSAGE_10505 = "Can not modify element";
  public static final String MESSAGE_17000 = "Funds approval error";
  public static final String MESSAGE_17001 = "Fund allocation error";
  public static final String MESSAGE_17002 = "Signon is successful";
  public static final String MESSAGE_17003 = "Enrolled successfully";
  public static final String MESSAGE_17004 = "Failed to enroll";
  public static final String MESSAGE_100000 = "Invalid due date";
  public static final String MESSAGE_100001 = "Invalid number of instances";
  public static final String MESSAGE_ServerError = "Server internal error";
  public static final String MESSAGE_MultiplePayAcctsNotSupported = "Multiple pay accounts not supported";
  public static final String MESSAGE_UnsupportedRequest = "Unsupported request type";
  public static final String MESSAGE_TokenOnlyNotSupported = "Token only requests not supported";
  public static final String MESSAGE_DateFormatError = "Date format not correct";
  public static final String MESSAGE_InvalidPayeeDelete = "Invalid payee delete";
  public static final String MESSAGE_InvalidPayeeMod = "Invalid payee modification";
  public static final int CODE_Success = 0;
  public static final int CODE_SrcPrvUnavl = 86;
  public static final int CODE_NoRecord = 888;
  public static final int CODE_GeneralError = 2000;
  public static final int CODE_GeneralAcctError = 2002;
  public static final int CODE_SrcAcctNotFound = 2006;
  public static final int CODE_SrcAcctClosed = 2007;
  public static final int CODE_SrcAcctNotAuth = 2008;
  public static final int CODE_DstAcctNotFound = 2009;
  public static final int CODE_DstAcctClosed = 2010;
  public static final int CODE_DstAcctNotAuth = 2011;
  public static final int CODE_InvalidAmt = 2012;
  public static final int CODE_DateTooSoon = 2014;
  public static final int CODE_DateTooFar = 2015;
  public static final int CODE_AlreadyCommit = 2016;
  public static final int CODE_AlreadyCancel = 2017;
  public static final int CODE_UnknownSrvrID = 2018;
  public static final int CODE_DuplicateRequest = 2019;
  public static final int CODE_InvalidPayee = 10501;
  public static final int CODE_InvalidPayeeAcct = 10503;
  public static final int CODE_InsufficientFund = 10504;
  public static final int CODE_InvalidFrequency = 10508;
  public static final int CODE_InvalidPayeeID = 10510;
  public static final int CODE_TransactionProcessed = 10514;
  public static final int CODE_PayeeNotModifiable = 10515;
  public static final int CODE_InvalidPayeeName = 10517;
  public static final int CODE_InvalidPayeeListID = 10519;
  public static final int CODE_InvalidModification = 10505;
  public static final int CODE_FundsApprovalError = 17000;
  public static final int CODE_FundsAllocationError = 17001;
  public static final int CODE_SignonSuccess = 17002;
  public static final int CODE_EnrollSuccess = 17003;
  public static final int CODE_FailToEnroll = 17004;
  public static final int CODE_InvalidDueDate = 100000;
  public static final int CODE_InvalidInstanceNum = 100001;
  
  public static String getCmStatusMessage(int paramInt)
  {
    String str1 = null;
    try
    {
      str1 = BPWLocaleUtil.getMessage(paramInt, null, "OFX_MESSAGE");
    }
    catch (Throwable localThrowable1)
    {
      FFSDebug.log(localThrowable1, "MsgBuilder.getCmStatusMessage: Exception caught on status code = " + paramInt, 6);
    }
    if (str1 == null)
    {
      if (paramInt != 0) {
        try
        {
          paramInt = 2000;
          str1 = BPWLocaleUtil.getMessage(paramInt, null, "OFX_MESSAGE");
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(localThrowable2, "MsgBuilder.getCmStatusMessage: Exception caught on status code = 2000", 6);
        }
      }
      if (str1 == null)
      {
        String str2 = Integer.toString(paramInt);
        str1 = (String)jdField_if.get(str2);
      }
    }
    return str1;
  }
  
  public static com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum getOFX151CmStatusSeverity(int paramInt)
  {
    String str1 = Integer.toString(paramInt);
    String str2 = (String)jdField_do.get(str1);
    if ((str2 != null) && (str2.equals(a) == true)) {
      return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.INFO;
    }
    return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumSeverityEnum.ERROR;
  }
  
  public static com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum getOFX200CmStatusSeverity(int paramInt)
  {
    String str1 = Integer.toString(paramInt);
    String str2 = (String)jdField_do.get(str1);
    if ((str2 != null) && (str2.equals(a) == true)) {
      return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.INFO;
    }
    return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumSeverityEnum.ERROR;
  }
  
  public static String getAcctType(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum paramEnumAccountEnum)
    throws BPWException
  {
    String str = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.ValueSetAccountEnum.getValue(paramEnumAccountEnum.value());
    return str;
  }
  
  public static String getAcctType(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum paramEnumAccountEnum)
    throws BPWException
  {
    String str = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetAccountEnum.getValue(paramEnumAccountEnum.value());
    return str;
  }
  
  public static String getAcctType(TypeBCCAcctFromV1Un paramTypeBCCAcctFromV1Un)
    throws BPWException
  {
    String str = paramTypeBCCAcctFromV1Un.__memberName;
    Object localObject = null;
    if (str.equals("BankAcctFrom")) {
      return getAcctType(paramTypeBCCAcctFromV1Un.BankAcctFrom.AcctType);
    }
    if (str.equals("CCAcctFrom")) {
      return "CREDITCARD";
    }
    throw new BPWException(" Wrong acctfrom aggregate.");
  }
  
  public static String getAcctType(TypeBCCAcctFromUn paramTypeBCCAcctFromUn)
    throws BPWException
  {
    String str = paramTypeBCCAcctFromUn.__memberName;
    Object localObject = null;
    if (str.equals("BankAcctFrom")) {
      return getAcctType(paramTypeBCCAcctFromUn.BankAcctFrom.AcctType);
    }
    if (str.equals("CCAcctFrom")) {
      return "CREDITCARD";
    }
    throw new BPWException(" Wrong acctfrom aggregate.");
  }
  
  public static com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum getOFX151AcctEnum(String paramString)
    throws BPWException
  {
    try
    {
      int i = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.ValueSetAccountEnum.getIndex(paramString);
      return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum.from_int(i);
    }
    catch (Throwable localThrowable)
    {
      throw new BPWException(localThrowable, "Wrong account type: " + paramString);
    }
  }
  
  public static com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum getOFX200AcctEnum(String paramString)
    throws BPWException
  {
    try
    {
      int i = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetAccountEnum.getIndex(paramString);
      return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum.from_int(i);
    }
    catch (Throwable localThrowable)
    {
      throw new BPWException(localThrowable, "Wrong account type: " + paramString);
    }
  }
  
  public static String getAcctID(TypeBCCAcctFromV1Un paramTypeBCCAcctFromV1Un)
    throws BPWException
  {
    String str = paramTypeBCCAcctFromV1Un.__memberName;
    Object localObject = null;
    if (str.equals("BankAcctFrom")) {
      return paramTypeBCCAcctFromV1Un.BankAcctFrom.AcctID;
    }
    if (str.equals("CCAcctFrom")) {
      return paramTypeBCCAcctFromV1Un.CCAcctFrom.AcctID;
    }
    throw new BPWException("Wrong acctfrom aggregate.");
  }
  
  public static String getAcctID(TypeBCCAcctFromUn paramTypeBCCAcctFromUn)
    throws BPWException
  {
    String str = paramTypeBCCAcctFromUn.__memberName;
    Object localObject = null;
    if (str.equals("BankAcctFrom")) {
      return paramTypeBCCAcctFromUn.BankAcctFrom.AcctID;
    }
    if (str.equals("CCAcctFrom")) {
      return paramTypeBCCAcctFromUn.CCAcctFrom.AcctID;
    }
    throw new BPWException("Wrong acctfrom aggregate.");
  }
  
  public static String getBankID(TypeBCCAcctFromV1Un paramTypeBCCAcctFromV1Un)
    throws BPWException
  {
    String str = paramTypeBCCAcctFromV1Un.__memberName;
    Object localObject = null;
    if (str.equals("BankAcctFrom")) {
      return paramTypeBCCAcctFromV1Un.BankAcctFrom.BankID;
    }
    if (str.equals("CCAcctFrom")) {
      return null;
    }
    throw new BPWException("Wrong acctfrom aggregate.");
  }
  
  public static String getBankID(TypeBCCAcctFromUn paramTypeBCCAcctFromUn)
    throws BPWException
  {
    String str = paramTypeBCCAcctFromUn.__memberName;
    Object localObject = null;
    if (str.equals("BankAcctFrom")) {
      return paramTypeBCCAcctFromUn.BankAcctFrom.BankID;
    }
    if (str.equals("CCAcctFrom")) {
      return null;
    }
    throw new BPWException("Wrong acctfrom aggregate.");
  }
  
  public static String getCurrencyString(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    try
    {
      String str = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.ValueSetCurrencyEnum.getValue(paramEnumCurrencyEnum.value());
      return str;
    }
    catch (Throwable localThrowable)
    {
      throw new BPWException(localThrowable, "Wrong currency EnumCurrencyEnum: " + paramEnumCurrencyEnum);
    }
  }
  
  public static String getCurrencyString(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    try
    {
      String str = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetCurrencyEnum.getValue(paramEnumCurrencyEnum.value());
      return str;
    }
    catch (Throwable localThrowable)
    {
      throw new BPWException(localThrowable, "Wrong currency EnumCurrencyEnum: " + paramEnumCurrencyEnum);
    }
  }
  
  public static com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum getOFX151CurrencyEnum(String paramString)
    throws BPWException
  {
    try
    {
      int i = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.ValueSetCurrencyEnum.getIndex(paramString);
      return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum.from_int(i);
    }
    catch (Throwable localThrowable)
    {
      throw new BPWException(localThrowable, "Wrong currency string: " + paramString);
    }
  }
  
  public static com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum getOFX200CurrencyEnum(String paramString)
    throws BPWException
  {
    try
    {
      int i = com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetCurrencyEnum.getIndex(paramString);
      return com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum.from_int(i);
    }
    catch (Throwable localThrowable)
    {
      throw new BPWException(localThrowable, "Wrong currency string: " + paramString);
    }
  }
  
  static
  {
    jdField_if = new HashMap();
    jdField_if.put(Integer.toString(0), "Success");
    jdField_if.put(Integer.toString(86), "Service provider is unavailable");
    jdField_if.put(Integer.toString(888), "No record found for this transfer");
    jdField_if.put(Integer.toString(2000), "General error");
    jdField_if.put(Integer.toString(2002), "General account error");
    jdField_if.put(Integer.toString(2006), "Source account not found");
    jdField_if.put(Integer.toString(2007), "Source account closed");
    jdField_if.put(Integer.toString(2008), "Source account not authorized");
    jdField_if.put(Integer.toString(2009), "Destination account not found");
    jdField_if.put(Integer.toString(2010), "Destination account closed");
    jdField_if.put(Integer.toString(2011), "Destination account not authorized");
    jdField_if.put(Integer.toString(2012), "Invalid amount");
    jdField_if.put(Integer.toString(2014), "Date too soon");
    jdField_if.put(Integer.toString(2015), "Date too far in future");
    jdField_if.put(Integer.toString(2016), "Already committed");
    jdField_if.put(Integer.toString(2017), "Already canceled");
    jdField_if.put(Integer.toString(2018), "Unknown server ID");
    jdField_if.put(Integer.toString(2019), "Duplicate request");
    jdField_if.put(Integer.toString(10501), "Invalid payee");
    jdField_if.put(Integer.toString(10503), "Invalid payee account number");
    jdField_if.put(Integer.toString(10504), "Insufficient funds");
    jdField_if.put(Integer.toString(10508), "Invalid Frequency");
    jdField_if.put(Integer.toString(10510), "Invalid payee ID");
    jdField_if.put(Integer.toString(10514), "Transaction already processed");
    jdField_if.put(Integer.toString(10515), "Payee not modifiable by client");
    jdField_if.put(Integer.toString(10517), "Invalid payee name");
    jdField_if.put(Integer.toString(10519), "Invalid payee list ID");
    jdField_if.put(Integer.toString(10505), "Can not modify element");
    jdField_if.put(Integer.toString(17000), "Funds approval error");
    jdField_if.put(Integer.toString(17001), "Fund allocation error");
    jdField_if.put(Integer.toString(17002), "Signon is successful");
    jdField_if.put(Integer.toString(17003), "Enrolled successfully");
    jdField_if.put(Integer.toString(17004), "Failed to enroll");
    jdField_if.put(Integer.toString(100000), "Invalid due date");
    jdField_if.put(Integer.toString(100001), "Invalid number of instances");
    jdField_do = new HashMap();
    jdField_do.put(Integer.toString(0), a);
    jdField_do.put(Integer.toString(86), ERROR);
    jdField_do.put(Integer.toString(888), ERROR);
    jdField_do.put(Integer.toString(2000), ERROR);
    jdField_do.put(Integer.toString(2002), ERROR);
    jdField_do.put(Integer.toString(2006), ERROR);
    jdField_do.put(Integer.toString(2007), ERROR);
    jdField_do.put(Integer.toString(2008), ERROR);
    jdField_do.put(Integer.toString(2009), ERROR);
    jdField_do.put(Integer.toString(2010), ERROR);
    jdField_do.put(Integer.toString(2011), ERROR);
    jdField_do.put(Integer.toString(2012), ERROR);
    jdField_do.put(Integer.toString(2014), ERROR);
    jdField_do.put(Integer.toString(2015), ERROR);
    jdField_do.put(Integer.toString(2016), ERROR);
    jdField_do.put(Integer.toString(2017), ERROR);
    jdField_do.put(Integer.toString(2018), ERROR);
    jdField_do.put(Integer.toString(2019), ERROR);
    jdField_do.put(Integer.toString(10501), ERROR);
    jdField_do.put(Integer.toString(10503), ERROR);
    jdField_do.put(Integer.toString(10504), ERROR);
    jdField_do.put(Integer.toString(10508), ERROR);
    jdField_do.put(Integer.toString(10510), ERROR);
    jdField_do.put(Integer.toString(10514), ERROR);
    jdField_do.put(Integer.toString(10515), ERROR);
    jdField_do.put(Integer.toString(10517), ERROR);
    jdField_do.put(Integer.toString(10519), ERROR);
    jdField_do.put(Integer.toString(10505), ERROR);
    jdField_do.put(Integer.toString(100000), ERROR);
    jdField_do.put(Integer.toString(100001), ERROR);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.serviceMsg.MsgBuilder
 * JD-Core Version:    0.7.0.1
 */