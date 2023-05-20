package com.ffusion.services.FFServer;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.MessageBroadcaster;
import com.ffusion.util.MessageListener;
import com.ffusion.util.logging.DebugLog;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.BufferedReader;
import java.io.Serializable;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;

public class Core
  implements Serializable, MessageBroadcaster, Defines
{
  public static final String DATEFORMAT = "yyyyMMdd";
  public static final String DATETIMEFORMAT = "yyyyMMddHHmmss";
  public static final String DATEEXTTIMEFORMAT = "yyyyMMddHHmmss.SSS";
  protected SecureUser secureUser = new SecureUser();
  protected String userID;
  protected String password;
  private String aX;
  protected Random random;
  protected HashMap savedValues;
  protected HashMap objStatus;
  protected int status;
  protected int lastError;
  private String aY = "";
  private static final String aW = "DebugService";
  protected boolean OFX_Test = false;
  protected boolean debugService = false;
  
  public void setSettings(String paramString)
  {
    try
    {
      StringReader localStringReader = new StringReader(paramString);
      BufferedReader localBufferedReader = new BufferedReader(localStringReader);
      XmlParser localXmlParser = new XmlParser();
      localXmlParser.setHandler(new a());
      localXmlParser.parse(null, null, localBufferedReader);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
    }
  }
  
  public boolean getDebugService()
  {
    return this.debugService;
  }
  
  protected DateTime getDate(String paramString)
  {
    int i = Integer.parseInt(paramString.substring(0, 4));
    int j = Integer.parseInt(paramString.substring(4, 6));
    int k = Integer.parseInt(paramString.substring(6, 8));
    int m = 12;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    if (paramString.length() > 8)
    {
      m = Integer.parseInt(paramString.substring(8, 10));
      n = Integer.parseInt(paramString.substring(10, 12));
      i1 = Integer.parseInt(paramString.substring(12, 14));
      if (paramString.indexOf(":") != -1) {
        i2 = -Integer.parseInt(paramString.substring(paramString.indexOf("[") + 1, paramString.indexOf(":")));
      }
    }
    DateTime localDateTime = new DateTime(Locale.getDefault());
    localDateTime.set(i, j - 1, k, m, n, i1);
    localDateTime.add(10, i2);
    if ((paramString.length() > 8) && (i2 != 0))
    {
      TimeZone localTimeZone = TimeZone.getDefault();
      localDateTime.add(14, localTimeZone.getRawOffset());
      if (localTimeZone.inDaylightTime(localDateTime.getTime())) {
        localDateTime.add(10, 1);
      }
    }
    return localDateTime;
  }
  
  public String setDate(DateTime paramDateTime, String paramString)
  {
    return DateFormatUtil.getFormatter(paramString).format(paramDateTime.getTime());
  }
  
  public int getLastError()
  {
    return this.lastError;
  }
  
  public String getLastErrorMessage()
  {
    return this.aY;
  }
  
  public void logError(int paramInt)
  {
    if (this.debugService) {
      DebugLog.log(Level.WARNING, "logError = " + paramInt);
    }
    this.lastError = paramInt;
  }
  
  public void logError(String paramString)
  {
    this.aY = paramString;
  }
  
  public void resetErrors()
  {
    this.lastError = 0;
    this.aY = null;
  }
  
  public void addMessageListener(MessageListener paramMessageListener) {}
  
  public void removeMessageListener(MessageListener paramMessageListener) {}
  
  protected void fireMessage(Object paramObject)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, paramObject.toString());
    }
  }
  
  protected int returnStatus()
  {
    Iterator localIterator = this.objStatus.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.objStatus.get(str1);
      if (!str2.equals("0")) {
        return Integer.parseInt(str2);
      }
    }
    return this.status;
  }
  
  public void setUserID(String paramString)
  {
    if (paramString == null) {
      return;
    }
    this.userID = paramString;
    this.aX = this.userID;
    this.aX = this.aX.toUpperCase();
    this.aX = this.aX.trim();
    int i = this.aX.lastIndexOf(' ');
    if (i != -1) {
      this.aX = (this.aX.substring(0, 2) + this.aX.substring(i + 1));
    }
    i = 0;
    while (i < this.aX.length()) {
      if (((this.aX.charAt(i) < 'A') || (this.aX.charAt(i) > 'Z')) && ((this.aX.charAt(i) < '0') || (this.aX.charAt(i) > '9'))) {
        this.aX = (this.aX.substring(0, i) + this.aX.substring(i + 1));
      } else {
        i++;
      }
    }
    if (this.aX.length() < 6) {
      this.aX += "HFNHFN";
    }
    this.aX = this.aX.substring(0, 6);
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int j = 65;
    i = localGregorianCalendar.get(2) + j;
    this.aX += i;
    i = localGregorianCalendar.get(5) / 10 + j;
    this.aX += i;
    i = localGregorianCalendar.get(5) % 10 + j;
    this.aX += i;
    i = localGregorianCalendar.get(1) % 10 + j;
    this.aX += i;
  }
  
  public String getUniqueSeqNum()
  {
    String str1 = this.aX;
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int k = Math.abs(this.random.nextInt()) + Math.abs(this.random.nextInt()) + Math.abs(this.random.nextInt());
    k <<= 15;
    int j = localGregorianCalendar.get(14) % 100;
    for (int i = 0; i < j; i += 10) {
      Math.abs(this.random.nextInt());
    }
    k = k + Math.abs(this.random.nextInt()) + Math.abs(this.random.nextInt()) + Math.abs(this.random.nextInt());
    Integer localInteger = new Integer(localGregorianCalendar.get(11));
    String str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar.get(12));
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar.get(13));
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar.get(14) / 10);
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    if (str2.length() < 3) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(Math.abs(k));
    for (str2 = localInteger.toString(); str2.length() < 10; str2 = "0" + str2) {}
    str1 = str1 + str2;
    return str1;
  }
  
  public void logStart(String paramString)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, paramString);
    }
  }
  
  public void logEnd() {}
  
  public class a
    extends HandlerBase
  {
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("DebugService")) {
        Core.this.debugService = Boolean.valueOf(paramString2).booleanValue();
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.Core
 * JD-Core Version:    0.7.0.1
 */