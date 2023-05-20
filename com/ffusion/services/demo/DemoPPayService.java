package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayAccounts;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.beans.positivepay.PPayDecision;
import com.ffusion.beans.positivepay.PPayDecisionRpt;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssueRpt;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.positivepay.PPaySummary;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.positivepay.PPayException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.PositivePay;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class DemoPPayService
  implements PositivePay
{
  public static final String PPAYACCOUNTS = "PPAYACCOUNTS";
  public static final String PPAYSUMMARIES = "PPAYSUMMARIES";
  public static final String PPAYCHECKRECORDS = "PPAYCHECKRECORDS";
  public static final String PPAYISSUES = "PPAYISSUES";
  private static Locale jdField_try = ;
  private static final String jdField_int = "demoPPay.xml";
  private PPayIssues jdField_do = null;
  private PPaySummaries jdField_byte = null;
  private PPayCheckRecords a = null;
  private PPayAccounts jdField_for = null;
  private int jdField_if = 0;
  private ArrayList jdField_new = null;
  
  public void initialize()
    throws PPayException
  {
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "demoPPay.xml");
    String str = null;
    try
    {
      str = Strings.streamToString(localInputStream);
    }
    catch (IOException localIOException)
    {
      DebugLog.throwing("Could not convert input stream to string", localIOException);
    }
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), str);
    }
    catch (Throwable localThrowable) {}
  }
  
  public PPayAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PPayException
  {
    if (this.jdField_for == null)
    {
      DebugLog.log("No Accounts to list: _ppAccts == null");
      throw new PPayException(4);
    }
    return this.jdField_for;
  }
  
  public synchronized PPaySummaries getSummaries(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    try
    {
      PPayAccounts localPPayAccounts = getAccounts(new SecureUser(), new HashMap());
      Iterator localIterator = localPPayAccounts.iterator();
      this.jdField_byte = new PPaySummaries(jdField_try);
      while (localIterator.hasNext())
      {
        PPaySummary localPPaySummary = this.jdField_byte.add();
        PPayAccount localPPayAccount = (PPayAccount)localIterator.next();
        localPPaySummary.setPPayAccount(localPPayAccount);
        PPayIssues localPPayIssues = getIssuesForAccount(localPPayAccount, new HashMap());
        localPPaySummary.setNumIssues(localPPayIssues.size());
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("123abc Could not get list of Summaries", localException);
      throw new PPayException(4, localException);
    }
    return this.jdField_byte;
  }
  
  public synchronized int getNumIssues(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    try
    {
      PPayAccounts localPPayAccounts = getAccounts(new SecureUser(), new HashMap());
      Iterator localIterator = localPPayAccounts.iterator();
      while (localIterator.hasNext())
      {
        PPayAccount localPPayAccount = (PPayAccount)localIterator.next();
        PPayIssues localPPayIssues = getIssuesForAccount(localPPayAccount, new HashMap());
        this.jdField_if += localPPayIssues.size();
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("123abc Could not get list of NumIssues", localException);
      throw new PPayException(4, localException);
    }
    return this.jdField_if;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException
  {
    PPayIssueRpt localPPayIssueRpt = null;
    PPayDecisionRpt localPPayDecisionRpt = null;
    PPayDecisions localPPayDecisions = new PPayDecisions(jdField_try);
    localPPayDecisions.addAll(this.jdField_new);
    if (paramReportCriteria == null)
    {
      DebugLog.log("Incoming ReportCriteria object is null.");
      throw new PPayException(5);
    }
    Properties localProperties = paramReportCriteria.getReportOptions();
    if (localProperties == null)
    {
      DebugLog.log("Incoming ReportCriteria's reportOptions(Properties) object is null.");
      throw new PPayException(5);
    }
    String str = localProperties.getProperty("REPORTTYPE");
    if (str.equals("Exceptions Summary"))
    {
      localPPayIssueRpt = new PPayIssueRpt(jdField_try);
      localPPayIssueRpt.setIssues(this.jdField_do);
      return localPPayIssueRpt;
    }
    if (str.equals("Decision History"))
    {
      localPPayDecisionRpt = new PPayDecisionRpt(jdField_try);
      localPPayDecisionRpt.setDecisions(localPPayDecisions);
      return localPPayDecisionRpt;
    }
    DebugLog.log("This report type '" + str + "' is not supported." + "Valid options include '" + "Exceptions Summary" + "' and '" + "Decision History" + "'.");
    throw new PPayException(19);
  }
  
  public PPayIssues getIssuesForAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {
    if (this.jdField_do == null)
    {
      DebugLog.log("No Issues to list: _ppIssues == null");
      throw new PPayException(4);
    }
    Iterator localIterator = this.jdField_do.iterator();
    PPayIssue localPPayIssue1 = null;
    PPayIssues localPPayIssues = new PPayIssues(jdField_try);
    while (localIterator.hasNext())
    {
      localPPayIssue1 = (PPayIssue)localIterator.next();
      if ((localPPayIssue1.getCheckRecord().getAccountID().equals(paramPPayAccount.getAccountID())) && (localPPayIssue1.getCheckRecord().getBankID().equals(paramPPayAccount.getBankID())) && (!a(localPPayIssue1.getCheckRecord().getAccountID(), localPPayIssue1.getCheckRecord().getBankID(), localPPayIssue1.getCheckRecord().getCheckNumber())))
      {
        PPayIssue localPPayIssue2 = localPPayIssues.add();
        localPPayIssue2.set(localPPayIssue1);
      }
    }
    return localPPayIssues;
  }
  
  public void submitDecisions(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws PPayException
  {
    this.jdField_new = new PPayDecisions(jdField_try);
    Iterator localIterator1 = paramPPayDecisions.iterator();
    while (localIterator1.hasNext())
    {
      PPayDecision localPPayDecision = (PPayDecision)localIterator1.next();
      this.jdField_new.add(localPPayDecision);
      Iterator localIterator2 = this.jdField_do.iterator();
      while (localIterator2.hasNext())
      {
        PPayIssue localPPayIssue = (PPayIssue)localIterator2.next();
        if ((localPPayIssue.getCheckRecord().equals(localPPayDecision.getCheckRecord())) && (localPPayIssue.getRejectReason().equals(localPPayDecision.getRejectReason())) && (localPPayIssue.getIssueDate().equals(localPPayDecision.getIssueDate()))) {
          localIterator2.remove();
        }
      }
    }
  }
  
  public void submitCheckRecords(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws PPayException
  {
    Iterator localIterator = paramPPayCheckRecords.iterator();
    while (localIterator.hasNext())
    {
      PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)localIterator.next();
      System.out.println("CHECKRECORD: ");
      System.out.println("  ACT:" + localPPayCheckRecord.getAccountID());
      System.out.println("  BID:" + localPPayCheckRecord.getBankID());
      System.out.println("  CN:" + localPPayCheckRecord.getCheckNumber());
      System.out.println("  CD:" + localPPayCheckRecord.getCheckDate());
      System.out.println("  AMT:" + localPPayCheckRecord.getAmount());
      System.out.println("  ADD:" + localPPayCheckRecord.getAdditionalData());
      System.out.println("  Void:" + localPPayCheckRecord.getVoidCheck());
    }
  }
  
  public void deleteAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {}
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws PPayException
  {
    System.out.println("We've gotten the cleanup method called!!!");
    System.out.println("Age? " + paramInt);
  }
  
  private boolean a(String paramString1, String paramString2, String paramString3)
  {
    if (this.jdField_new != null)
    {
      Iterator localIterator = this.jdField_new.iterator();
      PPayDecision localPPayDecision = null;
      while (localIterator.hasNext())
      {
        localPPayDecision = (PPayDecision)localIterator.next();
        if ((localPPayDecision.getCheckRecord().getCheckNumber().equals(paramString3)) && (localPPayDecision.getCheckRecord().getAccountID().equals(paramString1)) && (localPPayDecision.getCheckRecord().getBankID().equals(paramString2))) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  protected class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PPAYACCOUNTS"))
      {
        if (DemoPPayService.this.jdField_for == null) {
          DemoPPayService.this.jdField_for = new PPayAccounts(DemoPPayService.try);
        }
        DemoPPayService.this.jdField_for.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("PPAYCHECKRECORDS"))
      {
        if (DemoPPayService.this.a == null) {
          DemoPPayService.this.a = new PPayCheckRecords(DemoPPayService.try);
        }
        DemoPPayService.this.a.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("PPAYISSUES"))
      {
        if (DemoPPayService.this.jdField_do == null) {
          DemoPPayService.this.jdField_do = new PPayIssues(DemoPPayService.try);
        }
        DemoPPayService.this.jdField_do.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoPPayService
 * JD-Core Version:    0.7.0.1
 */