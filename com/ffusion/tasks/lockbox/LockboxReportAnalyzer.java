package com.ffusion.tasks.lockbox;

import com.ffusion.beans.lockbox.LockboxDepositItemRpt;
import com.ffusion.beans.lockbox.LockboxDepositRpt;
import com.ffusion.beans.lockbox.LockboxSummaryRpt;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxReportAnalyzer
  extends BaseTask
{
  private String aNo = "LOCKBOX_REPORT_DATA";
  private ArrayList aNs = new ArrayList();
  private IReportResult aNq = null;
  private String aNr = null;
  private String aNp = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Report localReport = (Report)localHttpSession.getAttribute(this.aNo);
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    if (localReportCriteria == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    if (jdMethod_for(localReport, localHttpSession) != 0) {
      return super.getServiceErrorURL();
    }
    this.aNq = localReport.getReportResult();
    localHttpSession.setAttribute(getClass().getName(), this.aNs);
    this.error = 0;
    return super.getSuccessURL();
  }
  
  public void setReportKey(String paramString)
  {
    this.aNo = paramString;
  }
  
  public String getReportKey()
  {
    return this.aNo;
  }
  
  public IReportResult getReportResult()
  {
    return this.aNq;
  }
  
  public ArrayList getReportCriteriaList()
  {
    return this.aNs;
  }
  
  public int getReportCriteriaListSize()
  {
    return this.aNs.size();
  }
  
  public String getDates()
  {
    return this.aNp;
  }
  
  public String getReportType()
  {
    return this.aNr;
  }
  
  private int jdMethod_for(Report paramReport, HttpSession paramHttpSession)
  {
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    String str1 = localReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    Properties localProperties = localReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    this.aNs.clear();
    if ((str1 == null) || (str1.length() <= 0)) {
      return 67;
    }
    String str2 = localProperties.getProperty("StartDate");
    Object localObject1 = localProperties.getProperty("EndDate");
    if ((str2 != null) && (str2.length() > 0))
    {
      if ((localObject1 != null) && (((String)localObject1).length() > 0))
      {
        localStringBuffer.append("From ");
        localStringBuffer.append(str2);
        localStringBuffer.append(" to ");
        localStringBuffer.append((String)localObject1);
      }
      else
      {
        localStringBuffer.append("After ");
        localStringBuffer.append(str2);
      }
    }
    else if ((localObject1 != null) && (((String)localObject1).length() > 0))
    {
      localStringBuffer.append("Before ");
      localStringBuffer.append((String)localObject1);
    }
    else
    {
      localStringBuffer.append("All records");
    }
    this.aNp = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    if (str1.equalsIgnoreCase("LockboxSummary"))
    {
      str2 = (String)localProperties.get("LockboxNumber");
      localStringBuffer.append("Account=");
      if ((str2 == null) || (str2.length() <= 0) || (str2.equalsIgnoreCase("ALL"))) {
        localStringBuffer.append("ALL");
      } else {
        localStringBuffer.append(str2.substring(0, str2.indexOf(":")));
      }
      this.aNs.add(localStringBuffer.toString());
      localStringBuffer.delete(0, localStringBuffer.length());
      localObject1 = ((LockboxSummaryRpt)paramReport.getReportResult()).getLockboxSummaries();
      if (localObject1 != null) {
        paramHttpSession.setAttribute(localObject1.getClass().getName(), localObject1);
      }
    }
    else
    {
      Object localObject2;
      if (str1.equalsIgnoreCase("LockboxDepositReport"))
      {
        str2 = (String)localProperties.get("LockboxNumber");
        localStringBuffer.append("Account=");
        if ((str2 == null) || (str2.length() <= 0) || (str2.equalsIgnoreCase("ALL"))) {
          localStringBuffer.append("ALL");
        } else {
          localStringBuffer.append(str2.substring(0, str2.indexOf(":")));
        }
        this.aNs.add(localStringBuffer.toString());
        localStringBuffer.delete(0, localStringBuffer.length());
        localObject1 = localProperties.getProperty("MinimumAmount");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Amount >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MaximumAmount");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Amount <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject2 = ((LockboxDepositRpt)paramReport.getReportResult()).getLockboxTransactions();
        if (localObject2 != null) {
          paramHttpSession.setAttribute(localObject2.getClass().getName(), localObject2);
        }
      }
      else if (str1.equalsIgnoreCase("DepositItemSearch"))
      {
        str2 = (String)localProperties.get("LockboxNumber");
        localStringBuffer.append("Account=");
        if ((str2 == null) || (str2.length() <= 0) || (str2.equalsIgnoreCase("ALL"))) {
          localStringBuffer.append("ALL");
        } else {
          localStringBuffer.append(str2.substring(0, str2.indexOf(":")));
        }
        this.aNs.add(localStringBuffer.toString());
        localStringBuffer.delete(0, localStringBuffer.length());
        localObject1 = localProperties.getProperty("Payor");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Payor=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCheckNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Check number >=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCheckNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Check number <=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MinimumAmount");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Amount >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MaximumAmount");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Amount <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("documentType");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Document Type=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCouponAccountNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Account Number >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCouponAccountNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Account Number <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MinimumCouponAmount1");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Amount 1 >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MaximumCouponAmount1");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Amount 1 <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MinimumCouponAmount2");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Amount 2 >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("MaximumCouponAmount2");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Amount 2 <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCouponDate1");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Date 1 after ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCouponDate1");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Date 1 before ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCouponDate2");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Date 2 after ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCouponDate2");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Date 2 before ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCouponReferenceNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Reference Number >=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCouponReferenceNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Coupon Reference Number <=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCheckAccountNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Check Account Number >=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCheckAccountNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Check Account Number <=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("lockboxWorkType");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Lockbox Work Type=");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartLockboxBatchNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Lockbox Batch Number >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndLockboxBatchNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Lockbox Batch Number <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartCheckRoutingNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Check Routing Number >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndCheckRoutingNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Check Routing Number <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("StartLockboxSequenceNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Lockbox Sequence Number >= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject1 = localProperties.getProperty("EndLockboxSequenceNumber");
        if ((localObject1 != null) && (((String)localObject1).trim().length() > 0))
        {
          localStringBuffer.append("Lockbox Sequence Number <= ");
          localStringBuffer.append((String)localObject1);
          this.aNs.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        localObject2 = ((LockboxDepositItemRpt)paramReport.getReportResult()).getLockboxCreditItems();
        if (localObject2 != null) {
          paramHttpSession.setAttribute(localObject2.getClass().getName(), localObject2);
        }
      }
      else
      {
        return 68;
      }
    }
    this.aNr = str1;
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxReportAnalyzer
 * JD-Core Version:    0.7.0.1
 */