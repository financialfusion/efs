package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;
import java.io.Serializable;
import java.text.Collator;
import java.text.NumberFormat;

public class PerformanceRpt
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final int WEEK_1 = 42;
  public static final int WEEK_2 = 35;
  public static final int WEEK_3 = 28;
  public static final int WEEK_4 = 21;
  public static final int WEEK_5 = 14;
  public static final int WEEK_6SUN = 7;
  public static final int WEEK_6MON = 6;
  public static final int WEEK_6TUE = 5;
  public static final int WEEK_6WED = 4;
  public static final int WEEK_6THU = 3;
  public static final int WEEK_6FRI = 2;
  public static final int WEEK_6SAT = 1;
  public static final String ID = "ID";
  public static final String NAME = "NAME";
  protected String id;
  protected String name;
  protected String owner;
  private a aXh;
  private a aW7;
  private a aXj;
  private a aXa;
  private a aW2;
  private a aW5;
  private a aXe;
  private a aW3;
  private a aXc;
  private a aXd;
  private a aXk;
  private a aW9;
  private float aW8 = 0.0F;
  private float aXf = 0.0F;
  private float aW4 = 0.0F;
  private float aXb = 0.0F;
  private float aXi = 0.0F;
  private float aW6 = 0.0F;
  private float aXg = 0.0F;
  
  protected PerformanceRpt()
  {
    this.aXh = new a();
    this.aW7 = new a();
    this.aXj = new a();
    this.aXa = new a();
    this.aW2 = new a();
    this.aW5 = new a();
    this.aXe = new a();
    this.aW3 = new a();
    this.aXc = new a();
    this.aXd = new a();
    this.aXk = new a();
    this.aW9 = new a();
  }
  
  protected PerformanceRpt(String paramString1, String paramString2)
  {
    this.id = paramString1;
    this.name = paramString2;
    this.aXh = new a();
    this.aW7 = new a();
    this.aXj = new a();
    this.aXa = new a();
    this.aW2 = new a();
    this.aW5 = new a();
    this.aXe = new a();
    this.aW3 = new a();
    this.aXc = new a();
    this.aXd = new a();
    this.aXk = new a();
    this.aW9 = new a();
  }
  
  private int jdMethod_for(a parama, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 0;
    if (parama == null)
    {
      i = -1;
    }
    else
    {
      parama.jdField_do += 1;
      parama.jdField_if += paramFloat1;
      parama.jdField_for += paramFloat2;
      if (paramInt1 == 0) {
        parama.a += 1;
      }
      if (paramInt2 != 0) {
        parama.jdField_new += 1;
      }
      if (paramBoolean) {
        parama.jdField_int += 1;
      }
    }
    return i;
  }
  
  private int jdMethod_for(a parama, float paramFloat, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    if (parama == null)
    {
      i = -1;
    }
    else
    {
      parama.jdField_do += 1;
      parama.jdField_for += paramFloat;
      if (paramInt != 0) {
        parama.jdField_try += 1;
      }
      if (paramBoolean) {
        parama.jdField_int += 1;
      }
    }
    return i;
  }
  
  public int RegisterPerformance(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 0;
    if (paramFloat1 < 1.0F) {
      i = jdMethod_for(this.aW9, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 2.0F) {
      i = jdMethod_for(this.aXk, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 3.0F) {
      i = jdMethod_for(this.aXd, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 4.0F) {
      i = jdMethod_for(this.aXc, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 5.0F) {
      i = jdMethod_for(this.aW3, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 6.0F) {
      i = jdMethod_for(this.aXe, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 7.0F) {
      i = jdMethod_for(this.aW5, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 14.0F) {
      i = jdMethod_for(this.aW2, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 21.0F) {
      i = jdMethod_for(this.aXa, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 28.0F) {
      i = jdMethod_for(this.aXj, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 35.0F) {
      i = jdMethod_for(this.aW7, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    } else if (paramFloat1 < 42.0F) {
      i = jdMethod_for(this.aXh, paramFloat2, paramFloat3, paramInt1, paramInt2, paramBoolean);
    }
    return i;
  }
  
  public int RegisterPerformance(float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    if (paramFloat1 < 1.0F) {
      i = jdMethod_for(this.aW9, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 2.0F) {
      i = jdMethod_for(this.aXk, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 3.0F) {
      i = jdMethod_for(this.aXd, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 4.0F) {
      i = jdMethod_for(this.aXc, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 5.0F) {
      i = jdMethod_for(this.aW3, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 6.0F) {
      i = jdMethod_for(this.aXe, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 7.0F) {
      i = jdMethod_for(this.aW5, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 14.0F) {
      i = jdMethod_for(this.aW2, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 21.0F) {
      i = jdMethod_for(this.aXa, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 28.0F) {
      i = jdMethod_for(this.aXj, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 35.0F) {
      i = jdMethod_for(this.aW7, paramFloat2, paramInt, paramBoolean);
    } else if (paramFloat1 < 42.0F) {
      i = jdMethod_for(this.aXh, paramFloat2, paramInt, paramBoolean);
    }
    return i;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "NAME");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PerformanceRpt localPerformanceRpt = (PerformanceRpt)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("NAME")) && (this.name != null) && (localPerformanceRpt.getName() != null)) {
      i = localCollator.compare(getName(), localPerformanceRpt.getName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase("ID")) && (this.id != null)) {
      return isFilterable(this.id, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("NAME")) && (this.name != null)) {
      return isFilterable(this.name, paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setCounterGoal(String paramString)
  {
    this.aW8 = Float.parseFloat(paramString);
  }
  
  public void setCounterGoal(float paramFloat)
  {
    this.aW8 = paramFloat;
  }
  
  public void setClosedCounterGoal(String paramString)
  {
    this.aXf = Float.parseFloat(paramString);
  }
  
  public void setClosedCounterGoal(float paramFloat)
  {
    this.aXf = paramFloat;
  }
  
  public void setAvgTimeToOpenGoal(String paramString)
  {
    this.aW4 = Float.parseFloat(paramString);
  }
  
  public void setAvgTimeToOpenGoal(float paramFloat)
  {
    this.aW4 = paramFloat;
  }
  
  public void setAvgTimeToHandleGoal(String paramString)
  {
    this.aXb = Float.parseFloat(paramString);
  }
  
  public void setAvgTimeToHandleGoal(float paramFloat)
  {
    this.aXb = paramFloat;
  }
  
  public void setPctInternalTransferGoal(String paramString)
  {
    this.aXi = Float.parseFloat(paramString);
  }
  
  public void setPctInternalTransferGoal(float paramFloat)
  {
    this.aXi = paramFloat;
  }
  
  public void setPctSingleEventResolutionsGoal(String paramString)
  {
    this.aW6 = Float.parseFloat(paramString);
  }
  
  public void setPctSingleEventResolutionsGoal(float paramFloat)
  {
    this.aW6 = paramFloat;
  }
  
  public void setPctAppDeniedGoal(String paramString)
  {
    this.aXg = Float.parseFloat(paramString);
  }
  
  public void setPctAppDeniedGoal(float paramFloat)
  {
    this.aXg = paramFloat;
  }
  
  public String getCounterGoal()
  {
    return String.valueOf(this.aW8);
  }
  
  public float getCounterGoalValue()
  {
    return this.aW8;
  }
  
  public String getClosedCounterGoal()
  {
    return String.valueOf(this.aXf);
  }
  
  public float getClosedCounterGoalValue()
  {
    return this.aXf;
  }
  
  public String getAvgTimeToOpenGoal()
  {
    return String.valueOf(this.aW4);
  }
  
  public float getAvgTimeToOpenGoalValue()
  {
    return this.aW4;
  }
  
  public String getAvgTimeToHandleGoal()
  {
    return String.valueOf(this.aXb);
  }
  
  public float getAvgTimeToHandleGoalValue()
  {
    return this.aXb;
  }
  
  public String getPctInternalTransferGoal()
  {
    return NumberFormat.getPercentInstance().format(this.aXi);
  }
  
  public float getPctInternalTransferGoalValue()
  {
    return this.aXi;
  }
  
  public String getPctSingleEventResolutionsGoal()
  {
    return NumberFormat.getPercentInstance().format(this.aW6);
  }
  
  public float getPctSingleEventResolutionsGoalValue()
  {
    return this.aW6;
  }
  
  public String getPctAppDeniedGoal()
  {
    return String.valueOf(this.aXg);
  }
  
  public float getPctAppDeniedGoalValue()
  {
    return this.aXg;
  }
  
  public String getCounterGoalAttainment()
  {
    float f1 = 0.0F;
    try
    {
      f1 = Float.parseFloat(getWeek6ItemCount());
    }
    catch (Exception localException)
    {
      return NumberFormat.getPercentInstance().format(0L);
    }
    if (this.aW8 == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = f1 / this.aW8;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public String getClosedCounterGoalAttainment()
  {
    float f1 = 0.0F;
    try
    {
      f1 = Float.parseFloat(getWeek6ItemClosedCount());
    }
    catch (Exception localException)
    {
      return NumberFormat.getPercentInstance().format(0L);
    }
    if (this.aXf == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = f1 / this.aXf;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public String getAvgTimeToOpenGoalAttainment()
  {
    float f1 = 0.0F;
    try
    {
      f1 = Float.parseFloat(getWeek6AvgTimeToOpen());
    }
    catch (Exception localException)
    {
      return NumberFormat.getPercentInstance().format(0L);
    }
    if (f1 == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = this.aW4 / f1;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public String getAvgTimeToHandleGoalAttainment()
  {
    float f1 = 0.0F;
    try
    {
      f1 = Float.parseFloat(getWeek6AvgTimeToHandle());
    }
    catch (Exception localException)
    {
      return NumberFormat.getPercentInstance().format(0L);
    }
    if (f1 == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = this.aXb / f1;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public String getPctInternalTransferGoalAttainment()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    int j = this.aW5.a + this.aXe.a + this.aW3.a + this.aXc.a + this.aXd.a + this.aXk.a + this.aW9.a;
    float f1 = 0.0F;
    if (i > 0) {
      f1 = j / i;
    }
    if (this.aXi == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = f1 / this.aXi;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public String getPctSingleEventResolutionsGoalAttainment()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    int j = this.aW5.jdField_new + this.aXe.jdField_new + this.aW3.jdField_new + this.aXc.jdField_new + this.aXd.jdField_new + this.aXk.jdField_new + this.aW9.jdField_new;
    float f1 = 0.0F;
    if (i > 0) {
      f1 = j / i;
    }
    if (this.aW6 == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = f1 / this.aW6;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public String getPctAppDeniedGoalAttainment()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    int j = this.aW5.jdField_try + this.aXe.jdField_try + this.aW3.jdField_try + this.aXc.jdField_try + this.aXd.jdField_try + this.aXk.jdField_try + this.aW9.jdField_try;
    float f1 = 0.0F;
    if (i > 0) {
      f1 = j / i;
    }
    if (this.aXg == 0.0F) {
      return NumberFormat.getPercentInstance().format(0L);
    }
    float f2 = f1 / this.aXg;
    return NumberFormat.getPercentInstance().format(f2);
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setOwner(String paramString)
  {
    this.owner = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getOwner()
  {
    return this.owner;
  }
  
  public String getWeek1ItemCount()
  {
    return String.valueOf(this.aXh.jdField_do);
  }
  
  public String getWeek2ItemCount()
  {
    return String.valueOf(this.aW7.jdField_do);
  }
  
  public String getWeek3ItemCount()
  {
    return String.valueOf(this.aXj.jdField_do);
  }
  
  public String getWeek4ItemCount()
  {
    return String.valueOf(this.aXa.jdField_do);
  }
  
  public String getWeek5ItemCount()
  {
    return String.valueOf(this.aW2.jdField_do);
  }
  
  public String getWeek6ItemCount()
  {
    int i = this.aW5.jdField_do + this.aXe.jdField_do + this.aW3.jdField_do + this.aXc.jdField_do + this.aXd.jdField_do + this.aXk.jdField_do + this.aW9.jdField_do;
    return String.valueOf(i);
  }
  
  public String getSunItemCount()
  {
    return String.valueOf(this.aW5.jdField_do);
  }
  
  public String getMonItemCount()
  {
    return String.valueOf(this.aXe.jdField_do);
  }
  
  public String getTueItemCount()
  {
    return String.valueOf(this.aW3.jdField_do);
  }
  
  public String getWedItemCount()
  {
    return String.valueOf(this.aXc.jdField_do);
  }
  
  public String getThuItemCount()
  {
    return String.valueOf(this.aXd.jdField_do);
  }
  
  public String getFriItemCount()
  {
    return String.valueOf(this.aXk.jdField_do);
  }
  
  public String getSatItemCount()
  {
    return String.valueOf(this.aW9.jdField_do);
  }
  
  public String getWeek1ItemClosedCount()
  {
    return String.valueOf(this.aXh.jdField_int);
  }
  
  public String getWeek2ItemClosedCount()
  {
    return String.valueOf(this.aW7.jdField_int);
  }
  
  public String getWeek3ItemClosedCount()
  {
    return String.valueOf(this.aXj.jdField_int);
  }
  
  public String getWeek4ItemClosedCount()
  {
    return String.valueOf(this.aXa.jdField_int);
  }
  
  public String getWeek5ItemClosedCount()
  {
    return String.valueOf(this.aW2.jdField_int);
  }
  
  public String getWeek6ItemClosedCount()
  {
    int i = this.aW5.jdField_int + this.aXe.jdField_int + this.aW3.jdField_int + this.aXc.jdField_int + this.aXd.jdField_int + this.aXk.jdField_int + this.aW9.jdField_int;
    return String.valueOf(i);
  }
  
  public String getSunItemClosedCount()
  {
    return String.valueOf(this.aW5.jdField_int);
  }
  
  public String getMonItemClosedCount()
  {
    return String.valueOf(this.aXe.jdField_int);
  }
  
  public String getTueItemClosedCount()
  {
    return String.valueOf(this.aW3.jdField_int);
  }
  
  public String getWedItemClosedCount()
  {
    return String.valueOf(this.aXc.jdField_int);
  }
  
  public String getThuItemClosedCount()
  {
    return String.valueOf(this.aXd.jdField_int);
  }
  
  public String getFriItemClosedCount()
  {
    return String.valueOf(this.aXk.jdField_int);
  }
  
  public String getSatItemClosedCount()
  {
    return String.valueOf(this.aW9.jdField_int);
  }
  
  public String getWeek1AvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXh.jdField_do > 0) {
      f = this.aXh.jdField_if / this.aXh.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek2AvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aW7.jdField_do > 0) {
      f = this.aW7.jdField_if / this.aW7.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek3AvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXj.jdField_do > 0) {
      f = this.aXj.jdField_if / this.aXj.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek4AvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXa.jdField_do > 0) {
      f = this.aXa.jdField_if / this.aXa.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek5AvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aW2.jdField_do > 0) {
      f = this.aW2.jdField_if / this.aW2.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek6AvgTimeToOpen()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    float f1 = this.aW5.jdField_if + this.aXe.jdField_if + this.aW3.jdField_if + this.aXc.jdField_if + this.aXd.jdField_if + this.aXk.jdField_if + this.aW9.jdField_if;
    float f2 = 0.0F;
    if (i > 0) {
      f2 = f1 / i;
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getSunAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aW5.jdField_do > 0) {
      f = this.aW5.jdField_if / this.aW5.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getMonAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXe.jdField_do > 0) {
      f = this.aXe.jdField_if / this.aXe.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getTueAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aW3.jdField_do > 0) {
      f = this.aW3.jdField_if / this.aW3.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWedAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXc.jdField_do > 0) {
      f = this.aXc.jdField_if / this.aXc.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getThuAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXd.jdField_do > 0) {
      f = this.aXd.jdField_if / this.aXd.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getFriAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aXk.jdField_do > 0) {
      f = this.aXk.jdField_if / this.aXk.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getSatAvgTimeToOpen()
  {
    float f = 0.0F;
    if (this.aW9.jdField_do > 0) {
      f = this.aW9.jdField_if / this.aW9.jdField_do;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek1AvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXh.jdField_int > 0) {
      f = this.aXh.jdField_for / this.aXh.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek2AvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aW7.jdField_int > 0) {
      f = this.aW7.jdField_for / this.aW7.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek3AvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXj.jdField_int > 0) {
      f = this.aXj.jdField_for / this.aXj.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek4AvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXa.jdField_int > 0) {
      f = this.aXa.jdField_for / this.aXa.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek5AvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aW2.jdField_int > 0) {
      f = this.aW2.jdField_for / this.aW2.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek6AvgTimeToHandle()
  {
    int i = Integer.parseInt(getWeek6ItemClosedCount());
    float f1 = this.aW5.jdField_for + this.aXe.jdField_for + this.aW3.jdField_for + this.aXc.jdField_for + this.aXd.jdField_for + this.aXk.jdField_for + this.aW9.jdField_for;
    float f2 = 0.0F;
    if (i > 0) {
      f2 = f1 / i;
    }
    return NumberFormat.getNumberInstance().format(f2);
  }
  
  public String getSunAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aW5.jdField_int > 0) {
      f = this.aW5.jdField_for / this.aW5.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getMonAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXe.jdField_int > 0) {
      f = this.aXe.jdField_for / this.aXe.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getTueAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aW3.jdField_int > 0) {
      f = this.aW3.jdField_for / this.aW3.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWedAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXc.jdField_int > 0) {
      f = this.aXc.jdField_for / this.aXc.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getThuAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXd.jdField_int > 0) {
      f = this.aXd.jdField_for / this.aXd.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getFriAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aXk.jdField_int > 0) {
      f = this.aXk.jdField_for / this.aXk.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getSatAvgTimeToHandle()
  {
    float f = 0.0F;
    if (this.aW9.jdField_int > 0) {
      f = this.aW9.jdField_for / this.aW9.jdField_int;
    }
    return NumberFormat.getNumberInstance().format(f);
  }
  
  public String getWeek1PctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXh.jdField_do > 0) {
      f = this.aXh.a / this.aXh.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek2PctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aW7.jdField_do > 0) {
      f = this.aW7.a / this.aW7.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek3PctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXj.jdField_do > 0) {
      f = this.aXj.a / this.aXj.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek4PctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXa.jdField_do > 0) {
      f = this.aXa.a / this.aXa.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek5PctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aW2.jdField_do > 0) {
      f = this.aW2.a / this.aW2.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek6PctInternalTransfer()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    int j = this.aW5.a + this.aXe.a + this.aW3.a + this.aXc.a + this.aXd.a + this.aXk.a + this.aW9.a;
    float f = 0.0F;
    if (i > 0) {
      f = j / i;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getSunPctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aW5.jdField_do > 0) {
      f = this.aW5.a / this.aW5.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getMonPctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXe.jdField_do > 0) {
      f = this.aXe.a / this.aXe.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getTuePctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aW3.jdField_do > 0) {
      f = this.aW3.a / this.aW3.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWedPctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXc.jdField_do > 0) {
      f = this.aXc.a / this.aXc.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getThuPctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXd.jdField_do > 0) {
      f = this.aXd.a / this.aXd.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getFriPctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aXk.jdField_do > 0) {
      f = this.aXk.a / this.aXk.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getSatPctInternalTransfer()
  {
    float f = 0.0F;
    if (this.aW9.jdField_do > 0) {
      f = this.aW9.a / this.aW9.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek1PctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXh.jdField_do > 0) {
      f = this.aXh.jdField_new / this.aXh.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek2PctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aW7.jdField_do > 0) {
      f = this.aW7.jdField_new / this.aW7.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek3PctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXj.jdField_do > 0) {
      f = this.aXj.jdField_new / this.aXj.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek4PctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXa.jdField_do > 0) {
      f = this.aXa.jdField_new / this.aXa.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek5PctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aW2.jdField_do > 0) {
      f = this.aW2.jdField_new / this.aW2.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek6PctSingleEventResolution()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    int j = this.aW5.jdField_new + this.aXe.jdField_new + this.aW3.jdField_new + this.aXc.jdField_new + this.aXd.jdField_new + this.aXk.jdField_new + this.aW9.jdField_new;
    float f = 0.0F;
    if (i > 0) {
      f = j / i;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getSunPctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aW5.jdField_do > 0) {
      f = this.aW5.jdField_new / this.aW5.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getMonPctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXe.jdField_do > 0) {
      f = this.aXe.jdField_new / this.aXe.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getTuePctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aW3.jdField_do > 0) {
      f = this.aW3.jdField_new / this.aW3.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWedPctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXc.jdField_do > 0) {
      f = this.aXc.jdField_new / this.aXc.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getThuPctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXd.jdField_do > 0) {
      f = this.aXd.jdField_new / this.aXd.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getFriPctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aXk.jdField_do > 0) {
      f = this.aXk.jdField_new / this.aXk.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getSatPctSingleEventResolution()
  {
    float f = 0.0F;
    if (this.aW9.jdField_do > 0) {
      f = this.aW9.jdField_new / this.aW9.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek1PctAppDenied()
  {
    float f = 0.0F;
    if (this.aXh.jdField_do > 0) {
      f = this.aXh.jdField_try / this.aXh.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek2PctAppDenied()
  {
    float f = 0.0F;
    if (this.aW7.jdField_do > 0) {
      f = this.aW7.jdField_try / this.aW7.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek3PctAppDenied()
  {
    float f = 0.0F;
    if (this.aXj.jdField_do > 0) {
      f = this.aXj.jdField_try / this.aXj.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek4PctAppDenied()
  {
    float f = 0.0F;
    if (this.aXa.jdField_do > 0) {
      f = this.aXa.jdField_try / this.aXa.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek5PctAppDenied()
  {
    float f = 0.0F;
    if (this.aW2.jdField_do > 0) {
      f = this.aW2.jdField_try / this.aW2.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWeek6PctAppDenied()
  {
    int i = Integer.parseInt(getWeek6ItemCount());
    int j = this.aW5.jdField_try + this.aXe.jdField_try + this.aW3.jdField_try + this.aXc.jdField_try + this.aXd.jdField_try + this.aXk.jdField_try + this.aW9.jdField_try;
    float f = 0.0F;
    if (i > 0) {
      f = j / i;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getSunPctAppDenied()
  {
    float f = 0.0F;
    if (this.aW5.jdField_do > 0) {
      f = this.aW5.jdField_try / this.aW5.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getMonPctAppDenied()
  {
    float f = 0.0F;
    if (this.aXe.jdField_do > 0) {
      f = this.aXe.jdField_try / this.aXe.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getTuePctAppDenied()
  {
    float f = 0.0F;
    if (this.aW3.jdField_do > 0) {
      f = this.aW3.jdField_try / this.aW3.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getWedPctAppDenied()
  {
    float f = 0.0F;
    if (this.aXc.jdField_do > 0) {
      f = this.aXc.jdField_try / this.aXc.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getThuPctAppDenied()
  {
    float f = 0.0F;
    if (this.aXd.jdField_do > 0) {
      f = this.aXd.jdField_try / this.aXd.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getFriPctAppDenied()
  {
    float f = 0.0F;
    if (this.aXk.jdField_do > 0) {
      f = this.aXk.jdField_try / this.aXk.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public String getSatPctAppDenied()
  {
    float f = 0.0F;
    if (this.aW9.jdField_do > 0) {
      f = this.aW9.jdField_try / this.aW9.jdField_do;
    }
    return String.valueOf(NumberFormat.getPercentInstance().format(f));
  }
  
  public int getWeek1Counter()
  {
    return this.aXh.jdField_do;
  }
  
  public int getWeek2Counter()
  {
    return this.aW7.jdField_do;
  }
  
  public int getWeek3Counter()
  {
    return this.aXj.jdField_do;
  }
  
  public int getWeek4Counter()
  {
    return this.aXa.jdField_do;
  }
  
  public int getWeek5Counter()
  {
    return this.aW2.jdField_do;
  }
  
  public int getSunCounter()
  {
    return this.aW5.jdField_do;
  }
  
  public int getMonCounter()
  {
    return this.aXe.jdField_do;
  }
  
  public int getTueCounter()
  {
    return this.aW3.jdField_do;
  }
  
  public int getWedCounter()
  {
    return this.aXc.jdField_do;
  }
  
  public int getThuCounter()
  {
    return this.aXd.jdField_do;
  }
  
  public int getFriCounter()
  {
    return this.aXk.jdField_do;
  }
  
  public int getSatCounter()
  {
    return this.aW9.jdField_do;
  }
  
  public int getWeek6Counter()
  {
    return this.aW5.jdField_do + this.aXe.jdField_do + this.aW3.jdField_do + this.aXc.jdField_do + this.aXd.jdField_do + this.aXk.jdField_do + this.aW9.jdField_do;
  }
  
  public int getWeek1ClosedCounter()
  {
    return this.aXh.jdField_int;
  }
  
  public int getWeek2ClosedCounter()
  {
    return this.aW7.jdField_int;
  }
  
  public int getWeek3ClosedCounter()
  {
    return this.aXj.jdField_int;
  }
  
  public int getWeek4ClosedCounter()
  {
    return this.aXa.jdField_int;
  }
  
  public int getWeek5ClosedCounter()
  {
    return this.aW2.jdField_int;
  }
  
  public int getSunClosedCounter()
  {
    return this.aW5.jdField_int;
  }
  
  public int getMonClosedCounter()
  {
    return this.aXe.jdField_int;
  }
  
  public int getTueClosedCounter()
  {
    return this.aW3.jdField_int;
  }
  
  public int getWedClosedCounter()
  {
    return this.aXc.jdField_int;
  }
  
  public int getThuClosedCounter()
  {
    return this.aXd.jdField_int;
  }
  
  public int getFriClosedCounter()
  {
    return this.aXk.jdField_int;
  }
  
  public int getSatClosedCounter()
  {
    return this.aW9.jdField_int;
  }
  
  public int getWeek6ClosedCounter()
  {
    return this.aW5.jdField_int + this.aXe.jdField_int + this.aW3.jdField_int + this.aXc.jdField_int + this.aXd.jdField_int + this.aXk.jdField_int + this.aW9.jdField_int;
  }
  
  public float getWeek1TimeToOpen()
  {
    return this.aXh.jdField_if;
  }
  
  public float getWeek2TimeToOpen()
  {
    return this.aW7.jdField_if;
  }
  
  public float getWeek3TimeToOpen()
  {
    return this.aXj.jdField_if;
  }
  
  public float getWeek4TimeToOpen()
  {
    return this.aXa.jdField_if;
  }
  
  public float getWeek5TimeToOpen()
  {
    return this.aW2.jdField_if;
  }
  
  public float getSunTimeToOpen()
  {
    return this.aW5.jdField_if;
  }
  
  public float getMonTimeToOpen()
  {
    return this.aXe.jdField_if;
  }
  
  public float getTueTimeToOpen()
  {
    return this.aW3.jdField_if;
  }
  
  public float getWedTimeToOpen()
  {
    return this.aXc.jdField_if;
  }
  
  public float getThuTimeToOpen()
  {
    return this.aXd.jdField_if;
  }
  
  public float getFriTimeToOpen()
  {
    return this.aXk.jdField_if;
  }
  
  public float getSatTimeToOpen()
  {
    return this.aW9.jdField_if;
  }
  
  public float getWeek6TimeToOpen()
  {
    return this.aW5.jdField_if + this.aXe.jdField_if + this.aW3.jdField_if + this.aXc.jdField_if + this.aXd.jdField_if + this.aXk.jdField_if + this.aW9.jdField_if;
  }
  
  public float getWeek1TimeToHandle()
  {
    return this.aXh.jdField_for;
  }
  
  public float getWeek2TimeToHandle()
  {
    return this.aW7.jdField_for;
  }
  
  public float getWeek3TimeToHandle()
  {
    return this.aXj.jdField_for;
  }
  
  public float getWeek4TimeToHandle()
  {
    return this.aXa.jdField_for;
  }
  
  public float getWeek5TimeToHandle()
  {
    return this.aW2.jdField_for;
  }
  
  public float getSunTimeToHandle()
  {
    return this.aW5.jdField_for;
  }
  
  public float getMonTimeToHandle()
  {
    return this.aXe.jdField_for;
  }
  
  public float getTueTimeToHandle()
  {
    return this.aW3.jdField_for;
  }
  
  public float getWedTimeToHandle()
  {
    return this.aXc.jdField_for;
  }
  
  public float getThuTimeToHandle()
  {
    return this.aXd.jdField_for;
  }
  
  public float getFriTimeToHandle()
  {
    return this.aXk.jdField_for;
  }
  
  public float getSatTimeToHandle()
  {
    return this.aW9.jdField_for;
  }
  
  public float getWeek6TimeToHandle()
  {
    return this.aW5.jdField_for + this.aXe.jdField_for + this.aW3.jdField_for + this.aXc.jdField_for + this.aXd.jdField_for + this.aXk.jdField_for + this.aW9.jdField_for;
  }
  
  public int getWeek1InternalTransfers()
  {
    return this.aXh.a;
  }
  
  public int getWeek2InternalTransfers()
  {
    return this.aW7.a;
  }
  
  public int getWeek3InternalTransfers()
  {
    return this.aXj.a;
  }
  
  public int getWeek4InternalTransfers()
  {
    return this.aXa.a;
  }
  
  public int getWeek5InternalTransfers()
  {
    return this.aW2.a;
  }
  
  public int getSunInternalTransfers()
  {
    return this.aW5.a;
  }
  
  public int getMonInternalTransfers()
  {
    return this.aXe.a;
  }
  
  public int getTueInternalTransfers()
  {
    return this.aW3.a;
  }
  
  public int getWedInternalTransfers()
  {
    return this.aXc.a;
  }
  
  public int getThuInternalTransfers()
  {
    return this.aXd.a;
  }
  
  public int getFriInternalTransfers()
  {
    return this.aXk.a;
  }
  
  public int getSatInternalTransfers()
  {
    return this.aW9.a;
  }
  
  public int getWeek6InternalTransfers()
  {
    return this.aW5.a + this.aXe.a + this.aW3.a + this.aXc.a + this.aXd.a + this.aXk.a + this.aW9.a;
  }
  
  public int getWeek1SingleEventResolutions()
  {
    return this.aXh.jdField_new;
  }
  
  public int getWeek2SingleEventResolutions()
  {
    return this.aW7.jdField_new;
  }
  
  public int getWeek3SingleEventResolutions()
  {
    return this.aXj.jdField_new;
  }
  
  public int getWeek4SingleEventResolutions()
  {
    return this.aXa.jdField_new;
  }
  
  public int getWeek5SingleEventResolutions()
  {
    return this.aW2.jdField_new;
  }
  
  public int getSunSingleEventResolutions()
  {
    return this.aW5.jdField_new;
  }
  
  public int getMonSingleEventResolutions()
  {
    return this.aXe.jdField_new;
  }
  
  public int getTueSingleEventResolutions()
  {
    return this.aW3.jdField_new;
  }
  
  public int getWedSingleEventResolutions()
  {
    return this.aXc.jdField_new;
  }
  
  public int getThuSingleEventResolutions()
  {
    return this.aXd.jdField_new;
  }
  
  public int getFriSingleEventResolutions()
  {
    return this.aXk.jdField_new;
  }
  
  public int getSatSingleEventResolutions()
  {
    return this.aW9.jdField_new;
  }
  
  public int getWeek6SingleEventResolutions()
  {
    return this.aW5.jdField_new + this.aXe.jdField_new + this.aW3.jdField_new + this.aXc.jdField_new + this.aXd.jdField_new + this.aXk.jdField_new + this.aW9.jdField_new;
  }
  
  public int getWeek1AppDenied()
  {
    return this.aXh.jdField_try;
  }
  
  public int getWeek2AppDenied()
  {
    return this.aW7.jdField_try;
  }
  
  public int getWeek3AppDenied()
  {
    return this.aXj.jdField_try;
  }
  
  public int getWeek4AppDenied()
  {
    return this.aXa.jdField_try;
  }
  
  public int getWeek5AppDenied()
  {
    return this.aW2.jdField_try;
  }
  
  public int getSunAppDenied()
  {
    return this.aW5.jdField_try;
  }
  
  public int getMonAppDenied()
  {
    return this.aXe.jdField_try;
  }
  
  public int getTueAppDenied()
  {
    return this.aW3.jdField_try;
  }
  
  public int getWedAppDenied()
  {
    return this.aXc.jdField_try;
  }
  
  public int getThuAppDenied()
  {
    return this.aXd.jdField_try;
  }
  
  public int getFriAppDenied()
  {
    return this.aXk.jdField_try;
  }
  
  public int getSatAppDenied()
  {
    return this.aW9.jdField_try;
  }
  
  public int getWeek6AppDenied()
  {
    return this.aW5.jdField_try + this.aXe.jdField_try + this.aW3.jdField_try + this.aXc.jdField_try + this.aXd.jdField_try + this.aXk.jdField_try + this.aW9.jdField_try;
  }
  
  private class a
  {
    public int jdField_do = 0;
    public int jdField_int = 0;
    public float jdField_if = 0.0F;
    public float jdField_for = 0.0F;
    public int a = 0;
    public int jdField_new = 0;
    public int jdField_try = 0;
    
    protected a() {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.PerformanceRpt
 * JD-Core Version:    0.7.0.1
 */