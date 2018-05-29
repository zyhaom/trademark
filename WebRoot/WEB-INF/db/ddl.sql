DROP DATABASE IF EXISTS `pic_search`;
CREATE DATABASE `pic_search` CHARACTER SET utf8;

drop table if exists t_PicType;

/*==============================================================*/
/* Table: t_PicType                                             */
/*==============================================================*/
create table t_PicType
(
   PicTypeID            int(2) not null comment 'ͼƬ����id',
   PicTypeName          varchar(20) comment 'ͼƬ�������',
   PiicTypeFolder       varchar(60) comment 'ͼƬ����Ŀ¼��ȫ·��',
   primary key (PicTypeID)
);

alter table t_PicType comment 'ͼƬ�����ֵ��';

drop table if exists T_pic_prop;

/*==============================================================*/
/* Table: T_pic_prop                                            */
/*==============================================================*/
create table T_pic_prop
(
   picID                varchar(36) not null comment 'ͼƬID',
   productName          varchar(50) comment 'ͼƬ����Ĳ�Ʒ����',
   brandName            varchar(50) comment 'ͼƬ�����Ʒ������',
   companyName          varchar(50) comment 'ͼƬ����Ĳ�Ʒ��˾����',
   originAddr           varchar(100) comment 'ͼƬ����Ĳ�Ʒ�ĳ�����',
   originTime           varchar(20) comment 'ͼƬ����Ĳ�Ʒ�ĳ���ʱ��',
   price                float(6,2) comment 'ͼƬ����Ĳ�Ʒ�ļ۸�',
   primary key (picID)
);

alter table T_pic_prop comment 'ͼƬ����������Ϣ';

drop table if exists t_pic;

/*==============================================================*/
/* Table: t_pic                                                 */
/*==============================================================*/
create table t_pic
(
   PicID                varchar(36) not null comment 'ͼƬid',
   T_p_picID            varchar(36) comment 'ͼƬID',
   PicTypeID            int(2) comment 'ͼƬ����id',
  PicName              varchar(50) comment 'ͼƬ����',
   primary key (PicID)
);

alter table t_pic comment 'ͼƬ��';

alter table t_pic add constraint FK_Reference_1 foreign key (T_p_picID)
      references T_pic_prop (picID) on delete restrict on update restrict;

alter table t_pic add constraint FK_Reference_2 foreign key (PicTypeID)
      references t_PicType (PicTypeID) on delete restrict on update restrict;
