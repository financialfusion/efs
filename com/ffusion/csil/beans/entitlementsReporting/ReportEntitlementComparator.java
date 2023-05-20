package com.ffusion.csil.beans.entitlementsReporting;

import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.util.beans.LocaleableBean;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ReportEntitlementComparator
  extends LocaleableBean
  implements Comparator
{
  EntitlementTypePropertyLists jdField_if;
  private String jdField_do;
  private String a;
  
  public ReportEntitlementComparator(EntitlementTypePropertyLists paramEntitlementTypePropertyLists, Locale paramLocale)
  {
    super(paramLocale);
    this.jdField_if = paramEntitlementTypePropertyLists;
    this.jdField_do = EntitlementsUtil.getPropertyNameLanguageCountry("display name", paramLocale);
    this.a = EntitlementsUtil.getPropertyNameLanguage("display name", paramLocale);
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    Entitlement localEntitlement1 = (Entitlement)paramObject1;
    Entitlement localEntitlement2 = (Entitlement)paramObject2;
    if ((localEntitlement1 == null) && (localEntitlement2 == null)) {
      return 0;
    }
    if (localEntitlement1 == null) {
      return -1;
    }
    if (localEntitlement2 == null) {
      return 1;
    }
    String str1 = localEntitlement1.getOperationName();
    String str2 = localEntitlement2.getOperationName();
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = this.jdField_if.getByOperationName(str1);
    EntitlementTypePropertyList localEntitlementTypePropertyList2 = this.jdField_if.getByOperationName(str2);
    if (localEntitlementTypePropertyList1 != null)
    {
      str1 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList1, this.jdField_do, this.a, "display name");
      if (str1 == null) {
        str1 = localEntitlement1.getOperationName();
      }
    }
    if (localEntitlementTypePropertyList2 != null)
    {
      str2 = EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList2, this.jdField_do, this.a, "display name");
      if (str2 == null) {
        str2 = localEntitlement2.getOperationName();
      }
    }
    int i = a(str1, str2);
    if (i != 0) {
      return i;
    }
    i = a(localEntitlement1.getObjectType(), localEntitlement2.getObjectType());
    if (i != 0) {
      return i;
    }
    return a(localEntitlement1.getObjectId(), localEntitlement2.getObjectId());
  }
  
  private int a(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    String str1 = paramString1.replace('-', ' ');
    String str2 = paramString2.replace('-', ' ');
    StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, " ");
    StringTokenizer localStringTokenizer2 = new StringTokenizer(str2, " ");
    while ((localStringTokenizer1.hasMoreTokens()) && (localStringTokenizer2.hasMoreTokens()))
    {
      String str3 = localStringTokenizer1.nextToken();
      String str4 = localStringTokenizer2.nextToken();
      int i = doGetCollator().compare(str3, str4);
      if (i != 0) {
        return i;
      }
    }
    if ((!localStringTokenizer1.hasMoreTokens()) && (localStringTokenizer2.hasMoreTokens())) {
      return -1;
    }
    if ((localStringTokenizer1.hasMoreTokens()) && (!localStringTokenizer2.hasMoreTokens())) {
      return 1;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlementsReporting.ReportEntitlementComparator
 * JD-Core Version:    0.7.0.1
 */