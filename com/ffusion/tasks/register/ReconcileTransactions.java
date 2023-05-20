package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReconcileTransactions
  extends ExtendedBaseTask
  implements Task
{
  protected int matchRange = 5;
  
  public ReconcileTransactions()
  {
    this.collectionSessionName = "RegisterTransactions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    RegisterTransactions localRegisterTransactions1 = (RegisterTransactions)localHttpSession.getAttribute(this.collectionSessionName);
    if (localRegisterTransactions1 == null)
    {
      this.error = 20004;
      return this.taskErrorURL;
    }
    localRegisterTransactions1.setFilter("All");
    RegisterTransactions localRegisterTransactions2 = jdMethod_for(localRegisterTransactions1, 1);
    localRegisterTransactions2.addAll(jdMethod_for(localRegisterTransactions1, 2));
    localRegisterTransactions2.addAll(jdMethod_for(localRegisterTransactions1, 3));
    RegisterTransactions localRegisterTransactions3 = jdMethod_for(localRegisterTransactions1, 0);
    matchTransactions(localRegisterTransactions2, localRegisterTransactions3);
    return reconcileTransactions(localRegisterTransactions2, localRegisterTransactions3, localRegisterTransactions1, localHttpSession, localSecureUser);
  }
  
  protected void matchTransactions(RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2)
  {
    paramRegisterTransactions2.setSortedBy("DATE_ISSUED");
    Iterator localIterator1 = paramRegisterTransactions1.iterator();
    while (localIterator1.hasNext())
    {
      RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)localIterator1.next();
      Iterator localIterator2 = paramRegisterTransactions2.iterator();
      while (localIterator2.hasNext())
      {
        RegisterTransaction localRegisterTransaction2 = (RegisterTransaction)localIterator2.next();
        if (localRegisterTransaction2.getReconcileMatch() == null) {
          if (transactionsMatch(localRegisterTransaction1, localRegisterTransaction2)) {
            break;
          }
        }
      }
    }
  }
  
  protected boolean transactionsMatch(RegisterTransaction paramRegisterTransaction1, RegisterTransaction paramRegisterTransaction2)
  {
    if (paramRegisterTransaction1.getRegisterTypeValue() == paramRegisterTransaction2.getRegisterTypeValue()) {
      if ((paramRegisterTransaction2.getRegisterTypeValue() == 3) && (RegisterUtil.hasValue(paramRegisterTransaction2.getReferenceNumber())))
      {
        if (checkNumbersMatch(paramRegisterTransaction1, paramRegisterTransaction2))
        {
          paramRegisterTransaction1.setReconcileMatch(paramRegisterTransaction2.getRegisterId());
          paramRegisterTransaction2.setReconcileMatch(paramRegisterTransaction1.getRegisterId());
          if (!paramRegisterTransaction1.getAmountValue().equals(paramRegisterTransaction2.getAmountValue()))
          {
            paramRegisterTransaction1.setDiscrepancy(true);
            paramRegisterTransaction2.setDiscrepancy(true);
          }
          return true;
        }
      }
      else if (paramRegisterTransaction1.getAmountValue().equals(paramRegisterTransaction2.getAmountValue()))
      {
        DateTime localDateTime1 = paramRegisterTransaction2.getDateIssuedValue();
        DateTime localDateTime2 = (DateTime)paramRegisterTransaction1.getDateValue().clone();
        DateTime localDateTime3 = (DateTime)localDateTime2.clone();
        localDateTime2.add(6, 1);
        localDateTime3.add(6, this.matchRange * -1);
        if ((localDateTime1.after(localDateTime3)) && (localDateTime1.before(localDateTime2)))
        {
          paramRegisterTransaction1.setReconcileMatch(paramRegisterTransaction2.getRegisterId());
          paramRegisterTransaction2.setReconcileMatch(paramRegisterTransaction1.getRegisterId());
          return true;
        }
      }
    }
    return false;
  }
  
  protected boolean checkNumbersMatch(RegisterTransaction paramRegisterTransaction1, RegisterTransaction paramRegisterTransaction2)
  {
    try
    {
      String str1 = paramRegisterTransaction1.getReferenceNumber();
      String str2 = paramRegisterTransaction2.getReferenceNumber();
      if ((RegisterUtil.hasValue(str1)) && (RegisterUtil.hasValue(str2)))
      {
        long l1 = Long.parseLong(str1);
        long l2 = Long.parseLong(str2);
        if (l1 == l2) {
          return true;
        }
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    return false;
  }
  
  protected String reconcileTransactions(RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2, RegisterTransactions paramRegisterTransactions3, HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    RegisterTransactions localRegisterTransactions1 = new RegisterTransactions();
    RegisterTransactions localRegisterTransactions2 = new RegisterTransactions();
    StringList localStringList = (StringList)paramHttpSession.getAttribute("RegisterDiscrepancies");
    if (localStringList == null)
    {
      localStringList = new StringList();
      paramHttpSession.setAttribute("RegisterDiscrepancies", localStringList);
    }
    Iterator localIterator = paramRegisterTransactions1.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)localIterator.next();
      String str1 = localRegisterTransaction1.getReconcileMatch();
      if (str1 == null)
      {
        if (localRegisterTransaction1.getStatusValue() == 1) {
          localRegisterTransaction1.setStatus(2);
        }
        localRegisterTransactions1.add(localRegisterTransaction1);
      }
      else
      {
        RegisterTransaction localRegisterTransaction2 = paramRegisterTransactions2.getByRegisterId(str1);
        localRegisterTransactions1.add(localRegisterTransaction2);
        localRegisterTransactions2.add(localRegisterTransaction1);
        paramRegisterTransactions3.removeByRegisterId(localRegisterTransaction1.getRegisterId());
        localRegisterTransaction2.setID(localRegisterTransaction1.getID());
        localRegisterTransaction2.setType(localRegisterTransaction1.getTypeValue());
        localRegisterTransaction2.setDate(localRegisterTransaction1.getDateValue());
        localRegisterTransaction2.setStatus(3);
        if (RegisterUtil.hasValue(localRegisterTransaction1.getReferenceNumber())) {
          localRegisterTransaction2.setReferenceNumber(localRegisterTransaction1.getReferenceNumber());
        }
        if (!RegisterUtil.hasValue(localRegisterTransaction2.getMemo())) {
          localRegisterTransaction2.setMemo(localRegisterTransaction1.getMemo());
        }
        if (!localRegisterTransaction2.getAmountValue().equals(localRegisterTransaction1.getAmountValue()))
        {
          if (localRegisterTransaction2.getMultipleCategories()) {
            localRegisterTransaction2.setRegisterCategoryId(0);
          }
          String[] arrayOfString = new String[2];
          arrayOfString[0] = localRegisterTransaction2.getAmount();
          arrayOfString[1] = localRegisterTransaction2.getMemo();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.register.resources", "DiscrepancyText", arrayOfString);
          String str2 = (String)localLocalizableString.localize(this.locale);
          localRegisterTransaction2.setMemo(str2);
          localRegisterTransaction2.setAmount(localRegisterTransaction1.getAmountValue());
          localRegisterTransaction2.setDiscrepancy(true);
          localStringList.add(localRegisterTransaction2.getRegisterId());
        }
      }
    }
    try
    {
      Register.reconcileRegisterTransactions(paramSecureUser, localRegisterTransactions1, localRegisterTransactions2, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    return this.successURL;
  }
  
  private RegisterTransactions jdMethod_for(RegisterTransactions paramRegisterTransactions, int paramInt)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getStatusValue() == paramInt) {
        localRegisterTransactions.add(localRegisterTransaction);
      }
    }
    return localRegisterTransactions;
  }
  
  public void setMatchRange(String paramString)
  {
    try
    {
      this.matchRange = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.ReconcileTransactions
 * JD-Core Version:    0.7.0.1
 */