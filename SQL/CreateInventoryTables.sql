USE DndDb
IF OBJECT_ID('DndDb.dbo.Inventory')
	IS NULL
	BEGIN
CREATE TABLE Inventory
(
Id int Identity(1,1) primary key,
UserId int foreign key references Users(Id),
CampaignId int null foreign key references CampaignName(id),
PlayerId int null foreign key references PlayerCharacterName(id),
ItemName varchar(255),
Description varchar(255),
Detail varchar(max),
UpdateTime BIGINT DEFAULT 0
)
END;