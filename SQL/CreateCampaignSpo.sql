USE DndDb
GO
CREATE PROCEDURE CreateCampaign
	@userName nvarchar(255),
	@campaignName nvarchar(255),
	@CampaignId INT OUTPUT

AS
BEGIN
	DECLARE @userId INT = (SELECT Id FROM DndDb.dbo.Users WHERE username = @username)
	DECLARE @id INT

	INSERT INTO DndDb.dbo.CampaignName VALUES (@userId, @campaignName)

	SET @id = (SELECT Id FROM DndDb.dbo.CampaignName WHERE UserId =@userId AND CampaignName = @campaignName)

	INSERT INTO DndDb.dbo.CampaignData (CampaignId) VALUES (@id)
	INSERT INTO DndDb.dbo.CampaignSourceData (CampaignId) VALUES (@id)

	SET @campaignId = @id
END