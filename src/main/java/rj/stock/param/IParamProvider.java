package rj.stock.param;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Raymond on 04/12/2016.
 */
public interface IParamProvider {
    String getUserName();
    String getPassword();
    Date getFetchDate() throws ParseException;  //format is : yyyy-mm-dd
}
