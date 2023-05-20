package com.ffusion.beans.fileimporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class Record
  extends HashMap
{
  private String jdField_if;
  private Integer a;
  private Integer jdField_do;
  
  public Record(String paramString, Integer paramInteger1, Integer paramInteger2)
  {
    this.jdField_if = paramString;
    this.a = paramInteger1;
    this.jdField_do = paramInteger2;
  }
  
  public String getLineContent()
  {
    return this.jdField_if;
  }
  
  public Integer getLineNumber()
  {
    return this.a;
  }
  
  public Integer getRecordNumber()
  {
    return this.jdField_do;
  }
  
  public String toString()
  {
    Set localSet = entrySet();
    if (localSet.size() == 0) {
      return "<empty>";
    }
    Iterator localIterator = localSet.iterator();
    ArrayList localArrayList = new ArrayList(localSet.size());
    while (localIterator.hasNext())
    {
      localObject1 = (Map.Entry)localIterator.next();
      localObject2 = (String)((Map.Entry)localObject1).getKey();
      String str = (String)((Map.Entry)localObject1).getValue();
      localArrayList.add("  " + (String)localObject2 + ": " + str);
    }
    Object localObject1 = System.getProperty("line.separator");
    Object localObject2 = new StringBuffer();
    Collections.sort(localArrayList);
    localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      ((StringBuffer)localObject2).append((String)localIterator.next());
      ((StringBuffer)localObject2).append((String)localObject1);
    }
    return ((StringBuffer)localObject2).toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.Record
 * JD-Core Version:    0.7.0.1
 */