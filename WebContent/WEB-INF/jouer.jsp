<%@ include file="header.jsp" %>
        <div id="corps">
        	        <div id="whoPlayed">
            <h1></h1>
        </div>
        <div id="hubScore">
            <div id="hubScorePos">
                <div class="hubscore" id="scoreP1"><h2>0</h2></div>
                <div class="hubPlayer"><h1><span id="nameP1">Joueur1</span><span class="glyphicon glyphicon-user pos-right"></span></h1></div>
                <div class="hubPlayer"><h1><span class="glyphicon glyphicon-user pos-left"></span><span id="nameP2">Joueur 2</span></h1></div>
                <div class="hubscore" id="scoreP2"><h2>0</h2></div>
            </div>
        </div>
          <button type="button" id="buttonAbandonner" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalAbandonner">Abandonner</button>
        
        <div id="syncPartie">
            <span class="glyphicon glyphicon-refresh"></span>
            <h1>Synchronisation des joueurs</h1>
        </div>
        <div id="backgroundPlateau">
        </div>
        <div id="plateauPartie">
            <div class="rowBille mleft-325">
                <span class="bille" onclick="clickBille(this,0,4)" onmouseover="survolBille(this,0,4)" onmouseout="unsurvolBille(this,0,4)"></span>
                <span class="bille" onclick="clickBille(this,0,6)" onmouseover="survolBille(this,0,6)" onmouseout="unsurvolBille(this,0,6)"></span>
                <span class="bille" onclick="clickBille(this,0,8)" onmouseover="survolBille(this,0,8)" onmouseout="unsurvolBille(this,0,8)"></span>
                <span class="bille" onclick="clickBille(this,0,10)" onmouseover="survolBille(this,0,10)" onmouseout="unsurvolBille(this,0,10)"></span>
                <span class="bille" onclick="clickBille(this,0,12)" onmouseover="survolBille(this,0,12)" onmouseout="unsurvolBille(this,0,12)"></span>
            </div>
            <div class="rowBille mleft-295">
                <span class="bille" onclick="clickBille(this,1,3)" onmouseover="survolBille(this,1,3)" onmouseout="unsurvolBille(this,1,3)"></span>
                <span class="bille" onclick="clickBille(this,1,5)" onmouseover="survolBille(this,1,5)" onmouseout="unsurvolBille(this,1,5)"></span>
                <span class="bille" onclick="clickBille(this,1,7)" onmouseover="survolBille(this,1,7)" onmouseout="unsurvolBille(this,1,7)"></span>
                <span class="bille" onclick="clickBille(this,1,9)" onmouseover="survolBille(this,1,9)" onmouseout="unsurvolBille(this,1,9)"></span>
                <span class="bille" onclick="clickBille(this,1,11)" onmouseover="survolBille(this,1,11)" onmouseout="unsurvolBille(this,1,11)"></span>
                <span class="bille" onclick="clickBille(this,1,13)" onmouseover="survolBille(this,1,13)" onmouseout="unsurvolBille(this,1,13)"></span>
            </div>
            <div class="rowBille  mleft-265">
                <span class="bille" onclick="clickBille(this,2,2)" onmouseover="survolBille(this,2,2)" onmouseout="unsurvolBille(this,2,2)"></span>
                <span class="bille" onclick="clickBille(this,2,4)" onmouseover="survolBille(this,2,4)" onmouseout="unsurvolBille(this,2,4)"></span>
                <span class="bille" onclick="clickBille(this,2,6)" onmouseover="survolBille(this,2,6)" onmouseout="unsurvolBille(this,2,6)"></span>
                <span class="bille" onclick="clickBille(this,2,8)" onmouseover="survolBille(this,2,8)" onmouseout="unsurvolBille(this,2,8)"></span>
                <span class="bille" onclick="clickBille(this,2,10)" onmouseover="survolBille(this,2,10)" onmouseout="unsurvolBille(this,2,10)"></span>
                <span class="bille" onclick="clickBille(this,2,12)" onmouseover="survolBille(this,2,12)" onmouseout="unsurvolBille(this,2,12)"></span>
                <span class="bille" onclick="clickBille(this,2,14)" onmouseover="survolBille(this,2,14)" onmouseout="unsurvolBille(this,2,14)"></span>
            </div>
            <div class="rowBille  mleft-235">
                <span class="bille" onclick="clickBille(this,3,1)" onmouseover="survolBille(this,3,1)" onmouseout="unsurvolBille(this,3,1)"></span>
                <span class="bille" onclick="clickBille(this,3,3)" onmouseover="survolBille(this,3,3)" onmouseout="unsurvolBille(this,3,3)"></span>
                <span class="bille" onclick="clickBille(this,3,5)" onmouseover="survolBille(this,3,5)" onmouseout="unsurvolBille(this,3,5)"></span>
                <span class="bille" onclick="clickBille(this,3,7)" onmouseover="survolBille(this,3,7)" onmouseout="unsurvolBille(this,3,7)"></span>
                <span class="bille" onclick="clickBille(this,3,9)" onmouseover="survolBille(this,3,9)" onmouseout="unsurvolBille(this,3,9)"></span>
                <span class="bille" onclick="clickBille(this,3,11)" onmouseover="survolBille(this,3,11)" onmouseout="unsurvolBille(this,3,11)"></span>
                <span class="bille" onclick="clickBille(this,3,13)" onmouseover="survolBille(this,3,13)" onmouseout="unsurvolBille(this,3,13)"></span>
                <span class="bille" onclick="clickBille(this,3,15)" onmouseover="survolBille(this,3,15)" onmouseout="unsurvolBille(this,3,15)"></span>
            </div>
            <div class="rowBille mleft-205">
                <span class="bille" onclick="clickBille(this,4,0)" onmouseover="survolBille(this,4,0)" onmouseout="unsurvolBille(this,4,0)"></span>
                <span class="bille" onclick="clickBille(this,4,2)" onmouseover="survolBille(this,4,2)" onmouseout="unsurvolBille(this,4,2)"></span>
                <span class="bille" onclick="clickBille(this,4,4)" onmouseover="survolBille(this,4,4)" onmouseout="unsurvolBille(this,4,4)"></span>
                <span class="bille" onclick="clickBille(this,4,6)" onmouseover="survolBille(this,4,6)" onmouseout="unsurvolBille(this,4,6)"></span>
                <span class="bille" onclick="clickBille(this,4,8)" onmouseover="survolBille(this,4,8)" onmouseout="unsurvolBille(this,4,8)"></span>
                <span class="bille" onclick="clickBille(this,4,10)" onmouseover="survolBille(this,4,10)" onmouseout="unsurvolBille(this,4,10)"></span>
                <span class="bille" onclick="clickBille(this,4,12)" onmouseover="survolBille(this,4,12)" onmouseout="unsurvolBille(this,4,12)"></span>
                <span class="bille" onclick="clickBille(this,4,14)" onmouseover="survolBille(this,4,14)" onmouseout="unsurvolBille(this,4,14)"></span>
                <span class="bille" onclick="clickBille(this,4,16)" onmouseover="survolBille(this,4,16)" onmouseout="unsurvolBille(this,4,16)"></span>
            </div>
            <div class="rowBille mleft-235">
                <span class="bille" onclick="clickBille(this,5,1)" onmouseover="survolBille(this,5,1)" onmouseout="unsurvolBille(this,5,1)"></span>
                <span class="bille" onclick="clickBille(this,5,3)" onmouseover="survolBille(this,5,3)" onmouseout="unsurvolBille(this,5,3)"></span>
                <span class="bille" onclick="clickBille(this,5,5)" onmouseover="survolBille(this,5,5)" onmouseout="unsurvolBille(this,5,5)"></span>
                <span class="bille" onclick="clickBille(this,5,7)" onmouseover="survolBille(this,5,7)" onmouseout="unsurvolBille(this,5,7)"></span>
                <span class="bille" onclick="clickBille(this,5,9)" onmouseover="survolBille(this,5,9)" onmouseout="unsurvolBille(this,5,9)"></span>
                <span class="bille" onclick="clickBille(this,5,11)" onmouseover="survolBille(this,5,11)" onmouseout="unsurvolBille(this,5,11)"></span>
                <span class="bille" onclick="clickBille(this,5,13)" onmouseover="survolBille(this,5,13)" onmouseout="unsurvolBille(this,5,13)"></span>
                <span class="bille" onclick="clickBille(this,5,15)" onmouseover="survolBille(this,5,15)" onmouseout="unsurvolBille(this,5,15)"></span>
            </div>
            <div class="rowBille mleft-265">
                <span class="bille" onclick="clickBille(this,6,2)" onmouseover="survolBille(this,6,2)" onmouseout="unsurvolBille(this,6,2)"></span>
                <span class="bille" onclick="clickBille(this,6,4)" onmouseover="survolBille(this,6,4)" onmouseout="unsurvolBille(this,6,4)"></span>
                <span class="bille" onclick="clickBille(this,6,6)" onmouseover="survolBille(this,6,6)" onmouseout="unsurvolBille(this,6,6)"></span>
                <span class="bille" onclick="clickBille(this,6,8)" onmouseover="survolBille(this,6,8)" onmouseout="unsurvolBille(this,6,8)"></span>
                <span class="bille" onclick="clickBille(this,6,10)" onmouseover="survolBille(this,6,10)" onmouseout="unsurvolBille(this,6,10)"></span>
                <span class="bille" onclick="clickBille(this,6,12)" onmouseover="survolBille(this,6,12)" onmouseout="unsurvolBille(this,6,12)"></span>
                <span class="bille" onclick="clickBille(this,6,14)" onmouseover="survolBille(this,6,14)" onmouseout="unsurvolBille(this,6,14)"></span>
            </div>
            <div class="rowBille mleft-295">
                <span class="bille" onclick="clickBille(this,7,3)" onmouseover="survolBille(this,7,3)" onmouseout="unsurvolBille(this,7,3)"></span>
                <span class="bille" onclick="clickBille(this,7,5)" onmouseover="survolBille(this,7,5)" onmouseout="unsurvolBille(this,7,5)"></span>
                <span class="bille" onclick="clickBille(this,7,7)" onmouseover="survolBille(this,7,7)" onmouseout="unsurvolBille(this,7,7)"></span>
                <span class="bille" onclick="clickBille(this,7,9)" onmouseover="survolBille(this,7,9)" onmouseout="unsurvolBille(this,7,9)"></span>
                <span class="bille" onclick="clickBille(this,7,11)" onmouseover="survolBille(this,7,11)" onmouseout="unsurvolBille(this,7,11)"></span>
                <span class="bille" onclick="clickBille(this,7,13)" onmouseover="survolBille(this,7,13)" onmouseout="unsurvolBille(this,7,13)"></span>
            </div>
            <div class="rowBille mleft-325">
                <span class="bille" onclick="clickBille(this,8,4)" onmouseover="survolBille(this,8,4)" onmouseout="unsurvolBille(this,8,4)"></span>
                <span class="bille" onclick="clickBille(this,8,6)" onmouseover="survolBille(this,8,6)" onmouseout="unsurvolBille(this,8,6)"></span>
                <span class="bille" onclick="clickBille(this,8,8)" onmouseover="survolBille(this,8,8)" onmouseout="unsurvolBille(this,8,8)"></span>
                <span class="bille" onclick="clickBille(this,8,10)" onmouseover="survolBille(this,8,10)" onmouseout="unsurvolBille(this,8,10)"></span>
                <span class="bille" onclick="clickBille(this,8,12)" onmouseover="survolBille(this,8,12)" onmouseout="unsurvolBille(this,8,12)"></span>
            </div>
        </div>
        <div id="finPartie">
            <div id="finPartieGlyph">
            </div>
            <h1 id="finPartieTitre"></h1>
            <p id="finPartieMessage"></p>
              <button type="button" class="btn btn-danger btn-lg" onclick="redirectionMenu();">Retour au menu</button>
        </div>
        <div class="modal fade" tabindex="-1" role="dialog" id="modalAbandonner" aria-labelledby="modalAbandonner">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><span class="glyphicon glyphicon-warning-sign"></span> Abandonner la partie ?</h4>
              </div>
              <div class="modal-body">
                <p>Vous �tes sur le point d'abandonner la partie, voulez-vous vraiment le faire ?</p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Je veux continuer</button>
                <button type="button" class="btn btn-danger" onclick="setAbandonner()">Abandonner</button>
              </div>
            </div>
          </div>
        </div>
    	</div>
    </body>
</html>