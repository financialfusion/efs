/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.ObjectVal;
/*   4:    */ import org.omg.CORBA.Any;
/*   5:    */ import org.omg.CORBA.BAD_OPERATION;
/*   6:    */ import org.omg.CORBA.ORB;
/*   7:    */ import org.omg.CORBA.StructMember;
/*   8:    */ import org.omg.CORBA.TCKind;
/*   9:    */ import org.omg.CORBA.TypeCode;
/*  10:    */ import org.omg.CORBA.portable.InputStream;
/*  11:    */ import org.omg.CORBA.portable.OutputStream;
/*  12:    */ 
/*  13:    */ public abstract class TypeExtdPmtAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeExtdPmtAggregate clone(TypeExtdPmtAggregate paramTypeExtdPmtAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeExtdPmtAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeExtdPmtAggregate localTypeExtdPmtAggregate = new TypeExtdPmtAggregate();
/*  23: 21 */     localTypeExtdPmtAggregate.ExtdPmtFor = ((EnumExtdPmtForEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeExtdPmtAggregate.ExtdPmtFor)));
/*  24: 22 */     localTypeExtdPmtAggregate.ExtdPmtForExists = paramTypeExtdPmtAggregate.ExtdPmtForExists;
/*  25: 23 */     localTypeExtdPmtAggregate.ExtdPmtChk = paramTypeExtdPmtAggregate.ExtdPmtChk;
/*  26: 24 */     localTypeExtdPmtAggregate.ExtdPmtChkExists = paramTypeExtdPmtAggregate.ExtdPmtChkExists;
/*  27: 25 */     localTypeExtdPmtAggregate.ExtdPmtUn = TypeExtdPmtUnHelper.clone(paramTypeExtdPmtAggregate.ExtdPmtUn);
/*  28: 26 */     return localTypeExtdPmtAggregate;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static TypeExtdPmtAggregate read(InputStream paramInputStream)
/*  32:    */   {
/*  33: 32 */     TypeExtdPmtAggregate localTypeExtdPmtAggregate = new TypeExtdPmtAggregate();
/*  34: 33 */     localTypeExtdPmtAggregate.ExtdPmtFor = EnumExtdPmtForEnumHelper.read(paramInputStream);
/*  35: 34 */     localTypeExtdPmtAggregate.ExtdPmtForExists = paramInputStream.read_boolean();
/*  36: 35 */     localTypeExtdPmtAggregate.ExtdPmtChk = paramInputStream.read_long();
/*  37: 36 */     localTypeExtdPmtAggregate.ExtdPmtChkExists = paramInputStream.read_boolean();
/*  38: 37 */     localTypeExtdPmtAggregate.ExtdPmtUn = TypeExtdPmtUnHelper.read(paramInputStream);
/*  39: 38 */     return localTypeExtdPmtAggregate;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void write(OutputStream paramOutputStream, TypeExtdPmtAggregate paramTypeExtdPmtAggregate)
/*  43:    */   {
/*  44: 45 */     if (paramTypeExtdPmtAggregate == null) {
/*  45: 47 */       paramTypeExtdPmtAggregate = new TypeExtdPmtAggregate();
/*  46:    */     }
/*  47: 49 */     EnumExtdPmtForEnumHelper.write(paramOutputStream, paramTypeExtdPmtAggregate.ExtdPmtFor);
/*  48: 50 */     paramOutputStream.write_boolean(paramTypeExtdPmtAggregate.ExtdPmtForExists);
/*  49: 51 */     paramOutputStream.write_long(paramTypeExtdPmtAggregate.ExtdPmtChk);
/*  50: 52 */     paramOutputStream.write_boolean(paramTypeExtdPmtAggregate.ExtdPmtChkExists);
/*  51: 53 */     TypeExtdPmtUnHelper.write(paramOutputStream, paramTypeExtdPmtAggregate.ExtdPmtUn);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static String _idl()
/*  55:    */   {
/*  56: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeExtdPmtAggregate";
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static TypeCode type()
/*  60:    */   {
/*  61: 65 */     if (_type == null)
/*  62:    */     {
/*  63: 67 */       ORB localORB = ORB.init();
/*  64: 68 */       StructMember[] arrayOfStructMember = 
/*  65: 69 */         {
/*  66: 70 */         new StructMember("ExtdPmtFor", EnumExtdPmtForEnumHelper.type(), null), 
/*  67: 71 */         new StructMember("ExtdPmtForExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  68: 72 */         new StructMember("ExtdPmtChk", localORB.get_primitive_tc(TCKind.tk_long), null), 
/*  69: 73 */         new StructMember("ExtdPmtChkExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  70: 74 */         new StructMember("ExtdPmtUn", TypeExtdPmtUnHelper.type(), null) };
/*  71:    */       
/*  72: 76 */       _type = localORB.create_struct_tc(id(), "TypeExtdPmtAggregate", arrayOfStructMember);
/*  73:    */     }
/*  74: 78 */     return _type;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void insert(Any paramAny, TypeExtdPmtAggregate paramTypeExtdPmtAggregate)
/*  78:    */   {
/*  79: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  80: 86 */     write(localOutputStream, paramTypeExtdPmtAggregate);
/*  81: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static TypeExtdPmtAggregate extract(Any paramAny)
/*  85:    */   {
/*  86: 93 */     if (!paramAny.type().equal(type())) {
/*  87: 95 */       throw new BAD_OPERATION();
/*  88:    */     }
/*  89: 97 */     return read(paramAny.create_input_stream());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String id()
/*  93:    */   {
/*  94:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeExtdPmtAggregate:1.0";
/*  95:    */   }
/*  96:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPmtAggregateHelper
 * JD-Core Version:    0.7.0.1
 */