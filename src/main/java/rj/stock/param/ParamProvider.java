package rj.stock.param;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raymond on 04/12/2016.
 */
public final class ParamProvider implements IParamProvider {

    private final static SimpleDateFormat ParseFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String cookiePath;
    private final String fetchDate;

    public ParamProvider(String cp, String fd) {
        this.cookiePath = cp;
        this.fetchDate = fd;
    }

    @Override
    public String getCookieStorePath() {
        if(this.cookiePath != null) {
            return this.cookiePath;
        } else {
            String defaultPath = String.format("%s/site_cookie", System.getProperty("user.dir"));
            File file = new File(defaultPath);
            if(!file.exists()) {
                throw new RuntimeException(String.format("cookie file is not exist: ", defaultPath));
            }
            return defaultPath;
        }
    }

    @Override
    public Date getFetchDate() throws ParseException {
        if(this.fetchDate == null) {
            return new Date();  //default return current date
        } else {
            //format yyyy-MM-dd
          return ParseFormat.parse(this.fetchDate);
        }
    }
}
