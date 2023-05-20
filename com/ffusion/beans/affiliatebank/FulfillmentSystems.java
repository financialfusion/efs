package com.ffusion.beans.affiliatebank;

import com.ffusion.util.FilteredList;

public class FulfillmentSystems
  extends FilteredList
{
  public FulfillmentSystem add()
  {
    FulfillmentSystem localFulfillmentSystem = new FulfillmentSystem();
    add(localFulfillmentSystem);
    return localFulfillmentSystem;
  }
  
  public FulfillmentSystem createNoAdd()
  {
    FulfillmentSystem localFulfillmentSystem = new FulfillmentSystem();
    return localFulfillmentSystem;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.FulfillmentSystems
 * JD-Core Version:    0.7.0.1
 */