package PollStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import models.PollStats;



@ManagedBean(name="pollstat")
@ReferencedBean
@RequestScoped
public class PollStatsBean {
    
    String totalpolls;
    
    final String JDBC_URL = "jdbc:mysql://localhost:3306/javaProject";
    final String USER = "root";
    final String PW = "";
    
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = externalContext.getSessionMap();
    String username = (String) sessionMap.get("username");
    
    public PollStatsBean(){
        
    }

    public String getTotalpolls() {
        return totalpolls;
    }

    public void setTotalpolls(String totalpolls) {
        this.totalpolls = totalpolls;
    }
    
    
    
    
    public List<PollStats> sendRec(){
        
        List<PollStats> list = new ArrayList<PollStats>();       
        
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cont = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = cont.prepareStatement("SELECT * FROM poll where Username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                PollStats mp = new PollStats();
                mp.setUn(rs.getString("Username"));
                mp.setPcount(rs.getString("PollsCount"));
                mp.setQues(rs.getString("Question"));
                mp.setOp1(rs.getString("Option1"));
                mp.setOp2(rs.getString("Option2"));
                mp.setOp3(rs.getString("Option3"));
                mp.setOp1_c(rs.getString("Option1_count"));
                mp.setOp2_c(rs.getString("Option2_count"));
                mp.setOp3_c(rs.getString("Option3_count"));
                list.add(mp);
            } 
            }catch(Exception e){
                e.printStackTrace();
            }
        return list;
    }
    
    public String tp(){
        
    
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection cont = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = cont.prepareStatement("SELECT COUNT(Username)as c FROM poll where Username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            totalpolls = rs.getString("c");
        }catch(Exception e){
                e.printStackTrace();
            }
        
        return totalpolls;
    }
}
   
