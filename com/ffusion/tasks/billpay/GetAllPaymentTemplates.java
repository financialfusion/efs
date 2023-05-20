package com.ffusion.tasks.billpay;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetAllPaymentTemplates
  extends GetPagedData
  implements Task
{
  protected String serviceName = "com.ffusion.services.BillPay";
  
  public GetAllPaymentTemplates()
  {
    setDataSetName("PaymentTemplates");
  }
  
  protected FilteredList loadData(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BillPay localBillPay = (BillPay)localHttpSession.getAttribute(this.serviceName);
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    if (localBillPay != null) {
      paramHashMap.put("SERVICE", localBillPay);
    }
    paramHashMap.put("ACCOUNTS", localAccounts);
    paramHashMap.put("PAYEES", localPayees);
    paramHashMap.put("PAGESIZE", "250");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    FundsTransactions localFundsTransactions = null;
    try
    {
      localFundsTransactions = Billpay.getAllPaymentTemplates(localSecureUser, null, paramPagingContext, paramHashMap);
      if (localFundsTransactions != null) {
        for (int i = 0; i < localFundsTransactions.size(); i++)
        {
          FundsTransaction localFundsTransaction = (FundsTransaction)localFundsTransactions.get(i);
          if ((localFundsTransaction instanceof PaymentBatch)) {
            localFundsTransaction.setType(22);
          } else if ((localFundsTransaction instanceof Payment)) {
            localFundsTransaction.setType(21);
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return localFundsTransactions;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return loadData(paramHttpServletRequest, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return loadData(paramHttpServletRequest, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return loadData(paramHttpServletRequest, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return loadData(paramHttpServletRequest, paramPagingContext, paramHashMap);
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    Object localObject;
    if ((paramObject instanceof Payment))
    {
      localObject = (Payment)paramObject;
      if (paramReportSortCriterion.getName().equals("TemplateName")) {
        return ((Payment)localObject).getTemplateName();
      }
      if (paramReportSortCriterion.getName().equals("PaymentType")) {
        return String.valueOf(21);
      }
    }
    else if ((paramObject instanceof PaymentBatch))
    {
      localObject = (PaymentBatch)paramObject;
      if (paramReportSortCriterion.getName().equals("TemplateName")) {
        return ((PaymentBatch)localObject).getTemplateName();
      }
      if (paramReportSortCriterion.getName().equals("PaymentType")) {
        return String.valueOf(22);
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetAllPaymentTemplates
 * JD-Core Version:    0.7.0.1
 */