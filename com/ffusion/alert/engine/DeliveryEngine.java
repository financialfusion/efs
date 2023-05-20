package com.ffusion.alert.engine;

import com.ffusion.alert.db.AERepository;
import com.ffusion.alert.db.AlertInstance;
import com.ffusion.alert.db.ChannelInfo;
import com.ffusion.alert.db.DeliveryInfo;
import com.ffusion.alert.interfaces.AEException;
import com.ffusion.alert.interfaces.IAEAlertInstance;
import com.ffusion.alert.interfaces.IAEChannel;
import com.ffusion.alert.interfaces.IAEChannelInfo;
import com.ffusion.alert.interfaces.IAEStatusConstants;
import com.ffusion.alert.shared.AEIntMap;
import com.ffusion.alert.shared.AEResourceBundle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DeliveryEngine
  implements IDispatcher, IStatusNotifier, IAEErrConstants, IAEStatusConstants
{
  private boolean cv = false;
  private static final AEResourceBundle cy = AEInstance.a;
  private AERepository cz;
  private AuditEngine cx;
  private AlertEngine cw;
  private final AEIntMap cu = new AEIntMap();
  
  public DeliveryEngine(AERepository paramAERepository, AuditEngine paramAuditEngine, AlertEngine paramAlertEngine)
    throws AEException
  {
    this.cz = paramAERepository;
    this.cx = paramAuditEngine;
    this.cw = paramAlertEngine;
  }
  
  public final void aa()
  {
    Y();
  }
  
  public final void a(IAEAlertInstance paramIAEAlertInstance)
  {
    if (this.cv) {
      return;
    }
    AlertInstance localAlertInstance = (AlertInstance)paramIAEAlertInstance;
    this.cx.a(1, 0, localAlertInstance.getId(), localAlertInstance.getSequence(), 0, null, 0, localAlertInstance.getNextRaised(), localAlertInstance.aB(), localAlertInstance.getUserId(), localAlertInstance.getApplicationName());
    jdMethod_if(localAlertInstance);
  }
  
  public void a(IAEAlertInstance paramIAEAlertInstance, int paramInt1, int paramInt2)
  {
    AlertInstance localAlertInstance = (AlertInstance)paramIAEAlertInstance;
    DeliveryInfo localDeliveryInfo = (DeliveryInfo)paramIAEAlertInstance.getDeliveryInfo()[paramInt1];
    synchronized (paramIAEAlertInstance.getDeliveryInfo())
    {
      a(localAlertInstance, localDeliveryInfo, paramInt1, paramInt2);
      jdMethod_if(localAlertInstance);
    }
  }
  
  private void a(AlertInstance paramAlertInstance, DeliveryInfo paramDeliveryInfo, int paramInt1, int paramInt2)
  {
    paramAlertInstance.jdMethod_do(paramInt1, paramInt2);
    this.cx.a(2, paramInt2, paramAlertInstance.getId(), paramAlertInstance.getSequence(), paramDeliveryInfo.jdMethod_if(), paramDeliveryInfo, paramDeliveryInfo.getId(), paramAlertInstance.getNextRaised(), paramAlertInstance.aB(), paramAlertInstance.getUserId(), paramAlertInstance.getApplicationName());
  }
  
  private void jdMethod_if(AlertInstance paramAlertInstance)
  {
    int i = 0;
    DeliveryInfo[] arrayOfDeliveryInfo = (DeliveryInfo[])paramAlertInstance.getDeliveryInfo();
    int j = arrayOfDeliveryInfo.length;
    int k = arrayOfDeliveryInfo[0].getDeliveryOrder();
    for (int m = 0; m < arrayOfDeliveryInfo.length; m++)
    {
      int n = arrayOfDeliveryInfo[m].getDeliveryOrder();
      if (n > k)
      {
        if (i != 0) {
          break;
        }
        k = n;
      }
      int i1 = paramAlertInstance.k(m);
      switch (i1)
      {
      case 0: 
        i = 1;
        break;
      case 1: 
        if (a(paramAlertInstance, arrayOfDeliveryInfo, m)) {
          return;
        }
        m--;
        break;
      case 2: 
        return;
      }
    }
    this.cx.a(3, 0, paramAlertInstance.getId(), paramAlertInstance.getSequence(), 0, null, 0, paramAlertInstance.getNextRaised(), paramAlertInstance.aB(), paramAlertInstance.getUserId(), paramAlertInstance.getApplicationName());
  }
  
  private boolean a(AlertInstance paramAlertInstance, DeliveryInfo[] paramArrayOfDeliveryInfo, int paramInt)
  {
    int i = paramArrayOfDeliveryInfo[paramInt].getDeliveryOrder();
    boolean bool = true;
    synchronized (paramArrayOfDeliveryInfo)
    {
      for (int j = paramInt; (j < paramArrayOfDeliveryInfo.length) && (paramArrayOfDeliveryInfo[j].getDeliveryOrder() == i); j++) {
        if (paramAlertInstance.k(j) == 1) {
          if (paramArrayOfDeliveryInfo[j].isSuspended())
          {
            bool = false;
            a(paramAlertInstance, paramArrayOfDeliveryInfo[j], j, 1003);
          }
          else
          {
            long l = paramArrayOfDeliveryInfo[j].getMaxDelay();
            if ((l >= 0L) && (paramAlertInstance.getNextRaised() + l + 5000L < System.currentTimeMillis()))
            {
              bool = false;
              a(paramAlertInstance, paramArrayOfDeliveryInfo[j], j, 1001);
            }
            else
            {
              ArrayList localArrayList = (ArrayList)this.cu.jdMethod_if(paramArrayOfDeliveryInfo[j].jdMethod_if());
              if (localArrayList == null)
              {
                bool = false;
                a(paramAlertInstance, paramArrayOfDeliveryInfo[j], j, 1000);
              }
              else
              {
                ChannelReceiver localChannelReceiver = (ChannelReceiver)localArrayList.get(0);
                DeliveryAlert localDeliveryAlert = new DeliveryAlert(paramAlertInstance, j);
                paramAlertInstance.jdMethod_do(j, 2);
                localChannelReceiver.jdMethod_do(localDeliveryAlert);
              }
            }
          }
        }
      }
      return bool;
    }
  }
  
  public final void Y()
  {
    ArrayList localArrayList1 = this.cu.a();
    int i = localArrayList1.size();
    for (int j = 0; j < i; j++)
    {
      ArrayList localArrayList2 = (ArrayList)localArrayList1.get(j);
      jdMethod_if(localArrayList2);
    }
    this.cu.jdMethod_if();
  }
  
  public final void e(int paramInt)
    throws AEException
  {
    ArrayList localArrayList = (ArrayList)this.cu.a(paramInt);
    if (localArrayList == null) {
      a(1001, "ERR_CR_NOT_LOADED");
    } else {
      jdMethod_if(localArrayList);
    }
  }
  
  private void jdMethod_if(ArrayList paramArrayList)
  {
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++) {
      ((ChannelReceiver)paramArrayList.get(j)).au();
    }
  }
  
  public void jdMethod_if(ChannelInfo paramChannelInfo)
    throws AEException
  {
    int i = paramChannelInfo.a();
    if (i <= 0) {
      return;
    }
    int j = paramChannelInfo.getId();
    if (this.cu.jdMethod_do(j)) {
      a(1002, "ERR_CR_ALREADY_LOADED", paramChannelInfo.getChannelName());
    }
    ChannelReceiver localChannelReceiver = null;
    Object localObject = null;
    ArrayList localArrayList = new ArrayList();
    while (i > 0)
    {
      localChannelReceiver = a(paramChannelInfo);
      localArrayList.add(localChannelReceiver);
      localChannelReceiver.a(((ChannelReceiver)localArrayList.get(0)).ay());
      i--;
    }
    this.cu.jdMethod_if(j, localArrayList);
  }
  
  public int[] Z()
  {
    return this.cu.jdMethod_new();
  }
  
  public boolean d(int paramInt)
  {
    return this.cu.jdMethod_do(paramInt);
  }
  
  private ChannelReceiver a(ChannelInfo paramChannelInfo)
    throws AEException
  {
    String str = paramChannelInfo.getClassName();
    Class localClass = null;
    try
    {
      localClass = Class.forName(str);
    }
    catch (Throwable localThrowable1)
    {
      a(1003, "ERR_CLASS_LOAD", str, localThrowable1);
    }
    Object localObject = null;
    try
    {
      localObject = localClass.newInstance();
    }
    catch (Throwable localThrowable2)
    {
      a(1004, "ERR_CONSTRUCTOR_FAILED", str, localThrowable2.getLocalizedMessage(), localThrowable2);
    }
    IAEChannel localIAEChannel = null;
    try
    {
      localIAEChannel = (IAEChannel)localObject;
    }
    catch (ClassCastException localClassCastException)
    {
      a(1005, "ERR_INVALID_CR_CLASS", str);
    }
    try
    {
      localIAEChannel.init(paramChannelInfo.getInitInfo(), this.cw, jdMethod_if(paramChannelInfo));
    }
    catch (Throwable localThrowable3)
    {
      a(1008, "ERR_CHANNEL_INIT_FAILED", str, localThrowable3);
    }
    ChannelReceiver localChannelReceiver = new ChannelReceiver(this, paramChannelInfo.getId(), paramChannelInfo.getChannelName());
    localChannelReceiver.a(localIAEChannel);
    localChannelReceiver.h(paramChannelInfo.getNumWorkers());
    localChannelReceiver.i(paramChannelInfo.getNumWorkers());
    localChannelReceiver.az();
    return localChannelReceiver;
  }
  
  private PrintWriter jdMethod_if(IAEChannelInfo paramIAEChannelInfo)
    throws IOException
  {
    if (paramIAEChannelInfo.getChannelName().equals("UAE Admin Channel")) {
      return null;
    }
    return new PrintWriter(new BufferedWriter(new FileWriter(paramIAEChannelInfo.getChannelName() + "Channel.log", true)), true);
  }
  
  private static void a(int paramInt, String paramString)
    throws AEException
  {
    throw new AEException(paramInt, cy.a(paramString));
  }
  
  private static void a(int paramInt, String paramString1, String paramString2)
    throws AEException
  {
    throw new AEException(paramInt, cy.a(paramString1, paramString2));
  }
  
  private static void a(int paramInt, String paramString, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, cy.a(paramString), paramThrowable);
  }
  
  private static void a(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, cy.a(paramString1, paramString2), paramThrowable);
  }
  
  private static void a(int paramInt, String paramString1, String paramString2, String paramString3, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, cy.a(paramString1, paramString2, paramString3), paramThrowable);
  }
  
  private static void a(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, Throwable paramThrowable)
    throws AEException
  {
    throw new AEException(paramInt, cy.a(paramString1, paramString2, paramString3, paramString4), paramThrowable);
  }
  
  public boolean X()
  {
    return this.cv;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.engine.DeliveryEngine
 * JD-Core Version:    0.7.0.1
 */