package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteMappingDefinitionTask
  extends ExtendedBaseTask
{
  public DeleteMappingDefinitionTask()
  {
    this.beanSessionName = "MappingDefinition";
    this.collectionSessionName = "MappingDefinitions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = null;
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
      HashMap localHashMap = new HashMap();
      FileImporter.removeMappingDefinition(localSecureUser, localMappingDefinition.getMappingID(), localHashMap);
      localMappingDefinitions.remove(localMappingDefinition);
      str = super.getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = super.getServiceErrorURL();
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.DeleteMappingDefinitionTask
 * JD-Core Version:    0.7.0.1
 */