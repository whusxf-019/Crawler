package search;


import vo.Program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DataBase {
//    private static final String url="jdbc:mysql://119.27.166.115:2017/java_exp";
//    private static final String userName="whu_iss_2017";
//    private static final String password="iss_java_2017";
    private static final String url="jdbc:mysql://localhost:3306/root";
    private static final String userName="root";
    private static final String password="123456";
    private static Connection conn;
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("找不到驱动!");
            e.printStackTrace();
        }
        try{
            conn= DriverManager.getConnection(url,userName,password);
            if(conn!=null){
                System.out.println("connection successful");
            }
        }catch(SQLException e){
            System.out.println("connection fail");
            e.printStackTrace();
        }
    }

    //将爬取的信息写入数据库
    // 执行动态SQL语句。通常通过PreparedStatement实例实现。
    public void add(List<Program> programs){
        for(Program program:programs) {
            try {
                String sql = "insert into program(Id,country,university,school,program_name,homepage,location,email,phone_number,degree,deadline_with_aid,deadline_without_aid)values(?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1,program.getId());
                statement.setString(2,program.getCountry());
                statement.setString(3,program.getUniversity());
                statement.setString(4,program.getSchool());
                statement.setString(5,program.getProgramName());
                statement.setString(6,program.getHomepage());
                statement.setString(7,program.getLocation());
                statement.setString(8,program.getEmail());
                statement.setString(9,program.getPhoneNumber());
                statement.setString(10,program.getDegree());
                statement.setString(11,program.getDeadlineWithAid());
                statement.setString(12,program.getDeadlineWithoutAid());
                statement.execute();
            }catch (SQLException e){
                e.printStackTrace();
            }
            System.out.println("写入成功!");
        }
    }

    //在数据库中添加内容，在此处为添加ID值
    public void in(String Str){
        try {
            String sql = "insert into program(Id)values(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,Str);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除数据库中内容，在此处为删除某ID值
    public void delete(String Str){
        try {
            String sql = "delete from program where Id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,Str);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //更改数据库中内容，在此处将某ID改为某值
    public void modify(String Str1,String Str2){
        try {
            String sql = "update program set Id=? where Id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,Str1);
            statement.setString(2,Str2);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //查看数据库中的内容，在此处为查看剑桥大学的项目名称
    //此处为String，因为要在TextArea中显示
    public static String query(){
        String query="";
        try {
            String sql = "select * from program where university =\"University of Cambridge\"";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                query+=rs.getString("program_name")+"\n";
            }
            System.out.println("OK");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return query;
    }

    //在数据库中搜索内容，此处用到模糊搜索，用来搜索项目名称
    //此处为String，因为要在TextArea中显示
    public static String search(String Str){
        String search="";
            try {
                String sql = "select * from program where program_name like\"%" + Str + "%\"";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    search += "ID:" + rs.getString("Id") + "\n" + "country:" + rs.getString("country") + "\n" + "university:" + rs.getString("university") + "\n" + "school:" + rs.getString("school") + "\n" + "programname:" + rs.getString("program_name") + "\n" + "homepage:" + rs.getString("homepage") + "\n\n";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return search;
    }
}
