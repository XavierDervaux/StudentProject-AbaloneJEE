<%@ include file="header.jsp" %>
      <div id="corps">
        <a href="menu.html">
            <button type="button" class="btn btn-primary btn-lg">Retour au menu</button>
        </a>
        <h1>Mes statistiques</h1>
        <table class="table table-bordered">
            <tr>
                <th colspan="2" class="th-title">Mes parties</th>
            </tr>
            <tr>
                <th class="grey">Jouées</th>
                <td class="info">50</td>
            </tr>
            <tr>
                <th class="grey">Gagnées</th>
                <td class="success">30</td>
            </tr>
             <tr>
                <th class="grey">Perdues</th>
                <td class="danger">15</td>
            </tr>
              <tr>
                <th class="grey">Abandonnées</th>
                <td class="warning">5</td>
            </tr>
        </table>
          <h1>Mon historique</h1>
        <table class="table">
            <tr>
                <th class="th-title">Adversaire</th>
                <th class="th-title">Résultat</th>
            </tr>
            <tr class="success">
                <td>
                    Toto
                </td>
                <td>
                6-0
                </td>
            </tr>
            <tr class="danger">
                <td>
                    Toto
                </td>
                <td>
                0-6
                </td>
            </tr>
             <tr class="warning">
                <td>
                    Toto
                </td>
                <td>
                0-6
                </td>
            </tr>
        </table>
    </div>
</body>
</html>  
