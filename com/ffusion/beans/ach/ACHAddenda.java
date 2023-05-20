package com.ffusion.beans.ach;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ACHAddenda
  extends ExtendABean
  implements Serializable
{
  protected static final String BEAN_NAME = ACHAddenda.class.getName();
  public static final String ADDENDA_ADDED = "ADDENDA_ADDED";
  public static final String ADDENDA_CANCELED = "ADDENDA_CANCELED";
  protected String pmtRelatedInfo;
  protected String id;
  protected Object bpwAddendaObject;
  protected String action;
  
  public String getPmtRelatedInfo()
  {
    return this.pmtRelatedInfo;
  }
  
  public void setPmtRelatedInfo(String paramString)
  {
    this.pmtRelatedInfo = paramString;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACHADDENDA");
    XMLHandler.appendTag(localStringBuffer, "ID", getID());
    XMLHandler.appendTag(localStringBuffer, "PMTRELATEDINFO", getPmtRelatedInfo());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHADDENDA");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHAddenda paramACHAddenda, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHAddenda.getID(), getID(), paramString);
    int i = 0;
    if ((this.pmtRelatedInfo != null) && ((this.pmtRelatedInfo.startsWith("TXP")) || (this.pmtRelatedInfo.startsWith("DED")))) {
      i = 1;
    }
    if ((paramACHAddenda.getPmtRelatedInfo() != null) && ((paramACHAddenda.getPmtRelatedInfo().startsWith("TXP")) || (paramACHAddenda.getPmtRelatedInfo().startsWith("DED")))) {
      i = 1;
    }
    if (i == 0)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "PMTRELATEDINFO", paramACHAddenda.getPmtRelatedInfo(), getPmtRelatedInfo(), paramString);
    }
    else
    {
      HashMap localHashMap1 = ar(getPmtRelatedInfo());
      HashMap localHashMap2 = ar(paramACHAddenda.getPmtRelatedInfo());
      Iterator localIterator;
      String str;
      if (localHashMap2 != null)
      {
        localIterator = localHashMap2.keySet().iterator();
        while (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          paramHistoryTracker.detectChange(BEAN_NAME, str, localHashMap2.get(str), localHashMap1.get(str), paramString);
          localHashMap1.remove(str);
        }
      }
      if ((localHashMap1 != null) && (!localHashMap1.isEmpty()))
      {
        localIterator = localHashMap1.keySet().iterator();
        while (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          paramHistoryTracker.detectChange(BEAN_NAME, str, null, localHashMap1.get(str), paramString);
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramACHAddenda, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHAddenda paramACHAddenda, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHAddenda.getID(), getID(), paramILocalizable);
    int i = 0;
    if ((this.pmtRelatedInfo != null) && ((this.pmtRelatedInfo.startsWith("TXP")) || (this.pmtRelatedInfo.startsWith("DED")))) {
      i = 1;
    }
    if ((paramACHAddenda.getPmtRelatedInfo() != null) && ((paramACHAddenda.getPmtRelatedInfo().startsWith("TXP")) || (paramACHAddenda.getPmtRelatedInfo().startsWith("DED")))) {
      i = 1;
    }
    if (i == 0)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "PMTRELATEDINFO", paramACHAddenda.getPmtRelatedInfo(), getPmtRelatedInfo(), paramILocalizable);
    }
    else
    {
      HashMap localHashMap1 = ar(getPmtRelatedInfo());
      HashMap localHashMap2 = ar(paramACHAddenda.getPmtRelatedInfo());
      Iterator localIterator;
      String str;
      Object localObject1;
      if (localHashMap2 != null)
      {
        localIterator = localHashMap2.keySet().iterator();
        while (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          localObject1 = localHashMap2.get(str);
          Object localObject2 = localHashMap1.get(str);
          if (((localObject1 instanceof ILocalizable)) && ((localObject2 instanceof ILocalizable))) {
            paramHistoryTracker.detectChange(BEAN_NAME, str, (ILocalizable)localObject1, (ILocalizable)localObject2, paramILocalizable);
          } else {
            paramHistoryTracker.detectChange(BEAN_NAME, str, localObject1 == null ? null : localObject1.toString(), localObject2 == null ? null : localObject2.toString(), paramILocalizable);
          }
          localHashMap1.remove(str);
        }
      }
      if ((localHashMap1 != null) && (!localHashMap1.isEmpty()))
      {
        localIterator = localHashMap1.keySet().iterator();
        while (localIterator.hasNext())
        {
          str = (String)localIterator.next();
          localObject1 = localHashMap1.get(str);
          if ((localObject1 instanceof ILocalizable)) {
            paramHistoryTracker.detectChange(BEAN_NAME, str, null, (ILocalizable)localObject1, paramILocalizable);
          } else {
            paramHistoryTracker.detectChange(BEAN_NAME, str, null, localObject1 == null ? null : localObject1.toString(), paramILocalizable);
          }
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramACHAddenda, paramILocalizable);
  }
  
  private static HashMap ar(String paramString)
  {
    String str1 = paramString.substring(0, 3);
    HashMap localHashMap = new HashMap();
    if ((paramString.startsWith("TXP*")) || (paramString.startsWith("DED*")))
    {
      paramString = paramString.substring(4);
      int i = paramString.indexOf("*");
      int j = paramString.indexOf("\\");
      int k = 0;
      int m = 1;
      int n = 0;
      while (i != -1)
      {
        String str2 = paramString.substring(k, i);
        String str3 = str1 + (m < 10 ? "0" : "") + m;
        localHashMap.put(str3, str2);
        if (n != 0) {
          break;
        }
        k = i + 1;
        i = paramString.indexOf("*", k);
        if (i < 0)
        {
          n = 1;
          i = j;
        }
        m++;
      }
    }
    return localHashMap;
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "ADDENDA_ADDED", getID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ADDENDA_CANCELED", getID(), null, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "ADDENDA_ADDED", getID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ADDENDA_CANCELED", getID(), null, paramILocalizable);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("PMTRELATEDINFO")) {
        setPmtRelatedInfo(paramString2);
      } else if (paramString1.equals("ID")) {
        setID(paramString2);
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
  
  public Object getBpwAddendaObject()
  {
    return this.bpwAddendaObject;
  }
  
  public void setBpwAddendaObject(Object paramObject)
  {
    this.bpwAddendaObject = paramObject;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String paramString)
  {
    this.action = paramString;
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
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ACHAddenda.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHAddenda
 * JD-Core Version:    0.7.0.1
 */