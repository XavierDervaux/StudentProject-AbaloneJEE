<%@ include file="header.jsp" %>
    <body>
    	<div id="corps">
	        <div id="contentConnexion">
	          <!-- Nav tabs -->
	          <ul class="nav nav-tabs" role="tablist">
	            <li role="presentation" class="active"><a href="#connection" aria-controls="connection" role="tab" data-toggle="tab">Connection</a></li>
	            <li role="presentation"><a href="#inscription" aria-controls="inscription" role="tab" data-toggle="tab">Inscription</a></li>
	          </ul>
	
	          <!-- Tab panes -->
	          <div class="tab-content">
	            <div role="tabpanel" class="tab-pane active" id="connection">
	                    <form class="form-horizontal" name="connection" method="post" action="">
	                      <div class="form-group">
	                        <label for="emailConnection" class="col-sm-2 control-label">Email</label>
	                        <div class="col-sm-10">
	                          <input type="email" class="form-control" id="emailConnection" name="emailConnection" onchange="checkConnecion(); validateEmail(this,true)" placeholder="Email" maxlength="64">
	                        </div>
	                      </div>
	                      <div class="form-group">
	                        <label for="passwordConnection" class="col-sm-2 control-label">Mot de passe</label>
	                        <div class="col-sm-10">
	                          <input type="password" class="form-control" id="passwordConnection" name="passwordConnection" onchange="checkConnecion();" placeholder="Password" maxlength="30">
	                        </div>
	                      </div>
	                      <div class="form-group">
	                        <div class="col-sm-offset-2 col-sm-10">
	                          <div class="checkbox">
	                            <label>
	                              <input type="checkbox"> Se souvenir de moi
	                            </label>
	                          </div>
	                        </div>
	                    </div>
	                  <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                      <button type="submit" id="submitConnection" class="btn btn-danger right" disabled>Connection</button>
	                    </div>
	                  </div>
	                </form>
	            </div>
	            <div role="tabpanel" class="tab-pane" id="inscription">
	                <form class="form-horizontal" name="inscription" method="post" action="">
	                    <div class="form-group">
	                        <label for="pseudoInscription" class="col-sm-2 control-label">Votre pseudo</label>
	                        <div class="col-sm-10">
	                          <input type="text" class="form-control" id="pseudoInscription" name="pseudoInscription" onchange="checkInscription()" placeholder="Pseudo" maxlength="64">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="emailInscription" class="col-sm-2 control-label">Email</label>
	                        <div class="col-sm-10">
	                          <input type="text" class="form-control" id="emailInscription" name="emailInscription" onchange="checkInscription(); validateEmail(this,true);" placeholder="Votre email" maxlength="64">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="passwordInscription" class="col-sm-2 control-label">Mot de passe</label>
	                        <div class="col-sm-10">
	                          <input type="password" class="form-control" id="passwordInscription" name="passwordInscription" onchange="checkInscription(); checkPasswordInscription();" placeholder="Votre mot de passe" maxlength="30">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label for="confirmPasswordInscription" class="col-sm-2 control-label">Confirmer mot de passe</label>
	                        <div class="col-sm-10">
	                          <input type="password" class="form-control" id="confirmPasswordInscription" name="confirmPasswordInscription" onchange="checkInscription(); checkPasswordInscription();" placeholder="Confirmer votre mot de passe" maxlength="30">
	                        </div>
	                    </div>
	                     <div class="form-group">
	                        <div class="col-sm-offset-2 col-sm-10">
	                          <button type="submit" id="submitInscription" class="btn btn-success right" disabled="disabled">Inscription</button>
	                        </div>
	                  </div>
	                </form>
	            </div>
	          </div>
	        </div>
	    </div>
    </body>
</html>