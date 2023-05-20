package com.ffusion.tasks.affiliatebank;

import com.ffusion.tasks.BaseTask;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBankIdentifierDisplayText
  extends BaseTask
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.affiliatebank.resources";
  protected String _bankIdentifierFlag = "FALSE";
  protected String _bankIdentifierDisplayText = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str2 = "BankIdentifierName_RoutingNumber";
    try
    {
      str2 = CommBankIdentifier.getBankIdentifierKey();
      boolean bool = CommBankIdentifier.getBankIdentifierFlag();
      this._bankIdentifierFlag = Boolean.toString(bool).toUpperCase();
    }
    catch (Exception localException1) {}
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 41;
      str1 = this.taskErrorURL;
    }
    else
    {
      try
      {
        this._bankIdentifierDisplayText = ResourceUtil.getString(str2, "com.ffusion.beans.affiliatebank.resources", localLocale);
        if (this._bankIdentifierDisplayText == null)
        {
          this.error = 43;
          str1 = this.taskErrorURL;
        }
        else
        {
          str1 = this.successURL;
        }
      }
      catch (Exception localException2)
      {
        this.error = 43;
        str1 = this.taskErrorURL;
      }
    }
    return str1;
  }
  
  public String getBankIdentifierDisplayText()
  {
    return this._bankIdentifierDisplayText;
  }
  
  public String getBankIdentifierFlag()
  {
    return this._bankIdentifierFlag;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetBankIdentifierDisplayText
 * JD-Core Version:    0.7.0.1
 */