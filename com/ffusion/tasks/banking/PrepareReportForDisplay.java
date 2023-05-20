package com.ffusion.tasks.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountHistoryRpt;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AcctBalanceSheetRpt;
import com.ffusion.beans.accounts.AcctCashFlowFcstRpt;
import com.ffusion.beans.accounts.AcctCashFlowRpt;
import com.ffusion.beans.accounts.AcctGeneralLedgerRpt;
import com.ffusion.beans.accounts.AcctTransactionRpt;
import com.ffusion.beans.accounts.BalanceDetailRecords;
import com.ffusion.beans.accounts.BalanceDetailRpt;
import com.ffusion.beans.accounts.BalanceSummaryRecords;
import com.ffusion.beans.accounts.BalanceSummaryRpt;
import com.ffusion.beans.accounts.CustomSummaryRpt;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrepareReportForDisplay
  extends BaseTask
  implements Task
{
  private static String zy = "Prepare";
  private static String zB = "CleanUp";
  private String zC = null;
  private String zx = zy;
  private String zA = null;
  private UserLocale zz = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.zz = ((UserLocale)localHttpSession.getAttribute("UserLocale"));
    if (this.zx.equals(zy))
    {
      try
      {
        this.error = 0;
        Report localReport;
        Object localObject1;
        if (this.zC.equals("CashFlowReport"))
        {
          localReport = (Report)localHttpSession.getAttribute("CashReport");
          if (localReport.getReportResult() != null)
          {
            localObject1 = ((AcctCashFlowRpt)localReport.getReportResult()).getReportOutput();
            if (localObject1 != null) {
              localHttpSession.setAttribute("ReportOutput", localObject1);
            }
          }
        }
        else
        {
          Object localObject2;
          if ((this.zC.equals("BalanceSheetReport")) || (this.zC.equals("BalanceSheetSummary")))
          {
            localReport = (Report)localHttpSession.getAttribute("CashReport");
            if (localReport.getReportResult() != null)
            {
              localObject1 = ((AcctBalanceSheetRpt)localReport.getReportResult()).getRequestedAccounts();
              if (localObject1 != null) {
                localHttpSession.setAttribute("RequestedAccounts", localObject1);
              }
              localObject2 = ((AcctBalanceSheetRpt)localReport.getReportResult()).getAccountsWithPendingTrans();
              if (localObject2 != null) {
                localHttpSession.setAttribute("AccountsWithPendingTrans", localObject2);
              }
            }
          }
          else
          {
            Object localObject4;
            Object localObject3;
            Object localObject6;
            Object localObject7;
            Object localObject8;
            Object localObject10;
            Object localObject9;
            if (this.zC.equals("CashFlowForecastReport"))
            {
              localReport = (Report)localHttpSession.getAttribute("CashReport");
              localObject1 = (AcctCashFlowFcstRpt)localReport.getReportResult();
              localObject2 = null;
              if (localObject1 != null)
              {
                localObject2 = new ArrayList();
                try
                {
                  String str = this.zz.getDateFormat();
                  localObject4 = new SimpleDateFormat(str, this.zz.getLocale());
                  localObject5 = ((SimpleDateFormat)localObject4).parse(this.zA);
                  ((AcctCashFlowFcstRpt)localObject1).setForecastDate(new DateTime((Date)localObject5, ((AcctCashFlowFcstRpt)localObject1).getLocale()));
                }
                catch (ParseException localParseException)
                {
                  return super.getSuccessURL();
                }
                localObject3 = ((AcctCashFlowFcstRpt)localObject1).getAccounts();
                localObject4 = ((AcctCashFlowFcstRpt)localObject1).getAccountHistories();
                Object localObject5 = ((AcctCashFlowFcstRpt)localObject1).getForecastBalances();
                localObject6 = ((Accounts)localObject3).iterator();
                localObject7 = new Currency();
                localObject8 = new Currency();
                for (int i = 0; i < ((Accounts)localObject3).size(); i++)
                {
                  Account localAccount = (Account)((Accounts)localObject3).get(i);
                  StringBuffer localStringBuffer = new StringBuffer();
                  localStringBuffer.append("and,").append("ACCOUNT_NUMBER").append("==").append(localAccount.getNumber());
                  if (localAccount.getRoutingNum() != null) {
                    localStringBuffer.append(",").append("ROUTING_NUMBER").append("==").append(localAccount.getRoutingNum());
                  }
                  if (localAccount.getBankID() != null) {
                    localStringBuffer.append(",").append("BANK_ID").append("==").append(localAccount.getBankID());
                  }
                  ((AccountHistories)localObject4).setSortedBy("HISTORY_DATE");
                  localObject10 = ((AccountHistory)((AccountHistories)localObject4).getFirstByFilter(localStringBuffer.toString())).getCurrentLedger();
                  Currency localCurrency1 = (Currency)((ArrayList)localObject5).get(i);
                  Currency localCurrency2 = new Currency();
                  double d2 = localCurrency1.doubleValue() - ((Currency)localObject10).doubleValue();
                  Locale localLocale = ((Currency)localObject10).getLocale();
                  localCurrency2.setLocale(localLocale);
                  localCurrency2.setAmount(new BigDecimal(d2));
                  ((Currency)localObject7).addAmount((Currency)localObject10);
                  ((Currency)localObject8).addAmount(localCurrency1);
                  ((ArrayList)localObject2).add(localAccount);
                  ((ArrayList)localObject2).add(localObject10);
                  ((ArrayList)localObject2).add(localCurrency2);
                  ((ArrayList)localObject2).add(localCurrency1);
                }
                if (localObject3 != null)
                {
                  localHttpSession.setAttribute("TotalBeginBalance", localObject7);
                  localHttpSession.setAttribute("TotalForecastBalance", localObject8);
                  localObject9 = new Currency();
                  double d1 = ((Currency)localObject7).doubleValue() - ((Currency)localObject8).doubleValue();
                  localObject10 = ((Currency)localObject8).getLocale();
                  ((Currency)localObject9).setLocale((Locale)localObject10);
                  ((Currency)localObject9).setAmount(new BigDecimal(d1));
                  localHttpSession.setAttribute("TotalNetInflows", localObject9);
                }
              }
              localHttpSession.setAttribute("ReportOutput", localObject2);
            }
            else if (this.zC.equals("GeneralLedgerReport"))
            {
              localReport = (Report)localHttpSession.getAttribute("CashReport");
              if (localReport.getReportResult() != null)
              {
                localObject1 = ((AcctGeneralLedgerRpt)localReport.getReportResult()).getReportOutput();
                if (localObject1 != null) {
                  localHttpSession.setAttribute("ReportOutput", localObject1);
                }
              }
            }
            else if ((this.zC.equals("CreditReport")) || (this.zC.equals("DebitReport")) || (this.zC.equals("DepositDetail")) || (this.zC.equals("TransactionDetail")))
            {
              localReport = (Report)localHttpSession.getAttribute("GenericReport");
              if (localReport.getReportResult() != null)
              {
                localObject1 = ((AcctTransactionRpt)localReport.getReportResult()).getReportOutput();
                if (localObject1 != null) {
                  localHttpSession.setAttribute("ReportOutput", localObject1);
                }
                localObject2 = ((AcctTransactionRpt)localReport.getReportResult()).getAccounts();
                if (localObject2 != null) {
                  localHttpSession.setAttribute("ReportAccounts", localObject2);
                }
              }
            }
            else if (this.zC.equals("BalanceSummaryReport"))
            {
              localReport = (Report)localHttpSession.getAttribute("GenericReport");
              if (localReport.getReportResult() != null)
              {
                localObject1 = ((BalanceSummaryRpt)localReport.getReportResult()).getBalanceSummaryRecords();
                if ((localObject1 != null) && (((BalanceSummaryRecords)localObject1).size() != 0)) {
                  localHttpSession.setAttribute("BalanceSummaryRecords", localObject1);
                }
              }
            }
            else if (this.zC.equals("BalanceDetailOnlyReport"))
            {
              localReport = (Report)localHttpSession.getAttribute("GenericReport");
              if (localReport.getReportResult() != null)
              {
                localObject1 = ((BalanceDetailRpt)localReport.getReportResult()).getBalanceDetailRecords();
                if ((localObject1 != null) && (((BalanceDetailRecords)localObject1).size() != 0)) {
                  localHttpSession.setAttribute("BalanceDetailRecords", localObject1);
                }
              }
            }
            else if (this.zC.equals("AccountHistoryReport"))
            {
              localReport = (Report)localHttpSession.getAttribute("GenericReport");
              if (localReport.getReportResult() != null)
              {
                localObject1 = ((AccountHistoryRpt)localReport.getReportResult()).getAccounts();
                if (localObject1 != null) {
                  localHttpSession.setAttribute("ReportAccounts", localObject1);
                }
                localObject2 = ((AccountHistoryRpt)localReport.getReportResult()).getAccountHistories();
                if (localObject2 != null) {
                  localHttpSession.setAttribute("ReportAccountHistories", localObject2);
                }
              }
            }
            else if (this.zC.equals("CustomSummaryReport"))
            {
              localReport = (Report)localHttpSession.getAttribute("GenericReport");
              if (localReport.getReportResult() != null)
              {
                localObject1 = ((CustomSummaryRpt)localReport.getReportResult()).getExtendedAccountSummaries();
                localObject2 = localReport.getReportCriteria().getSortCriteria();
                if (localObject2 == null) {
                  localObject2 = new ReportSortCriteria();
                }
                if (((ReportSortCriteria)localObject2).size() == 0)
                {
                  ((ReportSortCriteria)localObject2).create(1, "ProcessingDate", true);
                  ((ReportSortCriteria)localObject2).create(2, "AccountNumber", true);
                }
                ((ReportSortCriteria)localObject2).setSortedBy("ORDINAL");
                localObject3 = ((ReportSortCriteria)localObject2).iterator();
                localObject4 = null;
                boolean bool = true;
                while (((Iterator)localObject3).hasNext())
                {
                  localObject6 = (ReportSortCriterion)((Iterator)localObject3).next();
                  localObject4 = ((ReportSortCriterion)localObject6).getName();
                  if (((String)localObject4).equals("AccountNumber")) {
                    bool = false;
                  } else if (((String)localObject4).equals("ProcessingDate")) {
                    bool = true;
                  }
                }
                localObject6 = new ArrayList();
                ArrayList localArrayList;
                int j;
                if (bool)
                {
                  localObject7 = null;
                  localObject8 = null;
                  localObject9 = new ArrayList();
                  localArrayList = new ArrayList();
                  for (j = 0; j < ((ExtendedAccountSummaries)localObject1).size(); j++)
                  {
                    localObject10 = (ExtendedAccountSummary)((ExtendedAccountSummaries)localObject1).get(j);
                    if (j == 0)
                    {
                      localObject7 = ((ExtendedAccountSummary)localObject10).getSummaryDate();
                      localObject8 = ((ExtendedAccountSummary)localObject10).getAccountID();
                    }
                    if (!((ExtendedAccountSummary)localObject10).getSummaryDate().equals(localObject7))
                    {
                      ((ArrayList)localObject9).add(localArrayList);
                      ((ArrayList)localObject6).add(localObject9);
                      localObject9 = new ArrayList();
                      localArrayList = new ArrayList();
                      localObject7 = ((ExtendedAccountSummary)localObject10).getSummaryDate();
                      localObject8 = ((ExtendedAccountSummary)localObject10).getAccountID();
                    }
                    if (((ExtendedAccountSummary)localObject10).getAccountID().equals(localObject8))
                    {
                      localArrayList.add(localObject10);
                    }
                    else
                    {
                      ((ArrayList)localObject9).add(localArrayList);
                      localArrayList = new ArrayList();
                      localArrayList.add(localObject10);
                      localObject8 = ((ExtendedAccountSummary)localObject10).getAccountID();
                    }
                  }
                  if (localArrayList.size() != 0) {
                    ((ArrayList)localObject9).add(localArrayList);
                  }
                  if (((ArrayList)localObject9).size() != 0) {
                    ((ArrayList)localObject6).add(localObject9);
                  }
                }
                else
                {
                  localObject7 = null;
                  localObject8 = null;
                  localObject9 = new ArrayList();
                  localArrayList = new ArrayList();
                  for (j = 0; j < ((ExtendedAccountSummaries)localObject1).size(); j++)
                  {
                    localObject10 = (ExtendedAccountSummary)((ExtendedAccountSummaries)localObject1).get(j);
                    if (j == 0)
                    {
                      localObject7 = ((ExtendedAccountSummary)localObject10).getSummaryDate();
                      localObject8 = ((ExtendedAccountSummary)localObject10).getAccountID();
                    }
                    if (!((ExtendedAccountSummary)localObject10).getAccountID().equals(localObject8))
                    {
                      ((ArrayList)localObject9).add(localArrayList);
                      ((ArrayList)localObject6).add(localObject9);
                      localObject9 = new ArrayList();
                      localArrayList = new ArrayList();
                      localObject8 = ((ExtendedAccountSummary)localObject10).getAccountID();
                      localObject7 = ((ExtendedAccountSummary)localObject10).getSummaryDate();
                    }
                    if (((ExtendedAccountSummary)localObject10).getSummaryDate().equals(localObject7))
                    {
                      localArrayList.add(localObject10);
                    }
                    else
                    {
                      ((ArrayList)localObject9).add(localArrayList);
                      localArrayList = new ArrayList();
                      localArrayList.add(localObject10);
                      localObject7 = ((ExtendedAccountSummary)localObject10).getSummaryDate();
                    }
                  }
                  if (localArrayList.size() != 0) {
                    ((ArrayList)localObject9).add(localArrayList);
                  }
                  if (((ArrayList)localObject9).size() != 0) {
                    ((ArrayList)localObject6).add(localObject9);
                  }
                }
                if (((ArrayList)localObject6).size() != 0) {
                  localHttpSession.setAttribute("ExtendedAccountSummaries", localObject6);
                }
                localHttpSession.setAttribute("SortedByDateFirst", new Boolean(bool).toString());
              }
            }
            else
            {
              this.error = 68;
              return super.getTaskErrorURL();
            }
          }
        }
      }
      catch (Exception localException)
      {
        return super.getTaskErrorURL();
      }
      return super.getSuccessURL();
    }
    if (this.zx.equals(zB)) {
      return jdMethod_for(paramHttpServlet, localHttpSession);
    }
    return super.getTaskErrorURL();
  }
  
  private String jdMethod_for(HttpServlet paramHttpServlet, HttpSession paramHttpSession)
  {
    try
    {
      this.error = 0;
      if (this.zC.equals("CashFlowReport"))
      {
        paramHttpSession.removeAttribute("ReportOutput");
      }
      else if ((this.zC.equals("BalanceSheetReport")) || (this.zC.equals("BalanceSheetSummary")))
      {
        paramHttpSession.removeAttribute("RequestedAccounts");
        paramHttpSession.removeAttribute("AccountsWithPendingTrans");
      }
      else if (this.zC.equals("CashFlowForecastReport"))
      {
        paramHttpSession.removeAttribute("ReportOutput");
        paramHttpSession.removeAttribute("TotalBeginBalance");
        paramHttpSession.removeAttribute("TotalForecastBalance");
        paramHttpSession.removeAttribute("TotalNetInflows");
      }
      else if (this.zC.equals("GeneralLedgerReport"))
      {
        paramHttpSession.removeAttribute("ReportOutput");
      }
      else if ((this.zC.equals("CreditReport")) || (this.zC.equals("DebitReport")) || (this.zC.equals("DepositDetail")) || (this.zC.equals("TransactionDetail")))
      {
        paramHttpSession.removeAttribute("ReportOutput");
        paramHttpSession.removeAttribute("ReportAccounts");
      }
      else if (this.zC.equals("AccountHistoryReport"))
      {
        paramHttpSession.removeAttribute("ReportOutput");
        paramHttpSession.removeAttribute("ReportAccounts");
        paramHttpSession.removeAttribute("ReportAccountHistories");
      }
      else if (this.zC.equals("BalanceSummaryReport"))
      {
        paramHttpSession.removeAttribute("BalanceSummaryRecords");
      }
      else if (this.zC.equals("BalanceDetailOnlyReport"))
      {
        paramHttpSession.removeAttribute("BalanceDetailRecords");
      }
      else if (this.zC.equals("CustomSummaryReport"))
      {
        paramHttpSession.removeAttribute("ExtendedAccountSummaries");
        paramHttpSession.removeAttribute("SortedByDateFirst");
      }
      else
      {
        this.error = 68;
        return super.getTaskErrorURL();
      }
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setReportType(String paramString)
  {
    this.zC = paramString;
  }
  
  public String getReportType()
  {
    return this.zC;
  }
  
  public void setAction(String paramString)
  {
    this.zx = paramString;
  }
  
  public String getAction()
  {
    return this.zx;
  }
  
  public void setForecastDate(String paramString)
  {
    this.zA = paramString;
  }
  
  public String getForecastDate()
  {
    return this.zA;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.PrepareReportForDisplay
 * JD-Core Version:    0.7.0.1
 */