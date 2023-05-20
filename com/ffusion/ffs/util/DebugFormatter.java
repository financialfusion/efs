package com.ffusion.ffs.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class DebugFormatter
  extends Formatter
{
  public String format(LogRecord paramLogRecord)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(new Date());
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


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.DebugFormatter
 * JD-Core Version:    0.7.0.1
 */