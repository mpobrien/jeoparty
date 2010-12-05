package jparty.models;
import com.google.code.morphia.*;
import com.google.code.morphia.annotations.*;
import com.google.code.morphia.Morphia;
import com.google.inject.*;
import com.mongodb.*;
import java.util.*;
import jparty.models.*;
import org.apache.log4j.*;
import org.bson.types.*;
            
public class JpartyDAO{

	private final Mongo mongo;

	@Inject
	public JpartyDAO(Mongo mongo){//{{{
		this.mongo = mongo;
	}//}}}

	public DBCollection getQuestions(){//{{{
		return this.mongo.getDB("jparty").getCollection("questions");
	}//}}}

	public DBCollection getCategories(){//{{{
		return this.mongo.getDB("jparty").getCollection("categories");
	}//}}}

	public Category getCategoryById(String id){//{{{
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject obj = getCategories().findOne(query);
		if( obj == null ) return null;
		return new Category(obj);
	}//}}}

	public Question getQuestionById(String id){//{{{
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject obj = getQuestions().findOne(query);
		if( obj == null ) return null;
		return new Question(obj);
	}//}}}

    public List<Question> getByCategoryId(String categoryId){//{{{
        ArrayList<Question> result = new ArrayList<Question>();
        DBObject query = new BasicDBObject();
        query.put("category_id", new ObjectId(categoryId));
        DBCursor cur = getQuestions().find(query);
        while(cur.hasNext()) {
            DBObject next = cur.next();
            result.add( new Question(next));
        }
        return result;
    }//}}}

    public List<Category> getCategories(Integer start, Integer limit){//{{{
        ArrayList<Category> result = new ArrayList<Category>();
        //DBObject query = new BasicDBObject();
        //query.put("category_id", new ObjectId(categoryId));
        //DBCursor cur = getQuestions().find(query);
		DBObject fields = new BasicDBObject();
		fields.put("name",1);
		fields.put("_id", 1);
        DBCursor cur = getCategories().find(new BasicDBObject(), fields, start, limit );
		int count =0;
        while(cur.hasNext()) {
            DBObject next = cur.next();
            result.add( new Category(next));
			count++;
			if( count >= limit) return result;
        }
        return result;
    }//}}}

}
