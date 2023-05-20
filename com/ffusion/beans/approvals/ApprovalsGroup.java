package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsGroup
  extends ExtendABean
{
  private int aZR;
  private int aZT;
  private String aZS;
  
  public int getApprovalsGroupID()
  {
    return this.aZR;
  }
  
  public void setApprovalsGroupID(int paramInt)
  {
    this.aZR = paramInt;
  }
  
  public int getBusinessID()
  {
    return this.aZT;
  }
  
  public void setBusinessID(int paramInt)
  {
    this.aZT = paramInt;
  }
  
  public String getGroupName()
  {
    return this.aZS;
  }
  
  public void setGroupName(String paramString)
  {
    this.aZS = paramString;
  }
  
  public ApprovalsGroup(Locale paramLocale)
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
      if (str1.equalsIgnoreCase("APPROVALGROUPID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZR == i;
      }
      if (str1.equalsIgnoreCase("APPROVALGROUPBUSINESSID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZT == i;
      }
      if (str1.equalsIgnoreCase("APPROVALGROUPNAME")) {
        return this.aZS.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "APPROVALGROUPID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsGroup localApprovalsGroup = (ApprovalsGroup)paramObject;
    int i = 1;
    if (paramString.equals("APPROVALGROUPID"))
    {
      if (this.aZR == localApprovalsGroup.getApprovalsGroupID()) {
        i = 0;
      } else if (this.aZR < localApprovalsGroup.getApprovalsGroupID()) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else if (paramString.equals("APPROVALGROUPBUSINESSID"))
    {
      if (this.aZT == localApprovalsGroup.getBusinessID()) {
        i = 0;
      } else if (this.aZT < localApprovalsGroup.getBusinessID()) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else if (paramString.equals("APPROVALGROUPNAME"))
    {
      Collator localCollator = doGetCollator();
      i = localCollator.compare(this.aZS, localApprovalsGroup.getGroupName());
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsGroup)) {
      return false;
    }
    ApprovalsGroup localApprovalsGroup = (ApprovalsGroup)paramObject;
    return (this.aZR == localApprovalsGroup.getApprovalsGroupID()) && (this.aZT == localApprovalsGroup.getBusinessID()) && (this.aZS.equals(localApprovalsGroup.getGroupName()));
  }
  
  public void set(ApprovalsGroup paramApprovalsGroup)
  {
    super.set(paramApprovalsGroup);
    setApprovalsGroupID(paramApprovalsGroup.getApprovalsGroupID());
    setBusinessID(paramApprovalsGroup.getBusinessID());
    setGroupName(paramApprovalsGroup.getGroupName());
    setLocale(paramApprovalsGroup.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("APPROVALGROUPID")) {
        this.aZR = Integer.parseInt(paramString2);
      } else if (paramString1.equals("APPROVALGROUPBUSINESSID")) {
        this.aZT = Integer.parseInt(paramString2);
      } else if (paramString1.equals("APPROVALGROUPNAME")) {
        this.aZS = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALSGROUP");
    XMLHandler.appendTag(localStringBuffer, "APPROVALGROUPID", this.aZR);
    XMLHandler.appendTag(localStringBuffer, "APPROVALGROUPBUSINESSID", this.aZT);
    XMLHandler.appendTag(localStringBuffer, "APPROVALGROUPNAME", this.aZS);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALSGROUP");
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
    localStringBuffer.append("approvalsGroupID=").append(this.aZR);
    localStringBuffer.append("businessID=").append(this.aZT);
    localStringBuffer.append("groupName=").append(this.aZS);
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
      ApprovalsGroup.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsGroup
 * JD-Core Version:    0.7.0.1
 */