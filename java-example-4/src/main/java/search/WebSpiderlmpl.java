package search;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebSpiderlmpl implements WebSpider {
    public Parser getParser(){
        return new Parserlmpl();
    }

    public List<String> getHtmlFromWeb(){
        List<String> pages = new ArrayList<String>();

		/*因为课程列表不是直接在原网址中，而是通过json传给https://www.graduate.study.cam.ac.uk/courses的，
		 *所以我们不能直接从原网址中获取，因先找到课程列表的json文件，并获取，然后解析
		 * */
        String url = "https://2018.gaobase.admin.cam.ac.uk/api/courses.datatable";

        try {	/*从https://2018.gaobase.admin.cam.ac.uk/api/courses.datatable获取json数据
				*并将其转化为InputStream类型数据
				*/
            URLConnection conn = new URL(url).openConnection();

            //设置请求参数
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.15) Gecko/20110303 Firefox/3.6.15");
            conn.setRequestProperty("Content-Type", "text/html");
            conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            //将json转化为字符串
            InputStream is = conn.getInputStream();
            byte[] b = new byte[4096];
            StringBuffer out = new StringBuffer();
            for (int n; (n = is.read(b)) != -1;)
            {
                out.append(new String(b, 0, n));
            }

            //解析json数据，获取所有课程 的地址，并写入pages
            JSONObject data = JSONObject.fromObject(out.toString());
            //数组，包括所有课程基本信息
            JSONArray arr = data.getJSONArray("data");
            for(int i = 0;i<arr.size();i++){
                JSONObject info=arr.getJSONObject(i);
                //获取单个课程信息地址
                String buf = info.getString("prospectus_url");
                //存入
                if(buf.indexOf("www.2018.graduate")!=-1){
                    pages.add(buf);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        //多线程
        ExecutorService pool = Executors.newFixedThreadPool(pages.size());
        List<Future> fList = new ArrayList<>();
        for (int i = 0; i < pages.size(); i++) {
            Callable c = new MyCallable(pages.get(i));
            fList.add(pool.submit(c));
        }
        pool.shutdown();
        pages.clear();
        try {
            for (Future f : fList) {
                if (!f.get().toString().equals(""))
                    pages.add(f.get().toString());
            }
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pages;
    }

    private String getHtmlCode(String url) {
        int i = (int) (Math.random()*1000); //做一个随机延时，防止网站屏蔽
        while(i!=0){
            i--;
        }
        try {
            Document doc = Jsoup.connect(url).timeout(7000).get();
            System.out.println(url);
            return doc.toString();
        }
        catch (IOException e) {
            return "";
        }
    }

    class MyCallable implements Callable<String> {
        private String url;

        public MyCallable(String url) {
            this.url = url;
        }

        public String call() {
            return getHtmlCode(url);
        }
    }
}
