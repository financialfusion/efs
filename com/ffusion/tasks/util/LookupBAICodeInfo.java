package com.ffusion.tasks.util;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LookupBAICodeInfo
  extends BaseTask
{
  private FilteredList Q6;
  private String Q5 = "";
  
  public void setBAICodes(String paramString)
  {
    this.Q5 = paramString;
    if ((this.Q5.startsWith("[")) && (this.Q5.endsWith("]"))) {
      this.Q5 = this.Q5.substring(1, this.Q5.length() - 1);
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    this.Q6 = new FilteredList();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.Q5, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      int i;
      try
      {
        i = Integer.parseInt(localStringTokenizer.nextToken().trim());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.Q6 = null;
        this.error = 47;
        return this.taskErrorURL;
      }
      try
      {
        this.Q6.add(DataConsolidator.getBAITypeCodeInfo(i));
      }
      catch (CSILException localCSILException)
      {
        this.Q6 = null;
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
    }
    this.Q6.setSortedBy("Code");
    return this.successURL;
  }
  
  public FilteredList getCodeInfoList()
  {
    if (this.Q6 == null)
    {
      this.error = 63;
      return new FilteredList();
    }
    return this.Q6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.LookupBAICodeInfo
 * JD-Core Version:    0.7.0.1
 */