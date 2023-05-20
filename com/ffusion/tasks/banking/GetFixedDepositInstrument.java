package com.ffusion.tasks.banking;

import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFixedDepositInstrument
  extends BaseTask
  implements Task
{
  String ew;
  String eu = "FixedDepositInstruments";
  String ev = "FixedDepositInstrument";
  String es = null;
  String et = null;
  
  public void setInstrumentsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.eu = "FixedDepositInstruments";
    } else {
      this.eu = paramString;
    }
  }
  
  public String getInstrumentsName()
  {
    return this.eu;
  }
  
  public void setInstrumentName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.ev = "FixedDepositInstrument";
    } else {
      this.ev = paramString;
    }
  }
  
  public String getInstrumentName()
  {
    return this.ev;
  }
  
  public void setInstrumentNumber(String paramString)
  {
    this.es = paramString;
  }
  
  public String getInstrumentNumber()
  {
    return this.es;
  }
  
  public void setInstrumentBankName(String paramString)
  {
    this.et = paramString;
  }
  
  public String getInstrumentBankName()
  {
    return this.et;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FixedDepositInstruments localFixedDepositInstruments = (FixedDepositInstruments)localHttpSession.getAttribute(this.eu);
    if (localFixedDepositInstruments == null)
    {
      this.error = 51;
    }
    else
    {
      Object localObject = null;
      if ((this.es != null) && (this.et != null)) {
        for (int i = 0; i < localFixedDepositInstruments.size(); i++)
        {
          FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)localFixedDepositInstruments.get(i);
          if ((this.es.equals(localFixedDepositInstrument.getInstrumentNumber())) && (this.et.equals(localFixedDepositInstrument.getInstrumentBankName())))
          {
            localObject = localFixedDepositInstrument;
            break;
          }
        }
      }
      localHttpSession.setAttribute(this.ev, localObject);
      str = this.successURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetFixedDepositInstrument
 * JD-Core Version:    0.7.0.1
 */