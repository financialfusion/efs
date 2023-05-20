package com.ffusion.tasks.approvals;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.csil.core.CurrencyUtil;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApprovalsLevel
  extends BaseTask
  implements IApprovalsTask
{
  protected String _levelType = null;
  protected String _businessID = null;
  protected String _minAmount = null;
  protected String _maxAmount = null;
  protected String _levelID = null;
  protected String _operationType = null;
  protected boolean _isNewLevel = false;
  protected int _objectType = 1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    int i = 0;
    ApprovalsLevel localApprovalsLevel = null;
    if (validateInput(localHttpSession)) {
      try
      {
        Object localObject;
        if ((!this._isNewLevel) && (this._levelID != null))
        {
          localObject = (ApprovalsLevels)localHttpSession.getAttribute("ApprovalsLevels");
          int j = 0;
          int k = Integer.valueOf(this._levelID).intValue();
          while (i == 0)
          {
            localApprovalsLevel = (ApprovalsLevel)((ApprovalsLevels)localObject).get(j);
            if (localApprovalsLevel.getLevelID() == k) {
              i = 1;
            }
            j++;
            if (j == ((ApprovalsLevels)localObject).size()) {
              i = 1;
            }
          }
        }
        else if (this._isNewLevel)
        {
          localApprovalsLevel = new ApprovalsLevel(Locale.getDefault());
          if (localSecureUser.getUserType() == 1)
          {
            localApprovalsLevel.setBusinessID(localSecureUser.getBusinessID());
            localApprovalsLevel.setLevelType("Business_defined");
          }
          else if (localSecureUser.getUserType() == 2)
          {
            localApprovalsLevel.setBusinessID(Integer.parseInt(this._businessID));
            localApprovalsLevel.setLevelType(this._levelType);
          }
          localObject = CurrencyUtil.getCurrencyCodeByBusinessId(localApprovalsLevel.getBusinessID());
          if (this._minAmount != null) {
            localApprovalsLevel.setMinAmount(new Currency(this._minAmount, (String)localObject, Locale.getDefault()));
          }
          if (this._maxAmount != null) {
            localApprovalsLevel.setMaxAmount(new Currency(this._maxAmount, (String)localObject, Locale.getDefault()));
          }
          localApprovalsLevel.setOperationType(this._operationType);
          localApprovalsLevel.setObjectType(this._objectType);
        }
        localHttpSession.removeAttribute("ApprovalsLevel");
        if (localApprovalsLevel != null) {
          localHttpSession.setAttribute("ApprovalsLevel", localApprovalsLevel);
        }
      }
      catch (Exception localException)
      {
        str = this.taskErrorURL;
      }
    } else {
      return this.taskErrorURL;
    }
    return str;
  }
  
  public void setMinAmount(String paramString)
  {
    this._minAmount = paramString;
  }
  
  public void setMaxAmount(String paramString)
  {
    this._maxAmount = paramString;
  }
  
  public void setLevelID(String paramString)
  {
    this._levelID = paramString;
  }
  
  public void setNewLevel(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this._isNewLevel = true;
    }
  }
  
  public void setOperationType(String paramString)
  {
    this._operationType = paramString;
  }
  
  public void setLevelType(String paramString)
  {
    this._levelType = paramString;
  }
  
  public String getLevelType()
  {
    return this._levelType;
  }
  
  public void setBusinessID(String paramString)
  {
    this._businessID = paramString;
  }
  
  public String getBusinessID()
  {
    return this._businessID;
  }
  
  public void setObjectType(String paramString)
  {
    this._objectType = Integer.parseInt(paramString);
  }
  
  public String getObjectType()
  {
    return String.valueOf(this._objectType);
  }
  
  public boolean validateInput(HttpSession paramHttpSession)
    throws IOException
  {
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    if (this._isNewLevel)
    {
      try
      {
        if (this._minAmount == null) {
          localBigDecimal1 = new BigDecimal(0.0D);
        } else {
          localBigDecimal1 = new BigDecimal(this._minAmount);
        }
      }
      catch (Exception localException1)
      {
        this.error = 31010;
        return false;
      }
      try
      {
        if (this._maxAmount == null) {
          localBigDecimal2 = new BigDecimal(1.0D);
        } else {
          localBigDecimal2 = new BigDecimal(this._maxAmount);
        }
      }
      catch (Exception localException2)
      {
        this.error = 31011;
        return false;
      }
      if (localBigDecimal1.compareTo(new BigDecimal(0.0D)) < 0)
      {
        this.error = 31012;
        return false;
      }
      if (localBigDecimal2.compareTo(new BigDecimal(0.0D)) <= 0)
      {
        this.error = 31013;
        return false;
      }
      if (localBigDecimal2.compareTo(localBigDecimal1) <= 0)
      {
        this.error = 31014;
        return false;
      }
    }
    else
    {
      i = 0;
      try
      {
        if (this._levelID == null) {
          i = 999;
        } else {
          i = Integer.valueOf(this._levelID).intValue();
        }
      }
      catch (Exception localException3)
      {
        this.error = 31015;
        return false;
      }
      if (i < 0)
      {
        this.error = 31015;
        return false;
      }
    }
    int i = 0;
    try
    {
      if (this._businessID == null) {
        i = 999;
      } else {
        i = Integer.valueOf(this._businessID).intValue();
      }
    }
    catch (Exception localException4)
    {
      this.error = 31016;
      return false;
    }
    if (i < 0)
    {
      this.error = 31016;
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetApprovalsLevel
 * JD-Core Version:    0.7.0.1
 */