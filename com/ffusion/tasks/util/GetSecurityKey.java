package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Random;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSecurityKey
  extends BaseTask
{
  public static String SECURITY_KEY_NAME = "UserSecurityKey";
  private int QD = 15;
  private static String QC = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Random localRandom = new Random();
    int i = 0;
    char[] arrayOfChar = new char[this.QD];
    for (int j = 0; j < this.QD; j++)
    {
      i = localRandom.nextInt(QC.length());
      arrayOfChar[j] = QC.charAt(i);
    }
    String str = new String(arrayOfChar);
    ImmutableString localImmutableString = new ImmutableString(str);
    localHttpSession.setAttribute(SECURITY_KEY_NAME, localImmutableString);
    return this.successURL;
  }
  
  public void setKeyLength(String paramString)
  {
    int i = Integer.parseInt(paramString);
    this.QD = i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetSecurityKey
 * JD-Core Version:    0.7.0.1
 */