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
/*  13:    */ public abstract class TypeIntraModRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeIntraModRsAggregate clone(TypeIntraModRsAggregate paramTypeIntraModRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeIntraModRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeIntraModRsAggregate localTypeIntraModRsAggregate = new TypeIntraModRsAggregate();
/*  23: 21 */     localTypeIntraModRsAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeIntraModRsAggregate.CurDef)));
/*  24: 22 */     localTypeIntraModRsAggregate.CurDefExists = paramTypeIntraModRsAggregate.CurDefExists;
/*  25: 23 */     localTypeIntraModRsAggregate.SrvrTID = paramTypeIntraModRsAggregate.SrvrTID;
/*  26: 24 */     localTypeIntraModRsAggregate.XferInfo = TypeXferInfoAggregateHelper.clone(paramTypeIntraModRsAggregate.XferInfo);
/*  27: 25 */     localTypeIntraModRsAggregate.XferPrcSts = TypeXferPrcStsAggregateHelper.clone(paramTypeIntraModRsAggregate.XferPrcSts);
/*  28: 26 */     localTypeIntraModRsAggregate.XferPrcStsExists = paramTypeIntraModRsAggregate.XferPrcStsExists;
/*  29: 27 */     return localTypeIntraModRsAggregate;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static TypeIntraModRsAggregate read(InputStream paramInputStream)
/*  33:    */   {
/*  34: 33 */     TypeIntraModRsAggregate localTypeIntraModRsAggregate = new TypeIntraModRsAggregate();
/*  35: 34 */     localTypeIntraModRsAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  36: 35 */     localTypeIntraModRsAggregate.CurDefExists = paramInputStream.read_boolean();
/*  37: 36 */     localTypeIntraModRsAggregate.SrvrTID = paramInputStream.read_string();
/*  38: 37 */     localTypeIntraModRsAggregate.XferInfo = TypeXferInfoAggregateHelper.read(paramInputStream);
/*  39: 38 */     localTypeIntraModRsAggregate.XferPrcSts = TypeXferPrcStsAggregateHelper.read(paramInputStream);
/*  40: 39 */     localTypeIntraModRsAggregate.XferPrcStsExists = paramInputStream.read_boolean();
/*  41: 40 */     return localTypeIntraModRsAggregate;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void write(OutputStream paramOutputStream, TypeIntraModRsAggregate paramTypeIntraModRsAggregate)
/*  45:    */   {
/*  46: 47 */     if (paramTypeIntraModRsAggregate == null) {
/*  47: 49 */       paramTypeIntraModRsAggregate = new TypeIntraModRsAggregate();
/*  48:    */     }
/*  49: 51 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeIntraModRsAggregate.CurDef);
/*  50: 52 */     paramOutputStream.write_boolean(paramTypeIntraModRsAggregate.CurDefExists);
/*  51: 53 */     paramOutputStream.write_string(paramTypeIntraModRsAggregate.SrvrTID);
/*  52: 54 */     TypeXferInfoAggregateHelper.write(paramOutputStream, paramTypeIntraModRsAggregate.XferInfo);
/*  53: 55 */     TypeXferPrcStsAggregateHelper.write(paramOutputStream, paramTypeIntraModRsAggregate.XferPrcSts);
/*  54: 56 */     paramOutputStream.write_boolean(paramTypeIntraModRsAggregate.XferPrcStsExists);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static String _idl()
/*  58:    */   {
/*  59: 61 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeIntraModRsAggregate";
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
/*  72: 76 */         new StructMember("XferInfo", TypeXferInfoAggregateHelper.type(), null), 
/*  73: 77 */         new StructMember("XferPrcSts", TypeXferPrcStsAggregateHelper.type(), null), 
/*  74: 78 */         new StructMember("XferPrcStsExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  75:    */       
/*  76: 80 */       _type = localORB.create_struct_tc(id(), "TypeIntraModRsAggregate", arrayOfStructMember);
/*  77:    */     }
/*  78: 82 */     return _type;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void insert(Any paramAny, TypeIntraModRsAggregate paramTypeIntraModRsAggregate)
/*  82:    */   {
/*  83: 89 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  84: 90 */     write(localOutputStream, paramTypeIntraModRsAggregate);
/*  85: 91 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static TypeIntraModRsAggregate extract(Any paramAny)
/*  89:    */   {
/*  90: 97 */     if (!paramAny.type().equal(type())) {
/*  91: 99 */       throw new BAD_OPERATION();
/*  92:    */     }
/*  93:101 */     return read(paramAny.create_input_stream());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String id()
/*  97:    */   {
/*  98:106 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeIntraModRsAggregate:1.0";
/*  99:    */   }
/* 100:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */