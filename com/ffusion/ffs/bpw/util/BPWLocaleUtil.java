package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditAdapter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BPWLocaleUtil
{
  private static boolean jdField_new = false;
  private static final String h = "curcodes";
  private static final String e = "ofxmessages";
  private static final String jdField_int = "servermessages";
  private static final String jdField_else = "wiremessages";
  private static final String g = "transfermessages";
  private static final String jdField_goto = "apimessages";
  private static final String jdField_byte = "booktransferauditlogmessages";
  private static final String jdField_void = "externaltransferauditlogmessages";
  private static final String jdField_case = "billpayauditlogmessages";
  private static final String c = "paymentmessages";
  private static ResourceBundle jdField_char = null;
  private static ResourceBundle d = null;
  private static ResourceBundle a = null;
  private static ResourceBundle b = null;
  private static ResourceBundle k = null;
  private static ResourceBundle jdField_try = null;
  private static ResourceBundle f = null;
  private static ResourceBundle i = null;
  private static ResourceBundle jdField_do = null;
  private static ResourceBundle jdField_if = null;
  private static final String jdField_null = "LogLanguages.xml";
  private static MessageFormat jdField_for = null;
  private static String j = null;
  private static String jdField_long = null;
  public static final String OFX_MESSAGE = "OFX_MESSAGE";
  public static final String SERVER_MESSAGE = "SERVER_MESSAGE";
  public static final String WIRE_MESSAGE = "WIRE_MESSAGE";
  public static final String TRANSFER_MESSAGE = "TRANSFER_MESSAGE";
  public static final String API_MESSAGE = "API_MESSAGE";
  public static final String BOOKTRANSFER_AUDITLOG_MESSAGE = "BOOKTRANSFER_AUDITLOG_MESSAGE";
  public static final String EXTERNALTRANSFER_AUDITLOG_MESSAGE = "EXTERNALTRANSFER_AUDITLOG_MESSAGE";
  public static final String BILLPAY_AUDITLOG_MESSAGE = "BILLPAY_AUDITLOG_MESSAGE";
  public static final String PAYMENT_MESSAGE = "PAYMENT_MESSAGE";
  public static final String LOG_LANGUAGE = "LOG_LANGUAGE";
  public static final String LOG_LANGUAGE_LIST = "LOG_LANGUAGE_LIST";
  
  private static synchronized void a()
    throws BPWException
  {
    if (!jdField_new) {
      try
      {
        Locale localLocale = Locale.getDefault();
        BPWConfigWrapper localBPWConfigWrapper = (BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER");
        ArrayList localArrayList = null;
        try
        {
          localArrayList = localBPWConfigWrapper.getLogLanguages();
        }
        catch (FFSException localFFSException)
        {
          throw new BPWException(localFFSException, "BPWLocaleUtil, unable to parse LogLanguages.xml");
        }
        HashMap localHashMap1 = new HashMap();
        HashMap localHashMap2 = new HashMap();
        localHashMap1.put("LOG_LANGUAGE", localArrayList);
        localHashMap2.put("LOG_LANGUAGE_LIST", localHashMap1);
        AuditAdapter.setLogLanguages(localHashMap2);
        j = localLocale.getLanguage() + "_" + localLocale.getCountry();
        jdField_char = ResourceBundle.getBundle("curcodes", localLocale);
        d = ResourceBundle.getBundle("ofxmessages", localLocale);
        a = ResourceBundle.getBundle("servermessages", localLocale);
        b = ResourceBundle.getBundle("wiremessages", localLocale);
        jdField_try = ResourceBundle.getBundle("apimessages", localLocale);
        k = ResourceBundle.getBundle("transfermessages", localLocale);
        f = ResourceBundle.getBundle("booktransferauditlogmessages", localLocale);
        i = ResourceBundle.getBundle("externaltransferauditlogmessages", localLocale);
        jdField_do = ResourceBundle.getBundle("billpayauditlogmessages", localLocale);
        jdField_if = ResourceBundle.getBundle("paymentmessages", localLocale);
        jdField_for = new MessageFormat("");
        jdField_for.setLocale(localLocale);
        jdField_new = true;
      }
      catch (MissingResourceException localMissingResourceException)
      {
        throw new BPWException("BPWLocaleUtil.init :: Failed to initialize BPWLocaleUtil");
      }
    }
  }
  
  public static Locale getLocale()
  {
    if (!jdField_new) {
      try
      {
        a();
      }
      catch (BPWException localBPWException)
      {
        FFSDebug.log(localBPWException, "BPWLocaleUtil.getLocale :: BPWLocaleUtil not initialized", 2);
      }
    }
    return Locale.getDefault();
  }
  
  public static String getCurCode()
    throws BPWException
  {
    if (!jdField_new) {
      try
      {
        a();
      }
      catch (BPWException localBPWException)
      {
        throw new BPWException(localBPWException, "BPWLocaleUtil.getCurCode :: BPWLocaleUtil not initialized");
      }
    }
    if (jdField_long == null) {
      try
      {
        jdField_long = jdField_char.getString(j);
      }
      catch (MissingResourceException localMissingResourceException)
      {
        jdField_long = "USD";
      }
    }
    return jdField_long;
  }
  
  public static String getMessage(int paramInt, String[] paramArrayOfString, String paramString)
    throws BPWException
  {
    if (!jdField_new) {
      try
      {
        a();
      }
      catch (BPWException localBPWException1)
      {
        throw new BPWException(localBPWException1, "BPWLocaleUtil.getMessage :: BPWLocaleUtil not initialized");
      }
    }
    String str1 = null;
    String str2 = new Integer(paramInt).toString();
    try
    {
      if (paramString.equals("OFX_MESSAGE")) {
        str1 = d.getString(str2);
      } else if (paramString.equals("SERVER_MESSAGE")) {
        str1 = a.getString(str2);
      } else if (paramString.equals("WIRE_MESSAGE")) {
        str1 = b.getString(str2);
      } else if (paramString.equals("API_MESSAGE")) {
        str1 = jdField_try.getString(str2);
      } else if (paramString.equals("TRANSFER_MESSAGE")) {
        str1 = k.getString(str2);
      } else if (paramString.equals("BOOKTRANSFER_AUDITLOG_MESSAGE")) {
        str1 = f.getString(str2);
      } else if (paramString.equals("EXTERNALTRANSFER_AUDITLOG_MESSAGE")) {
        str1 = i.getString(str2);
      } else if (paramString.equals("BILLPAY_AUDITLOG_MESSAGE")) {
        str1 = jdField_do.getString(str2);
      } else if (paramString.equals("PAYMENT_MESSAGE")) {
        str1 = jdField_if.getString(str2);
      } else {
        throw new BPWException("BPWLocaleUtil.getMessage ::  Invalid message context specified");
      }
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
        synchronized (jdField_for)
        {
          jdField_for.applyPattern(str1);
          str1 = jdField_for.format(paramArrayOfString);
        }
      }
    }
    catch (BPWException localBPWException2)
    {
      throw localBPWException2;
    }
    catch (Exception localException)
    {
      throw new BPWException("BPWLocaleUtil.getMessage :: Failed  to load message string");
    }
    return str1;
  }
  
  public static LocalizableString getLocalizedMessage(int paramInt, String[] paramArrayOfString, String paramString)
  {
    return getLocalizableMessage(paramInt, paramArrayOfString, paramString);
  }
  
  public static LocalizableString getLocalizableMessage(int paramInt, Object[] paramArrayOfObject, String paramString)
  {
    String str = null;
    if (paramString.equals("OFX_MESSAGE")) {
      str = "ofxmessages";
    } else if (paramString.equals("SERVER_MESSAGE")) {
      str = "servermessages";
    } else if (paramString.equals("WIRE_MESSAGE")) {
      str = "wiremessages";
    } else if (paramString.equals("API_MESSAGE")) {
      str = "apimessages";
    } else if (paramString.equals("TRANSFER_MESSAGE")) {
      str = "transfermessages";
    } else if (paramString.equals("BOOKTRANSFER_AUDITLOG_MESSAGE")) {
      str = "booktransferauditlogmessages";
    } else if (paramString.equals("BILLPAY_AUDITLOG_MESSAGE")) {
      str = "billpayauditlogmessages";
    } else if (paramString.equals("EXTERNALTRANSFER_AUDITLOG_MESSAGE")) {
      str = "externaltransferauditlogmessages";
    } else if (paramString.equals("PAYMENT_MESSAGE")) {
      str = "paymentmessages";
    } else {
      str = "dummy";
    }
    try
    {
      return new LocalizableString(str, Integer.toString(paramInt), paramArrayOfObject);
    }
    catch (Exception localException) {}
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BPWLocaleUtil
 * JD-Core Version:    0.7.0.1
 */