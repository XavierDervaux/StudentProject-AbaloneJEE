<%@ include file="header.jsp" %>
        <div id="corps">
        	<div id="whoPlayed">
            	<h1></h1>
	        </div>
	        <div id="hubScore">
	            <div id="hubScorePos">
	                <div class="hubscore"><h2>5</h2></div>
	                <div class="hubPlayer"><h1>Joueur 1<span class="glyphicon glyphicon-user"></span></h1></div>
	                <div class="hubPlayer"><h1><span class="glyphicon glyphicon-user"></span>Joueur 2</h1></div>
	                <div class="hubscore"><h2>4</h2></div>
	            </div>
	        </div>
	          <button type="button" id="buttonAbandonner" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#modalAbandonner">Abandonner</button>
	        
	        <div id="syncPartie">
	            <span class="glyphicon glyphicon-refresh"></span>
	            <h1>Synchronisation des joueurs</h1>
	        </div>
	        <div id="plateauPartie">
	            <div class="rowBille mleft-325">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille mleft-295">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille  mleft-265">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille  mleft-235">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille mleft-205">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille mleft-235">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille mleft-265">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille mleft-295">
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	                <span class="bille"></span>
	            </div>
	            <div class="rowBille mleft-325">
	                <div class="bille"></div>
	                <div class="bille"></div>
	                <div class="bille"></div>
	                <div class="bille"></div>
	                <div class="bille"></div>
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
	                <p>Vous êtes sur le point d'abandonner la partie, voulez-vous vraiment le faire ?</p>
	              </div>
	              <div class="modal-footer">
	                <button type="button" class="btn btn-primary" onclick="respondAbandonner(false)">Je veux continuer</button>
	                <button type="button" class="btn btn-danger" onclick="respondAbandonner(true)">Abandonner</button>
	              </div>
	            </div>
	          </div>
	        </div>
    	</div>
    </body>
</html>