package com.ffusion.beans.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.Frequencies;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableString;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RecTransfer
  extends Transfer
  implements Frequencies
{
  protected static final String BEAN_NAME = RecTransfer.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  public static final String KEY_TRANSFER_FREQUENCY_PREFIX = "RecTransferFrequencies";
  protected int numberTransfers;
  protected int frequency;
  
  public RecTransfer()
  {
    this.status = 1;
    this.funds_type = 2;
    this.transferType = "Repetitive";
  }
  
  public RecTransfer(Locale paramLocale)
  {
    super(paramLocale);
    this.status = 1;
    this.funds_type = 2;
    this.transferType = "Repetitive";
  }
  
  public void set(RecTransfer paramRecTransfer)
  {
    super.set(paramRecTransfer);
    this.numberTransfers = paramRecTransfer.getNumberTransfersValue();
    this.frequency = paramRecTransfer.getFrequencyValue();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    int i = 1;
    if ((paramObject instanceof RecTransfer))
    {
      RecTransfer localRecTransfer = (RecTransfer)paramObject;
      if (paramString.equals("NUMBERTRANSFERS")) {
        i = getNumberTransfersValue() - localRecTransfer.getNumberTransfersValue();
      } else if (paramString.equals("REMAININGTRANSFERS")) {
        i = getRemainingTransfersValue() - localRecTransfer.getRemainingTransfersValue();
      } else if (paramString.equals("FREQUENCY")) {
        i = getFrequencyValue() - localRecTransfer.getFrequencyValue();
      } else if (paramString.equals("FREQUENCYSTRING")) {
        i = getFrequency().compareTo(localRecTransfer.getFrequency());
      } else if ((paramString.equals("NEXTDATE")) && (this.date != null) && (localRecTransfer.getNextDateValue() != null)) {
        i = getNextDateValue().equals(localRecTransfer.getNextDateValue()) ? 0 : getNextDateValue().before(localRecTransfer.getNextDateValue()) ? -1 : 1;
      } else {
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
    if (paramString1.equals("NUMBERTRANSFERS")) {
      return isFilterable(getNumberTransfers(), paramString2, paramString3);
    }
    if (paramString1.equals("REMAININGTRANSFERS")) {
      return isFilterable(getRemainingTransfers(), paramString2, paramString3);
    }
    if (paramString1.equals("FREQUENCY")) {
      return isFilterable(getFrequency(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setNumberTransfers(String paramString)
  {
    try
    {
      setNumberTransfers(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setNumberTransfers(0);
    }
  }
  
  public void setNumberTransfers(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.numberTransfers = paramInt;
  }
  
  public int getNumberTransfersValue()
  {
    return this.numberTransfers;
  }
  
  public String getNumberTransfers()
  {
    return String.valueOf(this.numberTransfers);
  }
  
  public int getRemainingTransfersValue()
  {
    return getEstRemainingTransfers(new GregorianCalendar());
  }
  
  public String getRemainingTransfers()
  {
    return String.valueOf(getRemainingTransfersValue());
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
      String str = ResourceUtil.getString("RecTransferFrequencies" + i, "com.ffusion.beans.banking.resources", this.locale);
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
    return ResourceUtil.getString("RecTransferFrequencies" + paramInt, "com.ffusion.beans.banking.resources", paramLocale);
  }
  
  public Calendar getNextDateValue()
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    Object localObject = getDateValue();
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    int i = this.numberTransfers;
    localCalendar.set(localGregorianCalendar.get(1), localGregorianCalendar.get(2), localGregorianCalendar.get(5));
    while ((((Calendar)localObject).before(localCalendar)) && (i > 0))
    {
      localObject = getEstNextDate((Calendar)localObject);
      i--;
    }
    return localObject;
  }
  
  public String getNextDate()
  {
    DateTime localDateTime = new DateTime(getNextDateValue(), this.locale);
    localDateTime.setFormat(this.datetype);
    return localDateTime.toString();
  }
  
  public String getNextNextDate()
  {
    DateTime localDateTime = new DateTime(getEstNextDate(getNextDateValue()), this.locale);
    localDateTime.setFormat(this.datetype);
    return localDateTime.toString();
  }
  
  public int getApprovalSubType()
  {
    return 2;
  }
  
  public String getApprovalSubTypeName()
  {
    return "Recurring Account Transfer";
  }
  
  public Calendar getEstNextDate(Calendar paramCalendar)
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
  
  public int getEstRemainingTransfers(Calendar paramCalendar)
  {
    Object localObject = getDateValue();
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    int i = this.numberTransfers;
    localCalendar.set(paramCalendar.get(1), paramCalendar.get(2), paramCalendar.get(5));
    while ((((Calendar)localObject).before(localCalendar)) && (i > 0))
    {
      localObject = getEstNextDate((Calendar)localObject);
      i--;
    }
    return i;
  }
  
  public boolean set(String paramString1, String paramString2, Accounts paramAccounts)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("NUMBERTRANSFERS")) {
      this.numberTransfers = Integer.parseInt(paramString2);
    } else if (paramString1.equalsIgnoreCase("FREQUENCY")) {
      try
      {
        this.frequency = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        setFrequency(paramString2);
      }
    } else {
      bool = super.set(paramString1, paramString2, paramAccounts);
    }
    return bool;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    return set(paramString1, paramString2, null);
  }
  
  public void setRecTransferInfo(RecTransferInfo paramRecTransferInfo, Accounts paramAccounts)
  {
    if (paramRecTransferInfo == null) {
      return;
    }
    super.setTransferInfo(paramRecTransferInfo, paramAccounts);
    String str1 = getDateFormat();
    setDateFormat("yyyyMMdd");
    String str2 = paramRecTransferInfo.getStartDate();
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    setDate(str2);
    setDateFormat(str1);
    setNumberTransfers(paramRecTransferInfo.getPmtsCount());
  }
  
  public RecTransferInfo getRecTransferInfo()
  {
    return getRecTransferInfo(null);
  }
  
  public RecTransferInfo getRecTransferInfo(String paramString)
  {
    RecTransferInfo localRecTransferInfo = new RecTransferInfo();
    super.copyToTransferInfo(localRecTransferInfo);
    if ("INTERNAL".equals(this.transferDestination)) {
      populateInternalExtraInfo(localRecTransferInfo);
    } else if (paramString != null) {
      populateExtraInfo(localRecTransferInfo, paramString);
    }
    localRecTransferInfo.setPmtsCount(getNumberTransfersValue());
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    String str = "";
    if (getDateValue() != null) {
      str = localDateFormat.format(getDateValue().getTime());
    }
    localRecTransferInfo.setStartDate(str);
    return localRecTransferInfo;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "RECTRANSFER");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendTag(localStringBuffer, "NUMBERTRANSFERS", this.numberTransfers);
    XMLHandler.appendTag(localStringBuffer, "FREQUENCY", this.frequency);
    XMLHandler.appendEndTag(localStringBuffer, "RECTRANSFER");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, RecTransfer paramRecTransfer, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBERTRANSFERS", paramRecTransfer.getNumberTransfers(), getNumberTransfers(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "FREQUENCY", paramRecTransfer.getFrequency(), getFrequency(), paramString);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramRecTransfer, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, RecTransfer paramRecTransfer, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBERTRANSFERS", paramRecTransfer.getNumberTransfers(), getNumberTransfers(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "FREQUENCY", new LocalizableString("com.ffusion.beans.banking.resources", "RecTransferFrequencies" + paramRecTransfer.getFrequencyValue(), null), new LocalizableString("com.ffusion.beans.banking.resources", "RecTransferFrequencies" + getFrequencyValue(), null), paramILocalizable);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramRecTransfer, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  class a
    extends Transfer.a
  {
    public a(Accounts paramAccounts)
    {
      super(paramAccounts);
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      RecTransfer.this.set(getElement(), str, this.jdField_int);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.RecTransfer
 * JD-Core Version:    0.7.0.1
 */