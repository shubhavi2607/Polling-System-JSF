package MyPolls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import models.MyPolls;

@ManagedBean(name="mypolls")
@ReferencedBean
@RequestScoped
public class MyPollsBean {
    
    final String JDBC_URL = "jdbc:mysql://localhost:3306/javaProject";
    final String USER = "root";
    final String PW = "";
    
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = externalContext.getSessionMap();
    String username = (String) sessionMap.get("username");
    
    public MyPollsBean(){
        
    }
    
    public List<MyPolls> sendRec(){

        List<MyPolls> list = new ArrayList<MyPolls>();       
        
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cont = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = cont.prepareStatement("SELECT * FROM poll where Username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                MyPolls mp = new MyPolls();
                mp.setUn(rs.getString("Username"));
                mp.setPcount(rs.getString("PollsCount"));
                mp.setQues(rs.getString("Question"));
                mp.setOp1(rs.getString("Option1"));
                mp.setOp2(rs.getString("Option2"));
                mp.setOp3(rs.getString("Option3"));
                list.add(mp);
            } 
            }catch(Exception e){
                e.printStackTrace();
            }
        return list;
    }
    
    public void deletePoll(String Q, String c){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cont = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps1 = cont.prepareStatement("DELETE FROM poll WHERE Username=? and Question=? and PollsCount=?");
            ps1.setString(1, username);
            ps1.setString(2, Q);
            ps1.setString(3, c);
            ps1.execute();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
