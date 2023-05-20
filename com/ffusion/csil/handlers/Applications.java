package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Banks;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Applications3;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class Applications
{
  private static final String jdField_do = "Applications";
  private static Applications3 jdField_for = null;
  private static String jdField_if;
  private static String a;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Applications", str, 20107);
    jdField_for = (Applications3)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      jdField_for.initialize("");
      jdField_if = HandlerUtil.getGlobalApplicationDisplaySize(paramHashMap);
      a = HandlerUtil.getGlobalApplicationMaxResultSize(paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(20107, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_for;
  }
  
  public static String getDisplayCount()
  {
    return jdField_if;
  }
  
  public static String getMaxResultCount()
  {
    return a;
  }
  
  public static Banks getBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getBanks";
    try
    {
      return jdField_for.getBanks(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7080), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Product getProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getProduct";
    try
    {
      return jdField_for.getProduct(paramSecureUser, paramProduct, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7031), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Products getProducts(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getProducts";
    try
    {
      return jdField_for.getProducts(paramSecureUser, paramProduct, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7030), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Product modifyProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.modifyProduct";
    try
    {
      jdField_for.modifyProduct(paramSecureUser, paramProduct, paramHashMap);
      return paramProduct;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7032), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Product deleteProduct(SecureUser paramSecureUser, Product paramProduct, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.deleteProduct";
    try
    {
      jdField_for.deleteProduct(paramSecureUser, paramProduct, paramHashMap);
      return paramProduct;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7033), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Application addApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.addApplication";
    try
    {
      return jdField_for.addApplication(paramSecureUser, paramApplication, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7008), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Application getApplication(SecureUser paramSecureUser, Application paramApplication, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getApplication";
    try
    {
      return jdField_for.getApplication(paramSecureUser, paramApplication, paramBoolean, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7007), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.applications.Applications getApplications(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getApplications";
    try
    {
      return jdField_for.getApplications(paramSecureUser, paramApplication, paramString1, paramString2, paramString3, paramString4, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7006), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Application deleteApplication(SecureUser paramSecureUser, Application paramApplication, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.deleteApplication";
    try
    {
      jdField_for.deleteApplication(paramSecureUser, paramApplication, paramHashMap);
      return paramApplication;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7005), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Application modifyApplication(SecureUser paramSecureUser, Application paramApplication, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.modifyApplication";
    try
    {
      return jdField_for.modifyApplication(paramSecureUser, paramApplication, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7004), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.applications.Applications modifyApplications(SecureUser paramSecureUser, StringList paramStringList, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.modifyApplications";
    try
    {
      return jdField_for.modifyApplications(paramSecureUser, paramStringList, paramString1, paramString2, paramString3, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7014), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Status deleteStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.deleteStatus";
    try
    {
      jdField_for.deleteStatus(paramSecureUser, paramStatus, paramHashMap);
      return paramStatus;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7051), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Status addStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.addStatus";
    try
    {
      return jdField_for.addStatus(paramSecureUser, paramStatus, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7055), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Status modifyStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.modifyStatus";
    try
    {
      jdField_for.modifyStatus(paramSecureUser, paramStatus, paramHashMap);
      return paramStatus;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7054), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Status getStatus(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getStatus";
    try
    {
      return jdField_for.getStatus(paramSecureUser, paramStatus, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7050), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Statuses getStatuses(SecureUser paramSecureUser, Status paramStatus, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getStatuses";
    try
    {
      return jdField_for.getStatuses(paramSecureUser, paramStatus, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7056), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Categories getCategories(SecureUser paramSecureUser, Category paramCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getCategories";
    try
    {
      return jdField_for.getCategories(paramSecureUser, paramCategory, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7022), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Form getForm(SecureUser paramSecureUser, Form paramForm, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getForm";
    try
    {
      return jdField_for.getForm(paramSecureUser, paramForm, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7060), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Integer getApplicationCount(SecureUser paramSecureUser, Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getApplicationCount";
    try
    {
      return new Integer(jdField_for.getApplicationCount(paramSecureUser, paramApplication, paramString1, paramString2, paramString3, paramString4, paramHashMap));
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7010), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ApplicationHistories getHistories(SecureUser paramSecureUser, ApplicationHistory paramApplicationHistory, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Applications.getHistories";
    try
    {
      return jdField_for.getHistories(paramSecureUser, paramApplicationHistory, paramString1, paramString2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(a(localProfileException.code, 7013), localProfileException.code, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    case 7092: 
      return 7092;
    case 7093: 
      return 7093;
    case 7094: 
      return 7094;
    case 7095: 
      return 7095;
    }
    return paramInt2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Applications
 * JD-Core Version:    0.7.0.1
 */