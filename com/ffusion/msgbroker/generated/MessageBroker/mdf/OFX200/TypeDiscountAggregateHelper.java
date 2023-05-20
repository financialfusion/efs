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
/*  12:    */ public abstract class TypeDiscountAggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeDiscountAggregate clone(TypeDiscountAggregate paramTypeDiscountAggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypeDiscountAggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeDiscountAggregate localTypeDiscountAggregate = new TypeDiscountAggregate();
/*  22: 21 */     localTypeDiscountAggregate.DscRate = paramTypeDiscountAggregate.DscRate;
/*  23: 22 */     localTypeDiscountAggregate.DscAmt = paramTypeDiscountAggregate.DscAmt;
/*  24: 23 */     localTypeDiscountAggregate.DscDate = paramTypeDiscountAggregate.DscDate;
/*  25: 24 */     localTypeDiscountAggregate.DscDateExists = paramTypeDiscountAggregate.DscDateExists;
/*  26: 25 */     localTypeDiscountAggregate.DscDesc = paramTypeDiscountAggregate.DscDesc;
/*  27: 26 */     return localTypeDiscountAggregate;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeDiscountAggregate read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeDiscountAggregate localTypeDiscountAggregate = new TypeDiscountAggregate();
/*  33: 33 */     localTypeDiscountAggregate.DscRate = paramInputStream.read_float();
/*  34: 34 */     localTypeDiscountAggregate.DscAmt = paramInputStream.read_float();
/*  35: 35 */     localTypeDiscountAggregate.DscDate = paramInputStream.read_string();
/*  36: 36 */     localTypeDiscountAggregate.DscDateExists = paramInputStream.read_boolean();
/*  37: 37 */     localTypeDiscountAggregate.DscDesc = paramInputStream.read_string();
/*  38: 38 */     return localTypeDiscountAggregate;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeDiscountAggregate paramTypeDiscountAggregate)
/*  42:    */   {
/*  43: 45 */     if (paramTypeDiscountAggregate == null) {
/*  44: 47 */       paramTypeDiscountAggregate = new TypeDiscountAggregate();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_float(paramTypeDiscountAggregate.DscRate);
/*  47: 50 */     paramOutputStream.write_float(paramTypeDiscountAggregate.DscAmt);
/*  48: 51 */     paramOutputStream.write_string(paramTypeDiscountAggregate.DscDate);
/*  49: 52 */     paramOutputStream.write_boolean(paramTypeDiscountAggregate.DscDateExists);
/*  50: 53 */     paramOutputStream.write_string(paramTypeDiscountAggregate.DscDesc);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeDiscountAggregate";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("DscRate", localORB.get_primitive_tc(TCKind.tk_float), null), 
/*  66: 71 */         new StructMember("DscAmt", localORB.get_primitive_tc(TCKind.tk_float), null), 
/*  67: 72 */         new StructMember("DscDate", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  68: 73 */         new StructMember("DscDateExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  69: 74 */         new StructMember("DscDesc", localORB.get_primitive_tc(TCKind.tk_string), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeDiscountAggregate", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeDiscountAggregate paramTypeDiscountAggregate)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeDiscountAggregate);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeDiscountAggregate extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeDiscountAggregate:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDiscountAggregateHelper
 * JD-Core Version:    0.7.0.1
 */