package com.ffusion.beans.fileimporter;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class FileTypeDisplayNames
  extends FilteredList
{
  public FileTypeDisplayName getByName(String paramString)
  {
    Object localObject = null;
    FileTypeDisplayName localFileTypeDisplayName = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localFileTypeDisplayName = (FileTypeDisplayName)localIterator.next();
      if (localFileTypeDisplayName.getName().equals(paramString)) {
        localObject = localFileTypeDisplayName;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.FileTypeDisplayNames
 * JD-Core Version:    0.7.0.1
 */