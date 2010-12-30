package jparty.ctrlrs;
import jparty.models.*;
import com.mob.web.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.common.collect.*;
import com.google.inject.*;

@At("^/q/(\\w+)/?$")
public class CategoryController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

	@Inject
	JpartyDAO jpd;
    @Inject
    MobileDetector mobileDetect;
	
	String categoryId;
	boolean json;

    @Inject
    public CategoryController(Visit visit){
        super(visit);
    }

    public WebResponse get(){
        this.categoryId = this.args.get(0);
        Category category = jpd.getCategoryById(this.categoryId);
        if( category == null ){
            return new StringWebResponse("Not found. :(");
        }
        List<Category.Question> questions = category.getQuestions();
        HashMap context = new HashMap();
        context.put("questions", questions);
        context.put("categoryName", category.getName());
        if( visit.getStringSafe("json").equals("1") ){
            return responses.json(questions);
        }else{
            String prefix = mobileDetect.isMobile() ? "mobile/" : "";
            return responses.render(prefix + "qs2.html", context);
        }
    }

}
