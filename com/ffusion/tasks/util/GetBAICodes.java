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

public class GetBAICodes
  extends BaseTask
{
  private FilteredList Rt;
  private String Rs = "";
  
  public void setLevels(String paramString)
  {
    this.Rs = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    this.Rt = new FilteredList();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.Rs, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      int i;
      try
      {
        i = Integer.parseInt(localStringTokenizer.nextToken());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.Rt = null;
        this.error = 47;
        return this.taskErrorURL;
      }
      if ((i == 0) || (i == 1) || (i == 2)) {
        try
        {
          this.Rt.addAll(DataConsolidator.getBAITypeCodeInfoList(i));
        }
        catch (CSILException localCSILException)
        {
          this.Rt = null;
          this.error = MapError.mapError(localCSILException);
          return this.serviceErrorURL;
        }
      }
    }
    this.Rt.setSortedBy("Code");
    return this.successURL;
  }
  
  public FilteredList getCodeInfoList()
  {
    if (this.Rt == null)
    {
      this.error = 63;
      return new FilteredList();
    }
    return this.Rt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetBAICodes
 * JD-Core Version:    0.7.0.1
 */