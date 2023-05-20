package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.util.logging.Level;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class GetL10NStringExtraInfo
  extends TagExtraInfo
{
  public VariableInfo[] getVariableInfo(TagData paramTagData)
  {
    VariableInfo[] arrayOfVariableInfo = null;
    try
    {
      String str = paramTagData.getAttributeString("assignTo");
      if (str == null) {
        return new VariableInfo[0];
      }
      arrayOfVariableInfo = new VariableInfo[1];
      arrayOfVariableInfo[0] = new VariableInfo(str, "java.lang.String", false, 2);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.FINEST, "\t<jtf:getProperty (getL10NStringExtraInfo)" + localException.toString());
    }
    return arrayOfVariableInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.GetL10NStringExtraInfo
 * JD-Core Version:    0.7.0.1
 */