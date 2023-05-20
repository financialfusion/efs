package com.ffusion.beans.positivepay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;
import java.util.StringTokenizer;

public class PPayDecision
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String PPAYDECISION = "PPAYDECISION";
  public static final String CHECKRECORD = "CHECKRECORD";
  public static final String REJECTREASON = "REJECTREASON";
  public static final String ISSUEDATE = "ISSUEDATE";
  public static final String DECISION = "DECISION";
  public static final String SUBMITTINGUSERNAME = "SUBMITTINGUSERNAME";
  private PPayCheckRecord a5e = null;
  private String a5h = null;
  private DateTime a5i = null;
  private String a5g = null;
  private String a5f = null;
  
  public PPayDecision()
  {
    this.datetype = "SHORT";
  }
  
  public PPayDecision(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a5e != null) {
      this.a5e.setLocale(paramLocale);
    }
    if (this.a5i != null) {
      this.a5i.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("ACCOUNTID")) {
        return this.a5e.getAccountID().equals(str2);
      }
      if (str1.equalsIgnoreCase("CHECKNUMBER")) {
        return this.a5e.getCheckNumber().equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKID")) {
        return this.a5e.getBankID().equals(str2);
      }
      if (str1.equalsIgnoreCase("CHECKDATE")) {
        try
        {
          int i = this.a5e.getCheckDate().compare(new DateTime(str2, this.locale), null);
          if (i != 0) {
            return false;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException)
        {
          DebugLog.throwing("Problem with date format when comparing checkdate.", localInvalidDateTimeException);
          return false;
        }
      }
      if (str1.equalsIgnoreCase("VOIDCHECK")) {
        return this.a5e.getVoidCheck() == Boolean.valueOf(str2).booleanValue();
      }
      if (str1.equalsIgnoreCase("REJECTREASON")) {
        return this.a5h.equals(str2);
      }
      if (str1.equalsIgnoreCase("DECISION")) {
        return this.a5g.equals(str2);
      }
      if (str1.equalsIgnoreCase("SUBMITTINGUSERNAME")) {
        return (this.a5f != null) && (this.a5f.equals(str2));
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return this.a5e.compare(paramObject, "ACCOUNTID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PPayDecision localPPayDecision = (PPayDecision)paramObject;
    int i = 1;
    if (paramString.equals("ACCOUNTID")) {
      i = this.a5e.compare(localPPayDecision.getCheckRecord(), "ACCOUNTID");
    } else if (paramString.equals("CHECKDATE")) {
      i = this.a5e.compare(localPPayDecision.getCheckRecord(), "CHECKDATE");
    } else if (paramString.equals("BANKID")) {
      i = this.a5e.compare(localPPayDecision.getCheckRecord(), "BANKID");
    } else if (paramString.equals("CHECKNUMBER")) {
      i = this.a5e.compare(localPPayDecision.getCheckRecord(), "CHECKNUMBER");
    } else if (paramString.equals("AMOUNT")) {
      i = this.a5e.compare(localPPayDecision.getCheckRecord(), "AMOUNT");
    } else if (paramString.equals("VOIDCHECK")) {
      i = this.a5e.compare(localPPayDecision.getCheckRecord(), "VOIDCHECK");
    } else if (paramString.equals("REJECTREASON")) {
      i = this.a5h.compareToIgnoreCase(localPPayDecision.getRejectReason());
    } else if (paramString.equals("DECISION")) {
      i = this.a5g.compareToIgnoreCase(localPPayDecision.getDecision());
    } else if (paramString.equals("SUBMITTINGUSERNAME"))
    {
      if (this.a5f == null)
      {
        if (localPPayDecision.a5f == null) {
          i = 0;
        } else {
          i = -1;
        }
      }
      else {
        i = this.a5f.compareToIgnoreCase(localPPayDecision.a5f);
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayDecision)) {
      return false;
    }
    PPayDecision localPPayDecision = (PPayDecision)paramObject;
    return (jdMethod_int(this.a5h, localPPayDecision.getRejectReason())) && (jdMethod_int(this.a5g, localPPayDecision.getDecision())) && (jdMethod_int(this.a5i, localPPayDecision.getIssueDate())) && (jdMethod_int(this.a5e, localPPayDecision.getCheckRecord())) && (jdMethod_int(this.a5f, localPPayDecision.getSubmittingUserName()));
  }
  
  public void setCheckRecord(PPayCheckRecord paramPPayCheckRecord)
  {
    this.a5e = paramPPayCheckRecord;
  }
  
  public PPayCheckRecord getCheckRecord()
  {
    return this.a5e;
  }
  
  public void setRejectReason(String paramString)
  {
    this.a5h = paramString;
  }
  
  public String getRejectReason()
  {
    return this.a5h;
  }
  
  public void setIssueDate(DateTime paramDateTime)
  {
    this.a5i = paramDateTime;
  }
  
  public DateTime getIssueDate()
  {
    return this.a5i;
  }
  
  public void setDecision(String paramString)
  {
    this.a5g = paramString;
  }
  
  public String getDecision()
  {
    return this.a5g;
  }
  
  public void setSubmittingUserName(String paramString)
  {
    this.a5f = paramString;
  }
  
  public String getSubmittingUserName()
  {
    return this.a5f;
  }
  
  public void set(PPayDecision paramPPayDecision)
  {
    super.set(paramPPayDecision);
    setCheckRecord(paramPPayDecision.getCheckRecord());
    setRejectReason(paramPPayDecision.getRejectReason());
    setIssueDate(paramPPayDecision.getIssueDate());
    setDecision(paramPPayDecision.getDecision());
    setSubmittingUserName(paramPPayDecision.getSubmittingUserName());
    this.datetype = paramPPayDecision.datetype;
    setLocale(paramPPayDecision.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("REJECTREASON")) {
        this.a5h = paramString2;
      } else if (paramString1.equals("DECISION")) {
        this.a5g = paramString2;
      } else if (paramString1.equals("SUBMITTINGUSERNAME")) {
        this.a5f = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYDECISION");
    if (this.a5e != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CHECKRECORD");
      localStringBuffer.append(this.a5e.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CHECKRECORD");
    }
    XMLHandler.appendTag(localStringBuffer, "REJECTREASON", this.a5h);
    if (this.a5i != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ISSUEDATE");
      localStringBuffer.append(this.a5i.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ISSUEDATE");
    }
    XMLHandler.appendTag(localStringBuffer, "DECISION", this.a5g);
    if (this.a5f != null) {
      XMLHandler.appendTag(localStringBuffer, "SUBMITTINGUSERNAME", this.a5f);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYDECISION");
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
  
  private boolean jdMethod_int(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    public a()
    {
      super();
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      PPayDecision.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("CHECKRECORD"))
      {
        if (PPayDecision.this.a5e == null) {
          PPayDecision.this.a5e = new PPayCheckRecord(PPayDecision.this.locale);
        }
        PPayDecision.this.a5e.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ISSUEDATE"))
      {
        if (PPayDecision.this.a5i == null) {
          PPayDecision.this.a5i = new DateTime(PPayDecision.this.locale);
        }
        PPayDecision.this.a5i.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayDecision
 * JD-Core Version:    0.7.0.1
 */