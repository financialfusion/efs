package com.ffusion.beans;

import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableString;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public abstract class ExtendABean
  extends com.ffusion.util.beans.ExtendABean
{
  protected static final String BEAN_NAME = ExtendABean.class.getName();
  private HashSet e = null;
  private String d = null;
  public static final String TRNUID = "TRNUID";
  
  public ExtendABean() {}
  
  public ExtendABean(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setTRNUID(String paramString)
  {
    this.d = paramString;
  }
  
  public String getTRNUID()
  {
    return this.d;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("TRNUID")) && (getTRNUID() != null)) {
      return isFilterable(getTRNUID(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    if ((this instanceof ExtendABean)) {
      paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
    } else {
      super.continueXMLParsing(paramXMLHandler);
    }
  }
  
  public void setKeysNotToLog(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      this.e = null;
    }
    else
    {
      if (this.e == null) {
        this.e = new HashSet();
      } else {
        this.e.clear();
      }
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
      while (localStringTokenizer.hasMoreTokens()) {
        this.e.add(localStringTokenizer.nextToken());
      }
    }
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ExtendABean paramExtendABean, String paramString)
  {
    HashMap localHashMap = paramExtendABean.map;
    Iterator localIterator = localHashMap.entrySet().iterator();
    Map.Entry localEntry;
    Object localObject1;
    Object localObject2;
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localObject1 = localEntry.getKey();
      if ((localObject1 != null) && ((this.e == null) || (this.e.isEmpty()) || (!this.e.contains(localObject1)))) {
        if (!this.map.containsKey(localObject1))
        {
          paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, localObject1.toString()), localEntry.getValue(), null, paramString == null ? "Field removed" : paramString);
        }
        else
        {
          localObject2 = this.map.get(localObject1);
          Object localObject3 = localEntry.getValue();
          if (localObject2 != localObject3) {
            if (localObject2 != null)
            {
              if (!localObject2.equals(localObject3)) {
                paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, localObject1.toString()), localObject3, localObject2, paramString == null ? "Field changed" : paramString);
              }
            }
            else if (!localObject3.equals(localObject2)) {
              paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, localObject1.toString()), localObject3, localObject2, paramString == null ? "Field changed" : paramString);
            }
          }
        }
      }
    }
    localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localObject1 = localEntry.getKey();
      if ((localObject1 != null) && ((this.e == null) || (this.e.isEmpty()) || (!this.e.contains(localObject1)))) {
        if (!localHashMap.containsKey(localObject1))
        {
          localObject2 = localEntry.getValue() == null ? null : localEntry.getValue().toString();
          if (!((String)localObject2).equals("")) {
            paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, localObject1.toString()), null, (String)localObject2, paramString == null ? "Field added" : paramString);
          }
        }
      }
    }
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    Iterator localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = localEntry.getKey() == null ? null : localEntry.getKey().toString();
      String str2 = localEntry.getValue() == null ? null : localEntry.getValue().toString();
      if ((this.e == null) || (this.e.isEmpty()) || (str1 == null) || (!this.e.contains(str1))) {
        paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, str1), null, str2, paramString);
      }
    }
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    Iterator localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = localEntry.getKey() == null ? null : localEntry.getKey().toString();
      String str2 = localEntry.getValue() == null ? null : localEntry.getValue().toString();
      if ((this.e == null) || (this.e.isEmpty()) || (str1 == null) || (!this.e.contains(str1))) {
        paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, str1), str2, null, paramString);
      }
    }
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ExtendABean paramExtendABean, ILocalizable paramILocalizable)
  {
    HashMap localHashMap = paramExtendABean.map;
    Iterator localIterator = localHashMap.entrySet().iterator();
    Map.Entry localEntry;
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localObject1 = localEntry.getKey();
      if ((localObject1 != null) && ((this.e == null) || (this.e.isEmpty()) || (!this.e.contains(localObject1)))) {
        if (!this.map.containsKey(localObject1))
        {
          localObject2 = localEntry.getValue();
          if (paramILocalizable == null)
          {
            localObject3 = new LocalizableString("com.ffusion.beans.history.resources", "FIELD_REMOVED", null);
          }
          else
          {
            localObject4 = new Object[1];
            localObject4[0] = paramILocalizable;
            localObject3 = new LocalizableString("com.ffusion.beans.history.resources", "FIELD_REMOVED_EXTRA", (Object[])localObject4);
          }
          if ((localObject2 instanceof ILocalizable)) {
            paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), (ILocalizable)localObject2, null, (ILocalizable)localObject3);
          } else {
            paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), localObject2 == null ? null : localObject2.toString(), null, (ILocalizable)localObject3);
          }
        }
        else
        {
          localObject2 = this.map.get(localObject1);
          localObject3 = localEntry.getValue();
          if (paramILocalizable == null)
          {
            localObject4 = new LocalizableString("com.ffusion.beans.history.resources", "FIELD_CHANGED", null);
          }
          else
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = paramILocalizable;
            localObject4 = new LocalizableString("com.ffusion.beans.history.resources", "FIELD_CHANGED_EXTRA", arrayOfObject);
          }
          if (localObject2 != localObject3) {
            if (localObject2 != null)
            {
              if (!localObject2.equals(localObject3)) {
                if (((localObject3 instanceof ILocalizable)) && ((localObject2 instanceof ILocalizable))) {
                  paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), (ILocalizable)localObject3, (ILocalizable)localObject2, (ILocalizable)localObject4);
                } else {
                  paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), localObject3 == null ? null : localObject3.toString(), localObject2.toString(), (ILocalizable)localObject4);
                }
              }
            }
            else if (!localObject3.equals(localObject2)) {
              if (((localObject3 instanceof ILocalizable)) && ((localObject2 instanceof ILocalizable))) {
                paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), (ILocalizable)localObject3, (ILocalizable)localObject2, (ILocalizable)localObject4);
              } else {
                paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), localObject3 == null ? null : localObject3.toString(), localObject2 == null ? null : localObject2.toString(), (ILocalizable)localObject4);
              }
            }
          }
        }
      }
    }
    localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localObject1 = localEntry.getKey();
      if ((localObject1 != null) && ((this.e == null) || (this.e.isEmpty()) || (!this.e.contains(localObject1)))) {
        if (!localHashMap.containsKey(localObject1))
        {
          localObject2 = localEntry.getValue();
          if (paramILocalizable == null)
          {
            localObject3 = new LocalizableString("com.ffusion.beans.history.resources", "FIELD_ADDED", null);
          }
          else
          {
            localObject4 = new Object[1];
            localObject4[0] = paramILocalizable;
            localObject3 = new LocalizableString("com.ffusion.beans.history.resources", "FIELD_ADDED_EXTRA", (Object[])localObject4);
          }
          if ((localObject2 instanceof ILocalizable))
          {
            paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), null, (ILocalizable)localObject2, (ILocalizable)localObject3);
          }
          else
          {
            localObject4 = localObject2 == null ? null : localObject2.toString();
            if ((localObject4 != null) && (!((String)localObject4).equals(""))) {
              paramHistoryTracker.logChange(BEAN_NAME, localObject1.toString(), null, (String)localObject4, (ILocalizable)localObject3);
            }
          }
        }
      }
    }
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    Iterator localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = localEntry.getKey() == null ? null : localEntry.getKey().toString();
      Object localObject = localEntry.getValue();
      if ((this.e == null) || (this.e.isEmpty()) || (str == null) || (!this.e.contains(str))) {
        if ((localObject instanceof ILocalizable)) {
          paramHistoryTracker.logChange(BEAN_NAME, str, null, (ILocalizable)localObject, paramILocalizable);
        } else {
          paramHistoryTracker.logChange(BEAN_NAME, str, null, localObject == null ? null : localObject.toString(), paramILocalizable);
        }
      }
    }
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    Iterator localIterator = this.map.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = localEntry.getKey() == null ? null : localEntry.getKey().toString();
      Object localObject = localEntry.getValue();
      if ((this.e == null) || (this.e.isEmpty()) || (str == null) || (!this.e.contains(str))) {
        if ((localObject instanceof ILocalizable)) {
          paramHistoryTracker.logChange(BEAN_NAME, str, (ILocalizable)localObject, null, paramILocalizable);
        } else {
          paramHistoryTracker.logChange(BEAN_NAME, str, localObject == null ? null : localObject.toString(), null, paramILocalizable);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ExtendABean
 * JD-Core Version:    0.7.0.1
 */