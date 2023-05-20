package com.ffusion.tasks.admin;

import com.ffusion.csil.CSILException;
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

public class GetMaxACHLimitForPeriod
  extends BaseTask
  implements AdminTask
{
  private static final String XV = "perBatch";
  private static final String XS = "daily";
  private static final int XO = 0;
  private static final int XT = 1;
  private static final int XP = 2;
  private static final int XU = 3;
  protected HttpSession _session;
  protected String _noLimitString;
  protected String _objectID;
  protected String _objectType;
  protected String _transactionMapName;
  protected String _dayMapName;
  protected HashMap _transactionMap;
  protected HashMap _dayMap;
  private Locale XQ;
  private static final String XR = "no_maximum";
  private static final String XW = "num_maximum";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    this._session = paramHttpServletRequest.getSession();
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
    this._transactionMap = ((HashMap)this._session.getAttribute(this._transactionMapName));
    this._dayMap = ((HashMap)this._session.getAttribute(this._dayMapName));
    this.XQ = ((Locale)this._session.getAttribute("java.util.Locale"));
    if (this.XQ == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.tasks.resources", this.XQ);
    if (localResourceBundle == null)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    return str;
  }
  
  public String getTransactionLimit()
    throws CSILException
  {
    BigDecimal localBigDecimal = getTransactionLimitValue();
    return getLimitString(localBigDecimal);
  }
  
  public BigDecimal getTransactionLimitValue()
    throws CSILException
  {
    BigDecimal localBigDecimal = getLimitAmount(this._transactionMap, getDailyLimitValue());
    return localBigDecimal;
  }
  
  public String getTransactionLimitDisplay()
    throws CSILException
  {
    String str = getTransactionLimit();
    return getLimitDisplay(str);
  }
  
  public String getDailyLimit()
    throws CSILException
  {
    BigDecimal localBigDecimal = getDailyLimitValue();
    return getLimitString(localBigDecimal);
  }
  
  public BigDecimal getDailyLimitValue()
    throws CSILException
  {
    BigDecimal localBigDecimal = getLimitAmount(this._dayMap, null);
    return localBigDecimal;
  }
  
  public String getDailyLimitDisplay()
    throws CSILException
  {
    String str = getDailyLimit();
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
  
  public void setObjectType(String paramString)
  {
    this._objectType = paramString;
  }
  
  public String getObjectType()
  {
    return this._objectType;
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
    Limit localLimit = (Limit)paramHashMap.get(this._objectID + " (" + this._objectType + ")");
    if (localLimit == null) {
      return paramBigDecimal;
    }
    BigDecimal localBigDecimal = localLimit.getAmount();
    if (paramBigDecimal != null) {
      localBigDecimal = localBigDecimal.compareTo(paramBigDecimal) <= 0 ? localBigDecimal : paramBigDecimal;
    }
    return localBigDecimal;
  }
  
  private String c(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    for (int i = 0; i < paramString2.length(); i++)
    {
      char c = paramString2.charAt(i);
      if ((c != '+') && (c != '-') && (c != ' ')) {
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
  
  private BigDecimal E(String paramString)
  {
    int i = 0;
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    for (int j = 0; j < paramString.length(); j++)
    {
      char c = paramString.charAt(j);
      if (c == '.')
      {
        i++;
        if (i > 1) {
          return null;
        }
      }
      else if (!Character.isDigit(c))
      {
        return null;
      }
    }
    BigDecimal localBigDecimal = null;
    try
    {
      localBigDecimal = new BigDecimal(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return localBigDecimal;
  }
  
  public String getLimitDisplay(String paramString)
  {
    if (paramString.equalsIgnoreCase(this._noLimitString)) {
      return ResourceUtil.getString("no_maximum", "com.ffusion.tasks.resources", this.XQ);
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.tasks.resources", "num_maximum", arrayOfObject);
    return (String)localLocalizableString.localize(this.XQ);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetMaxACHLimitForPeriod
 * JD-Core Version:    0.7.0.1
 */