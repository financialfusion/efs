package com.ffusion.tasks.positivepay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.csil.core.CurrencyUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCheckRecordsAmountTotal
  extends BaseTask
  implements Task
{
  Currency aPk = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    String str2 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    PPayCheckRecords localPPayCheckRecords = (PPayCheckRecords)localHttpSession.getAttribute("PPayCheckRecords");
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if ((localSecureUser == null) || (localPPayCheckRecords == null))
    {
      this.error = 22052;
      str1 = this.taskErrorURL;
    }
    else
    {
      int i = localSecureUser.getBusinessID();
      str2 = CurrencyUtil.getCurrencyCodeByBusinessId(i);
      this.aPk = new Currency(new BigDecimal(0.0D), str2, localLocale);
      for (int j = 0; j < localPPayCheckRecords.size(); j++)
      {
        PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)localPPayCheckRecords.get(j);
        if (localPPayCheckRecord.getAmount() != null) {
          this.aPk.addAmount(localPPayCheckRecord.getAmount());
        }
      }
    }
    return str1;
  }
  
  public String getTotalString()
  {
    String str = "";
    if (this.aPk != null) {
      str = this.aPk.getCurrencyString();
    }
    return str;
  }
  
  public String getTotalStringNoSymbol()
  {
    String str = "";
    if (this.aPk != null) {
      str = this.aPk.getCurrencyStringNoSymbol();
    }
    return str;
  }
  
  public String getTotalStringNoSymbolNoComma()
  {
    String str = "";
    if (this.aPk != null) {
      str = this.aPk.getCurrencyStringNoSymbolNoComma();
    }
    return str;
  }
  
  public String getTotalStringAbsolute()
  {
    String str = "";
    if (this.aPk != null) {
      str = this.aPk.toStringAbsolute();
    }
    return str;
  }
  
  public Currency getTotalAmount()
  {
    return this.aPk;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.GetCheckRecordsAmountTotal
 * JD-Core Version:    0.7.0.1
 */