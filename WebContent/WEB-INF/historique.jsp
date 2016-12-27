<%@ include file="header.jsp" %>
      <div id="corps">
        <a href="menu.html" class="button-back right">
            <button type="button" class="btn btn-primary btn-lg">Retour au menu</button>
        </a>
        <h1>Mes statistiques</h1>
        <table class="table table-bordered">
            <tr>
                <th colspan="2" class="th-title">Mes parties</th>
            </tr>
            <tr>
                <th class="grey">Jouées</th>
                <td class="info">${jouees}</td>
            </tr>
            <tr>
                <th class="grey">Gagnées</th>
                <td class="success">${gagnes}</td>
            </tr>
             <tr>
                <th class="grey">Perdues</th>
                <td class="danger">${perdues}</td>
            </tr>
              <tr>
                <th class="grey">Abandonnées</th>
                <td class="warning">${forfait}</td>
            </tr>
        </table>
        <h1>Mon historique</h1>
        <table class="table">
            <tr>
                <th class="th-title">Adversaire</th>
                <th class="th-title">Résultat</th>
                <th class="th-title">Date de la partie</th>
            </tr>  
            <c:forEach items="${liste}" var="item">
            	<c:choose> 
            		<c:when test="${item.getGagnant().equals(joueur)}">
            			<tr class="success">
            			<td>
                    		${item.getPerdant().getPseudo()}
                		</td>
            		</c:when>
            		<c:otherwise>
            			<c:choose> 
		            		<c:when test="${item.getEstForfait()}">
		            		 	<tr class="warning">
		            		 	<td>
		                    		${item.getGagnant().getPseudo()}
		                		</td>
		            		</c:when>
		            		<c:otherwise>
		            		 	<tr class="danger">
		            		 	<td>
		                    		${item.getGagnant().getPseudo()}
		                		</td>
		            		</c:otherwise>
		            	</c:choose>
            		</c:otherwise>
            	</c:choose>
                	<td>
                		${item.getScoreGagnant()} - ${item.getScorePerdant()}
               		</td>
               		<td>
               		 ${item.getDate()}
               		</td>
           		</tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>  
