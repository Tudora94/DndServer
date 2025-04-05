USE DndDb
GO
CREATE PROCEDURE AddPlayerToCampaign
@userId INT,
@updateTime BIGINT,
@characterId INT,
@roomCode VARCHAR(6),
@campaignId INT OUTPUT,
@campaignName VARCHAR(255) OUTPUT
AS
BEGIN

DECLARE @tempCampaignId INT
SET @tempCampaignId = (SELECT CampaignId
FROM CampaignRoomCode
WHERE CampaignCode = @roomCode)

UPDATE PlayerCharacterName
SET CampaignId = @tempCampaignId, UpdateTime = @updateTime
WHERE Id = @characterId
AND UserId = @userId

SET @campaignId = @tempCampaignId
SET @campaignName = (SELECT
CampaignName
FROM CampaignName
WHERE Id = @campaignId)
END;