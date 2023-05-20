package com.ffusion.tasks.disbursement;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementSummary;
import com.ffusion.beans.disbursement.DisbursementTransaction;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Disbursements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactions
  extends BaseTask
  implements Task
{
  public static final String MINIMUM_TRANSACTION_INDEX_FOR_RANGE = "MINIMUM_TRANSACTION_INDEX_FOR_RANGE";
  public static final String MAXIMUM_TRANSACTION_INDEX_FOR_RANGE = "MAXIMUM_TRANSACTION_INDEX_FOR_RANGE";
  private long DM = -1L;
  private long DW = -1L;
  private long DX = -1L;
  private boolean DU = false;
  private boolean DP = false;
  private boolean DO = false;
  private boolean DL = false;
  private String DN;
  private String DR = null;
  protected boolean _moreNextTransactionsForDisplay = true;
  protected boolean _morePrevTransactionsForDisplay = true;
  protected long _minTransactionInDay = -1L;
  protected long _maxTransactionInDay = -1L;
  String DV = "P";
  Calendar DS = null;
  Calendar DQ = null;
  String DT = "MM/dd/yyyy";
  
  public void processTransactions(DisbursementTransactions paramDisbursementTransactions)
  {
    DisbursementTransaction localDisbursementTransaction1;
    DisbursementTransaction localDisbursementTransaction2;
    if (this.DU)
    {
      this._morePrevTransactionsForDisplay = true;
      localDisbursementTransaction1 = (DisbursementTransaction)paramDisbursementTransactions.get(paramDisbursementTransactions.size() - 1);
      if (localDisbursementTransaction1.getTransactionIndex() > this._maxTransactionInDay)
      {
        this._moreNextTransactionsForDisplay = false;
        for (int i = paramDisbursementTransactions.size() - 1; i > 0; i--)
        {
          localDisbursementTransaction2 = (DisbursementTransaction)paramDisbursementTransactions.get(i);
          if (localDisbursementTransaction2.getTransactionIndex() <= this._maxTransactionInDay) {
            break;
          }
          paramDisbursementTransactions.remove(i);
        }
      }
      else if (localDisbursementTransaction1.getTransactionIndex() == this._maxTransactionInDay)
      {
        this._moreNextTransactionsForDisplay = false;
      }
      else
      {
        this._moreNextTransactionsForDisplay = true;
      }
    }
    else if (this.DP)
    {
      this._moreNextTransactionsForDisplay = true;
      localDisbursementTransaction1 = (DisbursementTransaction)paramDisbursementTransactions.get(0);
      if (localDisbursementTransaction1.getTransactionIndex() < this._minTransactionInDay)
      {
        this._morePrevTransactionsForDisplay = false;
        Iterator localIterator = paramDisbursementTransactions.iterator();
        while (localIterator.hasNext())
        {
          localDisbursementTransaction2 = (DisbursementTransaction)localIterator.next();
          if (localDisbursementTransaction2.getTransactionIndex() >= this._minTransactionInDay) {
            break;
          }
          localIterator.remove();
        }
      }
      else if (localDisbursementTransaction1.getTransactionIndex() == this._minTransactionInDay)
      {
        this._morePrevTransactionsForDisplay = false;
      }
      else
      {
        this._morePrevTransactionsForDisplay = true;
      }
    }
    else
    {
      this._morePrevTransactionsForDisplay = false;
      localDisbursementTransaction1 = (DisbursementTransaction)paramDisbursementTransactions.get(paramDisbursementTransactions.size() - 1);
      if (localDisbursementTransaction1.getTransactionIndex() == this._maxTransactionInDay) {
        this._moreNextTransactionsForDisplay = false;
      } else {
        this._moreNextTransactionsForDisplay = true;
      }
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    HashMap localHashMap = new HashMap();
    if ((this.DN != null) && (this.DN.length() > 0)) {
      localHashMap.put("PAGESIZE", this.DN);
    }
    localHashMap.put("DATA_CLASSIFICATION", this.DV);
    if (this._minTransactionInDay != -1L) {
      localHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._minTransactionInDay));
    }
    if (this._maxTransactionInDay != -1L) {
      localHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._maxTransactionInDay));
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    DisbursementTransactions localDisbursementTransactions = null;
    Object localObject2;
    Object localObject3;
    if (this.DR != null)
    {
      localHttpSession.removeAttribute("DisbursementAccount");
      localHttpSession.removeAttribute("DisbursementItems");
      localObject1 = (DisbursementSummaries)localHttpSession.getAttribute("DisbursementSummaries");
      if (localObject1 == null)
      {
        this.error = 21001;
      }
      else
      {
        localObject2 = ((DisbursementSummaries)localObject1).getByAccountID(this.DR);
        if (localObject2 == null)
        {
          this.error = 21001;
        }
        else
        {
          localObject3 = ((DisbursementSummary)localObject2).getAccount();
          localHttpSession.setAttribute("DisbursementAccount", localObject3);
        }
      }
    }
    Object localObject1 = (DisbursementAccount)localHttpSession.getAttribute("DisbursementAccount");
    if (localObject1 == null)
    {
      this.error = 21000;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        if (this.DR != null)
        {
          if (((this.DS == null ? 1 : 0) & (this.DQ == null ? 1 : 0)) != 0)
          {
            localObject2 = Calendar.getInstance();
            ((Calendar)localObject2).set(11, 0);
            ((Calendar)localObject2).set(12, 0);
            ((Calendar)localObject2).set(13, 0);
            ((Calendar)localObject2).set(14, 0);
            localObject3 = Calendar.getInstance();
            ((Calendar)localObject3).setTime(((Calendar)localObject2).getTime());
            ((Calendar)localObject3).add(5, 1);
            ((Calendar)localObject3).add(14, -1);
            localDisbursementTransactions = Disbursements.getPagedTransactions(localSecureUser, (DisbursementAccount)localObject1, (Calendar)localObject2, (Calendar)localObject3, localHashMap);
          }
          else
          {
            if (this.DS != null)
            {
              this.DS.set(11, 0);
              this.DS.set(12, 0);
              this.DS.set(13, 0);
              this.DS.set(14, 0);
            }
            if (this.DQ != null)
            {
              this.DQ.set(11, 0);
              this.DQ.set(12, 0);
              this.DQ.set(13, 0);
              this.DQ.set(14, 0);
              this.DQ.add(6, 1);
              this.DQ.add(14, -1);
            }
            localDisbursementTransactions = Disbursements.getPagedTransactions(localSecureUser, (DisbursementAccount)localObject1, this.DS, this.DQ, localHashMap);
          }
          this._minTransactionInDay = ((Long)localHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
          this._maxTransactionInDay = ((Long)localHashMap.get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
        }
        else
        {
          if (this.DO)
          {
            if (this.DX == this.DW) {
              this.DU = true;
            }
            this.DX += 1L;
          }
          else if (this.DL)
          {
            if (this.DX == this.DM) {
              this.DP = true;
            }
            this.DX -= 1L;
          }
          if (this.DU == true) {
            localDisbursementTransactions = Disbursements.getNextTransactions(localSecureUser, (DisbursementAccount)localObject1, this.DW + 1L, localHashMap);
          } else if (this.DP == true) {
            localDisbursementTransactions = Disbursements.getPreviousTransactions(localSecureUser, (DisbursementAccount)localObject1, this.DM - 1L, localHashMap);
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if ((localDisbursementTransactions != null) && (localDisbursementTransactions.size() > 0))
    {
      processTransactions(localDisbursementTransactions);
      DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localDisbursementTransactions.get(0);
      this.DM = localDisbursementTransaction.getTransactionIndex();
      localDisbursementTransaction = (DisbursementTransaction)localDisbursementTransactions.get(localDisbursementTransactions.size() - 1);
      this.DW = localDisbursementTransaction.getTransactionIndex();
      localHttpSession.setAttribute("DisbursementItems", localDisbursementTransactions);
      System.out.println("GetTransactions.process: size=" + localDisbursementTransactions.size() + ", firstIndex=" + this.DM + ", lastIndex=" + this.DW);
    }
    if ((this._maxTransactionInDay <= 0L) && (this._minTransactionInDay <= 0L))
    {
      this._moreNextTransactionsForDisplay = false;
      this._morePrevTransactionsForDisplay = false;
    }
    if ((this.DO) && (this.DX > this.DW)) {
      this.DX = this.DW;
    } else if ((this.DL) && (this.DX < this.DM)) {
      this.DX = this.DM;
    }
    this.DO = false;
    this.DL = false;
    this.DR = null;
    this.DU = false;
    this.DP = false;
    return str;
  }
  
  public void setNextPage(String paramString)
  {
    this.DU = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.DP = true;
  }
  
  public void setPageSize(String paramString)
  {
    this.DN = paramString;
  }
  
  public String getPageSize()
  {
    return this.DN;
  }
  
  public void setAccountID(String paramString)
  {
    this.DR = paramString;
  }
  
  public String getFirstIndex()
  {
    return String.valueOf(this.DM);
  }
  
  public String getLastIndex()
  {
    return String.valueOf(this.DW);
  }
  
  public String getTransactionIndex()
  {
    return String.valueOf(this.DX);
  }
  
  public void setTransactionIndex(String paramString)
  {
    try
    {
      this.DX = Long.parseLong(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setNextTransaction(String paramString)
  {
    this.DO = true;
  }
  
  public void setPreviousTransaction(String paramString)
  {
    this.DL = true;
  }
  
  public boolean getMoreNextTransactionsForDisplay()
  {
    return this._moreNextTransactionsForDisplay;
  }
  
  public boolean getMorePrevTransactionsForDisplay()
  {
    return this._morePrevTransactionsForDisplay;
  }
  
  public long getMinTransactionIndexInDay()
  {
    return this._minTransactionInDay;
  }
  
  public long getMaxTransactionIndexInDay()
  {
    return this._maxTransactionInDay;
  }
  
  public void setDataClassification(String paramString)
  {
    this.DV = paramString;
  }
  
  public String getDataClassification()
  {
    return this.DV;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.DT)) {
      this.DT = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.DT;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DT))) {
      this.DS = null;
    } else {
      try
      {
        this.DS = Calendar.getInstance();
        this.DS.setTime(DateFormatUtil.getFormatter(this.DT).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DS = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DT + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.DS != null) {
      str = DateFormatUtil.getFormatter(this.DT).format(this.DS.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DT))) {
      this.DQ = null;
    } else {
      try
      {
        this.DQ = Calendar.getInstance();
        this.DQ.setTime(DateFormatUtil.getFormatter(this.DT).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DQ = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DT + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.DQ != null) {
      str = DateFormatUtil.getFormatter(this.DT).format(this.DQ.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.GetTransactions
 * JD-Core Version:    0.7.0.1
 */