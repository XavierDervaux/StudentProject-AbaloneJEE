package be.abalone.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.abalone.database.AbstractDAOFactory;
import be.abalone.database.DAOFactory;
import be.abalone.database.SQLRequest;
import be.abalone.model.*;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAOFactory adf = (DAOFactory) AbstractDAOFactory.getFactory(0);
		Connection conn = SQLRequest.getInstance();
		PrintWriter out = response.getWriter();

		try {
			Joueur test3 = new Joueur("Roger", "cfvbhgcfbhjcgfbhjcgfbhjvcgfvbh", "u@t.com");
			adf.getJoueurDAO().create(test3);
			String sql = "{? = call last_inserted_rowid(?)}";
			CallableStatement statement = conn.prepareCall(sql); 
			statement.registerOutParameter(1,Types.INTEGER); 
			statement.setInt(2,1); 
			statement.execute(); 
			int resultat = statement.getInt(1);

			out.println("Last id = " + resultat);
		} catch (SQLException e) {
			out.println(e.toString());
		}

		out.println("fhh= ");
		// Achievement test1 = new Achievement("ACV_TEST", "Un test réussi",
		// "Réussir à se connecter a la bdd avec succès.");
		// Joueur test4 = new Joueur("Marcel", "cfvbhgcfbhjcgfbhjcgfbhjvcgfvbh",
		// "u@t.com");
		// Historique test2 = new Historique(new Date(), 6, 3, false, test3,
		// test4);


		// Gerer les booléens
		// Récuper le dernier id enregistré //Package créé, triggers a jour,
		// reste à voir pour récupe la valeur

		/*
		 * Boolean estConnecte = false; if(estConnecte){ //On redirige vers le
		 * menu response.sendRedirect("/Abalone/Menu.html"); } else { //N'est
		 * pas encore connecté, on affihce le formulaire de
		 * connexion/inscription
		 * this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").
		 * forward(request, response); }
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
