package jparty.models;
import com.google.code.morphia.*;
import java.util.*;
import com.google.code.morphia.annotations.*;
import com.mongodb.*;
import org.bson.types.*;
            
public class Question{
    //{ "_id" : ObjectId("4cfae0f240b1d21276000009"), "answer" : "Sean Connery", "category_id" : ObjectId("4cfae0f240b1d21276000006"), "question" : "He won an Oscar for \"The Untouchables\"", "value" : 600 }

    public Question(DBObject dbObj){//{{{
        setId((ObjectId)dbObj.get("_id"));
        setCategoryId((ObjectId)dbObj.get("category_id"));
        setAnswer((String)dbObj.get("answer"));
        setQuestion((String)dbObj.get("question"));
        setValue((Integer)dbObj.get("value"));
    }//}}}

	ObjectId id;
    ObjectId categoryId;
    String answer;
    String question;
    Integer value;
    
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    
    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public ObjectId getCategoryId() { return categoryId; }
    public void setCategoryId(ObjectId categoryId) { this.categoryId = categoryId; }
	
	public ObjectId getId() { return id; }
	public void setId(ObjectId id) { this.id = id; }

}
