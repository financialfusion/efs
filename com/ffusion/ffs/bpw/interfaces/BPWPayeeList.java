package com.ffusion.ffs.bpw.interfaces;

public class BPWPayeeList
  extends BPWPagingBase
{
  protected String jdField_if = null;
  public String custId = null;
  protected String[] a = null;
  public String[] payeeTypeList = null;
  public String[] payeeGroupList = null;
  public String[] submittedBy = null;
  public String[] payeeDestList = null;
  protected String jdField_int = null;
  public Object[] payees = null;
  public String payeeCategory = null;
  protected String jdField_do = null;
  protected String jdField_new = null;
  protected String jdField_for = null;
  public int statusCode = -1;
  public String statusMsg = null;
  
  public String getFIId()
  {
    return this.jdField_if;
  }
  
  public void setFIId(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String[] getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String[] paramArrayOfString)
  {
    this.submittedBy = paramArrayOfString;
  }
  
  public String[] getPayeeDestList()
  {
    return this.payeeDestList;
  }
  
  public void setPayeeDestList(String[] paramArrayOfString)
  {
    this.payeeDestList = paramArrayOfString;
  }
  
  public String[] getPayeeStatusList()
  {
    return this.a;
  }
  
  public void setPayeeStatusList(String[] paramArrayOfString)
  {
    this.a = paramArrayOfString;
  }
  
  public String getCustId()
  {
    return this.custId;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
  }
  
  public String[] getPayeeTypeList()
  {
    return this.payeeTypeList;
  }
  
  public void setPayeeTypeList(String[] paramArrayOfString)
  {
    this.payeeTypeList = paramArrayOfString;
  }
  
  public String getPayeeType()
  {
    return this.jdField_new;
  }
  
  public void setPayeeType(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String[] getPayeeGroupList()
  {
    return this.payeeGroupList;
  }
  
  public void setPayeeGroupList(String[] paramArrayOfString)
  {
    this.payeeGroupList = paramArrayOfString;
  }
  
  public String getPayeeGroup()
  {
    return this.jdField_for;
  }
  
  public void setPayeeGroup(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public Object[] getPayees()
  {
    return this.payees;
  }
  
  public Object getPayees(int paramInt)
  {
    if ((this.payees == null) || (paramInt < 0) || (paramInt >= this.payees.length)) {
      return null;
    }
    return this.payees[paramInt];
  }
  
  public void setPayees(Object[] paramArrayOfObject)
  {
    this.payees = paramArrayOfObject;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.statusMsg;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.statusMsg = paramString;
  }
  
  public String getPayeeCategory()
  {
    return this.payeeCategory;
  }
  
  public void setPayeeCategory(String paramString)
  {
    this.payeeCategory = paramString;
  }
  
  public String getManagedPayee()
  {
    return this.jdField_int;
  }
  
  public void setManagedPayee(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getRequiredStatus()
  {
    return this.jdField_do;
  }
  
  public void setRequiredStatus(String paramString)
  {
    this.jdField_do = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWPayeeList
 * JD-Core Version:    0.7.0.1
 */