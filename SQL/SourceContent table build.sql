USE DndDb

CREATE TABLE CampaignSourceData(
Id int Identity(1,1) primary key,
CampaignId int foreign key references CampaignName(Id),
PHB_5TH_EDITION_CONTENT bit,
HOMEBREW_CONTENT bit,
ONLINE_CONTENT bit,
OTHER_SOURCE_CONTENT bit)
