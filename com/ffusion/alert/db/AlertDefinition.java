package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEScheduleInfo;
import com.ffusion.alert.interfaces.IAEAlertDefinition;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import com.ffusion.alert.shared.AEUtils;

public final class AlertDefinition
  implements IAEAlertDefinition, IAEErrConstants, IAEStatusConstants
{
  public static final int H = 1;
  public static final int J = 2;
  private int G;
  private int C;
  private int K;
  private int E;
  private String A;
  private String z;
  private String M;
  private byte[] B;
  private int L;
  private AEScheduleInfo F;
  private DeliveryInfo[] D;
  private int I;
  
  public final int getId()
  {
    return this.G;
  }
  
  final void jdMethod_case(int paramInt)
  {
    this.G = paramInt;
  }
  
  public final int getType()
  {
    return this.C;
  }
  
  public final void jdMethod_char(int paramInt)
  {
    this.C = paramInt;
  }
  
  public final int getPriority()
  {
    return this.K;
  }
  
  public final void jdMethod_byte(int paramInt)
  {
    this.K = paramInt;
  }
  
  public final int jdMethod_try()
  {
    return this.E;
  }
  
  public final void jdMethod_try(int paramInt)
  {
    this.E = paramInt;
  }
  
  public final String getApplicationName()
  {
    return this.A;
  }
  
  public final void jdMethod_for(String paramString)
  {
    this.A = paramString;
  }
  
  public String getUserId()
  {
    return this.z;
  }
  
  public void jdMethod_if(String paramString)
  {
    this.z = paramString;
  }
  
  public final String getMessage()
  {
    if ((this.M == null) && (this.B != null)) {
      this.M = AEUtils.a(this.B);
    }
    return this.M;
  }
  
  public final void jdMethod_do(String paramString)
  {
    this.B = null;
    this.M = paramString;
  }
  
  public final byte[] getMessageBytes()
  {
    if ((this.B == null) && (this.M != null)) {
      this.B = AEUtils.a(this.M);
    }
    return this.B;
  }
  
  public final void a(byte[] paramArrayOfByte)
  {
    this.M = null;
    this.B = paramArrayOfByte;
  }
  
  public final void jdMethod_new(int paramInt)
  {
    this.L = paramInt;
  }
  
  public final int jdMethod_new()
  {
    return this.L;
  }
  
  public final void jdMethod_if(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      this.L |= paramInt;
    } else {
      this.L &= (paramInt ^ 0xFFFFFFFF);
    }
  }
  
  public final boolean jdMethod_goto(int paramInt)
  {
    return (this.L & paramInt) != 0;
  }
  
  public final AEScheduleInfo getScheduleInfo()
  {
    return this.F;
  }
  
  public final void a(AEScheduleInfo paramAEScheduleInfo)
  {
    this.F = paramAEScheduleInfo;
    jdMethod_if(1, paramAEScheduleInfo == null ? false : paramAEScheduleInfo.respectsDST());
  }
  
  public final IAEDeliveryInfo[] getDeliveryInfo()
  {
    return this.D;
  }
  
  public final void a(DeliveryInfo[] paramArrayOfDeliveryInfo)
  {
    this.D = paramArrayOfDeliveryInfo;
  }
  
  public final int getStatus()
  {
    return this.I;
  }
  
  public final void jdMethod_else(int paramInt)
  {
    this.I = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.AlertDefinition
 * JD-Core Version:    0.7.0.1
 */