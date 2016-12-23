package rj.stock;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import rj.stock.param.IParamProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raymond on 04/12/2016.
 */
public final class CnStockPaperDownloader {

    private static final String CnStock_Paper_Host = "http://paper.cnstock.com";
    private static final String CnStock_Paper_LoginRoot = "http://passport.cnstock.com/PassPortWeb/services/userBase/login";
    private static final String MockAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    private static final int ReadTimeOut = 60*1000;
    private static final int MaxDownloadFileSize = 2 * 1000 * 1000;  //set max download file to 2M
    private static final SimpleDateFormat Uf1 = new SimpleDateFormat("yyyy-MM/dd");
    private static final SimpleDateFormat Uf2 = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat StoreFolderFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final IParamProvider provider;


    public CnStockPaperDownloader(IParamProvider pd) {
        if(pd == null) {
            throw new NullPointerException("Param provider is null");
        }
        this.provider = pd;
    }
    public void execute() throws IOException, ParseException {
        String storePath = String.format("%s/%s", System.getProperty("user.dir"), StoreFolderFormat.format(this.provider.getFetchDate()));
        File file = new File(storePath);
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        Map<String, String> map = this.getLogonCookies();
        for(int i = 1 ; i <= 11 ; i++) {
            String targetUrl = getDownloadUrl(i);
            Connection.Response resp = Jsoup.connect(targetUrl).userAgent(MockAgent).cookies(map).header("Host","paper.cnstock.com").maxBodySize(MaxDownloadFileSize).timeout(ReadTimeOut).ignoreContentType(true).execute();
            if(resp.statusCode() == 200 && resp.bodyAsBytes().length > 0) {
                if(resp.contentType().equals("application/pdf")) {
                    FileOutputStream fs = new FileOutputStream(new File(String.format("%s/%d.pdf", storePath, i)));
                    fs.write(resp.bodyAsBytes());
                    fs.close();
                    System.out.println(String.format(">> Download finished: %d", i));
                } else {
                    System.out.println(String.format(">> Download failed: %d, may be cookie is expired or the file dose not exist: %s", i, targetUrl));
                }

            }

        }
    }

    private Map<String, String> getLogonCookies() {
        Map<String, String> cookieMap = new HashMap<>();

        String logonUrl = String.format("%s?username=%s&password=%s&_=1482465962500", CnStock_Paper_LoginRoot, this.provider.getUserName(), this.provider.getPassword());
        System.out.println("Login url: " + logonUrl);

        CookieStore httpCookieStore = new BasicCookieStore();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(httpCookieStore);
        HttpClient client = builder.build();

        HttpGet loginGet = new HttpGet(logonUrl);
        try {
            HttpResponse response = client.execute(loginGet);
            if(response.getStatusLine().getStatusCode() == 200) {
                for (Cookie ck : httpCookieStore.getCookies()) {
                   cookieMap.put(ck.getName(), ck.getValue());
                }

            } else {
                System.out.println("登陆失败: " + response.getEntity().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cookieMap;
    }
    protected String getDownloadUrl(int index) throws ParseException {
        Date fetchDate = this.provider.getFetchDate();
        return String.format("%s/images_new/3/%s/%d/%s%d_pdf.pdf", CnStock_Paper_Host, Uf1.format(fetchDate), index, Uf2.format(fetchDate), index);

    }
}
