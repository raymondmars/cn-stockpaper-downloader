package rj.stock;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import rj.stock.param.IParamProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raymond on 04/12/2016.
 */
public class CnStockPaperDownloader {

    private static final String CnStock_Paper_Host = "http://paper.cnstock.com";
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
        String cookieContent = new String(Files.readAllBytes(Paths.get(this.provider.getCookieStorePath()))).replace("\n","");
        String[] ca = cookieContent.split(";");
        Map<String, String> map = new HashMap<>();
        for(String c : ca) {
            String[] kp = c.split("=");
            map.put(kp[0], kp[1]);
        }
        for(int i = 1 ; i <= 10 ; i++) {
            String targetUrl = getDownloadUrl(i);
            Connection.Response resp = Jsoup.connect(targetUrl).userAgent(MockAgent).cookies(map).maxBodySize(MaxDownloadFileSize).timeout(ReadTimeOut).ignoreContentType(true).execute();
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

    protected String getDownloadUrl(int index) throws ParseException {
        Date fetchDate = this.provider.getFetchDate();
        return String.format("%s/images_new/3/%s/%d/%s%d_pdf.pdf", CnStock_Paper_Host, Uf1.format(fetchDate), index, Uf2.format(fetchDate), index);

    }
}
