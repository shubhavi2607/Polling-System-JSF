package CreatePoll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="cpoll")
@ReferencedBean
@RequestScoped
public class CreatePollBean {
    
    private String op1,op2,op3,ques,username;
    private int countpoll;
    
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = externalContext.getSessionMap();
    String un = (String) sessionMap.get("username");
    
    
    final String JDBC_URL = "jdbc:mysql://localhost:3306/javaProject";
    final String USER = "root";
    final String PW = "";

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
     
    public CreatePollBean(){
        
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

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }
    
    
    public String creatp(){
        
        int c = getcount()+1;
        
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
                PreparedStatement ps = con.prepareStatement("INSERT INTO poll VALUES (?,?,?,?,?,?,?,?,?)");
               
                ps.setString(1, un);
                ps.setString(2, Integer.toString(c));
                ps.setString(3, ques);
                if(op1.equals("")){
                    ps.setString(4, "-");}
                else{
                    ps.setString(4, op1);
                }
                if(op2.equals("")){
                    ps.setString(5, "-");}
                else{
                    ps.setString(5, op2);
                }
                if(op3.equals("")){
                    ps.setString(6, "-");}
                else{
                    ps.setString(6, op3);
                }
                ps.setInt(7, 0);
                ps.setInt(8, 0);
                ps.setInt(9, 0);
                ps.executeUpdate();
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
        
        return "myPolls.xhtml";
        
    }
    
    public int getcount(){
        int count=0;
        int countchk=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(PollsCount) as pc from poll where Username=?");
            ps.setString(1, un);
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt("pc");
            countchk = chkCount(count);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return countchk;
    }
    
    public int chkCount(int c){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement("SELECT PollsCount as pc from poll where Username=?");
            ps.setString(1, un);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                String pc = rs.getString("pc");
                
                for(int i=1; i<=c; i++){
                    if(Integer.parseInt(pc)!=i){
                        c = Integer.parseInt(pc);
                    }else{
                        continue;
                    }
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return c;
    }
}
