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

    @Override
    public WebResponse get(Visit visit){
        return responses.render("about.html");
    }

}
