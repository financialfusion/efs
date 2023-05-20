package com.ffusion.beans.banking;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import java.util.Iterator;
import java.util.Locale;

public class TransferBatches
  extends FilteredList
  implements Localeable
{
  public static final String TRANSFER_BATCHES = "TRANSFER_BATCHES";
  protected String dateformat;
  
  public TransferBatches() {}
  
  public TransferBatches(Locale paramLocale)
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
      TransferBatch localTransferBatch = (TransferBatch)localIterator.next();
      localTransferBatch.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    TransferBatch localTransferBatch = new TransferBatch(this.locale);
    add(localTransferBatch);
    return localTransferBatch;
  }
  
  public Object createNoAdd()
  {
    return new TransferBatch(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    TransferBatch localTransferBatch = (TransferBatch)paramObject;
    localTransferBatch.setLocale(this.locale);
    return super.add(localTransferBatch);
  }
  
  public TransferBatch getByID(String paramString)
  {
    return (TransferBatch)getFirstByFilter("ID=" + paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransferBatches
 * JD-Core Version:    0.7.0.1
 */