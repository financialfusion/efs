package com.ffusion.csil.core;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Properties;

public class AlertAuditLogger
  extends Initialize
{
  public static final String ALERT_CHANNEL_ID = "1";
  private static final String aw9 = "com.ffusion.util.logging.audit.alertauditlogger";
  private static final String aww = "com.ffusion.beans.alerts.resources";
  private static final String aw1 = "Channel_";
  private static final String awG = "AuditMessage_1";
  private static final String axe = "AuditMessage_2";
  private static final String awn = "AuditMessage_3";
  private static final String awM = "AuditMessage_4";
  private static final String awz = "AuditMessage_5";
  private static final String awp = "AuditMessage_6";
  private static final String awW = "AuditMessage_7";
  private static final String axl = "AuditMessage_8";
  private static final String awB = "AuditMessage_9";
  private static final String awP = "AuditMessage_10";
  private static final String awd = "AuditMessage_11";
  private static final String awu = "AuditMessage_12";
  private static final String awj = "AuditMessage_13";
  private static final String awx = "AuditMessage_14";
  private static final String aw3 = "AuditMessage_15";
  private static final String av8 = "AuditMessage_16";
  private static final String awN = "AuditMessage_17";
  private static final String axg = "AuditMessage_18";
  private static final String awQ = "AuditMessage_19";
  private static final String awy = "AuditMessage_20";
  private static final String aw4 = "AuditMessage_21";
  private static final String av9 = "AuditMessage_22";
  private static final String aws = "AuditMessage_23";
  private static final String awS = "AuditMessage_24";
  private static final String aw6 = "AuditMessage_25";
  private static final String awI = "AuditMessage_26";
  private static final String awO = "AuditMessage_27";
  private static final String aw2 = "AuditMessage_28";
  private static final String awf = "AuditMessage_29";
  private static final String awE = "AuditMessage_30";
  private static final String axh = "AuditMessage_31";
  private static final String awe = "AuditMessage_32";
  private static final String axb = "AuditMessage_33";
  private static final String awi = "AuditMessage_34";
  private static final String awT = "AuditMessage_35";
  private static final String axq = "AuditMessage_36";
  private static final String awD = "AuditMessage_37";
  private static final String aw7 = "AuditMessage_38";
  private static final String aw8 = "AuditMessage_39";
  private static final String awq = "AuditMessage_40";
  private static final String axm = "AuditMessage_41";
  private static final String awr = "AuditMessage_42";
  private static final String awY = "AuditMessage_43";
  private static final String axi = "AuditMessage_44";
  private static final String awX = "AuditMessage_45";
  private static final String axk = "AuditMessage_46";
  private static final String awF = "AuditMessage_47";
  private static final String awl = "AuditMessage_48";
  private static final String awa = "AuditMessage_49";
  private static final String awb = "AuditMessage_50";
  private static final String aw5 = "AuditMessage_51";
  private static final String awh = "AuditMessage_52";
  private static final String axc = "AuditMessage_53";
  private static final String awk = "AuditMessage_54";
  private static final String awL = "AuditMessage_55";
  private static final String axd = "AuditMessage_56";
  private static final String awg = "AuditMessage_57";
  private static final String awH = "AuditMessage_58";
  private static final String awt = "AuditMessage_59";
  private static final String aw0 = "AuditMessage_60";
  private static final String awo = "AuditMessage_61";
  private static final String axp = "AuditMessage_62";
  private static final String awC = "AuditMessage_63";
  private static final String awA = "AuditMessage_64";
  private static final String awc = "AuditMessage_65";
  private static final String axj = "AuditMessage_66";
  private static final String awm = "AuditMessage_67";
  private static final String awU = "AuditMessage_68";
  private static final String awJ = "AuditMessage_69";
  private static final String axn = "AuditMessage_70";
  private static final String axf = "AuditMessage_71";
  private static final String awR = "AuditMessage_72";
  private static final String awv = "AuditMessage_73";
  private static final String axo = "AuditMessage_74";
  private static final String awZ = "AuditMessage_75";
  private static final String awV = "AuditMessage_76";
  private static final String awK = "AuditMessage_77";
  private static final String axa = "AuditMessage_78";
  
  public static int getAlertType(Alert paramAlert)
  {
    int i = -1;
    String str = null;
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    str = localProperties.getProperty("AlertType");
    if (str != null)
    {
      str = str.trim();
      if (str.equals("AccountBalance")) {
        i = 1;
      } else if (str.equals("StockPortfolio")) {
        i = 2;
      } else if (str.equals("BankMessage")) {
        i = 0;
      } else if (str.equals("NSF")) {
        i = 3;
      } else if (str.equals("PaymentApprovals")) {
        i = 5;
      } else if (str.equals("PositivePay")) {
        i = 6;
      } else if (str.equals("ProcessStockPortfolio")) {
        i = 7;
      } else if (str.equals("Transaction")) {
        i = 4;
      }
    }
    return i;
  }
  
  private static ILocalizable jdMethod_for(Alert paramAlert, boolean paramBoolean, int paramInt)
  {
    LocalizableList localLocalizableList = new LocalizableList();
    DeliveryInfos localDeliveryInfos = paramAlert.getDeliveryInfosValue();
    DeliveryInfo localDeliveryInfo = null;
    Object localObject;
    if (localDeliveryInfos != null)
    {
      localObject = localDeliveryInfos.iterator();
      if (localDeliveryInfos.size() <= 0) {
        return new LocalizableString("dummy", "", null);
      }
      int i = 1;
      while (((Iterator)localObject).hasNext())
      {
        localDeliveryInfo = (DeliveryInfo)((Iterator)localObject).next();
        if ((i != 0) && (paramBoolean))
        {
          i = 0;
        }
        else if (!localDeliveryInfo.getSuspendedValue())
        {
          localDeliveryInfo.setPropertyKey("to");
          String str = localDeliveryInfo.getProperty();
          LocalizableString localLocalizableString = null;
          if ((str == null) || (str.length() == 0))
          {
            localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "Channel_" + localDeliveryInfo.getChannelName(), null);
          }
          else
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = str;
            localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "Channel_" + localDeliveryInfo.getChannelName() + "_DATA", arrayOfObject2);
          }
          localLocalizableList.add(localLocalizableString);
        }
      }
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = localLocalizableList;
    if ((paramInt == 1502) || (!jdMethod_for(paramAlert))) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_59", arrayOfObject1);
    } else {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_5", arrayOfObject1);
    }
    return localObject;
  }
  
  public static Properties getAlertDeliveryInfoProperties(Alert paramAlert)
  {
    DeliveryInfos localDeliveryInfos = paramAlert.getDeliveryInfosValue();
    DeliveryInfo localDeliveryInfo = null;
    Properties localProperties = null;
    if (localDeliveryInfos != null)
    {
      localDeliveryInfo = (DeliveryInfo)localDeliveryInfos.get(0);
      if (localDeliveryInfo != null) {
        localProperties = localDeliveryInfo.getPropertiesValue();
      }
    }
    return localProperties;
  }
  
  protected static ILocalizable getOneTimeValue(String paramString)
  {
    LocalizableString localLocalizableString = null;
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    if (bool) {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_18", null);
    } else {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_19", null);
    }
    return localLocalizableString;
  }
  
  protected static ILocalizable getOneTimeValue(String paramString, Alert paramAlert, int paramInt)
  {
    LocalizableString localLocalizableString = null;
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    if ((jdMethod_for(paramAlert)) && (paramInt != 1502))
    {
      if (bool) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_18", null);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_19", null);
      }
    }
    else if (bool) {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_60", null);
    } else {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_61", null);
    }
    return localLocalizableString;
  }
  
  protected static ILocalizable getAccountBalanceLogMessage(Alert paramAlert, int paramInt)
  {
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    Object localObject = null;
    int i = 0;
    int j = 0;
    int k = !jdMethod_for(paramAlert) ? 1 : 0;
    if (localProperties == null) {
      return new LocalizableString("dummy", "", null);
    }
    String str1 = jdMethod_for(paramAlert, paramInt);
    String str2 = localProperties.getProperty("MAXAMOUNT");
    String str3 = localProperties.getProperty("MINAMOUNT");
    String str4 = localProperties.getProperty("ONETIME");
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject1 = null;
    Currency localCurrency1 = null;
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      localCurrency1 = new Currency(str3, paramAlert.getLocale());
      if (localCurrency1.getAmountValue().compareTo(new BigDecimal("0")) != 0) {
        i = 1;
      }
    }
    Currency localCurrency2 = null;
    if ((str2 != null) && (str2.trim().length() != 0))
    {
      localCurrency2 = new Currency(str2, paramAlert.getLocale());
      if (localCurrency2.getAmountValue().compareTo(new BigDecimal("0")) != 0) {
        j = 1;
      }
    }
    if (i == 0)
    {
      if (j == 0)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_24", arrayOfObject1);
      }
      else
      {
        arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = str2;
        if ((paramInt == 1502) || (k != 0)) {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_58", arrayOfObject1);
        } else {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_27", arrayOfObject1);
        }
      }
    }
    else if (j == 0)
    {
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = str3;
      if ((paramInt == 1502) || (k != 0)) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_57", arrayOfObject1);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_26", arrayOfObject1);
      }
    }
    else
    {
      arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = str3;
      arrayOfObject1[1] = str2;
      if ((paramInt == 1502) || (k != 0)) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_56", arrayOfObject1);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_25", arrayOfObject1);
      }
    }
    Object[] arrayOfObject2 = new Object[4];
    arrayOfObject2[0] = str1;
    arrayOfObject2[1] = localLocalizableString;
    arrayOfObject2[2] = getOneTimeValue(str4, paramAlert, paramInt);
    arrayOfObject2[3] = jdMethod_for(paramAlert, true, paramInt);
    if (paramInt == 1500) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_20", arrayOfObject2);
    } else if (paramInt == 1502) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_21", arrayOfObject2);
    } else if (paramInt == 1501) {
      localObject = jdMethod_for(k == 0, "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_22", "AuditMessage_73", arrayOfObject2);
    } else {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_23", arrayOfObject2);
    }
    return localObject;
  }
  
  protected static ILocalizable getNsfLogMessage(Alert paramAlert, int paramInt)
  {
    Object localObject = null;
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    String str1 = jdMethod_for(paramAlert, paramInt);
    String str2 = localProperties.getProperty("ONETIME");
    Object[] arrayOfObject;
    if ((str1 == null) || (str1.equals("")))
    {
      arrayOfObject = new Object[2];
      arrayOfObject[0] = getOneTimeValue(str2, paramAlert, paramInt);
      arrayOfObject[1] = jdMethod_for(paramAlert, true, paramInt);
      if (paramInt == 1500) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_74", arrayOfObject);
      } else if (paramInt == 1502) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_75", arrayOfObject);
      } else if (paramInt == 1501) {
        localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_76", "AuditMessage_77", arrayOfObject);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_78", arrayOfObject);
      }
    }
    else
    {
      arrayOfObject = new Object[3];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = getOneTimeValue(str2, paramAlert, paramInt);
      arrayOfObject[2] = jdMethod_for(paramAlert, true, paramInt);
      if (paramInt == 1500) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_14", arrayOfObject);
      } else if (paramInt == 1502) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_15", arrayOfObject);
      } else if (paramInt == 1501) {
        localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_16", "AuditMessage_62", arrayOfObject);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_17", arrayOfObject);
      }
    }
    return localObject;
  }
  
  protected static ILocalizable getPaymentApprovalLogMessage(Alert paramAlert, int paramInt)
  {
    Object localObject = null;
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    String str = localProperties.getProperty("ONETIME");
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getOneTimeValue(str, paramAlert, paramInt);
    arrayOfObject[1] = jdMethod_for(paramAlert, true, paramInt);
    if (paramInt == 1500) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_10", arrayOfObject);
    } else if (paramInt == 1502) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_11", arrayOfObject);
    } else if (paramInt == 1501) {
      localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_12", "AuditMessage_63", arrayOfObject);
    } else {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_13", arrayOfObject);
    }
    return localObject;
  }
  
  protected static ILocalizable getPostivePayLogMessage(Alert paramAlert, int paramInt)
  {
    Object localObject = null;
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    String str = localProperties.getProperty("ONETIME");
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getOneTimeValue(str, paramAlert, paramInt);
    arrayOfObject[1] = jdMethod_for(paramAlert, true, paramInt);
    if (paramInt == 1500) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_6", arrayOfObject);
    } else if (paramInt == 1502) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_7", arrayOfObject);
    } else if (paramInt == 1501) {
      localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_8", "AuditMessage_64", arrayOfObject);
    } else {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_9", arrayOfObject);
    }
    return localObject;
  }
  
  protected static ILocalizable getStockPortfolioLogMessage(Alert paramAlert, int paramInt)
  {
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    Object localObject = null;
    if (localProperties == null) {
      return new LocalizableString("dummy", "", null);
    }
    String str1 = localProperties.getProperty("Symbol");
    String str2 = localProperties.getProperty("Criteria");
    String str3 = localProperties.getProperty("Amount");
    String str4 = localProperties.getProperty("Onetime");
    if (str1 == null) {
      str1 = jdMethod_int(paramAlert);
    }
    Object[] arrayOfObject = null;
    if ((str3 != null) && (str3.trim().length() > 0) && (!str2.trim().equalsIgnoreCase("ANY AMOUNT")))
    {
      arrayOfObject = new Object[5];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = str3;
      arrayOfObject[2] = getOneTimeValue(str4, paramAlert, paramInt);
      arrayOfObject[3] = jdMethod_for(paramAlert, true, paramInt);
      boolean bool = str2.trim().equalsIgnoreCase("More Than");
      if (paramInt == 1500)
      {
        if (bool) {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_40", arrayOfObject);
        } else {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_52", arrayOfObject);
        }
      }
      else if (paramInt == 1502)
      {
        if (bool) {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_41", arrayOfObject);
        } else {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_53", arrayOfObject);
        }
      }
      else if (paramInt == 1501)
      {
        if (bool) {
          localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_42", "AuditMessage_65", arrayOfObject);
        } else {
          localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_54", "AuditMessage_66", arrayOfObject);
        }
      }
      else if (bool) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_43", arrayOfObject);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_55", arrayOfObject);
      }
    }
    else
    {
      arrayOfObject = new Object[4];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = getOneTimeValue(str4, paramAlert, paramInt);
      arrayOfObject[2] = jdMethod_for(paramAlert, true, paramInt);
      if (paramInt == 1500) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_36", arrayOfObject);
      } else if (paramInt == 1502) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_37", arrayOfObject);
      } else if (paramInt == 1501) {
        localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_38", "AuditMessage_67", arrayOfObject);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_39", arrayOfObject);
      }
    }
    return localObject;
  }
  
  protected static ILocalizable getTransactionLogMessage(Alert paramAlert, int paramInt)
  {
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    Object localObject = null;
    if (localProperties == null) {
      return new LocalizableString("dummy", "", null);
    }
    String str1 = jdMethod_for(paramAlert, paramInt);
    String str2 = localProperties.getProperty("Amount");
    String str3 = localProperties.getProperty("Criteria");
    String str4 = localProperties.getProperty("ONETIME");
    String str5 = localProperties.getProperty("TransactionType");
    Object[] arrayOfObject = null;
    if ((str2 != null) && (str2.trim().length() > 0) && (!str3.trim().equalsIgnoreCase("ANY AMOUNT")))
    {
      arrayOfObject = new Object[5];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = str5;
      arrayOfObject[2] = str2;
      arrayOfObject[3] = getOneTimeValue(str4, paramAlert, paramInt);
      arrayOfObject[4] = jdMethod_for(paramAlert, true, paramInt);
      boolean bool1 = str3.trim().equalsIgnoreCase("More Than");
      boolean bool2 = str3.trim().equalsIgnoreCase("Less Than");
      if (paramInt == 1500)
      {
        if (bool1) {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_32", arrayOfObject);
        } else if (bool2) {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_44", arrayOfObject);
        } else {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_48", arrayOfObject);
        }
      }
      else if (paramInt == 1502)
      {
        if (bool1) {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_33", arrayOfObject);
        } else if (bool2) {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_45", arrayOfObject);
        } else {
          localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_49", arrayOfObject);
        }
      }
      else if (paramInt == 1501)
      {
        if (bool1) {
          localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_34", "AuditMessage_68", arrayOfObject);
        } else if (bool2) {
          localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_46", "AuditMessage_69", arrayOfObject);
        } else {
          localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_50", "AuditMessage_70", arrayOfObject);
        }
      }
      else if (bool1) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_35", arrayOfObject);
      } else if (bool2) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_47", arrayOfObject);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_51", arrayOfObject);
      }
    }
    else
    {
      arrayOfObject = new Object[4];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = str5;
      arrayOfObject[2] = getOneTimeValue(str4, paramAlert, paramInt);
      arrayOfObject[3] = jdMethod_for(paramAlert, true, paramInt);
      if (paramInt == 1500) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_28", arrayOfObject);
      } else if (paramInt == 1502) {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_29", arrayOfObject);
      } else if (paramInt == 1501) {
        localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_30", "AuditMessage_71", arrayOfObject);
      } else {
        localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_31", arrayOfObject);
      }
    }
    return localObject;
  }
  
  protected static ILocalizable getProcessStockPortfolioLogMessage(Alert paramAlert, int paramInt)
  {
    return getStockPortfolioLogMessage(paramAlert, paramInt);
  }
  
  protected static ILocalizable getBankMessageLogMessage(Alert paramAlert, int paramInt)
  {
    Object localObject = null;
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    String str = localProperties.getProperty("ONETIME");
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getOneTimeValue(str, paramAlert, paramInt);
    arrayOfObject[1] = jdMethod_for(paramAlert, true, paramInt);
    if (paramInt == 1500) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_1", arrayOfObject);
    } else if (paramInt == 1502) {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_2", arrayOfObject);
    } else if (paramInt == 1501) {
      localObject = jdMethod_for(jdMethod_for(paramAlert), "com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_3", "AuditMessage_72", arrayOfObject);
    } else {
      localObject = new LocalizableString("com.ffusion.util.logging.audit.alertauditlogger", "AuditMessage_4", arrayOfObject);
    }
    return localObject;
  }
  
  public static void log(SecureUser paramSecureUser, Alert paramAlert, String paramString, int paramInt)
  {
    if ((paramAlert != null) && (paramAlert.getType() != null))
    {
      int i = getAlertType(paramAlert);
      if (i == 1) {
        audit(paramSecureUser, getAccountBalanceLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 3) {
        audit(paramSecureUser, getNsfLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 5) {
        audit(paramSecureUser, getPaymentApprovalLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 6) {
        audit(paramSecureUser, getPostivePayLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 2) {
        audit(paramSecureUser, getStockPortfolioLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 4) {
        audit(paramSecureUser, getTransactionLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 7) {
        audit(paramSecureUser, getProcessStockPortfolioLogMessage(paramAlert, paramInt), paramString, paramInt);
      } else if (i == 0) {
        audit(paramSecureUser, getBankMessageLogMessage(paramAlert, paramInt), paramString, paramInt);
      }
    }
  }
  
  private static String jdMethod_int(Alert paramAlert)
  {
    AlertStocks localAlertStocks = paramAlert.getStocks();
    StringBuffer localStringBuffer = new StringBuffer("");
    String str1 = "";
    if (localAlertStocks != null)
    {
      Iterator localIterator = localAlertStocks.iterator();
      while (localIterator.hasNext())
      {
        AlertStock localAlertStock = (AlertStock)localIterator.next();
        String str2 = localAlertStock.getSymbol();
        if (localStringBuffer.indexOf(str2) == -1)
        {
          localStringBuffer.append(str1 + localAlertStock.getSymbol());
          str1 = ", ";
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  private static ILocalizable jdMethod_for(boolean paramBoolean, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
  {
    LocalizableString localLocalizableString;
    if (paramBoolean) {
      localLocalizableString = new LocalizableString(paramString1, paramString2, paramArrayOfObject);
    } else {
      localLocalizableString = new LocalizableString(paramString1, paramString3, paramArrayOfObject);
    }
    return localLocalizableString;
  }
  
  private static boolean jdMethod_for(Alert paramAlert)
  {
    DeliveryInfos localDeliveryInfos = paramAlert.getDeliveryInfosValue();
    DeliveryInfo localDeliveryInfo = localDeliveryInfos.getByID("1");
    return !localDeliveryInfo.getSuspendedValue();
  }
  
  private static String jdMethod_for(Alert paramAlert, int paramInt)
  {
    Properties localProperties = getAlertDeliveryInfoProperties(paramAlert);
    if (localProperties == null) {
      return "";
    }
    String str1 = localProperties.getProperty("REALACCOUNTNUMBER");
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = localProperties.getProperty("ACCOUNTNUMBER");
    }
    try
    {
      str1 = AccountUtil.buildAccountDisplayText(str1, paramAlert.getLocale());
    }
    catch (UtilException localUtilException)
    {
      if (paramInt == 1500) {
        DebugLog.throwing("Error while constructing account display string for audit log when an alert was added.", localUtilException);
      } else if (paramInt == 1502) {
        DebugLog.throwing("Error while constructing account display string for audit log when an alert was deleted.", localUtilException);
      } else if (paramInt == 1501) {
        DebugLog.throwing("Error while constructing account display string for audit log when an alert was modified.", localUtilException);
      } else {
        DebugLog.throwing("Error while constructing account display string for audit log when an alert was ignored or no action has been taken.", localUtilException);
      }
    }
    String str2 = localProperties.getProperty("ROUTINGNUMBER");
    String str3;
    if (str2 == null)
    {
      if (str1 == null) {
        str3 = "";
      } else {
        str3 = str1;
      }
    }
    else if (str1 == null) {
      str3 = str2;
    } else {
      str3 = str2 + ":" + str1;
    }
    return str3;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.AlertAuditLogger
 * JD-Core Version:    0.7.0.1
 */