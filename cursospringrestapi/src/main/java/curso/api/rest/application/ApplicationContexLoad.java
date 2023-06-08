package curso.api.rest.application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

@Component
public class ApplicationContexLoad implements ApplicationContextAware{
	
	@Autowired
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) 
			throws BeansException {		
		this.applicationContext = applicationContext;	
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
