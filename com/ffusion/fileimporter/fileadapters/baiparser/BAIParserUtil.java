package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.FX;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLableDate;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class BAIParserUtil
  implements BAIParserConsts
{
  private static final DateFormat aTh = DateFormatUtil.getFormatter("yyMMdd");
  private static final DateFormat aTg = DateFormatUtil.getFormatter("yyMMddHHmm");
  
  public static Calendar parseDateTime(int paramInt, String paramString1, String paramString2)
    throws FIException
  {
    try
    {
      return parseDateTime(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      StringBuffer localStringBuffer = new StringBuffer("Line ");
      if (paramInt > 0) {
        localStringBuffer.append(paramInt);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Unable to parse date '").append(paramString1);
      if (paramString2 != null) {
        localStringBuffer.append("' and time '").append(paramString2);
      }
      localStringBuffer.append("'.");
      FIException localFIException = new FIException("com.ffusion.fileimporter.BAIParser.BAIParserUtil", 9702, localStringBuffer.toString());
      throw localFIException;
    }
  }
  
  public static Calendar parseDateTime(String paramString1, String paramString2)
    throws ParseException
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    Date localDate;
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      localDate = aTg.parse(paramString1 + paramString2);
      localGregorianCalendar.setTime(localDate);
    }
    else
    {
      localDate = aTh.parse(paramString1);
      localGregorianCalendar.setTime(localDate);
    }
    return localGregorianCalendar;
  }
  
  protected static final int parseInt(String paramString)
    throws NumberFormatException
  {
    int i = 0;
    int j = paramString.length();
    if (j > 0) {
      if (paramString.charAt(0) == '+')
      {
        if (j > 1) {
          i = Integer.parseInt(paramString.substring(1));
        }
      }
      else {
        i = Integer.parseInt(paramString);
      }
    }
    return i;
  }
  
  protected static final int getCurrencyPrecision(String paramString)
  {
    int i = 2;
    try
    {
      i = FX.getNumDecimals(paramString, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      return i;
    }
    return i;
  }
  
  protected static final BigDecimal convertStrToAmount(String paramString, int paramInt)
    throws NumberFormatException
  {
    Object localObject = null;
    BigDecimal localBigDecimal = null;
    if (paramString != null)
    {
      int i = paramString.length();
      if (i > 0)
      {
        if (paramString.charAt(0) == '+')
        {
          if (i > 1) {
            localBigDecimal = new BigDecimal(new BigInteger(paramString.substring(1)), paramInt);
          } else {
            localBigDecimal = new BigDecimal(0.0D);
          }
        }
        else {
          localBigDecimal = new BigDecimal(new BigInteger(paramString), paramInt);
        }
      }
      else {
        localBigDecimal = new BigDecimal(0.0D);
      }
    }
    return localBigDecimal;
  }
  
  protected static final void checkFundsType(String paramString)
    throws FIException
  {
    if ((!paramString.equals("0")) && (!paramString.equals("1")) && (!paramString.equals("2")) && (!paramString.equals("S")) && (!paramString.equals("V")) && (!paramString.equals("D")) && (!paramString.equals("Z")))
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.BAIParserUtil.checkFundsType", 9718, "Invalid funds type found.");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParserUtil.checkFundsType", localFIException);
      throw localFIException;
    }
  }
  
  protected static final XMLableDate parseXMLableDate(String paramString1, String paramString2)
    throws ParseException
  {
    DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString2);
    Date localDate = localDateFormat.parse(paramString1);
    XMLableDate localXMLableDate = new XMLableDate(localDate.getTime());
    return localXMLableDate;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIParserUtil
 * JD-Core Version:    0.7.0.1
 */