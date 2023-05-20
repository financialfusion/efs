package com.ffusion.util.beans.blockedaccts;

import com.ffusion.util.beans.ExtendABean;

public class BlockedAccountSearchCriteria
  extends ExtendABean
{
  private String jdField_void;
  private String jdField_case;
  private String jdField_null;
  private String jdField_else;
  private String jdField_long;
  private String jdField_char;
  private String jdField_goto;
  
  public BlockedAccountSearchCriteria()
  {
    this.jdField_void = null;
    this.jdField_case = null;
    this.jdField_null = null;
    this.jdField_long = null;
    this.jdField_else = null;
  }
  
  public BlockedAccountSearchCriteria(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.jdField_void = paramString1;
    this.jdField_case = paramString2;
    this.jdField_null = paramString3;
    this.jdField_long = paramString4;
    this.jdField_char = paramString5;
    this.jdField_goto = paramString6;
    this.jdField_else = null;
  }
  
  public BlockedAccountSearchCriteria(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
    this.jdField_else = paramString7;
  }
  
  public void setRoutingNumber(String paramString)
  {
    if (paramString != null) {
      this.jdField_void = paramString.trim();
    } else {
      this.jdField_void = null;
    }
  }
  
  public String getRoutingNumber()
  {
    return this.jdField_void;
  }
  
  public void setBankName(String paramString)
  {
    if (paramString != null) {
      this.jdField_case = paramString.trim();
    } else {
      this.jdField_case = null;
    }
  }
  
  public String getBankName()
  {
    return this.jdField_case;
  }
  
  public void setAccountNumber(String paramString)
  {
    if (paramString != null) {
      this.jdField_null = paramString.trim();
    } else {
      this.jdField_null = null;
    }
  }
  
  public String getAccountNumber()
  {
    return this.jdField_null;
  }
  
  public void setStrippedAccountNumber(String paramString)
  {
    if (paramString != null) {
      this.jdField_else = paramString.trim();
    } else {
      this.jdField_else = null;
    }
  }
  
  public String getStrippedAccountNumber()
  {
    return this.jdField_else;
  }
  
  public void setUserName(String paramString)
  {
    if (paramString != null) {
      this.jdField_long = paramString.trim();
    } else {
      this.jdField_long = null;
    }
  }
  
  public String getUserName()
  {
    return this.jdField_long;
  }
  
  public void setFirstName(String paramString)
  {
    if (paramString != null) {
      this.jdField_goto = paramString.trim();
    } else {
      this.jdField_goto = null;
    }
  }
  
  public String getFirstName()
  {
    return this.jdField_goto;
  }
  
  public void setLastName(String paramString)
  {
    if (paramString != null) {
      this.jdField_char = paramString.trim();
    } else {
      this.jdField_char = null;
    }
  }
  
  public String getLastName()
  {
    return this.jdField_char;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria
 * JD-Core Version:    0.7.0.1
 */