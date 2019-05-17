package top.yzlin.homework;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import top.yzlin.homework.config.MainConfig;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

/**
 * web.xml直接删了
 */
public class WebProjectConfigInitializer  implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) {
        FilterRegistration.Dynamic struts2 = container.addFilter("struts2", StrutsPrepareAndExecuteFilter.class);
        struts2.addMappingForUrlPatterns(null,false,"/*");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(MainConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));
    }
}
