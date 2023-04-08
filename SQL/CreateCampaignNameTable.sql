USE DndDb

CREATE TABLE CampaignName
(
Id int Identity(1,1) primary key,
UserId int foreign key references Users(Id),
CampaignName varchar(255),
)