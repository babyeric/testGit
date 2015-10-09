CREATE TABLE user (
  LOGICAL_SHARD_ID int(11) NOT NULL,
  USER_ID bigint(20) NOT NULL,
  EMAIL varchar(100) COLLATE utf8_general_ci NOT NULL,
  FIRST_NAME varchar(50) NOT NULL,
  LAST_NAME varchar(50) NOT NULL,
  MOBILE varchar(20) DEFAULT NULL,
  COUNTRY varchar(2) DEFAULT NULL,
  BIRTHDAY date DEFAULT NULL,
  CREATE_DATE date DEFAULT NULL,
  MODIFIED_DATE date DEFAULT NULL,
  MODIFIED_BY varchar(20) DEFAULT NULL,
  PRIMARY KEY (USER_ID),
  INDEX EMAIL (EMAIL)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE user_password (
  LOGICAL_SHARD_ID int(11) NOT NULL,
  USER_ID bigint(20) NOT NULL,
  PASSWORD varchar(256) NOT NULL,
  SALT varchar(50) NOT NULL,
  VERSION tinyint(3) NOT NULL,
  CREATE_DATE date DEFAULT NULL,
  MODIFIED_DATE date DEFAULT NULL,
  MODIFIED_BY varchar(20) DEFAULT NULL,
  PRIMARY KEY (USER_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;