var joueurSocket = new WebSocket("ws://localhost:10080/Abalone/joueurSocket");
joueurSocket.onmessage = onMessage;

var making;
var player_current;

function initMatchMaking(id, pseudo,email){
    making = new MatchMaking();
    player_current = new Joueur(id, pseudo, email)
    making.addJoueur(player_current);
}

function MatchMaking(){
    this.joueurs= [];

    this.addJoueur = function(joueur){
        this.joueurs.push(joueur);
        this.refresh();
    }

    this.removeJoueur = function(joueur){
        var i = 0, trouver = false;
        
        while(i < this.joueurs.length && !trouver){
            if(joueur.id == this.joueurs[i].id){
                trouver = true;
            }
            i++;
        }
        
        if(trouver){
            this.joueurs[i-1] = null;
            this.joueurs.sort();
            this.refresh(); 
        }
    }
 
    this.refresh = function(){
        genereTableJoueur(this.joueurs);
    }
}

function Joueur(id, pseudo, email){
    this.id = id;
    this.pseudo = pseudo;
    this.email = email;
}

function genereTableJoueur(joueurs){
    var table = $id('listJoueur');
    var title = table.firstChild;
    
    //Je vide le tableau
    table.innerHTML = "";
    
    var trHEader = document.createElement("tr");
    var th = document.createElement("th");
    th.innerHTML = "Joueurs connectés";
    th.colSpan="2" ;
    th.className="th-title define-th";
   
    trHEader.appendChild(th);
    table.appendChild(trHEader);
    
    for(var i = 0; i < joueurs.length; i++){
       item = joueurs[i];
       
       if(item != player_current){
           //Genetation du tableau
            var tr = document.createElement("tr");
            var tdPseudo = document.createElement("td");
            var tdButton = document.createElement("td");
            var button = document.createElement("button");

           //Propriété de la td pseudo
           tdPseudo.innerHTML= item.joueur_pseudo;
           tdPseudo.className="grey td-";

           //Propriété du bouton
            button.className ="btn btn-success";
            button.type="button";
            button.innerHTML="Envoyer une invitation";
            button.onclick = function(){
                sendMessage(item);
            };

           //Assemblage
           tdButton.appendChild(button);
           tr.appendChild(tdPseudo);
           tr.appendChild(tdButton);

           //table.appendChild(title);
           table.appendChild(tr);
       }
   }
}

function testtoto(){
      making.addJoueur(new Joueur(1, 'joueur_pseudo', 'joueur_email'));
}

function addJoueur(name, type, description) {
    var json = {
        action: "add",
        pseudo: joueur.pseudo,
        email : joueur.email
    };
    joueurSocket.send(JSON.stringify(json));
}

this.sendInvitation = function(joueur){
    var json = {
        action: "demande",
        destinataire: joueur.id
    };
    joueurSocket.send(JSON.stringify(json));
}

this.sendResponse = function(joueur, response){
    var json = {
        action: "reponse",
        destinataire : joueur.id,
        confirm :response
    };
    joueurSocket.send(JSON.stringify(json));
}


function onMessage(event) { //On reçoit un message
    var json = JSON.parse(event.data);
    
    switch(json.action){
        case "add":{
            break;
        }
        case "remove":{
            break;
        }
        case "demande":{
            break;
        }
        case "reponse":{
            break;
        }
    }
    alert(truc);
}