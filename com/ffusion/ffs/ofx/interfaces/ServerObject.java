package com.ffusion.ffs.ofx.interfaces;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerObject
  implements Serializable
{
  public boolean _anonymousSon;
  public ArrayList _msgSets;
  
  public ServerObject() {}
  
  public ServerObject(boolean paramBoolean, ArrayList paramArrayList)
  {
    this._anonymousSon = paramBoolean;
    this._msgSets = paramArrayList;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.ofx.interfaces.ServerObject
 * JD-Core Version:    0.7.0.1
 */