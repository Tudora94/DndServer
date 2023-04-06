USE DndDb

CREATE TABLE Users
(
Id int Identity(1,1) primary key,
username varchar(255),
Hash varbinary(max),
Salt varbinary(max)
);
CREATE TABLE UserEmail(
id int Identity(1,1) primary key,
userId int foreign key references Users(Id),
Email Varchar(255)
);
