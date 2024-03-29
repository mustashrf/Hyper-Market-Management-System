
package Admin;
import DatabaseConncection.DatabaseConnection;
import Users.User;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Admin_implementation extends User {
    DatabaseConnection dc=new DatabaseConnection("com.mysql.cj.jdbc.Driver",  "jdbc:mysql://localhost:3306/market",
                "root", "root");

    
    void update_table(DefaultTableModel dft) {
        
        try {
            ResultSet Rs = dc.executeQuery("select * from employees where type != 'Admin'");
            ResultSetMetaData RSMD = Rs.getMetaData();
            int Column_count = RSMD.getColumnCount();
            DefaultTableModel DFT = dft;
            DFT.setRowCount(0);
 
            while (Rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= Column_count; i++) {
                    v2.add(Rs.getString("id"));
                    v2.add(Rs.getString("username"));
                    v2.add(Rs.getString("password"));
                    v2.add(Rs.getString("type"));
                }
                DFT.addRow(v2);
            }
        } catch (Exception e) {
        }
    }
    void add(DefaultTableModel dft,String username,String password,String tof)
    {
        try{
                ResultSet rs = dc.executeQuery("select count(1) from employees where username='"+username+"'");
                while(rs.next())
                if(rs.getInt("count(1)")==0)
                {
                    dc.excuteUpdate("insert into employees (username,password,type) values ('"+username+"','"+password+"','"+tof+"')");
                JOptionPane.showMessageDialog(null,"A New Employee has been Added Successfully");
                update_table(dft);
                
            }   
                else
                    JOptionPane.showMessageDialog(null,"Username ("+username+") already exists","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            catch (SQLException ex) {
            }
    }
    
    void update(DefaultTableModel dft,String username,String password,String tof,int s_row)
    {
        try {
            DefaultTableModel DTM=dft;
            int id=Integer.parseInt(DTM.getValueAt(s_row,0).toString());
            ResultSet rs = dc.executeQuery("select id from employees where username='"+username+"'");
                if(!rs.next() || rs.getInt("id")==id)
                {
                    dc.excuteUpdate("update employees set username='"+username+"',password='"+password+"',type='"+tof+"' where id='"+id+"'");
                    JOptionPane.showMessageDialog(null,"Employee has been updated");
                    update_table(dft);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Username ("+username+") already exists","ERROR",JOptionPane.ERROR_MESSAGE);
                }
        } catch (SQLException ex) {   System.out.println(ex.getMessage());     }
    }
    
    void delete(DefaultTableModel dft,int s_row)
    {
        int id=Integer.parseInt(dft.getValueAt(s_row,0).toString());
        int Dialog_Result=JOptionPane.showConfirmDialog(null,"Do you want to Delete This Record ?","warning",JOptionPane.YES_NO_OPTION);
        
        if(Dialog_Result==JOptionPane.YES_OPTION)
        {        
            dc.excuteUpdate("delete from employees where id='"+id+"'");
        JOptionPane.showMessageDialog(null,"Employee has been Deleted");
        update_table(dft);
        }
    }
    
    String []search()
    {
        int id=Integer.parseInt((String) JOptionPane.showInputDialog(null, "Enter ID to search for", "", JOptionPane.QUESTION_MESSAGE));
        String arr[]=new String[3];
        try {
            ResultSet rs=dc.executeQuery("select username,password,type from employees where id='"+id+"'");
            
            if(rs.next()==false)
            {
                JOptionPane.showMessageDialog(null,"ERROR!!  This user ID couldn't be found");
            }
            else
            {
                arr[0]=rs.getString("username");
                arr[1]=rs.getString("password");
                arr[2]=rs.getString("type");
            }

        }   catch (SQLException ex) {  System.out.println(ex.getMessage());      }
                return arr;
    }
    
    void update_Admin_Username()
    {
        String N_username=((String)(JOptionPane.showInputDialog(null, "Enter a New Admin Username","",JOptionPane.OK_CANCEL_OPTION)));
        try{
            if(N_username == null)//cancel option
            {}
            else if("".equals(N_username))
            {
                JOptionPane.showMessageDialog(null,"Invalid input Please Enter a Valid Username","Null Username",JOptionPane.ERROR_MESSAGE);
            }
            else{
            int r = dc.excuteUpdate("Update employees set username='"+N_username+"'where type='Admin'");
            if(r==1)
                JOptionPane.showMessageDialog(null,"Admin Username has been Update Successfully");
        }
        }catch(Exception e){
        JOptionPane.showMessageDialog(null,"Database Connection Error","Connection Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void update_Admin_Password()
    {
        String N_password=((String)(JOptionPane.showInputDialog(null, "Enter a New Admin Password","",JOptionPane.OK_CANCEL_OPTION)));       
        try{
            if(N_password == null)//cancel option
            {}
            else if("".equals(N_password))
            {
                JOptionPane.showMessageDialog(null,"Invalid input Please Enter a Valid Password","Null Password",JOptionPane.ERROR_MESSAGE);
            }
            else{//(N_password.length()>0)
            int r = dc.excuteUpdate("Update employees set password='"+N_password+"' where type='Admin'");
            if(r==1)
                JOptionPane.showMessageDialog(null,"Admin Password has been Update Successfully");
            }
          }catch(Exception e){
        JOptionPane.showMessageDialog(null,"Database Connection Error","Connection Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
