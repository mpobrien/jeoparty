package jparty.models;
import com.google.inject.*;
import com.mongodb.*;
import java.util.*;
import jparty.models.*;
import org.apache.log4j.*;
import org.bson.types.ObjectId;
            
public class JpartyDAO{

	private final Mongo mongo;
	private long categoryCount = 0;

	@Inject
	public JpartyDAO(Mongo mongo){//{{{
		this.mongo = mongo;
		this.categoryCount  = this.mongo.getDB("jparty").getCollection("categories").getCount();
	}//}}}

	//public DBCollection getQuestions(){//{{{
		//return this.mongo.getDB("jparty").getCollection("questions");
	//}//}}}

	public Category getCategoryById(String id){//{{{
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject obj = this.mongo.getDB("jparty").getCollection("categories").findOne(query);
        if( obj == null ) return null;
		return new Category(obj);
	}//}}}

    public List<Category> getCategories(Integer start, Integer limit){//{{{
        ArrayList<Category> result = new ArrayList<Category>();
        //DBObject query = new BasicDBObject();
        //query.put("category_id", new ObjectId(categoryId));
        //DBCursor cur = getQuestions().find(query);
		DBObject fields = new BasicDBObject();
		fields.put("name",1);
		fields.put("_id", 1);
        DBCollection cats = mongo.getDB("jparty").getCollection("categories");
        DBCursor cur = cats.find(new BasicDBObject(), fields).skip(start).limit(limit);
		int count =0;
        while(cur.hasNext()) {
            DBObject next = (DBObject)cur.next();
            result.add( new Category(next));
			count++;
			if( count >= limit) return result;
        }
        return result;
    }//}}}

	public Long getCategoryCount() { return categoryCount; }

}
