package search;

import vo.Program;
import main.Searcher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private static Searcher searcher;
    private static List<Program> programs;
    private static DataBase dataBase;

    public static void Scene() {
        searcher=new Searcher();
        programs=new ArrayList<>();
        dataBase=new DataBase();
        JFrame frame = new JFrame("数据库实验");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton buttonIn = new JButton("添加信息到数据库");
        buttonIn.setBounds(20, 20, 150, 25);
        panel.add(buttonIn);

        JButton buttonAdd = new JButton("增加");
        buttonAdd.setBounds(30, 55, 100, 25);
        panel.add(buttonAdd);

        JButton buttonDe = new JButton("删除");
        buttonDe.setBounds(150, 55, 100, 25);
        panel.add(buttonDe);

        JButton buttonMo = new JButton("修改");
        buttonMo.setBounds(270, 55, 100, 25);
        panel.add(buttonMo);

        JButton buttonQu = new JButton("查询");
        buttonQu.setBounds(390, 55, 100, 25);
        panel.add(buttonQu);


        final JTextField userText2 = new JTextField(20);
        userText2.setBounds(190, 20, 200, 25);
        panel.add(userText2);

        JButton buttonSearch = new JButton("搜索");
        buttonSearch.setBounds(400, 20, 100, 25);
        panel.add(buttonSearch);

        JTextArea resultArea = new JTextArea();
        resultArea.setLineWrap(true);
        JScrollPane jScrollPane=new JScrollPane(resultArea);
        jScrollPane.setBounds(30,100,500,400);
        panel.add(jScrollPane);

        //写入数据库按钮
        buttonIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                programs=searcher.searcher();
                dataBase.add(programs);
            }
        });

        //添加按钮
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Str = JOptionPane.showInputDialog("请输入添加的ID值:");
                dataBase.in(Str);
            }
        });

        //删除按钮
        buttonDe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Str = JOptionPane.showInputDialog("请输入删除的ID值:");
                dataBase.delete(Str);
            }
        });

        //更改按钮
        buttonMo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Str2 = JOptionPane.showInputDialog("请输入原ID值:");
                String Str1 = JOptionPane.showInputDialog("请输入更换的ID值:");
                dataBase.modify(Str1,Str2);
            }
        });

        //查询按钮
        buttonQu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Str=DataBase.query();
                resultArea.setText("");
                resultArea.setText("programName:"+"\n"+Str);
            }
        });

        //搜索按钮
        buttonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    resultArea.setText("");
                    if(userText2.getText().isEmpty()) return;
                    else {
                        String Str = userText2.getText().trim();
                        String result = DataBase.search(Str);
                        resultArea.setText(result);
                    }
                }catch (Exception m){
                    m.printStackTrace();
                }
            }
        });
    }
}
