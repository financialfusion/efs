package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ACHBatchHist
  implements Serializable
{
  protected String i = null;
  protected String jdField_char = null;
  protected String jdField_new = null;
  protected String jdField_void = null;
  protected String d = null;
  protected String q = null;
  protected String o = null;
  protected String jdField_for = null;
  protected String n = null;
  protected String r = null;
  protected String b = null;
  protected String[] jdField_if = null;
  protected String[] j = null;
  protected String[] jdField_null = null;
  protected String[] jdField_byte = null;
  protected Object[] jdField_else = null;
  protected boolean jdField_int = true;
  protected boolean t = false;
  protected String jdField_case = null;
  protected String p = null;
  protected String a = null;
  protected String c = null;
  protected int s = 0;
  protected int h = 0;
  protected String k = null;
  protected String jdField_long = null;
  protected String m = null;
  protected String jdField_try = "ACH";
  protected int jdField_goto = -1;
  protected String g = null;
  protected long f = 0L;
  protected long l = 0L;
  protected String e = null;
  protected String jdField_do = null;
  
  public void setCompId(String paramString)
  {
    this.d = paramString;
  }
  
  public String getFIId()
  {
    return this.jdField_char;
  }
  
  public void setFIId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getCustomerId()
  {
    return this.jdField_void;
  }
  
  public void setCustomerId(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public Object[] getBatches()
  {
    return this.jdField_else;
  }
  
  public Object getBatchAt(int paramInt)
  {
    if (this.jdField_else == null) {
      return null;
    }
    if (paramInt < this.jdField_else.length) {
      return this.jdField_else[paramInt];
    }
    return null;
  }
  
  public void setBatches(Object[] paramArrayOfObject)
  {
    this.jdField_else = paramArrayOfObject;
  }
  
  public String getStartDate()
  {
    return this.jdField_for;
  }
  
  public void setStartDate(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getEndDate()
  {
    return this.n;
  }
  
  public void setEndDate(String paramString)
  {
    this.n = paramString;
  }
  
  public String getStartEffectiveDate()
  {
    return this.r;
  }
  
  public void setStartEffectiveDate(String paramString)
  {
    this.r = paramString;
  }
  
  public String getEndEffectiveDate()
  {
    return this.b;
  }
  
  public void setEndEffectiveDate(String paramString)
  {
    this.b = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_goto;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_goto = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.g;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.g = paramString;
  }
  
  public long getTotalBatches()
  {
    return this.f;
  }
  
  public void setTotalBatches(long paramLong)
  {
    this.f = paramLong;
  }
  
  public long getBatchPageSize()
  {
    return this.l;
  }
  
  public void setBatchPageSize(long paramLong)
  {
    this.l = paramLong;
  }
  
  public String getHistId()
  {
    return this.e;
  }
  
  public void setHistId(String paramString)
  {
    this.e = paramString;
  }
  
  public String getCursorId()
  {
    return this.jdField_do;
  }
  
  public void setCursorId(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getPayeeId()
  {
    return this.q;
  }
  
  public void setPayeeId(String paramString)
  {
    this.q = paramString;
  }
  
  public String getLogId()
  {
    return this.o;
  }
  
  public void setLogId(String paramString)
  {
    this.o = paramString;
  }
  
  public String getCompId()
  {
    return this.d;
  }
  
  public String[] getSubmittedByList()
  {
    return this.jdField_if;
  }
  
  public void setSubmittedByList(String[] paramArrayOfString)
  {
    this.jdField_if = paramArrayOfString;
  }
  
  public String[] getBatchStatusList()
  {
    return this.j;
  }
  
  public void setBatchStatusList(String[] paramArrayOfString)
  {
    this.j = paramArrayOfString;
  }
  
  public String[] getBatchCategoryList()
  {
    return this.jdField_null;
  }
  
  public void setBatchCategoryList(String[] paramArrayOfString)
  {
    this.jdField_null = paramArrayOfString;
  }
  
  public String getODFIACHId()
  {
    return this.jdField_new;
  }
  
  public void setODFIACHId(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String[] getBatchIdList()
  {
    return this.jdField_byte;
  }
  
  public void setBatchIdList(String[] paramArrayOfString)
  {
    this.jdField_byte = paramArrayOfString;
  }
  
  public boolean getAscending()
  {
    return this.jdField_int;
  }
  
  public void setAscending(boolean paramBoolean)
  {
    this.jdField_int = paramBoolean;
  }
  
  public boolean getViewEntitlementCheck()
  {
    return this.t;
  }
  
  public void setViewEntitlementCheck(boolean paramBoolean)
  {
    this.t = paramBoolean;
  }
  
  public String getViewerId()
  {
    return this.jdField_case;
  }
  
  public void setViewerId(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getId()
  {
    return this.i;
  }
  
  public void setId(String paramString)
  {
    this.i = paramString;
  }
  
  public String getBatchName()
  {
    return this.a;
  }
  
  public void setBatchName(String paramString)
  {
    this.a = paramString;
  }
  
  public String getCompName()
  {
    return this.c;
  }
  
  public void setCompName(String paramString)
  {
    this.c = paramString;
  }
  
  public int getNumberOfEntries()
  {
    return this.s;
  }
  
  public void setNumberOfEntries(int paramInt)
  {
    this.s = paramInt;
  }
  
  public int getFrequency()
  {
    return this.h;
  }
  
  public void setFrequency(int paramInt)
  {
    this.h = paramInt;
  }
  
  public String getStatus()
  {
    return this.k;
  }
  
  public void setStatus(String paramString)
  {
    this.k = paramString;
  }
  
  public String getTotalCredit()
  {
    return this.jdField_long;
  }
  
  public void setTotalCredit(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getTotalDebit()
  {
    return this.m;
  }
  
  public void setTotalDebit(String paramString)
  {
    this.m = paramString;
  }
  
  public String getTranType()
  {
    return this.jdField_try;
  }
  
  public void setTranType(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getDueDate()
  {
    return this.p;
  }
  
  public void setDueDate(String paramString)
  {
    this.p = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHBatchHist
 * JD-Core Version:    0.7.0.1
 */