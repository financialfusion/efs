package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFDInstrumentTransactions
  extends BaseTask
  implements Task
{
  DateTime yK;
  DateTime yM;
  String yN;
  String yP = "FixedDepositInstrument";
  String yO = "Transactions";
  String yL = "P";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setDataClassification(String paramString)
  {
    this.yL = paramString;
  }
  
  public String getDataClassification()
  {
    return this.yL;
  }
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0) && (paramString.length() <= 10))
      {
        this.yK = new DateTime(paramString, null, this.yN);
        if (this.yK != null)
        {
          this.yK.set(11, 0);
          this.yK.set(12, 0);
          this.yK.set(13, 0);
        }
      }
      else
      {
        this.yK = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStartDate()
  {
    if (this.yK == null) {
      return null;
    }
    return this.yK.toString();
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0) && (paramString.length() <= 10))
      {
        this.yM = new DateTime(paramString, null, this.yN);
        if (this.yM != null)
        {
          this.yM.set(11, 23);
          this.yM.set(12, 59);
          this.yM.set(13, 59);
        }
      }
      else
      {
        this.yM = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getEndDate()
  {
    if (this.yM == null) {
      return null;
    }
    return this.yM.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.yN = paramString;
  }
  
  public String getDateFormat()
  {
    return this.yN;
  }
  
  public void setInstrumentName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.yP = "FixedDepositInstrument";
    } else {
      this.yP = paramString;
    }
  }
  
  public String getInstrumentName()
  {
    return this.yP;
  }
  
  public void setTransactionsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.yO = "Transactions";
    } else {
      this.yO = paramString;
    }
  }
  
  public String getTransactionsName()
  {
    return this.yO;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)localHttpSession.getAttribute(this.yP);
    if (localFixedDepositInstrument == null) {
      this.error = 1040;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking3);
        localHashMap.put("DATA_CLASSIFICATION", this.yL);
        Transactions localTransactions = Banking.getFDInstrumentTransactions(localSecureUser, localFixedDepositInstrument, this.yK, this.yM, localHashMap);
        if (localTransactions != null) {
          localTransactions.setDateFormat(this.yN);
        }
        localHttpSession.setAttribute(this.yO, localTransactions);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetFDInstrumentTransactions
 * JD-Core Version:    0.7.0.1
 */