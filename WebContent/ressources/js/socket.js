var joueurSocket;

var making;
var player_current;
var player_invitation;

function initMatchMaking(id, pseudo,email){
    if(validatePageWithExtension("matchmaking")){
    	alert("ok");
        joueurSocket = new WebSocket("ws://localhost:9090/Abalone/joueurSocket");
        joueurSocket.onmessage = onMessage;
        
        making = new MatchMaking();
        player_current = new Joueur(id, pseudo, email)
        making.addJoueur(player_current);

        $('#invitation').on('hide.bs.modal', function (e) {
            if(player_invitation != null){
                sendResponse(player_invitation, false);
            }
        });
    } else{
    	alert("pas ok");
    }
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
           tdPseudo.innerHTML= item.pseudo;
           tdPseudo.className="grey td-listPlayer";

           tdButton.className="wth-100 td-listPlayer";
           //Propriété du bouton
            button.className ="btn btn-lg btn-success mrg-right-10";
            button.type="button";
            button.title="Invitation";
            button.innerHTML="Envoyer une invitation";
           
            button.onclick = function(){
                setNotification(button);
                sendInvitation(item);
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

function setNotification(button){
     $(button).popover(
        {
            content: "Invitation envoyé !",
            trigger:"focus",
            placement:"top"
        }
     );
}

function testtoto(){
      making.addJoueur(new Joueur(1, 'toto', 'joueur_email'));
}

function respondInvitation(respond){
   if(player_invitation != null){
        sendResponse(player_invitation, respond);
        player_invitation = null;
        $('#invitation').modal('hide');
   }
}

function getInvitation(json){
    player_invitation = json;
    $id('messageInvitation').innerHTML= json.pseudo_source + " veut jouer avec vous";
    $('#invitation').modal('show');
}

function getRespond(json){
    if(json.confirm){
        $id('respondInvitation').innerHTML=json.pseudo_source = " a accepté votre invitation, lancement de la partie...";
        
        //request post à faire
        document.location.href="partie.html";
    } else{
        $id('respondInvitation').innerHTML=json.pseudo_source + " a refusé votre invitation";
    }
}

/*
    Réponse joueur
*/
function onMessage(event) { //On reçoit un message
    var json = JSON.parse(event.data);
    
    switch(json.action){
        case "add":{
            making.addJoueur(new Joueur(json.id, json.pseudo, json.email));
            break;
        }
        case "remove":{
            making.removeJoueur(new Joueur(json.id, json.pseudo, json.email));
            break;
        }
        case "demande":{
            getinvitation(json);
            break;
        }
        case "reponse":{
            getRespond(json);
            break;
        }
    }
}

function addJoueur(name, type, description) {
    var json = {
        action: "add",
        pseudo: joueur.pseudo,
        email : joueur.email
    };
    joueurSocket.send(JSON.stringify(json));
}

function sendInvitation (joueur){
    var json = {
        action: "demande",
        destinataire: joueur.id
    };
    joueurSocket.send(JSON.stringify(json));
}

function sendResponse (joueur, response){
    var json = {
        action: "reponse",
        destinataire : joueur.id,
        confirm :response
    };
    joueurSocket.send(JSON.stringify(json));
}