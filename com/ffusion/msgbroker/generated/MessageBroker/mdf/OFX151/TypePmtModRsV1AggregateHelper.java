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
/*  13:    */ public abstract class TypePmtModRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePmtModRsV1Aggregate clone(TypePmtModRsV1Aggregate paramTypePmtModRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePmtModRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePmtModRsV1Aggregate localTypePmtModRsV1Aggregate = new TypePmtModRsV1Aggregate();
/*  23: 21 */     localTypePmtModRsV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtModRsV1Aggregate.CurDef)));
/*  24: 22 */     localTypePmtModRsV1Aggregate.CurDefExists = paramTypePmtModRsV1Aggregate.CurDefExists;
/*  25: 23 */     localTypePmtModRsV1Aggregate.SrvrTID = paramTypePmtModRsV1Aggregate.SrvrTID;
/*  26: 24 */     localTypePmtModRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypePmtModRsV1Aggregate.PmtInfo);
/*  27: 25 */     localTypePmtModRsV1Aggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.clone(paramTypePmtModRsV1Aggregate.PmtPrcSts);
/*  28: 26 */     localTypePmtModRsV1Aggregate.PmtPrcStsExists = paramTypePmtModRsV1Aggregate.PmtPrcStsExists;
/*  29: 27 */     return localTypePmtModRsV1Aggregate;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static TypePmtModRsV1Aggregate read(InputStream paramInputStream)
/*  33:    */   {
/*  34: 33 */     TypePmtModRsV1Aggregate localTypePmtModRsV1Aggregate = new TypePmtModRsV1Aggregate();
/*  35: 34 */     localTypePmtModRsV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  36: 35 */     localTypePmtModRsV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/*  37: 36 */     localTypePmtModRsV1Aggregate.SrvrTID = paramInputStream.read_string();
/*  38: 37 */     localTypePmtModRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/*  39: 38 */     localTypePmtModRsV1Aggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.read(paramInputStream);
/*  40: 39 */     localTypePmtModRsV1Aggregate.PmtPrcStsExists = paramInputStream.read_boolean();
/*  41: 40 */     return localTypePmtModRsV1Aggregate;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void write(OutputStream paramOutputStream, TypePmtModRsV1Aggregate paramTypePmtModRsV1Aggregate)
/*  45:    */   {
/*  46: 47 */     if (paramTypePmtModRsV1Aggregate == null) {
/*  47: 49 */       paramTypePmtModRsV1Aggregate = new TypePmtModRsV1Aggregate();
/*  48:    */     }
/*  49: 51 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypePmtModRsV1Aggregate.CurDef);
/*  50: 52 */     paramOutputStream.write_boolean(paramTypePmtModRsV1Aggregate.CurDefExists);
/*  51: 53 */     paramOutputStream.write_string(paramTypePmtModRsV1Aggregate.SrvrTID);
/*  52: 54 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypePmtModRsV1Aggregate.PmtInfo);
/*  53: 55 */     TypePmtPrcStsAggregateHelper.write(paramOutputStream, paramTypePmtModRsV1Aggregate.PmtPrcSts);
/*  54: 56 */     paramOutputStream.write_boolean(paramTypePmtModRsV1Aggregate.PmtPrcStsExists);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String _idl()
/*  58:    */   {
/*  59: 61 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtModRsV1Aggregate";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static TypeCode type()
/*  63:    */   {
/*  64: 68 */     if (_type == null)
/*  65:    */     {
/*  66: 70 */       ORB localORB = ORB.init();
/*  67: 71 */       StructMember[] arrayOfStructMember = 
/*  68: 72 */         {
/*  69: 73 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/*  70: 74 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  71: 75 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  72: 76 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null), 
/*  73: 77 */         new StructMember("PmtPrcSts", TypePmtPrcStsAggregateHelper.type(), null), 
/*  74: 78 */         new StructMember("PmtPrcStsExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  75:    */       
/*  76: 80 */       _type = localORB.create_struct_tc(id(), "TypePmtModRsV1Aggregate", arrayOfStructMember);
/*  77:    */     }
/*  78: 82 */     return _type;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void insert(Any paramAny, TypePmtModRsV1Aggregate paramTypePmtModRsV1Aggregate)
/*  82:    */   {
/*  83: 89 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  84: 90 */     write(localOutputStream, paramTypePmtModRsV1Aggregate);
/*  85: 91 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static TypePmtModRsV1Aggregate extract(Any paramAny)
/*  89:    */   {
/*  90: 97 */     if (!paramAny.type().equal(type())) {
/*  91: 99 */       throw new BAD_OPERATION();
/*  92:    */     }
/*  93:101 */     return read(paramAny.create_input_stream());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String id()
/*  97:    */   {
/*  98:106 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtModRsV1Aggregate:1.0";
/*  99:    */   }
/* 100:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtModRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */