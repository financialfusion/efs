package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class FacilityAgingQueues
  extends FilteredList
{
  private int jdField_case = 0;
  private int jdField_goto = 0;
  private int jdField_else = 0;
  private int jdField_char = 0;
  private int jdField_byte = 0;
  private DateTime jdField_long;
  
  public void setReportDate(DateTime paramDateTime)
  {
    this.jdField_long = paramDateTime;
  }
  
  public String getReportDate()
  {
    return this.jdField_long.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.jdField_long.setFormat(paramString);
  }
  
  public void setUnderOneDayQueueTotal(int paramInt)
  {
    this.jdField_case = paramInt;
  }
  
  public String getUnderOneDayQueueTotal()
  {
    return String.valueOf(this.jdField_case);
  }
  
  public void setOne_TwoDayQueueTotal(int paramInt)
  {
    this.jdField_goto = paramInt;
  }
  
  public String getOne_TwoDayQueueTotal()
  {
    return String.valueOf(this.jdField_goto);
  }
  
  public void setTwo_FourDayQueueTotal(int paramInt)
  {
    this.jdField_else = paramInt;
  }
  
  public String getTwo_FourDayQueueTotal()
  {
    return String.valueOf(this.jdField_else);
  }
  
  public void setFive_SevenDayQueueTotal(int paramInt)
  {
    this.jdField_char = paramInt;
  }
  
  public String getFive_SevenDayQueueTotal()
  {
    return String.valueOf(this.jdField_char);
  }
  
  public void setOverSevenDayQueueTotal(int paramInt)
  {
    this.jdField_byte = paramInt;
  }
  
  public String getOverSevenDayQueueTotal()
  {
    return String.valueOf(this.jdField_byte);
  }
  
  public FacilityAgingQueue getByQueueName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FacilityAgingQueue localFacilityAgingQueue = (FacilityAgingQueue)localIterator.next();
      if (localFacilityAgingQueue.getQueueName().equals(paramString))
      {
        localObject = localFacilityAgingQueue;
        break;
      }
    }
    return localObject;
  }
  
  public FacilityAgingQueue getByQueueID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FacilityAgingQueue localFacilityAgingQueue = (FacilityAgingQueue)localIterator.next();
      if (localFacilityAgingQueue.getQueueID().equals(paramString))
      {
        localObject = localFacilityAgingQueue;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.FacilityAgingQueues
 * JD-Core Version:    0.7.0.1
 */