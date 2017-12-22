package Digilinc;

import com.itextpdf.text.*;
import java.awt.GradientPaint;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.ui.TextAnchor;

public class HomeGUI extends javax.swing.JFrame {
    DefaultTableModel model,model1,model2;
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    String suser,stype;
    DbConnect conn =null;
    int sessionid;
    String popdno , pdate, pname;
    AutoCompleteDecorator decorator;
    String name,address,uid,gender,regno,bgroup,sdate;
    int age;
    Connection con;
    int max;
    public HomeGUI(String username, String usertype, String sid)
    {
        initComponents();
        txtname.setCaretColor(Color.white);
        jTextField2.setCaretColor(Color.white);
        txtnotes.setCaretColor(Color.white);
        txtsearch.setCaretColor(Color.white);
        txtregno.setCaretColor(Color.white);
        txtuid.setCaretColor(Color.white);
        jTextField1.setCaretColor(Color.white);
        txtpname.setCaretColor(Color.white);
        txtreligion.setCaretColor(Color.white);
        txtcontact.setCaretColor(Color.white);
        txtoccupation.setCaretColor(Color.white);
        txtaddress.setCaretColor(Color.white);
        txtnote.setCaretColor(Color.white);
        txtfees.setCaretColor(Color.white);
        parsingarea.setCaretColor(Color.white);
        suser = username;
        sessionid = Integer.parseInt(sid);
        stype = usertype;
        if(stype.equals("Receptionist"))
        {
            jButton4.setVisible(false);
        }
        java.util.Date date = new java.util.Date();
        jDateChooser1.setDate(date);
        getContentPane().setBackground(Color.decode("#313d48"));
        jDateChooser1.getJCalendar().setMinSelectableDate(new java.util.Date());
        tblpatient.setFillsViewportHeight(true);
        jScrollPane7.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header1 = tblpatient.getTableHeader();
        header1.setBackground(Color.decode("#313d48"));
        header1.setForeground(Color.WHITE);
        tblpatient.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 14));
        tblpatient.setRowHeight(24);
        
        tabledisease.setFillsViewportHeight(true);
        jScrollPane5.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header2 = tabledisease.getTableHeader();
        header2.setBackground(Color.decode("#313d48"));
        header2.setForeground(Color.WHITE);
        tabledisease.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 14));
        tabledisease.setRowHeight(24);
        
        tblbillprint.setFillsViewportHeight(true);
        jScrollPane2.getViewport().setBackground(Color.decode("#313d48"));
        tblbillprint.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 12));
        tblbillprint.setRowHeight(24);
        
        tblallpatients.setFillsViewportHeight(true);
        jScrollPane4.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header3 = tblallpatients.getTableHeader();
        header3.setBackground(Color.decode("#313d48"));
        header3.setForeground(Color.WHITE);
        tblallpatients.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 14));
        tblallpatients.setRowHeight(24);
        
        jTable1.setFillsViewportHeight(true);
        jScrollPane4.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header4 = jTable1.getTableHeader();
        header4.setBackground(Color.decode("#313d48"));
        header4.setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 12));
        jTable1.getTableHeader().setSize(650, 35);
        jTable1.setRowHeight(24);
        
        tbltom.setFillsViewportHeight(true);
        jScrollPane8.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header5 = tbltom.getTableHeader();
        header5.setBackground(Color.decode("#313d48"));
        header5.setForeground(Color.WHITE);
        tbltom.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 14));
        tbltom.setRowHeight(24);
        
        jTable2.setFillsViewportHeight(true);
        jScrollPane9.getViewport().setBackground(Color.decode("#313d48"));
        JTableHeader header8 = tabledisease.getTableHeader();
        header8.setBackground(Color.decode("#313d48"));
        header8.setForeground(Color.WHITE);
        jTable2.getTableHeader().setFont(new java.awt.Font("Century Gothic", Font.BOLD, 14));
        jTable2.setRowHeight(24);
        
        btnOPD.setVisible(false);
        btndeleterow.setVisible(false);
        model = new DefaultTableModel();
        model.addColumn("Reg No.");
        model.addColumn("Name");
        model.addColumn("Address");
        model.addColumn("Date");
        model.addColumn("Time");
        tblpatient.setModel(model);
        model1 = new DefaultTableModel();
        model1.addColumn("Disease");
        model1.addColumn("Medicine");
        model1.addColumn("Dose");
        model1.addColumn("Frequency");
        model1.addColumn("Instructions");
        model1.addColumn("Days");
        tabledisease.setModel(model1);
        model2 = new DefaultTableModel();
        model2.addColumn("Disease");
        model2.addColumn("Medicine");
        model2.addColumn("Dose");
        model2.addColumn("Frequency");
        model2.addColumn("Instructions");
        model2.addColumn("Days");
        tblbillprint.setModel(model2);
        this.setIconImage(new ImageIcon(getClass().getResource("icon.ico")).getImage());
        Timer t = new Timer(1000,new Listener());
        t.start();
               
        btnprintp.addActionListener(new PrintGUIWindow(printpanel1bill));
        if(!username.equals(""))
        {
                suser = username;
        }
        try
        {
            conn = new DbConnect();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
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
        this.pack();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        PHome = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbtype = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        txtnotes = new javax.swing.JTextField();
        btnschedule = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        cmbtimeslot = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblnewpatient = new javax.swing.JLabel();
        lblappointment = new javax.swing.JLabel();
        txtnewpatient = new javax.swing.JTextField();
        txtappointment = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblpatient = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        txtsearch = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btnOPD = new javax.swing.JButton();
        jLabel144 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        Appointment = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbltom = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel150 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        AddPatient = new javax.swing.JPanel();
        OPDNew = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cmbdisease = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cmbmedicine = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cmbdose = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cmbfrequency = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        cmbinstruction = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cmbdays = new javax.swing.JComboBox<>();
        btnadddisease = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        lblopdreg1 = new javax.swing.JLabel();
        txtregno = new javax.swing.JTextField();
        lblageopd1 = new javax.swing.JLabel();
        txtage = new javax.swing.JTextField();
        jLabel220 = new javax.swing.JLabel();
        txttime = new javax.swing.JTextField();
        txtdate = new javax.swing.JTextField();
        lbldatetimeopd1 = new javax.swing.JLabel();
        cmbvtype = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lblnameopd1 = new javax.swing.JLabel();
        txtpname = new javax.swing.JTextField();
        lblgenderopd1 = new javax.swing.JLabel();
        cmbgender = new javax.swing.JComboBox();
        labelward3 = new javax.swing.JLabel();
        txtuid = new javax.swing.JTextField();
        txtcontact = new javax.swing.JTextField();
        txtoccupation = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtreligion = new javax.swing.JTextField();
        lblreligionopd1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cmbbgroup = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaddress = new javax.swing.JTextArea();
        lbladdressopd1 = new javax.swing.JLabel();
        btnload = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        parsingarea = new javax.swing.JTextArea();
        lblnotes = new javax.swing.JLabel();
        txtnote = new javax.swing.JTextField();
        lblfees = new javax.swing.JLabel();
        txtfees = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel34 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabledisease = new javax.swing.JTable();
        btndeleterow = new javax.swing.JButton();
        btnpreset = new javax.swing.JButton();
        btnprintp = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        jLabel145 = new javax.swing.JLabel();
        AllPatient = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblallpatients = new javax.swing.JTable();
        jLabel149 = new javax.swing.JLabel();
        PrintBill = new javax.swing.JPanel();
        printpanel1bill = new javax.swing.JPanel();
        jLabel65bill = new javax.swing.JLabel();
        jLabel66bill = new javax.swing.JLabel();
        jLabel77bill = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        lblbilldate = new javax.swing.JLabel();
        lblbillname = new javax.swing.JLabel();
        lblbillregno = new javax.swing.JLabel();
        lblbillno = new javax.swing.JLabel();
        jLabel237 = new javax.swing.JLabel();
        lblprintfees = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbillprint = new javax.swing.JTable();
        btnprint = new javax.swing.JButton();
        jLabel109 = new javax.swing.JLabel();
        cmbchoosebillname = new javax.swing.JComboBox<>();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        cmbchoosebilldate = new javax.swing.JComboBox<>();
        Statistics = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cmbgen = new javax.swing.JComboBox<>();
        cmbage = new javax.swing.JComboBox<>();
        cmbdayselect = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        cmbdayselect1 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        lblsfees = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        btnhomelogout = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VPS Hospital Management System");
        setBackground(new java.awt.Color(49, 61, 72));
        setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1366, 750));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jTabbedPane3.setBackground(new java.awt.Color(49, 61, 72));
        jTabbedPane3.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jTabbedPane3.setPreferredSize(new java.awt.Dimension(0, 0));
        jTabbedPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane3MouseClicked(evt);
            }
        });

        PHome.setBackground(new java.awt.Color(49, 61, 72));
        PHome.setForeground(new java.awt.Color(255, 255, 255));
        PHome.setPreferredSize(new java.awt.Dimension(1360, 670));
        PHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PHomeMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(49, 61, 72));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Book Appointment", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 36), new java.awt.Color(19, 166, 217))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Select Type:");

        cmbtype.setBackground(new java.awt.Color(49, 61, 72));
        cmbtype.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbtype.setForeground(new java.awt.Color(255, 255, 255));
        cmbtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New", "Repeat" }));
        cmbtype.setOpaque(false);

        jLabel6.setBackground(new java.awt.Color(49, 61, 72));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Date:");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Time Slot:");

        jRadioButton1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("Morning");
        jRadioButton1.setOpaque(false);

        jRadioButton2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("Evening");
        jRadioButton2.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Notes:");

        txtnotes.setBackground(new java.awt.Color(49, 61, 72));
        txtnotes.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtnotes.setForeground(new java.awt.Color(255, 255, 255));
        txtnotes.setOpaque(false);

        btnschedule.setBackground(new java.awt.Color(19, 166, 217));
        btnschedule.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnschedule.setForeground(new java.awt.Color(255, 255, 255));
        btnschedule.setText("Schedule");
        btnschedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnscheduleActionPerformed(evt);
            }
        });

        btnreset.setBackground(new java.awt.Color(19, 166, 217));
        btnreset.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnreset.setForeground(new java.awt.Color(255, 255, 255));
        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        cmbtimeslot.setBackground(new java.awt.Color(49, 61, 72));
        cmbtimeslot.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbtimeslot.setForeground(new java.awt.Color(255, 255, 255));
        cmbtimeslot.setOpaque(false);

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Name:");

        txtname.setBackground(new java.awt.Color(49, 61, 72));
        txtname.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtname.setForeground(new java.awt.Color(255, 255, 255));
        txtname.setOpaque(false);
        txtname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnameKeyTyped(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(49, 61, 72));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setDateFormatString("yyyy-MM-d");
        jDateChooser1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jDateChooser1.setOpaque(false);

        jSeparator15.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator17.setForeground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("ID:");

        jTextField2.setBackground(new java.awt.Color(49, 61, 72));
        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel17))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(cmbtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(81, 81, 81)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSeparator15)
                                            .addComponent(txtname))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel29)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(74, 74, 74)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnotes)
                                    .addComponent(jSeparator17)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 101, Short.MAX_VALUE)
                                .addComponent(btnschedule)
                                .addGap(54, 54, 54)
                                .addComponent(btnreset)
                                .addGap(107, 107, 107)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton1)
                                .addGap(6, 6, 6)
                                .addComponent(jRadioButton2)))
                        .addGap(10, 10, 10)
                        .addComponent(cmbtimeslot, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29))
                            .addGap(0, 0, 0)
                            .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbtimeslot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnschedule)
                    .addComponent(btnreset))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(49, 61, 72));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        lblnewpatient.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblnewpatient.setForeground(new java.awt.Color(255, 255, 255));
        lblnewpatient.setText("Patients Registrations Today:");

        lblappointment.setBackground(new java.awt.Color(49, 61, 72));
        lblappointment.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblappointment.setForeground(new java.awt.Color(255, 255, 255));
        lblappointment.setText("Scheduled Appointments:");

        txtnewpatient.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtnewpatient.setForeground(new java.awt.Color(255, 255, 255));
        txtnewpatient.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtnewpatient.setEnabled(false);
        txtnewpatient.setOpaque(false);
        txtnewpatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnewpatientActionPerformed(evt);
            }
        });

        txtappointment.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtappointment.setForeground(new java.awt.Color(255, 255, 255));
        txtappointment.setEnabled(false);
        txtappointment.setOpaque(false);

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblnewpatient)
                        .addGap(13, 13, 13)
                        .addComponent(txtnewpatient, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(182, 182, 182)
                .addComponent(lblappointment)
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtappointment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(293, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnewpatient)
                    .addComponent(txtnewpatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblappointment)
                    .addComponent(txtappointment, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );

        txtnewpatient.getAccessibleContext().setAccessibleDescription("");

        jPanel7.setBackground(new java.awt.Color(49, 61, 72));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Patient", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 36), new java.awt.Color(19, 166, 217))); // NOI18N
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        tblpatient.setBackground(new java.awt.Color(49, 61, 72));
        tblpatient.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        tblpatient.setForeground(new java.awt.Color(255, 255, 255));
        tblpatient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblpatient.setColumnSelectionAllowed(false);
        tblpatient.setGridColor(new java.awt.Color(49, 61, 72));
        tblpatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpatientMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblpatient);

        txtsearch.setBackground(new java.awt.Color(49, 61, 72));
        txtsearch.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtsearch.setForeground(new java.awt.Color(255, 255, 255));
        txtsearch.setNextFocusableComponent(btnsearch);
        txtsearch.setOpaque(false);

        btnsearch.setBackground(new java.awt.Color(19, 166, 217));
        btnsearch.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnsearch.setForeground(new java.awt.Color(255, 255, 255));
        btnsearch.setText("Search Patient");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));

        btnOPD.setBackground(new java.awt.Color(19, 166, 217));
        btnOPD.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btnOPD.setForeground(new java.awt.Color(255, 255, 255));
        btnOPD.setText("Get Details");
        btnOPD.setNextFocusableComponent(btnhomelogout);
        btnOPD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOPDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtsearch)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addComponent(btnsearch)
                        .addGap(18, 18, 18)
                        .addComponent(btnOPD))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnsearch)
                        .addComponent(btnOPD))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jLabel144.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setText("VPS TecHub Pvt. Ltd.");
        jLabel144.setPreferredSize(new java.awt.Dimension(173, 50));

        jTable1.setBackground(new java.awt.Color(49, 61, 72));
        jTable1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setGridColor(new java.awt.Color(49, 61, 72));
        jScrollPane6.setViewportView(jTable1);

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Immediate Appointments");

        jProgressBar1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N

        jLabel5.setBackground(new java.awt.Color(49, 61, 72));
        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel27.setBackground(new java.awt.Color(49, 61, 72));
        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));

        jLabel28.setBackground(new java.awt.Color(49, 61, 72));
        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Today's Appointment Status");

        javax.swing.GroupLayout PHomeLayout = new javax.swing.GroupLayout(PHome);
        PHome.setLayout(PHomeLayout);
        PHomeLayout.setHorizontalGroup(
            PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PHomeLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PHomeLayout.createSequentialGroup()
                        .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PHomeLayout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(PHomeLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PHomeLayout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PHomeLayout.createSequentialGroup()
                                .addGap(249, 249, 249)
                                .addComponent(jLabel10))
                            .addGroup(PHomeLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(72, 72, 72))))
            .addGroup(PHomeLayout.createSequentialGroup()
                .addGap(492, 492, 492)
                .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PHomeLayout.setVerticalGroup(
            PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PHomeLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PHomeLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(PHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(PHomeLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel28)))
                        .addGap(10, 10, 10)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PHomeLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Home", PHome);

        Appointment.setBackground(new java.awt.Color(49, 61, 72));
        Appointment.setForeground(new java.awt.Color(255, 255, 255));
        Appointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AppointmentMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(49, 61, 72));
        jPanel3.setBorder(new javax.swing.border.MatteBorder(null));

        tbltom.setBackground(new java.awt.Color(49, 61, 72));
        tbltom.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        tbltom.setForeground(new java.awt.Color(255, 255, 255));
        tbltom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbltom.setGridColor(new java.awt.Color(49, 61, 72));
        tbltom.setOpaque(false);
        tbltom.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tbltom);

        jLabel14.setBackground(new java.awt.Color(49, 61, 72));
        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Today's List");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(229, 229, 229))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jPanel12.setBackground(new java.awt.Color(49, 61, 72));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delete Appointment", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 24), new java.awt.Color(19, 166, 217))); // NOI18N

        jLabel18.setBackground(new java.awt.Color(49, 61, 72));
        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Appointment No.:");

        jComboBox1.setBackground(new java.awt.Color(49, 61, 72));
        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setOpaque(false);

        jButton1.setBackground(new java.awt.Color(19, 166, 217));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(jButton1))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
        );

        jLabel150.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(255, 255, 255));
        jLabel150.setText("VPS TecHub Pvt. Ltd.");
        jLabel150.setPreferredSize(new java.awt.Dimension(173, 50));

        jLabel26.setBackground(new java.awt.Color(49, 61, 72));
        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(19, 166, 217));

        jLabel31.setBackground(new java.awt.Color(49, 61, 72));
        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(19, 166, 217));

        jLabel32.setBackground(new java.awt.Color(49, 61, 72));
        jLabel32.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(19, 166, 217));

        javax.swing.GroupLayout AppointmentLayout = new javax.swing.GroupLayout(Appointment);
        Appointment.setLayout(AppointmentLayout);
        AppointmentLayout.setHorizontalGroup(
            AppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AppointmentLayout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(AppointmentLayout.createSequentialGroup()
                .addGroup(AppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AppointmentLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AppointmentLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AppointmentLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AppointmentLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        AppointmentLayout.setVerticalGroup(
            AppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AppointmentLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(AppointmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AppointmentLayout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41)
                .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jTabbedPane3.addTab("Appointment ", Appointment);

        jTabbedPane1.setBackground(new java.awt.Color(49, 61, 72));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1360, 670));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        AddPatient.setBackground(new java.awt.Color(49, 61, 72));
        AddPatient.setForeground(new java.awt.Color(255, 255, 255));
        AddPatient.setPreferredSize(new java.awt.Dimension(1366, 768));
        AddPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddPatientMouseClicked(evt);
            }
        });

        OPDNew.setBackground(new java.awt.Color(49, 61, 72));
        OPDNew.setForeground(new java.awt.Color(255, 255, 255));
        OPDNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OPDNewMouseClicked(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(49, 61, 72));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Diagnosis", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 24), new java.awt.Color(19, 166, 217))); // NOI18N

        cmbdisease.setBackground(new java.awt.Color(49, 61, 72));
        cmbdisease.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbdisease.setForeground(new java.awt.Color(255, 255, 255));
        cmbdisease.setOpaque(false);
        cmbdisease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbdiseaseActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ailments");

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Medicine: ");

        cmbmedicine.setBackground(new java.awt.Color(49, 61, 72));
        cmbmedicine.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbmedicine.setForeground(new java.awt.Color(255, 255, 255));
        cmbmedicine.setOpaque(false);

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Dose:");

        cmbdose.setBackground(new java.awt.Color(49, 61, 72));
        cmbdose.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbdose.setForeground(new java.awt.Color(255, 255, 255));
        cmbdose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 TAB", "2 TAB", "3 TAB", "25ML", "50ML", "75ML", "100ML" }));
        cmbdose.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Frequency:");

        cmbfrequency.setBackground(new java.awt.Color(49, 61, 72));
        cmbfrequency.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbfrequency.setForeground(new java.awt.Color(255, 255, 255));
        cmbfrequency.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ONCE A DAY", "TWICE A DAY", "THRICE A DAY" }));
        cmbfrequency.setOpaque(false);

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Instructions:");

        cmbinstruction.setBackground(new java.awt.Color(49, 61, 72));
        cmbinstruction.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbinstruction.setForeground(new java.awt.Color(255, 255, 255));
        cmbinstruction.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BEFORE BREAKFAST", "AFTER BREAKFAST", "BEFORE LUNCH", "AFTER LUNCH", "BEFORE DINNER", "AFTER DINNER" }));
        cmbinstruction.setOpaque(false);

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Days:");

        cmbdays.setBackground(new java.awt.Color(49, 61, 72));
        cmbdays.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbdays.setForeground(new java.awt.Color(255, 255, 255));
        cmbdays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 DAY", "2 DAYS", "3 DAYS ", "4 DAYS", "5 DAYS", "7 DAYS", "14 DAYS", "21 DAYS", "1 MONTH", "2 MONTHS" }));
        cmbdays.setOpaque(false);

        btnadddisease.setBackground(new java.awt.Color(19, 166, 217));
        btnadddisease.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnadddisease.setForeground(new java.awt.Color(255, 255, 255));
        btnadddisease.setText("Add Ailments");
        btnadddisease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadddiseaseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbinstruction, javax.swing.GroupLayout.Alignment.LEADING, 0, 180, Short.MAX_VALUE)
                    .addComponent(cmbdose, javax.swing.GroupLayout.Alignment.LEADING, 0, 180, Short.MAX_VALUE)
                    .addComponent(cmbdisease, 0, 180, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbmedicine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbfrequency, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbdays, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnadddisease)
                .addGap(184, 184, 184))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbdisease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel19)
                    .addComponent(cmbmedicine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cmbdose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbfrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbdays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(cmbinstruction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)))
                .addGap(18, 18, 18)
                .addComponent(btnadddisease)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(49, 61, 72));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Patient's Details", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 24), new java.awt.Color(19, 166, 217))); // NOI18N
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));

        lblopdreg1.setBackground(new java.awt.Color(49, 61, 72));
        lblopdreg1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblopdreg1.setForeground(new java.awt.Color(255, 255, 255));
        lblopdreg1.setText("Reg. No.:*");

        txtregno.setBackground(new java.awt.Color(49, 61, 72));
        txtregno.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtregno.setForeground(new java.awt.Color(255, 255, 255));
        txtregno.setOpaque(false);
        txtregno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtregnoKeyTyped(evt);
            }
        });

        lblageopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblageopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblageopd1.setForeground(new java.awt.Color(255, 255, 255));
        lblageopd1.setText("Age:*");

        txtage.setBackground(new java.awt.Color(49, 61, 72));
        txtage.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtage.setForeground(new java.awt.Color(255, 255, 255));
        txtage.setText("0");
        txtage.setMaximumSize(null);
        txtage.setOpaque(false);
        txtage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtageKeyTyped(evt);
            }
        });

        jLabel220.setBackground(new java.awt.Color(49, 61, 72));
        jLabel220.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel220.setForeground(new java.awt.Color(255, 255, 255));
        jLabel220.setText("Years");

        txttime.setEditable(false);
        txttime.setBackground(new java.awt.Color(49, 61, 72));
        txttime.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txttime.setForeground(new java.awt.Color(255, 255, 255));
        txttime.setOpaque(false);

        txtdate.setEditable(false);
        txtdate.setBackground(new java.awt.Color(49, 61, 72));
        txtdate.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtdate.setForeground(new java.awt.Color(255, 255, 255));
        txtdate.setOpaque(false);

        lbldatetimeopd1.setBackground(new java.awt.Color(49, 61, 72));
        lbldatetimeopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lbldatetimeopd1.setForeground(new java.awt.Color(255, 255, 255));
        lbldatetimeopd1.setText("Date/Time :*");

        cmbvtype.setBackground(new java.awt.Color(49, 61, 72));
        cmbvtype.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbvtype.setForeground(new java.awt.Color(255, 255, 255));
        cmbvtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New", "Follow-Up" }));
        cmbvtype.setOpaque(false);

        jLabel11.setBackground(new java.awt.Color(49, 61, 72));
        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Type:");

        lblnameopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblnameopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblnameopd1.setForeground(new java.awt.Color(255, 255, 255));
        lblnameopd1.setText("Name:*");

        txtpname.setBackground(new java.awt.Color(49, 61, 72));
        txtpname.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtpname.setForeground(new java.awt.Color(255, 255, 255));
        txtpname.setMaximumSize(null);
        txtpname.setOpaque(false);

        lblgenderopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblgenderopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblgenderopd1.setForeground(new java.awt.Color(255, 255, 255));
        lblgenderopd1.setText("Gender:*");

        cmbgender.setBackground(new java.awt.Color(49, 61, 72));
        cmbgender.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbgender.setForeground(new java.awt.Color(255, 255, 255));
        cmbgender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Other" }));
        cmbgender.setMaximumSize(null);
        cmbgender.setOpaque(false);

        labelward3.setBackground(new java.awt.Color(49, 61, 72));
        labelward3.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        labelward3.setForeground(new java.awt.Color(255, 255, 255));
        labelward3.setText("UID.:");

        txtuid.setBackground(new java.awt.Color(49, 61, 72));
        txtuid.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtuid.setForeground(new java.awt.Color(255, 255, 255));
        txtuid.setOpaque(false);
        txtuid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtuidKeyTyped(evt);
            }
        });

        txtcontact.setBackground(new java.awt.Color(49, 61, 72));
        txtcontact.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtcontact.setForeground(new java.awt.Color(255, 255, 255));
        txtcontact.setOpaque(false);
        txtcontact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcontactKeyTyped(evt);
            }
        });

        txtoccupation.setBackground(new java.awt.Color(49, 61, 72));
        txtoccupation.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtoccupation.setForeground(new java.awt.Color(255, 255, 255));
        txtoccupation.setOpaque(false);

        jLabel16.setBackground(new java.awt.Color(49, 61, 72));
        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Occupation:");

        jLabel15.setBackground(new java.awt.Color(49, 61, 72));
        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Mobile No:");

        txtreligion.setBackground(new java.awt.Color(49, 61, 72));
        txtreligion.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtreligion.setForeground(new java.awt.Color(255, 255, 255));
        txtreligion.setMaximumSize(null);
        txtreligion.setOpaque(false);

        lblreligionopd1.setBackground(new java.awt.Color(49, 61, 72));
        lblreligionopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblreligionopd1.setForeground(new java.awt.Color(255, 255, 255));
        lblreligionopd1.setText("Religion:");

        jLabel13.setBackground(new java.awt.Color(49, 61, 72));
        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Blood Group:");

        cmbbgroup.setBackground(new java.awt.Color(49, 61, 72));
        cmbbgroup.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        cmbbgroup.setForeground(new java.awt.Color(255, 255, 255));
        cmbbgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "O +ve", "O -ve", "A +ve", "A -ve", "B +ve", "B -ve", "AB +ve", "AB -ve" }));
        cmbbgroup.setOpaque(false);

        txtaddress.setBackground(new java.awt.Color(49, 61, 72));
        txtaddress.setColumns(20);
        txtaddress.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtaddress.setForeground(new java.awt.Color(255, 255, 255));
        txtaddress.setLineWrap(true);
        txtaddress.setRows(2);
        txtaddress.setMaximumSize(null);
        jScrollPane1.setViewportView(txtaddress);

        lbladdressopd1.setBackground(new java.awt.Color(49, 61, 72));
        lbladdressopd1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lbladdressopd1.setForeground(new java.awt.Color(255, 255, 255));
        lbladdressopd1.setText("Address:");

        btnload.setBackground(new java.awt.Color(19, 166, 217));
        btnload.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnload.setForeground(new java.awt.Color(255, 255, 255));
        btnload.setText("Load");
        btnload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloadActionPerformed(evt);
            }
        });

        parsingarea.setBackground(new java.awt.Color(49, 61, 72));
        parsingarea.setColumns(20);
        parsingarea.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        parsingarea.setForeground(new java.awt.Color(255, 255, 255));
        parsingarea.setLineWrap(true);
        parsingarea.setRows(5);
        jScrollPane3.setViewportView(parsingarea);

        lblnotes.setBackground(new java.awt.Color(49, 61, 72));
        lblnotes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblnotes.setForeground(new java.awt.Color(255, 255, 255));
        lblnotes.setText("Notes:");

        txtnote.setBackground(new java.awt.Color(49, 61, 72));
        txtnote.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtnote.setForeground(new java.awt.Color(255, 255, 255));
        txtnote.setOpaque(false);

        lblfees.setBackground(new java.awt.Color(49, 61, 72));
        lblfees.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        lblfees.setForeground(new java.awt.Color(255, 255, 255));
        lblfees.setText("Fees:");
        lblfees.setToolTipText("");

        txtfees.setBackground(new java.awt.Color(49, 61, 72));
        txtfees.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtfees.setForeground(new java.awt.Color(255, 255, 255));
        txtfees.setText("200.00");
        txtfees.setOpaque(false);
        txtfees.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfeesKeyTyped(evt);
            }
        });

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator5.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator7.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator8.setPreferredSize(new java.awt.Dimension(50, 1));

        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator9.setPreferredSize(new java.awt.Dimension(50, 1));

        jLabel34.setBackground(new java.awt.Color(49, 61, 72));
        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Appointment ID:");

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblnameopd1)
                                    .addComponent(lblreligionopd1)
                                    .addComponent(lblopdreg1))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addGap(44, 44, 44)
                                            .addComponent(txtpname, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtregno)
                                                .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(21, 21, 21)
                                            .addComponent(labelward3)
                                            .addGap(10, 10, 10)
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                                .addComponent(txtuid))
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel34)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                                .addComponent(jSeparator1))))
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(100, 100, 100))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                                .addComponent(lblgenderopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbgender, javax.swing.GroupLayout.Alignment.TRAILING, 0, 120, Short.MAX_VALUE)
                                            .addComponent(txtreligion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cmbvtype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbldatetimeopd1)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15)
                                    .addComponent(lblageopd1))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                            .addGap(20, 20, 20)
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtoccupation, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                .addGroup(jPanel10Layout.createSequentialGroup()
                                                    .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jLabel220))
                                                .addComponent(jSeparator11)))
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jSeparator10)
                                                .addComponent(txtcontact, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                            .addComponent(txtdate))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txttime)
                                            .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))))
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(lblnotes)
                                    .addGap(90, 90, 90)
                                    .addComponent(txtnote, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(lbladdressopd1)
                                .addGap(67, 67, 67)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(lblfees)
                        .addGap(87, 87, 87)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator14)
                            .addComponent(txtfees))
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnload)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblopdreg1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtregno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelward3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtuid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblnameopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbvtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbldatetimeopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblgenderopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblageopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel220, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblreligionopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtreligion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtcontact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtoccupation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblnotes)
                            .addComponent(txtnote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(btnload))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtfees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblfees, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lbladdressopd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(120, 120, 120))
        );

        jPanel9.setBackground(new java.awt.Color(49, 61, 72));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Bright", 1, 18))); // NOI18N

        tabledisease.setBackground(new java.awt.Color(49, 61, 72));
        tabledisease.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        tabledisease.setForeground(new java.awt.Color(255, 255, 255));
        tabledisease.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabledisease.setGridColor(new java.awt.Color(49, 61, 72));
        tabledisease.getTableHeader().setReorderingAllowed(false);
        tabledisease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablediseaseMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabledisease);
        tabledisease.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btndeleterow.setBackground(new java.awt.Color(19, 166, 217));
        btndeleterow.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btndeleterow.setForeground(new java.awt.Color(255, 255, 255));
        btndeleterow.setText("Delete Row");
        btndeleterow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleterowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btndeleterow)
                .addGap(183, 183, 183))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btndeleterow)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnpreset.setBackground(new java.awt.Color(19, 166, 217));
        btnpreset.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnpreset.setForeground(new java.awt.Color(255, 255, 255));
        btnpreset.setText("Reset");
        btnpreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpresetActionPerformed(evt);
            }
        });

        btnprintp.setBackground(new java.awt.Color(19, 166, 217));
        btnprintp.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnprintp.setForeground(new java.awt.Color(255, 255, 255));
        btnprintp.setText("Print");
        btnprintp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintpActionPerformed(evt);
            }
        });

        btnsave.setBackground(new java.awt.Color(19, 166, 217));
        btnsave.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Save Details");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        jLabel145.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("VPS TecHub Pvt. Ltd.");
        jLabel145.setPreferredSize(new java.awt.Dimension(173, 50));

        javax.swing.GroupLayout OPDNewLayout = new javax.swing.GroupLayout(OPDNew);
        OPDNew.setLayout(OPDNewLayout);
        OPDNewLayout.setHorizontalGroup(
            OPDNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OPDNewLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(OPDNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OPDNewLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btnsave)
                        .addGap(50, 50, 50)
                        .addComponent(btnpreset)
                        .addGap(50, 50, 50)
                        .addComponent(btnprintp))
                    .addGroup(OPDNewLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(OPDNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(OPDNewLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        OPDNewLayout.setVerticalGroup(
            OPDNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OPDNewLayout.createSequentialGroup()
                .addGroup(OPDNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OPDNewLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(OPDNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnprintp)
                            .addComponent(btnpreset)
                            .addComponent(btnsave))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AddPatientLayout = new javax.swing.GroupLayout(AddPatient);
        AddPatient.setLayout(AddPatientLayout);
        AddPatientLayout.setHorizontalGroup(
            AddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddPatientLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(OPDNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        AddPatientLayout.setVerticalGroup(
            AddPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddPatientLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(OPDNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        jTabbedPane1.addTab("New Patient", AddPatient);

        AllPatient.setBackground(new java.awt.Color(49, 61, 72));
        AllPatient.setPreferredSize(new java.awt.Dimension(0, 0));
        AllPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AllPatientMouseClicked(evt);
            }
        });

        tblallpatients.setBackground(new java.awt.Color(49, 61, 72));
        tblallpatients.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        tblallpatients.setForeground(new java.awt.Color(255, 255, 255));
        tblallpatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblallpatients);

        jLabel149.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(255, 255, 255));
        jLabel149.setText("VPS TecHub Pvt. Ltd.");
        jLabel149.setPreferredSize(new java.awt.Dimension(173, 50));

        javax.swing.GroupLayout AllPatientLayout = new javax.swing.GroupLayout(AllPatient);
        AllPatient.setLayout(AllPatientLayout);
        AllPatientLayout.setHorizontalGroup(
            AllPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AllPatientLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(525, 525, 525))
            .addGroup(AllPatientLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        AllPatientLayout.setVerticalGroup(
            AllPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AllPatientLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("All Patients", AllPatient);

        jTabbedPane3.addTab("Patient", jTabbedPane1);

        PrintBill.setBackground(new java.awt.Color(49, 61, 72));
        PrintBill.setForeground(new java.awt.Color(255, 255, 255));
        PrintBill.setPreferredSize(new java.awt.Dimension(1360, 670));
        PrintBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PrintBillMouseClicked(evt);
            }
        });

        printpanel1bill.setBackground(new java.awt.Color(255, 255, 255));
        printpanel1bill.setPreferredSize(new java.awt.Dimension(450, 650));

        jLabel65bill.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        jLabel65bill.setText("VPS TecHub Pvt. Ltd.");

        jLabel66bill.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        jLabel66bill.setText("Clinic Management System");

        jLabel77bill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/vpslogo.png"))); // NOI18N
        jLabel77bill.setText("jLabel77");
        jLabel77bill.setPreferredSize(new java.awt.Dimension(75, 75));

        jLabel168.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel168.setText("Name:");

        jLabel172.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel172.setText("Registration No:");

        jLabel173.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel173.setText("Bill No:");

        jLabel176.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel176.setText("Date:");

        lblbilldate.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblbilldate.setText("______");

        lblbillname.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblbillname.setText("___________");

        lblbillregno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblbillregno.setText("____");

        lblbillno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblbillno.setText("____");

        jLabel237.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel237.setText("Net Amount:");

        lblprintfees.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lblprintfees.setText("___________");

        jLabel61.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel61.setText("Authorized Signatory");

        tblbillprint.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        tblbillprint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblbillprint);

        javax.swing.GroupLayout printpanel1billLayout = new javax.swing.GroupLayout(printpanel1bill);
        printpanel1bill.setLayout(printpanel1billLayout);
        printpanel1billLayout.setHorizontalGroup(
            printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printpanel1billLayout.createSequentialGroup()
                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(printpanel1billLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(printpanel1billLayout.createSequentialGroup()
                                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel172)
                                    .addComponent(jLabel168))
                                .addGap(18, 18, 18)
                                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(printpanel1billLayout.createSequentialGroup()
                                        .addComponent(lblbillregno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel176)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblbilldate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel173)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblbillno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblbillname, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27))
                            .addGroup(printpanel1billLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel77bill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel65bill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printpanel1billLayout.createSequentialGroup()
                                        .addComponent(jLabel66bill, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29))))))
                    .addGroup(printpanel1billLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(printpanel1billLayout.createSequentialGroup()
                                .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblprintfees, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        printpanel1billLayout.setVerticalGroup(
            printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(printpanel1billLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(printpanel1billLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel65bill)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel66bill))
                    .addComponent(jLabel77bill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel176)
                    .addComponent(jLabel172)
                    .addComponent(lblbillregno)
                    .addComponent(lblbilldate)
                    .addComponent(jLabel173)
                    .addComponent(lblbillno))
                .addGap(20, 20, 20)
                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel168, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblbillname))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(printpanel1billLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(printpanel1billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblprintfees, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(printpanel1billLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel61)))
                .addContainerGap(236, Short.MAX_VALUE))
        );

        btnprint.setBackground(new java.awt.Color(19, 166, 217));
        btnprint.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnprint.setForeground(new java.awt.Color(255, 255, 255));
        btnprint.setText("Print");
        btnprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintActionPerformed(evt);
            }
        });

        jLabel109.setBackground(new java.awt.Color(49, 61, 72));
        jLabel109.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setText("Choose Name:");

        cmbchoosebillname.setBackground(new java.awt.Color(49, 61, 72));
        cmbchoosebillname.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbchoosebillname.setForeground(new java.awt.Color(255, 255, 255));
        cmbchoosebillname.setOpaque(false);
        cmbchoosebillname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbchoosebillnameActionPerformed(evt);
            }
        });

        jLabel146.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("VPS TecHub Pvt. Ltd.");
        jLabel146.setPreferredSize(new java.awt.Dimension(173, 50));

        jLabel147.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setText("VPS TecHub Pvt. Ltd.");
        jLabel147.setPreferredSize(new java.awt.Dimension(173, 50));

        jLabel110.setBackground(new java.awt.Color(49, 61, 72));
        jLabel110.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setText("Choose Date:");

        cmbchoosebilldate.setBackground(new java.awt.Color(49, 61, 72));
        cmbchoosebilldate.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbchoosebilldate.setForeground(new java.awt.Color(255, 255, 255));
        cmbchoosebilldate.setOpaque(false);
        cmbchoosebilldate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbchoosebilldateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PrintBillLayout = new javax.swing.GroupLayout(PrintBill);
        PrintBill.setLayout(PrintBillLayout);
        PrintBillLayout.setHorizontalGroup(
            PrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrintBillLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(PrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel110)
                    .addComponent(cmbchoosebillname, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbchoosebilldate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel109)
                    .addGroup(PrintBillLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnprint)))
                .addGap(150, 150, 150)
                .addComponent(printpanel1bill, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(499, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrintBillLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(587, 587, 587))
            .addGroup(PrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PrintBillLayout.createSequentialGroup()
                    .addGap(594, 594, 594)
                    .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(698, Short.MAX_VALUE)))
        );
        PrintBillLayout.setVerticalGroup(
            PrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrintBillLayout.createSequentialGroup()
                .addGroup(PrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrintBillLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel110)
                        .addGap(30, 30, 30)
                        .addComponent(cmbchoosebilldate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(jLabel109)
                        .addGap(30, 30, 30)
                        .addComponent(cmbchoosebillname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnprint))
                    .addGroup(PrintBillLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(printpanel1bill, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PrintBillLayout.createSequentialGroup()
                    .addGap(346, 346, 346)
                    .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(344, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("Print Receipt", PrintBill);

        Statistics.setBackground(new java.awt.Color(49, 61, 72));
        Statistics.setForeground(new java.awt.Color(255, 255, 255));
        Statistics.setPreferredSize(new java.awt.Dimension(1366, 670));

        jPanel1.setBackground(new java.awt.Color(49, 61, 72));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ailments & Number of Patients", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 36), new java.awt.Color(19, 166, 217))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        cmbgen.setBackground(new java.awt.Color(49, 61, 72));
        cmbgen.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbgen.setForeground(new java.awt.Color(255, 255, 255));
        cmbgen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Male", "Female" }));
        cmbgen.setOpaque(false);
        cmbgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbgenActionPerformed(evt);
            }
        });

        cmbage.setBackground(new java.awt.Color(49, 61, 72));
        cmbage.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbage.setForeground(new java.awt.Color(255, 255, 255));
        cmbage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Under 5", "5-18", "19-60", "60+" }));
        cmbage.setOpaque(false);

        cmbdayselect.setBackground(new java.awt.Color(49, 61, 72));
        cmbdayselect.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbdayselect.setForeground(new java.awt.Color(255, 255, 255));
        cmbdayselect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Today", "Last 7 days", "Last 30 days", "Last 365 days" }));
        cmbdayselect.setOpaque(false);

        jButton3.setBackground(new java.awt.Color(19, 166, 217));
        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(49, 61, 72));
        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Gender:");

        jLabel24.setBackground(new java.awt.Color(49, 61, 72));
        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Age:");

        jLabel25.setBackground(new java.awt.Color(49, 61, 72));
        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Days:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbgen, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(150, 150, 150)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbdayselect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150)
                        .addComponent(jButton3))
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(150, 150, 150))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbdayselect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(cmbgen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel11.setBackground(new java.awt.Color(49, 61, 72));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Income", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 36), new java.awt.Color(19, 166, 217))); // NOI18N
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));

        cmbdayselect1.setBackground(new java.awt.Color(49, 61, 72));
        cmbdayselect1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        cmbdayselect1.setForeground(new java.awt.Color(255, 255, 255));
        cmbdayselect1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Today", "Last 7 days", "Last 30 days", "Last 365 days" }));
        cmbdayselect1.setOpaque(false);

        jButton5.setBackground(new java.awt.Color(19, 166, 217));
        jButton5.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Submit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        lblsfees.setBackground(new java.awt.Color(49, 61, 72));
        lblsfees.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblsfees.setForeground(new java.awt.Color(255, 255, 255));
        lblsfees.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(19, 166, 217), 2));

        jLabel33.setBackground(new java.awt.Color(49, 61, 72));
        jLabel33.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Days:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(cmbdayselect1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(200, 200, 200)
                        .addComponent(jButton5)
                        .addGap(200, 200, 200)
                        .addComponent(lblsfees, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblsfees, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbdayselect1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))))
                .addGap(30, 30, 30))
        );

        jLabel148.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setText("VPS TecHub Pvt. Ltd.");
        jLabel148.setPreferredSize(new java.awt.Dimension(173, 50));

        javax.swing.GroupLayout StatisticsLayout = new javax.swing.GroupLayout(Statistics);
        Statistics.setLayout(StatisticsLayout);
        StatisticsLayout.setHorizontalGroup(
            StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatisticsLayout.createSequentialGroup()
                .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StatisticsLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(StatisticsLayout.createSequentialGroup()
                        .addGap(560, 560, 560)
                        .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        StatisticsLayout.setVerticalGroup(
            StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatisticsLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        jTabbedPane3.addTab("Statistics", Statistics);

        jPanel6.setBackground(new java.awt.Color(49, 61, 72));

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Enter Patient's Name or ID:");

        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField3.setOpaque(false);

        jTable2.setBackground(new java.awt.Color(49, 61, 72));
        jTable2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jTable2.setForeground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setGridColor(new java.awt.Color(49, 61, 72));
        jScrollPane9.setViewportView(jTable2);

        jButton2.setBackground(new java.awt.Color(19, 166, 217));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(363, 363, 363)
                .addComponent(jLabel30)
                .addGap(47, 47, 47)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(280, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("History", jPanel6);

        getContentPane().add(jTabbedPane3);
        jTabbedPane3.setBounds(10, 50, 1350, 650);

        jPanel5.setBackground(new java.awt.Color(19, 166, 217));

        jLabel9.setBackground(new java.awt.Color(49, 61, 72));
        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("VPS TecHub Pvt Ltd Clinic Management System");

        jButton4.setBackground(new java.awt.Color(19, 166, 217));
        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Admin Area");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnhomelogout.setBackground(new java.awt.Color(19, 166, 217));
        btnhomelogout.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnhomelogout.setForeground(new java.awt.Color(255, 255, 255));
        btnhomelogout.setText("Logout");
        btnhomelogout.setNextFocusableComponent(txtsearch);
        btnhomelogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhomelogoutActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/logoicon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(30, 30, 30)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 484, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(50, 50, 50)
                .addComponent(btnhomelogout)
                .addGap(50, 50, 50))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(btnhomelogout)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel5);
        jPanel5.setBounds(10, 10, 1280, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/Close Window_48px_3.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(1300, 10, 50, 40);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void radiobtn1()
    {
        ArrayList<String> list2 = new ArrayList<>();
        String m = "MORNING";
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
        String fe= "EVENING";
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
    }
    
    public void PrintFrameToPDF(JPanel panel,String no,String name)  
    {
    try 
    {
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
    catch(DocumentException | FileNotFoundException e)  
    {
        System.out.println(e);
    }
} 
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        fetch();
        getsearchfocus();
        loaddate();
        fillappointment();
        loadtoday();
        loadaid();
        loadprogressbar();
        loadappid();
        fillstats();
        setnull();
        //loadregno();
        loadbilldate();
        getregno();
    }//GEN-LAST:event_formWindowOpened
    public void setnull()
    {
        cmbvtype.setSelectedIndex(-1);
        cmbgender.setSelectedIndex(-1);
        cmbbgroup.setSelectedIndex(-1);
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
        cmbchoosebillname.setModel(new DefaultComboBoxModel(list.toArray()));
    }
           
    
    public void getsearchfocus()
    {
        txtsearch.requestFocus();
    }
    
    public void fillstats()
    {
        int count1 = 0,count2 = 0,count3 = 0;
        float per1,per2,per3;
        //jTextArea1.setText(null);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        String tom = dateFormat.format(cal.getTime());
        
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, +2);
        String tomplus1 = dateFormat1.format(cal1.getTime());
        
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, +3);
        String tomplus2 = dateFormat2.format(cal2.getTime());
        try
        {
            pst = con.prepareStatement("select count(*) from appointment where appdate = ?");
            pst.setString(1, tom);
            rs = pst.executeQuery();
            while(rs.next())
            {
                count1 = Integer.parseInt(rs.getString("count(*)"));
            }
            
        }
        catch(NumberFormatException | SQLException e)
        {
            
        }
        try
        {
            pst = con.prepareStatement("select count(*) from appointment where appdate = ?");
            pst.setString(1, tomplus1);
            rs = pst.executeQuery();
            while(rs.next())
            {
                count1 = Integer.parseInt(rs.getString("count(*)"));
            }
            
        }
        catch(NumberFormatException | SQLException e)
        {
            
        }
        try
        {
            pst = con.prepareStatement("select count(*) from appointment where appdate = ?");
            pst.setString(1, tomplus2);
            rs = pst.executeQuery();
            while(rs.next())
            {
                count1 = Integer.parseInt(rs.getString("count(*)"));
            }
            
        }
        catch(NumberFormatException | SQLException e)
        {
            
        }
        per1 = (count1 * 100 )/ max;
        per2 = (count2 * 100) /max;
        per3 = (count3 * 100) /max;
        if(per1>50)
        {
            jLabel26.setForeground(Color.red);
        }
        if(per2>50)
        {
            jLabel31.setForeground(Color.red);
        }
        if(per3>50)
        {
            jLabel32.setForeground(Color.red);
        }
        jLabel26.setText("Appointment Booking for tomorrow "+tom+" is "+per1+"% booked.\n");
        jLabel31.setText("Appointment Booking for day "+tomplus1+" is "+per2+"% booked.\n");
        jLabel32.setText("Appointment Booking for day "+tomplus2+" is "+per3+"% booked.\n");
        
//        jTextArea1.append("Appointment Booking for tomorrow "+tom+" is "+per1+"% booked.\n");
//        jTextArea1.append("\n");
//        jTextArea1.append("Appointment Booking for day "+tomplus1+" is "+per2+"% booked.\n");
//        jTextArea1.append("\n");
//        jTextArea1.append("Appointment Booking for day "+tomplus2+" is "+per3+"% booked.\n");
    }
    
    public void fetch()
    {
        try 
        {
            pst = con.prepareStatement("select count(*) from patient where adate = CURDATE()");
            rs = pst.executeQuery();
            while(rs.next())
            {
                txtnewpatient.setText(Integer.toString(rs.getInt("count(*)")));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HomeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try 
        {
            pst = con.prepareStatement("select count(*) from appointment where appdate = CURDATE() and checked =?");
            pst.setInt(1,0);
            rs = pst.executeQuery();
            while(rs.next())
            {
                txtappointment.setText(Integer.toString(rs.getInt("count(*)")));
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(HomeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane3MouseClicked
        parsingarea.requestFocus();
        clearsearch();
        fetch();
        loaddeldis();
        fillallpatient();
        fillappointment();
        loadtoday();
        loadprogressbar();
        loadappid();
        //loadregno();
        loadbilldate();
        getregno();
        jTextField3.requestFocus();
    }//GEN-LAST:event_jTabbedPane3MouseClicked
    
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
            
    public void loadprogressbar()
    {
        int min=0,today =0;
        float percent=0;
        jProgressBar1.setStringPainted(true);
        try
        {
            pst = con.prepareStatement("select count(*) from times");
            rs = pst.executeQuery();
            while(rs.next())
            {
                max = rs.getInt("count(*)");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error at first catch(): "+e);
        }
        try
        {
            pst = con.prepareStatement("select count(*) from appointment where appdate = CURDATE()");
            rs = pst.executeQuery();
            while(rs.next())
            {
                today = rs.getInt("count(*)");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error at first catch(): "+e);
        }
        jProgressBar1.setMinimum(min);
        jProgressBar1.setMaximum(max);
        percent = (today * 100)/max;
        jProgressBar1.setString(Float.toString(percent)+ "% FULL");
        jProgressBar1.setValue(today);
        jLabel5.setText(Integer.toString(min));
        jLabel27.setText(Integer.toString(max));
        if(percent > 50)
        {
            jProgressBar1.setForeground(Color.red);
            jLabel15.setForeground(Color.red);
            jLabel27.setForeground(Color.red);
        }
    }
    
    public void loadaid()
    {
        ArrayList<String> list = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select aid from appointment where appdate >= CURDATE() "
                    + "and checked = 0");
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
    
    public void loadtoday()
    {
        try
        {
            pst = con.prepareStatement("select name as Name, time_slot as Time, type as Type from appointment a, times t "
                    + "where a.appdate = CURDATE() and a.checked=0 and a.time_slot = t.slots order by t.tid ASC");
            rs = pst.executeQuery();
            tbltom.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            System.out.println("Error at loadtoday: "+e);
        }
    }
    public void fillappointment()
    {
        try
        {
            pst = con.prepareStatement("select a.aid as Appointment_Number, a.name as Name, "
                    + "a.type as Type from appointment a,times t "
                    + "where a.appdate = CURDATE() and a.checked=0 and a.time_slot = t.slots "
                    + "order by t.tid ASC limit 5");
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            System.out.println("Error at fillappointment: "+e);
        }
    }
    
    public void loaddeldis()
    {
        ArrayList<String> list2 = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select name from diseases");
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
        //cmbdisease.setSelectedIndex(-1);
    }
    
    public void loadmedicine()
    {
        try
        {
            ArrayList<String> list = new ArrayList<>();
            pst = con.prepareStatement("select pname from prescriptions");
            rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(rs.getString("pname"));
            }       
            cmbmedicine.setModel(new DefaultComboBoxModel(list.toArray()));
        }
        catch(SQLException e)
        {
            System.out.println("Error at loadmedicine: "+e);
        }
    }
    
    private void PrintBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrintBillMouseClicked
        fetch();
    }//GEN-LAST:event_PrintBillMouseClicked

    private void cmbchoosebillnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbchoosebillnameActionPerformed
        // Set the print bill details
        String bname = cmbchoosebillname.getSelectedItem().toString();
        String bdate = cmbchoosebilldate.getSelectedItem().toString();
        String id = "",billdate ="",billno = "",billname = "",bfees = "";
        try
        {
            pst = con.prepareStatement("select b.id,b.billdate,b.billno,b.fees,p.name from bill b,patient p "
                    + "where b.id=p.regno and p.name=? and b.billdate = ?");
            pst.setString(1, bname);
            pst.setString(2, bdate);
            rs =pst.executeQuery();
            while(rs.next())
            {
                id = rs.getString("b.id");
                billdate = rs.getString("b.billdate");
                billno = rs.getString("b.billno");
                billname = rs.getString("p.name");
                bfees = rs.getString("b.fees");
            }
            lblbillregno.setText(id);
            lblbilldate.setText(billdate);
            lblbillno.setText(billno);
            lblbillname.setText(billname);
            lblprintfees.setText(bfees);
            //billdate = "'"+billdate+"'";
            System.out.println("Regid : "+id);
            System.out.println("Date "+billdate);
            pst =con.prepareStatement("select disease as Disease,medicine as Medicine,dose as Dose,frequency as Frequency,"
                    + "instructions as Instructions,days as Days from diagnosis where regid = ? and ddate = ?");
            pst.setString(1,id);
            pst.setString(2,billdate);
            rs = pst.executeQuery();
  //          int total = rs.getRow();
//            System.out.println("The total rows are: "+total);
            tblbillprint.setModel(DbUtils.resultSetToTableModel(rs));
            tblbillprint.getColumnModel().getColumn(2).setPreferredWidth(15);
            tblbillprint.getColumnModel().getColumn(5).setPreferredWidth(15);
            tblbillprint.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblbillprint.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblbillprint.getColumnModel().getColumn(0).setPreferredWidth(100);
            
        }
        catch(SQLException e)
        {
            System.out.println("Error at cmbchoosebillnameActionPerformed: "+e);
        }   
    }//GEN-LAST:event_cmbchoosebillnameActionPerformed

    public void fillallpatient()
    {
        try
        {
            pst = con.prepareStatement("select regno as Registration_Number, name as Name, address as Address, agey as Age, "
                    + "gender as Gender, adate as Date, bgroup as Blood_Group, occupation as Occupation from patient");
            rs = pst.executeQuery();
            tblallpatients.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            System.out.println("Error at fillallpatient: "+e);
        }
    }
    
    public void loadbilldate()
    {
        ArrayList<String> list2 = new ArrayList<>();
        try
        {
            rs = conn.getbilldate();
            while(rs.next())
            {
                list2.add(rs.getString("billdate"));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Can't fetch bill date in combo box: "+e);
        }
        cmbchoosebilldate.setModel(new DefaultComboBoxModel(list2.toArray()));
    }
    
    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        if(cmbchoosebilldate.getSelectedIndex()==-1 || cmbchoosebillname.getSelectedIndex()==-1)
        {
            JOptionPane.showMessageDialog(null, "Select both Date and Patient's Name :(");
        }
        else
        {
            btnprint.addActionListener(new PrintGUIWindow(printpanel1bill));
            PrintFrameToPDF(printpanel1bill, lblbillno.getText(),lblbillname.getText() );
        }
    }//GEN-LAST:event_btnprintActionPerformed

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
                pst = con.prepareStatement("update appointment set checked=? where name = ? and appdate=CURDATE()");
                pst.setInt(1,1);
                pst.setString(2, txtpname.getText());
                pst.executeUpdate();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(HomeGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloadActionPerformed
        if(parsingarea.getText().length()==0)
        {
            JOptionPane.showMessageDialog(null, "Scanned the Aadhar Card to load personal details");
        }
        else
        {
            String test=parsingarea.getText();
            int l = test.length();
            String name="",gender="",yob="",age="",address="",uid="";
            char[] chr = test.toCharArray();
            int i;
            for(i=0;i<l;i++)
            {
                if(chr[i]=='u' && chr[i+1]=='i' && chr[i+2]=='d' )
                {
                    int j=i+5;
                    while(chr[j]!='\"')
                    {
                        uid = uid + Character.toString(chr[j++]);
                    }
                }
                if(chr[i]==' ' && chr[i+1]=='n' && chr[i+2]=='a' && chr[i+3]=='m' && chr[i+4]=='e')
                {
                    int j=i+7;
                    while(chr[j]!='\"')
                    {
                        name = name + Character.toString(chr[j++]);
                    }
                }
                if(chr[i]=='g' && chr[i+1]=='e' && chr[i+2]=='n' && chr[i+3]=='d' && chr[i+4]=='e' && chr[i+5]=='r')
                {
                    int j=i+8;
                    while(chr[j]!='\"')
                    {
                        gender = gender + Character.toString(chr[j++]);
                    }
                }
                if(chr[i]=='y' && chr[i+1]=='o' && chr[i+2]=='b' )
                {
                    int j=i+5;
                    while(chr[j]!='\"')
                    {
                        yob = yob + Character.toString(chr[j++]);
                    }
                }
                if(chr[i]=='h' && chr[i+1]=='o' && chr[i+2]=='u' && chr[i+3]=='s' && chr[i+4]=='e')
                {
                    int j=i+7;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ", ";
                }

                if(chr[i]=='s' && chr[i+1]=='t' && chr[i+2]=='r' && chr[i+3]=='e' && chr[i+4]=='e' && chr[i+5]=='t')
                {
                    int j=i+8;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ", ";
                }
                
                if(chr[i]=='l' && chr[i+1]=='o' && chr[i+2]=='c')
                {
                    int j=i+5;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ", ";
                }

                if(chr[i]=='v' && chr[i+1]=='t' && chr[i+2]=='c')
                {
                    int j=i+5;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ", ";
                }

                if(chr[i]=='d' && chr[i+1]=='i' && chr[i+2]=='s' && chr[i+3]=='t')
                {
                    int j=i+6;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ", ";
                }

                if(chr[i]=='s' && chr[i+1]=='t' && chr[i+2]=='a' && chr[i+3]=='t' && chr[i+4]=='3')
                {
                    int j=i+7;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ", ";
                }

                if(chr[i]=='p' && chr[i+1]=='c')
                {
                    int j=i+4;
                    while(chr[j]!='\"')
                    {
                        address = address + Character.toString(chr[j++]);
                    }
                    address = address + ". ";
                }
            }
            Calendar cal = Calendar.getInstance();
            int yr = cal.get(Calendar.YEAR);
            int yb = Integer.parseInt(yob);
            yr = yr-yb;
            age = Integer.toString(yr);
            System.out.println(name);
            System.out.println(gender);
            System.out.println(age);
            System.out.println(address);
            txtpname.setText(name);
            txtage.setText(age);
            txtaddress.setText(address);
            txtuid.setText(uid);
            switch (gender) 
            {
                case "M":
                    cmbgender.setSelectedIndex(0);
                    break;
                case "F":
                    cmbgender.setSelectedIndex(1);
                    break;
            }
        }
    }//GEN-LAST:event_btnloadActionPerformed

    private void txtageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtageKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtageKeyTyped

    private void btnOPDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOPDActionPerformed
        // Go to OPD
        jTabbedPane3.setSelectedIndex(2);
        txtpname.setText(name);
        txtaddress.setText(address);
        txtregno.setText(regno);
        txtuid.setText(uid);
        txtage.setText(Integer.toString(age));
        cmbvtype.setSelectedIndex(1);
        if(null!=bgroup)
        switch (bgroup) 
        {
            case "O +ve":
                cmbbgroup.setSelectedIndex(0);
                break;
            case "O -ve":
                cmbbgroup.setSelectedIndex(1);
                break;
            case "A +ve":
                cmbbgroup.setSelectedIndex(2);
                break;
            case "A -ve":
                cmbbgroup.setSelectedIndex(3);
                break;
            case "B +ve":
                cmbbgroup.setSelectedIndex(4);
                break;
            case "B -ve":
                cmbbgroup.setSelectedIndex(5);
                break;
            case "AB +ve":
                cmbbgroup.setSelectedIndex(6);
                break;
            case "AB -ve":
                cmbbgroup.setSelectedIndex(7);
                break;
            default:
                break;
        }
        switch(gender) 
        {
            case "Male":
                cmbgender.setSelectedIndex(0);
                break;
            case "Female":
                cmbgender.setSelectedIndex(1);
                break;
        }
    }//GEN-LAST:event_btnOPDActionPerformed

    private void tblpatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpatientMouseClicked
        //Redirect to admission page
        int row = tblpatient.getSelectedRow();
        String regisno = (tblpatient.getModel().getValueAt(row,0).toString());
        System.out.println("Row: "+ row +" and Reg No: "+regisno);
        System.out.println(regisno);

        try
        {
            rs = conn.fillsearchtable(regisno);
            while(rs.next())
            {
                name = rs.getString("name");
                address = rs.getString("address");
                regno = rs.getString("regno");
                gender = rs.getString("gender");
                uid = rs.getString("uid");
                sdate = rs.getString("adate");
                btnOPD.setVisible(true);
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error at table click event: "+e);
        }

    }//GEN-LAST:event_tblpatientMouseClicked

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tblpatient.getModel();
        dtm.setRowCount(0);
        String search = txtsearch.getText();
        if(search.length() == 0)
        {
            JOptionPane.showMessageDialog(null,"Please input search string :(");
            txtsearch.requestFocus();
        }
        else
        {
            try
            {
                rs = conn.searchpatient(search);
                if(!rs.next())
                {
                    JOptionPane.showMessageDialog(null,"No records found, please enhance your search.");
                }
                else
                { 
                    age = rs.getInt("agey");
                    gender = rs.getString("gender");
                    bgroup = rs.getString("bgroup");
                    model.addRow(new Object [] {rs.getString("regno"),rs.getString("name"),rs.getString("address"),rs.getString("adate"),rs.getString("atime")});
                }
            }
            catch(SQLException e)
            {
                System.out.print(e);
            }
        }
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnhomelogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhomelogoutActionPerformed
        logout();
    }//GEN-LAST:event_btnhomelogoutActionPerformed

    public void getregno()
    {
        String opdno = null;
        try 
        {
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
            System.out.println("Error at getregno:"+ex);
        }
    }
    
    private void txtregnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtregnoKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtregnoKeyTyped

    private void txtnewpatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnewpatientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnewpatientActionPerformed

    private void OPDNewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OPDNewMouseClicked
        parsingarea.requestFocus();
        loaddeldis();
    }//GEN-LAST:event_OPDNewMouseClicked

    public boolean validation()
    {
        java.util.Date d = jDateChooser1.getDate();
        String name = txtname.getText();
        String utype = cmbtype.getSelectedItem().toString();
        if (name.length() !=0) 
        {
            if (utype.length() !=0) 
            {
                if (d != null) 
                {
                    if(jRadioButton1.isSelected()==true || jRadioButton2.isSelected()==true)
                    {
                        return true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Choose Time Slot");
                        return false;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Choose date");
                    return false;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Select Appointment Type");
                return false;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Enter Name");
            return false;
        } 
    }
        
    private void btnscheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnscheduleActionPerformed
        if(validation())
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //reading date selected by user
            String startDateString = dateFormat.format(jDateChooser1.getDate());
            String name = txtname.getText();
            String utype = cmbtype.getSelectedItem().toString();
            String radioText = "";
            String notes = txtnotes.getText();
            //reading timeslot selected by user
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
                    pst = con.prepareStatement("insert into appointment values (?,?,?,?,?,?,?,?)");
                    pst.setInt(1,0);                    //aid
                    pst.setString(2, name);             //name
                    pst.setString(3, utype);            //type
                    pst.setString(4, startDateString);  //appdate
                    pst.setString(5, radioText);        //time morn/eve
                    pst.setString(6, timeslot);         //time_slot
                    pst.setString(7, notes);            //notes
                    pst.setInt(8, 0);                   //checked
                    int i=pst.executeUpdate();          
                    if(i >0)
                    {
                        fillappointment();
                        loadaid();
                        loadappid();
                        JOptionPane.showMessageDialog(null, "Appointment Scheduled successfully.");
                        resetapp();
                        loadprogressbar();
                        fetch();
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
                        fetch();
                    }
                }
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnscheduleActionPerformed

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
            if(a>0)
            {
                cmbtype.setSelectedIndex(1);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error:" +e);
        }
    }//GEN-LAST:event_txtnameKeyTyped

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        resetapp();
    }//GEN-LAST:event_btnresetActionPerformed

    private void btnadddiseaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadddiseaseActionPerformed
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

    private void btndeleterowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleterowActionPerformed
        if(tabledisease.getSelectedRow() != -1)
        {
            model1.removeRow(tabledisease.getSelectedRow());
        }
    }//GEN-LAST:event_btndeleterowActionPerformed

    private void tablediseaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablediseaseMouseClicked
        btndeleterow.setVisible(true);
    }//GEN-LAST:event_tablediseaseMouseClicked

    private void btnprintpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintpActionPerformed
        PrintFrameToPDF(printpanel1bill, lblbillno.getText(),lblbillname.getText());
    }//GEN-LAST:event_btnprintpActionPerformed

    private void PHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PHomeMouseClicked
        fetch();
    }//GEN-LAST:event_PHomeMouseClicked

    private void AddPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddPatientMouseClicked
        fetch();
    }//GEN-LAST:event_AddPatientMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new AdminPanel(suser,stype,Integer.toString(sessionid)).setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cmbdiseaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdiseaseActionPerformed
        cmbmedicine.removeAllItems();
        String dept = cmbdisease.getSelectedItem().toString();
        ArrayList<String> list = new ArrayList<>();
        try
        {
            pst = con.prepareStatement("select pname from prescriptions where purpose = ?");
            pst.setString(1, dept);
            rs = pst.executeQuery();
            while(rs.next())
            {
                list.add(rs.getString("pname"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error at cmbdiseaseActionPerformed:"+ex);
        }
        cmbmedicine.setModel(new DefaultComboBoxModel(list.toArray()));
        cmbmedicine.setSelectedIndex(-1);
    }//GEN-LAST:event_cmbdiseaseActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        logout();
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        fillallpatient();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void AllPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllPatientMouseClicked
        // TODO add your handling code here:
        fillallpatient();
    }//GEN-LAST:event_AllPatientMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int day = cmbdayselect1.getSelectedIndex();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String date7 = dateFormat.format(cal.getTime());
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -30);
        String date30 = dateFormat.format(cal.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -365);
        String date365 = dateFormat.format(cal.getTime());
        String que = null;
        if(day ==0)
        {
            que = "select sum(fees) as Total_Fees from bill";
        }
        if(day ==1)
        {
            que = "select sum(fees) as Total_Fees from bill where billdate = CURRENT_DATE";
        }
        if(day ==2)
        {
            que = "select sum(fees) as Total_Fees from bill where billdate between '"+date7+"' and CURRENT_DATE";
        }
        if(day ==3)
        {
            que = "select sum(fees) as Total_Fees from bill where billdate between '"+date30+"' and CURRENT_DATE";
        }
        if(day ==4)
        {
            que = "select sum(fees) as Total_Fees from bill where billdate between '"+date365+"' and CURRENT_DATE";
        }
        try
        {
            pst = con.prepareStatement(que);
            rs = pst.executeQuery();
            while(rs.next())
            {
                lblsfees.setText("Rs. " + Integer.toString(rs.getInt("Total_Fees")));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int daydetails = cmbdayselect.getSelectedIndex();
        int agedetails = cmbage.getSelectedIndex();
        int genderdetails = cmbgen.getSelectedIndex();
        String query = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String date7 = dateFormat.format(cal.getTime());
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -30);
        String date30 = dateFormat.format(cal.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -365);
        String date365 = dateFormat.format(cal.getTime());
        try
        {
            //For Gender = all, Age = all and variation in day i.e. today,last 7 days, last 30 days, last 1 year
            if(genderdetails ==0 && agedetails==0 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis group by disease";
            }
            if(genderdetails==0 && agedetails==0 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where ddate = CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==0 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==0 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==0 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For gender =All , Age = Under 5 and variation in day i.e. today,last 7 days, last 30 days, last 1 year
            if(genderdetails==0 && agedetails==1 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where agey <5) "
                + "group by disease";
            }
            if(genderdetails==0 && agedetails==1 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where having regid IN "
                + "(select regno from patient where agey <5) and ddate =CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==1 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where agey <5) "
                + "and ddate between '"+date7+"'CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==1 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey <5) "
                + "and ddate between '"+date30+"'CURRENT_DATE() group by disease ";
            }
            if(genderdetails==0 && agedetails==1 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey <5) "
                + "and ddate between '"+date365+"'CURRENT_DATE() group by disease";
            }
            //For Gender = all, Age =5-18
            if(genderdetails==0 && agedetails==2 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 5 and 18) group by disease";
            }
            if(genderdetails==0 && agedetails==2 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where agey between 5 and 18) and "
                + "ddate = CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==2 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 5 and 18) "
                + "and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==2 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 5 and 18) "
                + "and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==2 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 5 and 18) "
                + "and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }
            //For gender =All,Age = 19-60
            if(genderdetails==0 && agedetails==3 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 19 and 60) group by disease";
            }
            if(genderdetails==0 && agedetails==3 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 19 and 60) "
                + "and ddate =CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==3 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 19 and 60) "
                + "and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==3 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 19 and 60) "
                + "and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==3 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey between 19 and 60) "
                + "and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }
            //For gender = All, Age = 60+
            if(genderdetails==0 && agedetails==4 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey >60) group by disease";
            }
            if(genderdetails==0 && agedetails==4 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey >60) "
                + "and ddate =CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==4 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey >60) "
                + "and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==4 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey >60) "
                + "and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }
            if(genderdetails==0 && agedetails==4 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where regid IN (select regno from patient where agey >60) "
                + "and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male, Age = all and variation in day i.e. today,last 7 days, last 30 days, last 1 year
            if(genderdetails==1 && agedetails==0 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male') group by disease";
            }

            if(genderdetails ==1 && agedetails==0 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male') and ddate = CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==0 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male') and ddate between '"+date7+"' "
                + "and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==0 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Male')"
                + "and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==0 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male') and ddate between '"+date365+"' "
                + "and CURRENT_DATE() group by disease";
            }

            //For Gender = Male , Age = Under 5 and variation in day i.e. today,last 7 days, last 30 days, last 1 year
            if(genderdetails ==1 && agedetails==1 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey <5) group by disease";
            }

            if(genderdetails ==1 && agedetails==1 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey <5) and "
                + "ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==1 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey <5) and ddate "
                + "between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==1 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN "
                + "(select regno from patient where gender='Male' and agey <5) and ddate between '"+date30+"' "
                + "and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==1 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey <5) and ddate between '"+date365+"' "
                + "and CURRENT_DATE() group by disease";
            }

            //For Gender = Male, Age =5-18
            if(genderdetails ==1 && agedetails==2 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 5 and 18) group by disease";
            }

            if(genderdetails ==1 && agedetails==2 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 5 and 18) and "
                + "ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==2 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 5 and 18) and "
                + "ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==2 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 5 and 18) and ddate "
                + "between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==2 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN "
                + "(select regno from patient where gender='Male' and agey between 5 and 18) and "
                + "ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male,Age = 19-60
            if(genderdetails ==1 && agedetails==3 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN "
                + "(select regno from patient where gender='Male' and agey between 19 and 60) group by disease";
            }

            if(genderdetails ==1 && agedetails==3 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 19 and 60) and "
                + "ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==3 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 19 and 60) and "
                + "ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==3 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 19 and 60) and "
                + "ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==3 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey between 19 and 60) and "
                + "ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male, Age = 60+
            if(genderdetails ==1 && agedetails==4 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN "
                + "(select regno from patient where gender='Male' and agey >60) group by disease";
            }

            if(genderdetails ==1 && agedetails==4 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey >60) "
                + "and ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==4 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey >60) and "
                + "ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==4 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey >60) and ddate "
                + "between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails ==1 && agedetails==4 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN "
                + "(select regno from patient where gender='Male' and agey >60) and ddate "
                + "between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male, Age = all and variation in day i.e. today,last 7 days, last 30 days, last 1 year
            if(genderdetails==2 && agedetails==0 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female') group by disease";
            }

            if(genderdetails==2 && agedetails==0 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female') and ddate = CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==0 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female') and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==0 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis "
                + "where ddate = '"+date30+"' and CURRENT_DATE() group by disease having regid IN (select regno from patient where gender='Female'";
            }

            if(genderdetails==2 && agedetails==0 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female') and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male , Age = Under 5 and variation in day i.e. today,last 7 days, last 30 days, last 1 year
            if(genderdetails==2 && agedetails==1 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey <5) group by disease";
            }

            if(genderdetails==2 && agedetails==1 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey <5) and ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==1 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey <5) and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==1 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN (select regno from patient where gender='Female' and agey <5) and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==1 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey <5) and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male, Age =5-18
            if(genderdetails==2 && agedetails==2 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 5 and 18) group by disease";
            }

            if(genderdetails==2 && agedetails==2 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 5 and 18) and ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==2 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 5 and 18) and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==2 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 5 and 18) and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==2 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN (select regno from patient where gender='Female' and agey between 5 and 18) and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male,Age = 19-60
            if(genderdetails==2 && agedetails==3 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN (select regno from patient where gender='Female' and agey between 19 and 60) group by disease";
            }

            if(genderdetails==2 && agedetails==3 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 19 and 60) and ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==3 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 19 and 60) and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==3 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 19 and 60) and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==3 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey between 19 and 60) and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            //For Gender = Male, Age = 60+
            if(genderdetails==2 && agedetails==4 && daydetails==0)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis  where regid IN (select regno from patient where gender='Female' and agey >60) group by disease";
            }

            if(genderdetails==2 && agedetails==4 && daydetails==1)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey >60) and ddate =CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==4 && daydetails==2)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey >60) and ddate between '"+date7+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==4 && daydetails==3)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey >60) and ddate between '"+date30+"' and CURRENT_DATE() group by disease";
            }

            if(genderdetails==2 && agedetails==4 && daydetails==4)
            {
                query = "select disease as Disease,count(disease) as Patient from diagnosis where regid IN (select regno from patient where gender='Female' and agey >60) and ddate between '"+date365+"' and CURRENT_DATE() group by disease";
            }

            JDBCCategoryDataset dataset = new JDBCCategoryDataset("jdbc:mysql://localhost:3306/cms",
                "com.mysql.jdbc.Driver","root", "");
            dataset.executeQuery(query);
            JFreeChart chart = ChartFactory.createBarChart("Ailment and Number of Patients", "Ailment", "Patient",
                dataset, PlotOrientation.VERTICAL, false, true, false);
            CategoryPlot CPlot = chart.getCategoryPlot();
            CPlot.setRangeGridlinePaint(Color.BLACK);
            ChartFrame chartframe = new ChartFrame("Bar Graph: Patient and their Ailments",chart);
            chartframe.setSize(1024,1024);
            chartframe.setVisible(true);
            chart.setBackgroundPaint(Color.decode("#313d48"));
            CPlot.setBackgroundAlpha(0.0f);
            CPlot.getRenderer().setBaseItemLabelPaint(Color.WHITE);
            CPlot.setRangeGridlinePaint(Color.white);
            CategoryPlot p = chart.getCategoryPlot(); 
            CategoryAxis Xaxis = p.getDomainAxis();
            ValueAxis Yaxis = p.getRangeAxis();
            Xaxis.setTickLabelFont(new java.awt.Font("Century Gothic", Font.BOLD, 10));
            Xaxis.setTickLabelPaint(Color.white);
            Yaxis.setTickLabelFont(new java.awt.Font("Century Gothic", Font.BOLD, 10));
            Yaxis.setTickLabelPaint(Color.white);
            TextTitle tt = chart.getTitle();
            tt.setFont(new java.awt.Font("Century Gothic", Font.BOLD, 30));
            tt.setPaint(Color.white);
            CPlot.getDomainAxis().setLabelFont(new java.awt.Font("Century Gothic", Font.BOLD, 16));
            CPlot.getRangeAxis().setLabelFont(new java.awt.Font("Century Gothic", Font.BOLD, 16));
            Xaxis.setLabelPaint(Color.white);
            Yaxis.setLabelPaint(Color.white);
            Xaxis.setLabelFont(new java.awt.Font("Century Gothic", Font.BOLD, 24));
            Yaxis.setLabelFont(new java.awt.Font("Century Gothic", Font.BOLD, 24));
            GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.decode("#13a6d9"),0.0f, 0.0f, Color.decode("#13a6d9"));
            BarRenderer renderer = (BarRenderer) CPlot.getRenderer();  
            renderer.setSeriesPaint(0, gp0);          
            final ItemLabelPosition p1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, 
                    TextAnchor.CENTER_RIGHT,TextAnchor.CENTER_RIGHT, -Math.PI / 2.0);
            renderer.setPositiveItemLabelPosition(p1);
            renderer.setDrawBarOutline(false);
            Xaxis.setUpperMargin(0.2);
            Yaxis.setUpperMargin(0.2);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.out.println("Error at Line Chart creation: "+e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void AppointmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AppointmentMouseClicked
        loadtoday();
    }//GEN-LAST:event_AppointmentMouseClicked

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

    private void cmbchoosebilldateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbchoosebilldateActionPerformed
        String date = cmbchoosebilldate.getSelectedItem().toString();
        ArrayList<String> list2 = new ArrayList<>();
        try
        {
            rs = conn.getbillname(date);
            while(rs.next())
            {
                list2.add(rs.getString("name"));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Can't fetch bill name in combo box: "+e);
        }
        cmbchoosebillname.setModel(new DefaultComboBoxModel(list2.toArray()));
    }//GEN-LAST:event_cmbchoosebilldateActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

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

    private void txtuidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtuidKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtuidKeyTyped

    private void txtcontactKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcontactKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtcontactKeyTyped

    private void txtfeesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfeesKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c))||(c==KeyEvent.VK_BACK_SPACE)||(c==KeyEvent.VK_DELETE)))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtfeesKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String inp = jTextField3.getText();
        if(inp.length() == 0)
        {
            JOptionPane.showMessageDialog(null,"Please input search string :(");
            txtsearch.requestFocus();
        }
        else
        {
            try
            {
                pst = con.prepareStatement("select ddate as Date,disease as Ailment,medicine as Medicine from diagnosis where regid = "
                    + "(select regno from patient where regno LIKE ? OR name LIKE ? LIMIT 1)"); 
            pst.setString(1,"%"+inp+"%");
            pst.setString(2,"%"+inp+"%");
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            }
            catch(SQLException e)
            {
                System.out.print(e);
            }
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnpresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpresetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnpresetActionPerformed

    private void cmbgenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbgenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbgenActionPerformed
    
    public void addtotable()
    {
        String disease = cmbdisease.getSelectedItem().toString();
        String medicine = cmbmedicine.getSelectedItem().toString();
        String dose = cmbdose.getSelectedItem().toString();
        String frequency = cmbfrequency.getSelectedItem().toString();
        String instruct = cmbinstruction.getSelectedItem().toString();
        String day = cmbdays.getSelectedItem().toString();
        model1.addRow(new Object [] {disease,medicine,dose,frequency,instruct,day});
    }
    public void resetapp()
    {
        txtname.setText("");
        cmbtype.setSelectedIndex(-1);
        txtnotes.setText("");
        jDateChooser1.setCalendar(null);
        cmbtimeslot.setSelectedIndex(-1);
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(jRadioButton1);
        bg1.add(jRadioButton2);
        bg1.clearSelection();
        
    }

    public void clearsearch()
    {
        txtsearch.setText(null);
        DefaultTableModel dtm = (DefaultTableModel) tblpatient.getModel();
        dtm.setRowCount(0);
    }

    public void savedata() throws SQLException
    {
        String opdno=txtregno.getText();
        String date=txtdate.getText();
        String time=txttime.getText();
        String agey=txtage.getText();
        String religion=txtreligion.getText();
        String address= txtaddress.getText();
        String mtype = cmbvtype.getSelectedItem().toString();
        String gender = cmbgender.getSelectedItem().toString();
        System.out.println(gender);
        String uid = txtuid.getText();
        String name = txtpname.getText();
        String bgroup = cmbbgroup.getSelectedItem().toString();
        String contact = txtcontact.getText();
        String occupation = txtoccupation.getText();
        String note = txtnote.getText();
        popdno = opdno;
        pname = name;
        pdate = date;
        pst=con.prepareStatement("insert into patient values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pst.setString(1, opdno);
        pst.setString(2, mtype);
        pst.setString(3, name);
        pst.setString(4, address);
        pst.setString(5, agey);
        pst.setString(6, religion);
        pst.setString(7, gender);
        pst.setString(8, uid);
        pst.setString(9, date);
        pst.setString(10, time);
        pst.setString(11, bgroup);
        pst.setString(12, contact);
        pst.setString(13, occupation);
        pst.setString(14, note);
        int i=pst.executeUpdate();
        if(i>0)
        {
            int rows = tabledisease.getRowCount();
            pst = con.prepareStatement("insert into diagnosis values (?,?,?,?,?,?,?,?,?)");
            for(int row=0; row <rows;row++)
            {
                //getting values from diagnosis table
                String disease = (String) tabledisease.getValueAt(row, 0);
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
                pst.addBatch();
                tblbillprint.setValueAt(disease,row,0);
                tblbillprint.setValueAt(medicine,row,1);
                tblbillprint.setValueAt(dose,row,2);
                tblbillprint.setValueAt(frequency,row,3);
                tblbillprint.setValueAt(instructions,row,4);
                tblbillprint.setValueAt(days,row,5);
            }
            pst.executeBatch();
            String fees = txtfees.getText(), billno = null;
            pst = con.prepareStatement("insert into bill values (?,?,?,?)");
            pst.setInt(1,0);
            pst.setString(2, opdno);
            pst.setString(3, date);
            pst.setString(4, fees);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Data Saved Succesfully !!!");
            getregno();
            setnull();
            pst = con.prepareStatement("select max(billno) from bill");
            rs = pst.executeQuery();
            while(rs.next())
            {
                billno = Integer.toString(rs.getInt("max(billno)"));
            }
            lblbillregno.setText(regno);
            lblbilldate.setText(date);
            lblbillno.setText(billno);
            lblbillname.setText(name);
            lblprintfees.setText(fees);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Data not Saved ...");
        }
        clear();
    }
    
    public void clear()
    {
        parsingarea.setText("");
        parsingarea.requestFocus();
        txtregno.setText(""); 
        txtuid.setText("");
        txtage.setText("0");
        txtpname.setText("");
        cmbgender.setSelectedIndex(0);
        txtage.setText("");
        txtreligion.setText("");
        txtaddress.setText("");
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
    
    public void logout()
    {
        try 
        {
            pst = con.prepareStatement("update session set logoffat=NOW() where sid=?");
            pst.setInt(1,sessionid);
            pst.executeUpdate();
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: "+ex);
            Logger.getLogger(HomeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Login("").setVisible(true);
        dispose();
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try 
        {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } 
        catch (Exception e) 
        {
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeGUI("","","").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddPatient;
    private javax.swing.JPanel AllPatient;
    private javax.swing.JPanel Appointment;
    private javax.swing.JPanel OPDNew;
    private javax.swing.JPanel PHome;
    private javax.swing.JPanel PrintBill;
    private javax.swing.JPanel Statistics;
    private javax.swing.JButton btnOPD;
    private javax.swing.JButton btnadddisease;
    private javax.swing.JButton btndeleterow;
    private javax.swing.JButton btnhomelogout;
    private javax.swing.JButton btnload;
    private javax.swing.JButton btnpreset;
    private javax.swing.JButton btnprint;
    private javax.swing.JButton btnprintp;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnschedule;
    private javax.swing.JButton btnsearch;
    private javax.swing.JComboBox<String> cmbage;
    private javax.swing.JComboBox<String> cmbbgroup;
    private javax.swing.JComboBox<String> cmbchoosebilldate;
    private javax.swing.JComboBox<String> cmbchoosebillname;
    private javax.swing.JComboBox<String> cmbdays;
    private javax.swing.JComboBox<String> cmbdayselect;
    private javax.swing.JComboBox<String> cmbdayselect1;
    private javax.swing.JComboBox<String> cmbdisease;
    private javax.swing.JComboBox<String> cmbdose;
    private javax.swing.JComboBox<String> cmbfrequency;
    private javax.swing.JComboBox<String> cmbgen;
    private javax.swing.JComboBox cmbgender;
    private javax.swing.JComboBox<String> cmbinstruction;
    private javax.swing.JComboBox<String> cmbmedicine;
    private javax.swing.JComboBox<String> cmbtimeslot;
    private javax.swing.JComboBox<String> cmbtype;
    private javax.swing.JComboBox<String> cmbvtype;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel65bill;
    private javax.swing.JLabel jLabel66bill;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel77bill;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel labelward3;
    private javax.swing.JLabel lbladdressopd1;
    private javax.swing.JLabel lblageopd1;
    private javax.swing.JLabel lblappointment;
    private javax.swing.JLabel lblbilldate;
    private javax.swing.JLabel lblbillname;
    private javax.swing.JLabel lblbillno;
    private javax.swing.JLabel lblbillregno;
    private javax.swing.JLabel lbldatetimeopd1;
    private javax.swing.JLabel lblfees;
    private javax.swing.JLabel lblgenderopd1;
    private javax.swing.JLabel lblnameopd1;
    private javax.swing.JLabel lblnewpatient;
    private javax.swing.JLabel lblnotes;
    private javax.swing.JLabel lblopdreg1;
    private javax.swing.JLabel lblprintfees;
    private javax.swing.JLabel lblreligionopd1;
    private javax.swing.JLabel lblsfees;
    private javax.swing.JTextArea parsingarea;
    private javax.swing.JPanel printpanel1bill;
    private javax.swing.JTable tabledisease;
    private javax.swing.JTable tblallpatients;
    private javax.swing.JTable tblbillprint;
    private javax.swing.JTable tblpatient;
    private javax.swing.JTable tbltom;
    private javax.swing.JTextArea txtaddress;
    private javax.swing.JTextField txtage;
    private javax.swing.JTextField txtappointment;
    private javax.swing.JTextField txtcontact;
    private javax.swing.JTextField txtdate;
    private javax.swing.JTextField txtfees;
    private javax.swing.JTextField txtname;
    private javax.swing.JTextField txtnewpatient;
    private javax.swing.JTextField txtnote;
    private javax.swing.JTextField txtnotes;
    private javax.swing.JTextField txtoccupation;
    private javax.swing.JTextField txtpname;
    private javax.swing.JTextField txtregno;
    private javax.swing.JTextField txtreligion;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txttime;
    private javax.swing.JTextField txtuid;
    // End of variables declaration//GEN-END:variables
}
