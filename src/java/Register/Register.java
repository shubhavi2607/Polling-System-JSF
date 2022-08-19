package Register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@ManagedBean(name="R")
@RequestScoped
@ReferencedBean  
public class Register {
    
    private String firstName,lastName,username,pass1,pass2;
    private UIComponent textpass1, textuser;
    
    final String JDBC_URL = "jdbc:mysql://localhost:3306/javaProject";
    final String USER = "root";
    final String PW = "";


    public UIComponent getTextuser() {
        return textuser;
    }

    public void setTextuser(UIComponent textuser) {
        this.textuser = textuser;
    }
  
    public Register(){
        
    }
    
    public UIComponent getTextpass1() {
        return textpass1;
    }

    public void setTextpass1(UIComponent textpass1) {
        this.textpass1 = textpass1;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }
    
    public boolean verify(String username, String firstName ,String lastName ,String pass1, String pass2){
        
        if(pass1.equals(pass2)){
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(JDBC_URL, USER, PW);
                
                PreparedStatement ps = con.prepareStatement("INSERT INTO registrations VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
               
                ps.setString(1, username);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, pass1);
                
                if(ps.executeUpdate()>0){
                    return true;
                }
                
            }catch(Exception e){
                doActionUser();
                e.printStackTrace();
            }   
        }
        else{
            doActionPass();
        }        
        return false;
    }
    
    
    public String doActionPass() {

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(textpass1.getClientId(), new FacesMessage("Password Does Not Match"));
        return "";
    }
    
    public String doActionUser() {

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(textuser.getClientId(), new FacesMessage("Username already taken!"));
        return "";
    }
    
    public String submit(){  
        if(verify(username, firstName, lastName, pass1, pass2)){  
            return "login.xhtml";  
        }else return "index.xhtml";
    }     
}
