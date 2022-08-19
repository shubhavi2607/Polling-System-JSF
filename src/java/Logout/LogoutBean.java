package Logout;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="logout")
@RequestScoped
public class LogoutBean {
    
    public LogoutBean(){
        
    }
    
    public String logoutUser(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "aboutUs.xhtml";
    }
}
