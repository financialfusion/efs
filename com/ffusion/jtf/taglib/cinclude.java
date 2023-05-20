package com.ffusion.jtf.taglib;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.util.entitlements.ParentEntitlementsCache;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class cinclude
  extends BodyTagSupport
{
  public static final String EQUALS_STR = "equals";
  public static final String NOT_EQUALS_STR = "notEquals";
  String jdField_try = "";
  String jdField_int = "";
  String jdField_for = "equals";
  String jdField_case = null;
  String a = null;
  String jdField_do = null;
  String jdField_new = null;
  String jdField_byte = null;
  private boolean jdField_if = false;
  
  public cinclude()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_try = "";
    this.jdField_int = "";
    this.jdField_for = "equals";
    this.jdField_case = null;
    this.a = null;
    this.jdField_do = null;
    this.jdField_new = null;
  }
  
  public void setIfEntitled(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getIfEntitled()
  {
    return this.jdField_case;
  }
  
  public void setIfNotEntitled(String paramString)
  {
    this.a = paramString;
  }
  
  public String getIfNotEntitled()
  {
    return this.a;
  }
  
  public void setObjectType(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getObjectType()
  {
    return this.jdField_do;
  }
  
  public void setObjectId(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getObjectId()
  {
    return this.jdField_new;
  }
  
  public void setCheckParent(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getCheckParent()
  {
    return this.jdField_byte;
  }
  
  public void setOperator(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getOperator()
  {
    return this.jdField_for;
  }
  
  public void setValue1(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getValue1()
  {
    return this.jdField_try;
  }
  
  public void setValue2(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getValue2()
  {
    return this.jdField_int;
  }
  
  public int doStartTag()
    throws JspException
  {
    a("");
    try
    {
      if ((this.jdField_case != null) && (!a(propertyValue.Evaluate(this.jdField_case, this.pageContext), propertyValue.Evaluate(this.jdField_do, this.pageContext), propertyValue.Evaluate(this.jdField_new, this.pageContext), propertyValue.Evaluate(this.jdField_byte, this.pageContext), this.pageContext))) {
        return 0;
      }
      if ((this.a != null) && (a(propertyValue.Evaluate(this.a, this.pageContext), propertyValue.Evaluate(this.jdField_do, this.pageContext), propertyValue.Evaluate(this.jdField_new, this.pageContext), propertyValue.Evaluate(this.jdField_byte, this.pageContext), this.pageContext))) {
        return 0;
      }
      String str1;
      String str2;
      if (this.jdField_try != null)
      {
        if (this.jdField_int == null) {
          this.jdField_int = "";
        }
        str1 = propertyValue.Evaluate(this.jdField_try, this.pageContext);
        str2 = propertyValue.Evaluate(this.jdField_int, this.pageContext);
        if (this.jdField_for == null) {
          this.jdField_for = "equals";
        }
        if ((this.jdField_for.equals("equals")) && (!str1.equals(str2))) {
          return 0;
        }
        if ((this.jdField_for.equals("notEquals")) && (str1.equals(str2))) {
          return 0;
        }
      }
      else if (this.jdField_int != null)
      {
        if (this.jdField_try == null) {
          this.jdField_try = "";
        }
        str1 = propertyValue.Evaluate(this.jdField_try, this.pageContext);
        str2 = propertyValue.Evaluate(this.jdField_int, this.pageContext);
        if (this.jdField_for == null) {
          this.jdField_for = "equals";
        }
        if ((this.jdField_for.equals("equals")) && (!str1.equals(str2))) {
          return 0;
        }
        if ((this.jdField_for.equals("notEquals")) && (str1.equals(str2))) {
          return 0;
        }
      }
      else if ((this.jdField_try == null) && (this.jdField_int == null))
      {
        this.jdField_try = "";
        this.jdField_int = "";
        str1 = propertyValue.Evaluate(this.jdField_try, this.pageContext);
        str2 = propertyValue.Evaluate(this.jdField_int, this.pageContext);
        if (this.jdField_for == null) {
          this.jdField_for = "equals";
        }
        if ((this.jdField_for.equals("equals")) && (!str1.equals(str2))) {
          return 0;
        }
        if ((this.jdField_for.equals("notEquals")) && (str1.equals(str2))) {
          return 0;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
      if (!TagHandlerUtil.a(localThrowable, this.pageContext))
      {
        this.jdField_if = true;
        return 0;
      }
    }
    return 1;
  }
  
  public int doEndTag()
    throws JspException
  {
    if (this.pageContext.getRequest().getAttribute("AbortingResponse") != null)
    {
      DebugLog.log("ffi:cinclude: --------------------- ABORTING RESPONSE -----------------------");
      this.pageContext.getRequest().removeAttribute("AbortingResponse");
      return 5;
    }
    if (this.jdField_if)
    {
      this.jdField_if = false;
      return 5;
    }
    return 6;
  }
  
  private boolean a(String paramString1, String paramString2, String paramString3, String paramString4, PageContext paramPageContext)
  {
    SecureUser localSecureUser = null;
    Object localObject = paramPageContext.findAttribute("SecureUser");
    if (localObject == null) {
      localObject = paramPageContext.getSession().getAttribute("SecureUser");
    }
    if (localObject != null)
    {
      localSecureUser = (SecureUser)localObject;
      if ((paramString1 != null) && (paramString1.equals(""))) {
        paramString1 = null;
      }
      if ((paramString2 != null) && (paramString2.equals(""))) {
        paramString2 = null;
      }
      if ((paramString3 != null) && (paramString3.equals(""))) {
        paramString3 = null;
      }
      if ((paramString4 != null) && (paramString4.equals(""))) {
        paramString4 = null;
      }
      try
      {
        if ((paramString1 != null) && ((paramString1.equals("CTX")) || (paramString1.equals("CCD")) || (paramString1.equals("CIE")) || (paramString1.equals("WEB")) || (paramString1.equals("PPD"))) && (!Entitlements.entitlementTypeExists(paramString1))) {
          paramString1 = paramString1 + " + Addenda";
        }
        MultiEntitlement localMultiEntitlement;
        if ((paramString2 != null) && (paramString2.equals("Account")) && (localSecureUser.getUserType() == 1))
        {
          localMultiEntitlement = new MultiEntitlement();
          localMultiEntitlement.setOperations(new String[] { paramString1 });
          localMultiEntitlement.setObjects(new String[] { paramString2 }, new String[] { paramString3 });
          return EntitlementsUtil.checkAccountEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localMultiEntitlement, localSecureUser.getBusinessID()) == null;
        }
        if ((paramString1 != null) && (paramString4 != null) && (paramString4.equalsIgnoreCase("true")))
        {
          localMultiEntitlement = new MultiEntitlement();
          localMultiEntitlement.setOperations(new String[] { paramString1 });
          localMultiEntitlement.setObjects(new String[] { paramString2 }, new String[] { paramString3 });
          localMultiEntitlement = ParentEntitlementsCache.appendParentEntitlements(localMultiEntitlement);
          return Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localMultiEntitlement) == null;
        }
        if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), new Entitlement(paramString1, paramString2, paramString3))) {
          return true;
        }
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
      }
    }
    return false;
  }
  
  public int doAfterBody()
    throws JspTagException
  {
    try
    {
      BodyContent localBodyContent = getBodyContent();
      if (this.bodyContent != null) {
        localBodyContent.writeOut(getPreviousOut());
      }
    }
    catch (IOException localIOException)
    {
      throw new JspTagException("Error parsing <jtf:include... in doAfterBody\r\n" + localIOException);
    }
    return 0;
  }
  
  private void a(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, jdField_if(paramString));
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing(jdField_if("\n\tException: "), paramThrowable);
  }
  
  private String jdField_if(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("cinclude");
    if (this.jdField_try != null)
    {
      localStringBuffer.append(" value1=" + this.jdField_try);
      if (this.jdField_int != null) {
        localStringBuffer.append(" value2=" + this.jdField_int);
      }
      if (this.jdField_for != null) {
        localStringBuffer.append(" operator=" + this.jdField_for);
      }
    }
    if (this.jdField_case != null) {
      localStringBuffer.append(" ifEntitledFor=" + this.jdField_case);
    }
    if (this.a != null) {
      localStringBuffer.append(" ifNotEntitledFor=" + this.a);
    }
    if (this.jdField_do != null) {
      localStringBuffer.append(" objectType=" + this.jdField_do);
    }
    if (this.jdField_new != null) {
      localStringBuffer.append(" objectId=" + this.jdField_new);
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.cinclude
 * JD-Core Version:    0.7.0.1
 */