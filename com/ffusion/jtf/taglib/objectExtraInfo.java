package com.ffusion.jtf.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class objectExtraInfo
  extends TagExtraInfo
{
  public VariableInfo[] getVariableInfo(TagData paramTagData)
  {
    VariableInfo[] arrayOfVariableInfo = null;
    try
    {
      String str = paramTagData.getAttributeString("name");
      if (str == null) {
        return null;
      }
      arrayOfVariableInfo = new VariableInfo[1];
      arrayOfVariableInfo[0] = new VariableInfo(str, "java.lang.Object", true, 2);
    }
    catch (Exception localException) {}
    return arrayOfVariableInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.objectExtraInfo
 * JD-Core Version:    0.7.0.1
 */