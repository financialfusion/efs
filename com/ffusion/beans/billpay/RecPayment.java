package com.ffusion.beans.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.Frequencies;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableString;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RecPayment
  extends Payment
  implements Frequencies
{
  protected static final String BEAN_NAME = RecPayment.class.getName();
  public static final String KEY_PAYMENT_FREQUENCY_PREFIX = "RecPaymentFrequencies";
  protected int numberPayments;
  protected int frequency = 4;
  
  protected RecPayment()
  {
    this.status = 1;
    this.funds_type = 4;
  }
  
  protected RecPayment(String paramString)
  {
    super(paramString);
    this.status = 1;
    this.funds_type = 4;
  }
  
  protected RecPayment(Locale paramLocale)
  {
    super(paramLocale);
    this.status = 1;
    this.funds_type = 4;
  }
  
  public void set(RecPayment paramRecPayment)
  {
    super.set(paramRecPayment);
    this.numberPayments = paramRecPayment.getNumberPaymentsValue();
    this.frequency = paramRecPayment.getFrequencyValue();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    int i = 1;
    if ((paramObject instanceof RecPayment))
    {
      RecPayment localRecPayment = (RecPayment)paramObject;
      if (paramString.equals("NUMBERPAYMENTS"))
      {
        i = getNumberPaymentsValue() - localRecPayment.getNumberPaymentsValue();
      }
      else if (paramString.equals("REMAININGPAYMENTS"))
      {
        i = getRemainingPaymentsValue() - localRecPayment.getRemainingPaymentsValue();
      }
      else if (paramString.equals("FREQUENCY"))
      {
        i = getFrequencyValue() - localRecPayment.getFrequencyValue();
      }
      else if (paramString.equals("FREQUENCYSTRING"))
      {
        i = getFrequency().compareTo(localRecPayment.getFrequency());
      }
      else if (paramString.equals("NEXTPAYDATE"))
      {
        Calendar localCalendar1 = getNextPayDateValue();
        Calendar localCalendar2 = localRecPayment.getNextPayDateValue();
        if ((localCalendar1 != null) && (localCalendar2 != null)) {
          i = localCalendar1.equals(localCalendar2) ? 0 : localCalendar1.before(localCalendar2) ? -1 : 1;
        }
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("NUMBERPAYMENTS")) {
      return isFilterable(getNumberPayments(), paramString2, paramString3);
    }
    if (paramString1.equals("REMAININGPAYMENTS")) {
      return isFilterable(getRemainingPayments(), paramString2, paramString3);
    }
    if (paramString1.equals("FREQUENCY")) {
      return isFilterable(getFrequency(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setNumberPayments(String paramString)
  {
    try
    {
      setNumberPayments(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setNumberPayments(0);
    }
  }
  
  public void setNumberPayments(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.numberPayments = paramInt;
  }
  
  public int getNumberPaymentsValue()
  {
    return this.numberPayments;
  }
  
  public String getNumberPayments()
  {
    return String.valueOf(this.numberPayments);
  }
  
  public int getRemainingPaymentsValue()
  {
    return getEstRemainingPayments(new GregorianCalendar());
  }
  
  public String getRemainingPayments()
  {
    return String.valueOf(getRemainingPaymentsValue());
  }
  
  public void setFrequencyValue(String paramString)
  {
    try
    {
      setFrequency(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setFrequency(4);
    }
  }
  
  public void setFrequency(String paramString)
  {
    for (int i = 0; i < 10; i++)
    {
      String str = ResourceUtil.getString("RecPaymentFrequencies" + i, "com.ffusion.beans.billpay.resources", this.locale);
      if (str.equalsIgnoreCase(paramString))
      {
        setFrequency(i);
        break;
      }
    }
  }
  
  public void setFrequency(int paramInt)
  {
    this.frequency = paramInt;
  }
  
  public int getFrequencyValue()
  {
    return this.frequency;
  }
  
  public String getFrequency()
  {
    return getFrequency(this.frequency, this.locale);
  }
  
  public static String getFrequency(int paramInt, Locale paramLocale)
  {
    return ResourceUtil.getString("RecPaymentFrequencies" + paramInt, "com.ffusion.beans.billpay.resources", paramLocale);
  }
  
  public Calendar getNextPayDateValue()
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    Object localObject = getPayDateValue();
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    int i = this.numberPayments;
    localCalendar.set(localGregorianCalendar.get(1), localGregorianCalendar.get(2), localGregorianCalendar.get(5));
    while ((((Calendar)localObject).before(localCalendar)) && (i > 0))
    {
      localObject = getEstNextPayDate((Calendar)localObject);
      i--;
    }
    return localObject;
  }
  
  public String getNextPayDate()
  {
    DateTime localDateTime = new DateTime(getNextPayDateValue(), this.locale);
    localDateTime.setFormat(this.datetype);
    return localDateTime.toString();
  }
  
  protected Calendar getEstNextPayDate(Calendar paramCalendar)
  {
    Calendar localCalendar = (Calendar)paramCalendar.clone();
    switch (this.frequency)
    {
    case 1: 
      localCalendar.add(5, 7);
      break;
    case 2: 
      localCalendar.add(5, 14);
      break;
    case 5: 
      localCalendar.add(5, 28);
      break;
    case 4: 
      localCalendar.add(2, 1);
      break;
    case 6: 
      localCalendar.add(2, 2);
      break;
    case 7: 
      localCalendar.add(2, 3);
      break;
    case 9: 
      localCalendar.add(1, 1);
      break;
    case 8: 
      localCalendar.add(2, 6);
      break;
    case 3: 
      int i = localCalendar.get(5);
      if (i == 16)
      {
        localCalendar.set(5, 1);
        localCalendar.add(2, 1);
      }
      else if (i == 1)
      {
        localCalendar.set(5, 16);
      }
      else
      {
        localCalendar.add(5, 15);
      }
      break;
    }
    return localCalendar;
  }
  
  public int getEstRemainingPayments(Calendar paramCalendar)
  {
    Object localObject = getPayDateValue();
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    int i = this.numberPayments;
    localCalendar.set(paramCalendar.get(1), paramCalendar.get(2), paramCalendar.get(5));
    while ((((Calendar)localObject).before(localCalendar)) && (i > 0))
    {
      localObject = getEstNextPayDate((Calendar)localObject);
      i--;
    }
    return i;
  }
  
  public int getApprovalSubType()
  {
    return 4;
  }
  
  public String getApprovalSubTypeName()
  {
    return "Recurring Payment";
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    set(paramString1, paramString2, null, null);
    return true;
  }
  
  public void set(String paramString1, String paramString2, Accounts paramAccounts, Payees paramPayees)
  {
    if (paramString1.equals("NUMBERPAYMENTS")) {
      this.numberPayments = Integer.parseInt(paramString2);
    } else if (paramString1.equals("FREQUENCY")) {
      try
      {
        this.frequency = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        setFrequency(paramString2);
      }
    } else {
      super.set(paramString1, paramString2, paramAccounts, paramPayees);
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null, null));
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "RECPAYMENT");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendTag(localStringBuffer, "NUMBERPAYMENTS", this.numberPayments);
    XMLHandler.appendTag(localStringBuffer, "FREQUENCY", this.frequency);
    XMLHandler.appendEndTag(localStringBuffer, "RECPAYMENT");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, RecPayment paramRecPayment, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBERPAYMENTS", paramRecPayment.getNumberPayments(), getNumberPayments(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "FREQUENCY", paramRecPayment.getFrequency(), getFrequency(), paramString);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramRecPayment, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, RecPayment paramRecPayment, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBERPAYMENTS", paramRecPayment.getNumberPayments(), getNumberPayments(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "FREQUENCY", new LocalizableString("com.ffusion.beans.billpay.resources", "RecPaymentFrequencies" + paramRecPayment.getFrequencyValue(), null), new LocalizableString("com.ffusion.beans.billpay.resources", "RecPaymentFrequencies" + getFrequencyValue(), null), paramILocalizable);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramRecPayment, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  class a
    extends Payment.a
  {
    public a(Accounts paramAccounts, Payees paramPayees)
    {
      super(paramAccounts, paramPayees);
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      RecPayment.this.set(getElement(), str, this.jdField_new, this.jdField_int);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.RecPayment
 * JD-Core Version:    0.7.0.1
 */