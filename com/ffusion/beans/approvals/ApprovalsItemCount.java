package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsItemCount
  extends ExtendABean
  implements Serializable
{
  private int aZB;
  private int aZA;
  private int aZz;
  
  public int getItemType()
  {
    return this.aZB;
  }
  
  public void setItemType(int paramInt)
  {
    this.aZB = paramInt;
  }
  
  public int getItemSubType()
  {
    return this.aZA;
  }
  
  public void setItemSubType(int paramInt)
  {
    this.aZA = paramInt;
  }
  
  public int getNumItems()
  {
    return this.aZz;
  }
  
  public void setNumItems(int paramInt)
  {
    this.aZz = paramInt;
  }
  
  public ApprovalsItemCount(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      int i;
      if (str1.equalsIgnoreCase("ITEMTYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZB == i;
      }
      if (str1.equalsIgnoreCase("ITEMSUBTYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZA == i;
      }
      if (str1.equalsIgnoreCase("NUMITEMS"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          return false;
        }
        return this.aZz == i;
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = compare(paramObject, "ITEMTYPE");
    if (i == 0)
    {
      i = compare(paramObject, "ITEMSUBTYPE");
      if (i == 0) {
        i = compare(paramObject, "NUMITEMS");
      }
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsItemCount localApprovalsItemCount = (ApprovalsItemCount)paramObject;
    int i = 1;
    if (paramString.equals("ITEMTYPE")) {
      i = this.aZB - localApprovalsItemCount.getItemType();
    } else if (paramString.equals("ITEMSUBTYPE")) {
      i = this.aZA - localApprovalsItemCount.getItemSubType();
    } else if (paramString.equals("NUMITEMS")) {
      i = this.aZz - localApprovalsItemCount.getNumItems();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsItemCount)) {
      return false;
    }
    ApprovalsItemCount localApprovalsItemCount = (ApprovalsItemCount)paramObject;
    return (this.aZB == localApprovalsItemCount.getItemType()) && (this.aZA == localApprovalsItemCount.getItemSubType()) && (this.aZz == localApprovalsItemCount.getNumItems());
  }
  
  public int hashCode()
  {
    return this.aZB * 3 + this.aZA * 5;
  }
  
  public void set(ApprovalsItemCount paramApprovalsItemCount)
  {
    super.set(paramApprovalsItemCount);
    setItemType(paramApprovalsItemCount.getItemType());
    setItemSubType(paramApprovalsItemCount.getItemSubType());
    setNumItems(paramApprovalsItemCount.getNumItems());
    setLocale(paramApprovalsItemCount.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ITEMTYPE")) {
        this.aZB = Integer.parseInt(paramString2);
      } else if (paramString1.equals("ITEMSUBTYPE")) {
        this.aZA = Integer.parseInt(paramString2);
      } else if (paramString1.equals("NUMITEMS")) {
        this.aZz = Integer.parseInt(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_ITEM_COUNT");
    XMLHandler.appendTag(localStringBuffer, "ITEMTYPE", this.aZB);
    XMLHandler.appendTag(localStringBuffer, "ITEMSUBTYPE", this.aZA);
    XMLHandler.appendTag(localStringBuffer, "NUMITEMS", this.aZz);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_ITEM_COUNT");
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
  
  public boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("itemType=").append(this.aZB);
    localStringBuffer.append(" itemSubType=").append(this.aZA);
    localStringBuffer.append(" numItems=").append(this.aZz);
    return localStringBuffer.toString();
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ApprovalsItemCount.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsItemCount
 * JD-Core Version:    0.7.0.1
 */