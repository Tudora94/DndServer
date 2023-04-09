USE DndDb

CREATE TABLE CampaignName
(
Id int Identity(1,1) primary key,
UserId int foreign key references Users(Id),
CampaignName varchar(255),
)
CREATE TABLE CampaignData
(
Id int Identity(1,1) primary key,
CampaignId int foreign key references CampaignName(Id),
SourcesId int,
advancementType bit,
hpType bit,
weightType bit,
goldWeight bit,
allowedRacesId int
)