CREATE TABLE SEQUENCE_ID_NEXT_VALUE
(
    SEQUENCE_ID_GROUP SMALLINT NOT NULL,
    LOGICAL_SHARD_ID INT NOT NULL,
    NEXT_VALUE BIGINT NULL,
    SERVER_ID_FLAG TINYINT NOT NULL,
    PRIMARY KEY (SEQUENCE_ID_GROUP, LOGICAL_SHARD_ID, SERVER_ID_FLAG)
) ENGINE=INNODB;

DELIMITER //
CREATE PROCEDURE GET_SEQUENCE_ID_NEXT_VALUE(
  IN VAR_LOGICAL_SHARD_ID INT,
  IN VAR_SEQUENCE_ID_GROUP SMALLINT,
  IN VAR_BATCH_SIZE INT,
  OUT VAR_NEXT_VALUE BIGINT)
BEGIN
  DECLARE VAR_SERVER_ID_FLAG TINYINT DEFAULT @@global.server_id % 2;
  -- Shard-aware ID structure in decimal format:
  -- |<--generated id(>10000000)-->|<--logical shard id(00000-99999)-->|
  --       100000014                        12345
  -- NOTICE: For easier calculation, id return here doesn't include the shard id
  -- Shard id need to be inserted into the return value to get the actual generated id

  SET VAR_NEXT_VALUE = NULL;
  SELECT MAX(NEXT_VALUE) INTO VAR_NEXT_VALUE
  FROM SEQUENCE_ID_NEXT_VALUE
  WHERE SEQUENCE_ID_GROUP = VAR_SEQUENCE_ID_GROUP AND LOGICAL_SHARD_ID = VAR_LOGICAL_SHARD_ID
  FOR UPDATE;

  IF VAR_NEXT_VALUE IS NULL THEN
    BEGIN
      -- Set initial value to 10^7, which means 10^12 as actual id
      -- This value is bigger than any existing id values there won't be any duplicate with ids generated in old way
      SET VAR_NEXT_VALUE = 10000000/*10^7*/;
    END;
  END IF;

  INSERT INTO SEQUENCE_ID_NEXT_VALUE (SEQUENCE_ID_GROUP, LOGICAL_SHARD_ID, NEXT_VALUE, SERVER_ID_FLAG)
  VALUES (VAR_SEQUENCE_ID_GROUP, VAR_LOGICAL_SHARD_ID, VAR_NEXT_VALUE + VAR_BATCH_SIZE * 2, VAR_SERVER_ID_FLAG)
  ON DUPLICATE KEY UPDATE NEXT_VALUE = VAR_NEXT_VALUE + VAR_BATCH_SIZE * 2;

  -- return different values depending on server id
  -- this is used for ensure primary and secondary server will not generate duplicate ids
  -- client should be aware of this and get batch values by add 2 every time
  SET VAR_NEXT_VALUE = VAR_NEXT_VALUE + VAR_SERVER_ID_FLAG;
END//
DELIMITER ;