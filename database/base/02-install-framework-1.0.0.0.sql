/* Création des tables */
CREATE TABLE FWK_THEME ( THEME_ID NUMBER(19), THEME_LABEL VARCHAR2(20 CHAR), THEME_NAME VARCHAR2(20 CHAR) ) LOGGING NOCOMPRESS NOCACHE NOPARALLEL MONITORING;
CREATE TABLE FWK_USER (
	USR_ID NUMBER(19),
	USR_VERSION NUMBER(10),
	USR_LOGIN VARCHAR2(20 CHAR),
	USR_PASSWD VARCHAR2(50 CHAR),
	USR_FIRST_NAME VARCHAR2(50 CHAR),
	USR_LAST_NAME VARCHAR2(50 CHAR),
	USR_LOCATION VARCHAR2(255 CHAR),
	USR_EMAIL VARCHAR2(255 CHAR),
	USR_PHONE VARCHAR2(255 CHAR),
	USR_FAX VARCHAR2(255 CHAR),
	USR_ACTIVATED NUMBER(1),
	USR_LOGICAL_ID VARCHAR2(30 CHAR),
	USER_DATE_CONNEXION DATE,
	THEME_ID NUMBER(19),
	USR_GROUPS VARCHAR2(255 CHAR)
) LOGGING NOCOMPRESS NOCACHE NOPARALLEL MONITORING;

CREATE TABLE FWK_DOMAIN ( DOM_ID NUMBER(19), DOM_VERSION NUMBER(10), DOM_NAME VARCHAR2(20 CHAR), DOM_DISPLAY_NAME VARCHAR2(255 CHAR), DOM_COMMENT VARCHAR2(255 CHAR), DOM_INDEX INTEGER ) LOGGING NOCOMPRESS NOCACHE NOPARALLEL MONITORING;
CREATE TABLE FWK_PARAMETER ( PRM_ID NUMBER(19), PRM_VERSION NUMBER(10), PRM_NAME VARCHAR2(50 CHAR), PRM_DISPLAY_NAME VARCHAR2(255 CHAR), PRM_COMMENT VARCHAR2(255 CHAR), PRM_INITIAL_VALUE VARCHAR2(255 CHAR), PRM_VALUE VARCHAR2(255 CHAR), DOM_ID NUMBER(19), PRM_INDEX INTEGER, PRM_MUTABLE INTEGER, PRM_MANDATORY INTEGER, PRM_TYPE VARCHAR2(255 CHAR) ) LOGGING NOCOMPRESS NOCACHE NOPARALLEL MONITORING;
CREATE TABLE FWK_BATCH_HISTO ( BATCH_ID NUMBER(19), BATCH_NAME VARCHAR2(20 CHAR), BATCH_DATE_LANCEMENT DATE, BATCH_STATUT VARCHAR2(20 CHAR)) LOGGING NOCOMPRESS NOCACHE NOPARALLEL MONITORING;
CREATE TABLE FWK_BATCH_MESSAGE (
	BATCH_MESSAGE_ID NUMBER(38),
	BATCH_ID NUMBER(19),
	BATCH_MESSAGE_CONTENT VARCHAR2(500 CHAR),
	BATCH_MESSAGE_LEVEL VARCHAR2(100 CHAR),
	BATCH_MESSAGE_NNI VARCHAR2(20 CHAR)
)LOGGING NOCOMPRESS NOCACHE NOPARALLEL MONITORING;

CREATE TABLE FWK_ACTION_LOG (
	ACT_ID      NUMBER(38) NOT NULL,
	ACT_DATE    DATE NOT NULL,
	ACT_ORIGIN  VARCHAR2(100 CHAR) NOT NULL,
	USR_ID      NUMBER(19) NULL,
	ACT_STATUS  VARCHAR2(255 CHAR) NOT NULL,
	ACT_TITLE   VARCHAR2(255 CHAR) NOT NULL
);

CREATE TABLE FWK_ACTION_LOG_MESSAGE (
	ALM_ID        NUMBER(38) NOT NULL,
	ALM_DATE      TIMESTAMP(6) NOT NULL,
	ALM_SEVERITY  VARCHAR2(50 CHAR) NOT NULL,
	ALM_MESSAGE   VARCHAR2(255 CHAR) NOT NULL,
	ACT_ID        NUMBER(38)
);

CREATE TABLE FWK_MAIL_REPORTING (
  MAIL_ID NUMBER(38,0) NOT NULL,
  MAIL_SUBJECT VARCHAR2(4000 CHAR) NOT NULL,
  MAIL_STATUS NUMBER(38,0) NOT NULL,
  MAIL_ERROR_MESSAGE VARCHAR2(4000 CHAR),
  MAIL_DATE_SENT TIMESTAMP NOT NULL,
  MAIL_SENDER VARCHAR2(350 CHAR) NOT NULL,
  MAIL_RECIPIENTS VARCHAR2(4000 CHAR) NOT NULL,
  MAIL_COPY_RECIPIENTS VARCHAR2(4000 CHAR),
  MAIL_BLIND_COPY_RECIPIENTS VARCHAR2(4000 CHAR),
  MAIL_ATTACHMENTS NUMBER(3,0) NOT NULL
);

/* Initialisation de données */
INSERT INTO FWK_PARAMETER 
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3080, 4, 'defaultUserGroup', 'Default User Groups', 'default user groups used at user creation', 
    'administrateur,registered', 'registered', 1001, 3, 0, 
    0, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3180, 3, 'enableUserUpdate', 'Enable User Update', 'could be ''true'' or ''false''', 
    'true', 'true', 1001, 6, 1, 
    1, 'BOOLEAN');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3166, 31, 'enableViewInternalUser', 'Enable render Internal User List', 'show admin and anonymous user', 
    'true', 'true', 1001, 7, 1, 
    1, 'BOOLEAN');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (1003, 7, 'mailSenderAddress', 'Mail Sender Address', 'may be the administrator mail address', 
    'admin@enumlabs.net', 'admin@rte-france.com', 1000, 2, 1, 
    1, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (1004, 4, 'security', 'Enable Security', 'could be ''true'' or ''false''', 
    'true', 'true', 1001, 0, 1, 
    1, 'BOOLEAN');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (1005, 1, 'mailServer', 'Mail Server Host Name', 'The mail server host name', 
    'localhost', 'localhost', 1002, 0, 1, 
    1, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (2040, 18, 'siteName', 'Site Name', 'Name of the site to be displayed in Web browser title', 
    'Sight', 'Titre', 1000, 0, 1, 
    1, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3040, 11, 'siteSubName', 'Site Sub Name', 'Sub Name of the site to be displayed in Web browser title', 
    'Sous-titre', 'Sous-titre', 1000, 2, 1, 
    1, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3250, 1, 'synchronisationCron', 'Synchronization scheduler in cron format', 'using the CRON pattern', 
    '0 0 22 ? * MON-FRI', '0 0 22 ? * MON-FRI', 1000, 4, 1, 
    0, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3041, 3, 'siteVersion', 'Site Version', 'version of the site to be displayed in Web browser title', 
    '01.00.00', '01.00.00', 1000, 1, 1, 
    1, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
   (3200, 0, 'root', 'Root user''s login ', 'Default user when security is disable', 
    'root', 'root', 1001, 4, 0, 
    0, 'STRING');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT, 
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE, 
    PRM_MANDATORY, PRM_TYPE)
 VALUES
    (3240, 0, 'siteTimeout', 'Application timeout', 'Application connection timeout, in milliseconds',
    '1800000', '1800000', 1000, 3, 1, 0, 'INTEGER');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT,
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE,
    PRM_MANDATORY, PRM_TYPE)
 VALUES
    (3260, 0, 'mailServerSocketTimeout', 'SMTP socket timeout', 'SMTP socket timeout, in milliseconds',
    '60000', '60000', 1002, 1, 1, 1, 'INTEGER');
INSERT INTO FWK_PARAMETER
   (PRM_ID, PRM_VERSION, PRM_NAME, PRM_DISPLAY_NAME, PRM_COMMENT,
    PRM_INITIAL_VALUE, PRM_VALUE, DOM_ID, PRM_INDEX, PRM_MUTABLE,
    PRM_MANDATORY, PRM_TYPE)
 VALUES
    (3270, 0, 'mailServerConnectionTimeout', 'SMTP socket connection timeout', 'SMTP socket connection timeout, in milliseconds',
    '60000', '60000', 1002, 2, 1, 1, 'INTEGER');
INSERT INTO FWK_DOMAIN
   (DOM_ID, DOM_VERSION, DOM_NAME, DOM_DISPLAY_NAME, DOM_COMMENT, 
    DOM_INDEX)
 VALUES
   (1000, 2887, 'general', 'General', 'Web Application Parameters', 
    0);
INSERT INTO FWK_DOMAIN
   (DOM_ID, DOM_VERSION, DOM_NAME, DOM_DISPLAY_NAME, DOM_COMMENT, 
    DOM_INDEX)
 VALUES
   (1001, 3005, 'access', 'Access', 'Access Service Parameters', 
    1);
INSERT INTO FWK_DOMAIN
   (DOM_ID, DOM_VERSION, DOM_NAME, DOM_DISPLAY_NAME, DOM_COMMENT, 
    DOM_INDEX)
 VALUES
   (1002, 1399, 'net', 'Network', 'Network Parameters', 
    2);

Insert into FWK_USER
   (USR_ID, USR_VERSION, USR_LOGIN, USR_PASSWD, USR_FIRST_NAME, 
    USR_LAST_NAME, USR_LOCATION, USR_EMAIL, USR_ACTIVATED, USR_LOGICAL_ID, USER_DATE_CONNEXION)
 Values
   (1001, 3, 'anonymous', 'anonymous', 'anonymous', 
    'anonymous', 'anonymous', 'anonymous@localhost.fr', 1, 'anonymous', NULL);
Insert into FWK_USER
   (USR_ID, USR_VERSION, USR_LOGIN, USR_PASSWD, USR_FIRST_NAME, 
    USR_LAST_NAME, USR_LOCATION, USR_EMAIL, USR_ACTIVATED, USR_LOGICAL_ID, USER_DATE_CONNEXION)
 Values
   (0, 1025, 'ROOTROOT', '707e964ab383ea3e', 'root', 
    'root', 'info', 'root@root.fr', 1, 'root', NULL);

INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (1, 'Aristo', 'aristo');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (2, 'Black-Tie', 'black-tie');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (3, 'Blitzer', 'blitzer');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (4, 'Bluesky', 'bluesky');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (5, 'Casablanca', 'casablanca');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (6, 'Cupertino', 'cupertino');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (7, 'Dark-Hive', 'dark-hive');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (8, 'Dot-Luv', 'dot-luv');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (9, 'Eggplant', 'eggplant');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (10, 'Excite-Bike', 'excite-bike');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (11, 'Flick', 'flick');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (12, 'Glass-X', 'glass-x');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (13, 'Hot-Sneaks', 'hot-sneaks');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (14, 'Humanity', 'humanity');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (15, 'Le-Frog', 'le-frog');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (16, 'Midnight', 'midnight');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (17, 'Mint-Choc', 'mint-choc');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (18, 'Overcast', 'overcast');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (19, 'Pepper-Grinder', 'pepper-grinder');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (20, 'Redmond', 'redmond');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (21, 'Rocket', 'rocket');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (22, 'Sam', 'sam');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (23, 'Smoothness', 'smoothness');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (24, 'South-Street', 'south-street');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (25, 'Start', 'start');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (26, 'Sunny', 'sunny');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (27, 'Swanky-Purse', 'swanky-purse');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (28, 'Trontastic', 'trontastic');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (29, 'UI-Darkness', 'ui-darkness');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (30, 'UI-Lightness', 'ui-lightness');  
INSERT INTO FWK_THEME (THEME_ID, THEME_LABEL, THEME_NAME) VALUES (31, 'Vader', 'vader');

/* Ajout des clés primaires */
ALTER TABLE FWK_PARAMETER ADD (  CONSTRAINT PK_PARAMETER PRIMARY KEY (PRM_ID));
ALTER TABLE FWK_DOMAIN ADD (  CONSTRAINT PK_DOMAIN PRIMARY KEY (DOM_ID));
ALTER TABLE FWK_USER ADD (  CONSTRAINT PK_USER PRIMARY KEY (USR_ID));
ALTER TABLE FWK_THEME ADD (  CONSTRAINT PK_THEME PRIMARY KEY (THEME_ID));
ALTER TABLE FWK_BATCH_HISTO ADD (  CONSTRAINT PK_BATCH_HISTO PRIMARY KEY (BATCH_ID));
ALTER TABLE FWK_BATCH_MESSAGE ADD (  CONSTRAINT PK_BATCH_MESSAGE PRIMARY KEY (BATCH_MESSAGE_ID));
ALTER TABLE FWK_ACTION_LOG ADD CONSTRAINT PK_ACTION_LOG PRIMARY KEY (ACT_ID);
ALTER TABLE FWK_ACTION_LOG_MESSAGE ADD CONSTRAINT PK_ACTION_LOG_MESSAGE PRIMARY KEY (ALM_ID);
ALTER TABLE FWK_MAIL_REPORTING ADD CONSTRAINT PK_MAIL_REPORTING PRIMARY KEY (MAIL_ID);

/* Contraintes de clés étrangères */
ALTER TABLE FWK_PARAMETER ADD (CONSTRAINT FK_PARAMETER_2_DOMAIN FOREIGN KEY (DOM_ID) REFERENCES FWK_DOMAIN (DOM_ID));
ALTER TABLE FWK_USER ADD (  CONSTRAINT FK_USER_2_THEME FOREIGN KEY (THEME_ID) REFERENCES FWK_THEME (THEME_ID));
ALTER TABLE FWK_BATCH_MESSAGE ADD (  CONSTRAINT FK_BATCH_MESSAGE_2_BATCH_HISTO FOREIGN KEY (BATCH_ID) REFERENCES FWK_BATCH_HISTO (BATCH_ID));
/* Nom de la clé étrangère tronqué car 30 caractères maximum */
ALTER TABLE FWK_ACTION_LOG_MESSAGE ADD CONSTRAINT FK_ACTION_LOG_MES_2_ACTION_LOG FOREIGN KEY (ACT_ID) REFERENCES FWK_ACTION_LOG (ACT_ID);
ALTER TABLE FWK_ACTION_LOG ADD CONSTRAINT FK_ACTION_LOG_2_USER FOREIGN KEY (USR_ID) REFERENCES FWK_USER (USR_ID);

/* Autre contraintes */
ALTER TABLE FWK_BATCH_HISTO ADD (  CONSTRAINT CK_BATCH_HISTO CHECK (BATCH_STATUT IN ('OK','KO','IN_PROGRESS')));

/* Index */
CREATE INDEX IDX_BTCH_NAM_ON_FWK_BTCH_HIST ON FWK_BATCH_HISTO (BATCH_NAME);

/* Création des séquences */
CREATE SEQUENCE SEQ_GRP_ID START WITH 3320 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_USR_ID START WITH 13280 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_DOM_ID START WITH 3060 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_PRM_ID START WITH 3240 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_THEME_ID START WITH 100 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_BATCH_ID START WITH 100 MAXVALUE 999999999999999999999999999 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_MAIL_ID START WITH 0 NOMAXVALUE MINVALUE 0 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_BATCH_MESSAGE_ID START WITH 0 NOMAXVALUE MINVALUE 0 NOCYCLE CACHE 20 NOORDER;
CREATE SEQUENCE SEQ_ACT_ID INCREMENT BY 1 START WITH 0 NOMAXVALUE MINVALUE 0 NOCYCLE NOCACHE NOORDER;
CREATE SEQUENCE SEQ_ALM_ID INCREMENT BY 1 START WITH 0 NOMAXVALUE MINVALUE 0 NOCYCLE NOCACHE NOORDER;
