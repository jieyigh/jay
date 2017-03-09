package tj.david.springboot.common.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by David on 2016/1/30.
 */
@Component("initListener")
public class InitListener implements ServletContextListener {

    public void initPath(ServletContext servletContext){

        String contextPath = servletContext.getContextPath();
        servletContext.setAttribute("contextPath", contextPath);

    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        this.initPath(servletContext);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
