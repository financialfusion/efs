package com.ffusion.alert.plugins;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.ejb.ICSILEJB;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

public class CSILEJBWrapper
{
  private ICSILEJB jdField_if;
  private Properties a;
  
  public CSILEJBWrapper(ICSILEJB paramICSILEJB)
  {
    this.jdField_if = paramICSILEJB;
  }
  
  public CSILEJBWrapper(ICSILEJB paramICSILEJB, Properties paramProperties)
  {
    this.jdField_if = paramICSILEJB;
    this.a = paramProperties;
  }
  
  public void reconnect()
    throws EJBException, NamingException, RemoteException, CreateException, CSILException
  {
    this.jdField_if = AlertUtil.connectToCSILEJB(this.a);
  }
  
  public void reconnect(Properties paramProperties)
    throws EJBException, NamingException, RemoteException, CreateException, CSILException
  {
    this.jdField_if = AlertUtil.connectToCSILEJB(paramProperties);
    this.a = paramProperties;
  }
  
  public ICSILEJB getEJB()
  {
    return this.jdField_if;
  }
  
  public void remove()
    throws RemoteException, RemoveException
  {
    this.jdField_if.remove();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.CSILEJBWrapper
 * JD-Core Version:    0.7.0.1
 */