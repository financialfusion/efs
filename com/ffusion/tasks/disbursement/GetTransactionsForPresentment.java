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

public class GetTransactionsForPresentment
  extends BaseTask
  implements Task
{
  public static final String MINIMUM_TRANSACTION_INDEX_FOR_RANGE = "MINIMUM_TRANSACTION_INDEX_FOR_RANGE";
  public static final String MAXIMUM_TRANSACTION_INDEX_FOR_RANGE = "MAXIMUM_TRANSACTION_INDEX_FOR_RANGE";
  private long DZ = -1L;
  private long Ea = -1L;
  private long Eb = -1L;
  private boolean D7 = false;
  private boolean D2 = false;
  private boolean D1 = false;
  private boolean DY = false;
  private String D0;
  private String D4 = null;
  private String D8 = null;
  protected boolean _moreNextTransactionsForDisplay = true;
  protected boolean _morePrevTransactionsForDisplay = true;
  protected long _minTransactionInDay = -1L;
  protected long _maxTransactionInDay = -1L;
  String D9 = "P";
  Calendar D5 = null;
  Calendar D3 = null;
  String D6 = "MM/dd/yyyy";
  
  public void processTransactions(DisbursementTransactions paramDisbursementTransactions)
  {
    DisbursementTransaction localDisbursementTransaction1;
    DisbursementTransaction localDisbursementTransaction2;
    if (this.D7)
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
    else if (this.D2)
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
    if ((this.D0 != null) && (this.D0.length() > 0)) {
      localHashMap.put("PAGESIZE", this.D0);
    }
    localHashMap.put("DATA_CLASSIFICATION", this.D9);
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
    if (this.D4 != null)
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
        localObject2 = ((DisbursementSummaries)localObject1).getByAccountID(this.D4);
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
    else if (this.D8 == null)
    {
      this.error = 21002;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        if (this.D4 != null)
        {
          if ((this.D5 == null) && (this.D3 == null))
          {
            localObject2 = Calendar.getInstance();
            ((Calendar)localObject2).set(11, 0);
            ((Calendar)localObject2).set(12, 0);
            ((Calendar)localObject2).set(13, 0);
            ((Calendar)localObject2).set(14, 0);
            localObject3 = (Calendar)((Calendar)localObject2).clone();
            ((Calendar)localObject3).add(5, 1);
            ((Calendar)localObject3).add(14, -1);
            localDisbursementTransactions = Disbursements.getPagedTransactions(localSecureUser, (DisbursementAccount)localObject1, (Calendar)localObject2, (Calendar)localObject3, this.D8, localHashMap);
          }
          else
          {
            if (this.D5 != null)
            {
              this.D5.set(11, 0);
              this.D5.set(12, 0);
              this.D5.set(13, 0);
              this.D5.set(14, 0);
            }
            if (this.D3 != null)
            {
              this.D3.set(11, 0);
              this.D3.set(12, 0);
              this.D3.set(13, 0);
              this.D3.set(14, 0);
              this.D3.add(6, 1);
              this.D3.add(14, -1);
            }
            localDisbursementTransactions = Disbursements.getPagedTransactions(localSecureUser, (DisbursementAccount)localObject1, this.D5, this.D3, this.D8, localHashMap);
          }
          this._minTransactionInDay = ((Long)localHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
          this._maxTransactionInDay = ((Long)localHashMap.get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
        }
        else
        {
          if (this.D1)
          {
            if (this.Eb == this.Ea) {
              this.D7 = true;
            }
            this.Eb += 1L;
          }
          else if (this.DY)
          {
            if (this.Eb == this.DZ) {
              this.D2 = true;
            }
            this.Eb -= 1L;
          }
          if (this.D7 == true) {
            localDisbursementTransactions = Disbursements.getNextTransactions(localSecureUser, (DisbursementAccount)localObject1, this.Ea + 1L, localHashMap);
          } else if (this.D2 == true) {
            localDisbursementTransactions = Disbursements.getPreviousTransactions(localSecureUser, (DisbursementAccount)localObject1, this.DZ - 1L, localHashMap);
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
      this.DZ = localDisbursementTransaction.getTransactionIndex();
      localDisbursementTransaction = (DisbursementTransaction)localDisbursementTransactions.get(localDisbursementTransactions.size() - 1);
      this.Ea = localDisbursementTransaction.getTransactionIndex();
      localHttpSession.setAttribute("DisbursementItems", localDisbursementTransactions);
    }
    if ((this._maxTransactionInDay <= 0L) && (this._minTransactionInDay <= 0L))
    {
      this._moreNextTransactionsForDisplay = false;
      this._morePrevTransactionsForDisplay = false;
    }
    if ((this.D1) && (this.Eb > this.Ea)) {
      this.Eb = this.Ea;
    } else if ((this.DY) && (this.Eb < this.DZ)) {
      this.Eb = this.DZ;
    }
    this.D1 = false;
    this.DY = false;
    this.D4 = null;
    this.D7 = false;
    this.D2 = false;
    this.D8 = null;
    return str;
  }
  
  public void setNextPage(String paramString)
  {
    this.D7 = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.D2 = true;
  }
  
  public void setPageSize(String paramString)
  {
    this.D0 = paramString;
  }
  
  public String getPageSize()
  {
    return this.D0;
  }
  
  public void setAccountID(String paramString)
  {
    this.D4 = paramString;
  }
  
  public String getFirstIndex()
  {
    return String.valueOf(this.DZ);
  }
  
  public String getLastIndex()
  {
    return String.valueOf(this.Ea);
  }
  
  public String getTransactionIndex()
  {
    return String.valueOf(this.Eb);
  }
  
  public void setTransactionIndex(String paramString)
  {
    try
    {
      this.Eb = Long.parseLong(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setNextTransaction(String paramString)
  {
    this.D1 = true;
  }
  
  public void setPreviousTransaction(String paramString)
  {
    this.DY = true;
  }
  
  public void setPresentment(String paramString)
  {
    this.D8 = paramString;
  }
  
  public String getPresentment()
  {
    return this.D8;
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
    this.D9 = paramString;
  }
  
  public String getDataClassification()
  {
    return this.D9;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.D6)) {
      this.D6 = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.D6;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.D6))) {
      this.D5 = null;
    } else {
      try
      {
        this.D5 = Calendar.getInstance();
        this.D5.setTime(DateFormatUtil.getFormatter(this.D6).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.D5 = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.D6 + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.D5 != null) {
      str = DateFormatUtil.getFormatter(this.D6).format(this.D5.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.D6))) {
      this.D3 = null;
    } else {
      try
      {
        this.D3 = Calendar.getInstance();
        this.D3.setTime(DateFormatUtil.getFormatter(this.D6).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.D3 = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.D6 + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.D3 != null) {
      str = DateFormatUtil.getFormatter(this.D6).format(this.D3.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.GetTransactionsForPresentment
 * JD-Core Version:    0.7.0.1
 */