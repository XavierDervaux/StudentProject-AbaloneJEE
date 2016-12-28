<%@ include file="header.jsp" %>
        <div id="corps">
        	<a href="menu.html"  class="button-back right">
	            <button type="button" class="btn btn-primary btn-lg">
	                Retour au menu
	            </button>
	        </a>
	        <h1>Recherche de joueur</h1>
	        <table class="table table--striped" id="listJoueur">
	            
	        </table>
	        <div class="modal fade" tabindex="-1" role="dialog" id="invitation" aria-labelledby="invitation">
	          <div class="modal-dialog" role="document">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title">Invitation partie</h4>
	              </div>
	              <div class="modal-body">
	                <p id="messageInvitation"></p>
	              </div>
	              <div class="modal-footer">
	                <button type="button" class="btn btn-danger" onclick="respondInvitation(false)">Refuser</button>
	                <button type="button" class="btn btn-success" onclick="respondInvitation(true)">Accepter</button>
	              </div>
	            </div>
	          </div>
	        </div>
	        <div class="modal fade" tabindex="-1" role="dialog" id="respondInvitation" aria-labelledby="respondInvitation">
	          <div class="modal-dialog" role="document">
	            <div class="modal-content">
	              <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title">R�ponse de l'invitation</h4>
	              </div>
	              <div class="modal-body">
	                <p id="respondMessageInvitation"></p>
	              </div>
	              <div class="modal-footer">

	              </div>
	            </div>
	          </div>
	        </div>
    	</div>
    </body>
</html>