package com.ffusion.util.beans.blockedaccts;

import java.text.Collator;
import java.util.StringTokenizer;

public class BlockedAccountSearchResult
  extends BlockedAccount
{
  public static final String USER_NAME_FIELD = "USER_NAME";
  public static final String FIRST_NAME_FIELD = "FIRST_NAME";
  public static final String LAST_NAME_FIELD = "LAST_NAME";
  private String i;
  private String h;
  private String g;
  
  public BlockedAccountSearchResult()
  {
    this.i = null;
    this.h = null;
    this.g = null;
  }
  
  public BlockedAccountSearchResult(String paramString1, String paramString2, String paramString3)
  {
    this.i = paramString1;
    this.h = paramString2;
    this.g = paramString3;
  }
  
  public BlockedAccountSearchResult(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6)
  {
    super(paramString1, paramString2, paramString3, paramInt);
    this.i = paramString4;
    this.h = paramString5;
    this.g = paramString6;
  }
  
  public void setUserName(String paramString)
  {
    if (paramString != null) {
      this.i = paramString.trim();
    } else {
      this.i = null;
    }
  }
  
  public String getUserName()
  {
    return this.i;
  }
  
  public void setFirstName(String paramString)
  {
    if (paramString != null) {
      this.h = paramString.trim();
    } else {
      this.h = null;
    }
  }
  
  public String getFirstName()
  {
    return this.h;
  }
  
  public void setLastName(String paramString)
  {
    if (paramString != null) {
      this.g = paramString.trim();
    } else {
      this.g = null;
    }
  }
  
  public String getLastName()
  {
    return this.g;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    int j = -1;
    BlockedAccountSearchResult localBlockedAccountSearchResult = (BlockedAccountSearchResult)paramObject;
    if ("USER_NAME".equals(paramString)) {
      j = localCollator.compare(this.i == null ? null : this.i.toLowerCase(this.locale), localBlockedAccountSearchResult.i == null ? null : localBlockedAccountSearchResult.i.toLowerCase(this.locale));
    } else if ("FIRST_NAME".equals(paramString)) {
      j = localCollator.compare(this.h == null ? null : this.h.toLowerCase(this.locale), localBlockedAccountSearchResult.h == null ? null : localBlockedAccountSearchResult.h.toLowerCase(this.locale));
    } else if ("LAST_NAME".equals(paramString)) {
      j = localCollator.compare(this.g == null ? null : this.g.toLowerCase(this.locale), localBlockedAccountSearchResult.g == null ? null : localBlockedAccountSearchResult.g.toLowerCase(this.locale));
    } else {
      j = super.compare(paramObject, paramString);
    }
    return j;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof BlockedAccountSearchResult))
    {
      BlockedAccountSearchResult localBlockedAccountSearchResult = (BlockedAccountSearchResult)paramObject;
      if (((this.i != null) && (this.i.equals(localBlockedAccountSearchResult.i))) || ((this.i == null) && (localBlockedAccountSearchResult.i == null) && (((this.h != null) && (this.h.equals(localBlockedAccountSearchResult.h))) || ((this.h == null) && (localBlockedAccountSearchResult.h == null) && (((this.g != null) && (this.g.equals(localBlockedAccountSearchResult.g))) || ((this.g == null) && (localBlockedAccountSearchResult.g == null))))))) {
        return super.equals(paramObject);
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    return toString().hashCode();
  }
  
  public String toString()
  {
    String str1 = this.h != null ? this.h : "";
    String str2 = this.g != null ? this.g : "";
    String str3 = this.i != null ? this.i : "";
    return super.toString() + " " + str1 + " " + str2 + " " + str3;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      if (str1.equals("USER_NAME")) {
        return isFilterable(this.i, str2, str3);
      }
      if (str1.equals("FIRST_NAME")) {
        return isFilterable(this.h, str2, str3);
      }
      if (str1.equals("LAST_NAME")) {
        return isFilterable(this.g, str2, str3);
      }
      return super.isFilterable(paramString);
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResult
 * JD-Core Version:    0.7.0.1
 */