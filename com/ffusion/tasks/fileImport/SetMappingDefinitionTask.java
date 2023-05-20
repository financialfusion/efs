package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetMappingDefinitionTask
  extends BaseTask
{
  private int P9;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    MappingDefinitions localMappingDefinitions = (MappingDefinitions)localHttpSession.getAttribute("MappingDefinitions");
    if (localMappingDefinitions == null)
    {
      this.error = 23000;
      str = super.getTaskErrorURL();
    }
    else
    {
      MappingDefinition localMappingDefinition = localMappingDefinitions.findByID(this.P9);
      if (localMappingDefinition == null) {
        localMappingDefinition = new MappingDefinition();
      }
      localHttpSession.setAttribute("MappingDefinition", localMappingDefinition);
      str = super.getSuccessURL();
    }
    return str;
  }
  
  public String getID()
  {
    return String.valueOf(this.P9);
  }
  
  public int getIDValue()
  {
    return this.P9;
  }
  
  public void setID(String paramString)
  {
    this.P9 = Integer.parseInt(paramString);
  }
  
  public void setID(int paramInt)
  {
    this.P9 = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.SetMappingDefinitionTask
 * JD-Core Version:    0.7.0.1
 */