package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageRecipient;
import com.ffusion.beans.messages.GlobalMessageRecipients;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

public class ResultSetWrapper
{
  private Connection f;
  private PreparedStatement jdField_char;
  private ResultSet jdField_for;
  private String k;
  private PreparedStatement n;
  private String jdField_new;
  private PreparedStatement jdField_byte;
  private String b;
  private ResultSet g;
  private String j;
  private UserWrapper jdField_null = new UserWrapper();
  private int jdField_try;
  private int c;
  private String jdField_case;
  private int d;
  private boolean h = false;
  private boolean jdField_long = false;
  private DBCookie jdField_int = null;
  private GlobalMessage m;
  private Iterator jdField_void;
  private GlobalMessageRecipient jdField_goto = null;
  private boolean jdField_if = false;
  private static String l = "SELECT c.directory_id, c.first_name, c.last_name, c.email_address, c.preferred_lang FROM customer c, customer_directory cd ";
  private static String jdField_do = "LEFT OUTER JOIN business_employee be ON be.directory_id = cd.directory_id LEFT OUTER JOIN business b ON b.business_id = be.business_id ";
  private static String i = "where c.directory_id=cd.directory_id and cd.account_status = 1 and c.bank_id = ?";
  private static String a = l + i + " and c.directory_id = ?";
  private static String e = "SELECT c.directory_id, c.first_name, c.last_name, c.email_address, c.preferred_lang FROM customer c, customer_directory cd, business_employee be, business b WHERE c.directory_id=cd.directory_id and be.directory_id=c.directory_id and be.business_id=b.business_id and cd.account_status = 1 and c.bank_id = ? and be.business_id = ?";
  private static String jdField_else = " ORDER BY c.preferred_lang";
  
  public ResultSetWrapper(Connection paramConnection, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.j = null;
    this.f = paramConnection;
    this.jdField_try = paramInt2;
    this.c = paramInt3;
    this.jdField_case = paramString;
    this.d = paramInt1;
  }
  
  public ResultSetWrapper(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
  {
    this.j = paramString1;
    this.jdField_try = paramInt2;
    this.c = paramInt3;
    this.jdField_case = paramString2;
    this.d = paramInt1;
  }
  
  public ResultSetWrapper(String paramString, GlobalMessage paramGlobalMessage)
  {
    this.j = paramString;
    this.f = null;
    this.m = paramGlobalMessage;
    this.jdField_try = paramGlobalMessage.getToGroupTypeValue();
    this.jdField_case = paramGlobalMessage.getBankId();
    this.d = paramGlobalMessage.getAffiliateBankIdValue();
    this.jdField_void = ((GlobalMessageRecipients)paramGlobalMessage.get("_recipients")).iterator();
  }
  
  public ResultSetWrapper(Connection paramConnection, GlobalMessage paramGlobalMessage)
  {
    this.j = null;
    this.f = paramConnection;
    this.m = paramGlobalMessage;
    this.jdField_try = paramGlobalMessage.getToGroupTypeValue();
    this.jdField_case = paramGlobalMessage.getBankId();
    this.d = paramGlobalMessage.getAffiliateBankIdValue();
    this.jdField_void = ((GlobalMessageRecipients)paramGlobalMessage.get("_recipients")).iterator();
  }
  
  public ResultSetWrapper() {}
  
  public UserWrapper getNextUser()
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    try
    {
      if ((this.f == null) && (this.j != null)) {
        this.f = DBUtil.getConnection(Profile.poolName, false, 2);
      }
      if (this.jdField_try == 4) {
        return jdField_int();
      }
      if (this.jdField_try != 1)
      {
        if (this.jdField_for == null) {
          jdField_if();
        }
        if (this.jdField_for != null) {
          if (this.jdField_for.next()) {
            localUserWrapper = a(this.jdField_for);
          } else {
            close();
          }
        }
      }
      else
      {
        return jdField_do();
      }
    }
    catch (Exception localException)
    {
      close();
      throw localException;
    }
    return localUserWrapper;
  }
  
  private UserWrapper jdField_int()
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    if (this.g != null) {
      localUserWrapper = jdField_if(this.jdField_goto);
    }
    while ((localUserWrapper == null) && (this.jdField_void.hasNext()))
    {
      this.jdField_goto = ((GlobalMessageRecipient)this.jdField_void.next());
      if (this.jdField_goto.getRcptType() == 1) {
        localUserWrapper = a(this.jdField_goto);
      } else {
        localUserWrapper = jdField_if(this.jdField_goto);
      }
    }
    return localUserWrapper;
  }
  
  private UserWrapper a(GlobalMessageRecipient paramGlobalMessageRecipient)
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    ResultSet localResultSet = null;
    try
    {
      if (this.n == null)
      {
        StringBuffer localStringBuffer = new StringBuffer(a);
        if (this.d != 0) {
          localStringBuffer.append(" and c.affiliate_bank_id = ?");
        }
        localStringBuffer.append(this.jdField_if ? jdField_else : "");
        this.jdField_new = localStringBuffer.toString();
        this.n = DBUtil.prepareStatement(this.f, this.jdField_new);
      }
      int i1 = 1;
      Profile.setStatementString(this.n, i1++, this.jdField_case, "bank_id", true);
      Profile.setStatementInt(this.n, i1++, paramGlobalMessageRecipient.getRcptDirID(), true);
      if (this.d != 0) {
        Profile.setStatementInt(this.n, i1++, this.d, true);
      }
      localResultSet = DBUtil.executeQuery(this.n, this.jdField_new);
      if (localResultSet.next()) {
        localUserWrapper = a(localResultSet);
      }
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      localResultSet = null;
    }
    return localUserWrapper;
  }
  
  private UserWrapper jdField_if(GlobalMessageRecipient paramGlobalMessageRecipient)
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    if (this.g != null)
    {
      if (this.g.next())
      {
        localUserWrapper = a(this.g);
      }
      else
      {
        DBUtil.closeResultSet(this.g);
        this.g = null;
      }
      return localUserWrapper;
    }
    if (localUserWrapper == null)
    {
      if (this.jdField_byte == null)
      {
        StringBuffer localStringBuffer = new StringBuffer(e);
        if (this.d != 0) {
          localStringBuffer.append(" and b.affiliate_bank_id = ?");
        }
        localStringBuffer.append(this.jdField_if ? jdField_else : "");
        this.b = localStringBuffer.toString();
        this.jdField_byte = DBUtil.prepareStatement(this.f, this.b);
      }
      int i1 = 1;
      Profile.setStatementString(this.jdField_byte, i1++, this.jdField_case, "BANK_ID", true);
      Profile.setStatementInt(this.jdField_byte, i1++, paramGlobalMessageRecipient.getRcptDirID(), true);
      if (this.d != 0) {
        Profile.setStatementInt(this.jdField_byte, i1++, this.d, true);
      }
      this.g = DBUtil.executeQuery(this.jdField_byte, this.b);
      if (this.g.next()) {
        localUserWrapper = a(this.g);
      }
      if (localUserWrapper == null)
      {
        DBUtil.closeResultSet(this.g);
        this.g = null;
      }
    }
    return localUserWrapper;
  }
  
  private UserWrapper jdField_do()
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    if (!this.h) {
      localUserWrapper = jdField_for();
    }
    if ((localUserWrapper == null) && (!this.jdField_long)) {
      localUserWrapper = a();
    }
    return localUserWrapper;
  }
  
  private UserWrapper jdField_for()
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    EntitlementGroup localEntitlementGroup = null;
    if (this.jdField_int == null) {
      this.jdField_int = new DBCookie();
    }
    localEntitlementGroup = Entitlements.getChildrenByGroupType(this.c, "ConsumerAdmin", this.jdField_int);
    if (this.jdField_char == null)
    {
      localObject1 = new StringBuffer(a);
      if (this.d != 0) {
        ((StringBuffer)localObject1).append(" and c.affiliate_bank_id = ?");
      }
      ((StringBuffer)localObject1).append(this.jdField_if ? jdField_else : "");
      this.k = ((StringBuffer)localObject1).toString();
      this.jdField_char = DBUtil.prepareStatement(this.f, this.k);
    }
    Object localObject1 = null;
    int i1 = 0;
    int i2 = 0;
    ResultSet localResultSet = null;
    while ((localEntitlementGroup != null) && (localUserWrapper == null))
    {
      i2 = Entitlements.getChildrenByGroupType(localEntitlementGroup.getGroupId(), "USER").getGroup(0).getGroupId();
      localObject1 = ((EntitlementGroupMember)Entitlements.getMembers(i2).get(0)).getId();
      try
      {
        i1 = Integer.parseInt((String)localObject1);
      }
      catch (Exception localException)
      {
        i1 = 0;
      }
      if (i1 > 0)
      {
        int i3 = 1;
        i3 = Profile.setStatementString(this.jdField_char, i3, this.jdField_case, "bank_id", true);
        i3 = Profile.setStatementInt(this.jdField_char, i3, i1, true);
        if (this.d != 0) {
          i3 = Profile.setStatementInt(this.jdField_char, i3, this.d, true);
        }
        try
        {
          localResultSet = DBUtil.executeQuery(this.jdField_char, this.k);
          if (localResultSet.next())
          {
            localUserWrapper = a(localResultSet);
            localUserWrapper = this.jdField_null;
          }
        }
        finally
        {
          DBUtil.closeResultSet(localResultSet);
        }
      }
      if (localUserWrapper == null) {
        localEntitlementGroup = Entitlements.getChildrenByGroupType(this.c, "ConsumerAdmin", this.jdField_int);
      }
    }
    if (localEntitlementGroup == null)
    {
      this.h = true;
      if (this.jdField_int != null)
      {
        this.jdField_int.close();
        this.jdField_int = null;
      }
      DBUtil.closeStatement(this.jdField_char);
      this.jdField_char = null;
    }
    return localUserWrapper;
  }
  
  private UserWrapper a()
    throws Exception
  {
    UserWrapper localUserWrapper = null;
    if (this.jdField_for != null) {
      if (this.jdField_for.next())
      {
        localUserWrapper = a(this.jdField_for);
      }
      else
      {
        DBUtil.closeResultSet(this.jdField_for);
        this.jdField_for = null;
      }
    }
    if (localUserWrapper == null)
    {
      EntitlementGroup localEntitlementGroup = null;
      if (this.jdField_int == null) {
        this.jdField_int = new DBCookie();
      }
      localEntitlementGroup = Entitlements.getChildrenByGroupType(this.c, "BusinessAdmin", this.jdField_int);
      Object localObject;
      if (this.jdField_char == null)
      {
        localObject = new StringBuffer(e);
        if (this.d != 0) {
          ((StringBuffer)localObject).append(" and b.affiliate_bank_id = ?");
        }
        ((StringBuffer)localObject).append(this.jdField_if ? jdField_else : "");
        this.k = ((StringBuffer)localObject).toString();
        this.jdField_char = DBUtil.prepareStatement(this.f, this.k);
      }
      int i1 = 0;
      while ((localEntitlementGroup != null) && (localUserWrapper == null))
      {
        localObject = ((EntitlementGroupMember)Entitlements.getMembers(localEntitlementGroup.getGroupId()).get(0)).getId();
        try
        {
          i1 = Integer.parseInt((String)localObject);
        }
        catch (Exception localException)
        {
          i1 = 0;
        }
        if (i1 > 0)
        {
          int i2 = 1;
          i2 = Profile.setStatementString(this.jdField_char, i2, this.jdField_case, "bank_id", true);
          i2 = Profile.setStatementInt(this.jdField_char, i2, i1, true);
          if (this.d != 0) {
            i2 = Profile.setStatementInt(this.jdField_char, i2, this.d, true);
          }
          this.jdField_for = DBUtil.executeQuery(this.jdField_char, this.k);
          if (this.jdField_for.next()) {
            localUserWrapper = a(this.jdField_for);
          }
        }
        if (localUserWrapper == null)
        {
          DBUtil.closeResultSet(this.jdField_for);
          this.jdField_for = null;
          localEntitlementGroup = Entitlements.getChildrenByGroupType(this.c, "BusinessAdmin", this.jdField_int);
        }
      }
      if (localEntitlementGroup == null)
      {
        this.jdField_long = true;
        if (this.jdField_int != null)
        {
          this.jdField_int.close();
          this.jdField_int = null;
        }
        DBUtil.closeAll(this.jdField_char, this.jdField_for);
        this.jdField_for = null;
        this.jdField_char = null;
      }
    }
    return localUserWrapper;
  }
  
  private UserWrapper a(ResultSet paramResultSet)
    throws Exception
  {
    this.jdField_null.setDirectoryID(paramResultSet.getInt(1));
    this.jdField_null.setName(paramResultSet.getString(2) + " " + paramResultSet.getString(3));
    this.jdField_null.setEmail(paramResultSet.getString(4));
    this.jdField_null.setPreferredLanguage(paramResultSet.getString(5));
    return this.jdField_null;
  }
  
  private void jdField_if()
    throws Exception
  {
    try
    {
      if (this.jdField_try != 1)
      {
        String str = null;
        StringBuffer localStringBuffer = new StringBuffer(l);
        if ((this.d != 0) && (this.jdField_try != 3)) {
          localStringBuffer.append(jdField_do);
        }
        localStringBuffer.append(i);
        if (this.jdField_try == 3)
        {
          localStringBuffer.append(" and customer_type = ?");
          str = Integer.toString(2);
        }
        else if (this.jdField_try == 2)
        {
          localStringBuffer.append(" and customer_type= ?");
          str = Integer.toString(1);
        }
        if (this.d != 0) {
          if (this.jdField_try == 3) {
            localStringBuffer.append(" and c.affiliate_bank_id = ?");
          } else {
            localStringBuffer.append(" and (c.affiliate_bank_id = ? or b.affiliate_bank_id = ?)");
          }
        }
        localStringBuffer.append(this.jdField_if ? jdField_else : "");
        this.jdField_char = DBUtil.prepareStatement(this.f, localStringBuffer.toString());
        int i1 = 1;
        i1 = Profile.setStatementString(this.jdField_char, i1, this.jdField_case, "bank_id", true);
        if (str != null) {
          i1 = Profile.setStatementString(this.jdField_char, i1, str, "CUSTOMER_TYPE", true);
        }
        if (this.d != 0)
        {
          i1 = Profile.setStatementInt(this.jdField_char, i1, this.d, true);
          if (this.jdField_try != 3) {
            i1 = Profile.setStatementInt(this.jdField_char, i1, this.d, true);
          }
        }
        this.jdField_for = DBUtil.executeQuery(this.jdField_char, localStringBuffer.toString());
      }
    }
    catch (Exception localException)
    {
      close();
      throw localException;
    }
  }
  
  public void close()
  {
    if (this.jdField_int != null)
    {
      this.jdField_int.close();
      this.jdField_int = null;
    }
    DBUtil.closeAll(this.jdField_byte, this.g);
    this.g = null;
    this.jdField_byte = null;
    DBUtil.closeStatement(this.n);
    this.n = null;
    DBUtil.closeAll(this.jdField_char, this.jdField_for);
    this.jdField_for = null;
    this.jdField_char = null;
    if (this.j != null) {
      DBUtil.returnConnection(this.j, this.f);
    }
    this.j = null;
    this.f = null;
  }
  
  public void finalize()
  {
    close();
  }
  
  public void setOrderByLanguage(boolean paramBoolean)
  {
    this.jdField_if = paramBoolean;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ResultSetWrapper
 * JD-Core Version:    0.7.0.1
 */