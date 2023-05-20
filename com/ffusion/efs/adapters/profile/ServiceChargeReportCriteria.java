package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.reporting.ReportCriteria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ServiceChargeReportCriteria
{
  private ArrayList jdField_for = null;
  private DateTime jdField_do = null;
  private DateTime jdField_if = null;
  private int a;
  private ReportCriteria jdField_int = null;
  private HashMap jdField_try = null;
  private int jdField_new = 3;
  
  public ServiceChargeReportCriteria(ReportCriteria paramReportCriteria)
  {
    setReportCriteria(paramReportCriteria);
  }
  
  public DateTime getEnd()
  {
    return this.jdField_if;
  }
  
  public void setEnd(DateTime paramDateTime)
  {
    this.jdField_if = paramDateTime;
  }
  
  public DateTime getStart()
  {
    return this.jdField_do;
  }
  
  public void setStart(DateTime paramDateTime)
  {
    this.jdField_do = paramDateTime;
  }
  
  public String getIdsDivided()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (int j = 0; j < getIds().size(); j++)
    {
      localStringBuffer.append((String)getIds().get(j));
      localStringBuffer.append(",");
      i = 1;
    }
    if (i != 0) {
      localStringBuffer = localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
    }
    return localStringBuffer.toString();
  }
  
  public int getAffiliateBankId()
  {
    return this.a;
  }
  
  public void setAffiliateBankId(int paramInt)
  {
    this.a = paramInt;
  }
  
  public ReportCriteria getReportCriteria()
  {
    return this.jdField_int;
  }
  
  public void setReportCriteria(ReportCriteria paramReportCriteria)
  {
    this.jdField_int = paramReportCriteria;
  }
  
  public ArrayList getIds()
  {
    return this.jdField_for;
  }
  
  public void setIds(ArrayList paramArrayList)
  {
    this.jdField_for = paramArrayList;
  }
  
  public HashMap getChargeableOperations()
  {
    return this.jdField_try;
  }
  
  public void setChargeableOperations(HashMap paramHashMap)
  {
    this.jdField_try = paramHashMap;
  }
  
  public Object[] getChargeableOperationsAsArray()
  {
    return getChargeableOperations().keySet().toArray();
  }
  
  public int getGracePeriodInDays()
  {
    return this.jdField_new;
  }
  
  public void setGracePeriodInDays(int paramInt)
  {
    if (paramInt < 0) {
      this.jdField_new = 3;
    } else {
      this.jdField_new = paramInt;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria
 * JD-Core Version:    0.7.0.1
 */