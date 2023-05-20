package com.ffusion.jtf.template;

import javax.servlet.http.HttpServlet;

public class TemplateParseException
  extends Exception
{
  private static final String jdField_for = "Invalid Template -- ";
  private static final String a = "*********************************************************";
  private static final String jdField_if = " ***ERROR*** ";
  private static final int jdField_int = 50;
  private String jdField_do;
  
  public TemplateParseException(String paramString1, int paramInt, String paramString2)
  {
    int i = paramInt - 50;
    if (i < 0) {
      i = 0;
    }
    int j = paramInt + 50;
    if (j > paramString1.length()) {
      j = paramString1.length();
    }
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString2 != null)
    {
      localStringBuffer.append(paramString2);
      localStringBuffer.append("\n\n");
    }
    localStringBuffer.append(paramString1.substring(i, paramInt));
    localStringBuffer.append(" ***ERROR*** ");
    localStringBuffer.append(paramString1.substring(paramInt, j));
    this.jdField_do = localStringBuffer.toString();
  }
  
  public void log(HttpServlet paramHttpServlet, String paramString)
  {
    paramHttpServlet.log("*********************************************************");
    paramHttpServlet.log("Invalid Template -- " + paramString);
    paramHttpServlet.log(this.jdField_do);
    paramHttpServlet.log("*********************************************************");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.TemplateParseException
 * JD-Core Version:    0.7.0.1
 */