/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  12:    */ public abstract class TypePayeeV1AggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePayeeV1Aggregate clone(TypePayeeV1Aggregate paramTypePayeeV1Aggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypePayeeV1Aggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePayeeV1Aggregate localTypePayeeV1Aggregate = new TypePayeeV1Aggregate();
/*  22: 21 */     localTypePayeeV1Aggregate.Name = paramTypePayeeV1Aggregate.Name;
/*  23: 22 */     localTypePayeeV1Aggregate.AddressCm = TypeAddressCmHelper.clone(paramTypePayeeV1Aggregate.AddressCm);
/*  24: 23 */     localTypePayeeV1Aggregate.City = paramTypePayeeV1Aggregate.City;
/*  25: 24 */     localTypePayeeV1Aggregate.State = paramTypePayeeV1Aggregate.State;
/*  26: 25 */     localTypePayeeV1Aggregate.PostalCode = paramTypePayeeV1Aggregate.PostalCode;
/*  27: 26 */     localTypePayeeV1Aggregate.Country = paramTypePayeeV1Aggregate.Country;
/*  28: 27 */     localTypePayeeV1Aggregate.CountryExists = paramTypePayeeV1Aggregate.CountryExists;
/*  29: 28 */     localTypePayeeV1Aggregate.Phone = paramTypePayeeV1Aggregate.Phone;
/*  30: 29 */     return localTypePayeeV1Aggregate;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static TypePayeeV1Aggregate read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 35 */     TypePayeeV1Aggregate localTypePayeeV1Aggregate = new TypePayeeV1Aggregate();
/*  36: 36 */     localTypePayeeV1Aggregate.Name = paramInputStream.read_string();
/*  37: 37 */     localTypePayeeV1Aggregate.AddressCm = TypeAddressCmHelper.read(paramInputStream);
/*  38: 38 */     localTypePayeeV1Aggregate.City = paramInputStream.read_string();
/*  39: 39 */     localTypePayeeV1Aggregate.State = paramInputStream.read_string();
/*  40: 40 */     localTypePayeeV1Aggregate.PostalCode = paramInputStream.read_string();
/*  41: 41 */     localTypePayeeV1Aggregate.Country = paramInputStream.read_string();
/*  42: 42 */     localTypePayeeV1Aggregate.CountryExists = paramInputStream.read_boolean();
/*  43: 43 */     localTypePayeeV1Aggregate.Phone = paramInputStream.read_string();
/*  44: 44 */     return localTypePayeeV1Aggregate;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void write(OutputStream paramOutputStream, TypePayeeV1Aggregate paramTypePayeeV1Aggregate)
/*  48:    */   {
/*  49: 51 */     if (paramTypePayeeV1Aggregate == null) {
/*  50: 53 */       paramTypePayeeV1Aggregate = new TypePayeeV1Aggregate();
/*  51:    */     }
/*  52: 55 */     paramOutputStream.write_string(paramTypePayeeV1Aggregate.Name);
/*  53: 56 */     TypeAddressCmHelper.write(paramOutputStream, paramTypePayeeV1Aggregate.AddressCm);
/*  54: 57 */     paramOutputStream.write_string(paramTypePayeeV1Aggregate.City);
/*  55: 58 */     paramOutputStream.write_string(paramTypePayeeV1Aggregate.State);
/*  56: 59 */     paramOutputStream.write_string(paramTypePayeeV1Aggregate.PostalCode);
/*  57: 60 */     paramOutputStream.write_string(paramTypePayeeV1Aggregate.Country);
/*  58: 61 */     paramOutputStream.write_boolean(paramTypePayeeV1Aggregate.CountryExists);
/*  59: 62 */     paramOutputStream.write_string(paramTypePayeeV1Aggregate.Phone);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String _idl()
/*  63:    */   {
/*  64: 67 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeV1Aggregate";
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
/*  83: 88 */       _type = localORB.create_struct_tc(id(), "TypePayeeV1Aggregate", arrayOfStructMember);
/*  84:    */     }
/*  85: 90 */     return _type;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void insert(Any paramAny, TypePayeeV1Aggregate paramTypePayeeV1Aggregate)
/*  89:    */   {
/*  90: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  91: 98 */     write(localOutputStream, paramTypePayeeV1Aggregate);
/*  92: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static TypePayeeV1Aggregate extract(Any paramAny)
/*  96:    */   {
/*  97:105 */     if (!paramAny.type().equal(type())) {
/*  98:107 */       throw new BAD_OPERATION();
/*  99:    */     }
/* 100:109 */     return read(paramAny.create_input_stream());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static String id()
/* 104:    */   {
/* 105:114 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeV1Aggregate:1.0";
/* 106:    */   }
/* 107:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */