/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Digilinc;

import Digilinc.HomeFrameGUI.Listener;
//import static Digilinc.Valadity.conn;
//import static Digilinc.Valadity.rs;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.frequency;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author Gurpreet
 */
public class HomeFrameGUI extends javax.swing.JFrame {

    DefaultTableModel model,model1,model2,model3;
    ResultSet rs ,rs1,rs2,rs3;
    PreparedStatement pst;
    Statement st;
    String suser,stype;
    DbConnect conn =null;
    int sessionid;
    String popdno , pdate, pname;
    AutoCompleteDecorator decorator;
    String name,address,uid,gender,regno,bgroup;
    int age;
    Connection con; Statement stmt=null;
    int max; Point p,p1; int x1,y1,x2,y2;
    
    public HomeFrameGUI(String username,String Usertype, String sid) { 
            
        initComponents();
        
        
        if(Usertype.equals("Receptionist"))
        {
           
            pnlmenu.remove(lbladmin);
            pnlmenu.remove(jLabel6);
        }
        
       btn_all.setBackground(new Color(33,150,243));
       btn_today.setBackground(new Color(62, 77, 159));
       btn_7days.setBackground(new Color(33,150,243));
       btn_1month.setBackground(new Color(33,150,243));
       btn_1year.setBackground(new Color(33,150,243));
        
        getContentPane().setBackground(Color.decode("#313d48"));
        suser = username;
        stype=Usertype;
        sessionid = Integer.parseInt(sid);
        tabledisease.setFillsViewportHeight(true);
        jScrollPane5.getViewport().setBackground(Color.decode("#B39DDB")); //background of viewport
        JTableHeader header2 = tabledisease.getTableHeader();
        header2.setBackground(new Color(0,191,165));  //background of table header
        header2.setForeground(Color.WHITE);  
        tabledisease.getTableHeader().setFont(new java.awt.Font("Dialog", Font.BOLD, 14));
       //today's appt list
        tbltom.setFillsViewportHeight(true);
        jScrollPane8.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header4 = tbltom.getTableHeader();
        header4.setBackground(new Color(0,191,165));
        header4.setForeground(Color.WHITE);
        tbltom.getTableHeader().setFont(new java.awt.Font("Dialog", Font.BOLD, 12));
        btndeleterow.setVisible(false);
        model1 = new DefaultTableModel();
        model1.addColumn("Disease");
        model1.addColumn("Medicine");
        model1.addColumn("Dose");
        model1.addColumn("Frequency");
        model1.addColumn("Instructions");
        model1.addColumn("Days"); 
        tabledisease.setModel(model1);
      //  resizeColumnWidth(tabledisease);
        model3 = new DefaultTableModel();
        model3.addColumn("Sr No");
        model3.addColumn("Date");        
        model3.addColumn("Disease");
        model3.addColumn("Medicine");
        model3.addColumn("Dose");
        tblhistory.setModel(model3);
        resizeColumnWidth(tblhistory);
               
        Timer t = new Timer(1000,new Listener());
        t.start();
        t.start();
        this.pack();
        
       // btnprint.addActionListener(new PrintGUIWindow(pnl_printbill));
        if(!username.equals(""))
        {
                suser = username;
        }
        try
        {
            conn = new DbConnect();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/digilinc","root","");
            st =  con.createStatement();
            System.out.println("Connected");
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Error:"+ex);
        }
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(jRadioButton1);
        bg1.add(jRadioButton2);
        jRadioButton1.addActionListener((ActionEvent e) -> {
            radiobtn1();
        });
        
        jRadioButton2.addActionListener((ActionEvent e) -> {
            radiobtn2();
        });
    
        setLblColor(lblhome,jLabel1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlmenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblhome = new javax.swing.JLabel();
        lblappt = new javax.swing.JLabel();
        lblpatient = new javax.swing.JLabel();
        lbladmin = new javax.swing.JLabel();
        lbllogout = new javax.swing.JLabel();
        lblhistory = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbldiagnosis = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        header = new javax.swing.JLabel();
        cancel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        pnlHome = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_all = new javax.swing.JButton();
        btn_1year = new javax.swing.JButton();
        btn_1month = new javax.swing.JButton();
        btn_7days = new javax.swing.JButton();
        btn_today = new javax.swing.JButton();
        pnl_no_appt = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbl_no_appt = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pnl_no_patient = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbl_no_patient = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pnl_no_aliment = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_no_ailment = new javax.swing.JLabel();
        pnl_no_med = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_no_med = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel44 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        pnlAppt = new javax.swing.JPanel();
        pnl_bookappt = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        btnschedule = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        cmbtimeslot = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        pnl_deleteappt = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        pnl_apptlist = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbltom = new javax.swing.JTable();
        pnlPatient = new javax.swing.JPanel();
        pnl_patientreg = new javax.swing.JPanel();
        lblopdreg1 = new javax.swing.JLabel();
        txtregno = new javax.swing.JTextField();
        lblageopd1 = new javax.swing.JLabel();
        txtage = new javax.swing.JTextField();
        jLabel220 = new javax.swing.JLabel();
        txttime = new javax.swing.JTextField();
        txtdate = new javax.swing.JTextField();
        lbldatetimeopd1 = new javax.swing.JLabel();
        lblnameopd1 = new javax.swing.JLabel();
        txtpname = new javax.swing.JTextField();
        lblgenderopd1 = new javax.swing.JLabel();
        cmbgender = new javax.swing.JComboBox();
        txtcontact = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        cmbbgroup = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaddress = new javax.swing.JTextArea();
        lbladdressopd1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btngetregno = new javax.swing.JButton();
        jSeparator18 = new javax.swing.JSeparator();
        txtnote1 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnsave = new javax.swing.JButton();
        btnpreset = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        pnlDiagnosis = new javax.swing.JPanel();
        pnl_diagnosis1 = new javax.swing.JPanel();
        cmbdisease = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cmbmedicine = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        cmbdose = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        cmbfrequency = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        cmbinstruction = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        cmbdays = new javax.swing.JComboBox<>();
        btnadddisease1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtdate1 = new javax.swing.JTextField();
        cmb_opdno = new javax.swing.JComboBox<>();
        pnl_diagnosistable = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabledisease = new javax.swing.JTable();
        btndeleterow = new javax.swing.JButton();
        btn_save_diag = new javax.swing.JButton();
        pnlHistory = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblname = new javax.swing.JTextField();
        lbltype = new javax.swing.JTextField();
        lblgender = new javax.swing.JTextField();
        lblage = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblhistory = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlmenu.setBackground(new java.awt.Color(48, 48, 48));

        jLabel1.setBackground(new java.awt.Color(48, 48, 48));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Home_32px_1.png"))); // NOI18N
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(48, 48, 48));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Edit Property_32px.png"))); // NOI18N
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(48, 48, 48));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Add User Male_32px.png"))); // NOI18N
        jLabel3.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(48, 48, 48));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Accessibility 2_32px_2.png"))); // NOI18N
        jLabel6.setToolTipText("");
        jLabel6.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(48, 48, 48));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Logout Rounded Up_32px.png"))); // NOI18N
        jLabel7.setOpaque(true);

        lblhome.setBackground(new java.awt.Color(48, 48, 48));
        lblhome.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblhome.setForeground(new java.awt.Color(255, 255, 255));
        lblhome.setText("Home");
        lblhome.setOpaque(true);
        lblhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhomeMouseClicked(evt);
            }
        });

        lblappt.setBackground(new java.awt.Color(48, 48, 48));
        lblappt.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblappt.setForeground(new java.awt.Color(255, 255, 255));
        lblappt.setText("Appointment");
        lblappt.setOpaque(true);
        lblappt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblapptMouseClicked(evt);
            }
        });

        lblpatient.setBackground(new java.awt.Color(48, 48, 48));
        lblpatient.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblpatient.setForeground(new java.awt.Color(255, 255, 255));
        lblpatient.setText("Patient");
        lblpatient.setOpaque(true);
        lblpatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblpatientMouseClicked(evt);
            }
        });

        lbladmin.setBackground(new java.awt.Color(48, 48, 48));
        lbladmin.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lbladmin.setForeground(new java.awt.Color(255, 255, 255));
        lbladmin.setText("Admin");
        lbladmin.setOpaque(true);
        lbladmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbladminMouseClicked(evt);
            }
        });

        lbllogout.setBackground(new java.awt.Color(48, 48, 48));
        lbllogout.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lbllogout.setForeground(new java.awt.Color(255, 255, 255));
        lbllogout.setText("Logout");
        lbllogout.setOpaque(true);
        lbllogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbllogoutMouseClicked(evt);
            }
        });

        lblhistory.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblhistory.setForeground(new java.awt.Color(255, 255, 255));
        lblhistory.setText("History");
        lblhistory.setToolTipText("");
        lblhistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhistoryMouseClicked(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Accessibility 2_32px_2.png"))); // NOI18N

        lbldiagnosis.setBackground(new java.awt.Color(48, 48, 48));
        lbldiagnosis.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lbldiagnosis.setForeground(new java.awt.Color(255, 255, 255));
        lbldiagnosis.setText("Diagnosis");
        lbldiagnosis.setOpaque(true);
        lbldiagnosis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbldiagnosisMouseClicked(evt);
            }
        });

        jLabel25.setBackground(new java.awt.Color(48, 48, 48));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Add User Male_32px.png"))); // NOI18N
        jLabel25.setOpaque(true);

        javax.swing.GroupLayout pnlmenuLayout = new javax.swing.GroupLayout(pnlmenu);
        pnlmenu.setLayout(pnlmenuLayout);
        pnlmenuLayout.setHorizontalGroup(
            pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlmenuLayout.createSequentialGroup()
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlmenuLayout.createSequentialGroup()
                        .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbladmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblappt, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(lblhome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlmenuLayout.createSequentialGroup()
                        .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblpatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbldiagnosis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlmenuLayout.createSequentialGroup()
                                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblhistory, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbllogout, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)))))
                .addContainerGap())
        );
        pnlmenuLayout.setVerticalGroup(
            pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlmenuLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbladmin, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblhome, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblappt, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblpatient, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbldiagnosis, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblhistory, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbllogout, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        header.setBackground(new java.awt.Color(0, 191, 165));
        header.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        header.setForeground(new java.awt.Color(255, 255, 255));
        header.setText("     DigiLinc");
        header.setOpaque(true);

        cancel.setBackground(new java.awt.Color(0, 191, 165));
        cancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Cancel_32px_1.png"))); // NOI18N
        cancel.setOpaque(true);
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cancelMousePressed(evt);
            }
        });

        mainPanel.setLayout(new java.awt.CardLayout());

        pnlHome.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btn_all.setBackground(new java.awt.Color(83, 109, 254));
        btn_all.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_all.setForeground(new java.awt.Color(255, 255, 255));
        btn_all.setText("All");
        btn_all.setFocusPainted(false);
        btn_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_allActionPerformed(evt);
            }
        });

        btn_1year.setBackground(new java.awt.Color(83, 109, 254));
        btn_1year.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_1year.setForeground(new java.awt.Color(255, 255, 255));
        btn_1year.setText("Last 1 Year");
        btn_1year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_1yearActionPerformed(evt);
            }
        });

        btn_1month.setBackground(new java.awt.Color(83, 109, 254));
        btn_1month.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_1month.setForeground(new java.awt.Color(255, 255, 255));
        btn_1month.setText("Last 1 Month");
        btn_1month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_1monthActionPerformed(evt);
            }
        });

        btn_7days.setBackground(new java.awt.Color(83, 109, 254));
        btn_7days.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_7days.setForeground(new java.awt.Color(255, 255, 255));
        btn_7days.setText("Last 7 Days");
        btn_7days.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_7daysActionPerformed(evt);
            }
        });

        btn_today.setBackground(new java.awt.Color(83, 109, 254));
        btn_today.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btn_today.setForeground(new java.awt.Color(255, 255, 255));
        btn_today.setText("Today");
        btn_today.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_todayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(506, Short.MAX_VALUE)
                .addComponent(btn_today, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btn_7days)
                .addGap(0, 0, 0)
                .addComponent(btn_1month)
                .addGap(0, 0, 0)
                .addComponent(btn_1year)
                .addGap(0, 0, 0)
                .addComponent(btn_all, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_all, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(btn_1year, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                    .addComponent(btn_1month, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_today, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_7days, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pnl_no_appt.setBackground(new java.awt.Color(244, 67, 54));
        pnl_no_appt.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(255, 82, 82)));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Calendar Plus_100px.png"))); // NOI18N

        lbl_no_appt.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lbl_no_appt.setForeground(new java.awt.Color(255, 255, 255));
        lbl_no_appt.setText("      ");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Appointment");

        javax.swing.GroupLayout pnl_no_apptLayout = new javax.swing.GroupLayout(pnl_no_appt);
        pnl_no_appt.setLayout(pnl_no_apptLayout);
        pnl_no_apptLayout.setHorizontalGroup(
            pnl_no_apptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_no_apptLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_no_apptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_apptLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18))
                    .addGroup(pnl_no_apptLayout.createSequentialGroup()
                        .addComponent(lbl_no_appt)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnl_no_apptLayout.setVerticalGroup(
            pnl_no_apptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_apptLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lbl_no_appt)
                .addGap(18, 18, 18)
                .addGroup(pnl_no_apptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_apptLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_apptLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );

        pnl_no_patient.setBackground(new java.awt.Color(0, 191, 165));
        pnl_no_patient.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(38, 166, 154)));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Add User Male_100px.png"))); // NOI18N

        lbl_no_patient.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lbl_no_patient.setForeground(new java.awt.Color(255, 255, 255));
        lbl_no_patient.setText("      ");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Patient");

        javax.swing.GroupLayout pnl_no_patientLayout = new javax.swing.GroupLayout(pnl_no_patient);
        pnl_no_patient.setLayout(pnl_no_patientLayout);
        pnl_no_patientLayout.setHorizontalGroup(
            pnl_no_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_no_patientLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_no_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_patientLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(17, 17, 17))
                    .addGroup(pnl_no_patientLayout.createSequentialGroup()
                        .addComponent(lbl_no_patient, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnl_no_patientLayout.setVerticalGroup(
            pnl_no_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_patientLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lbl_no_patient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_no_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_patientLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_patientLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );

        pnl_no_aliment.setBackground(new java.awt.Color(33, 150, 243));
        pnl_no_aliment.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(83, 109, 254)));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Syringe_100px.png"))); // NOI18N
        jLabel14.setText("\n");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Ailment");

        lbl_no_ailment.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lbl_no_ailment.setForeground(new java.awt.Color(255, 255, 255));
        lbl_no_ailment.setText("   ");

        javax.swing.GroupLayout pnl_no_alimentLayout = new javax.swing.GroupLayout(pnl_no_aliment);
        pnl_no_aliment.setLayout(pnl_no_alimentLayout);
        pnl_no_alimentLayout.setHorizontalGroup(
            pnl_no_alimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_no_alimentLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_no_alimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_alimentLayout.createSequentialGroup()
                        .addComponent(lbl_no_ailment)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_no_alimentLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );
        pnl_no_alimentLayout.setVerticalGroup(
            pnl_no_alimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_no_alimentLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lbl_no_ailment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_no_alimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_alimentLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_alimentLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap())))
        );

        pnl_no_med.setBackground(new java.awt.Color(255, 152, 0));
        pnl_no_med.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(245, 127, 23)));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/Pill_100px.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Medicine");

        lbl_no_med.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lbl_no_med.setForeground(new java.awt.Color(255, 255, 255));
        lbl_no_med.setText("     ");

        javax.swing.GroupLayout pnl_no_medLayout = new javax.swing.GroupLayout(pnl_no_med);
        pnl_no_med.setLayout(pnl_no_medLayout);
        pnl_no_medLayout.setHorizontalGroup(
            pnl_no_medLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_no_medLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_no_medLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_medLayout.createSequentialGroup()
                        .addComponent(lbl_no_med, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_no_medLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        pnl_no_medLayout.setVerticalGroup(
            pnl_no_medLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_medLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lbl_no_med, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_no_medLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_no_medLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_no_medLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel44.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(48, 48, 48));
        jLabel44.setText("TOTAL COUNT");

        jSeparator12.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_no_appt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl_no_aliment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(114, 114, 114)
                        .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnl_no_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnl_no_med, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_no_appt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_no_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_no_aliment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_no_med, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        mainPanel.add(pnlHome, "pnlHome");

        pnlAppt.setBackground(new java.awt.Color(236, 239, 241));

        pnl_bookappt.setBackground(new java.awt.Color(236, 239, 241));
        pnl_bookappt.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 191, 165), 4, true), "Book Appointment", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 24))); // NOI18N
        pnl_bookappt.setForeground(new java.awt.Color(255, 255, 255));

        jLabel17.setBackground(new java.awt.Color(49, 61, 72));
        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel17.setText("Date:");

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel18.setText("Time Slot:");

        jRadioButton1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jRadioButton1.setText("Morning");
        jRadioButton1.setOpaque(false);

        jRadioButton2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jRadioButton2.setText("Evening");
        jRadioButton2.setOpaque(false);

        btnschedule.setBackground(new java.awt.Color(0, 191, 165));
        btnschedule.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnschedule.setForeground(new java.awt.Color(255, 255, 255));
        btnschedule.setText("Schedule");
        btnschedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnscheduleActionPerformed(evt);
            }
        });

        btnreset.setBackground(new java.awt.Color(0, 191, 165));
        btnreset.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnreset.setForeground(new java.awt.Color(255, 255, 255));
        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        cmbtimeslot.setBackground(new java.awt.Color(236, 239, 241));
        cmbtimeslot.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel20.setText("Name:");

        txtname.setBackground(new java.awt.Color(236, 239, 241));
        txtname.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtname.setBorder(null);
        txtname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnameKeyTyped(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(0, 191, 165));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setDateFormatString("yyyy-MM-d");
        jDateChooser1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jSeparator15.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel29.setText("ID:");

        jSeparator16.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator16.setForeground(new java.awt.Color(255, 255, 255));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(236, 239, 241));
        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextField2.setBorder(null);
        jTextField2.setCaretColor(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnl_bookapptLayout = new javax.swing.GroupLayout(pnl_bookappt);
        pnl_bookappt.setLayout(pnl_bookapptLayout);
        pnl_bookapptLayout.setHorizontalGroup(
            pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_bookapptLayout.createSequentialGroup()
                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_bookapptLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator15))
                                .addGap(178, 178, 178))
                            .addGroup(pnl_bookapptLayout.createSequentialGroup()
                                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel29))
                                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jRadioButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbtimeslot, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                                        .addGap(132, 132, 132)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(btnschedule)
                        .addGap(54, 54, 54)
                        .addComponent(btnreset)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_bookapptLayout.setVerticalGroup(
            pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_bookapptLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                        .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(jTextField2))
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(pnl_bookapptLayout.createSequentialGroup()
                        .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbtimeslot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(pnl_bookapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnschedule)
                    .addComponent(btnreset))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pnl_deleteappt.setBackground(new java.awt.Color(236, 239, 241));
        pnl_deleteappt.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 191, 165), 4, true), "Delete Appointment", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 24))); // NOI18N

        jLabel21.setBackground(new java.awt.Color(49, 61, 72));
        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel21.setText("Appointment No.:");

        jComboBox1.setBackground(new java.awt.Color(236, 239, 241));
        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 191, 165));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_deleteapptLayout = new javax.swing.GroupLayout(pnl_deleteappt);
        pnl_deleteappt.setLayout(pnl_deleteapptLayout);
        pnl_deleteapptLayout.setHorizontalGroup(
            pnl_deleteapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deleteapptLayout.createSequentialGroup()
                .addGroup(pnl_deleteapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_deleteapptLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_deleteapptLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_deleteapptLayout.setVerticalGroup(
            pnl_deleteapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deleteapptLayout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(pnl_deleteapptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addGap(20, 20, 20))
        );

        pnl_apptlist.setBackground(new java.awt.Color(236, 239, 241));
        pnl_apptlist.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 191, 165), 4, true), "Today's List", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 24))); // NOI18N

        tbltom.setBackground(new java.awt.Color(236, 239, 241));
        tbltom.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        tbltom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbltom.setGridColor(new java.awt.Color(49, 61, 72));
        tbltom.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tbltom);

        javax.swing.GroupLayout pnl_apptlistLayout = new javax.swing.GroupLayout(pnl_apptlist);
        pnl_apptlist.setLayout(pnl_apptlistLayout);
        pnl_apptlistLayout.setHorizontalGroup(
            pnl_apptlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_apptlistLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_apptlistLayout.setVerticalGroup(
            pnl_apptlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_apptlistLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlApptLayout = new javax.swing.GroupLayout(pnlAppt);
        pnlAppt.setLayout(pnlApptLayout);
        pnlApptLayout.setHorizontalGroup(
            pnlApptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlApptLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlApptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_bookappt, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_deleteappt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnl_apptlist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        pnlApptLayout.setVerticalGroup(
            pnlApptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApptLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlApptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_apptlist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlApptLayout.createSequentialGroup()
                        .addComponent(pnl_bookappt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnl_deleteappt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        mainPanel.add(pnlAppt, "pnlAppt");

        pnlPatient.setBackground(new java.awt.Color(236, 239, 241));

        pnl_patientreg.setBackground(new java.awt.Color(236, 239, 241));
        pnl_patientreg.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 191, 165), 4, true), "Patient Registration", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 24))); // NOI18N
        pnl_patientreg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblopdreg1.setBackground(new java.awt.Color(49, 61, 72));
        lblopdreg1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblopdreg1.setText("Reg. No.:*");
        lblopdreg1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtregno.setBackground(new java.awt.Color(149, 117, 205));
        txtregno.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtregno.setBorder(null);
        txtregno.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtregno.setOpaque(false);
        txtregno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtregnoKeyTyped(evt);
            }
        });

        lblageopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblageopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblageopd1.setText("Age:*");
        lblageopd1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtage.setBackground(new java.awt.Color(149, 117, 205));
        txtage.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtage.setText("0");
        txtage.setBorder(null);
        txtage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtage.setMaximumSize(null);
        txtage.setOpaque(false);
        txtage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtageKeyTyped(evt);
            }
        });

        jLabel220.setBackground(new java.awt.Color(49, 61, 72));
        jLabel220.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel220.setText("Years");
        jLabel220.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txttime.setEditable(false);
        txttime.setBackground(new java.awt.Color(149, 117, 205));
        txttime.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txttime.setBorder(null);
        txttime.setOpaque(false);

        txtdate.setEditable(false);
        txtdate.setBackground(new java.awt.Color(149, 117, 205));
        txtdate.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtdate.setBorder(null);
        txtdate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtdate.setOpaque(false);

        lbldatetimeopd1.setBackground(new java.awt.Color(49, 61, 72));
        lbldatetimeopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lbldatetimeopd1.setText("Date/Time :*");
        lbldatetimeopd1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblnameopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblnameopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblnameopd1.setText("Name:*");
        lblnameopd1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtpname.setBackground(new java.awt.Color(149, 117, 205));
        txtpname.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtpname.setBorder(null);
        txtpname.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtpname.setMaximumSize(null);
        txtpname.setOpaque(false);

        lblgenderopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblgenderopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblgenderopd1.setText("Gender:*");
        lblgenderopd1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cmbgender.setBackground(new java.awt.Color(236, 239, 241));
        cmbgender.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbgender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Other" }));
        cmbgender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbgender.setMaximumSize(null);
        cmbgender.setOpaque(false);

        txtcontact.setBackground(new java.awt.Color(149, 117, 205));
        txtcontact.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtcontact.setBorder(null);
        txtcontact.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtcontact.setOpaque(false);
        txtcontact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcontactKeyTyped(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(49, 61, 72));
        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel27.setText("Mobile No:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel28.setBackground(new java.awt.Color(49, 61, 72));
        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel28.setText("Blood Group:");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cmbbgroup.setBackground(new java.awt.Color(236, 239, 241));
        cmbbgroup.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbbgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "O +ve", "O -ve", "A +ve", "A -ve", "B +ve", "B -ve", "AB +ve", "AB -ve" }));
        cmbbgroup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbbgroup.setOpaque(false);

        txtaddress.setBackground(new java.awt.Color(236, 239, 241));
        txtaddress.setColumns(20);
        txtaddress.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtaddress.setLineWrap(true);
        txtaddress.setRows(2);
        txtaddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 191, 165)));
        txtaddress.setCaretColor(new java.awt.Color(255, 255, 255));
        txtaddress.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtaddress.setMaximumSize(null);
        jScrollPane1.setViewportView(txtaddress);

        lbladdressopd1.setBackground(new java.awt.Color(49, 61, 72));
        lbladdressopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lbladdressopd1.setText("Address:");
        lbladdressopd1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setOpaque(true);
        jSeparator7.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(255, 255, 255));

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setOpaque(false);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        btngetregno.setBackground(new java.awt.Color(0, 191, 165));
        btngetregno.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btngetregno.setForeground(new java.awt.Color(255, 255, 255));
        btngetregno.setText("Get Reg. No.");
        btngetregno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngetregnoActionPerformed(evt);
            }
        });

        jSeparator18.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator18.setForeground(new java.awt.Color(255, 255, 255));

        txtnote1.setBackground(new java.awt.Color(49, 61, 72));
        txtnote1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtnote1.setForeground(new java.awt.Color(255, 255, 255));
        txtnote1.setOpaque(false);

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnl_patientregLayout = new javax.swing.GroupLayout(pnl_patientreg);
        pnl_patientreg.setLayout(pnl_patientregLayout);
        pnl_patientregLayout.setHorizontalGroup(
            pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_patientregLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                        .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_patientregLayout.createSequentialGroup()
                                .addComponent(lblnameopd1)
                                .addGap(30, 30, 30)
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtpname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnl_patientregLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblopdreg1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                                        .addComponent(txtregno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28))
                                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)))
                                .addComponent(btngetregno, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbldatetimeopd1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator18))))
                        .addGap(253, 253, 253)
                        .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_patientregLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtnote1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                        .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_patientregLayout.createSequentialGroup()
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                                        .addComponent(lblgenderopd1)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(45, 45, 45)
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(lblageopd1))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtcontact, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(jSeparator11))
                                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                                        .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtage, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                            .addComponent(jSeparator2))
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel220))))
                            .addGroup(pnl_patientregLayout.createSequentialGroup()
                                .addComponent(lbladdressopd1)
                                .addGap(45, 45, 45)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_patientregLayout.setVerticalGroup(
            pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_patientregLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(pnl_patientregLayout.createSequentialGroup()
                        .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_patientregLayout.createSequentialGroup()
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btngetregno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbldatetimeopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_patientregLayout.createSequentialGroup()
                                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblopdreg1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtregno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblnameopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblgenderopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblageopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel220, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcontact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(pnl_patientregLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbladdressopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(txtnote1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );

        btnsave.setBackground(new java.awt.Color(0, 191, 165));
        btnsave.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Save Details");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnpreset.setBackground(new java.awt.Color(0, 191, 165));
        btnpreset.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnpreset.setForeground(new java.awt.Color(255, 255, 255));
        btnpreset.setText("Reset");
        btnpreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpresetActionPerformed(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Digilinc/images/patientmale.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 32)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 191, 165));
        jLabel19.setText("Register New Patient");

        javax.swing.GroupLayout pnlPatientLayout = new javax.swing.GroupLayout(pnlPatient);
        pnlPatient.setLayout(pnlPatientLayout);
        pnlPatientLayout.setHorizontalGroup(
            pnlPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientLayout.createSequentialGroup()
                .addGroup(pnlPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(pnlPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(61, 61, 61)
                        .addComponent(pnl_patientreg, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPatientLayout.createSequentialGroup()
                        .addGap(580, 580, 580)
                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnpreset, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPatientLayout.setVerticalGroup(
            pnlPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientLayout.createSequentialGroup()
                .addGroup(pnlPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnl_patientreg, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPatientLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel16)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnsave, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(btnpreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        mainPanel.add(pnlPatient, "pnlPatient");

        pnl_diagnosis1.setBackground(new java.awt.Color(236, 239, 241));
        pnl_diagnosis1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 191, 165), 4, true), "Diagnosis", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 24))); // NOI18N

        cmbdisease.setBackground(new java.awt.Color(236, 239, 241));
        cmbdisease.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbdisease.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbdisease.setOpaque(false);
        cmbdisease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbdiseaseActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel40.setText("Ailments");

        jLabel41.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel41.setText("Medicine: ");

        cmbmedicine.setBackground(new java.awt.Color(236, 239, 241));
        cmbmedicine.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbmedicine.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbmedicine.setOpaque(false);

        jLabel42.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel42.setText("Dose:");

        cmbdose.setBackground(new java.awt.Color(236, 239, 241));
        cmbdose.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbdose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 TAB", "2 TAB", "3 TAB", "25ML", "50ML", "75ML", "100ML" }));
        cmbdose.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbdose.setOpaque(false);

        jLabel45.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel45.setText("Frequency:");

        cmbfrequency.setBackground(new java.awt.Color(236, 239, 241));
        cmbfrequency.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbfrequency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ONCE A DAY", "TWICE A DAY", "THRICE A DAY" }));
        cmbfrequency.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbfrequency.setOpaque(false);

        jLabel46.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel46.setText("Instructions:");

        cmbinstruction.setBackground(new java.awt.Color(236, 239, 241));
        cmbinstruction.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cmbinstruction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BEFORE BREAKFAST", "AFTER BREAKFAST", "BEFORE LUNCH", "AFTER LUNCH", "BEFORE DINNER", "AFTER DINNER" }));
        cmbinstruction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbinstruction.setOpaque(false);

        jLabel47.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel47.setText("Days:");

        cmbdays.setBackground(new java.awt.Color(236, 239, 241));
        cmbdays.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cmbdays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 DAY", "2 DAYS", "3 DAYS ", "4 DAYS", "5 DAYS", "7 DAYS", "14 DAYS", "21 DAYS", "1 MONTH", "2 MONTHS" }));
        cmbdays.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 20, 140)));
        cmbdays.setOpaque(false);

        btnadddisease1.setBackground(new java.awt.Color(0, 191, 165));
        btnadddisease1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnadddisease1.setForeground(new java.awt.Color(255, 255, 255));
        btnadddisease1.setText("Add Diagnosis");
        btnadddisease1.setToolTipText("");
        btnadddisease1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadddiseaseActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel5.setText("Enter Registration No:");

        txtdate1.setEditable(false);
        txtdate1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtdate1.setBorder(null);

        javax.swing.GroupLayout pnl_diagnosis1Layout = new javax.swing.GroupLayout(pnl_diagnosis1);
        pnl_diagnosis1.setLayout(pnl_diagnosis1Layout);
        pnl_diagnosis1Layout.setHorizontalGroup(
            pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_diagnosis1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_diagnosis1Layout.createSequentialGroup()
                        .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_diagnosis1Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(18, 18, 18)
                                .addComponent(cmbdisease, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_diagnosis1Layout.createSequentialGroup()
                                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel42))
                                .addGap(43, 43, 43)
                                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbdose, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbdays, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel41)
                            .addComponent(jLabel46))
                        .addGap(10, 10, 10)
                        .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbfrequency, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbmedicine, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbinstruction, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_diagnosis1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(19, 19, 19)
                        .addComponent(cmb_opdno, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(txtdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
            .addGroup(pnl_diagnosis1Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(btnadddisease1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_diagnosis1Layout.setVerticalGroup(
            pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_diagnosis1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtdate1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(cmb_opdno, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbdisease, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(cmbmedicine, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbdose, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jLabel45)
                    .addComponent(cmbfrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnl_diagnosis1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cmbdays, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(cmbinstruction, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnadddisease1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pnl_diagnosistable.setBackground(new java.awt.Color(236, 239, 241));
        pnl_diagnosistable.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 191, 165), 4, true), "Diagnosis Table", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 24))); // NOI18N
        pnl_diagnosistable.setDoubleBuffered(false);

        tabledisease.setBackground(new java.awt.Color(236, 239, 241));
        tabledisease.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        tabledisease.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabledisease.setGridColor(new java.awt.Color(74, 20, 140));
        tabledisease.getTableHeader().setReorderingAllowed(false);
        tabledisease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablediseaseMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabledisease);

        btndeleterow.setBackground(new java.awt.Color(0, 191, 165));
        btndeleterow.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btndeleterow.setForeground(new java.awt.Color(255, 255, 255));
        btndeleterow.setText("Delete Row");
        btndeleterow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleterowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_diagnosistableLayout = new javax.swing.GroupLayout(pnl_diagnosistable);
        pnl_diagnosistable.setLayout(pnl_diagnosistableLayout);
        pnl_diagnosistableLayout.setHorizontalGroup(
            pnl_diagnosistableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_diagnosistableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnl_diagnosistableLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(btndeleterow, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        pnl_diagnosistableLayout.setVerticalGroup(
            pnl_diagnosistableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_diagnosistableLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btndeleterow, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_save_diag.setBackground(new java.awt.Color(0, 191, 165));
        btn_save_diag.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btn_save_diag.setForeground(new java.awt.Color(255, 255, 255));
        btn_save_diag.setText("Save Details");
        btn_save_diag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_diagActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDiagnosisLayout = new javax.swing.GroupLayout(pnlDiagnosis);
        pnlDiagnosis.setLayout(pnlDiagnosisLayout);
        pnlDiagnosisLayout.setHorizontalGroup(
            pnlDiagnosisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDiagnosisLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlDiagnosisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_diagnosis1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDiagnosisLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(btn_save_diag, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(pnl_diagnosistable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        pnlDiagnosisLayout.setVerticalGroup(
            pnlDiagnosisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDiagnosisLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(pnlDiagnosisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_diagnosistable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDiagnosisLayout.createSequentialGroup()
                        .addComponent(pnl_diagnosis1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_save_diag, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        mainPanel.add(pnlDiagnosis, "pnlDiagnosis");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        jLabel31.setText("Type:");

        jLabel32.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        jLabel32.setText("Name:");

        jLabel33.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        jLabel33.setText("Gender:");

        jLabel35.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        jLabel35.setText("Age:");

        lblname.setEditable(false);
        lblname.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        lblname.setOpaque(false);

        lbltype.setEditable(false);
        lbltype.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        lbltype.setOpaque(false);

        lblgender.setEditable(false);
        lblgender.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        lblgender.setOpaque(false);

        lblage.setEditable(false);
        lblage.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        lblage.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblage, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblgender, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltype, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltype, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblgender, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 43, Short.MAX_VALUE))
        );

        tblhistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblhistory);

        javax.swing.GroupLayout pnlHistoryLayout = new javax.swing.GroupLayout(pnlHistory);
        pnlHistory.setLayout(pnlHistoryLayout);
        pnlHistoryLayout.setHorizontalGroup(
            pnlHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        pnlHistoryLayout.setVerticalGroup(
            pnlHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryLayout.createSequentialGroup()
                .addGroup(pnlHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoryLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHistoryLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        mainPanel.add(pnlHistory, "pnlHistory");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logo)
                .addGap(0, 0, 0)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 1156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlmenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
    
    public void radiobtn1()
    {
        ArrayList<String> list2 = new ArrayList<>();
        String m = "Morning";
        try
        {
            pst = con.prepareStatement("select slots from times where batch = ? and slots NOT IN(select time_slot from appointment where appdate = CURDATE())");
            pst.setString(1, m);
            rs = pst.executeQuery();
            while(rs.next())
            {
                list2.add(rs.getString("slots"));
            }
            cmbtimeslot.setModel(new DefaultComboBoxModel(list2.toArray()));
        }
        catch(SQLException ex)
        {
            System.out.println("Error at jButton1ActionListener :"+ex);
        } 
    }
    public void radiobtn2()
    {
        ArrayList<String> list2 = new ArrayList<>();
        String fe= "Evening";
        try
            {
                pst = con.prepareStatement("select slots from times where batch = ? and slots NOT IN(select time_slot from appointment where appdate = CURDATE())");
                pst.setString(1, fe);
                rs = pst.executeQuery();
                while(rs.next())
                {
                    list2.add(rs.getString("slots"));
                }
                cmbtimeslot.setModel(new DefaultComboBoxModel(list2.toArray()));
            }
            catch(SQLException ex)
            {
                System.out.println("Error at jButton2ActionListener :"+ex);
            }
    }
    
    public void loaddate()
    {
        long t;
        t=System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String d=sdf.format(t);
        txtdate.setText(d);
        txtdate1.setText(d);
    }
    
    public void PrintFrameToPDF(JPanel panel,String no,String name)  
    {
    try {
        Document d = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream ("D:\\VPS\\"+name+"_"+no+".pdf"));
        d.open ();
        PdfContentByte cb = writer.getDirectContent();
        Graphics2D g2d = cb.createGraphics(600, 700);
        panel.print(g2d);
        panel.addNotify();
        panel.validate();
        g2d.dispose();
        d.close();
    }
    catch(DocumentException | FileNotFoundException e)  {
        System.out.println(e);
    }
} 
//    private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  
//        fetch();
//       // getsearchfocus();
//        loaddate();
//        loadbilldate();
//        fill_count();
//    }                                 
    public void getsearchfocus()
    { //search patient
       // txtsearch.requestFocus();
    }
    public void fetch()
    {
        String a="New",b="Follow-up";
        try 
        {
            pst = con.prepareStatement("select count(*) from patient where adate = CURDATE() and type = ?");
            pst.setString(1,a);
            rs = pst.executeQuery();
            while(rs.next())
            { //home page details
             // txtnewpatient.setText(Integer.toString(rs.getInt("count(*)")));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HomeFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            pst = con.prepareStatement("select count(*) from patient where adate = CURDATE() and type = ?");
            pst.setString(1,b);
            rs = pst.executeQuery();
            while(rs.next())
            {//home page details
                ////////txtfollowup.setText(Integer.toString(rs.getInt("count(*)")));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HomeFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst = con.prepareStatement("select count(*) from appointment where appdate = CURDATE()");
          //  pst.setInt(1);
            rs = pst.executeQuery();
            while(rs.next())
            { //home page details
                /////////txtappointment.setText(Integer.toString(rs.getInt("count(*)")));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HomeFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*************
      private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {                                          
        parsingarea.requestFocus();
        clearsearch();
        fetch();
        loadbilldate();
        loaddeldis();
        fillallpatient();
        fillappointment();
    }         
     */
    
    
    //extracting new appointment from database and displaying it on tbltom
    public void fillappointment()
    {
        try
        {
            pst = con.prepareStatement("select aid as Id, appdate as Date, name as Name,time_m_e as Time_Slot, "
                    + "time_slot as Time from appointment where appdate = CURDATE()");
            rs = pst.executeQuery();
            tbltom.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(tbltom);
        }
        catch(Exception e)
        {
            System.out.println("Error at fillallpatient: "+e);
        }
    }
    
     public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 15; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +5 , width);
        }
        if(width > 300)
            width=200;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}
    
    public void loaddeldis()
    {
        ArrayList<String> list2 = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select name from disease");
            rs = pst.executeQuery();
            while(rs.next())
            {
                list2.add(rs.getString("name"));
            }
        }
        catch(SQLException e)
        {
             System.out.println("Can't fetch disease name: "+e);
        }
        cmbdisease.setModel(new DefaultComboBoxModel(list2.toArray()));
    }
    
    public void loadmedicine()
    {
        try
        {
            ArrayList<String> list = new ArrayList<>();
            pst = con.prepareStatement("select name from medicine");
            rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(rs.getString("name"));
            }       
            cmbmedicine.setModel(new DefaultComboBoxModel(list.toArray()));
        }
        catch(SQLException e)
        {
            System.out.println("Error at loadmedicine: "+e);
        }
    }
    
    /*public void fillallpatient()
    {
        try
        {
            pst = con.prepareStatement("select regno as Registration_Number, name as Name, address as Address, agey as Age, gender as Gender, adate as Date, bgroup as Blood_Group, occupation as Occupation from patient");
            rs = pst.executeQuery();
            tblallpatients.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            System.out.println("Error at fillallpatient: "+e);
        }
    }*/
    
    public void getregno()
    {
        String opdno = null;
        try 
        {
            rs = conn.getopdno();
            System.out.println(rs.next());
            if(rs.next())
            {
                opdno = rs.getString("max(regno)");
                int ip = Integer.parseInt(opdno);
                ip = ip+1;
                txtregno.setText(Integer.toString(ip));
            }
            else
            {
                txtregno.setText(Integer.toString(1));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error at getregno:"+ex);
        }
    }
    
    private void lblhomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhomeMouseClicked
        // TODO add your handling code here:
        setLblColor(lblhome,jLabel1);
        resetLblColor(lblappt,jLabel2);
        resetLblColor(lblpatient,jLabel3);
        resetLblColor(lbldiagnosis,jLabel25);
        resetLblColor(lblhistory,jLabel4);
        
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel , "pnlHome"); 
    }//GEN-LAST:event_lblhomeMouseClicked

    private void lblapptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblapptMouseClicked
        // TODO add your handling code here:
        setLblColor(lblappt,jLabel2);
        resetLblColor(lblhome,jLabel1);
        resetLblColor(lblpatient,jLabel3);
        resetLblColor(lbldiagnosis,jLabel25);
        resetLblColor(lblhistory,jLabel4);
        
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel , "pnlAppt"); 
        loadappid();
        fillappointment();
        loadaid();
    }//GEN-LAST:event_lblapptMouseClicked

    private void lblpatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblpatientMouseClicked
        // TODO add your handling code here:
        setLblColor(lblpatient,jLabel3);
        resetLblColor(lblhome,jLabel1);
        resetLblColor(lblappt,jLabel2);
        resetLblColor(lbldiagnosis,jLabel25);
        resetLblColor(lblhistory,jLabel4);
        
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel , "pnlPatient"); 
        loaddeldis();
        loadmedicine();
       // clearsearch();
        loadtoday();
        loaddate();
        loadappid();
        //loadregno();
        //loadbilldate();
        txtregno.setText("");
        //getregno();
    }//GEN-LAST:event_lblpatientMouseClicked

    private void cancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_cancelMousePressed
    //book appointment validation
     public void validation()
    {
        java.util.Date d = jDateChooser1.getDate();
        String name = txtname.getText();
        if (name.length() !=0) 
        {
                if (d != null) 
                {
                    //if(purpose.length() !=0)
                    //{
                        if(jRadioButton1.isSelected()==true || jRadioButton2.isSelected()==true)
                        {
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Choose Time Slot");
                        }
                    //}
                   /* else
                    {
                        JOptionPane.showMessageDialog(null,"Enter Purpose of Appointment");
                    }*/
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Choose date");
                }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Enter Name");
        } 
    }
     
     public void addtotable()
    {
        String disease = cmbdisease.getSelectedItem().toString();
        String medicine = cmbmedicine.getSelectedItem().toString();
        String dose = cmbdose.getSelectedItem().toString();
        String frequency = cmbfrequency.getSelectedItem().toString();
        String instruct = cmbinstruction.getSelectedItem().toString();
        String day = cmbdays.getSelectedItem().toString();
        model1.addRow(new Object [] {disease,medicine,dose,frequency,instruct,day});
        //tabledisease.setModel(model1);
        //resizeColumnWidth(tabledisease);
    }
    public void resetapp()
    {
        txtname.setText("");
    }
    
//    public void loadbilldate()
//       {
//           ArrayList<String> list2 = new ArrayList<>();
//                try
//                {
//                    rs = conn.getbilldate();
//                    while(rs.next())
//                    {
//                        list2.add(rs.getString("billdate"));
//                    }
//                }
//                catch(SQLException e)
//                {
//                     System.out.println("Can't fetch bill date in combo box: "+e);
//                }
////                cmbchoosebilldate.setModel(new DefaultComboBoxModel(list2.toArray()));
//       }	    
   //search patient 
   /* public void clearsearch()
    {
        txtsearch.setText(null);
        DefaultTableModel dtm = (DefaultTableModel) tblpatient.getModel();
        dtm.setRowCount(0);
    }*/
     
    public void save_diagnosis_data()throws SQLException
    {
        try{
        String opdno = (String) cmb_opdno.getSelectedItem();
        String date = txtdate1.getText();
       int rows = tabledisease.getRowCount();
            pst = con.prepareStatement("insert into treatmentdetails values (?,?,?,?,?,?,?,?,?)");
            for(int row=0; row <rows;row++)
            {
                
                String disease = (String) tabledisease.getValueAt(row, 0);
                System.out.println(disease);
                String medicine = (String) tabledisease.getValueAt(row, 1);
                String dose = (String) tabledisease.getValueAt(row, 2);
                String frequency = (String) tabledisease.getValueAt(row, 3);
                String instructions = (String) tabledisease.getValueAt(row, 4);
                String days = (String) tabledisease.getValueAt(row, 5);
                pst.setString(1, null);
                pst.setString(2, opdno);
                pst.setString(3, date);
                pst.setString(4, disease);
                pst.setString(5, medicine);
                pst.setString(6, dose);
                pst.setString(7, frequency);
                pst.setString(8, instructions);
                pst.setString(9, days);
                pst.executeUpdate();
                //model2.addRow(new Object [] {disease,medicine,dose,frequency,instructions,days});
               
            }
            pst.executeBatch();
            
                JOptionPane.showMessageDialog(null,"Data Saved ...");
        }
        catch(Exception e)
        {
                JOptionPane.showMessageDialog(null,"Data not Saved ...");
        }
            
    }
    
    
    
    //Diagnosis table-tabledisease  (pnlPatient)
    public void savedata() throws SQLException
    {
        String opdno=txtregno.getText();
        String date=txtdate.getText();
        String time=txttime.getText();
        String agey=txtage.getText();        
        String address= txtaddress.getText();        
        String gender = cmbgender.getSelectedItem().toString();        
        String name = txtpname.getText();
        String bgroup = cmbbgroup.getSelectedItem().toString();
        String contact = txtcontact.getText();       
        popdno = opdno;
        pname = name;
        pdate = date;
        pst=con.prepareStatement("insert into patient values (?,?,?,?,?,?,?,?,?)");
        pst.setString(1, opdno);
        pst.setString(2, name);
        pst.setString(3, gender);
        pst.setString(4, date);
        pst.setString(5, time);
        pst.setString(6, contact);
        pst.setString(7, address);
        pst.setString(8, agey);
        pst.setString(9, bgroup);
       
        int i=pst.executeUpdate();
        System.out.println("in savedata function");
        if(i>0)
        {
            System.out.println("successfully inserted");
            JOptionPane.showMessageDialog(null,"Data Saved ...");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Data not Saved ...");
        }
        clear();
    }
    
    public void clear()
    {        
        txtregno.setText(""); 
        txtage.setText("0");
        txtpname.setText("");
        cmbgender.setSelectedIndex(0);
        txtage.setText("");        
        txtaddress.setText("");
        txtcontact.setText("");        
    }
    
     class Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //Continuous TIme
            Calendar now = Calendar.getInstance();
            int hr = now.get(Calendar.HOUR_OF_DAY);
            int min = now.get(Calendar.MINUTE);
            int sec = now.get(Calendar.SECOND);
            txttime.setText(hr+":"+min+":"+sec);
        }
    }
    
    private void btnscheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnscheduleActionPerformed
        validation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = dateFormat.format(jDateChooser1.getDate());
        String name = txtname.getText();
        String radioText = "";
        String timeslot = cmbtimeslot.getSelectedItem().toString(); 
        int count =0;
        if(jRadioButton1.isSelected())
        {
            radioText= jRadioButton1.getText();
        }
        if(jRadioButton2.isSelected())
        {
            radioText= jRadioButton2.getText();
        }
        try
        {
            pst = con.prepareStatement("select count(*) from appointment where appdate=? and time_slot=?");
            pst.setString(1, startDateString);
            pst.setString(2, timeslot);
            rs = pst.executeQuery();
            while(rs.next())
            {
                count = rs.getInt("count(*)");
            }
            if(count >0)
            {
                JOptionPane.showMessageDialog(null, "Already booked for this time period");
            }
            else
            {
                pst = con.prepareStatement("insert into appointment values (?,?,?,?,?)");
                pst.setInt(1,0);
                pst.setString(2, name);                
                pst.setString(3, startDateString);          
                pst.setString(4, radioText);
                pst.setString(5, timeslot);
                
                int i=pst.executeUpdate();
                if(i >0)
                {
                    fillappointment();
                        loadaid();
                        loadappid();  //getting new appointment id
                    JOptionPane.showMessageDialog(null, "Appointment Scheduled successfully.");
                    //fetch();
                    resetapp();
                      //  loadprogressbar();
                        
                    if(jRadioButton1.isSelected())
                    {
                        radiobtn1();
                    }
                    if(jRadioButton2.isSelected())
                    {
                        radiobtn2();
                    }
                }
                else
                {
                    System.out.println("Error while booking appointment");
                    //fetch();
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnscheduleActionPerformed

     public void loadappid()
    {
        try
        {
            int appid =0;
            pst = con.prepareStatement("select max(aid) from appointment");
            rs = pst.executeQuery();
            if(rs.next())
            {
                appid = rs.getInt("max(aid)");
            }
            appid++;
            jTextField2.setText(Integer.toString(appid));
        }
        catch(Exception e)
        {
            System.out.println("Error at loadappid:" +e);
        }
    }
    
    
    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        resetapp();
    }//GEN-LAST:event_btnresetActionPerformed

    private void txtnameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnameKeyTyped
        String name = txtname.getText();
        int a=0;
        try
        {
            pst = con.prepareStatement("select count(*) from patient where name = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();
            if(rs.next())
            {
                a = rs.getInt("count(*)");
            }
//            if(a>0)
//            {
//                cmbtype.setSelectedIndex(1);
//            }
        }
        catch(SQLException e)
        {
            System.out.println("Error:" +e);
        }
    }//GEN-LAST:event_txtnameKeyTyped

    //loads the tbltom( today's list --appointment) again after deleting one appointment
    public void loadtoday()
    {
        try
        {
            pst = con.prepareStatement("select aid as Appointment_Number, appdate as Appointment_Date, name as Name,time_m_e as Time_Slot, "
                    + "time_slot as Time from appointment a,times t where appdate = CURDATE() and a.time_slot = t.slots order by t.tid ASC");
            rs = pst.executeQuery();
            tbltom.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(tbltom);
        }
        catch(Exception e)
        {
            System.out.println("Error at loadtoday: "+e);
        }
    }
    public void loadaid()
    {
        ArrayList<String> list = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select aid from appointment where appdate >= CURDATE() ");
            rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(rs.getString("aid"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error at loadaid:"+ex);
        }
        jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
    }
    
    public void loadregno()
    {
        ArrayList<String> list = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select regno from patient");
            rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(rs.getString("regno"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error at loadregno:"+ex);
        }
       cmb_opdno.setModel(new DefaultComboBoxModel(list.toArray()));
    }
    
    //delete appointment
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       try
        {
            int i=0;
            pst = con.prepareStatement("delete from appointment where aid = ?");
            pst.setString(1,jComboBox1.getSelectedItem().toString());
            i = pst.executeUpdate();
            if(i>0)
            {
                JOptionPane.showMessageDialog(null, "Appointment deleted.");
                fillappointment();
                loadtoday();
                loadaid();                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error deleting Appointment.");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtregnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtregnoKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtregnoKeyTyped

    private void txtageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtageKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtageKeyTyped

    private void txtcontactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcontactKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtcontactKeyTyped

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        char c = evt.getKeyChar();
        if(c==KeyEvent.VK_ENTER)
        {
            int aid = Integer.parseInt(jTextField1.getText());
            try
            {
                pst = con.prepareStatement("select name from appointment where aid = ?");
                pst.setInt(1, aid);
                rs = pst.executeQuery();
                if(rs.next())
                {
                    txtpname.setText(rs.getString("name"));
                }
                else
                {
                    txtpname.setText(null);
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void tablediseaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablediseaseMouseClicked
        btndeleterow.setVisible(true);
    }//GEN-LAST:event_tablediseaseMouseClicked

    private void btndeleterowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleterowActionPerformed
        if(tabledisease.getSelectedRow() != -1)
        {
            model1.removeRow(tabledisease.getSelectedRow());
        }
    }//GEN-LAST:event_btndeleterowActionPerformed

    private void btngetregnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngetregnoActionPerformed
        // TODO add your handling code here:
        String opdno = null;
        try {
            rs = conn.getopdno();
            if(rs.next())
            {
                opdno = rs.getString("max(regno)");
                int ip = Integer.parseInt(opdno);
                ip = ip+1;
                txtregno.setText(Integer.toString(ip));
            }
            else
            {
                txtregno.setText(Integer.toString(1));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error at getopdno:"+ex);
        }
    }//GEN-LAST:event_btngetregnoActionPerformed
public void logout()
    {
        try 
        {
            stmt = con.createStatement();
            String query = "update session set logoffat=NOW() where sid='"+sessionid+"'";
            stmt.executeUpdate(query);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        new LoginFrameGUI().setVisible(true);
        dispose();
    }
    
    private void lbllogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbllogoutMouseClicked
        // TODO add your handling code here:
        logout();
    }//GEN-LAST:event_lbllogoutMouseClicked

    private void lbladminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbladminMouseClicked
        // TODO add your handling code here:
        new AdminFrameGUI(suser,stype,Integer.toString(sessionid)).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lbladminMouseClicked

    private void btn_todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_todayActionPerformed

        fill_count();
        btn_all.setBackground(new Color(33,150,243));
        btn_today.setBackground(new Color(62, 77, 159));
        btn_7days.setBackground(new Color(33,150,243));
        btn_1month.setBackground(new Color(33,150,243));
        btn_1year.setBackground(new Color(33,150,243));
    }//GEN-LAST:event_btn_todayActionPerformed

    private void btn_7daysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_7daysActionPerformed
        
        fill_var(7);
        btn_all.setBackground(new Color(33,150,243));
        btn_today.setBackground(new Color(33,150,243));
        btn_7days.setBackground(new Color(62, 77, 159));
        btn_1month.setBackground(new Color(33,150,243));
        btn_1year.setBackground(new Color(33,150,243));
    }//GEN-LAST:event_btn_7daysActionPerformed

    private void btn_1monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_1monthActionPerformed
        
        fill_var(30);
        btn_all.setBackground(new Color(33,150,243));
        btn_today.setBackground(new Color(33,150,243));
        btn_7days.setBackground(new Color(33,150,243));
        btn_1month.setBackground(new Color(62, 77, 159));
        btn_1year.setBackground(new Color(33,150,243));
    }//GEN-LAST:event_btn_1monthActionPerformed

    private void btn_1yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_1yearActionPerformed

        fill_var(365);
        btn_all.setBackground(new Color(33,150,243));
        btn_today.setBackground(new Color(33,150,243));
        btn_7days.setBackground(new Color(33,150,243));
        btn_1month.setBackground(new Color(33,150,243));
        btn_1year.setBackground(new Color(62, 77, 159));

    }//GEN-LAST:event_btn_1yearActionPerformed

    private void btn_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_allActionPerformed

        fill_all();
        btn_all.setBackground(new Color(62, 77, 159));
        btn_today.setBackground(new Color(33,150,243));
        btn_7days.setBackground(new Color(33,150,243));
        btn_1month.setBackground(new Color(33,150,243));
        btn_1year.setBackground(new Color(33,150,243));
    }//GEN-LAST:event_btn_allActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         fill_count();
    }//GEN-LAST:event_formWindowOpened

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        if(txtregno.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please fill/get Registration Number","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtpname.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please enter name","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtage.getText()==null)
        {
            JOptionPane.showMessageDialog(this, "Please enter age","Error",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try
            {
                pdate = txtdate.getText();
                savedata();
//                pst = con.prepareStatement("update appointment set checked=? where name = ? and appdate=CURDATE()");
//                pst.setInt(1,1);
//                pst.setString(2, txtpname.getText());
//                pst.executeUpdate();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HomeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnpresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpresetActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnpresetActionPerformed

    private void btnadddiseaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadddiseaseActionPerformed

        //Diagnosis
        if(cmbfrequency.getSelectedIndex()==-1 || cmbinstruction.getSelectedIndex()==-1||
            cmbdisease.getSelectedIndex()==-1|| cmbdose.getSelectedIndex()==-1 ||
            cmbmedicine.getSelectedIndex()==-1|| cmbdays.getSelectedIndex()==-1)
        {
            JOptionPane.showMessageDialog(null, "Please select all the fields");
        }
        else
        {
            addtotable();
        }
    }//GEN-LAST:event_btnadddiseaseActionPerformed

    private void cmbdiseaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdiseaseActionPerformed
        cmbmedicine.removeAllItems();
        String dept = cmbdisease.getSelectedItem().toString();
        ArrayList<String> list = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select name from medicine where disease = ?");
            pst.setString(1, dept);
            rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(rs.getString("name"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error at cmbdiseaseActionPerformed:"+ex);
        }
        cmbmedicine.setModel(new DefaultComboBoxModel(list.toArray()));
        cmbmedicine.setSelectedIndex(-1);
    }//GEN-LAST:event_cmbdiseaseActionPerformed

    private void lblhistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhistoryMouseClicked
        // TODO add your handling code here:
        resetLblColor(lbldiagnosis,jLabel25);
        setLblColor(lblhistory,jLabel4);
        resetLblColor(lblhome,jLabel1);
        resetLblColor(lblpatient,jLabel3);
        resetLblColor(lblappt,jLabel2);
        
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel , "pnlHistory"); 
        //showHistory();
        
    }//GEN-LAST:event_lblhistoryMouseClicked

    private void btn_save_diagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_diagActionPerformed
        try {
            // TODO add your handling code here:
            save_diagnosis_data();
        } catch (SQLException ex) {
            Logger.getLogger(HomeFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_save_diagActionPerformed

    private void lbldiagnosisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbldiagnosisMouseClicked
        // TODO add your handling code here:
        setLblColor(lbldiagnosis,jLabel25);
        resetLblColor(lblhome,jLabel1);
        resetLblColor(lblpatient,jLabel3);
        resetLblColor(lblappt,jLabel2);
        resetLblColor(lblhistory,jLabel4);
        
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel , "pnlDiagnosis"); 
        loaddeldis();
        loadmedicine();
        loadtoday();
        loaddate();   
        loadregno();
        
    }//GEN-LAST:event_lbldiagnosisMouseClicked

    
//    public String calcgen(int i)
//    {
//        String vargen;
//        if(i==0)
//            vargen="All";
//        else if(i==1)
//            vargen="Male";
//        else
//            vargen="Female";
//        
//        return vargen;
//    }
//    
//    public String calcdate(int i)
//    {
//        String vardate;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//        String date7 = dateFormat.format(cal.getTime());
//        Calendar cal1 = Calendar.getInstance();
//        cal1.add(Calendar.DATE, -30);
//        String date30 = dateFormat.format(cal1.getTime());
//        Calendar cal2 = Calendar.getInstance();
//        cal2.add(Calendar.DATE, -365);
//        String date365 = dateFormat.format(cal2.getTime());
//        Calendar cal3 = Calendar.getInstance();
//        
//        
//        if(i==0)  //ALL
//            vardate="All";
//        else if(i==1)
//            vardate=dateFormat.format(cal3.getTime());
//        else if(i==2)
//            vardate=date7;
//        else if(i==3)
//            vardate=date30;
//        else
//            vardate=date365;          
//        
//        return vardate;
//    }
//    
//    
//    
//    
    
    
    //dashboard-TODAY
    public void fill_count()
    {
        int apptno=0,patno=0,tot_ail=0,tot_med=0;
        try
        {
           /* //selecting format of date
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //getting current instance of Calender-gives current date
            Calendar cal = Calendar.getInstance();
            //adding given no. of days to current date
            cal.add(Calendar.DATE, +day);
            String tom = dateFormat.format(cal.getTime());*/
            
            
            rs=conn.today_appointment();
            if(rs.next())
            {
                apptno = rs.getInt("count(*)");
                System.out.println("apptno: "+apptno);
                lbl_no_appt.setText(Integer.toString(apptno));
            }
            
            rs1=conn.today_patients();
            if(rs1.next())
            {
                patno= rs1.getInt("count(*)");
                System.out.println("patno: "+patno);
                lbl_no_patient.setText(Integer.toString(patno));
            }
            
            rs2=conn.total_ailment();
            if(rs2.next())
            {
                tot_ail=rs2.getInt("count(*)");
                System.out.println("tot_ail: "+tot_ail);
                lbl_no_ailment.setText(Integer.toString(tot_ail));
                
            }
            
            rs3=conn.total_medicine();
            if(rs3.next())
            {
                tot_med=rs3.getInt("count(*)");
                System.out.println("tot_med: "+tot_med);
                lbl_no_med.setText(Integer.toString(tot_med));
            }
        }
        catch(Exception e)
        {
            System.out.println("Error at fill_count "+e);
        }
    }
    
    
    //all_days
    void fill_all()
    {
        int apptno=0,patno=0;
        try{
         rs=conn.all_appointment();
            if(rs.next())
            {
                apptno = rs.getInt("count(*)");
                System.out.println("apptno: "+apptno);
                lbl_no_appt.setText(Integer.toString(apptno));
            }
            
            rs1=conn.all_patients();
            if(rs1.next())
            {
                patno= rs1.getInt("count(*)");
                System.out.println("patno: "+patno);
                lbl_no_patient.setText(Integer.toString(patno));
            }
        }
        catch(Exception e)
        {
            System.out.println("error at fill_all:"+e);
        }
    }
   
    
    //last 7,30,365 days
    
     void fill_var(int day)
    {
        int apptno=0,patno=0;
        try{
            
            //selecting format of date
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //getting current instance of Calender-gives current date
            Calendar cal = Calendar.getInstance();
            //adding given no. of days to current date
            cal.add(Calendar.DATE, -day);
            String tom = dateFormat.format(cal.getTime());
            System.out.println("7 days:"+tom);
            
            
           /* //30 days
            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DATE, -30);
            String tom1 =dateFormat.format(cal1.getTime());
            System.out.println("30 days:"+tom1);
            
            //365 days
            Calendar cal2 = Calendar.getInstance();
            cal2.add(Calendar.DATE, -365);
            String tom2 =dateFormat.format(cal2.getTime());
            System.out.println("365 days:"+tom2);*/
                    
			            
            rs=conn.var_appointment(tom);
            if(rs.next())
            {
                apptno = rs.getInt("count(*)");
                System.out.println("apptno: "+apptno);
                lbl_no_appt.setText(Integer.toString(apptno));
            }
            
            rs1=conn.var_patients(tom);
            if(rs1.next())
            {
                patno= rs1.getInt("count(*)");
                System.out.println("patno: "+patno);
                lbl_no_patient.setText(Integer.toString(patno));
            }
        }
        catch(Exception e)
        {
            System.out.println("error at fill_all:"+e);
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeFrameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFrameGUI("","","").setVisible(true);
            }
        });
    }
    
    public void setLblColor(JLabel lbl,JLabel lbl_icon)
    {
        lbl.setBackground(new Color(97, 97, 97));
        lbl_icon.setBackground(new Color(97, 97, 97));
    }
    public void resetLblColor(JLabel lbl,JLabel lbl_icon)
    {
        lbl.setBackground(new Color(48,48,48));
        lbl_icon.setBackground(new Color(48,48,48));
    }
            
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_1month;
    private javax.swing.JButton btn_1year;
    private javax.swing.JButton btn_7days;
    private javax.swing.JButton btn_all;
    private javax.swing.JButton btn_save_diag;
    private javax.swing.JButton btn_today;
    private javax.swing.JButton btnadddisease1;
    private javax.swing.JButton btndeleterow;
    private javax.swing.JButton btngetregno;
    private javax.swing.JButton btnpreset;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnschedule;
    private javax.swing.JLabel cancel;
    private javax.swing.JComboBox<String> cmb_opdno;
    private javax.swing.JComboBox<String> cmbbgroup;
    private javax.swing.JComboBox<String> cmbdays;
    private javax.swing.JComboBox<String> cmbdisease;
    private javax.swing.JComboBox<String> cmbdose;
    private javax.swing.JComboBox<String> cmbfrequency;
    private javax.swing.JComboBox cmbgender;
    private javax.swing.JComboBox<String> cmbinstruction;
    private javax.swing.JComboBox<String> cmbmedicine;
    private javax.swing.JComboBox<String> cmbtimeslot;
    private javax.swing.JLabel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lbl_no_ailment;
    private javax.swing.JLabel lbl_no_appt;
    private javax.swing.JLabel lbl_no_med;
    private javax.swing.JLabel lbl_no_patient;
    private javax.swing.JLabel lbladdressopd1;
    private javax.swing.JLabel lbladmin;
    private javax.swing.JTextField lblage;
    private javax.swing.JLabel lblageopd1;
    private javax.swing.JLabel lblappt;
    private javax.swing.JLabel lbldatetimeopd1;
    private javax.swing.JLabel lbldiagnosis;
    private javax.swing.JTextField lblgender;
    private javax.swing.JLabel lblgenderopd1;
    private javax.swing.JLabel lblhistory;
    private javax.swing.JLabel lblhome;
    private javax.swing.JLabel lbllogout;
    private javax.swing.JTextField lblname;
    private javax.swing.JLabel lblnameopd1;
    private javax.swing.JLabel lblopdreg1;
    private javax.swing.JLabel lblpatient;
    private javax.swing.JTextField lbltype;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel pnlAppt;
    private javax.swing.JPanel pnlDiagnosis;
    private javax.swing.JPanel pnlHistory;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlPatient;
    private javax.swing.JPanel pnl_apptlist;
    private javax.swing.JPanel pnl_bookappt;
    private javax.swing.JPanel pnl_deleteappt;
    private javax.swing.JPanel pnl_diagnosis1;
    private javax.swing.JPanel pnl_diagnosistable;
    private javax.swing.JPanel pnl_no_aliment;
    private javax.swing.JPanel pnl_no_appt;
    private javax.swing.JPanel pnl_no_med;
    private javax.swing.JPanel pnl_no_patient;
    private javax.swing.JPanel pnl_patientreg;
    private javax.swing.JPanel pnlmenu;
    private javax.swing.JTable tabledisease;
    private javax.swing.JTable tblhistory;
    private javax.swing.JTable tbltom;
    private javax.swing.JTextArea txtaddress;
    private javax.swing.JTextField txtage;
    private javax.swing.JTextField txtcontact;
    private javax.swing.JTextField txtdate;
    private javax.swing.JTextField txtdate1;
    private javax.swing.JTextField txtname;
    private javax.swing.JTextField txtnote1;
    private javax.swing.JTextField txtpname;
    private javax.swing.JTextField txtregno;
    private javax.swing.JTextField txttime;
    // End of variables declaration//GEN-END:variables
}
