package com.ffusion.tasks.util;

import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetEntitlementObjectID
  extends BaseTask
{
  public static final String OBJECT_TYPE_ACCOUNT = "Account";
  public static final String KEY_ACCOUNT_ID = "AccountID";
  public static final String KEY_BANK_ID = "BankID";
  public static final String KEY_ROUTING_NUMBER = "RoutingNumber";
  private HashMap QM = new HashMap();
  private String QN = null;
  private String QO;
  private String QL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    if ((this.QO == null) || (this.QO.length() <= 0))
    {
      this.error = 84;
      return super.getTaskErrorURL();
    }
    if (this.QO.equals("Account"))
    {
      int i = D();
      if (i != 0)
      {
        this.error = i;
        return super.getTaskErrorURL();
      }
      this.QO = null;
      this.QM.clear();
    }
    else
    {
      this.error = 84;
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setCurrentPropertyName(String paramString)
  {
    this.QN = paramString;
  }
  
  public void setCurrentPropertyValue(String paramString)
  {
    if ((this.QN != null) && (paramString != null)) {
      this.QM.put(this.QN, paramString);
    }
  }
  
  public String getCurrentPropertyValue()
  {
    return (String)this.QM.get(this.QN);
  }
  
  public void setObjectType(String paramString)
  {
    this.QO = paramString;
  }
  
  public String getObjectID()
  {
    return this.QL;
  }
  
  private int D()
  {
    String str1 = (String)this.QM.get("AccountID");
    String str2 = (String)this.QM.get("BankID");
    String str3 = (String)this.QM.get("RoutingNumber");
    if ((str1 == null) || (str1.length() <= 0)) {
      return 50;
    }
    if ((str3 == null) || (str3.length() <= 0)) {
      return 88;
    }
    Account localAccount = new Account();
    localAccount.setID(str1);
    localAccount.setRoutingNum(str3);
    localAccount.setBankID(str2);
    this.QL = EntitlementsUtil.getEntitlementObjectId(localAccount);
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetEntitlementObjectID
 * JD-Core Version:    0.7.0.1
 */