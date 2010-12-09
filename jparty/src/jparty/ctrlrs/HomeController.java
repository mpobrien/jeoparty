package jparty.ctrlrs;
import jparty.models.*;
import com.mob.web.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.gson.*;

@At("^/?$")
public class HomeController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

	@Inject
	JpartyDAO jpd;
	
	Integer pageNum;
	boolean json=false;

	public void preprocess(Visit visit){
		this.json = visit.getStringSafe("json").equals("1");
	}

    @Override
    public WebResponse get(Visit visit){
		List<Category> categories = getRandomCategorySet();
		HashMap context = new HashMap();
		context.put("categories", categories);
		if( json ){
			return responses.json(categories);
		}else{
			return responses.render("index.html", context);
		}
    }

	private List<Category> getRandomCategorySet(){
		Long numCategories = jpd.getCategoryCount();
		Random randy = new Random();
		this.pageNum = randy.nextInt(numCategories.intValue()/10);
		List<Category> categories = jpd.getCategories(this.pageNum*10, 10);
		return categories;
	}
}
