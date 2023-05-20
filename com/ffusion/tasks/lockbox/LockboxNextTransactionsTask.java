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
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxNextTransactionsTask
  extends BaseTask
{
  protected String _accountID = null;
  protected long _nextIdx;
  protected int _pageSize;
  protected long _lastIndex;
  protected long _firstIndex;
  protected boolean _moreTransactionsForDisplay = true;
  protected long _minTransactionInDay = -1L;
  protected long _maxTransactionInDay = -1L;
  String aNu = "P";
  
  public long getMinTransactionInDay()
  {
    return this._minTransactionInDay;
  }
  
  public void setMinTransactionInDay(String paramString)
  {
    this._minTransactionInDay = Long.parseLong(paramString);
  }
  
  public long getMaxTransactionInDay()
  {
    return this._maxTransactionInDay;
  }
  
  public void setMaxTransactionInDay(String paramString)
  {
    this._maxTransactionInDay = Long.parseLong(paramString);
  }
  
  public boolean getMoreTransactionsForDisplay()
  {
    return this._moreTransactionsForDisplay;
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
  
  public long getNextIndex()
  {
    return this._nextIdx;
  }
  
  public void setNextIndex(long paramLong)
  {
    this._nextIdx = paramLong;
  }
  
  public void setNextIndex(String paramString)
  {
    this._nextIdx = Long.parseLong(paramString);
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
  
  public void setDataClassification(String paramString)
  {
    this.aNu = paramString;
  }
  
  public String getDataClassification()
  {
    return this.aNu;
  }
  
  public void processTransactions(LockboxTransactions paramLockboxTransactions)
  {
    LockboxTransaction localLockboxTransaction1 = (LockboxTransaction)paramLockboxTransactions.get(paramLockboxTransactions.size() - 1);
    if (localLockboxTransaction1.getTransactionIndex() > this._maxTransactionInDay)
    {
      this._moreTransactionsForDisplay = false;
      for (int i = paramLockboxTransactions.size() - 1; i > 0; i--)
      {
        LockboxTransaction localLockboxTransaction2 = (LockboxTransaction)paramLockboxTransactions.get(i);
        if (localLockboxTransaction2.getTransactionIndex() <= this._maxTransactionInDay) {
          break;
        }
        paramLockboxTransactions.remove(i);
      }
    }
    else if (localLockboxTransaction1.getTransactionIndex() == this._maxTransactionInDay)
    {
      this._moreTransactionsForDisplay = false;
    }
    else
    {
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
    try
    {
      localObject2 = new HashMap();
      ((HashMap)localObject2).put("PAGESIZE", new Integer(this._pageSize));
      ((HashMap)localObject2).put("DATA_CLASSIFICATION", this.aNu);
      if (this._minTransactionInDay != -1L) {
        ((HashMap)localObject2).put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._minTransactionInDay));
      }
      if (this._maxTransactionInDay != -1L) {
        ((HashMap)localObject2).put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._maxTransactionInDay));
      }
      LockboxTransactions localLockboxTransactions = Lockbox.getNextTransactions(localSecureUser, localObject1, this._nextIdx, (HashMap)localObject2);
      if (localLockboxTransactions.size() != 0)
      {
        processTransactions(localLockboxTransactions);
        setLastIndex(localLockboxTransactions);
        setFirstIndex(localLockboxTransactions);
      }
      else
      {
        this._moreTransactionsForDisplay = false;
        this._lastIndex = (this._nextIdx - this._pageSize);
        this._firstIndex = (this._nextIdx + this._pageSize);
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxNextTransactionsTask
 * JD-Core Version:    0.7.0.1
 */