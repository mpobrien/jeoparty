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

    @Override
    public WebResponse get(HttpServletRequest req, HttpServletResponse res){
        this.categoryId = this.args.get(0);
		Category category = jpd.getCategoryById(this.categoryId);
		List<Question> questions = jpd.getByCategoryId(this.categoryId);
		HashMap context = new HashMap();
		context.put("questions", questions);
		context.put("categoryName", category.getName());
		return responses.render("qs.html", context);
    }

}
