package com.ffusion.tasks.ach;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditACHPayee
  extends ACHPayee
  implements Task
{
  protected int error = 0;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  private boolean z9 = false;
  protected ACHPayee originalPayee = null;
  protected String _PayeeID = null;
  protected String payeeSessionName = "ACHPayee";
  protected String payeeCollectionName = "ACHPayees";
  protected int maxUserAccountNumberLength;
  protected int maxNameLength = 35;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute(this.payeeCollectionName);
    String str = null;
    if (localACHPayees == null)
    {
      this.error = 16150;
      str = this.taskErrorURL;
    }
    else if (this.initFlag)
    {
      initProcess(localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = validateInput(localHttpSession, localACHPayees);
      if (this.error == 0)
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          if (!this.z9)
          {
            this.z9 = true;
            this.error = doProcess(localHttpSession, localACHPayees);
            if (this.error == 0) {
              str = this.successURL;
            } else {
              str = this.serviceErrorURL;
            }
            this.z9 = false;
          }
          else
          {
            this.error = 16500;
            str = this.taskErrorURL;
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
    }
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    ACHPayee localACHPayee = null;
    ACHPayees localACHPayees = (ACHPayees)paramHttpSession.getAttribute(this.payeeCollectionName);
    if (this._PayeeID != null) {
      localACHPayee = localACHPayees.getByID(this._PayeeID);
    }
    if (localACHPayee == null) {
      localACHPayee = (ACHPayee)paramHttpSession.getAttribute(this.payeeSessionName);
    }
    if (localACHPayee == null)
    {
      this.error = 16151;
    }
    else
    {
      set(localACHPayee);
      this.originalPayee = localACHPayee;
    }
  }
  
  protected int validateInput(HttpSession paramHttpSession, ACHPayees paramACHPayees)
  {
    int i = 0;
    String str = getNickName();
    if ((str == null) || (str.length() == 0))
    {
      str = getName();
      setNickName(str);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if ((getSecurePayeeValue() == true) && (getSubmittedBy() != null) && (!getSubmittedBy().equals("" + localSecureUser.getProfileID())))
    {
      i = 16180;
    }
    else if (this.validate != null)
    {
      if (this.validate.indexOf("ROUTINGNUM") != -1)
      {
        FinancialInstitution localFinancialInstitution = new FinancialInstitution();
        FinancialInstitutions localFinancialInstitutions = null;
        try
        {
          localFinancialInstitution.setAchRoutingNumber(this.routingNumber);
          localFinancialInstitutions = ACH.getFinancialInstitutions(localSecureUser, localFinancialInstitution, 5, new HashMap());
        }
        catch (Exception localException) {}
        if ((localFinancialInstitutions != null) && (localFinancialInstitutions.size() > 0))
        {
          if (((getBankName() == null) || (getBankName().trim().length() == 0)) && (localFinancialInstitutions.size() == 1)) {
            setBankName(((FinancialInstitution)localFinancialInstitutions.get(0)).getInstitutionName());
          }
        }
        else {
          i = 16183;
        }
      }
      if (i == 0) {
        if ((this.validate.indexOf("NAME") != -1) && ((this.name == null) || (this.name.length() == 0) || (this.name.length() > this.maxNameLength))) {
          i = 16102;
        } else if ((this.validate.indexOf("ROUTINGNUM") != -1) && ((this.routingNumber == null) || (this.routingNumber.length() != 9) || (!RoutingNumberUtil.isValidRoutingNumber(this.routingNumber, true)))) {
          i = 16101;
        } else if ((this.validate.indexOf("BANKNAME") != -1) && ((this.bankName == null) || (this.bankName.length() == 0))) {
          i = 16100;
        } else if ((this.validate.indexOf("DESCRIPTION") != -1) && ((this.description == null) || (this.description.length() == 0))) {
          i = 16106;
        } else if ((this.validate.indexOf("ACCOUNTNUMBER") != -1) && ((this.accountNumber == null) || (this.accountNumber.length() == 0))) {
          i = 16104;
        } else if ((this.validate.indexOf("ACCOUNTNUMBER") != -1) && (!Strings.isValidAccountNumber(this.accountNumber, localSecureUser.getLocale()))) {
          i = 136;
        } else if ((this.validate.indexOf("IDENTIFICATIONNUMBER") != -1) && ((this.userAccountNumber == null) || (this.userAccountNumber.length() == 0))) {
          i = 16533;
        } else if ((this.validate.indexOf("IDENTIFICATIONNUMBER") != -1) && (!Strings.isValidAccountNumber(this.userAccountNumber, localSecureUser.getLocale()))) {
          i = 16534;
        }
      }
      if ((this.validate.indexOf("NAME") != -1) && (i == 0) && (paramACHPayees.isPayeeUnique(this) != null)) {
        i = 16103;
      }
      this.validate = null;
    }
    return i;
  }
  
  protected int doProcess(HttpSession paramHttpSession, ACHPayees paramACHPayees)
  {
    int i = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      ACH.modifyACHPayee(localSecureUser, this, this.originalPayee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      i = MapError.mapError(localCSILException, paramHttpSession);
    }
    if ((i == 0) && (this.originalPayee != null)) {
      this.originalPayee.set(this);
    }
    return i;
  }
  
  public void setMaxUserAccountNumberLength(String paramString)
  {
    this.maxUserAccountNumberLength = Integer.valueOf(paramString).intValue();
  }
  
  public void setMaxNameLength(String paramString)
  {
    this.maxNameLength = Integer.valueOf(paramString).intValue();
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
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setPayeeID(String paramString)
  {
    this._PayeeID = null;
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this._PayeeID = paramString;
    }
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
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
  
  public String getMaxUserAccountNumberLength()
  {
    return "" + this.maxUserAccountNumberLength;
  }
  
  public String getMaxNameLength()
  {
    return "" + this.maxNameLength;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.EditACHPayee
 * JD-Core Version:    0.7.0.1
 */