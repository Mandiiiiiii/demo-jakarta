package io.openliberty.sample.jakarta.cdi;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@WebServlet(name = "greetingServlet", urlPatterns = { "/greeting" })
public class GreetingServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    
    @Inject
    private Greeting greeting;

    @Produces
    public GreetingNoDefaultConstructor getInstance() {
        return new GreetingNoDefaultConstructor("Howdy");
    }

    @Produces
    public GreetingNoDefaultConstructor getInstance(@Disposes String word) {
        return new GreetingNoDefaultConstructor(word);
    }
    

    public void close(@Disposes GreetingNoDefaultConstructor construct) {
        System.out.println("constructor:" + construct.greet("Bob"));
    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// use @Inject greeting
        // String greetingString = greeting.greet("Bob");

        // use @Produces greeting
        String greetingString = getInstance("Nothing").greet("Bob");
        
        res.setContentType("text/html;charset=UTF-8");
		res.getWriter().println(greetingString);
	}
    
}
