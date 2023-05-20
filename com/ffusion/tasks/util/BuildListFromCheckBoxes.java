package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuildListFromCheckBoxes
  extends BaseTask
{
  protected static final String DEFAULT_CODES_LIST_NAME = "RestrictedCodesList";
  protected static final String DEFAULT_DELIMITER = ", ";
  protected String _checkboxBaseName = null;
  protected String _codesListName = "RestrictedCodesList";
  protected String _codesList = null;
  protected boolean _useRestricted = true;
  protected boolean _doReversed = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(this._codesList, ", ");
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (localHttpSession.getAttribute(this._checkboxBaseName + str) == null)
      {
        if (this._doReversed)
        {
          if (!this._useRestricted) {
            localArrayList.add(str);
          }
        }
        else if (this._useRestricted) {
          localArrayList.add(str);
        }
      }
      else if (this._doReversed)
      {
        if (this._useRestricted) {
          localArrayList.add(str);
        }
      }
      else if (!this._useRestricted) {
        localArrayList.add(str);
      }
    }
    localHttpSession.setAttribute(this._codesListName, localArrayList);
    return this.successURL;
  }
  
  public void setCheckBoxBaseName(String paramString)
  {
    this._checkboxBaseName = paramString;
  }
  
  public void setCodesList(String paramString)
  {
    this._codesList = paramString;
  }
  
  public void setCodesListName(String paramString)
  {
    this._codesListName = paramString;
  }
  
  public void setUseRestricted(String paramString)
  {
    this._useRestricted = "true".equalsIgnoreCase(paramString);
  }
  
  public void setDoReversed(String paramString)
  {
    this._doReversed = "true".equalsIgnoreCase(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.BuildListFromCheckBoxes
 * JD-Core Version:    0.7.0.1
 */