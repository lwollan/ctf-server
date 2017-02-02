package no.soprasteria.sikkerhet.owasp.ctf.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class CtFServer {

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitParameter("javax.ws.rs.Application", "no.soprasteria.sikkerhet.owasp.ctf.CtFApplication");

        Server server = new Server(9090);
        server.setHandler(context);
        server.start();

    }
}
