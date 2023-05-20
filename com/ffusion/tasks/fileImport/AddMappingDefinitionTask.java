package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddMappingDefinitionTask
  extends ValidateMappingDefinitionTask
{
  public AddMappingDefinitionTask()
  {
    this.beanSessionName = "MappingDefinition";
    this.collectionSessionName = "MappingDefinitions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str1 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      MappingDefinition localMappingDefinition = (MappingDefinition)localHttpSession.getAttribute("MappingDefinition");
      if (localMappingDefinition == null)
      {
        this.error = 23000;
        return super.getTaskErrorURL();
      }
      MappingDefinitions localMappingDefinitions = (MappingDefinitions)localHttpSession.getAttribute("MappingDefinitions");
      if (localMappingDefinitions == null)
      {
        this.error = 23001;
        return super.getTaskErrorURL();
      }
      String str2 = super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
      if (str2 != super.getSuccessURL()) {
        return str2;
      }
      HashMap localHashMap = new HashMap();
      FileImporter.addMappingDefinition(localSecureUser, localMappingDefinition, localHashMap);
      localMappingDefinitions.add(localMappingDefinition);
      str1 = super.getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = super.getServiceErrorURL();
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.AddMappingDefinitionTask
 * JD-Core Version:    0.7.0.1
 */