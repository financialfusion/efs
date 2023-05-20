package com.ffusion.jtf.taglib;

public class SetL10NPropertyTag
  extends setProperty
{
  protected String _l10nID;
  
  public void release()
  {
    this._l10nID = null;
    super.release();
  }
  
  public String getL10nID()
  {
    return this._l10nID;
  }
  
  public void setL10nID(String paramString)
  {
    this._l10nID = paramString;
  }
  
  protected String getTagName()
  {
    return "setL10NProperty";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.SetL10NPropertyTag
 * JD-Core Version:    0.7.0.1
 */