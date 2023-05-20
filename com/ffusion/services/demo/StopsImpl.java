package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.services.Stops3;
import com.ffusion.util.XMLHandler;
import java.util.Calendar;
import java.util.Iterator;

public class StopsImpl
  extends Service
  implements Stops3
{
  protected Accounts accountList = new Accounts();
  protected StopChecks stopChecks;
  protected String userName;
  protected String password;
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public int addStopPayment(StopCheck paramStopCheck)
  {
    if (this.stopChecks == null) {
      this.stopChecks = new StopChecks();
    }
    this.stopChecks.add(paramStopCheck);
    return 0;
  }
  
  public int deleteStopPayment(StopCheck paramStopCheck)
  {
    if (this.stopChecks != null)
    {
      this.stopChecks.remove(paramStopCheck);
      paramStopCheck.setStatus("13010");
    }
    return 0;
  }
  
  public int getStopPayments(StopChecks paramStopChecks)
  {
    if ((this.stopChecks == null) || (this.stopChecks.size() == 0))
    {
      this.stopChecks = new StopChecks();
      if ((paramStopChecks != null) && (paramStopChecks.getLocale() != null)) {
        this.stopChecks.setLocale(paramStopChecks.getLocale());
      }
      jdMethod_case();
    }
    paramStopChecks.clear();
    Iterator localIterator = this.stopChecks.iterator();
    while (localIterator.hasNext()) {
      paramStopChecks.add(localIterator.next());
    }
    if (paramStopChecks.size() > 0) {
      paramStopChecks.setSortedBy("PAYEENAME");
    }
    return 0;
  }
  
  public int getStopPayments(StopChecks paramStopChecks, Accounts paramAccounts)
  {
    int i = 0;
    this.accounts = paramAccounts;
    if ((this.stopChecks == null) || (this.stopChecks.size() == 0))
    {
      this.stopChecks = new StopChecks();
      if ((paramStopChecks != null) && (paramStopChecks.getLocale() != null)) {
        this.stopChecks.setLocale(paramStopChecks.getLocale());
      }
      jdMethod_case();
    }
    paramStopChecks.clear();
    Iterator localIterator = this.stopChecks.iterator();
    while (localIterator.hasNext())
    {
      StopCheck localStopCheck = (StopCheck)localIterator.next();
      if (paramAccounts.getByID(localStopCheck.getAccountID()) != null) {
        paramStopChecks.add(localStopCheck);
      }
    }
    if (paramStopChecks.size() > 0) {
      paramStopChecks.setSortedBy("PAYEENAME");
    }
    return i;
  }
  
  public int getStopPayments(StopChecks paramStopChecks, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    int i = 0;
    this.accounts = paramAccounts;
    if ((this.stopChecks == null) || (this.stopChecks.size() == 0))
    {
      this.stopChecks = new StopChecks();
      if ((paramStopChecks != null) && (paramStopChecks.getLocale() != null)) {
        this.stopChecks.setLocale(paramStopChecks.getLocale());
      }
      jdMethod_case();
    }
    paramStopChecks.clear();
    Iterator localIterator = this.stopChecks.iterator();
    while (localIterator.hasNext())
    {
      StopCheck localStopCheck = (StopCheck)localIterator.next();
      if ((paramAccounts.getByID(localStopCheck.getAccountID()) != null) && ((paramCalendar1 == null) || (localStopCheck.getCheckDateValue().compare(paramCalendar1, null) >= 0)) && ((paramCalendar2 == null) || (localStopCheck.getCheckDateValue().compare(paramCalendar2, null) <= 0))) {
        paramStopChecks.add(localStopCheck);
      }
    }
    if (paramStopChecks.size() > 0) {
      paramStopChecks.setSortedBy("PAYEENAME");
    }
    return i;
  }
  
  private void jdMethod_case()
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  protected class a
    extends Service.InternalXMLHandler
  {
    public a()
    {
      super();
    }
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("STOPCHECKS")) && (StopsImpl.this.stopChecks != null)) {
        StopsImpl.this.stopChecks.continueXMLParsing(getHandler(), StopsImpl.this.accountList);
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.StopsImpl
 * JD-Core Version:    0.7.0.1
 */