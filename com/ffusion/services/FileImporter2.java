package com.ffusion.services;

import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
import com.ffusion.csil.FIException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface FileImporter2
  extends FileImporter
{
  public abstract ArrayList getUploadFileTypeDisplayNames(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract OutputFormatDisplayNames getOutputFormatDisplayNames(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract OutputFormatDisplayNames getOutputFormatDisplayNamesByCategory(String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FileImporter2
 * JD-Core Version:    0.7.0.1
 */