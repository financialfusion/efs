package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.beans.billpresentment.Billers;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEBillAccounts
  extends EBillAccounts
  implements Task
{
  private static final String IC = "";
  private String IG;
  private String IA;
  private String IF;
  private int IB;
  private boolean IH;
  private boolean Iz;
  private boolean IE;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    clear();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    int i = getEBillAccounts(localHttpSession);
    if (i == 0) {
      str = this.IF;
    } else if (i == -1) {
      str = this.IG;
    } else if (i == -2) {
      str = this.IA;
    }
    return str;
  }
  
  protected int getEBillAccounts(HttpSession paramHttpSession)
  {
    int i = 0;
    if (getLocale() == null) {
      setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    }
    Biller localBiller = null;
    Consumer localConsumer = null;
    EBillAccount localEBillAccount = null;
    EBillAccounts localEBillAccounts = (EBillAccounts)clone();
    if (this.IH) {
      localBiller = (Biller)paramHttpSession.getAttribute("Biller");
    }
    if (this.Iz) {
      localConsumer = (Consumer)paramHttpSession.getAttribute("Consumer");
    }
    if (this.IE) {
      localEBillAccount = (EBillAccount)paramHttpSession.getAttribute("EBillAccount");
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.IB = 0;
    try
    {
      localEBillAccounts = BillPresentment.getAccounts(localSecureUser, localBiller, localConsumer, localEBillAccount, localHashMap);
      clear();
      addAll(localEBillAccounts);
    }
    catch (CSILException localCSILException1)
    {
      this.IB = MapError.mapError(localCSILException1);
    }
    if (this.IB == 0)
    {
      int j = 0;
      Billers localBillers = (Billers)paramHttpSession.getAttribute("Billers");
      if (localBillers == null)
      {
        localBillers = new Billers();
        localHashMap = new HashMap();
        try
        {
          localBillers = BillPresentment.getBillers(localSecureUser, null, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.IB = MapError.mapError(localCSILException2);
        }
      }
      if ((localBillers != null) && (j == 0)) {
        fillAccountNickNames(this, localBillers);
      } else {
        i = -2;
      }
      paramHttpSession.setAttribute("EBillAccounts", this);
    }
    else
    {
      i = -2;
    }
    return i;
  }
  
  public void fillAccountNickNames(EBillAccounts paramEBillAccounts, Billers paramBillers)
  {
    Iterator localIterator = paramEBillAccounts.iterator();
    while ((localIterator != null) && (localIterator.hasNext()))
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      if ((localEBillAccount != null) && ((localEBillAccount.getNickName() == null) || (localEBillAccount.getNickName().equals(""))))
      {
        Biller localBiller = paramBillers.getByBillerID(localEBillAccount.getBillerIDValue());
        localEBillAccount.setNickName(localBiller.getBillerName());
      }
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.IF = paramString;
  }
  
  public final String getSuccessURL()
  {
    return this.IF;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.IA = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.IG = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.IB);
  }
  
  public final void setFilterOnBiller(String paramString)
  {
    this.IH = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setFilterOnBiller(boolean paramBoolean)
  {
    this.IH = paramBoolean;
  }
  
  public final String getFilterOnBiller()
  {
    return String.valueOf(this.IH);
  }
  
  public final boolean getFilterOnBillerValue()
  {
    return this.IH;
  }
  
  public final void setFilterOnConsumer(String paramString)
  {
    this.Iz = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setFilterOnConsumer(boolean paramBoolean)
  {
    this.Iz = paramBoolean;
  }
  
  public final String getFilterOnConsumer()
  {
    return String.valueOf(this.Iz);
  }
  
  public final boolean getFilterOnConsumerValue()
  {
    return this.Iz;
  }
  
  public final void setFilterOnAccount(String paramString)
  {
    this.IE = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setFilterOnAccount(boolean paramBoolean)
  {
    this.IE = paramBoolean;
  }
  
  public final String getFilterOnAccount()
  {
    return String.valueOf(this.IE);
  }
  
  public final boolean getFilterOnAccountValue()
  {
    return this.IE;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetEBillAccounts
 * JD-Core Version:    0.7.0.1
 */