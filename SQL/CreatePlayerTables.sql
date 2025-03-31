IF OBJECT_ID('DndDb.dbo.CampaignName')
	IS NULL
	BEGIN
CREATE TABLE PlayerCharacterName
(
Id int Identity(1,1) primary key,
UserId int foreign key references Users(Id),
CampaignId int NULL foreign key references CampaignName(Id),
CharacterName VARCHAR(255),
UpdateTime BIGINT DEFAULT 0
)
END;