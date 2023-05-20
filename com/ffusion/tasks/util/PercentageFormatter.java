package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PercentageFormatter
  extends BaseTask
{
  public static final int ERROR_MISSING_PCT_VALUE = 27000;
  public static final int ERROR_CANNOT_FORMAT_PCT_VALUE = 27001;
  private SecureUser QR;
  private Locale QU;
  private String QQ;
  private String QP;
  private int QS;
  private boolean QT;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.QR = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.QU = BaseTask.getLocale(localHttpSession, this.QR);
    if (validateInput()) {
      try
      {
        BigDecimal localBigDecimal = null;
        NumberFormat localNumberFormat = null;
        localBigDecimal = new BigDecimal(this.QP);
        if (!this.QT) {
          localBigDecimal = localBigDecimal.divide(new BigDecimal(100.0D), 10, 4);
        }
        localNumberFormat = NumberFormat.getPercentInstance(this.QU);
        localNumberFormat.setMinimumFractionDigits(this.QS);
        localNumberFormat.setMaximumFractionDigits(this.QS);
        this.QQ = localNumberFormat.format(localBigDecimal.doubleValue());
      }
      catch (Exception localException)
      {
        this.QQ = null;
        this.error = 27001;
        str = this.taskErrorURL;
      }
    } else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setPercentageValue(String paramString)
  {
    this.QP = paramString;
  }
  
  public void setIsDecimalRepresentation(String paramString)
  {
    this.QT = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setNumDecimalPlaces(String paramString)
  {
    try
    {
      this.QS = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.QS = 0;
    }
  }
  
  public String getFormattedPercentageValue()
  {
    return this.QQ;
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.QR == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && ((this.QP == null) || (this.QP.length() == 0)))
    {
      this.error = 27000;
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.PercentageFormatter
 * JD-Core Version:    0.7.0.1
 */