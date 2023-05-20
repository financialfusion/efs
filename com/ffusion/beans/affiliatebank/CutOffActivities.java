package com.ffusion.beans.affiliatebank;

import com.ffusion.util.FilteredList;

public class CutOffActivities
  extends FilteredList
{
  public CutOffActivity add()
  {
    CutOffActivity localCutOffActivity = new CutOffActivity();
    add(localCutOffActivity);
    return localCutOffActivity;
  }
  
  public CutOffActivity createNoAdd()
  {
    CutOffActivity localCutOffActivity = new CutOffActivity();
    return localCutOffActivity;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.CutOffActivities
 * JD-Core Version:    0.7.0.1
 */