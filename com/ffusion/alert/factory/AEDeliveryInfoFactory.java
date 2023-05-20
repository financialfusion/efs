package com.ffusion.alert.factory;

import com.ffusion.alert.db.DeliveryInfo;
import com.ffusion.alert.interfaces.IAEDeliveryInfo;
import java.util.Properties;

public class AEDeliveryInfoFactory
{
  public static IAEDeliveryInfo create(String paramString, int paramInt1, int paramInt2, Properties paramProperties)
  {
    DeliveryInfo localDeliveryInfo = new DeliveryInfo();
    localDeliveryInfo.setDeliveryChannelName(paramString);
    localDeliveryInfo.setDeliveryOrder(paramInt1);
    localDeliveryInfo.setMaxDelay(paramInt2);
    localDeliveryInfo.setDeliveryProperties(paramProperties);
    return localDeliveryInfo;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.factory.AEDeliveryInfoFactory
 * JD-Core Version:    0.7.0.1
 */