package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.register.TransactionCategory;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReassignTransactionsCategory
  extends ExtendedBaseTask
  implements Task
{
  String EU;
  String ET;
  
  public ReassignTransactionsCategory()
  {
    this.collectionSessionName = "RegisterTransactions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    try
    {
      Integer.parseInt(this.EU);
      Integer.parseInt(this.ET);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 20107;
      return this.taskErrorURL;
    }
    try
    {
      Register.reassignTransactionsCategory(localSecureUser, this.EU, this.ET, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.collectionSessionName);
      if (localRegisterTransactions != null)
      {
        Iterator localIterator1 = localRegisterTransactions.iterator();
        while (localIterator1.hasNext())
        {
          RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator1.next();
          TransactionCategories localTransactionCategories = localRegisterTransaction.getTransactionCategories();
          Iterator localIterator2 = localTransactionCategories.iterator();
          while (localIterator2.hasNext())
          {
            TransactionCategory localTransactionCategory = (TransactionCategory)localIterator2.next();
            if (localTransactionCategory.getRegisterCategoryId().equals(this.EU)) {
              localTransactionCategory.setRegisterCategoryId(this.ET);
            }
          }
        }
        localHttpSession.setAttribute(this.collectionSessionName, localRegisterTransactions);
      }
    }
    else
    {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setOldCategoryId(String paramString)
  {
    this.EU = paramString;
  }
  
  public void setNewCategoryId(String paramString)
  {
    this.ET = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.ReassignTransactionsCategory
 * JD-Core Version:    0.7.0.1
 */