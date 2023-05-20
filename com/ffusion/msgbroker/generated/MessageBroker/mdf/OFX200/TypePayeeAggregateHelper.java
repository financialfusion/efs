/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.Any;
/*   4:    */ import org.omg.CORBA.BAD_OPERATION;
/*   5:    */ import org.omg.CORBA.ORB;
/*   6:    */ import org.omg.CORBA.StructMember;
/*   7:    */ import org.omg.CORBA.TCKind;
/*   8:    */ import org.omg.CORBA.TypeCode;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class TypePayeeAggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePayeeAggregate clone(TypePayeeAggregate paramTypePayeeAggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypePayeeAggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePayeeAggregate localTypePayeeAggregate = new TypePayeeAggregate();
/*  22: 21 */     localTypePayeeAggregate.Name = paramTypePayeeAggregate.Name;
/*  23: 22 */     localTypePayeeAggregate.AddressCm = TypeAddressCmHelper.clone(paramTypePayeeAggregate.AddressCm);
/*  24: 23 */     localTypePayeeAggregate.City = paramTypePayeeAggregate.City;
/*  25: 24 */     localTypePayeeAggregate.State = paramTypePayeeAggregate.State;
/*  26: 25 */     localTypePayeeAggregate.PostalCode = paramTypePayeeAggregate.PostalCode;
/*  27: 26 */     localTypePayeeAggregate.Country = paramTypePayeeAggregate.Country;
/*  28: 27 */     localTypePayeeAggregate.CountryExists = paramTypePayeeAggregate.CountryExists;
/*  29: 28 */     localTypePayeeAggregate.Phone = paramTypePayeeAggregate.Phone;
/*  30: 29 */     return localTypePayeeAggregate;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static TypePayeeAggregate read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 35 */     TypePayeeAggregate localTypePayeeAggregate = new TypePayeeAggregate();
/*  36: 36 */     localTypePayeeAggregate.Name = paramInputStream.read_string();
/*  37: 37 */     localTypePayeeAggregate.AddressCm = TypeAddressCmHelper.read(paramInputStream);
/*  38: 38 */     localTypePayeeAggregate.City = paramInputStream.read_string();
/*  39: 39 */     localTypePayeeAggregate.State = paramInputStream.read_string();
/*  40: 40 */     localTypePayeeAggregate.PostalCode = paramInputStream.read_string();
/*  41: 41 */     localTypePayeeAggregate.Country = paramInputStream.read_string();
/*  42: 42 */     localTypePayeeAggregate.CountryExists = paramInputStream.read_boolean();
/*  43: 43 */     localTypePayeeAggregate.Phone = paramInputStream.read_string();
/*  44: 44 */     return localTypePayeeAggregate;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void write(OutputStream paramOutputStream, TypePayeeAggregate paramTypePayeeAggregate)
/*  48:    */   {
/*  49: 51 */     if (paramTypePayeeAggregate == null) {
/*  50: 53 */       paramTypePayeeAggregate = new TypePayeeAggregate();
/*  51:    */     }
/*  52: 55 */     paramOutputStream.write_string(paramTypePayeeAggregate.Name);
/*  53: 56 */     TypeAddressCmHelper.write(paramOutputStream, paramTypePayeeAggregate.AddressCm);
/*  54: 57 */     paramOutputStream.write_string(paramTypePayeeAggregate.City);
/*  55: 58 */     paramOutputStream.write_string(paramTypePayeeAggregate.State);
/*  56: 59 */     paramOutputStream.write_string(paramTypePayeeAggregate.PostalCode);
/*  57: 60 */     paramOutputStream.write_string(paramTypePayeeAggregate.Country);
/*  58: 61 */     paramOutputStream.write_boolean(paramTypePayeeAggregate.CountryExists);
/*  59: 62 */     paramOutputStream.write_string(paramTypePayeeAggregate.Phone);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String _idl()
/*  63:    */   {
/*  64: 67 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeAggregate";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static TypeCode type()
/*  68:    */   {
/*  69: 74 */     if (_type == null)
/*  70:    */     {
/*  71: 76 */       ORB localORB = ORB.init();
/*  72: 77 */       StructMember[] arrayOfStructMember = 
/*  73: 78 */         {
/*  74: 79 */         new StructMember("Name", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  75: 80 */         new StructMember("AddressCm", TypeAddressCmHelper.type(), null), 
/*  76: 81 */         new StructMember("City", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  77: 82 */         new StructMember("State", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  78: 83 */         new StructMember("PostalCode", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  79: 84 */         new StructMember("Country", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  80: 85 */         new StructMember("CountryExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  81: 86 */         new StructMember("Phone", localORB.get_primitive_tc(TCKind.tk_string), null) };
/*  82:    */       
/*  83: 88 */       _type = localORB.create_struct_tc(id(), "TypePayeeAggregate", arrayOfStructMember);
/*  84:    */     }
/*  85: 90 */     return _type;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void insert(Any paramAny, TypePayeeAggregate paramTypePayeeAggregate)
/*  89:    */   {
/*  90: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  91: 98 */     write(localOutputStream, paramTypePayeeAggregate);
/*  92: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static TypePayeeAggregate extract(Any paramAny)
/*  96:    */   {
/*  97:105 */     if (!paramAny.type().equal(type())) {
/*  98:107 */       throw new BAD_OPERATION();
/*  99:    */     }
/* 100:109 */     return read(paramAny.create_input_stream());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static String id()
/* 104:    */   {
/* 105:114 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeAggregate:1.0";
/* 106:    */   }
/* 107:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregateHelper
 * JD-Core Version:    0.7.0.1
 */