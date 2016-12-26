<%@ include file="header.jsp" %>
        <div id="corps">
        	 <div class="row">
	            <a href="jouer.html">
	              <div class="col-xs-6 col-md-3">
	                  <div class="thumbnail h100px">
	                        <h1><span class="glyphicon center glyphicon-play glyph-menu"/></h1>
	                        <h2 class="center">Jouer</h2>
	                  </div>
	              </div>
	            </a>
	            <a href="historique.html">
	              <div class="col-xs-6 col-md-3">
	                   <div class="thumbnail h100px">
	                        <h1><span class="glyphicon center glyphicon-repeat glyph-menu"/></h1>
	                        <h2 class="center">Historique</h2>
	                  </div>
	              </div>
	            </a>
	            <a href="parametre.html">
	              <div class="col-xs-6 col-md-3">
	                   <div class="thumbnail h100px">
	                        <h1><span class="glyphicon center glyphicon-cog glyph-menu"/></h1>
	                        <h2 class="center">Paramètre</h2>
	                  </div>
	              </div>
	            </a>
	             <a>
	                <div class="col-xs-6 col-md-3">
	                    <form method="post" action="index.html">
	                     <input type="checkbox" name="estDeconnexion" class="hidden" checked />
	                           <div class="thumbnail h100px">
	                            <button type="submit" class="no-design">
	                                <h1><span class="glyphicon center glyphicon-log-out glyph-menu"/></h1>
	                                <h2 class="center">Se déconnecter</h2>
	                            </button>
	                          </div>
	                    </form>
	                </div>
	            </a>
	        </div>
    	</div>
    </body>
</html>