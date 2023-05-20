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
/*  12:    */ public abstract class TypeAdjustmentAggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeAdjustmentAggregate clone(TypeAdjustmentAggregate paramTypeAdjustmentAggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypeAdjustmentAggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeAdjustmentAggregate localTypeAdjustmentAggregate = new TypeAdjustmentAggregate();
/*  22: 21 */     localTypeAdjustmentAggregate.AdjNo = paramTypeAdjustmentAggregate.AdjNo;
/*  23: 22 */     localTypeAdjustmentAggregate.AdjNoExists = paramTypeAdjustmentAggregate.AdjNoExists;
/*  24: 23 */     localTypeAdjustmentAggregate.AdjDesc = paramTypeAdjustmentAggregate.AdjDesc;
/*  25: 24 */     localTypeAdjustmentAggregate.AdjAmt = paramTypeAdjustmentAggregate.AdjAmt;
/*  26: 25 */     localTypeAdjustmentAggregate.AdjDate = paramTypeAdjustmentAggregate.AdjDate;
/*  27: 26 */     localTypeAdjustmentAggregate.AdjDateExists = paramTypeAdjustmentAggregate.AdjDateExists;
/*  28: 27 */     return localTypeAdjustmentAggregate;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static TypeAdjustmentAggregate read(InputStream paramInputStream)
/*  32:    */   {
/*  33: 33 */     TypeAdjustmentAggregate localTypeAdjustmentAggregate = new TypeAdjustmentAggregate();
/*  34: 34 */     localTypeAdjustmentAggregate.AdjNo = paramInputStream.read_string();
/*  35: 35 */     localTypeAdjustmentAggregate.AdjNoExists = paramInputStream.read_boolean();
/*  36: 36 */     localTypeAdjustmentAggregate.AdjDesc = paramInputStream.read_string();
/*  37: 37 */     localTypeAdjustmentAggregate.AdjAmt = paramInputStream.read_float();
/*  38: 38 */     localTypeAdjustmentAggregate.AdjDate = paramInputStream.read_string();
/*  39: 39 */     localTypeAdjustmentAggregate.AdjDateExists = paramInputStream.read_boolean();
/*  40: 40 */     return localTypeAdjustmentAggregate;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void write(OutputStream paramOutputStream, TypeAdjustmentAggregate paramTypeAdjustmentAggregate)
/*  44:    */   {
/*  45: 47 */     if (paramTypeAdjustmentAggregate == null) {
/*  46: 49 */       paramTypeAdjustmentAggregate = new TypeAdjustmentAggregate();
/*  47:    */     }
/*  48: 51 */     paramOutputStream.write_string(paramTypeAdjustmentAggregate.AdjNo);
/*  49: 52 */     paramOutputStream.write_boolean(paramTypeAdjustmentAggregate.AdjNoExists);
/*  50: 53 */     paramOutputStream.write_string(paramTypeAdjustmentAggregate.AdjDesc);
/*  51: 54 */     paramOutputStream.write_float(paramTypeAdjustmentAggregate.AdjAmt);
/*  52: 55 */     paramOutputStream.write_string(paramTypeAdjustmentAggregate.AdjDate);
/*  53: 56 */     paramOutputStream.write_boolean(paramTypeAdjustmentAggregate.AdjDateExists);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static String _idl()
/*  57:    */   {
/*  58: 61 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeAdjustmentAggregate";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static TypeCode type()
/*  62:    */   {
/*  63: 68 */     if (_type == null)
/*  64:    */     {
/*  65: 70 */       ORB localORB = ORB.init();
/*  66: 71 */       StructMember[] arrayOfStructMember = 
/*  67: 72 */         {
/*  68: 73 */         new StructMember("AdjNo", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  69: 74 */         new StructMember("AdjNoExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  70: 75 */         new StructMember("AdjDesc", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  71: 76 */         new StructMember("AdjAmt", localORB.get_primitive_tc(TCKind.tk_float), null), 
/*  72: 77 */         new StructMember("AdjDate", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  73: 78 */         new StructMember("AdjDateExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  74:    */       
/*  75: 80 */       _type = localORB.create_struct_tc(id(), "TypeAdjustmentAggregate", arrayOfStructMember);
/*  76:    */     }
/*  77: 82 */     return _type;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void insert(Any paramAny, TypeAdjustmentAggregate paramTypeAdjustmentAggregate)
/*  81:    */   {
/*  82: 89 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  83: 90 */     write(localOutputStream, paramTypeAdjustmentAggregate);
/*  84: 91 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static TypeAdjustmentAggregate extract(Any paramAny)
/*  88:    */   {
/*  89: 97 */     if (!paramAny.type().equal(type())) {
/*  90: 99 */       throw new BAD_OPERATION();
/*  91:    */     }
/*  92:101 */     return read(paramAny.create_input_stream());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static String id()
/*  96:    */   {
/*  97:106 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeAdjustmentAggregate:1.0";
/*  98:    */   }
/*  99:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAdjustmentAggregateHelper
 * JD-Core Version:    0.7.0.1
 */