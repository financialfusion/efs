package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.Records;
import com.ffusion.csil.FIException;
import java.util.HashMap;

public abstract interface ICustomMappingAdapter
{
  public abstract void initialize(HashMap paramHashMap)
    throws FIException;
  
  public abstract void open(HashMap paramHashMap)
    throws FIException;
  
  public abstract void close(HashMap paramHashMap)
    throws FIException;
  
  public abstract void process(ErrorMessages paramErrorMessages, Records paramRecords, HashMap paramHashMap)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.ICustomMappingAdapter
 * JD-Core Version:    0.7.0.1
 */