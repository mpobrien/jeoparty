package jparty.models;
import java.util.*;
import com.mongodb.*;
import org.bson.types.*;

public class Category {
    private ObjectId id;
    private String name;
    private Integer gameId;
    private List<Category.Question> questions;

    public ObjectId getId() { return id; }
    public String getName() { return name; }
    public Integer getGameId() { return gameId; }
    public List<Category.Question> getQuestions(){ return this.questions; }

    public Category(com.mongodb.DBObject backing) {
        this.id = (ObjectId)backing.get("_id");
        this.name   = (String)backing.get("name");
        this.gameId = (Integer)backing.get("gameId");
        BasicDBList questionsObj = (BasicDBList)backing.get("questions");
        ArrayList<Category.Question> backingQuestions = new ArrayList<Category.Question>();
        if( questionsObj != null ){
            for(Object item : questionsObj){
                backingQuestions.add(new Category.Question((DBObject)item));
            }
        }
        this.questions = backingQuestions;
    }

    public static class Question{
        private String question;
        private String answer;
        private List<String> urls;
        private Integer value;
        private Boolean isDailyDouble;

        public Question(com.mongodb.DBObject backing){
            this.question = (String)backing.get("question");
            this.answer = (String)backing.get("answer");
            this.value = (Integer)backing.get("value");
            this.isDailyDouble = (Boolean)backing.get("isDouble");
            BasicDBList urlsObj = (BasicDBList)backing.get("urls");
            if( urlsObj != null ){
                this.urls = new ArrayList<String>();
                for(Object item : urlsObj){
                    this.urls.add((String)item);
                }
            }else{
                this.urls = null;
            }
        }

        public String getQuestion(){ return this.question; }
        public String getAnswer(){ return this.answer; }
        public Integer getValue(){ return this.value; }
        public Boolean isDailyDouble(){ return this.isDailyDouble != null && this.isDailyDouble; }
        public List<String> getUrls(){ return this.urls; }
    }

}
