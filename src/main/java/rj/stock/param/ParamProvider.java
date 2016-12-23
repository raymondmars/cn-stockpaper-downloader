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
    private final String fetchDate;
    //set default value
    private String userName = System.getenv("CNSTOCK_USERNAME");
    private String passWord = System.getenv("CNSTOCK_PASSWORD");

    public ParamProvider(String fd, String username, String password) {
        this.fetchDate = fd;
        if(username != null && username.trim().length() > 0) {
            this.userName  = username;
        }
        if(password != null && password.trim().length() > 0) {
            this.passWord  = password;
        }
    }

    public ParamProvider(String fd) {
        this(fd, null,null);
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.passWord;
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
