package com.ffusion.alert.db;

import com.ffusion.alert.interfaces.AEDBParams;
import com.ffusion.alert.interfaces.AEException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DBConnectionPool
{
  private int jdField_do;
  private AEDBParams jdField_if;
  private HashSet a;
  private ArrayList jdField_for;
  
  public DBConnectionPool(AEDBParams paramAEDBParams, int paramInt)
  {
    this.jdField_do = (paramInt < 1 ? 1 : paramInt);
    this.jdField_if = paramAEDBParams;
    this.jdField_for = new ArrayList(paramInt);
    int i = this.jdField_do < 5 ? 11 : 2 * this.jdField_do + 1;
    this.a = new HashSet(i);
  }
  
  public synchronized DBConnection jdField_for()
    throws AEException
  {
    return a(true);
  }
  
  private DBConnection a(boolean paramBoolean)
    throws AEException
  {
    DBConnection localDBConnection = null;
    while (localDBConnection == null)
    {
      int i = this.jdField_for.size();
      if (i == 0)
      {
        if ((paramBoolean) && (this.a.size() == this.jdField_do)) {
          throw new AEException(10);
        }
        localDBConnection = DBConnection.jdMethod_new(this.jdField_if);
        localDBConnection.aZ();
        this.a.add(localDBConnection);
      }
      else
      {
        localDBConnection = (DBConnection)this.jdField_for.remove(i - 1);
        if (!localDBConnection.aT())
        {
          this.a.remove(localDBConnection);
          localDBConnection.aV();
          localDBConnection = null;
        }
      }
    }
    return localDBConnection;
  }
  
  public synchronized void a(DBConnection paramDBConnection)
    throws AEException
  {
    if (!this.a.contains(paramDBConnection)) {
      throw new AEException(11);
    }
    if (!paramDBConnection.aU()) {
      throw new AEException(12);
    }
    int i = 1;
    try
    {
      paramDBConnection.a1();
    }
    catch (SQLException localSQLException)
    {
      i = 0;
    }
    if ((i != 0) && (this.a.size() <= this.jdField_do))
    {
      this.jdField_for.add(paramDBConnection);
    }
    else
    {
      this.a.remove(paramDBConnection);
      paramDBConnection.aV();
    }
  }
  
  public synchronized DBConnection jdField_if(DBConnection paramDBConnection)
    throws AEException
  {
    DBConnection localDBConnection = paramDBConnection;
    if (!this.a.contains(paramDBConnection)) {
      throw new AEException(11);
    }
    if (paramDBConnection.aT())
    {
      if (!paramDBConnection.aU()) {
        try
        {
          paramDBConnection.jdMethod_try(true);
        }
        catch (SQLException localSQLException)
        {
          localDBConnection = null;
        }
      }
    }
    else {
      localDBConnection = null;
    }
    if (localDBConnection == null)
    {
      localDBConnection = a(false);
      this.a.remove(paramDBConnection);
      paramDBConnection.aV();
      paramDBConnection = null;
    }
    return localDBConnection;
  }
  
  public synchronized void jdField_if()
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      ((DBConnection)localIterator.next()).aV();
    }
    this.a.clear();
    this.jdField_for.clear();
  }
  
  public AEDBParams a()
  {
    return this.jdField_if;
  }
  
  public final int jdField_do()
  {
    return this.jdField_do;
  }
  
  public synchronized void jdField_if(int paramInt)
  {
    int i = paramInt < 1 ? 1 : paramInt;
    if (this.jdField_do > i) {
      a(this.jdField_do - i);
    }
    this.jdField_do = i;
  }
  
  private void a(int paramInt)
  {
    int i = this.jdField_for.size();
    int j = paramInt > i ? i : paramInt;
    for (int k = 1; k <= j; k++)
    {
      DBConnection localDBConnection = (DBConnection)this.jdField_for.remove(i - k);
      this.a.remove(localDBConnection);
      localDBConnection.aV();
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.db.DBConnectionPool
 * JD-Core Version:    0.7.0.1
 */