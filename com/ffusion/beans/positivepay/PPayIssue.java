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

public class PPayIssue
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String PPAYISSUE = "PPAYISSUE";
  public static final String CHECKRECORD = "CHECKRECORD";
  public static final String REJECTREASON = "REJECTREASON";
  public static final String ISSUEDATE = "ISSUEDATE";
  private PPayCheckRecord a5r = null;
  private String a5t = null;
  private DateTime a5s = null;
  
  public PPayIssue()
  {
    this.datetype = "SHORT";
  }
  
  public PPayIssue(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a5r != null) {
      this.a5r.setLocale(paramLocale);
    }
    if (this.a5s != null) {
      this.a5s.setLocale(paramLocale);
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
        return this.a5r.getAccountID().equals(str2);
      }
      if (str1.equalsIgnoreCase("CHECKNUMBER")) {
        return this.a5r.getCheckNumber().equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKID")) {
        return this.a5r.getBankID().equals(str2);
      }
      if (str1.equalsIgnoreCase("CHECKDATE")) {
        try
        {
          int i = this.a5r.getCheckDate().compare(new DateTime(str2, this.locale), null);
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
        return this.a5r.getVoidCheck() == Boolean.valueOf(str2).booleanValue();
      }
      if (str1.equalsIgnoreCase("REJECTREASON")) {
        return this.a5t.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return this.a5r.compare(paramObject, "ACCOUNTID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PPayIssue localPPayIssue = (PPayIssue)paramObject;
    int i = 1;
    if (paramString.equals("ACCOUNTID")) {
      i = this.a5r.compare(localPPayIssue.getCheckRecord(), "ACCOUNTID");
    } else if (paramString.equals("CHECKDATE")) {
      i = this.a5r.compare(localPPayIssue.getCheckRecord(), "CHECKDATE");
    } else if (paramString.equals("BANKID")) {
      i = this.a5r.compare(localPPayIssue.getCheckRecord(), "BANKID");
    } else if (paramString.equals("CHECKNUMBER")) {
      i = this.a5r.compare(localPPayIssue.getCheckRecord(), "CHECKNUMBER");
    } else if (paramString.equals("AMOUNT")) {
      i = this.a5r.compare(localPPayIssue.getCheckRecord(), "AMOUNT");
    } else if (paramString.equals("VOIDCHECK")) {
      i = this.a5r.compare(localPPayIssue.getCheckRecord(), "VOIDCHECK");
    } else if (paramString.equals("REJECTREASON")) {
      i = this.a5t.compareToIgnoreCase(localPPayIssue.getRejectReason());
    } else if (paramString.equals("ISSUEDATE")) {
      i = this.a5s.equals(localPPayIssue.getIssueDate()) ? 0 : this.a5s.before(localPPayIssue.getIssueDate()) ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayIssue)) {
      return false;
    }
    PPayIssue localPPayIssue = (PPayIssue)paramObject;
    return (jdMethod_byte(this.a5t, localPPayIssue.getRejectReason())) && (jdMethod_byte(this.a5s, localPPayIssue.getIssueDate())) && (jdMethod_byte(this.a5r, localPPayIssue.getCheckRecord()));
  }
  
  public void setCheckRecord(PPayCheckRecord paramPPayCheckRecord)
  {
    this.a5r = paramPPayCheckRecord;
  }
  
  public PPayCheckRecord getCheckRecord()
  {
    return this.a5r;
  }
  
  public void setRejectReason(String paramString)
  {
    this.a5t = paramString;
  }
  
  public String getRejectReason()
  {
    return this.a5t;
  }
  
  public void setIssueDate(DateTime paramDateTime)
  {
    this.a5s = paramDateTime;
  }
  
  public DateTime getIssueDate()
  {
    return this.a5s;
  }
  
  public void set(PPayIssue paramPPayIssue)
  {
    super.set(paramPPayIssue);
    setCheckRecord(paramPPayIssue.getCheckRecord());
    setRejectReason(paramPPayIssue.getRejectReason());
    setIssueDate(paramPPayIssue.getIssueDate());
    this.datetype = paramPPayIssue.datetype;
    setLocale(paramPPayIssue.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("REJECTREASON")) {
        this.a5t = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYISSUE");
    if (this.a5r != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CHECKRECORD");
      localStringBuffer.append(this.a5r.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CHECKRECORD");
    }
    XMLHandler.appendTag(localStringBuffer, "REJECTREASON", this.a5t);
    if (this.a5s != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ISSUEDATE");
      localStringBuffer.append(this.a5s.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ISSUEDATE");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYISSUE");
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
  
  private boolean jdMethod_byte(Object paramObject1, Object paramObject2)
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
      PPayIssue.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("CHECKRECORD"))
      {
        if (PPayIssue.this.a5r == null) {
          PPayIssue.this.a5r = new PPayCheckRecord(PPayIssue.this.locale);
        }
        PPayIssue.this.a5r.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ISSUEDATE"))
      {
        if (PPayIssue.this.a5s == null) {
          PPayIssue.this.a5s = new DateTime(PPayIssue.this.locale);
        }
        PPayIssue.this.a5s.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayIssue
 * JD-Core Version:    0.7.0.1
 */