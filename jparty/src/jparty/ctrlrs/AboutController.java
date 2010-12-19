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

@At("^/about/?$")
public class AboutController extends Controller{

    public AboutController(Visit visit){
        super(visit);
    }

    @Override
    public WebResponse get(){
        return responses.render("about.html");
    }

}
