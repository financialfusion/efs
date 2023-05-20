package com.ffusion.services.ofx;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import com.ffusion.services.Enroll2;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;

public class Enroll
  extends Base
  implements Defines, Enroll2
{
  private static final String bp = "EnrollID";
  private static final String bq = "EnrollPassword";
  protected static String enrollID;
  protected static String enrollPassword;
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new EnrollXMLHandler());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ENROLLSERVICE");
    if (this.debugService)
    {
      XMLHandler.appendTag(localStringBuffer, "EnrollID", enrollID);
      XMLHandler.appendTag(localStringBuffer, "EnrollPassword", enrollPassword);
    }
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "ENROLLSERVICE");
    return localStringBuffer.toString();
  }
  
  public int enroll(HashMap paramHashMap)
  {
    boolean bool = false;
    if (this.debugService) {
      DebugLog.log(Level.INFO, "Enroll message " + paramHashMap);
    }
    char[] arrayOfChar = sendRequest(formatEnrollRequest(paramHashMap));
    bool = handleResponse(arrayOfChar);
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int activateAccount(User paramUser, Accounts paramAccounts)
  {
    return 0;
  }
  
  protected char[] formatEnrollRequest(HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatEnrollRequest:");
    }
    setUserID(enrollID);
    this.password = enrollPassword;
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "SIGNUPMSGSRQV1");
    appendBeginTag(localStringBuffer, "ENROLLTRNRQ");
    appendTag(localStringBuffer, "TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "ENROLLRQ");
    appendTag(localStringBuffer, "FIRSTNAME", (String)paramHashMap.get("FIRST_NAME"));
    if ((paramHashMap.get("MiddleName") != null) && (((String)paramHashMap.get("MiddleName")).trim().length() > 0)) {
      appendTag(localStringBuffer, "MIDDLENAME", (String)paramHashMap.get("MiddleName"));
    } else {
      appendTag(localStringBuffer, "MIDDLENAME", "none");
    }
    appendTag(localStringBuffer, "LASTNAME", (String)paramHashMap.get("LAST_NAME"));
    appendTag(localStringBuffer, "ADDR1", (String)paramHashMap.get("ADDR"));
    if (paramHashMap.get("ADDR2") != null) {
      appendTag(localStringBuffer, "ADDR2", (String)paramHashMap.get("ADDR2"));
    }
    if (paramHashMap.get("ADDR3") != null) {
      appendTag(localStringBuffer, "ADDR3", (String)paramHashMap.get("ADDR3"));
    }
    appendTag(localStringBuffer, "CITY", (String)paramHashMap.get("CITY"));
    appendTag(localStringBuffer, "STATE", (String)paramHashMap.get("STATE"));
    appendTag(localStringBuffer, "POSTALCODE", (String)paramHashMap.get("ZIPCODE"));
    if (paramHashMap.get("COUNTRY") != null) {
      appendTag(localStringBuffer, "COUNTRY", (String)paramHashMap.get("COUNTRY"));
    } else {
      appendTag(localStringBuffer, "COUNTRY", "USA");
    }
    appendTag(localStringBuffer, "DAYPHONE", (String)paramHashMap.get("Phone"));
    if (paramHashMap.get("EVEPHONE") != null) {
      appendTag(localStringBuffer, "EVEPHONE", (String)paramHashMap.get("EVEPHONE"));
    } else {
      appendTag(localStringBuffer, "EVEPHONE", "none");
    }
    appendTag(localStringBuffer, "EMAIL", (String)paramHashMap.get("EMAIL_ADDRESS"));
    if (paramHashMap.get("CUSTID") != null) {
      appendTag(localStringBuffer, "USERID", (String)paramHashMap.get("CUSTID"));
    }
    appendTag(localStringBuffer, "TAXID", (String)paramHashMap.get("SSN"));
    appendTag(localStringBuffer, "SECURITYNAME", (String)paramHashMap.get("MothersMaidenName"));
    String str = (String)paramHashMap.get("Birthdate");
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    try
    {
      localGregorianCalendar.setTime(DateFormatUtil.getFormatter("MM/dd/yyyy").parse(str));
    }
    catch (Exception localException) {}
    appendTag(localStringBuffer, "DATEBIRTH", localGregorianCalendar, "yyyyMMdd");
    appendEndTag(localStringBuffer, "ENROLLRQ");
    appendEndTag(localStringBuffer, "ENROLLTRNRQ");
    appendEndTag(localStringBuffer, "SIGNUPMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected boolean handleResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    this.objStatus.clear();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".handleResponse:");
    }
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNUPMSGSRSV1")) {
          parseSIGNUPMSGSRSV1();
        } else if (!handleResponseNotHandled(str)) {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        bool = true;
      }
    }
    return bool;
  }
  
  public void parseSIGNUPMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("SIGNUPMSGSRSV1"))) {
      if (str.equals("ENROLLTRNRS")) {
        parseENROLLTRNRS();
      } else if (str.equals("ACCTINFOTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("CHGUSERINFOTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("CHGUSERINFOSYNCRS")) {
        parseNotSupported(str);
      } else if (str.equals("ACCTTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("ACCTSYNCRS")) {
        parseNotSupported(str);
      } else {
        throw new IllegalArgumentException("parseSIGNUPMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  public void parseENROLLTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("ENROLLTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("ENROLLRS")) {
        parseENROLLRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        getField();
      } else {
        throw new IllegalArgumentException("parseENROLLTRNRS failed on tag=" + str);
      }
    }
  }
  
  public void parseENROLLRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("ENROLLRS"))) {
      if (str.equals("USERID")) {
        setUserID(getField());
      } else if (str.equals("TEMPPASS")) {
        this.password = getField();
      } else if (str.equals("DTEXPIRE")) {
        skipField();
      } else {
        throw new IllegalArgumentException("parseENROLLRS failed on tag=" + str);
      }
    }
  }
  
  public class EnrollXMLHandler
    extends Base.ServiceXMLHandler
  {
    public EnrollXMLHandler()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("EnrollID")) {
        Enroll.enrollID = paramString2;
      } else if (paramString1.equals("EnrollPassword")) {
        Enroll.enrollPassword = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Enroll
 * JD-Core Version:    0.7.0.1
 */