 <%@ include file="header.jsp" %>
 	<div id="corps">
 		<a href="menu.html" class="button-back right">
            <button type="button" class="btn btn-primary btn-lg">Retour au menu</button>
        </a>
        <h1>Succ�s</h1>
            <table class="table table">
                <tr>
                    <th colspan="2" class="th-title">Mes succ�s accomplis</th>
                </tr>
                <c:if test="${accomplis.size() == 0}">
                	<tr>
                		<td> <pre>Aucun succ�s n'a encore �t� accompli !</pre></td>
                	</tr>
                </c:if>
                <c:forEach items="${accomplis}" var="item">
                	<tr class="achievement">
	                    <td>
	                        <h4>${item.getNom()}</h4>
	                        <pre>${item.getDescription()}</pre>
	                    </td>
                	</tr> 
             	</c:forEach>
                <tr>
                    <th colspan="2" class="th-title">Les succ�s en cours</th>
                </tr>
                <c:forEach items="${pasEncoreAccomplis}" var="item">
                	<tr class="achievement">
	                    <td>
	                        <h4>${item.getNom()}</h4>
	                        <pre>${item.getDescription()}</pre>
	                    </td>
                	</tr> 
             	</c:forEach>
        </table>
    </div>
</body>
</html>    