USE DndDb
GO
CREATE PROCEDURE AddNewPlayer
@roomCode VARCHAR(255),
@name VARCHAR(255),
@userName VARCHAR(255)
AS
BEGIN

DECLARE @userId INT
SET @userId = (SELECT TOP(1) Id FROM Users WHERE username = @userName)

DECLARE @CampaignId INT
SET @CampaignId = (SELECT TOP(1) CampaignId FROM CampaignRoomCode WHERE CampaignCode = @roomCode)

INSERT INTO PlayerCharacterName
VALUES
(
@userId,
@CampaignId,
@name
)
END
