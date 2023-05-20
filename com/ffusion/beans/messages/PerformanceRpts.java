package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.util.FilteredList;
import java.text.NumberFormat;
import java.util.Iterator;

public class PerformanceRpts
  extends FilteredList
{
  DateTime jdField_byte;
  String jdField_goto = "";
  private float b = 0.0F;
  private float jdField_case = 0.0F;
  private float jdField_else = 0.0F;
  private float jdField_void = 0.0F;
  private float jdField_char = 0.0F;
  private float jdField_long = 0.0F;
  private float jdField_null = 0.0F;
  
  public void setReportDate(DateTime paramDateTime)
  {
    this.jdField_byte = paramDateTime;
  }
  
  public String getReportDate()
  {
    if (this.jdField_byte != null) {
      return this.jdField_byte.toString();
    }
    return null;
  }
  
  public DateTime getReportDateValue()
  {
    return this.jdField_byte;
  }
  
  public void setReportDateFormat(String paramString)
  {
    this.jdField_byte.setFormat(paramString);
  }
  
  public PerformanceRpt create()
  {
    PerformanceRpt localPerformanceRpt = new PerformanceRpt();
    localPerformanceRpt.setAvgTimeToHandleGoal(this.jdField_void);
    localPerformanceRpt.setAvgTimeToOpenGoal(this.jdField_else);
    localPerformanceRpt.setClosedCounterGoal(this.jdField_case);
    localPerformanceRpt.setCounterGoal(this.b);
    localPerformanceRpt.setPctAppDeniedGoal(this.jdField_null);
    localPerformanceRpt.setPctInternalTransferGoal(this.jdField_char);
    localPerformanceRpt.setPctSingleEventResolutionsGoal(this.jdField_long);
    add(localPerformanceRpt);
    return localPerformanceRpt;
  }
  
  public PerformanceRpt create(String paramString1, String paramString2)
  {
    PerformanceRpt localPerformanceRpt = new PerformanceRpt(paramString1, paramString2);
    localPerformanceRpt.setAvgTimeToHandleGoal(this.jdField_void);
    localPerformanceRpt.setAvgTimeToOpenGoal(this.jdField_else);
    localPerformanceRpt.setClosedCounterGoal(this.jdField_case);
    localPerformanceRpt.setCounterGoal(this.b);
    localPerformanceRpt.setPctAppDeniedGoal(this.jdField_null);
    localPerformanceRpt.setPctInternalTransferGoal(this.jdField_char);
    localPerformanceRpt.setPctSingleEventResolutionsGoal(this.jdField_long);
    add(localPerformanceRpt);
    return localPerformanceRpt;
  }
  
  public PerformanceRpt createNoAdd()
  {
    PerformanceRpt localPerformanceRpt = new PerformanceRpt();
    localPerformanceRpt.setAvgTimeToHandleGoal(this.jdField_void);
    localPerformanceRpt.setAvgTimeToOpenGoal(this.jdField_else);
    localPerformanceRpt.setClosedCounterGoal(this.jdField_case);
    localPerformanceRpt.setCounterGoal(this.b);
    localPerformanceRpt.setPctAppDeniedGoal(this.jdField_null);
    localPerformanceRpt.setPctInternalTransferGoal(this.jdField_char);
    localPerformanceRpt.setPctSingleEventResolutionsGoal(this.jdField_long);
    return localPerformanceRpt;
  }
  
  public PerformanceRpt createNoAdd(String paramString1, String paramString2)
  {
    PerformanceRpt localPerformanceRpt = new PerformanceRpt(paramString1, paramString2);
    localPerformanceRpt.setAvgTimeToHandleGoal(this.jdField_void);
    localPerformanceRpt.setAvgTimeToOpenGoal(this.jdField_else);
    localPerformanceRpt.setClosedCounterGoal(this.jdField_case);
    localPerformanceRpt.setCounterGoal(this.b);
    localPerformanceRpt.setPctAppDeniedGoal(this.jdField_null);
    localPerformanceRpt.setPctInternalTransferGoal(this.jdField_char);
    localPerformanceRpt.setPctSingleEventResolutionsGoal(this.jdField_long);
    return localPerformanceRpt;
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof PerformanceRpt)) {
      return super.add(paramObject);
    }
    return false;
  }
  
  public PerformanceRpt getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      if (localPerformanceRpt.getId().equals(paramString))
      {
        localObject = localPerformanceRpt;
        break;
      }
    }
    return localObject;
  }
  
  public PerformanceRpt getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      if (localPerformanceRpt.getName().equals(paramString))
      {
        localObject = localPerformanceRpt;
        break;
      }
    }
    return localObject;
  }
  
  public void setCounterGoal(String paramString)
  {
    this.b = Float.parseFloat(paramString);
  }
  
  public void setCounterGoal(float paramFloat)
  {
    this.b = paramFloat;
  }
  
  public void setClosedCounterGoal(String paramString)
  {
    this.jdField_case = Float.parseFloat(paramString);
  }
  
  public void setClosedCounterGoal(float paramFloat)
  {
    this.jdField_case = paramFloat;
  }
  
  public void setAvgTimeToOpenGoal(String paramString)
  {
    this.jdField_else = Float.parseFloat(paramString);
  }
  
  public void setAvgTimeToOpenGoal(float paramFloat)
  {
    this.jdField_else = paramFloat;
  }
  
  public void setAvgTimeToHandleGoal(String paramString)
  {
    this.jdField_void = Float.parseFloat(paramString);
  }
  
  public void setAvgTimeToHandleGoal(float paramFloat)
  {
    this.jdField_void = paramFloat;
  }
  
  public void setPctInternalTransferGoal(String paramString)
  {
    this.jdField_char = Float.parseFloat(paramString);
  }
  
  public void setPctInternalTransferGoal(float paramFloat)
  {
    this.jdField_char = paramFloat;
  }
  
  public void setPctSingleEventResolutionsGoal(String paramString)
  {
    this.jdField_long = Float.parseFloat(paramString);
  }
  
  public void setPctSingleEventResolutionsGoal(float paramFloat)
  {
    this.jdField_long = paramFloat;
  }
  
  public void setPctAppDeniedGoal(String paramString)
  {
    this.jdField_null = Float.parseFloat(paramString);
  }
  
  public void setPctAppDeniedGoal(float paramFloat)
  {
    this.jdField_null = paramFloat;
  }
  
  public void setAgentName(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getAgentName()
  {
    return this.jdField_goto;
  }
  
  public String getWeek1ItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWeek1ItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWeek2ItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWeek2ItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWeek3ItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWeek3ItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWeek4ItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWeek4ItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWeek5ItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWeek5ItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWeek6ItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWeek6ItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getSunItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getSunItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getMonItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getMonItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getTueItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getTueItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWedItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getWedItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getThuItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getThuItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getFriItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getFriItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getSatItemClosedCountTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += Integer.parseInt(localPerformanceRpt.getSatItemClosedCount());
    }
    return String.valueOf(i);
  }
  
  public String getWeek1AvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek1TimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek1Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek2AvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek2TimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek2Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek3AvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek3TimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek3Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek4AvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek4TimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek4Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek5AvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek5TimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek5Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek6AvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek6TimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek6Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getSunAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getSunTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSunCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getMonAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getMonTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getMonCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getTueAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getTueTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getTueCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWedAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWedTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWedCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getThuAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getThuTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getThuCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getFriAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getFriTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getFriCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getSatAvgTimeToOpenTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getSatTimeToOpen())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSatCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek1AvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek1TimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek1Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek2AvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek2TimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek2Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek3AvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek3TimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek3Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek4AvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek4TimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek4Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek5AvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek5TimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek5Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek6AvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWeek6TimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek6Counter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getSunAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getSunTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSunCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getMonAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getMonTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getMonCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getTueAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getTueTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getTueCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWedAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getWedTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWedCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getThuAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getThuTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getThuCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getFriAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getFriTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getFriCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getSatAvgTimeToHandleTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    PerformanceRpt localPerformanceRpt;
    for (float f1 = 0.0F; localIterator.hasNext(); f1 += localPerformanceRpt.getSatTimeToHandle())
    {
      localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSatCounter();
    }
    float f2 = f1 / i;
    if (i == 0) {
      return "0";
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getWeek1PctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek1Counter();
      j += localPerformanceRpt.getWeek1InternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek2PctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek2Counter();
      j += localPerformanceRpt.getWeek2InternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek3PctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek3Counter();
      j += localPerformanceRpt.getWeek3InternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek4PctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek4Counter();
      j += localPerformanceRpt.getWeek4InternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek5PctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek5Counter();
      j += localPerformanceRpt.getWeek5InternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek6PctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek6Counter();
      j += localPerformanceRpt.getWeek6InternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getSunPctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSunCounter();
      j += localPerformanceRpt.getSunInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getMonPctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getMonCounter();
      j += localPerformanceRpt.getMonInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getTuePctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getTueCounter();
      j += localPerformanceRpt.getTueInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWedPctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWedCounter();
      j += localPerformanceRpt.getWedInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getThuPctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getThuCounter();
      j += localPerformanceRpt.getThuInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getFriPctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getFriCounter();
      j += localPerformanceRpt.getFriInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getSatPctInternalTransfersTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSatCounter();
      j += localPerformanceRpt.getSatInternalTransfers();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek1PctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek1Counter();
      j += localPerformanceRpt.getWeek1SingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek2PctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek2Counter();
      j += localPerformanceRpt.getWeek2SingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek3PctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek3Counter();
      j += localPerformanceRpt.getWeek3SingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek4PctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek4Counter();
      j += localPerformanceRpt.getWeek4SingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek5PctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek5Counter();
      j += localPerformanceRpt.getWeek5SingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek6PctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek6Counter();
      j += localPerformanceRpt.getWeek6SingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getSunPctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSunCounter();
      j += localPerformanceRpt.getSunSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getMonPctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getMonCounter();
      j += localPerformanceRpt.getMonSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getTuePctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getTueCounter();
      j += localPerformanceRpt.getTueSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWedPctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWedCounter();
      j += localPerformanceRpt.getWedSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getThuPctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getThuCounter();
      j += localPerformanceRpt.getThuSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getFriPctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getFriCounter();
      j += localPerformanceRpt.getFriSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getSatPctSingleEventResolutionsTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSatCounter();
      j += localPerformanceRpt.getSatSingleEventResolutions();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek1PctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek1Counter();
      j += localPerformanceRpt.getWeek1AppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek2PctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek2Counter();
      j += localPerformanceRpt.getWeek2AppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek3PctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek3Counter();
      j += localPerformanceRpt.getWeek3AppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek4PctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek4Counter();
      j += localPerformanceRpt.getWeek4AppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek5PctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek5Counter();
      j += localPerformanceRpt.getWeek5AppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWeek6PctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWeek6Counter();
      j += localPerformanceRpt.getWeek6AppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getSunPctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSunCounter();
      j += localPerformanceRpt.getSunAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getMonPctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getMonCounter();
      j += localPerformanceRpt.getMonAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getTuePctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getTueCounter();
      j += localPerformanceRpt.getTueAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getWedPctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getWedCounter();
      j += localPerformanceRpt.getWedAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getThuPctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getThuCounter();
      j += localPerformanceRpt.getThuAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getFriPctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getFriCounter();
      j += localPerformanceRpt.getFriAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
  
  public String getSatPctAppDeniedTotal()
  {
    Iterator localIterator = iterator();
    int i = 0;
    int j = 0;
    while (localIterator.hasNext())
    {
      PerformanceRpt localPerformanceRpt = (PerformanceRpt)localIterator.next();
      i += localPerformanceRpt.getSatCounter();
      j += localPerformanceRpt.getSatAppDenied();
    }
    if (i == 0) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f = j / i;
    return NumberFormat.getPercentInstance().format(f);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.PerformanceRpts
 * JD-Core Version:    0.7.0.1
 */