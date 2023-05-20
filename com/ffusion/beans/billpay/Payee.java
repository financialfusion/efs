package com.ffusion.beans.billpay;

import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class Payee
  extends PayeeI18N
{
  protected static final String BEAN_NAME = Payee.class.getName();
  protected String fiID;
  public static final String NICKNAME = "NICK_NAME";
  protected String nickName;
  protected String userAccountNumber;
  protected String hostID;
  protected String transactionID;
  protected int status;
  protected int daysToPay;
  protected String trackingID;
  protected PayeeRoute payeeRoute = null;
  protected HashMap i18nPayee = new HashMap();
  protected String contactName = null;
  public static final String defaultLanguage = "en_US";
  private String dy = "en_US";
  private String dx = "en_US";
  protected boolean noAccountNumber;
  
  public Payee()
  {
    this.error = 0;
  }
  
  public Payee(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void set(Payee paramPayee)
  {
    if ((this == paramPayee) || (paramPayee == null)) {
      return;
    }
    super.set(paramPayee);
    setID(paramPayee.getID());
    setName(paramPayee.getName());
    setNickName(paramPayee.getNickName());
    setHostID(paramPayee.getHostID());
    setUserAccountNumber(paramPayee.getUserAccountNumber());
    setTransactionID(paramPayee.getTransactionID());
    setStatus(paramPayee.getStatus());
    setTrackingID(paramPayee.getTrackingID());
    setDaysToPay(paramPayee.getDaysToPay());
    setFiID(paramPayee.getFiID());
    setContactName(paramPayee.getContactName());
    Object localObject;
    if (paramPayee.getPayeeRoute() != null)
    {
      localObject = new PayeeRoute();
      ((PayeeRoute)localObject).setRouteID(paramPayee.getPayeeRoute().getRouteID());
      ((PayeeRoute)localObject).setBankIdentifier(paramPayee.getPayeeRoute().getBankIdentifier());
      ((PayeeRoute)localObject).setAccountID(paramPayee.getPayeeRoute().getAccountID());
      ((PayeeRoute)localObject).setAcctType(paramPayee.getPayeeRoute().getAcctType());
      ((PayeeRoute)localObject).setCurrencyCode(paramPayee.getPayeeRoute().getCurrencyCode());
      ((PayeeRoute)localObject).setCustAcctRequired(paramPayee.getPayeeRoute().isCustAcctRequired());
      setPayeeRoute((PayeeRoute)localObject);
    }
    if (paramPayee.i18nPayee != null)
    {
      localObject = paramPayee.getLanguages();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        setName(str, paramPayee.getName(str));
      }
    }
    setCurrentLanguage(paramPayee.getSearchLanguage());
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Payee localPayee = (Payee)paramObject;
    int i;
    if (this == localPayee)
    {
      i = 0;
    }
    else
    {
      if (localPayee.getName() == null) {
        return 1;
      }
      if (this.name == null) {
        return -1;
      }
      i = this.name.compareTo(localPayee.getName());
      if (i == 0)
      {
        if ((this.nickName != null) && (localPayee.getNickName() != null)) {
          i = this.nickName.compareTo(localPayee.getNickName());
        }
        if ((i == 0) && (this.userAccountNumber != null) && (localPayee.getUserAccountNumber() != null)) {
          i = this.userAccountNumber.compareTo(localPayee.getUserAccountNumber());
        }
        if (i == 0) {
          i = this.id.compareTo(localPayee.getID());
        }
      }
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Payee localPayee = (Payee)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ID")) && (getID() != null) && (localPayee.getID() != null)) {
      i = localCollator.compare(getID(), localPayee.getID());
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localPayee.getName() != null)) {
      i = localCollator.compare(getName(), localPayee.getName());
    } else if ((paramString.equals("NICK_NAME")) && (getNickName() != null) && (localPayee.getNickName() != null)) {
      i = localCollator.compare(getNickName(), localPayee.getNickName());
    } else if ((paramString.equals("USERACCOUNTNUMBER")) && (getUserAccountNumber() != null) && (localPayee.getUserAccountNumber() != null)) {
      i = localCollator.compare(getUserAccountNumber(), localPayee.getUserAccountNumber());
    } else if (paramString.equals("STATUS")) {
      i = getStatus() - localPayee.getStatus();
    } else if (paramString.equals("DAYSTOPAY")) {
      i = getDaysToPayValue() - localPayee.getDaysToPayValue();
    } else if (paramString.equals("PREFERRED_LANG")) {
      i = localCollator.compare(getSearchLanguage(), localPayee.getSearchLanguage());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof Payee)) {
      return false;
    }
    Payee localPayee = (Payee)paramObject;
    if ((getID() != null) && (!getID().equals(localPayee.getID()))) {
      return false;
    }
    if ((getName() != null) && (!getName().equals(localPayee.getName()))) {
      return false;
    }
    if ((getNickName() != null) && (!getNickName().equals(localPayee.getNickName()))) {
      return false;
    }
    if (localPayee.getStatus() != getStatus()) {
      return false;
    }
    if ((getUserAccountNumber() != null) && (!getUserAccountNumber().equals(localPayee.getUserAccountNumber()))) {
      return false;
    }
    if (localPayee.getDaysToPayValue() != getDaysToPayValue()) {
      return false;
    }
    if ((getHostID() != null) && (!getHostID().equals(localPayee.getHostID()))) {
      return false;
    }
    return super.equals(paramObject);
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((str.equals("NAME")) && (getName() != null)) {
      return isFilterable(getName(), paramString2, paramString3);
    }
    if ((str.equals("NICK_NAME")) && (getNickName() != null)) {
      return isFilterable(getNickName(), paramString2, paramString3);
    }
    if ((str.equals("USERACCOUNTNUMBER")) && (getUserAccountNumber() != null)) {
      return isFilterable(getUserAccountNumber(), paramString2, paramString3);
    }
    if (str.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((str.equals("DAYSTOPAY")) && (getDaysToPay() != null)) {
      return isFilterable(getDaysToPay(), paramString2, paramString3);
    }
    if ((str.equals("HOSTID")) && (getHostID() != null)) {
      return isFilterable(getHostID(), paramString2, paramString3);
    }
    if ((str.equals("PREFERRED_LANG")) && (getSearchLanguage() != null)) {
      return isFilterable(getSearchLanguage(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setNickName(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.nickName = paramString;
  }
  
  public String getNickName()
  {
    if (this.nickName == null) {
      return this.name;
    }
    return this.nickName;
  }
  
  public void setHostID(String paramString)
  {
    this.hostID = paramString;
  }
  
  public String getHostID()
  {
    return this.hostID;
  }
  
  public void setUserAccountNumber(String paramString)
  {
    this.userAccountNumber = paramString;
    if ((paramString != null) && (paramString.compareTo("NA") == 0)) {
      this.noAccountNumber = true;
    }
  }
  
  public String getUserAccountNumber()
  {
    return this.userAccountNumber;
  }
  
  public void setTransactionID(String paramString)
  {
    this.transactionID = paramString;
  }
  
  public String getTransactionID()
  {
    return this.transactionID;
  }
  
  public void setStatus(int paramInt)
  {
    if ((paramInt >= 1) && (paramInt <= 3)) {
      this.status = paramInt;
    }
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setDaysToPay(String paramString)
  {
    try
    {
      this.daysToPay = Integer.parseInt(paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setNoAccountNumber(String paramString)
  {
    this.noAccountNumber = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getNoAccountNumber()
  {
    return "" + this.noAccountNumber;
  }
  
  public void setDaysToPay(int paramInt)
  {
    this.daysToPay = paramInt;
  }
  
  public String getDaysToPay()
  {
    return String.valueOf(this.daysToPay);
  }
  
  public int getDaysToPayValue()
  {
    return this.daysToPay;
  }
  
  public HashMap getPayeeI18NInfo()
  {
    return this.i18nPayee;
  }
  
  public void setPayeeI18NInfo(HashMap paramHashMap)
  {
    this.i18nPayee = paramHashMap;
  }
  
  public void setError(int paramInt)
  {
    this.error = paramInt;
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this.id = paramString2;
    } else if (paramString1.equals("NAME")) {
      setName(paramString2);
    } else if (paramString1.equals("NICK_NAME")) {
      setNickName(paramString2);
    } else if (paramString1.equals("USERACCOUNTNUMBER")) {
      this.userAccountNumber = paramString2;
    } else if (paramString1.equals("HOSTID")) {
      this.hostID = paramString2;
    } else if (paramString1.equals("TRANSACTIONID")) {
      this.transactionID = paramString2;
    } else if (paramString1.equals("STATUS")) {
      this.status = Integer.parseInt(paramString2);
    } else if (paramString1.equals("DAYSTOPAY")) {
      this.daysToPay = Integer.parseInt(paramString2);
    } else if (paramString1.equals("ERROR")) {
      this.error = Integer.parseInt(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PAYEE");
    XMLHandler.appendTag(localStringBuffer, "NICK_NAME", this.nickName);
    XMLHandler.appendTag(localStringBuffer, "USERACCOUNTNUMBER", this.userAccountNumber);
    XMLHandler.appendTag(localStringBuffer, "HOSTID", this.hostID);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONID", this.transactionID);
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "DAYSTOPAY", this.daysToPay);
    XMLHandler.appendTag(localStringBuffer, "ERROR", this.error);
    XMLHandler.appendTag(localStringBuffer, "TRACKINGID", this.trackingID);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PAYEE");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Payee paramPayee, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramPayee.getID(), getID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramPayee.getName(), getName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramPayee.getTrackingID(), getTrackingID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NICK_NAME", paramPayee.getNickName(), getNickName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERACCOUNTNUMBER", paramPayee.getUserAccountNumber(), getUserAccountNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "HOSTID", paramPayee.getHostID(), getHostID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRANSACTIONID", paramPayee.getTransactionID(), getTransactionID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", paramPayee.getStatus(), getStatus(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DAYSTOPAY", paramPayee.getDaysToPay(), getDaysToPay(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ERROR", paramPayee.getError(), getError(), paramString);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramPayee, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Payee paramPayee, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramPayee.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramPayee.getName(), getName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramPayee.getTrackingID(), getTrackingID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NICK_NAME", paramPayee.getNickName(), getNickName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERACCOUNTNUMBER", paramPayee.getUserAccountNumber(), getUserAccountNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "HOSTID", paramPayee.getHostID(), getHostID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRANSACTIONID", paramPayee.getTransactionID(), getTransactionID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", paramPayee.getStatus(), getStatus(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DAYSTOPAY", paramPayee.getDaysToPay(), getDaysToPay(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ERROR", paramPayee.getError(), getError(), paramILocalizable);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramPayee, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public String getFiID()
  {
    return this.fiID;
  }
  
  public void setFiID(String paramString)
  {
    this.fiID = paramString;
  }
  
  public PayeeRoute getPayeeRoute()
  {
    return this.payeeRoute;
  }
  
  public void setPayeeRoute(PayeeRoute paramPayeeRoute)
  {
    this.payeeRoute = paramPayeeRoute;
  }
  
  public String getCurrentLanguage()
  {
    return this.dx;
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this.dx = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.dy;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.dy = paramString;
  }
  
  public String getName()
  {
    if ((this.dx == null) || (this.dx.equals("en_US"))) {
      return this.name;
    }
    return getName(this.dx);
  }
  
  public String getName(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("en_US")) {
        return this.name;
      }
      if (this.i18nPayee == null) {
        return null;
      }
      PayeeI18N localPayeeI18N = (PayeeI18N)this.i18nPayee.get(paramString);
      if (localPayeeI18N == null) {
        return null;
      }
      return localPayeeI18N.getName();
    }
    return null;
  }
  
  public void setName(String paramString)
  {
    if (this.dx != null) {
      setName(this.dx, paramString);
    } else {
      super.setName(paramString);
    }
  }
  
  public void setName(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      if (paramString1.equalsIgnoreCase("en_US"))
      {
        super.setName(paramString2);
      }
      else
      {
        PayeeI18N localPayeeI18N;
        if (this.i18nPayee != null)
        {
          localPayeeI18N = (PayeeI18N)this.i18nPayee.get(paramString1);
          if (localPayeeI18N == null)
          {
            localPayeeI18N = new PayeeI18N();
            localPayeeI18N.setID(getID());
            localPayeeI18N.setName(paramString2);
            this.i18nPayee.put(paramString1, localPayeeI18N);
          }
          else
          {
            localPayeeI18N.setName(paramString2);
            this.i18nPayee.put(paramString1, localPayeeI18N);
          }
        }
        else
        {
          this.i18nPayee = new HashMap();
          localPayeeI18N = new PayeeI18N();
          localPayeeI18N.setID(getID());
          localPayeeI18N.setName(paramString2);
          this.i18nPayee.put(paramString1, localPayeeI18N);
        }
      }
    }
  }
  
  public Iterator getLanguages()
  {
    if (this.i18nPayee == null) {
      return null;
    }
    return this.i18nPayee.keySet().iterator();
  }
  
  public String getContactName()
  {
    return this.contactName;
  }
  
  public void setContactName(String paramString)
  {
    this.contactName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.Payee
 * JD-Core Version:    0.7.0.1
 */