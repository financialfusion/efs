package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogWriter
  extends BaseTask
{
  private static final String Q4 = "java.util.logging.config.file";
  private static final String Q3 = ".log";
  protected static HashMap loggers = new HashMap();
  protected String logFile = "fusion.log";
  protected Level logLevel = Level.SEVERE;
  protected String messageLevel = Level.INFO.getName();
  protected String message = "";
  protected String configFilePath = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      Logger localLogger = (Logger)loggers.get(this.logFile);
      if (localLogger == null)
      {
        if (System.getProperty("java.util.logging.config.file") == null) {
          System.setProperty("java.util.logging.config.file", this.configFilePath);
        }
        localLogger = Logger.getLogger(null);
        localLogger.addHandler(new FileHandler(this.logFile));
        loggers.put(this.logFile, localLogger);
        localLogger.setLevel(this.logLevel);
      }
      if (this.messageLevel.equalsIgnoreCase(Level.CONFIG.getName())) {
        localLogger.config(this.message);
      } else if (this.messageLevel.equalsIgnoreCase(Level.FINE.getName())) {
        localLogger.fine(this.message);
      } else if (this.messageLevel.equalsIgnoreCase(Level.FINER.getName())) {
        localLogger.finer(this.message);
      } else if (this.messageLevel.equalsIgnoreCase(Level.FINEST.getName())) {
        localLogger.finest(this.message);
      } else if (this.messageLevel.equalsIgnoreCase(Level.SEVERE.getName())) {
        localLogger.severe(this.message);
      } else if (this.messageLevel.equalsIgnoreCase(Level.WARNING.getName())) {
        localLogger.warning(this.message);
      } else {
        localLogger.info(this.message);
      }
    }
    catch (Exception localException)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setLogLevel(String paramString)
  {
    if (paramString.equalsIgnoreCase(Level.ALL.getName())) {
      this.logLevel = Level.ALL;
    } else if (paramString.equalsIgnoreCase(Level.OFF.getName())) {
      this.logLevel = Level.OFF;
    } else if (paramString.equalsIgnoreCase(Level.CONFIG.getName())) {
      this.logLevel = Level.CONFIG;
    } else if (paramString.equalsIgnoreCase(Level.FINE.getName())) {
      this.logLevel = Level.FINE;
    } else if (paramString.equalsIgnoreCase(Level.FINER.getName())) {
      this.logLevel = Level.FINER;
    } else if (paramString.equalsIgnoreCase(Level.FINEST.getName())) {
      this.logLevel = Level.FINEST;
    } else if (paramString.equalsIgnoreCase(Level.SEVERE.getName())) {
      this.logLevel = Level.SEVERE;
    } else if (paramString.equalsIgnoreCase(Level.WARNING.getName())) {
      this.logLevel = Level.WARNING;
    } else {
      this.logLevel = Level.INFO;
    }
  }
  
  public String getLogLevel()
  {
    if (this.logLevel == null) {
      return null;
    }
    return this.logLevel.getName();
  }
  
  public Level getLogLevelValue()
  {
    return this.logLevel;
  }
  
  public void setMessageLevel(String paramString)
  {
    this.messageLevel = paramString;
  }
  
  public String getMessageLevel()
  {
    return this.messageLevel;
  }
  
  public void setLogFile(String paramString)
  {
    if (paramString.indexOf(".") == -1) {
      paramString = paramString + ".log";
    }
    this.logFile = paramString;
  }
  
  public String getLogFile()
  {
    return this.logFile;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setConfigFilePath(String paramString)
  {
    this.configFilePath = paramString;
  }
  
  public String getConfigFilePath()
  {
    return this.configFilePath;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.LogWriter
 * JD-Core Version:    0.7.0.1
 */