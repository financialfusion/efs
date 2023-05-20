package com.ffusion.tasks.bptw;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BPTW;
import com.ffusion.ffs.bpw.db.InstructionActivityLog;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLogs
  extends BaseTask
  implements Task
{
  private String ko = "";
  private String kn = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.bptw.Log localLog = (com.ffusion.services.bptw.Log)localHttpSession.getAttribute("BptwLog");
    FilteredList localFilteredList = new FilteredList();
    localHttpSession.setAttribute("BptwLogs", localFilteredList);
    InstructionActivityLog[] arrayOfInstructionActivityLog = null;
    HashMap localHashMap = null;
    if (localLog != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localLog);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      arrayOfInstructionActivityLog = BPTW.getLogInfo(localSecureUser, this.ko, this.kn, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (arrayOfInstructionActivityLog != null) {
      for (int i = 0; i < arrayOfInstructionActivityLog.length; i++)
      {
        com.ffusion.beans.bptw.Log localLog1 = new com.ffusion.beans.bptw.Log();
        localLog1.setType(arrayOfInstructionActivityLog[i].LogType);
        localLog1.setDate(arrayOfInstructionActivityLog[i].LogDate);
        localLog1.setInstructionType(arrayOfInstructionActivityLog[i].InstructionType);
        localLog1.setInstructionID(arrayOfInstructionActivityLog[i].InstructionID);
        localLog1.setContent(arrayOfInstructionActivityLog[i].Content);
        localFilteredList.add(localLog1);
      }
    }
    return this.successURL;
  }
  
  public void setID(String paramString)
  {
    this.ko = paramString;
  }
  
  public String getID()
  {
    return this.ko;
  }
  
  public void setType(String paramString)
  {
    this.kn = paramString;
  }
  
  public String getType()
  {
    return this.kn;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.GetLogs
 * JD-Core Version:    0.7.0.1
 */