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
	
	String categoryId;
	boolean json;

	public void preprocess(Visit visit){
		this.json = visit.getStringSafe("json").equals("1");
	}

    @Override
    public WebResponse get(Visit visit){
        this.categoryId = this.args.get(0);
		Category category = jpd.getCategoryById(this.categoryId);
		List<Question> questions = jpd.getByCategoryId(this.categoryId);
		HashMap context = new HashMap();
		context.put("questions", questions);
		context.put("categoryName", category.getName());
		if( json ){
			return responses.json(questions);
		}else{
			return responses.render("qs.html", context);
		}
    }

}
