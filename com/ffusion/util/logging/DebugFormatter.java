package com.ffusion.util.logging;

import com.ffusion.util.DateFormatUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class DebugFormatter
  extends Formatter
{
  public String format(LogRecord paramLogRecord)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(DateFormatUtil.getFormatter("MMM dd, yyyy hh:mm:ss a").format(new Date()));
    localStringBuffer.append(" ");
    localStringBuffer.append(paramLogRecord.getLevel().getLocalizedName());
    localStringBuffer.append(": ");
    localStringBuffer.append(formatMessage(paramLogRecord));
    String str = paramLogRecord.getSourceClassName();
    if ((str != null) && (str.length() > 0)) {
      localStringBuffer.append(" (in " + paramLogRecord.getSourceClassName() + ")");
    }
    localStringBuffer.append(" \n");
    Throwable localThrowable = paramLogRecord.getThrown();
    if (localThrowable != null) {
      try
      {
        StringWriter localStringWriter = new StringWriter();
        PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
        localThrowable.printStackTrace(localPrintWriter);
        localPrintWriter.close();
        localStringBuffer.append(localStringWriter.toString());
      }
      catch (Exception localException) {}
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.logging.DebugFormatter
 * JD-Core Version:    0.7.0.1
 */