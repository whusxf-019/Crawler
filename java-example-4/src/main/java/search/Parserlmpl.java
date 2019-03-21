package search;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.UUID;

import vo.Program;

public class Parserlmpl implements Parser {
    public Program parseHtml(String html){
//        System.out.println(html);
        Program pro = new Program();
        pro.setId(UUID.randomUUID().toString().substring(0,32));
        Document doc = Jsoup.parse(html);

//            int i = (int) (Math.random()*1000); //做一个随机延时，防止网站屏蔽
//            while(i!=0){
//                i--;
//            }
//            doc = Jsoup.connect(html).timeout(7000).get();
        //doc = Jsoup.connect(html).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(10000).get();

        /**以下内容根据网页具体html来获得，参数由*.html决定
         */
        //用于获取信息的两个临时存储器
        Elements elm;
        Element elmt;
        //得到课程名
        elm = doc.select(".campl-sub-title");
        if(elm!=null) {
            pro.setProgramName(elm.html());
        }else{
            pro.setProgramName("NULL");
        }
        //得到学院名
        elmt = doc.select(".campl-column3").first();
        elm = elmt.select(".field-item h4");
        elm = elm.select(".fa-university+a");
        if(elm!=null) {
            pro.setSchool(elm.html());
        }else{
            pro.setSchool("NULL");
        }
        //获得联系方式
        elm = elmt.select(".field-item h4");
        elm = elm.select(".fa-phone+a");
        if(elm!=null) {
            pro.setPhoneNumber(elm.html());
        }else{
            pro.setPhoneNumber("NULL");
        }
        //获得地址
        elm = elmt.select(".field-item h4");
        elm = elm.select(".fa-location+a");
        if(elm!=null) {
            pro.setLocation(elm.html());
        }else{
            pro.setLocation("NULL");
        }
        //得到课程主页地址
        elm = elmt.select(".field-item h4");
        elm = elm.select(".fa-link+a");
        elm = elm.select("a[href]");
        if(elm!=null) {
            pro.setHomepage(elm.attr("abs:href"));
        }else{
            pro.setHomepage("NULL");
        }
        //得到邮箱地址
        elm = elmt.select(".field-item h4");
        elm = elm.select(".fa-envelope+a");
        elm = elm.select("a[href]");
        if(elm!=null) {
            pro.setEmail(elm.attr("abs:href"));
        }else{
            pro.setEmail("NULL");
        }
        //获取学位信息
        elm = elmt.select(".field-item h4");
        elm = elm.select(".fa-graduation-cap+a");
        if(elm!=null) {
            pro.setDegree(elm.html());
        }else{
            pro.setDegree("NULL");
        }
        //无奖学金deadline
        elm = elmt.select(".field-item");
        elmt = elm.select(".panel-body dd").get(1);
        if(elmt!=null) {
            pro.setDeadlineWithoutAid(elmt.html());
        }else{
            pro.setDeadlineWithoutAid("NULL");
        }
        //有奖学金deadline
        elmt = elm.select(".panel-body dd").get(3);
        if(elmt!=null) {
            pro.setDeadlineWithAid(elmt.html());
        }else{
            pro.setDeadlineWithAid("NULL");
        }
        //设置学校名和所在国家
        pro.setCountry("United States");
        pro.setUniversity("University of Cambridge");

        //返回一条信息
        return pro;
    }

}
