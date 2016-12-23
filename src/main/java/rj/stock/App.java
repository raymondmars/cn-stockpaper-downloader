package rj.stock;


import org.jsoup.HttpStatusException;
import rj.stock.param.IParamProvider;
import rj.stock.param.ParamProvider;

import java.io.IOException;

/**
 * CN Stock Paper PDF downloader
 *
 */
public final class App {

    public static void main( String[] args ) {
        String fetchDate = args.length > 0 ? args[0] : null;
        String username  = args.length > 1 ? args[1] : null;
        String password  = args.length > 2 ? args[2] : null;
        IParamProvider provider = new ParamProvider(fetchDate, username, password);

        CnStockPaperDownloader cnd = new CnStockPaperDownloader(provider);
        try {
            cnd.execute();
        } catch (IOException e) {
            if(e instanceof HttpStatusException) {
                HttpStatusException hse = (HttpStatusException)e;
                System.out.println(String.format("%s => %s, %d", hse.getMessage(), hse.getUrl(), hse.getStatusCode()));
            } else {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
