/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  13:    */ public abstract class TypeExtdPmtV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeExtdPmtV1Aggregate clone(TypeExtdPmtV1Aggregate paramTypeExtdPmtV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeExtdPmtV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeExtdPmtV1Aggregate localTypeExtdPmtV1Aggregate = new TypeExtdPmtV1Aggregate();
/*  23: 21 */     localTypeExtdPmtV1Aggregate.ExtdPmtFor = ((EnumExtdPmtForEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeExtdPmtV1Aggregate.ExtdPmtFor)));
/*  24: 22 */     localTypeExtdPmtV1Aggregate.ExtdPmtForExists = paramTypeExtdPmtV1Aggregate.ExtdPmtForExists;
/*  25: 23 */     localTypeExtdPmtV1Aggregate.ExtdPmtChk = paramTypeExtdPmtV1Aggregate.ExtdPmtChk;
/*  26: 24 */     localTypeExtdPmtV1Aggregate.ExtdPmtChkExists = paramTypeExtdPmtV1Aggregate.ExtdPmtChkExists;
/*  27: 25 */     localTypeExtdPmtV1Aggregate.ExtdPmtV1Un = TypeExtdPmtV1UnHelper.clone(paramTypeExtdPmtV1Aggregate.ExtdPmtV1Un);
/*  28: 26 */     return localTypeExtdPmtV1Aggregate;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static TypeExtdPmtV1Aggregate read(InputStream paramInputStream)
/*  32:    */   {
/*  33: 32 */     TypeExtdPmtV1Aggregate localTypeExtdPmtV1Aggregate = new TypeExtdPmtV1Aggregate();
/*  34: 33 */     localTypeExtdPmtV1Aggregate.ExtdPmtFor = EnumExtdPmtForEnumHelper.read(paramInputStream);
/*  35: 34 */     localTypeExtdPmtV1Aggregate.ExtdPmtForExists = paramInputStream.read_boolean();
/*  36: 35 */     localTypeExtdPmtV1Aggregate.ExtdPmtChk = paramInputStream.read_long();
/*  37: 36 */     localTypeExtdPmtV1Aggregate.ExtdPmtChkExists = paramInputStream.read_boolean();
/*  38: 37 */     localTypeExtdPmtV1Aggregate.ExtdPmtV1Un = TypeExtdPmtV1UnHelper.read(paramInputStream);
/*  39: 38 */     return localTypeExtdPmtV1Aggregate;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void write(OutputStream paramOutputStream, TypeExtdPmtV1Aggregate paramTypeExtdPmtV1Aggregate)
/*  43:    */   {
/*  44: 45 */     if (paramTypeExtdPmtV1Aggregate == null) {
/*  45: 47 */       paramTypeExtdPmtV1Aggregate = new TypeExtdPmtV1Aggregate();
/*  46:    */     }
/*  47: 49 */     EnumExtdPmtForEnumHelper.write(paramOutputStream, paramTypeExtdPmtV1Aggregate.ExtdPmtFor);
/*  48: 50 */     paramOutputStream.write_boolean(paramTypeExtdPmtV1Aggregate.ExtdPmtForExists);
/*  49: 51 */     paramOutputStream.write_long(paramTypeExtdPmtV1Aggregate.ExtdPmtChk);
/*  50: 52 */     paramOutputStream.write_boolean(paramTypeExtdPmtV1Aggregate.ExtdPmtChkExists);
/*  51: 53 */     TypeExtdPmtV1UnHelper.write(paramOutputStream, paramTypeExtdPmtV1Aggregate.ExtdPmtV1Un);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static String _idl()
/*  55:    */   {
/*  56: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeExtdPmtV1Aggregate";
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
/*  70: 74 */         new StructMember("ExtdPmtV1Un", TypeExtdPmtV1UnHelper.type(), null) };
/*  71:    */       
/*  72: 76 */       _type = localORB.create_struct_tc(id(), "TypeExtdPmtV1Aggregate", arrayOfStructMember);
/*  73:    */     }
/*  74: 78 */     return _type;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void insert(Any paramAny, TypeExtdPmtV1Aggregate paramTypeExtdPmtV1Aggregate)
/*  78:    */   {
/*  79: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  80: 86 */     write(localOutputStream, paramTypeExtdPmtV1Aggregate);
/*  81: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static TypeExtdPmtV1Aggregate extract(Any paramAny)
/*  85:    */   {
/*  86: 93 */     if (!paramAny.type().equal(type())) {
/*  87: 95 */       throw new BAD_OPERATION();
/*  88:    */     }
/*  89: 97 */     return read(paramAny.create_input_stream());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String id()
/*  93:    */   {
/*  94:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeExtdPmtV1Aggregate:1.0";
/*  95:    */   }
/*  96:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPmtV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */