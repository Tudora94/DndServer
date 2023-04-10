USE DndDb
GO
CREATE PROCEDURE SetCampaignAttributes

@campaignId int,
@campaign nvarchar(255),
@phb bit,
@home bit,
@online bit,
@other bit,
@advancement bit,
@hpType bit,
@weightType bit,
@goldWeight bit

AS
BEGIN 

UPDATE CampaignName
SET CampaignName = @campaign
WHERE Id = @campaignId

UPDATE CampaignData
SET advancementType = @advancement,
hpType = @hpType,
weightType = @weightType,
goldWeight = @goldWeight
WHERE CampaignId = @campaignId

UPDATE CampaignSourceData
SET PHB_5TH_EDITION_CONTENT = @phb,
HOMEBREW_CONTENT = @home,
ONLINE_CONTENT = @online,
OTHER_SOURCE_CONTENT = @other
WHERE CampaignId = @campaignId

END