package com.ffusion.fileimporter.fileadapters;

import com.ffusion.csil.FIException;
import java.io.InputStream;
import java.util.HashMap;

public abstract interface IFileAdapter
{
  public abstract void initialize(HashMap paramHashMap)
    throws FIException;
  
  public abstract void open(HashMap paramHashMap)
    throws FIException;
  
  public abstract void close(HashMap paramHashMap)
    throws FIException;
  
  public abstract void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.IFileAdapter
 * JD-Core Version:    0.7.0.1
 */