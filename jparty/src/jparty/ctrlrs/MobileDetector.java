package jparty.ctrlrs;
import jparty.models.*;
import com.mob.web.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.google.common.collect.*;
import com.google.inject.*;
import com.google.inject.servlet.*;
import com.google.gson.*;
import java.util.regex.*;

@RequestScoped
public class MobileDetector{

    private static final String DESKTOP = "(windows|linux|os\\s+[x9]|solaris|bsd)";
    private static final String MOBILE  = "(iphone|ipod|blackberry|android|palm|windows\\s+ce)";
    private static final String BOT     = "(spider|crawl|slurp|bot)";

    private Visit visit;
    private boolean isMobile = false;

    @Inject
    public MobileDetector(Visit visit){
        this.visit = visit;
    }

    private String getUserAgent(){//{{{
        String userAgent = visit.getRequest().getHeader("HTTP_X_OPERAMINI_PHONE_UA");
        userAgent = userAgent != null ? userAgent : visit.getRequest().getHeader("HTTP_X_OPERAMINI_PHONE_UA");
        userAgent = userAgent != null ? userAgent : visit.getRequest().getHeader("HTTP_X_SKYFIRE_PHONE");
        userAgent = userAgent != null ? userAgent : visit.getRequest().getHeader("HTTP_USER_AGENT");
        userAgent = userAgent != null ? userAgent : "";
        return userAgent;
    }//}}}

    public boolean isMobile(){//{{{
        String userAgent = getUserAgent().toLowerCase();
        return !userAgent.matches(MOBILE) && (userAgent.matches(DESKTOP) || userAgent.matches(MOBILE));
    }//}}}

}
