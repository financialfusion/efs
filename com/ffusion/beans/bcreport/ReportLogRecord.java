package com.ffusion.beans.bcreport;

import com.ffusion.beans.DateTime;
import com.ffusion.util.Filterable;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.StringTokenizer;

public class ReportLogRecord
  extends AuditLogRecord
  implements Filterable, Sortable, Comparable, XMLable
{
  DateTime jdField_int;
  String jdField_if = "SHORT";
  String jdField_for;
  String jdField_do;
  
  public ReportLogRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt3)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt3);
  }
  
  public ReportLogRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt3, Locale paramLocale)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt3, paramLocale);
  }
  
  public ReportLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt4)
  {
    super(paramString1, paramInt1, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt2, paramInt3, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt4);
  }
  
  public ReportLogRecord(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, int paramInt4, Locale paramLocale)
  {
    super(paramString1, paramInt1, paramString2, paramString3, new LocalizableString("dummy", paramString4, null), paramString5, paramInt2, paramInt3, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramInt4, paramLocale);
  }
  
  public void setTranDate(DateTime paramDateTime)
  {
    this.jdField_int = paramDateTime;
  }
  
  public String getTranDate()
  {
    if (this.jdField_int == null) {
      return null;
    }
    this.jdField_int.setFormat(this.jdField_if);
    return this.jdField_int.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getDateFormat()
  {
    return this.jdField_if;
  }
  
  public void setTimestamp(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getTimestamp()
  {
    return this.jdField_for;
  }
  
  public void setProcessedBy(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getProcessedBy()
  {
    return this.jdField_do;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    ReportLogRecord localReportLogRecord = (ReportLogRecord)paramObject;
    int i;
    if (this.tranID.equalsIgnoreCase(localReportLogRecord.getTranID())) {
      i = 0;
    } else if (this.jdField_int.after(localReportLogRecord.getTranDate())) {
      i = 1;
    } else {
      i = -1;
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportLogRecord localReportLogRecord = (ReportLogRecord)paramObject;
    int i = 1;
    if ((paramString.equals("AGENTID")) && (this.agentID != null) && (localReportLogRecord.getAgentID() != null)) {
      i = this.agentID.compareTo(localReportLogRecord.getAgentID());
    } else if ((paramString.equals("AGENTTYPE")) && (this.agentType != null) && (localReportLogRecord.getAgentType() != null)) {
      i = this.agentType.compareTo(localReportLogRecord.getAgentType());
    } else if ((paramString.equals("TRANSACTIONID")) && (this.tranID != null) && (localReportLogRecord.getTranID() != null)) {
      i = this.tranID.compareTo(localReportLogRecord.getTranID());
    } else if ((paramString.equals("TRANSACTIONDATE")) && (this.jdField_int != null) && (localReportLogRecord.getTranDate() != null)) {
      i = this.jdField_int.equals(localReportLogRecord.getTranDate()) ? 0 : this.jdField_int.before(localReportLogRecord.getTranDate()) ? -1 : 1;
    } else if (paramString.equals("TRANSACTIONTYPE")) {
      i = this.tranType - localReportLogRecord.getTranType();
    } else if (paramString.equals("BUSINESSID")) {
      i = this.businessID - localReportLogRecord.getBusinessID();
    } else if ((paramString.equals("AMOUNT")) && (this.tranID != null) && (localReportLogRecord.getTranID() != null)) {
      i = this.amount.compareTo(new BigDecimal(localReportLogRecord.getAmount()));
    } else if ((paramString.equals("CURRENCY_CODE")) && (this.currencyCode != null) && (localReportLogRecord.getCurrencyCode() != null)) {
      i = this.currencyCode.compareTo(localReportLogRecord.getCurrencyCode());
    } else if ((paramString.equals("STATE")) && (this.state != null) && (localReportLogRecord.getSrvrTid() != null)) {
      i = this.state.compareTo(localReportLogRecord.getState());
    } else if ((paramString.equals("TO_ACCT_ID")) && (this.toAcctID != null) && (localReportLogRecord.getToAcctID() != null)) {
      i = this.toAcctID.compareTo(localReportLogRecord.getToAcctID());
    } else if ((paramString.equals("TO_ACCT_RTG_NUM")) && (this.toAcctRtgNum != null) && (localReportLogRecord.getToAcctRtgNum() != null)) {
      i = this.toAcctRtgNum.compareTo(localReportLogRecord.getToAcctRtgNum());
    } else if ((paramString.equals("FROM_ACCT_ID")) && (this.fromAcctID != null) && (localReportLogRecord.getFromAcctID() != null)) {
      i = this.fromAcctID.compareTo(localReportLogRecord.getFromAcctID());
    } else if ((paramString.equals("FROM_ACCT_RTG_NUM")) && (this.fromAcctRtgNum != null) && (localReportLogRecord.getFromAcctRtgNum() != null)) {
      i = this.fromAcctRtgNum.compareTo(localReportLogRecord.getFromAcctRtgNum());
    } else if (paramString.equals("MODULE")) {
      i = this.module - localReportLogRecord.getModule();
    }
    return i;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      if ((str1.equals("AGENTID")) && (this.agentID != null)) {
        return isFilterable(this.agentID, str2, str3);
      }
      if ((str1.equals("AGENTTYPE")) && (this.agentType != null)) {
        return isFilterable(this.agentType, str2, str3);
      }
      if ((str1.equals("TRANSACTIONID")) && (this.tranID != null)) {
        return isFilterable(this.tranID, str2, str3);
      }
      if ((str1.equals("TRANSACTIONDATE")) && (this.jdField_int != null)) {
        return this.jdField_int.isFilterable("VALUE" + str2 + str3);
      }
      if (str1.equals("TRANSACTIONTYPE")) {
        return isFilterable(String.valueOf(this.tranType), str2, str3);
      }
      if (str1.equals("BUSINESSID")) {
        return isFilterable(String.valueOf(this.businessID), str2, str3);
      }
      if ((str1.equals("CURRENCY_CODE")) && (this.currencyCode != null)) {
        return isFilterable(this.currencyCode, str2, str3);
      }
      if ((str1.equals("AMOUNT")) && (this.amount != null)) {
        return isFilterable(this.amount.toString(), str2, str3);
      }
      if ((str1.equals("STATE")) && (this.state != null)) {
        return isFilterable(this.state, str2, str3);
      }
      if ((str1.equals("TO_ACCT_ID")) && (this.toAcctID != null)) {
        return isFilterable(this.toAcctID, str2, str3);
      }
      if ((str1.equals("TO_ACCT_RTG_NUM")) && (this.toAcctRtgNum != null)) {
        return isFilterable(this.toAcctRtgNum, str2, str3);
      }
      if ((str1.equals("FROM_ACCT_ID")) && (this.fromAcctID != null)) {
        return isFilterable(this.fromAcctID, str2, str3);
      }
      if ((str1.equals("FROM_ACCT_RTG_NUM")) && (this.fromAcctRtgNum != null)) {
        return isFilterable(this.fromAcctRtgNum, str2, str3);
      }
      if (str1.equals("MODULE")) {
        return isFilterable(String.valueOf(this.module), str2, str3);
      }
    }
    return false;
  }
  
  public boolean isFilterable(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if (paramString2.equals("=")) {
      bool = paramString1.equals(paramString3);
    } else if (paramString2.equals("==")) {
      bool = paramString1.equalsIgnoreCase(paramString3);
    } else if (paramString2.equals("!")) {
      bool = !paramString1.equals(paramString3);
    } else if (paramString2.equals("!!")) {
      bool = !paramString1.equalsIgnoreCase(paramString3);
    } else if (paramString2.equals("<")) {
      bool = paramString1.compareTo(paramString3) < 0;
    } else if (paramString2.equals("<<")) {
      bool = paramString1.compareToIgnoreCase(paramString3) < 0;
    } else if (paramString2.equals(">")) {
      bool = paramString1.compareTo(paramString3) > 0;
    } else if (paramString2.equals(">>")) {
      bool = paramString1.compareToIgnoreCase(paramString3) > 0;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("userID=" + this.userID + ",agentID=" + this.agentID + ",desc=" + getMessage() + ",tranID=" + this.tranID + ",businessID=" + this.businessID + ",amount=" + this.amount + ",currencyCode=" + this.currencyCode + ",state=" + this.state + ",toAcctID=" + this.toAcctID + ",toAcctRtgNum=" + this.toAcctRtgNum + ",fromAcctID=" + this.fromAcctID + ",fromAcctRtgNum=" + this.fromAcctRtgNum + ",module=" + this.module);
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "AUDIT_LOG_RECORD");
    XMLHandler.appendTag(localStringBuffer, "USERID", this.userID);
    XMLHandler.appendTag(localStringBuffer, "AGENTID", this.agentID);
    XMLHandler.appendTag(localStringBuffer, "AGENTTYPE", this.agentType);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONID", this.tranID);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONTYPE", this.tranType);
    if (this.jdField_int != null) {
      XMLHandler.appendTag(localStringBuffer, "TRANSACTIONDATE", this.jdField_int.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", super.getMessage());
    XMLHandler.appendTag(localStringBuffer, "BUSINESSID", this.businessID);
    XMLHandler.appendTag(localStringBuffer, "AMOUNT", this.amount.toString());
    XMLHandler.appendTag(localStringBuffer, "CURRENCY_CODE", this.currencyCode);
    XMLHandler.appendTag(localStringBuffer, "SRVR_TID", this.srvr_tid);
    XMLHandler.appendTag(localStringBuffer, "STATE", this.state);
    XMLHandler.appendTag(localStringBuffer, "TO_ACCT_ID", this.toAcctID);
    XMLHandler.appendTag(localStringBuffer, "TO_ACCT_RTG_NUM", this.toAcctRtgNum);
    XMLHandler.appendTag(localStringBuffer, "FROM_ACCT_ID", this.fromAcctID);
    XMLHandler.appendTag(localStringBuffer, "FROM_ACCT_RTG_NUM", this.fromAcctRtgNum);
    XMLHandler.appendTag(localStringBuffer, "MODULE", this.module);
    XMLHandler.appendEndTag(localStringBuffer, "AUDIT_LOG_RECORD");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("USERID"))
    {
      setUserID(paramString2);
    }
    else if (paramString1.equals("AGENTID"))
    {
      setAgentID(paramString2);
    }
    else if (paramString1.equals("AGENTTYPE"))
    {
      setAgentType(paramString2);
    }
    else if (paramString1.equals("TRANSACTIONID"))
    {
      setTranID(paramString2);
    }
    else if (paramString1.equals("TRANSACTIONTYPE"))
    {
      setTranType(Integer.parseInt(paramString2));
    }
    else if (paramString1.equals("TRANSACTIONDATE"))
    {
      if (this.jdField_int == null)
      {
        this.jdField_int = new DateTime();
        this.jdField_int.setFormat(this.jdField_if);
      }
      this.jdField_int.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("DESCRIPTION"))
    {
      super.setMessage(paramString2);
    }
    else if (paramString1.equals("BUSINESSID"))
    {
      setBusinessID(Integer.parseInt(paramString2));
    }
    else if (paramString1.equals("AMOUNT"))
    {
      setAmount(paramString2);
    }
    else if (paramString1.equals("CURRENCY_CODE"))
    {
      setCurrencyCode(paramString2);
    }
    else if (paramString1.equals("SRVR_TID"))
    {
      setSrvrTid(paramString2);
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        ReportLogRecord.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.ReportLogRecord
 * JD-Core Version:    0.7.0.1
 */