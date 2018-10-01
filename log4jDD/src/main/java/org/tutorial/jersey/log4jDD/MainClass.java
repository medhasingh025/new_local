package org.tutorial.jersey.log4jDD;

import org.apache.log4j.Logger;

public class MainClass 
{
	private static final Logger log =Logger.getLogger(MainClass.class);

	public static void main( String[] args )
	{
		MainClass obj =new MainClass();
		obj.doLogMessages();

	}

	private void doLogMessages() {	
		log.fatal("hello");
		log.error("hello");
		log.warn("hello");
		log.info("hello");
		log.trace("Hello World");
		log.debug("Hello World");

	}
}
