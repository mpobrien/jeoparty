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
    @Inject
    MobileDetector mobileDetect;
	
	Integer pageNum;
	boolean json=false;
 
    @Inject
    public HomeController(Visit visit){
        super(visit);
    }

    @Override
    public WebResponse get(){
        Date now = new Date();
        log.info(now.toString() + " accessed / from " + visit.getRequest().getRemoteAddr() +
                 " UserAgent: "+ visit.getRequest().getHeader("User-Agent")  +
                 " Referer: " + visit.getRequest().getHeader("Referer")
                 );
		List<Category> categories = getRandomCategorySet();
		HashMap context = new HashMap();
		context.put("categories", categories);
		if( visit.getStringSafe("json").equals("1") ){
			return responses.json(categories);
		}else{
            String prefix = false ? "mobile/" : "";
			return responses.render(prefix + "index.html", context);
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
