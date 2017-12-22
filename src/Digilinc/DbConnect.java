package Digilinc;
import java.sql.*;

public class DbConnect  
{
    Connection con;
    Statement st;
    ResultSet rs1,rs2,rs3,rs4;
    PreparedStatement pst;
    public DbConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/digilinc","root","");
            st =  con.createStatement();
            System.out.println("Connected");
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Error:"+ex);
        }        
    }
    public Connection connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/digilinc","root","");
            st =  con.createStatement();
            System.out.println("Connected");
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Error:"+ex);
        } 
        return null;    
    }
    
    public ResultSet getdate()
    {
        try 
        {
            connect();
            pst = con.prepareStatement("select adate from patient");
            rs1 = pst.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs1;
    }
    
    public ResultSet licencecheck(String yr){
        int y = Integer.parseInt(yr);
        try 
        {
            connect();
            pst = con.prepareStatement("select expire,count from licence where expyr = ?");
            pst.setInt(1, y);
            rs1 = pst.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs1;
    }
    
    public ResultSet getdoc()
    {
        try 
        {
            connect();
            pst = con.prepareStatement("select dname from doctor group by dname");
            rs1 = pst.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs1;
    }
    
    public ResultSet getbillname(String date)
    {
        try 
        {
            connect();
            pst = con.prepareStatement("select name from patient where regno IN (select id from bill where billdate = ?) group by name");
            pst.setString(1, date);
            rs3 = pst.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs3;
    }
    
    public ResultSet fillsearchtable(String regno)
    {
        try
        {
            connect();
            pst = con.prepareStatement("Select name,address,regno,gender,uid from patient where regno = ?");
            pst.setString(1,regno);
            rs1 =pst.executeQuery();
        }
        catch(SQLException e)
        {
             System.out.println(e);
        }
        return rs1;
    }
    
    public ResultSet getdept()
    {
        try 
        {
            connect();
            pst = con.prepareStatement("select dept from department");
            rs1 = pst.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs1;
    }
    
    public ResultSet searchpatient (String patient)
    {
        try
        {
            connect();
            pst = con.prepareStatement("select regno, name, address, agey, gender, adate, atime, bgroup from patient "
                    + "where name LIKE ? OR regno LIKE ? "); 
            pst.setString(1,"%"+patient+"%");
            pst.setString(2,"%"+patient+"%");
            rs4 = pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println("Error at search patient: "+e );
        }
        return rs4;
    }
    
    public ResultSet searchpatienthistory(String patient)
    {
        try{
            connect();
            pst = con.prepareStatement("select d.regid,p.name,d.ddate,d.disease,d.medicine,d.dose,d.days from patient p, diagnosis d where d.regid=p.regno AND p.regno like ? ");
            pst.setString(1,patient);
            rs1 = pst.executeQuery();
            
        }
        catch(SQLException e)
        {
            System.out.println("Error at search patient history: "+e );
        }
        return rs1;
    }
    
    //to count total no. of patients "today"
    public ResultSet today_patients()
    {
        try
        {
            connect();  //connecting with database
            pst= con.prepareStatement("select count(*) from patient where adate= curdate()");
            //using preparedStatement to write the query
            rs1=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e);
        }
        
        return rs1;
    }
    
    //to count total no. of appointments "today"
    public ResultSet today_appointment()
    {
        try{
            connect();
            pst=con.prepareStatement("SELECT count(*) FROM `appointment` WHERE appdate = curdate()");
            rs2=pst.executeQuery();
        }   
        catch(Exception e){
            System.out.println("Error: "+e);            
        }
        return rs2;
    }
    
    public ResultSet all_patients()
    {
        try
        {
            connect();  //connecting with database
            pst= con.prepareStatement("select count(*) from patient");
            //using preparedStatement to write the query
            rs1=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e);
        }
        
        return rs1;
    }
    
    public ResultSet all_appointment()
    {
        try
        {
            connect();  //connecting with database
            pst= con.prepareStatement("select count(*) from appointment");
            //using preparedStatement to write the query
            rs1=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e);
        }
        
        return rs1;
    }
    
    
    //7,30,365 days
    public ResultSet var_appointment(String tom)
    {
        try
        {
            connect();  //connecting with database
            pst= con.prepareStatement("select count(*) from appointment where appdate >= '" +tom+ "' AND appdate <= curdate()");
            //using preparedStatement to write the query
            rs1=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error at var_appointments: "+e);
        }
        
        return rs1;
    }
    
    public ResultSet var_patients(String tom)
    {
        try
        {
            connect();  //connecting with database
            pst= con.prepareStatement("select count(*) from patient where adate >= '" +tom+ "' AND adate <= curdate()");
            //using preparedStatement to write the query
            rs1=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error at var_patients: "+e);
        }
        
        return rs1;
    }
    
    
    
    
    
    
    
    //to count total no. of medicines 
    public ResultSet total_medicine()
    {
        try
        {
            connect();
            pst=con.prepareStatement("select count(*) from medicine");
            rs3=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e);
        }
        
        return rs3;
    }
    
    public ResultSet total_ailment()
    {
        try
        {
            connect();
            pst=con.prepareStatement("select count(*) from disease");
            rs4=pst.executeQuery();
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e);
        }
        
        return rs4;
    }
    
    public ResultSet searchonepatient (String patient)
    {
        try
        {
            connect();
            pst = con.prepareStatement("select ddate,disease,medicine from diagnosis where regid = "
                    + "(select regno from patient where regno LIKE ? OR name LIKE ? LIMIT 1)"); 
            pst.setString(1,"%"+patient+"%");
            pst.setString(2,"%"+patient+"%");
            rs4 = pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return rs4;
    }
    
    public ResultSet getLogindetails(String username,String password,String usertype)
    {
        try 
        {
            connect();
            pst = con.prepareStatement("SELECT username,usertype FROM login WHERE username=? AND password=? AND usertype = ?");
            pst.setString(1,username);
            pst.setString(2,password);
            pst.setString(3,usertype);
            rs1 = pst.executeQuery();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs1;
    }
    
    public void loadsession(String username)
    {
        try 
        {
            connect();
            pst = con.prepareStatement("insert into session values (?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, username);
            pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            int i = pst.executeUpdate();
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
    }
    //reg no. from patient
    public ResultSet getopdno()
    {
        try 
        {
            connect();
            pst = con.prepareStatement("select max(regno) from patient");            
            rs1 = pst.executeQuery();
            System.out.println(rs1);
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
        return rs1;
    }
    
    public ResultSet getbilldate()
    {
        try
        {
            connect();
            pst = con.prepareStatement("select billdate from bill group by billdate");
            rs4 = pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return rs4;
    }
    
    public ResultSet fillbillprint(String date,String name)
    {
        try
        {
            connect();
            pst = con.prepareStatement("select p.type, b.billdate, p.regno, b.billno, p.name, p.hod, b.roomchrg, "
                    + "b.labchrg, b.admnchrg, b.radiochrg, b.nursechrg, b.consultchrg, b.icuchrg, b.concession,b.paytype, "
                    + "b.payment, b.gross, b.stax, b.netamount from patient p,bill b where p.regno = b.id "
                    + "and p.name =? and b.billdate = ?");
            pst.setString(1,name);
            pst.setString(2, date);
            rs2= pst.executeQuery();
        }
        catch(SQLException e)
        {
            System.err.println("Error at fillbillprint: "+e);
        }
        return rs2;
    }
}