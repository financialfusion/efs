package com.ffusion.util.beans.filemonitor;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.beans.LocalizableString;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class FMLogRecord
  extends LogRecord
  implements Cloneable
{
  public static final String STATUS_COMPLETE = "Complete";
  public static final String STATUS_IN_PROCESS = "In Process";
  public static final String STATUS_FAILED = "Failed";
  public static final String STATUS_INFO = "Info";
  public static final String STATUS_WARNING = "Warning";
  public static final String STATUS_ERROR = "Error";
  public static final String ACTIVITY_TRANSFER = "Transfer";
  public static final String ACTIVITY_IMPORT = "ACH Import Entries";
  public static final String ACTIVITY_UPLOAD = "Upload";
  public static final String ACTIVITY_PROCESS = "Process";
  public static final String ACTIVITY_UNDO = "Undo";
  public static final String HOST_NAME = "HOST_NAME";
  public static final String STATUS = "STATUS";
  public static final String COMMENT = "COMMENT";
  public static final String FILE_NAME = "FILE_NAME";
  public static final String FROM_SYSTEM = "FROM_SYSTEM";
  public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";
  public static final String TO_SYSTEM = "TO_SYSTEM";
  public static final String TRACKING_ID = "TRACKING_ID";
  public static final String FILE_TYPE_ACH = "ACH";
  public static final String FILE_TYPE_BAI = "BAI";
  public static final String FILE_TYPE_POSITIVE_PAY_CHECK_RECORD = "Positive Pay Check Record";
  public static final String FILE_TYPE_POSITIVE_PAY_EXCEPTIONS = "Positive Pay Check Record";
  public static final String SYSTEM_CBS_DC = "CBS DC";
  public static final String SYSTEM_CBS_ACH = "CBS ACH";
  protected String _fileType;
  protected String _hostName;
  protected String _fileName;
  protected String _activityType;
  protected String _fromSystem;
  protected String _toSystem;
  protected String _status;
  protected long _timeInMillis;
  protected Locale _locale = LocaleUtil.getDefaultLocale();
  protected ILocalizable _localizableMessage;
  
  public FMLogRecord()
  {
    super(Level.SEVERE, null);
    this._localizableMessage = null;
    this._timeInMillis = System.currentTimeMillis();
  }
  
  public FMLogRecord(Level paramLevel, String paramString)
  {
    this(paramLevel, new LocalizableString("dummy", paramString, null));
  }
  
  public FMLogRecord(Level paramLevel, ILocalizable paramILocalizable)
  {
    super(paramLevel, "");
    this._localizableMessage = paramILocalizable;
    this._timeInMillis = System.currentTimeMillis();
  }
  
  public long getTimeInMillis()
  {
    return this._timeInMillis;
  }
  
  public String getFileType()
  {
    return this._fileType;
  }
  
  public String getHostName()
  {
    return this._hostName;
  }
  
  public String getFileName()
  {
    return this._fileName;
  }
  
  public String getActivityType()
  {
    return this._activityType;
  }
  
  public String getFromSystem()
  {
    return this._fromSystem;
  }
  
  public String getToSystem()
  {
    return this._toSystem;
  }
  
  public String getStatus()
  {
    return this._status;
  }
  
  public void setFileType(String paramString)
  {
    this._fileType = paramString;
  }
  
  public void setHostName(String paramString)
  {
    this._hostName = paramString;
  }
  
  public void setFileName(String paramString)
  {
    this._fileName = paramString;
  }
  
  public void setActivityType(String paramString)
  {
    this._activityType = paramString;
  }
  
  public void setFromSystem(String paramString)
  {
    this._fromSystem = paramString;
  }
  
  public void setToSystem(String paramString)
  {
    this._toSystem = paramString;
  }
  
  public void setStatus(String paramString)
  {
    this._status = paramString;
  }
  
  public void setTimeInMillis(long paramLong)
  {
    this._timeInMillis = paramLong;
  }
  
  public ILocalizable getLocalizableMessage()
  {
    return this._localizableMessage;
  }
  
  public String getMessage()
  {
    return getMessage(this._locale);
  }
  
  public String getMessage(Locale paramLocale)
  {
    if (this._localizableMessage == null) {
      return null;
    }
    return (String)this._localizableMessage.localize(paramLocale);
  }
  
  public void setMessage(String paramString)
  {
    super.setMessage(paramString);
    this._localizableMessage = new LocalizableString("dummy", paramString, null);
  }
  
  public void setLocalizableMessage(ILocalizable paramILocalizable)
  {
    this._localizableMessage = paramILocalizable;
  }
  
  public void copy(FMLogRecord paramFMLogRecord)
  {
    this._fileType = paramFMLogRecord._fileType;
    this._hostName = paramFMLogRecord._hostName;
    this._fileName = paramFMLogRecord._fileName;
    this._activityType = paramFMLogRecord._activityType;
    this._fromSystem = paramFMLogRecord._fromSystem;
    this._toSystem = paramFMLogRecord._toSystem;
    this._status = paramFMLogRecord._status;
    this._localizableMessage = paramFMLogRecord._localizableMessage;
    super.setMillis(paramFMLogRecord.getMillis());
    super.setMessage(paramFMLogRecord.getMessage());
    super.setParameters(paramFMLogRecord.getParameters());
    super.setResourceBundle(paramFMLogRecord.getResourceBundle());
    super.setLevel(paramFMLogRecord.getLevel());
    super.setLoggerName(paramFMLogRecord.getLoggerName());
  }
  
  public Object clone()
  {
    FMLogRecord localFMLogRecord = new FMLogRecord();
    localFMLogRecord.copy(this);
    return localFMLogRecord;
  }
  
  public String toString()
  {
    String str = "";
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localStringBuffer.append("fileType=" + this._fileType + ",hostName=" + this._hostName + ",message=" + getMessage() + ",fileName=" + this._fileName + ",activityType=" + this._activityType + ",fromSystem=" + this._fromSystem + ",toSystem=" + this._toSystem + ",status=" + this._status + ",millis=" + getMillis() + ",parameters=" + getParameters() + ",ressourceBundle=" + getResourceBundle() + ",level=" + getLevel() + ",logerName=" + getLoggerName() + ")");
      str = localStringBuffer.toString();
    }
    catch (Exception localException) {}
    return str;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.filemonitor.FMLogRecord
 * JD-Core Version:    0.7.0.1
 */