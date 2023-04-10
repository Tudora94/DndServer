CREATE TABLE PlayerCharacterName
(
Id int Identity(1,1) primary key,
UserId int foreign key references Users(Id),
CampaignId int foreign key references CampaignName(Id),
CharacterName VARCHAR(255)
)