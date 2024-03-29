package Users;

import Admin.Adminstration;
import DatabaseConncection.DatabaseConnection;
import Inventory.Inventory;
import Marketing.MarketingFrame;
import Seller.MakeOrder;
import java.util.logging.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Login {
    
        DatabaseConnection dc=new DatabaseConnection("com.mysql.cj.jdbc.Driver",  "jdbc:mysql://localhost:3306/market",
                "root", "root");
        User user = new User();
    
     public boolean admin_is_exist()
            {
                    try {
                        int count = 0;
                        ResultSet rs=dc.executeQuery("select count(1) from employees where type='Admin'");
                        if(rs.next()) {
                            count = rs.getInt("count(1)");
                        }
                        if (count == 0) {
                            return true;
                        }
                    } catch (SQLException ex) {            }
                return false;
            }
     
     public void add_admin(String username,String password)
     {
         dc.excuteUpdate("insert into employees(username,password,type) values('"+username+"','"+password+"','Admin')");
         user_login back=new user_login();
         back.setVisible(true);
     }
             
        public boolean login(String username, String password){
                try {
                    ResultSet rs=dc.executeQuery("select * from employees where username='" + username + "' and password='" + password + "'");
                    if(rs.next()) {
                        switch(rs.getString("type"))
                        {
                            case "Admin" :
                        Adminstration admin=new Adminstration();
                        admin.setVisible(true);
                        break;
                    case "Marketing Employee":
                        MarketingFrame marketing = new MarketingFrame();
                        marketing.setVisible(true); 
                        break;
                    case "Inventory Employee" :
                        Inventory inventory = new Inventory();
                        inventory.setVisible(true); 
                        break;
                    case "Seller":
                        MakeOrder seller = new MakeOrder();
                        seller.setVisible(true);
                        break;
                        }
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username")); 
                        user.setpassword(rs.getString("password"));
                        return true;
                    }
                    else 
                        JOptionPane.showMessageDialog(null,"Invalid username or password", "Error",JOptionPane.ERROR_MESSAGE);
                }catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
    }
}
