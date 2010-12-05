package jparty.ctrlrs;
import jparty.models.*;
import com.mob.web.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.common.collect.*;
import com.google.inject.*;

@At("^/(\\d+)?/?$")
public class HomeController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

	@Inject
	JpartyDAO jpd;
	
	Integer pageNum;

    @Override
    public WebResponse get(HttpServletRequest req, HttpServletResponse res){
		if( this.args.get(0) != null ){
			this.pageNum = new Integer(this.args.get(0));
		}else{
			this.pageNum = 0;
		}

		List<Category> categories = jpd.getCategories(this.pageNum*10, 10);
		HashMap context = new HashMap();
		context.put("nextPage", this.pageNum + 1);
		context.put("categories", categories);

		return responses.render("index.html", context);
    }

}
