package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxPagedTransactionsTask
  extends BaseTask
{
  public static final String MINIMUM_TRANSACTION_INDEX_FOR_RANGE = "MINIMUM_TRANSACTION_INDEX_FOR_RANGE";
  public static final String MAXIMUM_TRANSACTION_INDEX_FOR_RANGE = "MAXIMUM_TRANSACTION_INDEX_FOR_RANGE";
  protected String _accountID = null;
  protected LockboxAccount _account = null;
  protected int _pageSize;
  protected long _lastIndex;
  protected long _firstIndex;
  protected boolean _moreTransactionsForDisplay = true;
  protected long _minTransactionInDay;
  protected long _maxTransactionInDay;
  String aNm = "P";
  Calendar aNk = null;
  Calendar aNl = null;
  String aNj = "MM/dd/yyyy";
  
  public boolean getMoreTransactionsForDisplay()
  {
    return this._moreTransactionsForDisplay;
  }
  
  public long getMinTransactionInDay()
  {
    return this._minTransactionInDay;
  }
  
  public long getMaxTransactionInDay()
  {
    return this._maxTransactionInDay;
  }
  
  public void setFirstIndex(String paramString)
  {
    this._firstIndex = Long.parseLong(paramString);
  }
  
  public long getFirstIndex()
  {
    return this._firstIndex;
  }
  
  public void setLastIndex(String paramString)
  {
    this._lastIndex = Long.parseLong(paramString);
  }
  
  public long getLastIndex()
  {
    return this._lastIndex;
  }
  
  public void setPageSize(String paramString)
  {
    this._pageSize = Integer.parseInt(paramString);
  }
  
  public int getPageSize()
  {
    return this._pageSize;
  }
  
  public String getAcctID()
  {
    return this._accountID;
  }
  
  public void setAcctID(String paramString)
  {
    this._accountID = paramString;
  }
  
  public LockboxAccount getAccount()
  {
    return this._account;
  }
  
  public void setLastIndex(LockboxTransactions paramLockboxTransactions)
  {
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramLockboxTransactions.get(paramLockboxTransactions.size() - 1);
    this._lastIndex = (localLockboxTransaction.getTransactionIndex() + 1L);
  }
  
  public void setFirstIndex(LockboxTransactions paramLockboxTransactions)
  {
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramLockboxTransactions.get(0);
    this._firstIndex = (localLockboxTransaction.getTransactionIndex() - 1L);
  }
  
  public void processTransactions(LockboxTransactions paramLockboxTransactions)
  {
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramLockboxTransactions.get(paramLockboxTransactions.size() - 1);
    if (localLockboxTransaction.getTransactionIndex() == this._maxTransactionInDay) {
      this._moreTransactionsForDisplay = false;
    } else {
      this._moreTransactionsForDisplay = true;
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    LockboxSummaries localLockboxSummaries = (LockboxSummaries)localHttpSession.getAttribute("LOCKBOX_SUMMARIES");
    if ((this._accountID == null) || (this._accountID.length() <= 0))
    {
      this.error = 50;
      return super.getServiceErrorURL();
    }
    Object localObject1 = null;
    Object localObject2;
    if (localLockboxSummaries != null)
    {
      localObject2 = null;
      for (int i = 0; i < localLockboxSummaries.size(); i++)
      {
        localObject2 = ((LockboxSummary)localLockboxSummaries.get(i)).getLockboxAccount();
        if (this._accountID.equals(((LockboxAccount)localObject2).getAccountID()))
        {
          localObject1 = localObject2;
          break;
        }
      }
    }
    if (localObject1 == null)
    {
      this.error = 62;
      return super.getServiceErrorURL();
    }
    this._account = localObject1;
    try
    {
      localObject2 = new HashMap();
      ((HashMap)localObject2).put("PAGESIZE", new Integer(this._pageSize));
      ((HashMap)localObject2).put("DATA_CLASSIFICATION", this.aNm);
      LockboxTransactions localLockboxTransactions = null;
      if ((this.aNk == null) && (this.aNl == null))
      {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.setTime(localCalendar1.getTime());
        localCalendar2.add(5, 1);
        localCalendar2.add(14, -1);
        localLockboxTransactions = Lockbox.getPagedTransactions(localSecureUser, localObject1, localCalendar1, localCalendar2, (HashMap)localObject2);
      }
      else
      {
        if (this.aNk != null)
        {
          this.aNk.set(11, 0);
          this.aNk.set(12, 0);
          this.aNk.set(13, 0);
          this.aNk.set(14, 0);
        }
        if (this.aNl != null)
        {
          this.aNl.set(11, 0);
          this.aNl.set(12, 0);
          this.aNl.set(13, 0);
          this.aNl.set(14, 0);
          this.aNl.add(6, 1);
          this.aNl.add(14, -1);
        }
        localLockboxTransactions = Lockbox.getPagedTransactions(localSecureUser, localObject1, this.aNk, this.aNl, (HashMap)localObject2);
      }
      this._minTransactionInDay = ((Long)((HashMap)localObject2).get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
      this._maxTransactionInDay = ((Long)((HashMap)localObject2).get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
      if (localLockboxTransactions.size() != 0)
      {
        processTransactions(localLockboxTransactions);
        setLastIndex(localLockboxTransactions);
        setFirstIndex(localLockboxTransactions);
      }
      else
      {
        this._moreTransactionsForDisplay = false;
      }
      localHttpSession.setAttribute("LOCKBOX_TRANSACTIONS", localLockboxTransactions);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setDataClassification(String paramString)
  {
    this.aNm = paramString;
  }
  
  public String getDataClassification()
  {
    return this.aNm;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.aNj)) {
      this.aNj = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.aNj;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.aNj))) {
      this.aNk = null;
    } else {
      try
      {
        this.aNk = Calendar.getInstance();
        this.aNk.setTime(DateFormatUtil.getFormatter(this.aNj).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.aNk = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.aNj + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.aNk != null) {
      str = DateFormatUtil.getFormatter(this.aNj).format(this.aNk.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.aNj))) {
      this.aNl = null;
    } else {
      try
      {
        this.aNl = Calendar.getInstance();
        this.aNl.setTime(DateFormatUtil.getFormatter(this.aNj).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.aNl = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.aNj + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.aNl != null) {
      str = DateFormatUtil.getFormatter(this.aNj).format(this.aNl.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxPagedTransactionsTask
 * JD-Core Version:    0.7.0.1
 */