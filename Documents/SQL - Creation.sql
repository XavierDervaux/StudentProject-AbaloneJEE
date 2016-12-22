CREATE TABLE joueur(
	id 		  INTEGER 		NOT NULL,
	pseudo    VARCHAR2(64) 	NOT NULL,
	mdp       VARCHAR2(256) NOT NULL,
	email     VARCHAR2(64) 	NOT NULL,
	CONSTRAINT pk_joueur PRIMARY KEY(id)
);

CREATE TABLE historique(
	id    			 INTEGER 		NOT NULL,
	date_partie      DATE 			NOT NULL,
	score_gagnant    INTEGER 		NOT NULL,
	score_perdant    INTEGER 		NOT NULL,
	est_forfait      NUMBER(1,0) 	NOT NULL,
	id_gagnant       INTEGER 		NOT NULL,
	id_perdant		 INTEGER 		NOT NULL,
	CONSTRAINT pk_historique 	PRIMARY KEY(id),
	CONSTRAINT fk_histo_gagnant FOREIGN KEY(id_gagnant) REFERENCES joueur(id),
	CONSTRAINT fk_histo_perdant FOREIGN KEY(id_perdant) REFERENCES joueur(id)
);

CREATE TABLE achievement(
	id 			   INTEGER 		 NOT NULL,
	titre          VARCHAR2(256)  NOT NULL,
	nom            VARCHAR2(256)  NOT NULL,
	description    VARCHAR2(512) NOT NULL,
	CONSTRAINT pk_achievement PRIMARY KEY(id)
);

CREATE TABLE fait(
	id_joueur      INTEGER NOT NULL,
	id_achievement INTEGER NOT NULL,
	CONSTRAINT pk_fait 				PRIMARY KEY(id_joueur ,id_achievement),
	CONSTRAINT fk_fait_joueur 		FOREIGN KEY(id_joueur) 	 REFERENCES joueur(id),
	CONSTRAINT fk_fait_achievement  FOREIGN KEY(id_achievement) REFERENCES achievement(id)
);

--
-- Triggers pour Auto Increment
--


CREATE SEQUENCE joueur_seq START WITH 1;
CREATE SEQUENCE histo_seq START WITH 1;
CREATE SEQUENCE achiev_seq START WITH 1;


CREATE OR REPLACE TRIGGER joueur_bir 
BEFORE INSERT ON joueur 
FOR EACH ROW
BEGIN
  SELECT joueur_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/


CREATE OR REPLACE TRIGGER historique_bir 
BEFORE INSERT ON historique 
FOR EACH ROW
BEGIN
  SELECT histo_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/


CREATE OR REPLACE TRIGGER achiev_bir 
BEFORE INSERT ON achievement
FOR EACH ROW
BEGIN
  SELECT achiev_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/