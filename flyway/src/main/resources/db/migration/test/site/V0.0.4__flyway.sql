CREATE TABLE site (
  LOGICAL_SHARD_ID int(11) NOT NULL,
	SITE_ID BIGINT NOT NULL,
	USER_ID BIGINT NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	DESCRIPTION VARCHAR(4000) NOT NULL,
	SITE_TAG VARCHAR(50) NOT NULL,
	MODIFIED_BY VARCHAR(50) NOT NULL,
	MODIFIED_DATE DATETIME NOT NULL,
	PRIMARY KEY (SITE_ID),
	INDEX USER_ID (USER_ID)
) ENGINE=InnoDB CHARSET=utf8 COLLATE=utf8_bin;

