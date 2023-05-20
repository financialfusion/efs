package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsGroupMember
  extends ExtendABean
{
  private int aZU;
  private int aZV;
  
  public int getApprovalsGroupID()
  {
    return this.aZU;
  }
  
  public void setApprovalsGroupID(int paramInt)
  {
    this.aZU = paramInt;
  }
  
  public int getUserID()
  {
    return this.aZV;
  }
  
  public void setUserID(int paramInt)
  {
    this.aZV = paramInt;
  }
  
  public ApprovalsGroupMember(Locale paramLocale)
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
        return this.aZU == i;
      }
      if (str1.equalsIgnoreCase("APPROVALGROUPUSERID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZV == i;
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
    ApprovalsGroupMember localApprovalsGroupMember = (ApprovalsGroupMember)paramObject;
    int i = 1;
    if (paramString.equals("APPROVALGROUPID"))
    {
      if (this.aZU == localApprovalsGroupMember.getApprovalsGroupID()) {
        i = 0;
      } else if (this.aZU < localApprovalsGroupMember.getApprovalsGroupID()) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else if (paramString.equals("APPROVALGROUPUSERID")) {
      if (this.aZV == localApprovalsGroupMember.getUserID()) {
        i = 0;
      } else if (this.aZV < localApprovalsGroupMember.getUserID()) {
        i = -1;
      } else {
        i = 1;
      }
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsGroupMember)) {
      return false;
    }
    ApprovalsGroupMember localApprovalsGroupMember = (ApprovalsGroupMember)paramObject;
    return (this.aZU == localApprovalsGroupMember.getApprovalsGroupID()) && (this.aZV == localApprovalsGroupMember.getUserID());
  }
  
  public void set(ApprovalsGroupMember paramApprovalsGroupMember)
  {
    super.set(paramApprovalsGroupMember);
    setApprovalsGroupID(paramApprovalsGroupMember.getApprovalsGroupID());
    setUserID(paramApprovalsGroupMember.getUserID());
    setLocale(paramApprovalsGroupMember.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("APPROVALGROUPID")) {
        this.aZU = Integer.parseInt(paramString2);
      } else if (paramString1.equals("APPROVALGROUPUSERID")) {
        this.aZV = Integer.parseInt(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALSGROUPMEMBER");
    XMLHandler.appendTag(localStringBuffer, "APPROVALGROUPID", this.aZU);
    XMLHandler.appendTag(localStringBuffer, "APPROVALGROUPUSERID", this.aZV);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALSGROUPMEMBER");
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
    localStringBuffer.append("approvalsGroupID=").append(this.aZU);
    localStringBuffer.append("userID=").append(this.aZV);
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
      ApprovalsGroupMember.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsGroupMember
 * JD-Core Version:    0.7.0.1
 */