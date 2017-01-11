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
	id_gagnant       INTEGER 		NOT NULL ,
	id_perdant		 INTEGER 		NOT NULL,
	CONSTRAINT pk_historique 	PRIMARY KEY(id),
	CONSTRAINT fk_histo_gagnant FOREIGN KEY(id_gagnant) REFERENCES joueur(id) ON DELETE CASCADE,
	CONSTRAINT fk_histo_perdant FOREIGN KEY(id_perdant) REFERENCES joueur(id) ON DELETE CASCADE
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
	CONSTRAINT fk_fait_joueur 		FOREIGN KEY(id_joueur) 	 REFERENCES joueur(id) ON DELETE CASCADE,
	CONSTRAINT fk_fait_achievement  FOREIGN KEY(id_achievement) REFERENCES achievement(id) ON DELETE CASCADE
);


--
-- Package utilisé pour l'aspectpersistance, stocke le dernier id créé
--

--Achivement

create or replace package pkg_achievement is
    lastId integer;
    procedure createAchievJoueur (id_joueur in number, id_achiev in number);
end pkg_achievement;
/
create or replace package body pkg_achievement is
     procedure createAchievJoueur (id_joueur in number, id_achiev in number) is
        begin
            INSERT INTO fait (id_joueur,id_achievement) VALUES (id_joueur,id_achiev);
        exception
            when DUP_VAL_ON_INDEX then
                DBMS_OUTPUT.PUT_LINE('Duplication id invalide');
    end createAchievJoueur;
end pkg_achievement;


--Historique

create or replace package pkg_historique is
    lastId integer;
    function last_id_historique return integer;
end pkg_historique;
/
create or replace package body pkg_historique is
    function last_id_historique return integer 
        is last_id integer;
        begin 
            last_id := lastId;
            return last_id;
    end last_id_historique;
end pkg_historique;
/

--Joueur

create or replace package pkg_joueur is
    lastId integer;
    function createJoueur (pseudo in varchar2, mdp in varchar2, email in varchar2) return integer;
    procedure updateJoueur (v_pseudo in varchar2, v_mdp in varchar2, v_email in varchar2, v_id in number);
end pkg_joueur;
/
create or replace package body pkg_joueur is 
    function createJoueur (pseudo in varchar2, mdp in varchar2, email in varchar2) return integer is
        begin
            INSERT INTO joueur (id,pseudo,mdp,email) VALUES ('', pseudo, mdp, email);
            return lastId;
        exception
            when DUP_VAL_ON_INDEX then
                DBMS_OUTPUT.PUT_LINE('Duplication id invalide');
    end createJoueur;

    procedure updateJoueur (v_pseudo in varchar2, v_mdp in varchar2, v_email in varchar2, v_id in number) is
        begin
            update joueur set pseudo = v_pseudo, mdp= v_mdp, email= v_email WHERE id= v_id;
    end updateJoueur;
end pkg_joueur;
/


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
  PKG_JOUEUR.lastId := :new.id; --On stocke l'id créé dans le package
END;
/


CREATE OR REPLACE TRIGGER historique_bir 
BEFORE INSERT ON historique 
FOR EACH ROW
BEGIN
  SELECT histo_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
  PKG_HISTORIQUE.lastId := :new.id; --On stocke l'id créé dans le package
END;
/


CREATE OR REPLACE TRIGGER achiev_bir 
BEFORE INSERT ON achievement
FOR EACH ROW
BEGIN
  SELECT achiev_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
  PKG_ACHIEVEMENT.lastId := :new.id; --On stocke l'id créé dans le package
END;
/


--
-- Insertions
--


INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_FIRST_WIN','Il faut une première fois à tout','Gagner votre première partie contre un adversaire humain.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_PERFECT','Le fruit de la compétence','Gagner une partie en n''ayant perdu aucune bille.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_SIX_FIVE','In Extremis','Gagner une partie avec un score de 6 - 5.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_TEN_WIN','La chance du débutant ?','Gagner 10 parties contre un adversaire humain.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_HUNDRER_WIN','Excusez du peu...','Gagner 100 parties contre un adversaire humain.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_SURRENDER','Trouillard !','Gagner une partie par abandon.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_COMBO_2','Combo !','Prendre deux billes à la suite.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_COMBO_3','Combo x3 !','Prendre trois billes à la suite.');
INSERT INTO ACHIEVEMENT (ID,TITRE,NOM,DESCRIPTION) VALUES('','ACV_COMBO_4','Combo x4 !','Prendre quatre billes à la suite.');
