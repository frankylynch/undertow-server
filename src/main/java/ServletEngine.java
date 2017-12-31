import ExampleServlet.*;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.*;

public class ServletEngine {

    public static final String MYAPP = "/myapp";

    public static void main(final String[] args)  {

        try {

            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(ServletEngine.class.getClassLoader())
                    .setContextPath(MYAPP)
                    .setDeploymentName("test.war")
                    .addListener(new ListenerInfo(MySessionListener.class))
                    .addServlets(
                            servlet("ExampleServlet",
                                    ExampleServlet.class)
                                    .addInitParam("message", "Hello World")
                                    .addMapping("/myservlet")
                                );

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();

            PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
                    .addPrefixPath(MYAPP, servletHandler);


            Undertow server = Undertow.builder()
                    .addHttpListener(8081, "localhost")
                    .setHandler(path)
                    .build();

            server.start();

        }  catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
