USE DndDb
GO
CREATE PROCEDURE AddNewPlayer
@name VARCHAR(255),
@userId INT,
@updateTime BIGINT
AS
BEGIN

INSERT INTO PlayerCharacterName
VALUES
(
@userId,
0,
@name,
@updateTime
)
END
