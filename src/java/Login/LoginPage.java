package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="L")
@SessionScoped
@ReferencedBean 
public class LoginPage {
    
    private String username, pass;
    private UIComponent textpass, textuser;
    
    static int count = 3;
    
    final String JDBC_URL = "jdbc:mysql://localhost:3306/javaProject";
    final String USER = "root";
    final String PW = "";
    
    public LoginPage(){
        
    }
    
    public UIComponent getTextpass() {
        return textpass;
    }

    public void setTextpass(UIComponent textpass) {
        this.textpass = textpass;
    }

    public UIComponent getTextuser() {
        return textuser;
    }

    public void setTextuser(UIComponent textuser) {
        this.textuser = textuser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public boolean verify(String username, String pass){
            
            
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
            PreparedStatement ps = con.prepareStatement("SELECT Username FROM registrations WHERE Username=? AND Password=?");
            ps.setString(1, username);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                return true;
            }
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
        
        return doActionMsg();
    }
    
    public boolean doActionMsg() {

        count = count-1;
        FacesContext context = FacesContext.getCurrentInstance();
        if(count>0){
            context.addMessage(textuser.getClientId(), new FacesMessage("Incorrect Credentials!     Tries left: "+ Integer.toString(count)));
        }
        return false;
    }
    
    
    public String submit(){
      
        if(verify(username, pass)){
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("username", username);
            
            return "allPolls.xhtml";
        }
        else{
            if(count>0 & count<4){
                return "login.xhtml";
            }else{
                count = 3;
            return "index.xhtml";
            }            
        }
    }    
}
