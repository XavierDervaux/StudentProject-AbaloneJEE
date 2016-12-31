var partieSocket;
var partie;

function initPartie(){
    if(validatePageWithExtension("partie")){
        alert("PARTIE.JS ACTIVER !");
        //On veut communiquer avec le socket
        partieSocket = new WebSocket("ws://localhost:9090/Abalone/partieSocket");
        partieSocket.onmessage = onMessagePartie;
         
        //Pour une utilisation correcte, on vire le responsive.
        $id('corps').style.width="1000px";
    
        //On inialise l'interface
        initInterface();
    
    }
}


function initInterface(){
    hideAll();
    show("syncPartie");
    
    //TEST
   // setDebutPartie("json");
   // partie = new Partie("toto", "tutu");
  //  partie.yourTurn();
   // setFinPartie(true, false);
}

function Partie(JoueurMe, joueurTwo){
    this.JoueurMe = JoueurMe;
    this.joueurTwo = joueurTwo;
    this.scoreMe = 0;
    this.scoreAdversaire = 0;
    this.isTurn = true;
    
    this.plateau = new Plateau();
    this.init = function(){
        this.plateau.initTerrain();
        this.plateau.initBillePlayer();
    }

    this.yourTurn = function(){ //Détermine le message pour savoir qui joue.
        if(this.isTurn){
            $id('whoPlayed').children[0].innerHTML = "A vous de jouer !";
            $id('whoPlayed').style.color = "#21a637";
        } else{
            $id('whoPlayed').children[0].innerHTML = "Votre adversaire joue !";
            $id('whoPlayed').style.color = "#D9534F";
        }
    }
}

function Plateau(){
    this.terrain = [17];
    this.billeSelected = [3];
    
    this.init = function(){
        this.initTerrain(9);
    }
    
    this.initTerrain = function(size ,val = 0){
        for(i = 0; i < this.terrain.length; i++){
            this.terrain[i] = [size];
            for(j = 0; j <  this.terrain[i].length; j++){
                this.terrain[i][j] = val;
            }
        }
    }
    
    this.initBillePlayer = function(){
         for(i = 0; i < 3; i++){
             for(j = 0; j <  this.terrain[i].length; j = j+2){
                   this.terrain[i][j] = -1;
             }
         }
         for(i = this.terrain.length-4; i < this.terrain.length; i++){
             for(j = 0; j <  this.terrain[i].length; j = j+2){
                   this.terrain[i][j] = 1;
             }
         }
    }
    
    this.emptyBilleSelected = function(){
        this.billeSelected = [3];
    }
}

function redirectionMenu(){
    document.location.href="menu.html";
}

/*
    Fonction qui gère les différents messages de fin de partie.
*/
function setMessageFinPartie(isGagner, isAbandon){
    if(isGagner){ //Vous avez gagnez
        $id('finPartieTitre').innerHTML="Victoire !";
        if(isAbandon){  //Vous avez gagnez par abandon
             $id('finPartieMessage').innerHTML="Votre adversaire a abandonné !";
        } else{ //Vous avez gagnez normalement
             $id('finPartieMessage').innerHTML="Vous avez gagné "+partie.scoreMe + " - " + partie.scoreAdversaire;
        }
    } else{
         $id('finPartieTitre').innerHTML="Defaite !";
        if(isAbandon){  //Vous avez gagnez par abandon
             $id('finPartieMessage').innerHTML="Votre avez abandonné !";
        } else{ //Vous avez gagnez normalement
             $id('finPartieMessage').innerHTML="Vous avez perdu "+partie.scoreMe + " - " + partie.scoreAdversaire;
        }
    }
}

function onMessagePartie(event) {
    var json = JSON.parse(event.data);
    
    switch(json.action){
        case "pret":{
            setDebutPartie();
            break;
        }
        case "Timeout":{
            
            break;
        }    
    }
}

/*
    Est appeler quand les joueurs sont synchronisés
*/
function setDebutPartie(json){
    //On affiche le plateau
    hideAll();
    show('whoPlayed');
    show("hubScore");
    show("plateauPartie");
    show('buttonAbandonner');
    
    //On initialise l'objet partie
    partie = new Partie(json.me, json.adversaire);  //VARIABLE A MODIFIER !
    partie.plateau.init();
}


/*
    Intervient quand la partie se termine (victoire, defaite ou abandon).
*/
function setFinPartie(isGagner, isAbandon){
    hideAll();
    show("finPartie");
    setMessageFinPartie(isGagner, isAbandon);
    span = document.createElement('span');
    if(isGagner){
        $id('finPartie').style.color = "#21a637";
        span.className="glyphicon glyphicon-thumbs-up";
        
    } else{
         $id('finPartie').style.color = "#C9302C";
         span.className="glyphicon glyphicon-thumbs-down";
    }
    $id('finPartieGlyph').appendChild(span);
}

function hideAll(){
    hide('syncPartie');
    hide('whoPlayed');
    hide('hubScore');
    hide('plateauPartie');
    hide('finPartie');
    hide('buttonAbandonner');
}