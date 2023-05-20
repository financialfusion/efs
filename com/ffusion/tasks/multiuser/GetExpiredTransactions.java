package com.ffusion.tasks.multiuser;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetExpiredTransactions
  extends BaseTask
{
  protected boolean initialize = true;
  protected boolean process = false;
  protected String sourceCollectionSessionName = null;
  protected String destinationCollectionSessionName = null;
  protected FilteredList sourceCollection = null;
  protected FilteredList destinationCollection = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.initialize) {
      str = initialize(localHttpSession);
    }
    if ((this.error == 0) && (this.process))
    {
      str = validateInput();
      if ((this.error == 0) && (!this.sourceCollection.isEmpty()))
      {
        Object localObject = null;
        Payment localPayment1 = null;
        Payment localPayment2 = null;
        RecPayment localRecPayment1 = null;
        RecPayment localRecPayment2 = null;
        Transfer localTransfer1 = null;
        Transfer localTransfer2 = null;
        RecTransfer localRecTransfer1 = null;
        RecTransfer localRecTransfer2 = null;
        Iterator localIterator = this.sourceCollection.iterator();
        while ((this.error == 0) && (localIterator.hasNext()))
        {
          localObject = localIterator.next();
          if ((localObject instanceof RecPayment))
          {
            localRecPayment1 = (RecPayment)localObject;
            if ((isExpired(localRecPayment1.getNextPayDateValue())) && ((this.destinationCollection instanceof RecPayments)))
            {
              RecPayments localRecPayments = (RecPayments)this.destinationCollection;
              localRecPayment2 = (RecPayment)localRecPayments.create();
              localRecPayment2.setLocale(localRecPayment2.getLocale());
              localRecPayment2.set(localRecPayment1);
            }
          }
          else if ((localObject instanceof Payment))
          {
            localPayment1 = (Payment)localObject;
            if (isExpired(localPayment1.getPayDateValue()))
            {
              localPayment2 = (Payment)Payments.createNoAdd(localPayment1.getLocale());
              localPayment2.set(localPayment1);
              this.destinationCollection.add(localPayment2);
            }
          }
          else if ((localObject instanceof RecTransfer))
          {
            localRecTransfer1 = (RecTransfer)localObject;
            if (isExpired(localRecTransfer1.getNextDateValue()))
            {
              localRecTransfer2 = new RecTransfer(localRecTransfer1.getLocale());
              localRecTransfer2.set(localRecTransfer1);
              this.destinationCollection.add(localRecTransfer2);
            }
          }
          else if ((localObject instanceof Transfer))
          {
            localTransfer1 = (Transfer)localObject;
            if (isExpired(localTransfer1.getDateValue()))
            {
              localTransfer2 = new Transfer(localTransfer1.getLocale());
              localTransfer2.set(localTransfer1);
              this.destinationCollection.add(localTransfer2);
            }
          }
          else
          {
            this.error = 28056;
          }
        }
      }
      if (this.error == 0) {
        localHttpSession.setAttribute(this.destinationCollectionSessionName, this.destinationCollection);
      }
    }
    return str;
  }
  
  public void setInitialize(String paramString)
  {
    this.initialize = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.process = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSourceCollectionSessionName(String paramString)
  {
    this.sourceCollectionSessionName = paramString;
  }
  
  public void setDestinationCollectionSessionName(String paramString)
  {
    this.destinationCollectionSessionName = paramString;
  }
  
  protected String initialize(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    int i = 1;
    if ((i != 0) && (this.sourceCollectionSessionName == null))
    {
      i = 0;
      str = this.taskErrorURL;
      this.error = 28052;
    }
    if (i != 0)
    {
      Object localObject = paramHttpSession.getAttribute(this.sourceCollectionSessionName);
      if (localObject == null)
      {
        i = 0;
        str = this.taskErrorURL;
        this.error = 28053;
      }
      else if ((localObject instanceof RecPayments))
      {
        this.sourceCollection = ((RecPayments)localObject);
        this.destinationCollection = new RecPayments(this.sourceCollection.getLocale());
      }
      else if ((localObject instanceof Payments))
      {
        this.sourceCollection = ((Payments)localObject);
        this.destinationCollection = new Payments(this.sourceCollection.getLocale());
      }
      else if ((localObject instanceof RecTransfers))
      {
        this.sourceCollection = ((RecTransfers)localObject);
        this.destinationCollection = new RecTransfers(this.sourceCollection.getLocale());
      }
      else if ((localObject instanceof Transfers))
      {
        this.sourceCollection = ((Transfers)localObject);
        this.destinationCollection = new Transfers(this.sourceCollection.getLocale());
      }
      else
      {
        i = 0;
        str = this.taskErrorURL;
        this.error = 28055;
      }
    }
    if ((i != 0) && (this.destinationCollection == null))
    {
      i = 0;
      str = this.taskErrorURL;
      this.error = 28054;
    }
    return str;
  }
  
  protected String validateInput()
  {
    String str = this.successURL;
    int i = 1;
    if ((i != 0) && (this.sourceCollection == null))
    {
      i = 0;
      str = this.taskErrorURL;
      this.error = 28053;
    }
    if ((i != 0) && (this.destinationCollection == null))
    {
      i = 0;
      str = this.taskErrorURL;
      this.error = 28054;
    }
    return str;
  }
  
  protected boolean isExpired(DateTime paramDateTime)
  {
    boolean bool = false;
    DateTime localDateTime = new DateTime();
    localDateTime.setTimeInMillis(System.currentTimeMillis());
    localDateTime.clear(11);
    localDateTime.clear(12);
    localDateTime.clear(13);
    localDateTime.clear(14);
    bool = localDateTime.compare(paramDateTime) > 0;
    return bool;
  }
  
  protected boolean isExpired(Calendar paramCalendar)
  {
    boolean bool = false;
    DateTime localDateTime = new DateTime();
    localDateTime.setTimeInMillis(paramCalendar.getTimeInMillis());
    bool = isExpired(localDateTime);
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.GetExpiredTransactions
 * JD-Core Version:    0.7.0.1
 */