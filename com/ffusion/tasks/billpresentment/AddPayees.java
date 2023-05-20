package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.beans.billpresentment.Billers;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddPayees
  extends EBillAccounts
  implements Task
{
  private static final String Ir = "PAYEEID";
  private Payees Is;
  private Billers Iy;
  private BillPay Iq;
  private boolean Ip = false;
  private boolean Ix = false;
  private int Iw;
  private String It = null;
  private String Iu;
  private String Iv;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.Iq = ((BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay"));
    this.Is = ((Payees)localHttpSession.getAttribute("Payees"));
    if (this.Is == null)
    {
      this.Iw = 6515;
      str = this.Iu;
      return str;
    }
    this.Iy = ((Billers)localHttpSession.getAttribute("Billers"));
    if (this.Iy == null)
    {
      this.Iw = 6503;
      str = this.Iu;
      return str;
    }
    if (this.Ip)
    {
      if (initProcess(localHttpSession)) {
        str = this.It;
      } else {
        str = this.Iu;
      }
    }
    else
    {
      str = this.It;
      processAddPayee(paramHttpServletRequest, localHttpSession);
      if (this.Iw != 0) {
        str = this.Iu;
      }
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    EBillAccounts localEBillAccounts = (EBillAccounts)paramHttpSession.getAttribute("EBillAccounts");
    if (localEBillAccounts == null)
    {
      this.Iw = 6510;
      return false;
    }
    Iterator localIterator = localEBillAccounts.iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      add(localEBillAccount);
    }
    this.Ip = false;
    return this.Iw == 0;
  }
  
  protected void processAddPayee(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EBillAccount localEBillAccount = (EBillAccount)localIterator.next();
      if ((!validatePayeeID(paramHttpServletRequest, paramHttpSession, localEBillAccount)) && (this.Ix)) {
        addPayee(paramHttpServletRequest, paramHttpSession, localEBillAccount);
      }
    }
    paramHttpSession.setAttribute("EBillAccounts", this);
    this.Ix = false;
  }
  
  protected void addPayee(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession, EBillAccount paramEBillAccount)
  {
    if (paramEBillAccount != null)
    {
      Biller localBiller = this.Iy.getByBillerID(paramEBillAccount.getBillerID());
      if (localBiller == null)
      {
        this.Iw = 6704;
        return;
      }
      Payees localPayees = new Payees();
      Payee localPayee = localPayees.create();
      localPayee.setUserAccountNumber(paramEBillAccount.getAccountNumber());
      localPayee.setName(localBiller.getBillerName());
      localPayee.setNickName(paramEBillAccount.getNickName());
      localPayee.setStreet(localBiller.getStreet());
      localPayee.setStreet2(localBiller.getStreet2());
      localPayee.setCity(localBiller.getCity());
      localPayee.setState(localBiller.getState());
      localPayee.setZipCode(localBiller.getZipCode());
      localPayee.setCountry(localBiller.getCountry());
      localPayee.setPhone(localBiller.getPhone());
      localPayee.setPhone2(localBiller.getPhone2());
      localPayee.setEmail(localBiller.getEmail());
      localPayee.setStatus(1);
      localPayee.setTransactionID("0");
      HashMap localHashMap = null;
      if (this.Iq != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", this.Iq);
      }
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.Iw = 0;
      try
      {
        localPayees = Billpay.addPayees(localSecureUser, localPayees, localHashMap);
        localPayee.set((Payee)localPayees.get(0));
      }
      catch (CSILException localCSILException1)
      {
        this.Iw = MapError.mapError(localCSILException1);
      }
      if (this.Iw == 0)
      {
        paramEBillAccount.setPayeeID(localPayee.getID());
        localHashMap = new HashMap();
        this.Iw = 0;
        try
        {
          paramEBillAccount = BillPresentment.modifyAccount(localSecureUser, paramEBillAccount, false, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.Iw = MapError.mapError(localCSILException2);
        }
        if (this.Iw != 0) {
          return;
        }
        this.Is.add(localPayee);
      }
    }
    else
    {
      this.Iw = 6509;
    }
  }
  
  protected boolean validatePayeeID(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession, EBillAccount paramEBillAccount)
  {
    if (paramEBillAccount == null) {
      return false;
    }
    Iterator localIterator = this.Is.iterator();
    while (localIterator.hasNext())
    {
      Payee localPayee = (Payee)localIterator.next();
      if (paramEBillAccount.getPayeeID() == null) {
        return false;
      }
      if (localPayee.getID().compareTo(paramEBillAccount.getPayeeID().trim()) == 0) {
        return true;
      }
    }
    return false;
  }
  
  public final void setInitialize(String paramString)
  {
    this.Ip = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setProcess(String paramString)
  {
    this.Ix = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.It = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Iu = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Iv = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Iw);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.AddPayees
 * JD-Core Version:    0.7.0.1
 */