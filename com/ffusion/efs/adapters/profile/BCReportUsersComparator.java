package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.util.CollatorUtil;
import java.text.Collator;
import java.util.Comparator;

class BCReportUsersComparator
  implements Comparator
{
  Businesses a;
  
  public BCReportUsersComparator(Businesses paramBusinesses)
  {
    this.a = paramBusinesses;
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) && (paramObject2 == null)) {
      return 0;
    }
    if (paramObject1 == null) {
      return -1;
    }
    if (paramObject2 == null) {
      return 1;
    }
    User localUser1 = (User)paramObject1;
    User localUser2 = (User)paramObject2;
    Collator localCollator = CollatorUtil.getCollator(localUser1.getLocale());
    if ((!(localUser1 instanceof BusinessEmployee)) && ((localUser2 instanceof BusinessEmployee))) {
      return -1;
    }
    if (((localUser1 instanceof BusinessEmployee)) && (!(localUser2 instanceof BusinessEmployee))) {
      return 1;
    }
    if (((localUser1 instanceof BusinessEmployee)) && ((localUser2 instanceof BusinessEmployee)))
    {
      Business localBusiness1 = this.a.getById(((BusinessEmployee)localUser1).getBusinessId());
      Business localBusiness2 = this.a.getById(((BusinessEmployee)localUser2).getBusinessId());
      String str1 = localBusiness1.getBusinessName();
      String str2 = localBusiness2.getBusinessName();
      int j = localCollator.compare(str1, str2);
      if (j != 0) {
        return j;
      }
    }
    int i = localCollator.compare(localUser1.getLastName(), localUser2.getLastName());
    if (i != 0) {
      return i;
    }
    return localCollator.compare(localUser1.getFirstName(), localUser2.getFirstName());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.BCReportUsersComparator
 * JD-Core Version:    0.7.0.1
 */