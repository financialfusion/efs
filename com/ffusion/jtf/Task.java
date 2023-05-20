package com.ffusion.jtf;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Task
  extends Serializable
{
  public static final String CURRENT_TASK = "CurrentTask";
  public static final String LOCALE = "java.util.Locale";
  public static final String SESSION_ID = "SessionID";
  public static final String DO_NOT_FORWARD = "";
  
  public abstract String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.Task
 * JD-Core Version:    0.7.0.1
 */