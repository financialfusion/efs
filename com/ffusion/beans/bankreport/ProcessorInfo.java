package com.ffusion.beans.bankreport;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class ProcessorInfo
  extends ExtendABean
{
  private ArrayList jdField_do;
  private String a;
  private HashMap jdField_if;
  
  public ProcessorInfo() {}
  
  public ProcessorInfo(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ArrayList getReportTypes()
  {
    return this.jdField_do;
  }
  
  public void setReportTypes(ArrayList paramArrayList)
  {
    this.jdField_do = paramArrayList;
  }
  
  public String getProcessorClassName()
  {
    return this.a;
  }
  
  public void setProcessorClassName(String paramString)
  {
    this.a = paramString;
  }
  
  public HashMap getProcessorConfig()
  {
    return this.jdField_if;
  }
  
  public void setProcessorConfig(HashMap paramHashMap)
  {
    this.jdField_if = paramHashMap;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("reportTypes=").append(this.jdField_do);
    localStringBuffer.append(" processorClassName=").append(this.a);
    localStringBuffer.append(" processorConfig=").append(this.jdField_if.toString());
    return localStringBuffer.toString();
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("PROCESSOR_CLASS_NAME"))
      {
        if (this.a == null) {
          return false;
        }
        return this.a.equalsIgnoreCase(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "PROCESSOR_CLASS_NAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ProcessorInfo localProcessorInfo = (ProcessorInfo)paramObject;
    int i = 1;
    if (paramString.equals("PROCESSOR_CLASS_NAME")) {
      i = this.a.compareToIgnoreCase(localProcessorInfo.getProcessorClassName());
    } else if (paramString.equals("REPORT_TYPES")) {
      i = this.jdField_do.equals(localProcessorInfo.getReportTypes()) ? 0 : 1;
    } else if (paramString.equals("PROCESSOR_CONFIG")) {
      i = this.jdField_if.equals(localProcessorInfo.getProcessorConfig()) ? 0 : 1;
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
    if ((paramObject instanceof ProcessorInfo))
    {
      ProcessorInfo localProcessorInfo = (ProcessorInfo)paramObject;
      if (localProcessorInfo == this) {
        return true;
      }
      if ((!jdField_if(this.jdField_do, localProcessorInfo.getReportTypes())) || (!jdField_if(this.a, localProcessorInfo.getProcessorClassName())) || (!jdField_if(this.jdField_if, localProcessorInfo.getProcessorConfig()))) {
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
    XMLHandler.appendBeginTag(localStringBuffer, "PROCESSOR_INFO");
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_TYPES");
    Iterator localIterator = this.jdField_do.iterator();
    while (localIterator.hasNext())
    {
      localObject = (String)localIterator.next();
      XMLHandler.appendTag(localStringBuffer, "REPORT_TYPE", (String)localObject);
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_TYPES");
    XMLHandler.appendTag(localStringBuffer, "PROCESSOR_CLASS_NAME", this.a);
    XMLHandler.appendBeginTag(localStringBuffer, "PROCESSOR_CONFIG");
    Object localObject = this.jdField_if.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      XMLHandler.appendTag(localStringBuffer, str, (String)this.jdField_if.get(str));
    }
    XMLHandler.appendEndTag(localStringBuffer, "PROCESSOR_CONFIG");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PROCESSOR_INFO");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new c(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new c());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("PROCESSOR_CLASS_NAME")) {
      this.a = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(ProcessorInfo paramProcessorInfo)
  {
    super.set(paramProcessorInfo);
    setReportTypes(paramProcessorInfo.getReportTypes());
    setProcessorClassName(paramProcessorInfo.getProcessorClassName());
    setProcessorConfig(paramProcessorInfo.getProcessorConfig());
    setLocale(paramProcessorInfo.locale);
  }
  
  class a
    extends XMLHandler
  {
    private ArrayList jdField_int;
    
    public a(ArrayList paramArrayList)
    {
      this.jdField_int = paramArrayList;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      if (getElement().equals("REPORT_TYPE"))
      {
        String str = new String(paramArrayOfChar, paramInt1, paramInt2);
        str = str.trim();
        this.jdField_int.add(str);
      }
    }
  }
  
  class b
    extends XMLHandler
  {
    private HashMap jdField_int;
    
    public b(HashMap paramHashMap)
    {
      this.jdField_int = paramHashMap;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      if (!getElement().equals("PROCESSOR_CONFIG"))
      {
        String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
        str1 = str1.trim();
        String str2 = getElement();
        this.jdField_int.put(str2, str1);
      }
    }
  }
  
  class c
    extends XMLHandler
  {
    public c() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ProcessorInfo.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PROCESSOR_CONFIG"))
      {
        if (ProcessorInfo.this.jdField_if == null) {
          ProcessorInfo.this.jdField_if = new HashMap();
        }
        getHandler().continueWith(new ProcessorInfo.b(ProcessorInfo.this, ProcessorInfo.this.jdField_if));
      }
      else if (paramString.equals("REPORT_TYPES"))
      {
        if (ProcessorInfo.this.jdField_do == null) {
          ProcessorInfo.this.jdField_do = new ArrayList();
        }
        getHandler().continueWith(new ProcessorInfo.a(ProcessorInfo.this, ProcessorInfo.this.jdField_do));
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankreport.ProcessorInfo
 * JD-Core Version:    0.7.0.1
 */