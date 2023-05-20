package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;

public abstract interface BAIParserCallback
{
  public abstract void processBAIRecord(Object paramObject)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIParserCallback
 * JD-Core Version:    0.7.0.1
 */