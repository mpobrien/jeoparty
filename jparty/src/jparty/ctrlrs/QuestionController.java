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
    @Inject
    MobileDetector mobileDetect;
	
	String questionId;
 
    @Inject
    public QuestionController(Visit visit){
        super(visit);
    }

    @Override
    public WebResponse get(){
        this.questionId = this.args.get(0);
        Question q = jpd.getQuestionById(this.questionId);
		HashMap context = new HashMap();
		context.put("question", q.getQuestion());
		context.put("answer", q.getAnswer());
		if( visit.getStringSafe("json").equals("1") ){
			return responses.json(q);
		}else{
            String prefix = mobileDetect.isMobile() ? "mobile/" : "";
			return responses.render(prefix + "question.html", context);
		}
    }

}
