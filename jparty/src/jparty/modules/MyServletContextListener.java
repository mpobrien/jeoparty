package jparty.modules;
import jparty.ctrlrs.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.inject.servlet.*;
import com.mob.web.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

public @Singleton class MyServletContextListener extends GuiceServletContextListener {

	//@Override
	protected Injector getInjector() {
		Injector injector = Guice.createInjector( new MainServletModule(),
											      new MongoModule(),
		                                          new AppConfigModule( HomeController.class.getPackage() ) );
		return injector;
	}

}
