package com.ffusion.services.filemonitor;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.filemonitor.adapter.FileMonitorAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMFileDescription;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import com.ffusion.util.filemonitor.FMLogAdapter;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FileMonitorService
  extends FileMonitorServiceBase
{
  private FileMonitorAdapter a;
  
  public void initialize(HashMap paramHashMap)
    throws FMException
  {
    String str = "FileMonitorService.initialize";
    super.initialize(paramHashMap);
    InputStream localInputStream = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, "filemonitor.xml");
      if (localInputStream == null)
      {
        localObject1 = new FMException("Unable to open file filemonitor.xml for read from the classpath settings. Make sure there is such a file in classpath, and it is readable", 1);
        DebugLog.throwing(str, (Throwable)localObject1);
        throw ((Throwable)localObject1);
      }
      Object localObject1 = Strings.streamToString(localInputStream);
      localObject2 = new XMLTag(true);
      ((XMLTag)localObject2).build((String)localObject1);
      XMLTag localXMLTag1 = ((XMLTag)localObject2).getContainedTag("DB_PROPERTIES");
      if (localXMLTag1 == null)
      {
        localObject3 = new FMException("There is no database properties defined by tag name DB_PROPERTIES", 1);
        DebugLog.throwing(str, (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
      this.a = new FileMonitorAdapter();
      this.a.initialize(localXMLTag1.getTagHashMap());
      for (Object localObject3 = ((XMLTag)localObject2).getContainedTag("FILEDESC"); localObject3 != null; localObject3 = ((XMLTag)localObject2).getContainedTag("FILEDESC"))
      {
        FMFileDescription localFMFileDescription = new FMFileDescription();
        XMLTag localXMLTag2 = ((XMLTag)localObject3).getContainedTag("FILETYPE");
        localFMFileDescription.setFileType(localXMLTag2.getTagContent());
        localXMLTag2 = ((XMLTag)localObject3).getContainedTag("SYSTEMS");
        ArrayList localArrayList = new ArrayList();
        if (localXMLTag2 != null)
        {
          for (XMLTag localXMLTag3 = localXMLTag2.getContainedTag("SYSTEM"); localXMLTag3 != null; localXMLTag3 = localXMLTag2.getContainedTag("SYSTEM"))
          {
            localArrayList.add(localXMLTag3.getTagContent());
            localXMLTag2.removeContainedTag("SYSTEM");
          }
          Collections.sort(localArrayList);
        }
        localFMFileDescription.setSystems(localArrayList);
        super.addFileDescription(localFMFileDescription);
        ((XMLTag)localObject2).removeContainedTag("FILEDESC");
      }
    }
    catch (Exception localException1)
    {
      Object localObject2 = new FMException(localException1, -1);
      DebugLog.throwing("FileMonitorService.initialize", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    finally
    {
      if (localInputStream != null) {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException2) {}
      }
    }
  }
  
  public FMFileDescriptions getFileDescriptions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws FMException
  {
    return this._fileDescriptions;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException
  {
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      String str = "Unable to calcualte the date ranges. A report cannot be run.";
      DebugLog.log(str);
      if (localCSILException.getCode() == -1009) {
        throw new RptException(localCSILException.getServiceError(), str);
      }
      throw new RptException(localCSILException.getCode(), str);
    }
    return this.a.getReportData(paramReportCriteria, paramHashMap);
  }
  
  public void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws FMException
  {
    FMLogAdapter.cleanup(paramString, paramInt);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.filemonitor.FileMonitorService
 * JD-Core Version:    0.7.0.1
 */