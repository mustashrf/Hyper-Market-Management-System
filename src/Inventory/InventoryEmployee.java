
package Inventory;
import Users.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Vector;

public class InventoryEmployee extends User{
    
    public InventoryEmployee() {
        //super("com.mysql.cj.jdbc.Driver",  "jdbc:mysql://localhost:3306/market",
               // "root", "root");
    }
    
    protected static ResultSet listAllProducts(){
        String sql = "select * from stock";
        ResultSet r = DB.executeQuery(sql);
        return r;
    }
    
    int addproduct(String name,String category,LocalDate productionDate,LocalDate expiryDate,
                   double price,int quantity,int damages,int shortage){
        
        String sql = "insert into stock (name,category,production_date,expiry_date,price,quantity,damages,shortage)"
                + "values('"+name+"','"+category+"','"+productionDate+"','"+expiryDate+"',"
                + "'"+price+"','"+quantity+"','"+damages+"','"+shortage+"')";
        int r = DB.excuteUpdate(sql);
        return r;
    }
    
    int deleteProduct(int id){
        String sql = "delete from stock where id='"+id+"'";
        int r = DB.excuteUpdate(sql);
        return r;
    }
    
    int updateProduct(int id,String name,String category,double price,int quantity,int damages,int shortage){
        String sql = "update stock set name='"+name+"',category='"+category+"',price='"+price+"',quantity='"+quantity+""
                + "',damages='"+damages+"',shortage='"+shortage+"'"
                + " where id ='"+id+"'";
        int r = DB.excuteUpdate(sql);
        return r;
    }
    
    protected ResultSet search(String keyWord){
        String sql = "select * from stock where name like '"+keyWord+"%'or category like'"+keyWord+"%'";
        ResultSet r = DB.executeQuery(sql);
        return r;
    }
    
    private static Vector getName() throws SQLException{
        Vector name = new Vector();
        String sql = "select * from stock";
        ResultSet r = DB.executeQuery(sql);
        while(r.next())
            name.add(r.getString("name"));
        return name;
    }
    
    private static Vector getShortage() throws SQLException{
        Vector shortage = new Vector();
        String sql = "select shortage from stock";
        ResultSet r = DB.executeQuery(sql);
        while(r.next())
            shortage.add(r.getInt("shortage"));
        return shortage;
    }
    
    private static Vector getQuantity() throws SQLException{
        Vector quantity = new Vector();
        String sql = "select quantity from stock";
        ResultSet r = DB.executeQuery(sql);
        while(r.next())
            quantity.add(r.getInt("quantity"));
        return quantity;
    }
    
    private static Vector getExpiry() throws SQLException{
        Vector expiry = new Vector();
        String sql = "select expiry_date from stock";
        ResultSet r = DB.executeQuery(sql);
        while(r.next())
            expiry.add(LocalDate.parse(r.getString("expiry_date")));
        return expiry;
    }
    
    protected Object[] getNotifications(char x) throws SQLException{
        Object[] arr = new Object[2];
        Vector temp = new Vector();
        int c = 0;
        if(x=='q')//loop for quantity
        {
            for(int i=0;i<getQuantity().size();i++){
                if ((int) getQuantity().get(i) <= (int) getShortage().get(i)) {
                    c++;
                    temp.add(getName().get(i));
                }
            }
            arr[0] = c;
            arr[1] = temp;
        }
        else //loop for expiry
        {
            for(int i=0;i<getExpiry().size();i++){
                if (LocalDate.now().compareTo((ChronoLocalDate) getExpiry().get(i)) == 0
                        || LocalDate.now().compareTo((ChronoLocalDate) getExpiry().get(i)) == 1) {
                    c++;
                    temp.add(getName().get(i));
                }
            }
            arr[0] = c;
            arr[1] = temp;
        }
        return arr;
    }
    
}
