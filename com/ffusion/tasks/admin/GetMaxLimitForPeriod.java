package com.ffusion.tasks.admin;

import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMaxLimitForPeriod
  extends BaseTask
  implements AdminTask
{
  protected String _noLimitString;
  protected String _objectID;
  protected String _transactionMapName;
  protected String _dayMapName;
  protected String _weekMapName;
  protected String _monthMapName;
  protected HashMap _transactionMap;
  protected HashMap _dayMap;
  protected HashMap _weekMap;
  protected HashMap _monthMap;
  private Locale aeI;
  private static final String aeJ = "no_maximum";
  private static final String aeK = "num_maximum";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this._transactionMapName == null)
    {
      this.error = 4550;
      return this.taskErrorURL;
    }
    if (this._dayMapName == null)
    {
      this.error = 4551;
      return this.taskErrorURL;
    }
    if (this._weekMapName == null)
    {
      this.error = 4552;
      return this.taskErrorURL;
    }
    if (this._monthMapName == null)
    {
      this.error = 4553;
      return this.taskErrorURL;
    }
    this._transactionMap = ((HashMap)localHttpSession.getAttribute(this._transactionMapName));
    this._dayMap = ((HashMap)localHttpSession.getAttribute(this._dayMapName));
    this._weekMap = ((HashMap)localHttpSession.getAttribute(this._weekMapName));
    this._monthMap = ((HashMap)localHttpSession.getAttribute(this._monthMapName));
    this.aeI = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.aeI == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.tasks.resources", this.aeI);
    if (localResourceBundle == null)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    return str;
  }
  
  public String getTransactionLimit()
  {
    BigDecimal localBigDecimal = getTransactionLimitValue();
    return getLimitString(localBigDecimal);
  }
  
  public BigDecimal getTransactionLimitValue()
  {
    BigDecimal localBigDecimal = getLimitAmount(this._transactionMap, getDailyLimitValue());
    return localBigDecimal;
  }
  
  public String getTransactionLimitDisplay()
  {
    String str = getTransactionLimit();
    return getLimitDisplay(str);
  }
  
  public String getDailyLimit()
  {
    BigDecimal localBigDecimal = getDailyLimitValue();
    return getLimitString(localBigDecimal);
  }
  
  public BigDecimal getDailyLimitValue()
  {
    BigDecimal localBigDecimal = getLimitAmount(this._dayMap, getWeeklyLimitValue());
    return localBigDecimal;
  }
  
  public String getDailyLimitDisplay()
  {
    String str = getDailyLimit();
    return getLimitDisplay(str);
  }
  
  public String getWeeklyLimit()
  {
    BigDecimal localBigDecimal = getWeeklyLimitValue();
    return getLimitString(localBigDecimal);
  }
  
  public BigDecimal getWeeklyLimitValue()
  {
    BigDecimal localBigDecimal = getLimitAmount(this._weekMap, getMonthlyLimitValue());
    if (localBigDecimal == null) {
      localBigDecimal = getMonthlyLimitValue();
    }
    return localBigDecimal;
  }
  
  public String getWeeklyLimitDisplay()
  {
    String str = getWeeklyLimit();
    return getLimitDisplay(str);
  }
  
  public String getMonthlyLimit()
  {
    BigDecimal localBigDecimal = getMonthlyLimitValue();
    return getLimitString(localBigDecimal);
  }
  
  public BigDecimal getMonthlyLimitValue()
  {
    BigDecimal localBigDecimal = getLimitAmount(this._monthMap, null);
    return localBigDecimal;
  }
  
  public String getMonthlyLimitDisplay()
  {
    String str = getMonthlyLimit();
    return getLimitDisplay(str);
  }
  
  public void setNoLimitString(String paramString)
  {
    this._noLimitString = paramString;
  }
  
  public String getNoLimitString()
  {
    return this._noLimitString;
  }
  
  public void setObjectID(String paramString)
  {
    this._objectID = paramString;
  }
  
  public String getObjectID()
  {
    return this._objectID;
  }
  
  public void setPerTransactionMapName(String paramString)
  {
    this._transactionMapName = paramString;
  }
  
  public String getPerTransactionMapName()
  {
    return this._transactionMapName;
  }
  
  public void setPerDayMapName(String paramString)
  {
    this._dayMapName = paramString;
  }
  
  public String getPerDayMapName()
  {
    return this._dayMapName;
  }
  
  public void setPerWeekMapName(String paramString)
  {
    this._weekMapName = paramString;
  }
  
  public String getPerWeekMapName()
  {
    return this._weekMapName;
  }
  
  public void setPerMonthMapName(String paramString)
  {
    this._monthMapName = paramString;
  }
  
  public String getPerMonthMapName()
  {
    return this._monthMapName;
  }
  
  protected String getLimitString(BigDecimal paramBigDecimal)
  {
    String str = this._noLimitString;
    if (paramBigDecimal != null) {
      str = paramBigDecimal.toString();
    }
    return str;
  }
  
  protected BigDecimal getLimitAmount(HashMap paramHashMap, BigDecimal paramBigDecimal)
  {
    if (paramHashMap == null) {
      return paramBigDecimal;
    }
    Limit localLimit = (Limit)paramHashMap.get(this._objectID);
    if (localLimit == null) {
      return paramBigDecimal;
    }
    BigDecimal localBigDecimal = localLimit.getAmount();
    if (paramBigDecimal != null) {
      localBigDecimal = localBigDecimal.compareTo(paramBigDecimal) <= 0 ? localBigDecimal : paramBigDecimal;
    }
    return localBigDecimal;
  }
  
  public String getLimitDisplay(String paramString)
  {
    if (paramString.equalsIgnoreCase(this._noLimitString)) {
      return ResourceUtil.getString("no_maximum", "com.ffusion.tasks.resources", this.aeI);
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.tasks.resources", "num_maximum", arrayOfObject);
    return (String)localLocalizableString.localize(this.aeI);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetMaxLimitForPeriod
 * JD-Core Version:    0.7.0.1
 */