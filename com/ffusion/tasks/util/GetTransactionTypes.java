package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.util.KeyValue;
import com.ffusion.beans.util.KeyValueList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactionTypes
  extends BaseTask
{
  public static final String SORTFIELD_TYPECODE = "typeCode";
  public static final String SORTFIELD_DESCRIPTION = "typeDesc";
  public static final String APPLICATION_ALL = Integer.toString(0);
  public static final String APPLICATION_CORPORATE = Integer.toString(2);
  public static final String APPLICATION_CONSUMER = Integer.toString(1);
  private boolean Qo = false;
  private HashMap Ql = null;
  private String Qp = null;
  private String Qn = "Value";
  private String Qj = "TransactionTypesList";
  private String Qm = APPLICATION_ALL;
  private Locale Qk = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    KeyValueList localKeyValueList = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    this.Qk = localSecureUser.getLocale();
    this.error = 0;
    int i = 0;
    try
    {
      if ((this.Qm == null) || (this.Qm.length() <= 0)) {
        i = 0;
      } else {
        i = Integer.parseInt(this.Qm);
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
      return super.getTaskErrorURL();
    }
    try
    {
      this.Ql = Banking.getTransactionTypes(i, new HashMap());
      if (this.Qo)
      {
        localKeyValueList = getTypeList();
        if (this.error == 0) {
          localHttpSession.setAttribute(this.Qj, localKeyValueList);
        } else {
          return this.taskErrorURL;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setTypeCode(String paramString)
  {
    this.Qp = paramString;
  }
  
  public void setSortField(String paramString)
  {
    if ("typeCode".equals(paramString)) {
      this.Qn = "Key";
    } else if ("typeDesc".equals(paramString)) {
      this.Qn = "Value";
    }
  }
  
  public String getDescription()
  {
    if ((this.Qp == null) || (this.Ql == null))
    {
      this.error = 63;
      return "";
    }
    Integer localInteger = null;
    try
    {
      localInteger = new Integer(this.Qp);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
      return "";
    }
    TransactionTypeInfo localTransactionTypeInfo = (TransactionTypeInfo)this.Ql.get(localInteger);
    String str = localTransactionTypeInfo == null ? null : localTransactionTypeInfo.getDescription(this.Qk);
    if (str == null) {
      return "";
    }
    return str;
  }
  
  public HashMap getMap()
  {
    if (this.Ql == null)
    {
      this.error = 63;
      return new HashMap();
    }
    return this.Ql;
  }
  
  public KeyValueList getTypeList()
  {
    if (this.Ql == null)
    {
      this.error = 63;
      return new KeyValueList();
    }
    KeyValueList localKeyValueList = new KeyValueList();
    Iterator localIterator = this.Ql.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Integer localInteger = (Integer)localEntry.getKey();
      TransactionTypeInfo localTransactionTypeInfo = (TransactionTypeInfo)localEntry.getValue();
      String str = localTransactionTypeInfo.getDescription(this.Qk);
      localKeyValueList.add(new KeyValue(localInteger, str));
    }
    localKeyValueList.setSortedBy(this.Qn);
    return localKeyValueList;
  }
  
  public void setAddListToSession(String paramString)
  {
    this.Qo = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setTransactionTypesListName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.Qj = paramString.trim();
    } else {
      this.Qj = "TransactionTypesList";
    }
  }
  
  public String getTransactionTypesListName()
  {
    return this.Qj;
  }
  
  public String getApplication()
  {
    if ((this.Qm == null) || (this.Qm.length() <= 0)) {
      return Integer.toString(0);
    }
    return this.Qm;
  }
  
  public void setApplication(String paramString)
  {
    this.Qm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetTransactionTypes
 * JD-Core Version:    0.7.0.1
 */