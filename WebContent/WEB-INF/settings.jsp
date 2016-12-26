<%@ include file="header.jsp" %>
 	<div id="corps">
 		<a href="menu.html">
            <button type="button" class="btn btn-primary btn-lg">Retour au menu</button>
        </a>
 		<h1>Paramètre</h1>
        <form method="post" name="settings" action="settings.html">
        	<c:if test="${not empty erreur}"> 
	            <p class="bg-danger"><span class="glyphicon glyphicon-info-sign"></span><span>${erreur}</span></p>
	         </c:if>
            <table class="table table-bordered">
                <tr>
                    <th class="grey w-ms-100">
                        Email
                    </th>
                    <td>
                     <input type="email" class="form-control" id="emailSetting" name="emailSetting" onchange="checkSetting(); validateEmail(this,true)" placeholder="Email" maxlength="64" value="${joueur.getEmail()}">
                    </td>
                </tr>
                <tr>
                    <th class="grey w-ms-100">Nouveau mot de passe</th>
                    <td>
                        <input type="password" class="form-control" id="passwordSetting" name="passwordSetting" onchange="checkSetting(); checkPasswordSetting();" placeholder="Nouveau mot de passe" maxlength="16" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-content="Le mot de passe doit contenir entre 8 et 16 caractères">
                    </td>
                </tr>
                <tr>
                    <th class="grey w-ms-100">Confirmer nouveu mot de passe</th>
                    <td>
                        <input type="password" class="form-control" id="confirmPasswordSetting" name="confirmPasswordSetting" onchange="checkSetting(); checkPasswordSetting();" placeholder="Confimer mot de passe" maxlength="16">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="submit" id="submitSetting" class="btn btn-danger right">Modifier</button>
                    </td>
                </tr>
            </table>
        </form>
 	</div>
 </body>