package com.ffusion.ffs.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConfigurableFormatter
  extends Formatter
{
  private static final String jdField_do = "yyyy-MM-dd HH:mm:ss.SSS z";
  private static final String jdField_case = "|";
  private static final String jdField_byte = "\n";
  private SimpleDateFormat jdField_new = null;
  private String a = null;
  private String jdField_try = null;
  private boolean jdField_if = false;
  private boolean jdField_int = false;
  private ThreadLocal jdField_for = null;
  
  ConfigurableFormatter()
  {
    String str = null;
    try
    {
      this.jdField_try = System.getProperty("line.separator");
      this.a = System.getProperty("com.ffusion.ffs.util.ConfigurableFormatter.fieldSeparator");
      str = System.getProperty("com.ffusion.ffs.util.ConfigurableFormatter.timeFormat");
      this.jdField_if = Boolean.getBoolean("com.ffusion.ffs.util.ConfigurableFormatter.hideThreadId");
      this.jdField_int = Boolean.getBoolean("com.ffusion.ffs.util.ConfigurableFormatter.showRecordLevel");
    }
    catch (Exception localException1) {}
    if (this.jdField_try == null) {
      this.jdField_try = "\n";
    }
    if ((this.a == null) || (this.a.trim().length() == 0)) {
      this.a = "|";
    }
    this.a = (" " + this.a.trim() + " ");
    if ((str == null) || (str.trim().length() == 0)) {
      str = "yyyy-MM-dd HH:mm:ss.SSS z";
    }
    try
    {
      this.jdField_new = new SimpleDateFormat(str);
    }
    catch (Exception localException2)
    {
      try
      {
        this.jdField_new = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
      }
      catch (Exception localException3) {}
    }
    this.jdField_for = new ThreadLocal();
  }
  
  private DateFormat a()
  {
    DateFormat localDateFormat = (DateFormat)this.jdField_for.get();
    if ((localDateFormat == null) && (this.jdField_new != null))
    {
      localDateFormat = (DateFormat)this.jdField_new.clone();
      this.jdField_for.set(localDateFormat);
    }
    return localDateFormat;
  }
  
  public String format(LogRecord paramLogRecord)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Date localDate = new Date();
    DateFormat localDateFormat = a();
    if (localDateFormat != null) {
      localStringBuffer.append(localDateFormat.format(localDate));
    } else {
      localStringBuffer.append(localDate);
    }
    localStringBuffer.append(this.a);
    if (!this.jdField_if)
    {
      localStringBuffer.append(Thread.currentThread().getName());
      localStringBuffer.append(this.a);
    }
    if (this.jdField_int == true)
    {
      localStringBuffer.append(paramLogRecord.getLevel().getLocalizedName());
      localStringBuffer.append(this.a);
    }
    localStringBuffer.append(formatMessage(paramLogRecord));
    String str = paramLogRecord.getSourceClassName();
    if ((str != null) && (str.length() > 0)) {
      localStringBuffer.append(" (in " + str + ")");
    }
    localStringBuffer.append(this.jdField_try);
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
 * Qualified Name:     com.ffusion.ffs.util.ConfigurableFormatter
 * JD-Core Version:    0.7.0.1
 */