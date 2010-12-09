package jparty.ctrlrs;
import jparty.models.*;
import com.mob.web.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.common.collect.*;
import com.google.inject.*;

@At("^/qa/(\\w+)/?$")
public class QuestionController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

	@Inject
	JpartyDAO jpd;
	
	String questionId;
	boolean json = false;

	public void preprocess(Visit visit){
		this.json = visit.getStringSafe("json").equals("1");
	}

    @Override
    public WebResponse get(Visit visit){
        this.questionId = this.args.get(0);
        Question q = jpd.getQuestionById(this.questionId);
		HashMap context = new HashMap();
		context.put("question", q.getQuestion());
		context.put("answer", q.getAnswer());
		if( this.json ){
			return responses.json(q);
		}else{
			return responses.render("question.html", context);
		}
    }

}
