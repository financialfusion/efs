package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageConsts;
import com.ffusion.beans.messages.GlobalMessageRecipient;
import com.ffusion.beans.messages.GlobalMessageRecipients;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.messages.CreateGlobalMessage;
import com.ffusion.tasks.messages.ModifyGlobalMessage;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GlobalMessageRecipientsFormHandler
  extends BaseTask
  implements GlobalMessageConsts
{
  public static final String CURRENT_GLOBAL_MESSAGE = "CurrentGlobalMessage";
  public static final String COMMAND = "cmd";
  public static final String PARAMETER = "param";
  public static final String USERS_FOR_GLOBAL_MESSAGE = "UsersForGlobalMessage";
  public static final int C_SEARCH_BUSINESS = 1;
  public static final int C_SEARCH_CONSUMER = 2;
  public static final int C_DELETE_RECIPIENT = 3;
  public static final int C_CONTINUE = 4;
  public static final int C_CANCEL = 5;
  public static final int C_ADD_BUSINESS = 6;
  public static final int C_ADD_CONSUMER = 7;
  public static final String P_BUSINESS = "businessRcpts";
  public static final String P_CONSUMER = "consumerRcpts";
  public static final String P_BUSINESS_NAME = "businessName";
  public static final String P_BUSINESS_CUSTID = "businessCustID";
  public static final String P_CONSUMER_NAME = "consumerName";
  public static final String P_CONSUMER_CUSTID = "consumerCustID";
  public static final String SEARCHCRITERIA = "SEARCHCRITERIA";
  protected String _currentGlobalMessageSessionName = "CurrentGlobalMessage";
  protected String _businessesSessionName = "BusinessesSearchList";
  protected String _consumersSessionName = "UsersForGlobalMessage";
  protected GlobalMessage _gm;
  protected String _continueURL;
  protected String _init;
  protected GlobalMessageRecipients _tmpRecipients;
  protected Businesses _busList;
  protected Users _conList;
  public String _validate = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = getSuccessURL();
    if (!jdMethod_for(paramHttpServletRequest)) {
      return this.taskErrorURL;
    }
    Object localObject = localHttpSession.getAttribute(this._currentGlobalMessageSessionName);
    if ((localObject instanceof CreateGlobalMessage)) {
      this._gm = ((CreateGlobalMessage)localObject).getGlobalMessage();
    } else if ((localObject instanceof ModifyGlobalMessage)) {
      this._gm = ((ModifyGlobalMessage)localObject).getGlobalMessage();
    } else if ((localObject instanceof GlobalMessage)) {
      this._gm = ((GlobalMessage)localObject);
    }
    if (this._gm == null) {
      throw new IOException();
    }
    this._busList = ((Businesses)localHttpSession.getAttribute(this._businessesSessionName));
    this._conList = ((Users)localHttpSession.getAttribute(this._consumersSessionName));
    if ((this._init == null) || (this._init.equals("true")))
    {
      this._tmpRecipients = new GlobalMessageRecipients();
      if (this._gm.get("_recipients") != null) {
        this._tmpRecipients.addAll((GlobalMessageRecipients)this._gm.get("_recipients"));
      }
      this._init = "false";
    }
    String str2 = paramHttpServletRequest.getParameter("cmd");
    String str3 = paramHttpServletRequest.getParameter("param");
    if (str2 != null) {
      switch (Integer.parseInt(str2))
      {
      case 3: 
        delete_recipient(str3);
        break;
      case 4: 
        str1 = getContinueURL();
        break;
      case 5: 
        resetFilters();
        str1 = getContinueURL();
        break;
      case 6: 
        addBusinessRecipients(paramHttpServletRequest.getParameterValues("businessRcpts"));
        break;
      case 7: 
        addConsumerRecipients(paramHttpServletRequest.getParameterValues("consumerRcpts"));
        break;
      case 1: 
        localHttpSession.setAttribute(this._businessesSessionName, searchBusiness(paramHttpServletRequest.getParameter("businessName"), paramHttpServletRequest.getParameter("businessCustID"), (SecureUser)localHttpSession.getAttribute("SecureUser")));
        break;
      case 2: 
        localHttpSession.setAttribute(this._consumersSessionName, searchConsumer(paramHttpServletRequest.getParameter("consumerName"), paramHttpServletRequest.getParameter("consumerCustID"), (SecureUser)localHttpSession.getAttribute("SecureUser")));
      }
    }
    return str1;
  }
  
  protected void delete_recipient(String paramString)
  {
    GlobalMessageRecipients localGlobalMessageRecipients = (GlobalMessageRecipients)this._gm.get("_recipients");
    if (localGlobalMessageRecipients == null) {
      return;
    }
    Iterator localIterator = localGlobalMessageRecipients.iterator();
    while (localIterator.hasNext()) {
      if (((GlobalMessageRecipient)localIterator.next()).getRcptDirID() == Integer.parseInt(paramString)) {
        localIterator.remove();
      }
    }
    this._gm.put("_recipients", localGlobalMessageRecipients);
    this._gm.put("FILTERS_RECIPIENTS_MODIFIED_KEY", new Boolean("true"));
  }
  
  protected void addBusinessRecipients(String[] paramArrayOfString)
  {
    com.ffusion.beans.business.Business localBusiness = null;
    GlobalMessageRecipients localGlobalMessageRecipients = (GlobalMessageRecipients)this._gm.get("_recipients");
    if (localGlobalMessageRecipients == null) {
      localGlobalMessageRecipients = new GlobalMessageRecipients();
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      GlobalMessageRecipient localGlobalMessageRecipient = new GlobalMessageRecipient();
      localBusiness = this._busList.getById(Integer.parseInt(paramArrayOfString[i]));
      localGlobalMessageRecipient.setCustomerID((String)localBusiness.get("CUST_ID"));
      localGlobalMessageRecipient.setCustomerName(localBusiness.getBusinessName());
      localGlobalMessageRecipient.setRcptDirID(localBusiness.getIdValue());
      localGlobalMessageRecipient.setAffiliateBankID(localBusiness.getAffiliateBankID());
      localGlobalMessageRecipient.setRcptType(2);
      localGlobalMessageRecipients.add(localGlobalMessageRecipient);
    }
    this._gm.put("_recipients", localGlobalMessageRecipients);
    this._gm.put("FILTERS_RECIPIENTS_MODIFIED_KEY", new Boolean("true"));
  }
  
  protected void addConsumerRecipients(String[] paramArrayOfString)
  {
    User localUser = null;
    GlobalMessageRecipients localGlobalMessageRecipients = (GlobalMessageRecipients)this._gm.get("_recipients");
    if (localGlobalMessageRecipients == null) {
      localGlobalMessageRecipients = new GlobalMessageRecipients();
    }
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      GlobalMessageRecipient localGlobalMessageRecipient = new GlobalMessageRecipient();
      localUser = this._conList.getByID(paramArrayOfString[i]);
      localGlobalMessageRecipient.setCustomerID(localUser.getCustId());
      localGlobalMessageRecipient.setCustomerName(localUser.getSortableFullNameWithLoginId());
      localGlobalMessageRecipient.setRcptDirID(localUser.getIdValue());
      localGlobalMessageRecipient.setAffiliateBankID(localUser.getAffiliateBankID());
      localGlobalMessageRecipient.setRcptType(1);
      localGlobalMessageRecipients.add(localGlobalMessageRecipient);
    }
    this._gm.put("_recipients", localGlobalMessageRecipients);
    this._gm.put("FILTERS_RECIPIENTS_MODIFIED_KEY", new Boolean("true"));
  }
  
  protected void resetFilters()
  {
    this._gm.put("_recipients", this._tmpRecipients);
  }
  
  protected Businesses searchBusiness(String paramString1, String paramString2, SecureUser paramSecureUser)
  {
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business();
    localBusiness.setBankId(paramSecureUser.getBankID());
    if ((this._gm.getAffiliateBankId() != null) && (!this._gm.getAffiliateBankId().equals("0"))) {
      localBusiness.setAffiliateBankID(this._gm.getAffiliateBankId());
    }
    if ((paramString2 != null) && (!paramString2.equals(""))) {
      localBusiness.setCustId(paramString2);
    }
    if ((paramString1 != null) && (!paramString1.equals(""))) {
      localBusiness.setBusinessName(paramString1);
    }
    try
    {
      return com.ffusion.csil.core.Business.getFilteredBusinesses(paramSecureUser, localBusiness, null, null);
    }
    catch (CSILException localCSILException) {}
    return new Businesses();
  }
  
  protected Users searchConsumer(String paramString1, String paramString2, SecureUser paramSecureUser)
  {
    User localUser = new User();
    localUser.setBankId(paramSecureUser.getBankID());
    if ((this._gm.getAffiliateBankId() != null) && (!this._gm.getAffiliateBankId().equals("0"))) {
      localUser.setAffiliateBankID(this._gm.getAffiliateBankId());
    }
    if ((paramString2 != null) && (!paramString2.equals(""))) {
      localUser.setCustId(paramString2);
    }
    if ((paramString1 != null) && (!paramString1.equals(""))) {
      localUser.setLastName(paramString1);
    }
    try
    {
      return UserAdmin.getConsumers(paramSecureUser, localUser, null);
    }
    catch (CSILException localCSILException) {}
    return new Users(paramSecureUser.getLocale());
  }
  
  private boolean jdMethod_for(HttpServletRequest paramHttpServletRequest)
  {
    if (this._validate != null)
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      if (this._validate.indexOf("SEARCHCRITERIA") != -1)
      {
        String str = (String)localHttpSession.getAttribute("cmd");
        switch (Integer.parseInt(str))
        {
        case 6: 
          String[] arrayOfString1 = (String[])paramHttpServletRequest.getParameterValues("businessRcpts");
          if ((arrayOfString1 == null) || (arrayOfString1.length == 0)) {
            this.error = 8071;
          }
          break;
        case 7: 
          String[] arrayOfString2 = (String[])paramHttpServletRequest.getParameterValues("consumerRcpts");
          if ((arrayOfString2 == null) || (arrayOfString2.length == 0)) {
            this.error = 8072;
          }
          break;
        }
      }
      this._validate = null;
    }
    return this.error == 0;
  }
  
  public String getBusinessesSessionName()
  {
    return this._businessesSessionName;
  }
  
  public void setBusinessesSessionName(String paramString)
  {
    this._businessesSessionName = paramString;
  }
  
  public String getContinueURL()
  {
    return this._continueURL;
  }
  
  public void setContinueURL(String paramString)
  {
    this._continueURL = paramString;
  }
  
  public String getCurrentGlobalMessageSessionName()
  {
    return this._currentGlobalMessageSessionName;
  }
  
  public void setCurrentGlobalMessageSessionName(String paramString)
  {
    this._currentGlobalMessageSessionName = paramString;
  }
  
  public GlobalMessage getGm()
  {
    return this._gm;
  }
  
  public void setGm(GlobalMessage paramGlobalMessage)
  {
    this._gm = paramGlobalMessage;
  }
  
  public String getInit()
  {
    return this._init;
  }
  
  public void setInit(String paramString)
  {
    this._init = paramString;
  }
  
  public String getValidate()
  {
    return this._validate;
  }
  
  public void setValidate(String paramString)
  {
    this._validate = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GlobalMessageRecipientsFormHandler
 * JD-Core Version:    0.7.0.1
 */