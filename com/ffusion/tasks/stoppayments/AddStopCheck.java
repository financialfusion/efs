package com.ffusion.tasks.stoppayments;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddStopCheck
  extends StopCheck
  implements Task
{
  protected int error = 0;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String accountsCollection = "Accounts";
  protected Boolean currentlyProcessing = Boolean.FALSE;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected static long timeoutValue = 120000L;
  protected String fromCheckNumber = null;
  protected String toCheckNumber = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.initFlag)
    {
      str = initProcess(localHttpSession);
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
    }
    else
    {
      str = addStopCheck(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.currentlyProcessing = Boolean.FALSE;
    this.haveProcessed = false;
    this.checkNumbers = "";
    return this.successURL;
  }
  
  protected String addStopCheck(HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        int i = 0;
        synchronized (this)
        {
          if ((!this.currentlyProcessing.booleanValue()) && (!this.haveProcessed))
          {
            this.currentlyProcessing = Boolean.TRUE;
            i = 1;
          }
        }
        if (i != 0)
        {
          try
          {
            str = doProcess(paramHttpSession);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            this.nextURL = this.serviceErrorURL;
          }
          finally
          {
            this.currentlyProcessing = Boolean.FALSE;
          }
        }
        else
        {
          long l = System.currentTimeMillis();
          while ((!this.haveProcessed) && (this.currentlyProcessing.booleanValue() == true))
          {
            if (System.currentTimeMillis() - l > timeoutValue)
            {
              if (this.error != 0) {
                break;
              }
              this.error = 1;
              break;
            }
            try
            {
              Thread.sleep(2000L);
            }
            catch (Exception localException2) {}
          }
          str = this.nextURL;
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    boolean bool = true;
    com.ffusion.services.Stops localStops = (com.ffusion.services.Stops)paramHttpSession.getAttribute("com.ffusion.services.Stops");
    bool = processAddStopCheck(localStops, paramHttpSession);
    if (bool) {
      this.haveProcessed = true;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean processAddStopCheck(com.ffusion.services.Stops paramStops, HttpSession paramHttpSession)
  {
    StopCheck localStopCheck = new StopCheck(this.locale);
    localStopCheck.set(this);
    try
    {
      HashMap localHashMap = new HashMap(1);
      if (paramStops != null) {
        localHashMap.put("SERVICE", paramStops);
      }
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      localStopCheck = com.ffusion.csil.core.Stops.addStopPayment(localSecureUser, localStopCheck, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute("StopCheck", localStopCheck);
    }
    return this.error == 0;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((bool) && (this.validate.indexOf("ACCOUNTID") != -1)) {
        bool = validateAccount(paramHttpSession);
      }
      if ((bool) && (this.validate.indexOf("CHECKNUMBERS") != -1)) {
        if (containsCheckNumberRange(paramHttpSession))
        {
          bool = validateCheckNumbers(paramHttpSession);
          if (bool) {
            bool = validateCheckDate(paramHttpSession);
          }
        }
        else
        {
          bool = validateCheckNumbers(paramHttpSession);
          if ((bool) && (this.validate.indexOf("PAYEENAME") != -1)) {
            bool = validatePayeeName(paramHttpSession);
          }
          if ((bool) && (this.validate.indexOf("AMOUNT") != -1)) {
            bool = validateAmount(paramHttpSession);
          }
          if ((bool) && (this.validate.indexOf("CHECKDATE") != -1)) {
            bool = validateCheckDate(paramHttpSession);
          }
        }
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateAccount(HttpSession paramHttpSession)
  {
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsCollection);
    if (localAccounts != null)
    {
      Account localAccount = localAccounts.getByID(this.accountID);
      if (localAccount != null)
      {
        setAccount(localAccount);
        paramHttpSession.setAttribute("Account", localAccount);
      }
      else
      {
        paramHttpSession.removeAttribute("Account");
        this.error = 13012;
      }
    }
    else
    {
      this.error = 13011;
    }
    return this.error == 0;
  }
  
  protected boolean validatePayeeName(HttpSession paramHttpSession)
  {
    if ((this.payeeName == null) || (this.payeeName.trim().length() == 0))
    {
      this.payeeName = null;
      this.error = 13016;
    }
    return this.error == 0;
  }
  
  protected boolean validateAmount(HttpSession paramHttpSession)
  {
    if (this.amount == null) {
      this.error = 13013;
    }
    return this.error == 0;
  }
  
  protected boolean validateCheckDate(HttpSession paramHttpSession)
  {
    if (this.checkDate == null)
    {
      this.checkDate = null;
      this.error = 13014;
    }
    return this.error == 0;
  }
  
  protected static boolean hasDigits(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    try
    {
      for (int i = 0;; i++)
      {
        char c = arrayOfChar[i];
        if (Character.isDigit(c)) {
          return true;
        }
      }
      return false;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
  }
  
  protected boolean containsCheckNumberRange(HttpSession paramHttpSession)
  {
    if (this.checkNumbers.length() == 0) {
      return false;
    }
    int i = 0;
    if (((i = this.checkNumbers.indexOf(" ")) > 0) && (i < this.checkNumbers.length() - 1) && (hasDigits(this.checkNumbers.substring(i + 1)))) {
      return true;
    }
    if (((i = this.checkNumbers.indexOf(",")) > 0) && (i < this.checkNumbers.length() - 1) && (hasDigits(this.checkNumbers.substring(i + 1)))) {
      return true;
    }
    return ((i = this.checkNumbers.indexOf("-")) > 0) && (i < this.checkNumbers.length() - 1) && (hasDigits(this.checkNumbers.substring(i + 1)));
  }
  
  protected boolean validateCheckNumbers(HttpSession paramHttpSession)
  {
    int i = this.checkNumbers.length();
    if (i == 0)
    {
      this.error = 13015;
    }
    else
    {
      char[] arrayOfChar = this.checkNumbers.toCharArray();
      int j = 0;
      int k = 0;
      char c;
      try
      {
        for (int m = 0;; m++)
        {
          c = arrayOfChar[m];
          if (Character.isDigit(c))
          {
            j = 1;
          }
          else if (c == '-')
          {
            k++;
          }
          else
          {
            this.error = 13015;
            break;
          }
        }
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
      if (k > 1) {
        this.error = 13015;
      } else if ((k == 1) && ((arrayOfChar[0] == '-') || (arrayOfChar[(i - 1)] == '-'))) {
        this.error = 13015;
      }
      if (j == 0) {
        this.error = 13015;
      }
      if ((this.error == 0) && (k == 1))
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(this.checkNumbers, "-");
        try
        {
          c = '\000';
          int i1 = 0;
          int n;
          if (localStringTokenizer.hasMoreTokens()) {
            n = Integer.parseInt(localStringTokenizer.nextToken());
          }
          if (localStringTokenizer.hasMoreTokens()) {
            i1 = Integer.parseInt(localStringTokenizer.nextToken());
          }
          if (n >= i1) {
            this.error = 13015;
          }
        }
        catch (Exception localException)
        {
          this.error = 13015;
        }
      }
    }
    return this.error == 0;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setAccountsCollection(String paramString)
  {
    this.accountsCollection = paramString;
  }
  
  public String getAccountsCollection()
  {
    return this.accountsCollection;
  }
  
  public void setFromCheckNumber(String paramString)
  {
    this.fromCheckNumber = paramString;
  }
  
  public void setToCheckNumber(String paramString)
  {
    this.toCheckNumber = paramString;
  }
  
  public void setCheckNumbers(String paramString)
  {
    int i = paramString.indexOf("-");
    this.checkNumbers = paramString;
    if (i != -1)
    {
      this.fromCheckNumber = paramString.substring(0, i);
      this.toCheckNumber = paramString.substring(i + 1);
    }
  }
  
  public String getFromCheckNumber()
  {
    return this.fromCheckNumber;
  }
  
  public String getToCheckNumber()
  {
    return this.toCheckNumber;
  }
  
  public void setAccountID(String paramString)
  {
    super.setAccountID(paramString);
    this.account = null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.AddStopCheck
 * JD-Core Version:    0.7.0.1
 */