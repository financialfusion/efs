package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsChainItem
  extends ExtendABean
{
  private int aZN;
  private boolean aZO;
  private int aZQ;
  private boolean aZP;
  
  public int getGroupOrUserID()
  {
    return this.aZN;
  }
  
  public void setGroupOrUserID(int paramInt)
  {
    this.aZN = paramInt;
  }
  
  public boolean getUsingUser()
  {
    return this.aZO;
  }
  
  public boolean isUsingUser()
  {
    return getUsingUser();
  }
  
  public void setUsingUser(boolean paramBoolean)
  {
    this.aZO = paramBoolean;
  }
  
  public boolean getIsApprovalsGroup()
  {
    return this.aZP;
  }
  
  public void setIsApprovalsGroup(boolean paramBoolean)
  {
    this.aZP = paramBoolean;
  }
  
  public int getUserType()
  {
    return this.aZQ;
  }
  
  public void setUserType(int paramInt)
  {
    this.aZQ = paramInt;
  }
  
  public ApprovalsChainItem(Locale paramLocale)
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
      if (str1.equalsIgnoreCase("GROUPORUSERID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZN == i;
      }
      if (str1.equalsIgnoreCase("ISUSER")) {
        return this.aZO == Boolean.valueOf(str2).booleanValue();
      }
      if (str1.equalsIgnoreCase("ISAPPROVALSGROUP")) {
        return this.aZP == Boolean.valueOf(str2).booleanValue();
      }
      if (str1.equalsIgnoreCase("USERTYPE"))
      {
        if (!this.aZO) {
          return false;
        }
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZQ == i;
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "GROUPORUSERID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsChainItem localApprovalsChainItem = (ApprovalsChainItem)paramObject;
    int i = 1;
    if (paramString.equals("GROUPORUSERID"))
    {
      if ((this.aZO == localApprovalsChainItem.isUsingUser()) && (this.aZP == localApprovalsChainItem.getIsApprovalsGroup())) {
        i = this.aZN - localApprovalsChainItem.getGroupOrUserID();
      } else if (!this.aZO) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else if (paramString.equals("ISUSER"))
    {
      if (this.aZO == localApprovalsChainItem.isUsingUser()) {
        i = 0;
      } else if (!this.aZO) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else if (paramString.equals("USERTYPE")) {
      i = this.aZQ - localApprovalsChainItem.getUserType();
    } else if (paramString.equals("ISAPPROVALSGROUP"))
    {
      if (this.aZP == localApprovalsChainItem.getIsApprovalsGroup()) {
        i = 0;
      } else if (!this.aZP) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsChainItem)) {
      return false;
    }
    ApprovalsChainItem localApprovalsChainItem = (ApprovalsChainItem)paramObject;
    return (this.aZN == localApprovalsChainItem.getGroupOrUserID()) && (this.aZO == localApprovalsChainItem.isUsingUser()) && (this.aZQ == localApprovalsChainItem.getUserType()) && (this.aZP == localApprovalsChainItem.getIsApprovalsGroup());
  }
  
  public void set(ApprovalsChainItem paramApprovalsChainItem)
  {
    super.set(paramApprovalsChainItem);
    setGroupOrUserID(paramApprovalsChainItem.getGroupOrUserID());
    setUsingUser(paramApprovalsChainItem.isUsingUser());
    setUserType(paramApprovalsChainItem.getUserType());
    setIsApprovalsGroup(paramApprovalsChainItem.getIsApprovalsGroup());
    setLocale(paramApprovalsChainItem.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("GROUPORUSERID")) {
        this.aZN = Integer.parseInt(paramString2);
      } else if (paramString1.equals("ISUSER")) {
        this.aZO = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("USERTYPE")) {
        this.aZQ = Integer.parseInt(paramString2);
      } else if (paramString1.equals("ISAPPROVALSGROUP")) {
        this.aZP = Boolean.valueOf(paramString2).booleanValue();
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_CHAIN_ITEM");
    XMLHandler.appendTag(localStringBuffer, "GROUPORUSERID", this.aZN);
    XMLHandler.appendTag(localStringBuffer, "ISUSER", new Boolean(this.aZO).toString());
    XMLHandler.appendTag(localStringBuffer, "USERTYPE", this.aZQ);
    XMLHandler.appendTag(localStringBuffer, "ISAPPROVALSGROUP", new Boolean(this.aZP).toString());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_CHAIN_ITEM");
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
    localStringBuffer.append("groupOrUserID=").append(this.aZN);
    localStringBuffer.append(" isUser=").append(this.aZO);
    localStringBuffer.append(" userType=").append(this.aZQ);
    localStringBuffer.append(" isApprovalsGroup=").append(this.aZP);
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
      ApprovalsChainItem.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsChainItem
 * JD-Core Version:    0.7.0.1
 */