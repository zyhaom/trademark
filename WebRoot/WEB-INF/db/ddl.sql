DROP DATABASE IF EXISTS `pic_search`;
CREATE DATABASE `pic_search` CHARACTER SET utf8;

drop table if exists t_PicType;

/*==============================================================*/
/* Table: t_PicType                                             */
/*==============================================================*/
create table t_PicType
(
   PicTypeID            int(2) not null comment '图片类型id',
   PicTypeName          varchar(20) comment '图片类别名称',
   PiicTypeFolder       varchar(60) comment '图片所在目录的全路径',
   primary key (PicTypeID)
);

alter table t_PicType comment '图片类型字典表';

drop table if exists T_pic_prop;

/*==============================================================*/
/* Table: T_pic_prop                                            */
/*==============================================================*/
create table T_pic_prop
(
   picID                varchar(36) not null comment '图片ID',
   productName          varchar(50) comment '图片代表的产品名称',
   brandName            varchar(50) comment '图片代表的品牌名称',
   companyName          varchar(50) comment '图片代表的产品公司名称',
   originAddr           varchar(100) comment '图片代表的产品的出厂地',
   originTime           varchar(20) comment '图片代表的产品的出厂时间',
   price                float(6,2) comment '图片代表的产品的价格',
   primary key (picID)
);

alter table T_pic_prop comment '图片固有属性信息';

drop table if exists t_pic;

/*==============================================================*/
/* Table: t_pic                                                 */
/*==============================================================*/
create table t_pic
(
   PicID                varchar(36) not null comment '图片id',
   T_p_picID            varchar(36) comment '图片ID',
   PicTypeID            int(2) comment '图片类型id',
  PicName              varchar(50) comment '图片名称',
   primary key (PicID)
);

alter table t_pic comment '图片表';

alter table t_pic add constraint FK_Reference_1 foreign key (T_p_picID)
      references T_pic_prop (picID) on delete restrict on update restrict;

alter table t_pic add constraint FK_Reference_2 foreign key (PicTypeID)
      references t_PicType (PicTypeID) on delete restrict on update restrict;
