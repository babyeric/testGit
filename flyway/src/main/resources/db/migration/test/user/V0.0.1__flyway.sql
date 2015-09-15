CREATE TABLE IF NOT EXISTS `user` (
  `USER_ID` bigint(20) NOT NULL,
  `NAME` varchar(50) COLLATE utf8_bin NOT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `LOGICAL_SHARD_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;