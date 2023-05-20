package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
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

public class LockboxPagedCreditItemsTask
  extends BaseTask
{
  public static final String MINIMUM_TRANSACTION_INDEX_FOR_RANGE = "MINIMUM_TRANSACTION_INDEX_FOR_RANGE";
  public static final String MAXIMUM_TRANSACTION_INDEX_FOR_RANGE = "MAXIMUM_TRANSACTION_INDEX_FOR_RANGE";
  protected String _accountID = null;
  protected LockboxAccount _account = null;
  protected int _pageSize;
  protected String _lockboxNumber = null;
  protected long _lastIndex;
  protected long _firstIndex;
  protected boolean _moreCreditItemsForDisplay = true;
  protected long _minCreditItemInDay;
  protected long _maxCreditItemInDay;
  String aNd = "P";
  Calendar aNb = null;
  Calendar aNc = null;
  String aNa = "MM/dd/yyyy";
  
  public boolean getMoreCreditItemsForDisplay()
  {
    return this._moreCreditItemsForDisplay;
  }
  
  public long getMinCreditItemInDay()
  {
    return this._minCreditItemInDay;
  }
  
  public long getMaxCreditItemInDay()
  {
    return this._maxCreditItemInDay;
  }
  
  public void setLockboxNumber(String paramString)
  {
    this._lockboxNumber = paramString;
  }
  
  public String getLockboxNumber()
  {
    return this._lockboxNumber;
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
  
  public void setLastIndex(LockboxCreditItems paramLockboxCreditItems)
  {
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(paramLockboxCreditItems.size() - 1);
    this._lastIndex = (localLockboxCreditItem.getItemIndex() + 1L);
  }
  
  public void setFirstIndex(LockboxCreditItems paramLockboxCreditItems)
  {
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(0);
    this._firstIndex = (localLockboxCreditItem.getItemIndex() - 1L);
  }
  
  public void setDataClassification(String paramString)
  {
    this.aNd = paramString;
  }
  
  public String getDataClassification()
  {
    return this.aNd;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.aNa)) {
      this.aNa = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.aNa;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.aNa))) {
      this.aNb = null;
    } else {
      try
      {
        this.aNb = Calendar.getInstance();
        this.aNb.setTime(DateFormatUtil.getFormatter(this.aNa).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.aNb = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.aNa + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.aNb != null) {
      str = DateFormatUtil.getFormatter(this.aNa).format(this.aNb.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.aNa))) {
      this.aNc = null;
    } else {
      try
      {
        this.aNc = Calendar.getInstance();
        this.aNc.setTime(DateFormatUtil.getFormatter(this.aNa).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.aNc = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.aNa + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.aNc != null) {
      str = DateFormatUtil.getFormatter(this.aNa).format(this.aNc.getTime());
    }
    return str;
  }
  
  public void processCreditItems(LockboxCreditItems paramLockboxCreditItems)
  {
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(paramLockboxCreditItems.size() - 1);
    if (localLockboxCreditItem.getItemIndex() == this._maxCreditItemInDay) {
      this._moreCreditItemsForDisplay = false;
    } else {
      this._moreCreditItemsForDisplay = true;
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
    if ((this._lockboxNumber == null) || (this._lockboxNumber.length() <= 0))
    {
      this.error = 71;
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
      ((HashMap)localObject2).put("DATA_CLASSIFICATION", this.aNd);
      ((HashMap)localObject2).put("PAGESIZE", new Integer(this._pageSize));
      LockboxCreditItems localLockboxCreditItems = null;
      if (((this.aNb == null ? 1 : 0) & (this.aNc == null ? 1 : 0)) != 0)
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
        localLockboxCreditItems = Lockbox.getPagedCreditItems(localSecureUser, localObject1, this._lockboxNumber, localCalendar1, localCalendar2, (HashMap)localObject2);
      }
      else
      {
        if (this.aNb != null)
        {
          this.aNb.set(11, 0);
          this.aNb.set(12, 0);
          this.aNb.set(13, 0);
          this.aNb.set(14, 0);
        }
        if (this.aNc != null)
        {
          this.aNc.set(11, 0);
          this.aNc.set(12, 0);
          this.aNc.set(13, 0);
          this.aNc.set(14, 0);
          this.aNc.add(6, 1);
          this.aNc.add(14, -1);
        }
        localLockboxCreditItems = Lockbox.getPagedCreditItems(localSecureUser, localObject1, this._lockboxNumber, this.aNb, this.aNc, (HashMap)localObject2);
      }
      this._minCreditItemInDay = ((Long)((HashMap)localObject2).get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
      this._maxCreditItemInDay = ((Long)((HashMap)localObject2).get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
      if (localLockboxCreditItems.size() != 0)
      {
        processCreditItems(localLockboxCreditItems);
        setLastIndex(localLockboxCreditItems);
        setFirstIndex(localLockboxCreditItems);
      }
      else
      {
        this._moreCreditItemsForDisplay = false;
      }
      localHttpSession.setAttribute("LOCKBOX_CREDIT_ITEMS", localLockboxCreditItems);
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
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxPagedCreditItemsTask
 * JD-Core Version:    0.7.0.1
 */