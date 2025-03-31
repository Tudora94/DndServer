USE DndDb
GO
CREATE PROCEDURE AddNewPlayer
@name VARCHAR(255),
@userId INT,
@updateTime BIGINT,
@playerId INT OUTPUT
AS
BEGIN

INSERT INTO PlayerCharacterName
VALUES
(
@userId,
NULL,
@name,
@updateTime
)
SET @playerId = (SELECT Id FROM PlayerCharacterName WHERE UserId = @userId AND updateTime = @updateTime)
END
