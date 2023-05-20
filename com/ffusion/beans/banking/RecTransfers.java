package com.ffusion.beans.banking;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class RecTransfers
  extends Transfers
{
  public static final String RECTRANSFERS = "RECTRANSFERS";
  
  public RecTransfers() {}
  
  public RecTransfers(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public Object create()
  {
    RecTransfer localRecTransfer = new RecTransfer(this.locale);
    add(localRecTransfer);
    return localRecTransfer;
  }
  
  public Object createNoAdd()
  {
    RecTransfer localRecTransfer = new RecTransfer(this.locale);
    return localRecTransfer;
  }
  
  public boolean add(Object paramObject)
  {
    RecTransfer localRecTransfer = (RecTransfer)paramObject;
    localRecTransfer.setLocale(this.locale);
    return super.add(localRecTransfer);
  }
  
  public RecTransfers getTransfersToAccount(Account paramAccount)
  {
    RecTransfers localRecTransfers = new RecTransfers(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RecTransfer localRecTransfer = (RecTransfer)localIterator.next();
      if ((localRecTransfer.getStatus() == 2) && (paramAccount == localRecTransfer.getToAccount())) {
        localRecTransfers.add(localRecTransfer);
      }
    }
    return localRecTransfers;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "RECTRANSFERS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((RecTransfer)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "RECTRANSFERS");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts)
  {
    paramXMLHandler.continueWith(new a(paramAccounts));
  }
  
  class a
    extends Transfers.a
  {
    public a(Accounts paramAccounts)
    {
      super(paramAccounts);
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("RECTRANSFER"))
      {
        RecTransfer localRecTransfer = (RecTransfer)RecTransfers.this.create();
        localRecTransfer.continueXMLParsing(getHandler(), this.jdField_int);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.RecTransfers
 * JD-Core Version:    0.7.0.1
 */