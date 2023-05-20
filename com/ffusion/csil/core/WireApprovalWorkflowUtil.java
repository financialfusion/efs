package com.ffusion.csil.core;

import java.util.Hashtable;

public class WireApprovalWorkflowUtil
{
  public static Hashtable apprWorkflowTree = new Hashtable();
  
  public static String[] getOperationsByWireActivityType(String paramString)
  {
    String[] arrayOfString = (String[])apprWorkflowTree.get(paramString);
    if (arrayOfString != null) {
      return arrayOfString;
    }
    return null;
  }
  
  static
  {
    String[] arrayOfString1 = { "Wire Book Freeform", "Wire Book", "Wire Create" };
    String[] arrayOfString2 = { "Wire Domestic Freeform", "Wire Domestic", "Wire Create" };
    String[] arrayOfString3 = { "Wire International Freeform", "Wire International", "Wire Create" };
    String[] arrayOfString4 = { "Wire FED Freeform", "Wire FED", "Wire Create" };
    String[] arrayOfString5 = { "Wire Drawdown Freeform", "Wire Drawdown", "Wire Create" };
    String[] arrayOfString6 = { "Wire Host", "Wire Create" };
    apprWorkflowTree.put("BOOK_FREEFORM_WIRE", arrayOfString1);
    apprWorkflowTree.put("DOMESTIC_FREEFORM_WIRE", arrayOfString2);
    apprWorkflowTree.put("INTERNATIONAL_FREEFORM_WIRE", arrayOfString3);
    apprWorkflowTree.put("FED_FREEFORM_WIRE", arrayOfString4);
    apprWorkflowTree.put("DRAWDOWN_FREEFORM_WIRE", arrayOfString5);
    apprWorkflowTree.put("HOST_WIRE", arrayOfString6);
    String[] arrayOfString7 = { "Wire Entry", "Wire Book Templated", "Wire Book", "Wire Create" };
    String[] arrayOfString8 = { "Wire Entry", "Wire Domestic Templated", "Wire Domestic", "Wire Create" };
    String[] arrayOfString9 = { "Wire Entry", "Wire International Templated", "Wire International", "Wire Create" };
    String[] arrayOfString10 = { "Wire Entry", "Wire FED Templated", "Wire FED", "Wire Create" };
    String[] arrayOfString11 = { "Wire Entry", "Wire Drawdown Templated", "Wire Drawdown", "Wire Create" };
    apprWorkflowTree.put("BOOK_TEMPLATED_WIRE", arrayOfString7);
    apprWorkflowTree.put("DOMESTIC_TEMPLATED_WIRE", arrayOfString8);
    apprWorkflowTree.put("INTERNATIONAL_TEMPLATED_WIRE", arrayOfString9);
    apprWorkflowTree.put("FED_TEMPLATED_WIRE", arrayOfString10);
    apprWorkflowTree.put("DRAWDOWN_TEMPLATED_WIRE", arrayOfString11);
    String[] arrayOfString12 = { "Wire Templates Create" };
    apprWorkflowTree.put("TEMPLATE_WIRE", arrayOfString12);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.WireApprovalWorkflowUtil
 * JD-Core Version:    0.7.0.1
 */