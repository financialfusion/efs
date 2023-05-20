package com.ffusion.util.logging;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.enums.UserAssignedAmount;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class AuditLogRecord
  extends LogRecord
{
  public static final String USERID = "USERID";
  public static final String AGENTID = "AGENTID";
  public static final String AGENTTYPE = "AGENTTYPE";
  public static final String TRANSACTIONID = "TRANSACTIONID";
  public static final String TRANSACTIONDATE = "TRANSACTIONDATE";
  public static final String TRANSACTIONTYPE = "TRANSACTIONTYPE";
  public static final String BUSINESSID = "BUSINESSID";
  public static final String AMOUNT = "AMOUNT";
  public static final String CURRENCY_CODE = "CURRENCY_CODE";
  public static final String TO_AMOUNT = "TO_AMOUNT";
  public static final String TO_AMOUNT_CURRENCY = "TO_AMOUNT_CURRENCY";
  public static final String USER_ASSIGNED_AMOUNT = "USER_ASSIGNED_AMOUNT";
  public static final String SRVR_TID = "SRVR_TID";
  public static final String STATE = "STATE";
  public static final String TO_ACCT_ID = "TO_ACCT_ID";
  public static final String TO_ACCT_RTG_NUM = "TO_ACCT_RTG_NUM";
  public static final String FROM_ACCT_ID = "FROM_ACCT_ID";
  public static final String FROM_ACCT_RTG_NUM = "FROM_ACCT_RTG_NUM";
  public static final String MODULE = "MODULE";
  public static final String AUDIT_LOG_RECORD = "AUDIT_LOG_RECORD";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  protected String userID;
  protected String agentID;
  protected String agentType;
  protected String tranID;
  protected int businessID;
  protected BigDecimal amount;
  protected String currencyCode;
  protected BigDecimal toAmount;
  protected String toAmountCurrency;
  protected UserAssignedAmount userAssignedAmount;
  protected String srvr_tid;
  protected int tranType;
  protected String state;
  protected String toAcctID;
  protected String toAcctRtgNum;
  protected String fromAcctID;
  protected String fromAcctRtgNum;
  protected int module;
  protected long timeInMillis;
  protected Locale locale = LocaleUtil.getDefaultLocale();
  ILocalizable a;
  protected int primaryUserID;
  
  public AuditLogRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt3)
  {
    this(paramString1, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt3, null);
  }
  
  public AuditLogRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt3, Locale paramLocale)
  {
    this(paramString1, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt3, paramLocale);
  }
  
  public AuditLogRecord(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt3)
  {
    this(paramString1, 0, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt3, null);
  }
  
  public AuditLogRecord(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt3, Locale paramLocale)
  {
    this(paramString1, 0, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt3, paramLocale);
  }
  
  public AuditLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt4)
  {
    this(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal, paramString5, null, null, null, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt4, null);
  }
  
  public AuditLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt4, Locale paramLocale)
  {
    this(paramString1, 0, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal, paramString5, null, null, null, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt4, paramLocale);
  }
  
  public AuditLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal1, String paramString5, BigDecimal paramBigDecimal2, String paramString6, UserAssignedAmount paramUserAssignedAmount, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt4)
  {
    this(paramString1, paramInt1, paramString2, paramString3, paramILocalizable, paramString4, paramInt2, paramInt3, paramBigDecimal1, paramString5, paramBigDecimal2, paramString6, paramUserAssignedAmount, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt4, null);
  }
  
  public AuditLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt2, int paramInt3, BigDecimal paramBigDecimal1, String paramString5, BigDecimal paramBigDecimal2, String paramString6, UserAssignedAmount paramUserAssignedAmount, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt4, Locale paramLocale)
  {
    super(Level.SEVERE, "");
    this.a = paramILocalizable;
    this.userID = paramString1;
    if ((paramInt1 == 0) && (paramString1 != null) && (!paramString1.trim().equals(""))) {
      this.primaryUserID = Integer.parseInt(paramString1);
    } else {
      this.primaryUserID = paramInt1;
    }
    this.agentID = paramString2;
    this.agentType = paramString3;
    this.tranID = paramString4;
    this.tranType = paramInt2;
    this.businessID = paramInt3;
    this.amount = paramBigDecimal1;
    this.currencyCode = paramString5;
    this.toAmount = paramBigDecimal2;
    this.toAmountCurrency = paramString6;
    this.userAssignedAmount = paramUserAssignedAmount;
    this.srvr_tid = paramString7;
    this.state = paramString8;
    this.toAcctID = paramString9;
    this.toAcctRtgNum = paramString10;
    this.fromAcctID = paramString11;
    this.fromAcctRtgNum = paramString12;
    this.module = paramInt4;
    this.timeInMillis = System.currentTimeMillis();
    setLocale(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      paramLocale = LocaleUtil.getDefaultLocale();
    }
    this.locale = paramLocale;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public String getAgentID()
  {
    return this.agentID;
  }
  
  public void setAgentID(String paramString)
  {
    this.agentID = paramString;
  }
  
  public String getAgentType()
  {
    return this.agentType;
  }
  
  public void setAgentType(String paramString)
  {
    this.agentType = paramString;
  }
  
  public String getTranID()
  {
    return this.tranID;
  }
  
  public void setTranID(String paramString)
  {
    this.tranID = paramString;
  }
  
  public int getTranType()
  {
    return this.tranType;
  }
  
  public String getTranTypeText()
  {
    return getTranTypeText(this.locale);
  }
  
  public String getTranTypeText(Locale paramLocale)
  {
    return ResourceUtil.getString("TransactionTypeText" + this.tranType, "com.ffusion.beans.banking.resources", paramLocale);
  }
  
  public void setTranType(int paramInt)
  {
    this.tranType = paramInt;
  }
  
  public int getBusinessID()
  {
    return this.businessID;
  }
  
  public void setBusinessID(int paramInt)
  {
    this.businessID = paramInt;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.amount = paramBigDecimal;
  }
  
  public void setAmount(String paramString)
  {
    this.amount = new BigDecimal(paramString);
  }
  
  public String getAmount()
  {
    if (this.amount == null) {
      return null;
    }
    return this.amount.toString();
  }
  
  public BigDecimal getAmountValue()
  {
    return this.amount;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.currencyCode = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCode;
  }
  
  public void setToAmount(BigDecimal paramBigDecimal)
  {
    this.toAmount = paramBigDecimal;
  }
  
  public void setToAmount(String paramString)
  {
    this.toAmount = new BigDecimal(paramString);
  }
  
  public String getToAmount()
  {
    if (this.toAmount == null) {
      return null;
    }
    return this.toAmount.toString();
  }
  
  public BigDecimal getToAmountValue()
  {
    return this.toAmount;
  }
  
  public void setToAmountCurrency(String paramString)
  {
    this.toAmountCurrency = paramString;
  }
  
  public String getToAmountCurrency()
  {
    return this.toAmountCurrency;
  }
  
  public void setUserAssignedAmount(UserAssignedAmount paramUserAssignedAmount)
  {
    this.userAssignedAmount = paramUserAssignedAmount;
  }
  
  public UserAssignedAmount getUserAssignedAmount()
  {
    return this.userAssignedAmount;
  }
  
  public void setSrvrTid(String paramString)
  {
    this.srvr_tid = paramString;
  }
  
  public String getSrvrTid()
  {
    return this.srvr_tid;
  }
  
  public void setState(String paramString)
  {
    this.state = paramString;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setToAcctID(String paramString)
  {
    this.toAcctID = paramString;
  }
  
  public String getToAcctID()
  {
    return this.toAcctID;
  }
  
  public void setToAcctRtgNum(String paramString)
  {
    this.toAcctRtgNum = paramString;
  }
  
  public String getToAcctRtgNum()
  {
    return this.toAcctRtgNum;
  }
  
  public void setFromAcctID(String paramString)
  {
    this.fromAcctID = paramString;
  }
  
  public String getFromAcctID()
  {
    return this.fromAcctID;
  }
  
  public void setFromAcctRtgNum(String paramString)
  {
    this.fromAcctRtgNum = paramString;
  }
  
  public String getFromAcctRtgNum()
  {
    return this.fromAcctRtgNum;
  }
  
  public void setModule(int paramInt)
  {
    this.module = paramInt;
  }
  
  public int getModule()
  {
    return this.module;
  }
  
  public ILocalizable getLocalizableMessage()
  {
    return this.a;
  }
  
  public String getMessage()
  {
    return getMessage(this.locale);
  }
  
  public String getMessage(Locale paramLocale)
  {
    return (String)this.a.localize(paramLocale);
  }
  
  public void setMessage(String paramString)
  {
    super.setMessage(paramString);
    this.a = new LocalizableString("dummy", paramString, null);
  }
  
  public void setLocalizableMessage(ILocalizable paramILocalizable)
  {
    this.a = paramILocalizable;
  }
  
  public void setPrimaryUserID(int paramInt)
  {
    this.primaryUserID = paramInt;
  }
  
  public int getPrimaryUserID()
  {
    return this.primaryUserID;
  }
  
  public long getTimeInMillis()
  {
    return this.timeInMillis;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("userID=").append(this.userID).append(",");
    localStringBuffer.append("agentID=").append(this.agentID).append(",");
    localStringBuffer.append("desc=").append(getMessage()).append(",");
    localStringBuffer.append("tranID=").append(this.tranID).append(",");
    localStringBuffer.append("businessID=").append(this.businessID).append(",");
    localStringBuffer.append("amount=").append(this.amount).append(",");
    localStringBuffer.append("currencyCode=").append(this.currencyCode).append(",");
    localStringBuffer.append("toAmount=").append(this.toAmount).append(",");
    localStringBuffer.append("toAmountCurrency=").append(this.toAmountCurrency).append(",");
    localStringBuffer.append("userAssignedAmount=");
    if (this.userAssignedAmount != null) {
      localStringBuffer.append(this.userAssignedAmount.getName());
    }
    localStringBuffer.append(",");
    localStringBuffer.append("state=").append(this.state).append(",");
    localStringBuffer.append("toAcctID=").append(this.toAcctID).append(",");
    localStringBuffer.append("toAcctRtgNum=").append(this.toAcctRtgNum).append(",");
    localStringBuffer.append("fromAcctID=").append(this.fromAcctID).append(",");
    localStringBuffer.append("fromAcctRtgNum=").append(this.fromAcctRtgNum).append(",");
    localStringBuffer.append("module=").append(this.module).append(",");
    localStringBuffer.append("primaryUserID=").append(this.primaryUserID).append(",");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.AuditLogRecord
 * JD-Core Version:    0.7.0.1
 */