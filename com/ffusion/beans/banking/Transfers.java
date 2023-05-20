package com.ffusion.beans.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.enums.UserAssignedAmount;
import java.util.Iterator;
import java.util.Locale;

public class Transfers
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String TRANSFERS = "TRANSFERS";
  protected String dateformat;
  
  public Transfers() {}
  
  public Transfers(Locale paramLocale)
  {
    super(paramLocale);
    this.dateformat = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      localTransfer.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    Transfer localTransfer = new Transfer(this.locale);
    add(localTransfer);
    return localTransfer;
  }
  
  public Object createNoAdd()
  {
    return new Transfer(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    Transfer localTransfer = (Transfer)paramObject;
    localTransfer.setLocale(this.locale);
    return super.add(localTransfer);
  }
  
  public Transfer getByID(String paramString)
  {
    return (Transfer)getFirstByFilter("ID=" + paramString);
  }
  
  public Transfers getScheduledPaymentsToPayee(Account paramAccount)
  {
    Transfers localTransfers = new Transfers(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      if ((localTransfer.getStatus() == 2) && ((paramAccount == null) || (paramAccount == localTransfer.getToAccount()))) {
        localTransfers.add(localTransfer);
      }
    }
    return localTransfers;
  }
  
  public Transfer getByTransactionID(String paramString)
  {
    return (Transfer)getFirstByFilter("TransactionID=" + paramString);
  }
  
  public boolean getHasMultiCurrency()
  {
    boolean bool = false;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      UserAssignedAmount localUserAssignedAmount = localTransfer.getUserAssignedAmountFlag();
      if ((localUserAssignedAmount != null) && (localUserAssignedAmount != UserAssignedAmount.SINGLE)) {
        bool = true;
      }
    }
    return bool;
  }
  
  public boolean getHasSingleUserAssignedAmountCurrency()
  {
    String str = null;
    boolean bool = true;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      Currency localCurrency = localTransfer.getUserAssignedAmountValue();
      if (localCurrency != null) {
        if (str == null)
        {
          str = localCurrency.getCurrencyCode();
        }
        else if (!str.equals(localCurrency.getCurrencyCode()))
        {
          bool = false;
          break;
        }
      }
    }
    return bool;
  }
  
  public boolean getHasEstimatedAmount()
  {
    boolean bool = false;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      if ((localTransfer.getIsAmountEstimated()) || (localTransfer.getIsToAmountEstimated())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public Account getCommonFromAccount()
  {
    Account localAccount = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      if (localAccount == null) {
        localAccount = localTransfer.getFromAccount();
      } else if (!localAccount.getID().equals(localTransfer.getFromAccount().getID())) {
        return null;
      }
    }
    return localAccount;
  }
  
  public Account getCommonToAccount()
  {
    Account localAccount = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      if (localAccount == null) {
        localAccount = localTransfer.getToAccount();
      } else if (!localAccount.getID().equals(localTransfer.getToAccount().getID())) {
        return null;
      }
    }
    return localAccount;
  }
  
  public String getCommonDestination()
  {
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      if (str == null) {
        str = localTransfer.getTransferDestination();
      } else if (!str.equals(localTransfer.getTransferDestination())) {
        return null;
      }
    }
    return str;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSFERS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Transfer)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "TRANSFERS");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void setXML(String paramString, Accounts paramAccounts)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramAccounts), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void fromXML(String paramString, Accounts paramAccounts)
  {
    setXML(paramString, paramAccounts);
  }
  
  public void setXML(String paramString)
  {
    setXML(paramString, null);
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts)
  {
    paramXMLHandler.continueWith(new a(paramAccounts));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  class a
    extends XMLHandler
  {
    Accounts jdField_int;
    
    public a(Accounts paramAccounts)
    {
      this.jdField_int = paramAccounts;
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("TRANSFER"))
      {
        Transfer localTransfer = (Transfer)Transfers.this.create();
        localTransfer.continueXMLParsing(getHandler(), this.jdField_int);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.Transfers
 * JD-Core Version:    0.7.0.1
 */