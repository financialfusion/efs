package com.ffusion.jtf.taglib.authoring;

import java.io.PrintStream;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class updateExtraInfo
  extends TagExtraInfo
{
  public VariableInfo[] getVariableInfo(TagData paramTagData)
  {
    VariableInfo[] arrayOfVariableInfo = new VariableInfo[2];
    try
    {
      arrayOfVariableInfo[0] = new VariableInfo("status", "java.lang.String", true, 0);
      arrayOfVariableInfo[1] = new VariableInfo("statusMessage", "java.lang.String", true, 0);
    }
    catch (Exception localException)
    {
      System.out.println("Error: <author:update> " + localException);
    }
    return arrayOfVariableInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.authoring.updateExtraInfo
 * JD-Core Version:    0.7.0.1
 */