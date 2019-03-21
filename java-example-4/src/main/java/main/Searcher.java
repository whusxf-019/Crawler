package main;

import search.Parser;
import search.WebSpider;
import vo.Program;

import java.util.ArrayList;
import java.util.List;

public class Searcher {

    public static List<Program> searcher() {

        try {
            Class.forName("search.impl.Manager");
        } catch (ClassNotFoundException e) {
            System.out.println("Manager类不存在");
        }

        List<WebSpider> webSpiders = SearchManager.getWebSpider();

        List<Program> programs = new ArrayList<>();
        for (WebSpider webSpider : webSpiders) {
            Parser parser = webSpider.getParser();
            List<String> pages = webSpider.getHtmlFromWeb();
            for (String page : pages) {
                programs.add(parser.parseHtml(page));
            }
        }

        //数据库表country,university,school,programname,homepage,degree不能为空
        for(int i=0;i<programs.size();i++){
            if(programs.get(i).getCountry()==null)
                programs.get(i).setCountry("United States");
            if(programs.get(i).getUniversity()==null)
                programs.get(i).setUniversity("University of Cambridge");
            if(programs.get(i).getSchool()==null||programs.get(i).getProgramName()==null||programs.get(i).getHomepage()==null||programs.get(i).getDegree()==null){
                programs.remove(i);
                --i;
            }
        }
        return programs;
    }
}
