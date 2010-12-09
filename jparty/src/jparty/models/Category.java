package jparty.models;
import com.google.code.morphia.*;
import java.util.*;
import com.google.code.morphia.annotations.*;
import com.mongodb.*;
import org.bson.types.*;
            
public class Category{
	//{ "_id" : ObjectId("4cfae0f240b1d21276000000"), "game_id" : 1, "name" : "THE OLD TESTAMENT" }

    public Category(DBObject dbObj){//{{{
        setId((ObjectId)dbObj.get("_id"));
        setName((String)dbObj.get("name"));
//         setQuestion((String)dbObj.get("question"));
//         setValue((Integer)dbObj.get("value"));
    }//}}}

    ObjectId id;
	String idStr;
    String name;
//     String question; Integer value;
    
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; this.idStr = this.id.toString(); }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

	public String getIdStr() { return this.id.toString(); }

}
