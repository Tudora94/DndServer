USE DndDb
BEGIN TRANSACTION;
CREATE TABLE AbilityScores (
ID int NOT NULL default 0,
CharacterID int NOT NULL default 0,
Strength int NOT NULL default 0,
Dexterity int NOT NULL default 0,
Constitution int NOT NULL default 0, 
Intelligence int NOT NULL default 0,
Wisdom int NOT NULL default 0,
Charisma int NOT NULL default 0,
IS_VarientRace CHAR NOT NULL, 
)

INSERT INTO AbilityScores (ID, CharacterID, Strength, Dexterity, Constitution,Intelligence, Wisdom, Charisma, IS_VarientRace)
VALUES
(1,1,0,0,2,0,0,0,'N'),
(2,2,0,0,0,0,1,0,'Y'),
(3,3,2,0,0,0,0,0,'Y'),
(4,4,0,2,0,0,0,0,'N'),
(5,5,0,0,0,1,0,0,'Y'),
(6,6,0,0,0,0,1,0,'Y'),
(7,7,0,0,0,0,0,1,'Y'),
(8,8,0,2,0,0,0,0,'N'),
(9,9,0,0,0,0,0,1,'Y'),
(10,10,0,0,1,0,0,0,'Y'),
(11,11,1,1,1,1,1,1,'N'),
/*FOR VARIENT HUMAN BASE STATS ARE PICKED OUT OF 2 OF THESE OPTIONS (THIS WILL NEED TO BE DONE AS A POST REQUEST IN THE CODE)*/
(12,12,0,0,0,0,0,0,'Y'),
(13,13,2,0,0,0,0,2,'N'),
(14,14,0,0,0,2,0,0,'N'),
(15,15,0,1,0,0,0,0,'Y'),
(16,16,0,0,1,0,0,0,'Y'),
(17,17,0,0,0,0,0,2,'N'),
(18,18,2,0,1,0,0,0,'N'),
(19,19,0,0,0,1,0,2,'N')

SELECT * FROM AbilityScores

--COMMIT
--ROLLBACK