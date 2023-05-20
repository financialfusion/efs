package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import java.util.Iterator;
import java.util.Locale;

public class AffiliateBanks
  extends FilteredList
  implements IdCollection
{
  public AffiliateBanks() {}
  
  public AffiliateBanks(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public AffiliateBank add()
  {
    AffiliateBank localAffiliateBank = new AffiliateBank(this.locale);
    add(localAffiliateBank);
    return localAffiliateBank;
  }
  
  public AffiliateBank create()
  {
    AffiliateBank localAffiliateBank = new AffiliateBank(this.locale);
    return localAffiliateBank;
  }
  
  public void set(AffiliateBanks paramAffiliateBanks)
  {
    Iterator localIterator = paramAffiliateBanks.iterator();
    while (localIterator.hasNext())
    {
      AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
      if (localAffiliateBank != null) {
        add(localAffiliateBank);
      }
    }
  }
  
  public void removeByAffiliateBankID(int paramInt)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
      if (localAffiliateBank.getAffiliateBankID() == paramInt)
      {
        remove(localAffiliateBank);
        break;
      }
    }
  }
  
  public AffiliateBank getAffiliateBankByAffiliateBankID(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
      if (localAffiliateBank.getAffiliateBankID() == paramInt)
      {
        localObject = localAffiliateBank;
        break;
      }
    }
    return localObject;
  }
  
  public AffiliateBank getByID(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
      if (localAffiliateBank.getAffiliateBankID() == paramInt)
      {
        localObject = localAffiliateBank;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    AffiliateBank localAffiliateBank = null;
    try
    {
      int i = Integer.valueOf(paramString).intValue();
      localAffiliateBank = getByID(i);
    }
    catch (Exception localException) {}
    return localAffiliateBank;
  }
  
  public void removeByID(String paramString)
  {
    try
    {
      int i = Integer.valueOf(paramString).intValue();
      removeByAffiliateBankID(i);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.AffiliateBanks
 * JD-Core Version:    0.7.0.1
 */