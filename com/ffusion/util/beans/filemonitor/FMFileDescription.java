package com.ffusion.util.beans.filemonitor;

import com.ffusion.util.beans.ExtendABean;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class FMFileDescription
  extends ExtendABean
{
  public static final String FILE_TYPE = "FILE_TYPE";
  protected String _fileType = null;
  protected ArrayList _systems = new ArrayList();
  
  public FMFileDescription() {}
  
  public FMFileDescription(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getFileType()
  {
    return this._fileType;
  }
  
  public ArrayList getSystems()
  {
    return this._systems;
  }
  
  public void setFileType(String paramString)
  {
    this._fileType = paramString;
  }
  
  public void addSystem(String paramString)
  {
    this._systems.add(paramString);
  }
  
  public void setSystems(ArrayList paramArrayList)
  {
    this._systems = paramArrayList;
  }
  
  public void set(FMFileDescription paramFMFileDescription)
  {
    if (paramFMFileDescription == this) {
      return;
    }
    super.set(paramFMFileDescription);
    this._fileType = paramFMFileDescription._fileType;
    this._systems.clear();
    this._systems.addAll(paramFMFileDescription._systems);
  }
  
  public Object clone()
  {
    FMFileDescription localFMFileDescription = new FMFileDescription();
    localFMFileDescription.set(this);
    return localFMFileDescription;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    if (!paramObject.getClass().equals(getClass())) {
      return false;
    }
    FMFileDescription localFMFileDescription = (FMFileDescription)paramObject;
    if (!localFMFileDescription._fileType.equals(this._fileType)) {
      return false;
    }
    ArrayList localArrayList = localFMFileDescription.getSystems();
    if (localArrayList == this._systems) {
      return super.equals(paramObject);
    }
    if ((localArrayList == null) || (this._systems == null)) {
      return false;
    }
    if (localArrayList.size() != this._systems.size()) {
      return false;
    }
    for (int i = 0; i < this._systems.size(); i++) {
      if (!localArrayList.contains(this._systems.get(i))) {
        return false;
      }
    }
    return super.equals(paramObject);
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() >= 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("FILE_TYPE")) {
        return this._fileType.equalsIgnoreCase(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "FILE_TYPE");
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    FMFileDescription localFMFileDescription = (FMFileDescription)paramObject;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("FILE_TYPE")) {
      return localCollator.compare(this._fileType, localFMFileDescription._fileType);
    }
    return super.compare(paramObject, paramString);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.filemonitor.FMFileDescription
 * JD-Core Version:    0.7.0.1
 */