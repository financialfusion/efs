package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FieldDefinition;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.util.StringTable;
import com.ffusion.csil.handlers.FileImporterHandler;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateMappingDefinitionTask
  extends ExtendedBaseTask
{
  String dz = null;
  String dA = "CustomMappingErrors";
  public static final String RESOURCE_BUNDLE_NAME = "com.ffusion.beans.fileimporter.resources";
  public static final String ERROR_MESSAGE_PREFIX = "ErrorMessage";
  public static final int ERROR_MESSAGE_NAME_REQUIRED = 1;
  public static final int ERROR_MESSAGE_DUPLICATE_MAPPING = 2;
  public static final int ERROR_MESSAGE_RECORD_DELIMITER_OR_LENGTH_REQUIRED = 3;
  public static final int ERROR_MESSAGE_SPECIFY_ONLY_DELIMITER_OR_LENGTH = 4;
  public static final int ERROR_MESSAGE_NAME_TOO_LONG = 5;
  public static final int ERROR_MESSAGE_DESCRIPTION_TOO_LONG = 6;
  public static final int ERROR_MESSAGE_REQUIRED_FIELD_MISSING = 7;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    StringTable localStringTable = new StringTable();
    localHttpSession.setAttribute(this.dA, localStringTable);
    MappingDefinition localMappingDefinition = (MappingDefinition)localHttpSession.getAttribute("MappingDefinition");
    if (localMappingDefinition == null)
    {
      this.error = 23000;
      return super.getTaskErrorURL();
    }
    MappingDefinitions localMappingDefinitions = (MappingDefinitions)localHttpSession.getAttribute("MappingDefinitions");
    if (localMappingDefinitions == null)
    {
      this.error = 23001;
      return super.getTaskErrorURL();
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.fileimporter.resources", localLocale);
    if (localResourceBundle == null)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    return validateMappingDefinition(localMappingDefinition, localMappingDefinitions, localStringTable, localLocale);
  }
  
  protected String validateMappingDefinition(MappingDefinition paramMappingDefinition, MappingDefinitions paramMappingDefinitions, StringTable paramStringTable, Locale paramLocale)
  {
    String str1 = super.getSuccessURL();
    if (paramMappingDefinition == null)
    {
      this.error = 23000;
      return super.getTaskErrorURL();
    }
    if (paramMappingDefinitions == null)
    {
      this.error = 23001;
      return super.getTaskErrorURL();
    }
    if ((paramMappingDefinition.getName() == null) || (paramMappingDefinition.getName().equals("")))
    {
      paramStringTable.setKey("Name");
      paramStringTable.setValue(getErrorMessage(1, paramLocale));
      this.error = 23021;
      return super.getTaskErrorURL();
    }
    Object[] arrayOfObject;
    if ((paramMappingDefinition.getName().trim().length() == 0) || (paramMappingDefinition.getName().trim().length() > 255))
    {
      paramStringTable.setKey("Name");
      arrayOfObject = new Object[] { "255" };
      paramStringTable.setValue(getErrorMessage(5, arrayOfObject, paramLocale));
      this.error = 23022;
      return super.getTaskErrorURL();
    }
    if ((paramMappingDefinition.getDescription() != null) && (paramMappingDefinition.getDescription().trim().length() > 255))
    {
      paramStringTable.setKey("Description");
      arrayOfObject = new Object[] { "255" };
      paramStringTable.setValue(getErrorMessage(6, arrayOfObject, paramLocale));
      this.error = 23023;
      return super.getTaskErrorURL();
    }
    for (int i = 0; i < paramMappingDefinitions.size(); i++)
    {
      localObject1 = (MappingDefinition)paramMappingDefinitions.get(i);
      if ((((MappingDefinition)localObject1).getName().equalsIgnoreCase(paramMappingDefinition.getName())) && (((MappingDefinition)localObject1).getMappingID() != paramMappingDefinition.getMappingID()))
      {
        paramStringTable.setKey("Name");
        paramStringTable.setValue(getErrorMessage(2, paramLocale));
        this.error = 23024;
        return super.getTaskErrorURL();
      }
    }
    OutputFormat localOutputFormat = null;
    Object localObject1 = new ArrayList();
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap = new HashMap();
    Object localObject2;
    Object localObject3;
    try
    {
      localOutputFormat = FileImporterHandler.getOutputFormat(new SecureUser(), paramMappingDefinition.getOutputFormatName(), new HashMap());
      localObject1 = localOutputFormat.getRequiredFieldList();
      localArrayList1 = localOutputFormat.getFieldList();
      int j = 0;
      Iterator localIterator = localArrayList1.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (String)localIterator.next();
        localHashMap.put(localObject2, (String)((ArrayList)localObject1).get(j++));
      }
      localObject2 = localOutputFormat.getMatchRecordOptionsDisplayNames();
      ArrayList localArrayList2 = localOutputFormat.getMatchRecordOptions();
      if ((paramMappingDefinition.getUpdateRecordsBy() == 3) && (((ArrayList)localObject2).size() > 0))
      {
        localHashMap.clear();
        for (int i2 = 0; i2 < localArrayList1.size(); i2++)
        {
          localObject3 = (String)localArrayList1.get(i2);
          boolean bool2 = false;
          if ((((String)localObject3).indexOf("Amount") != -1) || (((String)localObject3).indexOf("Credit or Debit") != -1)) {
            bool2 = true;
          }
          for (j = 0; j < ((ArrayList)localObject2).size(); j++)
          {
            String str4 = (String)((ArrayList)localObject2).get(j);
            if (str4.indexOf((String)localObject3) != -1)
            {
              if ((localArrayList2.indexOf("Name") == j) && ((paramMappingDefinition.getMatchRecordsBy() == 0) || (paramMappingDefinition.getMatchRecordsBy() == 4) || (paramMappingDefinition.getMatchRecordsBy() == 2))) {
                bool2 = true;
              }
              if ((localArrayList2.indexOf("ID") == j) && ((paramMappingDefinition.getMatchRecordsBy() == 1) || (paramMappingDefinition.getMatchRecordsBy() == 4) || (paramMappingDefinition.getMatchRecordsBy() == 2))) {
                bool2 = true;
              }
              if ((localArrayList2.indexOf("Account") == j) && ((paramMappingDefinition.getMatchRecordsBy() == 4) || (paramMappingDefinition.getMatchRecordsBy() == 3))) {
                bool2 = true;
              }
            }
          }
          localHashMap.put(localObject3, "" + bool2);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    FieldDefinitions localFieldDefinitions = paramMappingDefinition.getFieldDefinitions();
    if (paramMappingDefinition.getInputFormatType() == 1)
    {
      int k = -1;
      if (paramMappingDefinition.getRecordDelimiterType() == 2)
      {
        if (paramMappingDefinition.getRecordLength() == 0)
        {
          paramStringTable.setKey("DelimiterLength");
          paramStringTable.setValue(getErrorMessage(3, paramLocale));
          this.error = 23025;
          return super.getTaskErrorURL();
        }
        k = paramMappingDefinition.getRecordLength();
      }
      else if (paramMappingDefinition.getRecordLength() != 0)
      {
        paramStringTable.setKey("DelimiterLength");
        paramStringTable.setValue(getErrorMessage(4, paramLocale));
        this.error = 23026;
        return super.getTaskErrorURL();
      }
      localObject2 = new BitSet();
      for (int n = 0; n < localFieldDefinitions.size(); n++)
      {
        localObject3 = (FieldDefinition)localFieldDefinitions.get(n);
        String str2 = ((FieldDefinition)localObject3).getFieldStartString();
        String str3 = ((FieldDefinition)localObject3).getFieldEndString();
        int i4 = 0;
        if ((str2 != null) && (!str2.equals(""))) {
          try
          {
            Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            this.error = 23011;
            return super.getTaskErrorURL();
          }
        }
        if ((str3 != null) && (!str3.equals(""))) {
          try
          {
            Integer.parseInt(str3);
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            this.error = 23012;
            return super.getTaskErrorURL();
          }
        }
        int i5 = ((FieldDefinition)localObject3).getFieldStart();
        int i6 = ((FieldDefinition)localObject3).getFieldEnd();
        boolean bool3 = Boolean.valueOf((String)localHashMap.get(((FieldDefinition)localObject3).getName())).booleanValue();
        if (i5 == 0)
        {
          if (i6 == 0)
          {
            i4 = 1;
            if ((!bool3) || ((((FieldDefinition)localObject3).getDefaultValue() != null) && (((FieldDefinition)localObject3).getDefaultValue().length() > 0))) {
              continue;
            }
            this.error = 23027;
            paramStringTable.setKey("Field_" + ((FieldDefinition)localObject3).getName());
            paramStringTable.setValue(getErrorMessage(7, paramLocale));
          }
          else
          {
            this.error = 23012;
            return super.getTaskErrorURL();
          }
        }
        else
        {
          if (i5 < 0)
          {
            this.error = 23011;
            return super.getTaskErrorURL();
          }
          if (i6 < i5)
          {
            this.error = 23012;
            return super.getTaskErrorURL();
          }
        }
        if ((k > 0) && (i6 > k))
        {
          this.error = 23019;
          return super.getTaskErrorURL();
        }
        if ((i4 == 0) && (!ensureNoFixedLengthFieldsOverlap(i5, i6, localObject2)))
        {
          this.error = 23018;
          return super.getTaskErrorURL();
        }
      }
    }
    else
    {
      int[] arrayOfInt = new int[localFieldDefinitions.size()];
      for (int m = 0; m < localFieldDefinitions.size(); m++)
      {
        FieldDefinition localFieldDefinition = (FieldDefinition)localFieldDefinitions.get(m);
        int i1 = localFieldDefinition.getFieldNumber();
        boolean bool1 = Boolean.valueOf((String)localHashMap.get(localFieldDefinition.getName())).booleanValue();
        if (i1 == 0)
        {
          if ((bool1) && ((localFieldDefinition.getDefaultValue() == null) || (localFieldDefinition.getDefaultValue().length() <= 0)))
          {
            this.error = 23027;
            paramStringTable.setKey("Field_" + localFieldDefinition.getName());
            paramStringTable.setValue(getErrorMessage(7, paramLocale));
          }
        }
        else
        {
          if (i1 < 0)
          {
            this.error = 23010;
            return super.getTaskErrorURL();
          }
          for (int i3 = 0; i3 < m; i3++) {
            if ((i1 != 0) && (i1 == arrayOfInt[i3]))
            {
              this.error = 23010;
              return super.getTaskErrorURL();
            }
          }
          arrayOfInt[m] = i1;
        }
      }
    }
    if (this.error != 0) {
      return super.getTaskErrorURL();
    }
    return str1;
  }
  
  protected static boolean ensureNoFixedLengthFieldsOverlap(int paramInt1, int paramInt2, Object paramObject)
  {
    BitSet localBitSet = (BitSet)paramObject;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (localBitSet.get(i)) {
        return false;
      }
    }
    for (i = paramInt1; i <= paramInt2; i++) {
      localBitSet.set(i);
    }
    return true;
  }
  
  public void setFailedValidationURL(String paramString)
  {
    this.dz = paramString;
  }
  
  public void setErrorCollectionName(String paramString)
  {
    this.dA = paramString;
  }
  
  protected static String getErrorMessage(int paramInt, Locale paramLocale)
  {
    return ResourceUtil.getString("ErrorMessage" + paramInt, "com.ffusion.beans.fileimporter.resources", paramLocale);
  }
  
  public static String getErrorMessage(int paramInt, Object[] paramArrayOfObject, Locale paramLocale)
  {
    if (paramArrayOfObject == null) {
      return ResourceUtil.getString("ErrorMessage" + paramInt, "com.ffusion.beans.fileimporter.resources", paramLocale);
    }
    return MessageFormat.format(ResourceUtil.getString("ErrorMessage" + paramInt, "com.ffusion.beans.fileimporter.resources", paramLocale), paramArrayOfObject);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ValidateMappingDefinitionTask
 * JD-Core Version:    0.7.0.1
 */