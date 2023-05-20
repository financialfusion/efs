package com.ffusion.beans.messages;

import com.ffusion.util.Sortable;

public class FacilityAgingQueue
  implements Sortable
{
  private String jdField_int = "";
  private String jdField_if = "";
  private int jdField_do = 0;
  private int a = 0;
  private int jdField_try = 0;
  private int jdField_for = 0;
  private int jdField_new = 0;
  
  public FacilityAgingQueue(String paramString1, String paramString2)
  {
    this.jdField_if = paramString1;
    this.jdField_int = paramString2;
  }
  
  public String getQueueID()
  {
    return this.jdField_if;
  }
  
  public void setQueueID(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getQueueName()
  {
    return this.jdField_int;
  }
  
  public void setQueueName(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public void setUnderOneDayThread(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getUnderOneDayThread()
  {
    return String.valueOf(this.jdField_do);
  }
  
  public void setOne_TwoDayThread(int paramInt)
  {
    this.a = paramInt;
  }
  
  public String getOne_TwoDayThread()
  {
    return String.valueOf(this.a);
  }
  
  public void setTwo_FourDayThread(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public String getTwo_fourDayThread()
  {
    return String.valueOf(this.jdField_try);
  }
  
  public void setFive_SevenDayThread(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public String getFive_SevenDayThread()
  {
    return String.valueOf(this.jdField_for);
  }
  
  public void setOverSevenDayThread(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public String getOverSevenDayThread()
  {
    return String.valueOf(this.jdField_new);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    FacilityAgingQueue localFacilityAgingQueue = (FacilityAgingQueue)paramObject;
    int i;
    if (this == localFacilityAgingQueue) {
      i = 0;
    } else {
      i = getQueueName().compareTo(localFacilityAgingQueue.getQueueName());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FacilityAgingQueue localFacilityAgingQueue = (FacilityAgingQueue)paramObject;
    int i = 0;
    if ((paramString.equals("QueueName")) && (getQueueName() != null) && (localFacilityAgingQueue.getQueueName() != null)) {
      i = getQueueName().compareTo(localFacilityAgingQueue.getQueueName());
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.FacilityAgingQueue
 * JD-Core Version:    0.7.0.1
 */