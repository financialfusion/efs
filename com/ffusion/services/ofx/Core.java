package com.ffusion.services.ofx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLUtil;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public abstract class Core
  implements Serializable
{
  public static final String DATEFORMAT = "yyyyMMdd";
  public static final String DATETIMEFORMAT = "yyyyMMddHHmmss";
  public static final String DATEEXTTIMEFORMAT = "yyyyMMddHHmmss.SSS";
  public static final int OFX_VERSION_102 = 102;
  public static final int OFX_VERSION_200 = 200;
  protected int lastError;
  protected String lastErrorString = "";
  private int t;
  private int q;
  private boolean s;
  private boolean r;
  private char[] p;
  private String u;
  protected int OFX_Version = 102;
  
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
  
  private void a(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<");
    paramStringBuffer.append(paramString);
    paramStringBuffer.append(">");
  }
  
  public void appendTag(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    a(paramStringBuffer, paramString1);
    if (paramString2 == null) {
      paramString2 = "ERROR: MISSING TAG VALUE!";
    }
    int i = paramString2.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString2.charAt(j);
      switch (c)
      {
      case '<': 
        paramStringBuffer.append("&lt;");
        break;
      case '>': 
        paramStringBuffer.append("&gt;");
        break;
      case '&': 
        if (paramString2.charAt(j + 1) == '#') {
          paramStringBuffer.append(c);
        } else {
          paramStringBuffer.append("&amp;");
        }
        break;
      default: 
        paramStringBuffer.append(c);
      }
    }
    if (this.OFX_Version >= 200) {
      a(paramStringBuffer, "/" + paramString1);
    }
    paramStringBuffer.append("\r\n");
  }
  
  public void appendTag(StringBuffer paramStringBuffer, String paramString, int paramInt)
  {
    a(paramStringBuffer, paramString);
    paramStringBuffer.append(String.valueOf(paramInt));
    if (this.OFX_Version >= 200) {
      a(paramStringBuffer, "/" + paramString);
    }
    paramStringBuffer.append("\r\n");
  }
  
  public void appendTag(StringBuffer paramStringBuffer, String paramString, Currency paramCurrency)
  {
    a(paramStringBuffer, paramString);
    NumberFormat localNumberFormat = NumberFormat.getInstance();
    localNumberFormat.setGroupingUsed(false);
    localNumberFormat.setMinimumFractionDigits(2);
    localNumberFormat.setMaximumFractionDigits(2);
    if (paramCurrency != null) {
      paramStringBuffer.append(localNumberFormat.format(paramCurrency.doubleValue()));
    } else {
      paramStringBuffer.append("ERROR: MISSING $$$$ TAG VALUE!");
    }
    if (this.OFX_Version >= 200) {
      a(paramStringBuffer, "/" + paramString);
    }
    paramStringBuffer.append("\r\n");
  }
  
  public void appendTag(StringBuffer paramStringBuffer, String paramString1, Calendar paramCalendar, String paramString2)
  {
    a(paramStringBuffer, paramString1);
    if (paramCalendar != null) {
      paramStringBuffer.append(DateFormatUtil.getFormatter(paramString2).format(paramCalendar.getTime()));
    } else {
      paramStringBuffer.append("ERROR: MISSING DATE TAG VALUE!");
    }
    if (this.OFX_Version >= 200) {
      a(paramStringBuffer, "/" + paramString1);
    }
    paramStringBuffer.append("\r\n");
  }
  
  public void appendBeginTag(StringBuffer paramStringBuffer, String paramString)
  {
    a(paramStringBuffer, paramString);
    paramStringBuffer.append("\r\n");
  }
  
  public void appendEndTag(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("</");
    paramStringBuffer.append(paramString);
    paramStringBuffer.append(">\r\n");
  }
  
  public String getLastError()
  {
    return String.valueOf(this.lastError);
  }
  
  public String getLastErrorMessage()
  {
    return this.lastErrorString;
  }
  
  public int getOFXVersion()
  {
    return this.OFX_Version;
  }
  
  protected void setOFXVersion(int paramInt)
  {
    this.OFX_Version = paramInt;
  }
  
  public void logError(int paramInt)
  {
    this.lastError = paramInt;
  }
  
  public void logError(String paramString)
  {
    this.lastErrorString = paramString;
  }
  
  public void start(String paramString)
  {
    start(paramString.toCharArray());
  }
  
  public void start(char[] paramArrayOfChar)
  {
    this.lastError = 0;
    this.lastErrorString = null;
    this.t = 0;
    this.q = 0;
    this.s = false;
    this.r = true;
    this.p = paramArrayOfChar;
  }
  
  public String getLastToken()
  {
    return this.u;
  }
  
  public boolean getToken(String paramString)
  {
    getToken();
    return (this.u != null) && (!paramString.equals(this.u));
  }
  
  public String getToken()
  {
    this.u = null;
    while ((this.t < this.p.length) && (this.u == null))
    {
      switch (this.p[this.t])
      {
      case '\n': 
      case '\r': 
      case '<': 
        if (this.q < this.t) {
          this.u = new String(this.p, this.q, this.t - this.q);
        }
        this.q = (this.t + 1);
        this.s = true;
        this.r = false;
        break;
      case '>': 
        this.u = new String(this.p, this.q, this.t - this.q);
        this.r = true;
        this.q = (this.t + 1);
        break;
      case '/': 
        if (!this.r)
        {
          this.s = false;
          this.q = (this.t + 1);
        }
        break;
      }
      this.t += 1;
    }
    if (this.u.indexOf('&') != -1) {
      this.u = XMLUtil.XMLDecode(this.u);
    }
    return this.u;
  }
  
  public String getField()
  {
    String str = getToken();
    if ((this.OFX_Version >= 200) && (this.p[this.t] == '/')) {
      skipToken();
    }
    return str;
  }
  
  public void skipField()
  {
    skipToken();
    if ((this.OFX_Version >= 200) && (this.p[this.t] == '/')) {
      skipToken();
    }
  }
  
  public void skipToken()
  {
    int i = 0;
    while ((this.t < this.p.length) && (i == 0))
    {
      switch (this.p[this.t])
      {
      case '\n': 
      case '\r': 
      case '<': 
        if (this.q < this.t) {
          i = 1;
        }
        this.q = (this.t + 1);
        this.s = true;
        this.r = false;
        break;
      case '>': 
        i = 1;
        this.r = true;
        this.q = (this.t + 1);
        break;
      case '/': 
        if (!this.r)
        {
          this.s = false;
          this.q = (this.t + 1);
        }
        break;
      }
      this.t += 1;
    }
  }
  
  public void skipTokens(String paramString)
  {
    boolean bool = false;
    char[] arrayOfChar = paramString.toCharArray();
    while ((this.t < this.p.length) && (!bool))
    {
      switch (this.p[this.t])
      {
      case '\n': 
      case '\r': 
      case '<': 
        if (this.q < this.t) {
          bool = a(arrayOfChar, this.p, this.q, this.t);
        }
        this.q = (this.t + 1);
        this.s = true;
        this.r = false;
        break;
      case '>': 
        bool = a(arrayOfChar, this.p, this.q, this.t);
        this.r = true;
        this.q = (this.t + 1);
        break;
      case '/': 
        if (!this.r)
        {
          this.s = false;
          this.q = (this.t + 1);
        }
        break;
      }
      this.t += 1;
    }
  }
  
  public static void OFX_Formatter(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    int i = 0;
    while (paramString1.indexOf("<", i) != -1)
    {
      while ((paramString1.indexOf("\n", i) == i) || (paramString1.indexOf("\r", i) == i)) {
        i++;
      }
      int j = paramString1.indexOf("<", i);
      int k = paramString1.indexOf(">", j) + 1;
      String str = paramString1.substring(j + 1, k - 1);
      int m = paramString1.indexOf("</" + str + ">", i);
      if (m != -1)
      {
        paramStringBuffer.append(paramString2 + "<" + str + ">\r\n");
        OFX_Formatter(paramStringBuffer, paramString1.substring(k, m), paramString2 + "  ");
        paramStringBuffer.append(paramString2 + "</" + str + ">\r\n");
        i = m + str.length() + 3;
        if (i >= paramString1.length()) {
          break;
        }
      }
      else
      {
        k = paramString1.indexOf("<", j + 1);
        if (k != -1)
        {
          str = paramString2 + paramString1.substring(i, k);
          paramStringBuffer.append(str);
          if (str.indexOf("\n") == -1) {
            paramStringBuffer.append("\r\n");
          }
          i = k;
          if (i >= paramString1.length()) {
            break;
          }
        }
        else
        {
          str = paramString2 + paramString1.substring(i);
          paramStringBuffer.append(str);
          if (str.indexOf("\n") == -1) {
            paramStringBuffer.append("\r\n");
          }
        }
      }
    }
  }
  
  private boolean a(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt1, int paramInt2)
  {
    if (paramArrayOfChar1.length != paramInt2 - paramInt1) {
      return false;
    }
    int i = 0;
    while (i < paramArrayOfChar1.length)
    {
      if (paramArrayOfChar1[i] != paramArrayOfChar2[paramInt1]) {
        return false;
      }
      i++;
      paramInt1++;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Core
 * JD-Core Version:    0.7.0.1
 */