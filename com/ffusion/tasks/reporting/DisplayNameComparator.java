package com.ffusion.tasks.reporting;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.util.CollatorUtil;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

class DisplayNameComparator
  implements Comparator
{
  private Collator jdField_do;
  private String jdField_if;
  private String a;
  
  public DisplayNameComparator(Locale paramLocale)
  {
    this.jdField_do = CollatorUtil.getCollator(paramLocale);
    this.jdField_if = EntitlementsUtil.getPropertyNameLanguageCountry("display name", paramLocale);
    this.a = EntitlementsUtil.getPropertyNameLanguage("display name", paramLocale);
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)paramObject1;
    EntitlementTypePropertyList localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)paramObject2;
    return this.jdField_do.compare(EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList1, this.jdField_if, this.a, "display name"), EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList2, this.jdField_if, this.a, "display name"));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.DisplayNameComparator
 * JD-Core Version:    0.7.0.1
 */