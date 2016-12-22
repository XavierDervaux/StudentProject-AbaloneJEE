package be.abalone.controller;

import java.io.IOException; //123456794
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import be.abalone.model.*;

@WebServlet("/Test")
public class Test extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Test() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Personne andy = new Personne("Bianco", "Andy", true);
    	Personne xavier = new Personne("Dervaux", "Xavier", false);
    	
    	request.setAttribute("andy", andy);
    	request.setAttribute("xavier", xavier);
        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
