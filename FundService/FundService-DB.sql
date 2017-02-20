DROP TABLE IF EXISTS portfolio;
DROP TABLE IF EXISTS fund_account;
DROP TABLE IF EXISTS external_capital_account_type_to_currency;
DROP TABLE IF EXISTS external_capital_account_type_to_external_open_organization_type;
DROP TABLE IF EXISTS external_trade_account_to_investment_scope;
DROP TABLE IF EXISTS external_trade_account;
DROP TABLE IF EXISTS external_capital_account;
DROP TABLE IF EXISTS external_open_organization;
DROP TABLE IF EXISTS external_open_organization_type;
DROP TABLE IF EXISTS investment_scope;
DROP TABLE IF EXISTS external_trade_account_type;
DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS external_capital_account_type;
DROP TABLE IF EXISTS fund;

--����
--�����Ʒ
CREATE TABLE fund 
(
	id BIGINT NOT NULL auto_increment, 
	code VARCHAR(255), 
	name VARCHAR(255), 
	short_name VARCHAR(255), 
	total_shares BIGINT, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�����Ʒ�˻�
CREATE TABLE fund_account 
(
	id BIGINT NOT NULL auto_increment, 
	name VARCHAR(255), 
	fund_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--Ͷ�����
CREATE TABLE portfolio 
(
	id BIGINT NOT NULL auto_increment, 
	create_date DATE, 
	name VARCHAR(255), 
	fund_account_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ�ʽ��˻�����
CREATE TABLE external_capital_account_type 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--����
CREATE TABLE currency 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	chinese_name VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ�ʽ��˻���������ֵĹ���
CREATE TABLE external_capital_account_type_to_currency 
(
	currency_id BIGINT NOT NULL, 
	external_capital_account_type_id BIGINT NOT NULL
)CHARACTER SET = utf8;

--�ⲿ�����ṹ����
CREATE TABLE external_open_organization_type 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ��������
CREATE TABLE external_open_organization 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	short_name VARCHAR(255), 
	external_open_organization_type_id BIGINT NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ�ʽ��˻��������ⲿ�����������͵Ĺ�ϵ
CREATE TABLE external_capital_account_type_to_external_open_organization_type 
(
	external_capital_account_type_id BIGINT NOT NULL, 
	external_open_organization_type_id BIGINT NOT NULL
)CHARACTER SET = utf8;

--�ⲿ�ʽ��˻�
CREATE TABLE external_capital_account 
(
	id BIGINT NOT NULL auto_increment, 
	external_capital_account VARCHAR(255), 
	fund_id BIGINT NOT NULL, 
	external_capital_account_type_id BIGINT NOT NULL, 
	external_open_organization_id BIGINT NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ�����˻���Ͷ�ʷ�Χ�Ĺ�ϵ
CREATE TABLE external_trade_account_to_investment_scope 
(
	external_trade_account_id BIGINT NOT NULL, 
	investment_scope_id BIGINT NOT NULL
)CHARACTER SET = utf8;

--Ͷ�ʷ�Χ
CREATE TABLE investment_scope 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	external_trade_account_type_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ�����˻�����
CREATE TABLE external_trade_account_type 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	external_capital_account_type_id BIGINT NOT NULL, 
	support_external_capital_account_currency_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--�ⲿ�����˻�
CREATE TABLE external_trade_account 
(
	id BIGINT NOT NULL auto_increment, 
	external_trade_account VARCHAR(255), 
	external_trade_account_type_id BIGINT NOT NULL, 
	external_capital_account_id BIGINT NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--���
ALTER TABLE fund_account ADD CONSTRAINT fk_fund FOREIGN KEY (fund_id) REFERENCES fund (id);
ALTER TABLE portfolio ADD CONSTRAINT fk_fund_account FOREIGN KEY (fund_account_id) REFERENCES fund_account (id);
ALTER TABLE external_capital_account_type_to_currency ADD CONSTRAINT fk_external_capital_account FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_capital_account_type_to_currency ADD CONSTRAINT fk_currency FOREIGN KEY (currency_id) REFERENCES currency (id);
ALTER TABLE external_open_organization ADD CONSTRAINT fk_external_open_organization_type FOREIGN KEY (external_open_organization_type_id) REFERENCES external_open_organization_type (id);
ALTER TABLE external_capital_account_type_to_external_open_organization_type ADD CONSTRAINT fk_open_organization_type FOREIGN KEY (external_open_organization_type_id) REFERENCES external_open_organization_type (id);
ALTER TABLE external_capital_account_type_to_external_open_organization_type ADD CONSTRAINT fk_capital_account_type FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_capital_account ADD CONSTRAINT fk_external_capital_account_type FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_capital_account ADD CONSTRAINT fk_fund_for_extcapital FOREIGN KEY (fund_id) REFERENCES fund (id);
ALTER TABLE external_capital_account ADD CONSTRAINT fk_external_open_organization FOREIGN KEY (external_open_organization_id) REFERENCES external_open_organization (id);
ALTER TABLE external_trade_account ADD CONSTRAINT fk_external_trade_account_type FOREIGN KEY (external_trade_account_type_id) REFERENCES external_trade_account_type (id);
ALTER TABLE external_trade_account ADD CONSTRAINT fk_ext_capital_account foreign key (external_capital_account_id) references external_capital_account (id);
ALTER TABLE external_trade_account_type ADD CONSTRAINT fk_belong_to_capital_account_type FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_trade_account_type ADD CONSTRAINT fk_support_currency FOREIGN KEY (support_external_capital_account_currency_id) REFERENCES currency (id);
ALTER TABLE investment_scope ADD CONSTRAINT fk_support_external_trade_account_type FOREIGN KEY (external_trade_account_type_id) REFERENCES external_trade_account_type (id);

--��ʼ������
INSERT INTO external_capital_account_type(id,name) 
	VALUES(1,'��ͨ�ʽ��˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(2,'�����ʽ��˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(3,'����Ʒ��֤���˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(4,'�ڻ���֤���˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(5,'��ծ�ǽ����ʽ�ר��');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(6,'�Ϻ������������ʽ�ר��');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(7,'�ƽ����˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(8,'������˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(9,'���ר�����л���');
INSERT INTO external_capital_account_type(id,name)  
	VALUES(10,'����֤ȯ�����˻�');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(11,'�Զ������˻�');

INSERT INTO currency(id, name, chinese_name) 
	VALUES(1,'ALL','ȫ����');
INSERT INTO currency(id,name, chinese_name) 
	VALUES(2,'CNY','�����');
INSERT INTO currency(id,name, chinese_name) 
	VALUES(3,'USD','��Ԫ');
INSERT INTO currency(id,name, chinese_name) 
	VALUES(4,'HKD','�۱�');
	
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(1,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(1,3);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(1,4);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(2,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(3,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(4,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(5,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(6,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(7,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(8,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(9,4);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(10,3);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(11,1);	

INSERT INTO external_open_organization_type(id,name) 
	VALUES(1,'���Ļ���');
INSERT INTO external_open_organization_type(id,name) 
	VALUES(2,'֤ȯ��˾');
INSERT INTO external_open_organization_type(id,name) 
	VALUES(3,'�ڻ���˾');
INSERT INTO external_open_organization_type(id,name) 
	VALUES(4,'����˾');

INSERT INTO external_open_organization(id, name, short_name, external_open_organization_type_id) 
	VALUES(1,'�Ϻ�֤ȯ������', '�Ͻ���', 1);
INSERT INTO external_open_organization(id, name, short_name, external_open_organization_type_id) 
	VALUES(2,'����֤ȯ�������ι�˾', '����֤ȯ', 2);
INSERT INTO external_open_organization(id,name, short_name, external_open_organization_type_id) 
	VALUES(3,'�����ڻ����޹�˾', '�����ڻ�', 3);

INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(1,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(2,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(3,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(4,3);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,1);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,3);	
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,4);

INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (1, '����A���˻�', 1, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (2, '����B���˻�', 1, 3);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (3, '��������֤ȯ�˻�', 2, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (4, '����A���˻�', 1, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (5, '����B���˻�', 1, 4);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (6, '��������֤ȯ�˻�', 2, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (7, 'ȫ����С��ҵ�ɷ�ת��ϵͳ�˻�', 1, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (8, '���������ױ���', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (9, '���������ױ���', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (10, '֣�������ױ���', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (11, '�����ڻ��˻�', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (12, '���֤ȯ�˻�', 9, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (13, '����֤ȯ�����˻�', 10, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (14, '��ծ�Ǳ���ծȯ�˻�', 5, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (15, '�Ϻ�������ծȯ�˻�', 6, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (16, '�ƽ����˻�', 7, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (17, '������˻�', 8, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (18, '�Զ������˻�', 11, 1);
	
INSERT INTO investment_scope (id, name, external_trade_account_type_id)
VALUES (1, '��֤A�ɾ��۽��׷���', 1);