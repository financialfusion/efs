package com.ffusion.beans.positivepay;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;
import java.util.StringTokenizer;

public class PPaySummary
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String PPAYSUMMARY = "PPAYSUMMARY";
  public static final String PPAYACCOUNT = "PPAYACCOUNT";
  public static final String NUMISSUES = "NUMISSUES";
  private PPayAccount a5q = null;
  private int a5p = -1;
  
  public PPaySummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a5q != null) {
      this.a5q.setLocale(paramLocale);
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
        return this.a5q.getAccountID().equals(str2);
      }
      if (str1.equalsIgnoreCase("NICKNAME")) {
        return this.a5q.getNickName().equals(str2);
      }
      if (str1.equalsIgnoreCase("BANKID")) {
        return this.a5q.getBankID().equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ACCOUNTID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PPaySummary localPPaySummary = (PPaySummary)paramObject;
    int i = 1;
    if (paramString.equals("ACCOUNTID")) {
      i = this.a5q.compare(localPPaySummary.getPPayAccount(), "ACCOUNTID");
    } else if (paramString.equals("NICKNAME")) {
      i = this.a5q.compare(localPPaySummary.getPPayAccount(), "NICKNAME");
    } else if (paramString.equals("CURRENCYTYPE")) {
      i = this.a5q.compare(localPPaySummary.getPPayAccount(), "CURRENCYTYPE");
    } else if (paramString.equals("NUMISSUES")) {
      i = this.a5p - localPPaySummary.getNumIssues();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPaySummary)) {
      return false;
    }
    PPaySummary localPPaySummary = (PPaySummary)paramObject;
    return (this.a5p == localPPaySummary.getNumIssues()) && (jdMethod_try(this.a5q, localPPaySummary.getPPayAccount()));
  }
  
  public void setPPayAccount(PPayAccount paramPPayAccount)
  {
    this.a5q = paramPPayAccount;
  }
  
  public PPayAccount getPPayAccount()
  {
    return this.a5q;
  }
  
  public void setNumIssues(int paramInt)
  {
    this.a5p = paramInt;
  }
  
  public int getNumIssues()
  {
    return this.a5p;
  }
  
  public void set(PPaySummary paramPPaySummary)
  {
    super.set(paramPPaySummary);
    setPPayAccount(paramPPaySummary.getPPayAccount());
    setNumIssues(paramPPaySummary.getNumIssues());
    setLocale(paramPPaySummary.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("NUMISSUES")) {
        this.a5p = Integer.parseInt(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYSUMMARY");
    if (this.a5q != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "PPAYACCOUNT");
      localStringBuffer.append(this.a5q.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "PPAYACCOUNT");
    }
    if (this.a5p != -1) {
      XMLHandler.appendTag(localStringBuffer, "NUMISSUES", this.a5p);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PPAYSUMMARY");
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
  
  private boolean jdMethod_try(Object paramObject1, Object paramObject2)
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
      PPaySummary.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PPAYACCOUNT"))
      {
        if (PPaySummary.this.a5q == null) {
          PPaySummary.this.a5q = new PPayAccount(PPaySummary.this.locale);
        }
        PPaySummary.this.a5q.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPaySummary
 * JD-Core Version:    0.7.0.1
 */