package com.oracle.jp.shinyay.cache;

import com.oracle.jp.shinyay.cache.servlet.CacheServlet;
import com.oracle.jp.shinyay.cache.servlet.SessionServlet;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * Main class.
 *
 */
public class Main {

    public static final String BASE_URI;
    public static final String PROTOCOL;
    public static String HOST;
    public static final String HOST_LOCALHOST = "localhost";
    public static final String HOST_DEFAULT_ROOT = "0.0.0.0";
    public static final Optional<String> PORT;
    public static final Optional<String> APP_HOME;

    static{
        PROTOCOL = "http://";
        HOST = HOST_LOCALHOST;
        PORT = Optional.ofNullable(System.getenv("PORT"));
        APP_HOME = Optional.ofNullable(System.getenv("APP_HOME"));
        if(APP_HOME.isPresent()){
            HOST = HOST_DEFAULT_ROOT;
        }
        BASE_URI = PROTOCOL
                + HOST
                + ":"
                + PORT.orElseGet(() -> "8080")
                + "/";
    }

    public static HttpServer startServer() {

        WebappContext webappContext = new WebappContext("grizzly web context", "");
        ServletRegistration servletRegistration = webappContext.addServlet("SessionServlet", new SessionServlet());
        servletRegistration.addMapping("/session/*");
        ServletRegistration servletRegistrationCache = webappContext.addServlet("CacheServlet", new CacheServlet());
        servletRegistrationCache.addMapping("/cache/*");



        System.out.println("Starting grizzly... : " + BASE_URI);

        final ResourceConfig rc = new ResourceConfig().packages("com.oracle.jp.shinyay.cache");

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        webappContext.deploy(server);

        server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Main.class.getClassLoader(), "/static/"), "/");
        return server;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}

