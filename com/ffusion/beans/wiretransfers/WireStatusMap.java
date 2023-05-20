package com.ffusion.beans.wiretransfers;

import java.util.HashMap;

public class WireStatusMap
{
  public static HashMap statusMapToInt = null;
  public static HashMap statusMapToStr = null;
  
  public static int mapStatusToInt(String paramString)
  {
    Integer localInteger = (Integer)statusMapToInt.get(paramString);
    if (localInteger == null) {
      return 0;
    }
    return localInteger.intValue();
  }
  
  public static String mapToStatusToStr(int paramInt)
  {
    String str = (String)statusMapToStr.get(new Integer(paramInt));
    if (str == null) {
      return "UNKNOWN";
    }
    return str;
  }
  
  static
  {
    statusMapToInt = new HashMap();
    statusMapToStr = new HashMap();
    statusMapToInt.put("CREATED", new Integer(1));
    statusMapToInt.put("WILLPROCESSON", new Integer(2));
    statusMapToInt.put("INPROCESS", new Integer(12));
    statusMapToInt.put("ACCEPTED", new Integer(9));
    statusMapToInt.put("POSTEDON", new Integer(5));
    statusMapToInt.put("COMPLETED", new Integer(8));
    statusMapToInt.put("CONFIRMED", new Integer(10));
    statusMapToInt.put("ACKNOWLEDGED", new Integer(17));
    statusMapToInt.put("CANCELEDON", new Integer(3));
    statusMapToInt.put("REJECTED", new Integer(7));
    statusMapToInt.put("HELD", new Integer(11));
    statusMapToInt.put("BACKENDPENDING", new Integer(14));
    statusMapToInt.put("BACKENDFAILED", new Integer(27));
    statusMapToInt.put("BACKENDFAILED_NOTIF", new Integer(27));
    statusMapToInt.put("BACKENDREJECTED", new Integer(28));
    statusMapToInt.put("BACKENDREJECTED_NOTIF", new Integer(28));
    statusMapToInt.put("INFUNDSAPPROVAL", new Integer(4));
    statusMapToInt.put("FUNDSAPPROVALACTIVE", new Integer(4));
    statusMapToInt.put("FUNDSAPPROVED", new Integer(29));
    statusMapToInt.put("FUNDSPENDING", new Integer(13));
    statusMapToInt.put("NOFUNDS", new Integer(15));
    statusMapToInt.put("NOFUNDS_NOTIF", new Integer(15));
    statusMapToInt.put("NOFUNDSON", new Integer(15));
    statusMapToInt.put("NOFUNDSON_NOTIF", new Integer(15));
    statusMapToInt.put("FUNDSFAILEDON", new Integer(30));
    statusMapToInt.put("FUNDSFAILEDON_NOTIF", new Integer(30));
    statusMapToInt.put("IMMED_INPROCESS", new Integer(40));
    statusMapToInt.put("FAILED", new Integer(6));
    statusMapToInt.put("FAILEDON", new Integer(6));
    statusMapToInt.put("FAILEDON_NOTIF", new Integer(6));
    statusMapToInt.put("INFUNDSREVERT", new Integer(31));
    statusMapToInt.put("FUNDSREVERTACTIVE", new Integer(31));
    statusMapToInt.put("FUNDSREVERTFAILED", new Integer(32));
    statusMapToInt.put("FUNDSREVERTFAILED_NOTIF", new Integer(32));
    statusMapToInt.put("FUNDSREVERTED", new Integer(33));
    statusMapToInt.put("FUNDSREVERTED_NOTIF", new Integer(33));
    statusMapToInt.put("RELEASED", new Integer(18));
    statusMapToInt.put("RELEASEREJECTED", new Integer(19));
    statusMapToInt.put("RELEASEFAILED", new Integer(20));
    statusMapToInt.put("RELEASEPENDING", new Integer(21));
    statusMapToInt.put("TEMPLATE", new Integer(22));
    statusMapToInt.put("RECTEMPLATE", new Integer(23));
    statusMapToInt.put("MIXED", new Integer(26));
    statusMapToInt.put("CANCELED", new Integer(3));
    statusMapToInt.put("MODIFIED", new Integer(34));
    statusMapToInt.put("PENDING", new Integer(35));
    statusMapToInt.put("DUPLICATE", new Integer(36));
    statusMapToInt.put("LIMIT_CHECK_FAILED", new Integer(37));
    statusMapToInt.put("LIMIT_REVERT_FAILED", new Integer(38));
    statusMapToInt.put("APPROVAL_FAILED", new Integer(39));
    statusMapToInt.put("APPROVAL_PENDING", new Integer(100));
    statusMapToInt.put("APPROVAL_REJECTED", new Integer(101));
    statusMapToInt.put("APPROVAL_NOT_ALLOWED", new Integer(105));
    statusMapToStr.put(new Integer(18), "RELEASED");
    statusMapToStr.put(new Integer(19), "RELEASEREJECTED");
    statusMapToStr.put(new Integer(20), "RELEASEFAILED");
    statusMapToStr.put(new Integer(21), "RELEASEPENDING");
    statusMapToStr.put(new Integer(1), "CREATED");
    statusMapToStr.put(new Integer(2), "WILLPROCESSON");
    statusMapToStr.put(new Integer(12), "INPROCESS");
    statusMapToStr.put(new Integer(9), "ACCEPTED");
    statusMapToStr.put(new Integer(5), "POSTEDON");
    statusMapToStr.put(new Integer(8), "COMPLETED");
    statusMapToStr.put(new Integer(10), "CONFIRMED");
    statusMapToStr.put(new Integer(17), "ACKNOWLEDGED");
    statusMapToStr.put(new Integer(3), "CANCELEDON");
    statusMapToStr.put(new Integer(7), "REJECTED");
    statusMapToStr.put(new Integer(11), "HELD");
    statusMapToStr.put(new Integer(14), "BACKENDPENDING");
    statusMapToStr.put(new Integer(27), "BACKENDFAILED");
    statusMapToStr.put(new Integer(28), "BACKENDREJECTED");
    statusMapToStr.put(new Integer(4), "INFUNDSAPPROVAL");
    statusMapToStr.put(new Integer(29), "FUNDSAPPROVED");
    statusMapToStr.put(new Integer(13), "FUNDSPENDING");
    statusMapToStr.put(new Integer(15), "NOFUNDS");
    statusMapToStr.put(new Integer(30), "FUNDSFAILEDON");
    statusMapToStr.put(new Integer(40), "IMMED_INPROCESS");
    statusMapToStr.put(new Integer(6), "FAILED");
    statusMapToStr.put(new Integer(31), "INFUNDSREVERT");
    statusMapToStr.put(new Integer(32), "FUNDSREVERTFAILED");
    statusMapToStr.put(new Integer(33), "FUNDSREVERTED");
    statusMapToStr.put(new Integer(22), "TEMPLATE");
    statusMapToStr.put(new Integer(23), "RECTEMPLATE");
    statusMapToStr.put(new Integer(26), "MIXED");
    statusMapToStr.put(new Integer(3), "CANCELED");
    statusMapToStr.put(new Integer(34), "MODIFIED");
    statusMapToStr.put(new Integer(35), "PENDING");
    statusMapToStr.put(new Integer(36), "DUPLICATE");
    statusMapToStr.put(new Integer(37), "LIMIT_CHECK_FAILED");
    statusMapToStr.put(new Integer(38), "LIMIT_REVERT_FAILED");
    statusMapToStr.put(new Integer(39), "APPROVAL_FAILED");
    statusMapToStr.put(new Integer(100), "APPROVAL_PENDING");
    statusMapToStr.put(new Integer(101), "APPROVAL_REJECTED");
    statusMapToStr.put(new Integer(105), "APPROVAL_NOT_ALLOWED");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireStatusMap
 * JD-Core Version:    0.7.0.1
 */