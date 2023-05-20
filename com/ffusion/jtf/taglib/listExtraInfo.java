package com.ffusion.jtf.taglib;

import java.util.StringTokenizer;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class listExtraInfo
  extends TagExtraInfo
{
  public VariableInfo[] getVariableInfo(TagData paramTagData)
  {
    VariableInfo[] arrayOfVariableInfo = null;
    try
    {
      String str1 = paramTagData.getAttributeString("items");
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, ", ");
      int i = localStringTokenizer.countTokens();
      arrayOfVariableInfo = new VariableInfo[i];
      for (int j = 0; j < i; j++)
      {
        String str2 = localStringTokenizer.nextToken();
        arrayOfVariableInfo[j] = new VariableInfo(str2, "java.lang.String", false, 0);
      }
    }
    catch (Exception localException) {}
    return arrayOfVariableInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.listExtraInfo
 * JD-Core Version:    0.7.0.1
 */