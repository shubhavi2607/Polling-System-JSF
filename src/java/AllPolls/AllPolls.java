package AllPolls;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="A")
@RequestScoped
@ReferencedBean  
public class AllPolls implements Serializable{
    int c;
    
    final String JDBC_URL = "jdbc:mysql://localhost:3306/javaProject";
    final String USER = "root";
    final String PW = "";
    
    public AllPolls(){
        
    }

    String un,ques,op1,op2,op3, radioValue;
    int pollCount, index;
    
    List<AllPolls> list = new ArrayList<AllPolls>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPollCount() {
        return pollCount;
    }

    public void setPollCount(int pollCount) {
        this.pollCount = pollCount;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getRadioValue() {
        return radioValue;
    }

    public void setRadioValue(String radioValue) {
        this.radioValue = radioValue;        
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }
    
    
    
    
    
    
    
    
    public List<AllPolls> sendRec(){

        
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            ps = con.prepareStatement("SELECT * FROM poll");
            rs = ps.executeQuery();
            int index = 0;
            list = new ArrayList<AllPolls>();
            while(rs.next()){
                AllPolls p = new AllPolls();
                p.setUn(rs.getString("Username"));
                p.setQues(rs.getString("Question"));
                p.setOp1(rs.getString("Option1"));
                p.setOp2(rs.getString("Option2"));
                p.setOp3(rs.getString("Option3"));
                p.setPollCount(rs.getInt("PollsCount"));
                p.setIndex(index);
                list.add(p);
                index++;
            } 
            }catch(Exception e){
                e.printStackTrace();
            }
        return list;
    }
    
    public void vote(String user, String pc, int index){
                
        
        String query = "";
        int count = 0;
        if(list.get(index).op1.equals(radioValue)){
            
            count = getCount1(user,pc)+1;
            query = "UPDATE poll SET Option1_count=? where Username=? and PollsCount=?;";
        }
        if(list.get(index).op2.equals(radioValue)){
            count = getCount2(user,pc)+1;
            query = "UPDATE poll SET Option2_count=? where Username=? and PollsCount=?;";
        }
        if(list.get(index).op3.equals(radioValue)){
            count = getCount3(user,pc)+1;
            query = "UPDATE poll SET Option3_count=? where Username=? and PollsCount=?;";
        }
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,count);
            ps.setString(2, user);
            ps.setInt(3,Integer.parseInt(pc));
            
            ps.executeUpdate();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public int getCount1(String user, String pc){
        int b = 0;
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement("Select Option1_count as oc from poll where Username=? and PollsCount=?");
            ps.setString(1,user);
            ps.setInt(2,Integer.parseInt(pc));
            ResultSet rs = ps.executeQuery();
            rs.next();
            b = rs.getInt("oc");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }
    
    public int getCount2(String user, String pc){
        int b = 0;
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement("Select Option2_count as oc from poll where Username=? and PollsCount=?");
            ps.setString(1,user);
            ps.setInt(2,Integer.parseInt(pc));
            ResultSet rs = ps.executeQuery();
            rs.next();
            b = rs.getInt("oc");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }
    
    public int getCount3(String user, String pc){
        int b = 0;
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement("Select Option3_count as oc from poll where Username=? and PollsCount=?");
            ps.setString(1,user);
            ps.setInt(2,Integer.parseInt(pc));
            ResultSet rs = ps.executeQuery();
            rs.next();
            b = rs.getInt("oc");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
