package Digilinc;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

public class Login extends javax.swing.JFrame {
DbConnect conn= null;
ResultSet rst= null;
Statement stmt=null;
Connection con;

PreparedStatement pst=null;
String exp;
    public Login(String msg) 
    {
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        try
        {
            conn = new DbConnect();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            stmt =  con.createStatement();
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Error:"+ex);
        }
        initComponents();
        getContentPane().setBackground(Color.decode("#313d48"));
        getRootPane().setDefaultButton(btnlogin);
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon.ico"));
        lblmsg.setText(msg);
        this.setIconImage(new ImageIcon(getClass().getResource("icon.ico")).getImage());
        txtusername.setCaretColor(Color.white);
        txtpassword.setCaretColor(Color.white);
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        int mon = now.get(Calendar.MONTH)+1;
        int yr = now.get(Calendar.YEAR);
        String date = day+"/"+mon+"/"+yr;
        int expcount = yr + (mon)*100 + day;
        int count = 10000;
        try
        {
            String a = Integer.toString(yr);
            rst = conn.licencecheck(a);
            while(rst.next())
            {
                exp = rst.getString("expire");
                count = rst.getInt("count");
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(expcount >= count)
        {
            txtpassword.setEditable(false);
            txtusername.setEditable(false);
            btnlogin.disable();
            JOptionPane.showMessageDialog(null, "Product is not activated please contact VPS Techub Private Limited");
            lblmsg.setText("Product is not activated");
        }
        
        if(!lblmsg.getText().equals("Product is not activated"))
        {
            btnaddkey.setVisible(false);
        }       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblusername = new javax.swing.JLabel();
        lblpassword = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        txtpassword = new javax.swing.JPasswordField();
        btnlogin = new javax.swing.JButton();
        btnaddkey = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        lblusertype = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        lblmsg = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VPS Hospital Management System");
        setMinimumSize(new java.awt.Dimension(1350, 750));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(49, 61, 72));

        lblusername.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblusername.setForeground(new java.awt.Color(255, 255, 255));
        lblusername.setText("Username");

        lblpassword.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblpassword.setForeground(new java.awt.Color(255, 255, 255));
        lblpassword.setText("Password");

        txtusername.setBackground(new java.awt.Color(49, 61, 72));
        txtusername.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        txtusername.setForeground(new java.awt.Color(255, 255, 255));
        txtusername.setCaretColor(new java.awt.Color(255, 255, 255));
        txtusername.setOpaque(false);
        txtusername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtusernameMouseClicked(evt);
            }
        });
        txtusername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtusernameKeyTyped(evt);
            }
        });

        txtpassword.setBackground(new java.awt.Color(49, 61, 72));
        txtpassword.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        txtpassword.setForeground(new java.awt.Color(255, 255, 255));
        txtpassword.setCaretColor(new java.awt.Color(255, 255, 255));
        txtpassword.setOpaque(false);
        txtpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpasswordMouseClicked(evt);
            }
        });

        btnlogin.setBackground(new java.awt.Color(19, 166, 217));
        btnlogin.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnlogin.setForeground(new java.awt.Color(255, 255, 255));
        btnlogin.setText("Login");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        btnaddkey.setBackground(new java.awt.Color(19, 166, 217));
        btnaddkey.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        btnaddkey.setForeground(new java.awt.Color(255, 255, 255));
        btnaddkey.setText("Add Key");
        btnaddkey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddkeyActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/Close Window_48px_3.png"))); // NOI18N
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        lblusertype.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblusertype.setForeground(new java.awt.Color(255, 255, 255));
        lblusertype.setText("User Type:");

        jComboBox1.setBackground(new java.awt.Color(49, 61, 72));
        jComboBox1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select One", "Receptionist", "Doctor" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(btnlogin, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(244, 244, 244)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(45, 45, 45)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblusername)
                                        .addComponent(lblpassword))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtusername)
                                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblusertype)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnaddkey, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(111, 111, 111)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblusername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblpassword))
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblusertype))
                .addGap(83, 83, 83)
                .addComponent(btnlogin)
                .addGap(95, 95, 95)
                .addComponent(btnaddkey)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblmsg.setFont(new java.awt.Font("Lucida Bright", 1, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(49, 61, 72));

        jLabel53.setBackground(new java.awt.Color(19, 166, 217));
        jLabel53.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Clinic Management System");

        jLabel54.setBackground(new java.awt.Color(19, 166, 217));
        jLabel54.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("VPS TecHub Pvt. Ltd.");

        jLabel1.setBackground(new java.awt.Color(49, 61, 72));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/LOGO transparent.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jLabel54))
                            .addComponent(jLabel53)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(892, 892, 892)
                .addComponent(lblmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(lblmsg)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        int sid = 0;
        if(txtusername.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(null,"Enter the username");
        }
        else if (txtpassword.getPassword().length == 0)
        {
            JOptionPane.showMessageDialog(null,"Enter the password");
        }
        else if(jComboBox1.getSelectedIndex() == 0 || jComboBox1.getSelectedIndex() == -1)
        {
            JOptionPane.showMessageDialog(null,"Select User Type");
        }
        else if(txtusername.getText()!=null && txtpassword.getPassword()!=null && jComboBox1.getSelectedIndex() > 0)
        {
            try 
            {
                rst= conn.getLogindetails(txtusername.getText(),txtpassword.getText(),jComboBox1.getSelectedItem().toString());
                if (rst.next()) 
                {
                    String Username = rst.getString("username");
                    String Usertype = rst.getString("usertype");
                    conn.loadsession(Username);
                    pst = con.prepareStatement("select max(sid) from session where username = ?");
                    pst.setString(1, Username);
                    rst = pst.executeQuery();
                    while(rst.next())
                    {
                        sid = rst.getInt("max(sid)");
                    }
                    new HomeGUI(Username,Usertype, Integer.toString(sid)).setVisible(true);
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Something is incorrect","Error",JOptionPane.ERROR_MESSAGE);
                    txtpassword.setText("");
                    txtusername.setText("");
                    txtusername.requestFocus();
                    jComboBox1.setSelectedIndex(0);
                }
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnloginActionPerformed

    private void btnaddkeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddkeyActionPerformed
        // TODO add your handling code here:
        Valaditylater val = new Valaditylater();
        val.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnaddkeyActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void txtpasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpasswordMouseClicked
        txtpassword.setText(null);
    }//GEN-LAST:event_txtpasswordMouseClicked

    private void txtusernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtusernameMouseClicked
        txtusername.setText(null);
    }//GEN-LAST:event_txtusernameMouseClicked

    private void txtusernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusernameKeyTyped
        
    }//GEN-LAST:event_txtusernameKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) 
    {
        try 
        {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
            UIManager.put("TextField.caretForeground", new ColorUIResource(Color.decode("#ffffff")));
        } 
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) 
        {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Login("").setVisible(true);
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaddkey;
    private javax.swing.JButton btnlogin;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblmsg;
    private javax.swing.JLabel lblpassword;
    private javax.swing.JLabel lblusername;
    private javax.swing.JLabel lblusertype;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
