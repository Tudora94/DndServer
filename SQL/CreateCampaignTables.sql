USE DndDb
IF OBJECT_ID('DndDb.dbo.CampaignName')
	IS NULL
	BEGIN
CREATE TABLE CampaignName
(
Id int Identity(1,1) primary key,
UserId int foreign key references Users(Id),
CampaignName varchar(255),
)
END;
IF OBJECT_ID('DndDb.dbo.CampaignData')
	IS NULL
	BEGIN
CREATE TABLE CampaignData
(
Id int Identity(1,1) primary key,
CampaignId int foreign key references CampaignName(Id),
advancementType bit NOT NULL DEFAULT(0),
hpType bit NOT NULL DEFAULT(0),
weightType bit NOT NULL DEFAULT(0),
goldWeight bit NOT NULL DEFAULT(0)
)
END;
IF OBJECT_ID('DndDb.dbo.CampaignSourceData')
	IS NULL
	BEGIN
CREATE TABLE CampaignSourceData
(
Id int Identity(1,1) primary key,
CampaignId int foreign key references CampaignName(Id),
PHB_5TH_EDITION_CONTENT bit NOT NULL DEFAULT(0),
HOMEBREW_CONTENT bit NOT NULL DEFAULT(0),
ONLINE_CONTENT bit NOT NULL DEFAULT(0),
OTHER_SOURCE_CONTENT bit NOT NULL DEFAULT(0)
)
END;
IF OBJECT_ID('DndDb.dbo.CampaignRoomCode')
	IS NULL
	BEGIN
CREATE TABLE CampaignRoomCode
(
CampaignId INT foreign key references CampaignName(Id),
CampaignCode VARCHAR(6),
ExpiryDateTime DATETIME
)
END;