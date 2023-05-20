package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import com.ffusion.util.beans.XMLStrings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGlobalPayees
  extends BaseTask
  implements Task, XMLStrings
{
  protected boolean reload = false;
  protected String payeeName = "GlobalPayees";
  protected String serviceName = "Billpay";
  protected String payeeLevelType = "GLOBAL";
  protected String prefixPayeeName = null;
  protected String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  protected boolean findFirstLetter = false;
  protected String firstPayeeLetter = "A";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payees localPayees1 = (Payees)localHttpSession.getAttribute(this.payeeName);
    String str = this.successURL;
    if (this.reload)
    {
      localPayees1 = null;
      localHttpSession.removeAttribute(this.payeeName);
      this.reload = false;
    }
    localPayees1 = new Payees();
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute(this.serviceName);
    HashMap localHashMap = new HashMap();
    localHashMap.put("PrefixPayeeName", this.prefixPayeeName);
    if (localBillPay != null) {
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      Payees localPayees2 = (Payees)localHttpSession.getAttribute("Payees");
      int i;
      if (this.findFirstLetter)
      {
        this.findFirstLetter = false;
        if (this.alphabet != null) {
          for (i = 0; i < this.alphabet.length() - 1; i++)
          {
            this.firstPayeeLetter = this.alphabet.substring(i, i + 1);
            localHashMap.put("PrefixPayeeName", this.firstPayeeLetter);
            localPayees1 = Billpay.getGlobalPayees(localSecureUser, localHashMap);
            int j = jdMethod_for(localPayees1, localPayees2);
            if (j != localPayees1.size()) {
              break;
            }
          }
        }
      }
      else
      {
        localPayees1 = Billpay.getGlobalPayees(localSecureUser, localHashMap);
        i = jdMethod_for(localPayees1, localPayees2);
        if (i == localPayees1.size()) {
          localPayees1 = new Payees();
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localPayees1.setSortedBy("ID,PREFERRED_LANG");
      localHttpSession.setAttribute(this.payeeName, localPayees1);
    }
    return str;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public void setPayeeLevelType(String paramString)
  {
    this.payeeLevelType = paramString;
  }
  
  public void setPrefixPayeeName(String paramString)
  {
    this.prefixPayeeName = paramString;
  }
  
  public String getPrefixPayeeName()
  {
    return this.prefixPayeeName;
  }
  
  public void setAlphabet(String paramString)
  {
    this.alphabet = paramString;
  }
  
  public void setFindFirstPayeeLetter(String paramString)
  {
    this.findFirstLetter = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getFirstPayeeLetter()
  {
    return this.firstPayeeLetter;
  }
  
  private int jdMethod_for(Payees paramPayees1, Payees paramPayees2)
  {
    int i = 0;
    if ((paramPayees1 != null) && (paramPayees1.size() > 0) && (paramPayees1.get(0) != null))
    {
      Iterator localIterator = paramPayees1.iterator();
      while (localIterator.hasNext())
      {
        Payee localPayee1 = (Payee)localIterator.next();
        String str = "and,HOSTID==" + localPayee1.getHostID() + "," + "STREET" + "==" + localPayee1.getStreet() + "," + "CITY" + "==" + localPayee1.getCity() + "," + "STATE" + "==" + localPayee1.getState() + "," + "COUNTRY" + "==" + localPayee1.getCountry() + "," + "ZIPCODE" + "==" + localPayee1.getZipCode();
        Payee localPayee2 = (Payee)paramPayees2.getFirstByFilter(str);
        if (localPayee2 != null) {
          i++;
        }
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetGlobalPayees
 * JD-Core Version:    0.7.0.1
 */