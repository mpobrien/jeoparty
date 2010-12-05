package jparty.modules;
import com.mongodb.*;
import com.google.code.morphia.Morphia;
import com.google.inject.*;
import org.apache.log4j.*;
import jparty.models.*;

public class MongoModule extends AbstractModule{
	private static final Logger log = Logger.getLogger( MongoModule.class );


    public void configure(){
		try{
			Mongo m = new Mongo("127.0.0.1", 27017);
			bind(Mongo.class).toInstance(m);
		}catch(Exception e){
			log.error("couldn't get DB connection.", e);
		}

		Morphia morphia = new Morphia();
// 		morphia.map(TextSnippet.class)
// 		       .map(TextNote.class)
// 		       .map(Verb.class)
// 			   .map(Definition.class)
// 			   .map(User.class);
		bind(Morphia.class).toInstance(morphia);
    }



}
