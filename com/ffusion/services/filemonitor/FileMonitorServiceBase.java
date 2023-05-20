package com.ffusion.services.filemonitor;

import com.ffusion.services.IFileMonitorService;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMFileDescription;
import com.ffusion.util.beans.filemonitor.FMFileDescriptions;
import com.ffusion.util.filemonitor.FMLog;
import java.util.HashMap;

public abstract class FileMonitorServiceBase
  implements IFileMonitorService
{
  protected FMFileDescriptions _fileDescriptions;
  
  public void initialize(HashMap paramHashMap)
    throws FMException
  {
    this._fileDescriptions = new FMFileDescriptions();
    FMLog.setAvailableFileDescriptions(this._fileDescriptions);
  }
  
  protected void addFileDescription(FMFileDescription paramFMFileDescription)
  {
    if (paramFMFileDescription != null) {
      this._fileDescriptions.add(paramFMFileDescription);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.filemonitor.FileMonitorServiceBase
 * JD-Core Version:    0.7.0.1
 */