USE DndDb
IF OBJECT_ID('DndDb.dbo.Users')
	IS NULL
	BEGIN
CREATE TABLE Users
(
Id int Identity(1,1) primary key,
username varchar(255),
Hash varbinary(max),
Salt varbinary(max)
)
END;
IF OBJECT_ID('DndDb.dbo.UserEmail')
	IS NULL
	BEGIN
CREATE TABLE UserEmail(
id int Identity(1,1) primary key,
userId int foreign key references Users(Id),
Email Varchar(255)
)
END;
IF OBJECT_ID('DndDb.dbo.UserDetails')
	IS NULL
	BEGIN
CREATE TABLE UserDetails(
userId int foreign key references Users(Id),
firstName varchar(255) NOT NULL
)
END