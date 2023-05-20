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
/*  13:    */ public abstract class TypePmtModRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePmtModRsAggregate clone(TypePmtModRsAggregate paramTypePmtModRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePmtModRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePmtModRsAggregate localTypePmtModRsAggregate = new TypePmtModRsAggregate();
/*  23: 21 */     localTypePmtModRsAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtModRsAggregate.CurDef)));
/*  24: 22 */     localTypePmtModRsAggregate.CurDefExists = paramTypePmtModRsAggregate.CurDefExists;
/*  25: 23 */     localTypePmtModRsAggregate.SrvrTID = paramTypePmtModRsAggregate.SrvrTID;
/*  26: 24 */     localTypePmtModRsAggregate.PmtInfo = TypePmtInfoAggregateHelper.clone(paramTypePmtModRsAggregate.PmtInfo);
/*  27: 25 */     localTypePmtModRsAggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.clone(paramTypePmtModRsAggregate.PmtPrcSts);
/*  28: 26 */     localTypePmtModRsAggregate.PmtPrcStsExists = paramTypePmtModRsAggregate.PmtPrcStsExists;
/*  29: 27 */     return localTypePmtModRsAggregate;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static TypePmtModRsAggregate read(InputStream paramInputStream)
/*  33:    */   {
/*  34: 33 */     TypePmtModRsAggregate localTypePmtModRsAggregate = new TypePmtModRsAggregate();
/*  35: 34 */     localTypePmtModRsAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  36: 35 */     localTypePmtModRsAggregate.CurDefExists = paramInputStream.read_boolean();
/*  37: 36 */     localTypePmtModRsAggregate.SrvrTID = paramInputStream.read_string();
/*  38: 37 */     localTypePmtModRsAggregate.PmtInfo = TypePmtInfoAggregateHelper.read(paramInputStream);
/*  39: 38 */     localTypePmtModRsAggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.read(paramInputStream);
/*  40: 39 */     localTypePmtModRsAggregate.PmtPrcStsExists = paramInputStream.read_boolean();
/*  41: 40 */     return localTypePmtModRsAggregate;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void write(OutputStream paramOutputStream, TypePmtModRsAggregate paramTypePmtModRsAggregate)
/*  45:    */   {
/*  46: 47 */     if (paramTypePmtModRsAggregate == null) {
/*  47: 49 */       paramTypePmtModRsAggregate = new TypePmtModRsAggregate();
/*  48:    */     }
/*  49: 51 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypePmtModRsAggregate.CurDef);
/*  50: 52 */     paramOutputStream.write_boolean(paramTypePmtModRsAggregate.CurDefExists);
/*  51: 53 */     paramOutputStream.write_string(paramTypePmtModRsAggregate.SrvrTID);
/*  52: 54 */     TypePmtInfoAggregateHelper.write(paramOutputStream, paramTypePmtModRsAggregate.PmtInfo);
/*  53: 55 */     TypePmtPrcStsAggregateHelper.write(paramOutputStream, paramTypePmtModRsAggregate.PmtPrcSts);
/*  54: 56 */     paramOutputStream.write_boolean(paramTypePmtModRsAggregate.PmtPrcStsExists);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String _idl()
/*  58:    */   {
/*  59: 61 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtModRsAggregate";
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
/*  72: 76 */         new StructMember("PmtInfo", TypePmtInfoAggregateHelper.type(), null), 
/*  73: 77 */         new StructMember("PmtPrcSts", TypePmtPrcStsAggregateHelper.type(), null), 
/*  74: 78 */         new StructMember("PmtPrcStsExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  75:    */       
/*  76: 80 */       _type = localORB.create_struct_tc(id(), "TypePmtModRsAggregate", arrayOfStructMember);
/*  77:    */     }
/*  78: 82 */     return _type;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void insert(Any paramAny, TypePmtModRsAggregate paramTypePmtModRsAggregate)
/*  82:    */   {
/*  83: 89 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  84: 90 */     write(localOutputStream, paramTypePmtModRsAggregate);
/*  85: 91 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static TypePmtModRsAggregate extract(Any paramAny)
/*  89:    */   {
/*  90: 97 */     if (!paramAny.type().equal(type())) {
/*  91: 99 */       throw new BAD_OPERATION();
/*  92:    */     }
/*  93:101 */     return read(paramAny.create_input_stream());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String id()
/*  97:    */   {
/*  98:106 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtModRsAggregate:1.0";
/*  99:    */   }
/* 100:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */