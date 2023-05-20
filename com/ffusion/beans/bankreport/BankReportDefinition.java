package com.ffusion.beans.bankreport;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class BankReportDefinition
  extends ExtendABean
{
  private String jdField_do;
  private String jdField_if;
  private HashMap a;
  
  public BankReportDefinition() {}
  
  public BankReportDefinition(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getReportType()
  {
    return this.jdField_do;
  }
  
  public void setReportType(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getReportKey()
  {
    return this.jdField_if;
  }
  
  public void setReportKey(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public HashMap getOptions()
  {
    return this.a;
  }
  
  public void setOptions(HashMap paramHashMap)
  {
    this.a = paramHashMap;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("reportType=").append(this.jdField_do);
    localStringBuffer.append(" reportKey=").append(this.jdField_if);
    localStringBuffer.append(" options=").append(this.a.toString());
    return localStringBuffer.toString();
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("REPORT_TYPE"))
      {
        if (this.jdField_do == null) {
          return false;
        }
        return this.jdField_do.equalsIgnoreCase(str2);
      }
      if (str1.equalsIgnoreCase("REPORT_KEY"))
      {
        if (this.jdField_if == null) {
          return false;
        }
        return this.jdField_if.equalsIgnoreCase(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = compare(paramObject, "REPORT_TYPE");
    if (i == 0) {
      return compare(paramObject, "REPORT_KEY");
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BankReportDefinition localBankReportDefinition = (BankReportDefinition)paramObject;
    int i = 1;
    if (paramString.equals("REPORT_TYPE")) {
      i = this.jdField_do.compareToIgnoreCase(localBankReportDefinition.getReportType());
    } else if (paramString.equals("REPORT_KEY")) {
      i = this.jdField_do.compareToIgnoreCase(localBankReportDefinition.getReportKey());
    } else if (paramString.equals("OPTIONS")) {
      i = this.a.equals(localBankReportDefinition.getOptions()) ? 0 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if ((paramObject instanceof BankReportDefinition))
    {
      BankReportDefinition localBankReportDefinition = (BankReportDefinition)paramObject;
      if (localBankReportDefinition == this) {
        return true;
      }
      if ((!jdField_if(this.jdField_do, localBankReportDefinition.getReportType())) || (!jdField_if(this.jdField_if, localBankReportDefinition.getReportKey())) || (!jdField_if(this.a, localBankReportDefinition.getOptions()))) {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  private boolean jdField_if(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANK_REPORT_DEFINITION");
    XMLHandler.appendTag(localStringBuffer, "REPORT_TYPE", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "REPORT_KEY", this.jdField_if);
    XMLHandler.appendBeginTag(localStringBuffer, "OPTIONS");
    Iterator localIterator = this.a.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      XMLHandler.appendTag(localStringBuffer, str, (String)this.a.get(str));
    }
    XMLHandler.appendEndTag(localStringBuffer, "OPTIONS");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BANK_REPORT_DEFINITION");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new b(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new b());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("REPORT_TYPE")) {
      this.jdField_do = paramString2;
    } else if (paramString1.equals("REPORT_KEY")) {
      this.jdField_if = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(BankReportDefinition paramBankReportDefinition)
  {
    super.set(paramBankReportDefinition);
    setReportType(paramBankReportDefinition.getReportType());
    setReportKey(paramBankReportDefinition.getReportKey());
    setOptions(paramBankReportDefinition.getOptions());
    setLocale(paramBankReportDefinition.locale);
  }
  
  class a
    extends XMLHandler
  {
    private HashMap jdField_int;
    
    public a(HashMap paramHashMap)
    {
      this.jdField_int = paramHashMap;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      if (!getElement().equals("OPTIONS"))
      {
        String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
        str1 = str1.trim();
        String str2 = getElement();
        this.jdField_int.put(str2, str1);
      }
    }
  }
  
  class b
    extends XMLHandler
  {
    public b() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      BankReportDefinition.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("OPTIONS"))
      {
        if (BankReportDefinition.this.a == null) {
          BankReportDefinition.this.a = new HashMap();
        }
        getHandler().continueWith(new BankReportDefinition.a(BankReportDefinition.this, BankReportDefinition.this.a));
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankreport.BankReportDefinition
 * JD-Core Version:    0.7.0.1
 */