package cedocpackage;
    import java.util.Date;
    import java.util.Properties;

    import java.time.LocalDateTime; 
    import java.time.format.DateTimeFormatter;
    
    import java.awt.BorderLayout;
    import java.awt.Color;
    import java.awt.EventQueue;

    import javax.swing.JFrame;
    import javax.swing.JPanel;
    import javax.swing.border.EmptyBorder;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.JTableHeader;
    import javax.mail.Authenticator;
    import java.net.URI;
    import javax.swing.JPopupMenu;
    import java.awt.Component;
    import java.awt.Cursor;
    import java.awt.Desktop;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.awt.Font;
    import java.awt.Frame;
    import java.awt.Image;

    import javax.swing.JTextArea;
    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import java.awt.event.ActionListener;
    import java.awt.event.ActionEvent;
    import java.io.File;
    import javax.mail.Message;
    import javax.mail.PasswordAuthentication;
    import javax.mail.Session;
    import javax.mail.Transport;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeMessage;
    import javax.swing.JLabel;
    import javax.swing.JTextField;
    import javax.activation.*;
    import javax.swing.CellEditor;
    import javax.swing.JComboBox;
    import javax.swing.JFileChooser;
    import javax.swing.JOptionPane;
    import javax.swing.JScrollPane;
    import javax.swing.JTable;
    import javax.swing.filechooser.FileNameExtensionFilter;

    
    public class Acceuil extends JFrame {
	private JPanel contentPane;
	private JPanel panel_3,panel_3_1,panel_3_1_1,panel_4,panel_6,homescreen,panel_10,panel_8,panel_5,panel_7;
	private JLabel homescreen1,Wallpaper,ensias_logo,header1,profile_label,submit_header1,submit_label,sujet_label,description_label,theme_label,abstrait_label,cv_label;
        private JLabel[] profile_labels; 
        private JLabel suivie_label,suivie_label1,avancements_label,avancements_label1,taches_label,taches_label1,ajouter_avancement_label,check_avancement_label;
        private JPanel header,panel_11,profile_panel,submit_header,submit_panel,ajouter_avancement_panel,check_avancement_panel;
	public JPanel welcome,suivie_panel,suivie_panel1,avancements_panel,avancements_panel1,taches_panel,taches_panel1,FAQ_panel,FAQ_panel1;
        private JTextField[] profile_text;
        private JButton modifier,cv,modifier_cv,envoyer,deposer,refresh_labo;
        private JButton changer_photo,chercher_nom_affect,full_list,reserver,mes_reservations,annuler;
        private JTextArea sujet,description,theme,abstrait,message,about_text;
        private JScrollPane scrollpane,scrollpane1,scrollpane2,scrollpane3,pg,affectation,contact,taches,about_app,labo_app;
        private JTable table,table_affect,taches_table,labo_table;
        private DefaultTableModel model,model1,model2,model3;
        private JTextField chercher_nom,sujet_text;
        private JPanel contact_panel,contact_panel1;
        private JLabel contact_label,contact_label1,sujet_contact_label,message_label,FAQ_label,FAQ_label1,about,labo_label,labo_label1;
        private JPanel panel,labo_panel,labo_panel1;
        private JComboBox combobox;
        Connection connection=sqlConnection.dbconnection();
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
        public String[] getinfo_etudiant(int id){
            try{
                String sql = "SELECT nom,prenom FROM info_etudiant WHERE id=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1,id);
                ResultSet rst=stm.executeQuery();
                if(rst.next()==true) {
                    String[] info= {rst.getString(1),rst.getString(2)};
                    return  info;
                }else{
                    JOptionPane.showMessageDialog(null, "ID n'existe pas");
                }
                return null;
            }catch(Exception e6){
                e6.printStackTrace();
                return null;
            }
        }
        
	public Boolean isaccepted(int id){
            try{
                String sql = "SELECT id_encadrant FROM info_etudiant WHERE id=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1,id);
                ResultSet rst=stm.executeQuery();
                while(rst.next()) {
                    if(rst.getInt(1)==0) return false;
                    else return true;
                }
                return false;
            }catch(Exception e6){
                e6.printStackTrace();
                return false;
            }
            
        }
        public String getEmail(int id){
            try{
                String sql = "SELECT email FROM info_etudiant where id=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1,id);
                ResultSet rst = st.executeQuery();
                rst.next();
                return rst.getString(1);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }    
                        
        }
        public String getPassword(int id){
            try{
                String sql = "SELECT password FROM info_etudiant where id=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1,id);
                ResultSet rst = st.executeQuery();
                rst.next();
                return rst.getString(1);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            } 
        }
        public int getEncadrantID(int id){
            try{
                String sql = "SELECT info_ens.id FROM info_ens join info_etudiant ON info_etudiant.id_encadrant=info_ens.id where info_etudiant.id=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1,id);
                ResultSet rst = stm.executeQuery();
                while(rst.next()) return rst.getInt(1);
                stm.close();
                rst.close();
                return 0;
            }catch(Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        public int getnbrPlaces(String date,String time,String departement){
            try{
                String sql="SELECT count(*) FROM labo WHERE date=? AND temps=? AND departement=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1,date);
                st.setString(2,time);
                st.setString(3,departement);
                ResultSet rst = st.executeQuery();
                rst.next();
                return rst.getInt(1);
            }catch(Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        public Boolean a_reserver(int id,String day){
            try{
                String sql="SELECT count(*) FROM labo WHERE date=? AND id_etudiant=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1,day);
                st.setInt(2,id);
                ResultSet rst = st.executeQuery();
                rst.next();
                if(rst.getInt(1)==0) return false;
                else return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
        
        public String[] getinfo_encadrant(int id){
            try{
                String sql = "SELECT info_ens.nom,info_ens.prenom,info_ens.email FROM info_ens join info_etudiant ON info_ens.id=info_etudiant.id_encadrant WHERE info_etudiant.id=?";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1,id);
                ResultSet rst=stm.executeQuery();
                if(rst.next()==true) {
                    String[] info= {rst.getString(1),rst.getString(2),rst.getString(3)};
                    return  info;
                }else{
                    JOptionPane.showMessageDialog(null, "ID n'existe pas");
                }
                return null;
            }catch(Exception e6){
                e6.printStackTrace();
                return null;
            }
        }
        
        public int id;
        
	public Acceuil(int id) {
		acceuil(id);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			private int id;
			public void run() {
				try {
					Acceuil frame = new Acceuil(this.id);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void acceuil(int id) {
            
                setResizable(false);
                setIconImage(new ImageIcon("img/graduated.png").getImage());
                setTitle("CeDoc");
		Connection connection=sqlConnection.dbconnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 786, 663);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
                panel_2.setBackground(new Color(128,128,128));
		panel_2.setBounds(0, 117, 218, 545);
		panel_2.setLayout(null);
		
		panel_3 = new JPanel();
                panel_3.setBackground(new Color(128,128,128));
		panel_3.setBounds(10, 73, 196, 54);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<HTML>          Profil<HR></HR></HTML>");
		lblNewLabel.setIcon(new ImageIcon("img/user.png"));
		lblNewLabel.setBounds(0, 0, 196, 54);
		panel_3.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 26));
		
		panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
                panel_3_1.setBackground(new Color(128,128,128));
		panel_3_1.setBounds(10, 138, 196, 47);
		
		JLabel lblNewLabel_1 = new JLabel("<HTML>          D\u00E9p\u00F4t<HR></HR></HTML>");
		lblNewLabel_1.setIcon(new ImageIcon("img/resume.png"));
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 26));
		lblNewLabel_1.setBounds(0, 0, 196, 47);
		panel_3_1.add(lblNewLabel_1);
		
		panel_3_1_1 = new JPanel();
		panel_3_1_1.setLayout(null);
                panel_3_1_1.setBackground(new Color(128,128,128));
		panel_3_1_1.setBounds(10, 196, 196, 47);
		
		JLabel lblNewLabel_1_1 = new JLabel("<HTML>          Suivi<HR></HR></HTML>");
		lblNewLabel_1_1.setBounds(0, 0, 196, 47);
		lblNewLabel_1_1.setIcon(new ImageIcon("img/tick.png"));
		panel_3_1_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 26));
		
		panel_4 = new JPanel();
                panel_4.setBackground(new Color(128,128,128));
		panel_4.setBounds(10, 359, 196, 47);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("<HTML>       Deconnexion<HR></HR></HTML>");
		lblNewLabel_2.setBounds(0, 0, 196, 47);
		lblNewLabel_2.setIcon(new ImageIcon("img/logout.png"));
		panel_4.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 18));
		
                panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBounds(10, 254, 198, 47);
                panel_11.setBackground(new Color(128,128,128));
                
                JLabel lblNewLabel_1_1_1 = new JLabel("<HTML>          Acceuil<HR></HR></HTML>");
		lblNewLabel_1_1_1.setBounds(0, 0, 196, 47);
		lblNewLabel_1_1_1.setIcon(new ImageIcon("img/home(1).png"));
		panel_11.add(lblNewLabel_1_1_1);
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.BOLD, 26));
		
		welcome = new JPanel();
		welcome.setBackground(new Color(232, 230, 241));
		welcome.setBounds(0,108,786,554);
                
		panel.add(welcome);
		welcome.setLayout(null);
		
		homescreen = new JPanel();
		homescreen.setBackground(new Color(232, 230, 241));
		homescreen.setBounds(350,0,140,76);
		welcome.add(homescreen);
		homescreen.setLayout(null);
		
		homescreen1 = new JLabel("");
		homescreen1.setBounds(10, 11, 76, 57);
		homescreen1.setIcon(new ImageIcon("img/home.png"));
		homescreen.add(homescreen1);
		
		
		panel_7 = new JPanel();
		panel_7.setBackground(new Color(232, 230, 241));
		panel_7.setBounds(10,11,50,51);
		welcome.add(panel_7);
		panel_7.setLayout(null);
		
		JLabel sidebar = new JLabel("");
		sidebar.setBounds(0,11,50,51);
		sidebar.setIcon(new ImageIcon("img/menu(1).png"));
		panel_7.add(sidebar);
		
		panel_8 = new JPanel();
		panel_8.setBackground(new Color(232, 230, 241));
		panel_8.setBounds(200, 77, 423, 60);
		welcome.add(panel_8);
		panel_8.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("<HTML>Welcome to our ENSIAS CeDoc application<HR></HR><HTML>");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setBounds(0, 0, 423, 51);
		panel_8.add(lblNewLabel_4);
		
		panel_5 = new JPanel();
		panel_5.setBackground(new Color(192,192,192));
		panel_5.setBounds(0, 139, 800, 415);
		welcome.add(panel_5);
		panel_5.setLayout(null);
		 
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(new Color(192, 192, 192));
		panel_12.setBounds(100, 50, 122, 119);
		panel_5.add(panel_12);
		panel_12.setLayout(null);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(new Color(241, 255, 28));
		panel_13.setBounds(2, 2, 118, 115);
		panel_12.add(panel_13);
		panel_13.setLayout(null);
		
		JLabel profile_dashboard = new JLabel("");
		profile_dashboard.setIcon(new ImageIcon("img/user(1).png"));
		profile_dashboard.setBounds(26, 0, 82, 70);
		panel_13.add(profile_dashboard);
		
		JLabel lblNewLabel_6 = new JLabel("<HTML>Profile<HR></HR></HTML>");
		lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_6.setForeground(Color.BLACK);
		lblNewLabel_6.setBounds(26, 81, 82, 23);
		panel_13.add(lblNewLabel_6);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBackground(new Color(192, 192, 192));
		panel_14.setBounds(270, 50, 122, 119);
		panel_5.add(panel_14);
		panel_14.setLayout(null);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBackground(new Color(161, 251, 255));
		panel_15.setBounds(2, 2, 118, 115);
		panel_14.add(panel_15);
		panel_15.setLayout(null);
		
		JLabel submit_dashboard = new JLabel("");
		submit_dashboard.setIcon(new ImageIcon("img/submit.png"));
		submit_dashboard.setBounds(26, 0, 82, 70);
		panel_15.add(submit_dashboard);
		
		JLabel lblNewLabel_7 = new JLabel("<HTML>Dépôt<HR></HR></HTML>");
		lblNewLabel_7.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_7.setForeground(Color.BLACK);
		lblNewLabel_7.setBounds(26, 81, 82, 23);
		panel_15.add(lblNewLabel_7);
		
		JPanel panel_16 = new JPanel();
		panel_16.setBackground(new Color(192, 192, 192));
		panel_16.setBounds(440, 50, 122, 119);
		panel_5.add(panel_16);
		panel_16.setLayout(null);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBackground(new Color(8, 229, 163));
		panel_17.setBounds(2, 2, 118, 115);
		panel_16.add(panel_17);
		panel_17.setLayout(null);
		
		JLabel suivie_dashboard = new JLabel("");
		suivie_dashboard.setIcon(new ImageIcon("img/dossier.png"));
		suivie_dashboard.setBounds(26, 0, 82, 70);
		panel_17.add(suivie_dashboard);
		
		JLabel lblNewLabel_8 = new JLabel("<HTML>Suivi<HR></HR></HTML>");
		lblNewLabel_8.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_8.setForeground(Color.BLACK);
		lblNewLabel_8.setBounds(26, 81, 82, 23);
		panel_17.add(lblNewLabel_8);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBackground(new Color(192, 192, 192));
		panel_18.setBounds(100,219, 122, 119);
		panel_5.add(panel_18);
		panel_18.setLayout(null);
		
		JPanel panel_19 = new JPanel();
		panel_19.setBackground(new Color(140, 229, 8));
		panel_19.setBounds(2, 2, 118, 115);
		panel_18.add(panel_19);
		panel_19.setLayout(null);
		
		JLabel advancement_dashboard = new JLabel("");
		advancement_dashboard.setIcon(new ImageIcon("img/check-list.png"));
		advancement_dashboard.setBounds(30, 0, 92, 70);
		panel_19.add(advancement_dashboard);
		
		JLabel lblNewLabel_9 = new JLabel("<HTML>Avancements<HR></HR></HTML>");
		lblNewLabel_9.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_9.setForeground(Color.BLACK);
		lblNewLabel_9.setBounds(10, 79, 98, 25);
		panel_19.add(lblNewLabel_9);
		
		JPanel panel_20 = new JPanel();
		panel_20.setBackground(new Color(192, 192, 192));
		panel_20.setBounds(270, 219, 122, 119);
		panel_5.add(panel_20);
		panel_20.setLayout(null);
		
		JPanel panel_21 = new JPanel();
		panel_21.setBackground(new Color(243, 165, 47));
		panel_21.setBounds(2, 2, 118, 115);
		panel_20.add(panel_21);
		panel_21.setLayout(null);
		
		JLabel task_dashboard = new JLabel("");
		task_dashboard.setIcon(new ImageIcon("img/task.png"));
		task_dashboard.setBounds(26, 0, 82, 70);
		panel_21.add(task_dashboard);
		
		JLabel lblNewLabel_10 = new JLabel("<HTML>Taches<HR></HR></HTML>");
		lblNewLabel_10.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_10.setForeground(Color.BLACK);
		lblNewLabel_10.setBounds(28, 81, 82, 23);
		panel_21.add(lblNewLabel_10);
		
		JPanel panel_22 = new JPanel();
		panel_22.setBackground(new Color(192, 192, 192));
		panel_22.setBounds(440, 219, 122, 119);
		panel_5.add(panel_22);
		panel_22.setLayout(null);
		
		JPanel panel_23 = new JPanel();
		panel_23.setBackground(new Color(255, 28, 36));
		panel_23.setBounds(2, 2, 118, 115);
		panel_22.add(panel_23);
		panel_23.setLayout(null);
		
		JLabel faq_dashboard = new JLabel("");
		faq_dashboard.setIcon(new ImageIcon("img/faq.png"));
		faq_dashboard.setBounds(26, 0, 82, 70);
		panel_23.add(faq_dashboard);
		
		JLabel lblNewLabel_11 = new JLabel("<HTML>FAQ<HR></HR></HTML>");
		lblNewLabel_11.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_11.setForeground(Color.BLACK);
		lblNewLabel_11.setBounds(36, 81, 72, 23);
		panel_23.add(lblNewLabel_11);
		
		
		JPanel panel_24 = new JPanel();
		panel_24.setBackground(new Color(192, 192, 192));
		panel_24.setBounds(610, 50, 122, 119);
		panel_5.add(panel_24);
		panel_24.setLayout(null);
		
		JPanel panel_25 = new JPanel();
		panel_25.setBackground(new Color(28, 28, 255));
		panel_25.setBounds(2, 2, 118, 115);
		panel_24.add(panel_25);
		panel_25.setLayout(null);
		
		JLabel contact_dashboard = new JLabel("");
		contact_dashboard.setIcon(new ImageIcon("img/contact-form.png"));
		contact_dashboard.setBounds(26, 0, 82, 70);
		panel_25.add(contact_dashboard);
		
		JLabel lblNewLabel_12 = new JLabel("<HTML>CONTACT<HR></HR></HTML>");
		lblNewLabel_12.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_12.setForeground(Color.BLACK);
		lblNewLabel_12.setBounds(10, 81, 98, 23);
		panel_25.add(lblNewLabel_12);
		
		JPanel panel_26 = new JPanel();
		panel_26.setBackground(new Color(192, 192, 192));
		panel_26.setBounds(610, 219, 122, 119);
		panel_5.add(panel_26);
		panel_26.setLayout(null);
		
		JPanel panel_27 = new JPanel();
		panel_27.setBackground(new Color(255, 144, 244));
		panel_27.setBounds(2, 2, 118, 115);
		panel_26.add(panel_27);
		panel_27.setLayout(null);
		
		JLabel lab_dashboard = new JLabel("");
		lab_dashboard.setIcon(new ImageIcon("img/chemistry.png"));
		lab_dashboard.setBounds(26, 0, 82, 70);
		panel_27.add(lab_dashboard);
		
		JLabel lblNewLabel_13 = new JLabel("<HTML>LAB<HR></HR></HTML>");
		lblNewLabel_13.setFont(new Font("Arial", Font.BOLD, 19));
		lblNewLabel_13.setForeground(Color.BLACK);
		lblNewLabel_13.setBounds(38, 81, 72, 23);
		panel_27.add(lblNewLabel_13);
                
		
		Wallpaper = new JLabel("New label");
		Wallpaper.setIcon(new ImageIcon("img/wallpaper.jpg"));
		Wallpaper.setBounds(0, 0, 800, 415);
		panel_5.add(Wallpaper);
		
		JPanel panel_1 = new JPanel();
                panel_1.setBackground(new Color(128,128,128));
		panel_1.setBounds(0, 0, 786, 109);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel student = new JLabel("");
		student.setBounds(10, 1, 135, 106);
                try {
			String sql= "select picture from info_etudiant where id=? ";
			PreparedStatement st=connection.prepareStatement(sql);
			st.setInt(1,id);
			ResultSet rst=st.executeQuery();
			while(rst.next()){
                        ImageIcon imag = new ImageIcon(rst.getString(1));
		        Image image = imag.getImage().getScaledInstance(student.getWidth(), student.getHeight(), Image.SCALE_SMOOTH);     
		        student.setIcon(new ImageIcon(image));
                        }
                        st.close();
                        rst.close();
		}catch(Exception e3) {
			e3.printStackTrace();
                }
		panel_1.add(student);
		
		panel_6 = new JPanel();
                panel_6.setBackground(new Color(128,128,128));
		panel_6.setBounds(170, 11, 49, 47);
		
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBackground(new Color(204, 51, 102));
		lblNewLabel_3.setBounds(0, 0, 46, 47);
		lblNewLabel_3.setIcon(new ImageIcon("img/back-arrow(1).png"));
		panel_6.add(lblNewLabel_3);
		
		panel_10 = new JPanel();
                panel_10.setBackground(new Color(128,128,128));
		panel_10.setBounds(10, 437, 198, 54);
		panel_10.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("<HTML>Check our website<HR></HR></HTML>");
		lblNewLabel_5.setIcon(new ImageIcon("img/global.png"));
		lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel_5.setBounds(0, 0, 198, 54);
		panel_10.add(lblNewLabel_5);
		
		JLabel Prenom = new JLabel("  Prenom");
		Prenom.setFont(new Font("Arial", Font.BOLD, 18));
		try {
			String sql= "select prenom from info_etudiant where id=? ";
			PreparedStatement st=connection.prepareStatement(sql);
			st.setInt(1,id);
			ResultSet rst=st.executeQuery();
			while(rst.next())     Prenom.setText("Prenom : "+rst.getString(1));
                        st.close();
			rst.close();
		}catch(Exception e3) {
			e3.printStackTrace();
		}
		Prenom.setBounds(227, 11, 257, 44);
		panel_1.add(Prenom);
		
		JLabel NOM = new JLabel("  Nom");
		NOM.setFont(new Font("Arial", Font.BOLD, 18));
		try {
			String sql= "select nom from info_etudiant where id=? ";
			PreparedStatement st=connection.prepareStatement(sql);
			st.setInt(1,id);
			ResultSet rst=st.executeQuery();
			while(rst.next())   NOM.setText("Nom : "+rst.getString(1));
                        st.close();
                        rst.close();
		}catch(Exception e3) {
			e3.printStackTrace();
		}
		NOM.setBounds(227, 59, 257, 40);
		panel_1.add(NOM);
		
		JLabel ID = new JLabel("");
		ID.setFont(new Font("Arial", Font.BOLD, 20));
		ID.setBounds(494, 11, 166, 88);
		ID.setText("ID : "+id);
		panel_1.add(ID);
		
		ensias_logo = new JLabel("");
		ensias_logo.setIcon(new ImageIcon("img/rsz_ensiaslogo.png"));
		ensias_logo.setBounds(657, 0, 129, 109);
		panel_1.add(ensias_logo);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(51, 51, 51));
		panel_9.setBounds(0, 95, 230, 23);
		
		
		
		/*profile panel*/{
			
			header= new JPanel();
			header.setBackground(new Color(232, 230, 241));
			header.setBounds(350,0,140,65);
			header.setLayout(null);
			
			header1 = new JLabel("");
			header1.setBounds(10, 0, 76, 65);
			header1.setIcon(new ImageIcon("img/user(1).png"));
			header.add(header1);
			
			profile_panel= new JPanel();
			profile_panel.setBackground(new Color(232, 230, 241));
			profile_panel.setBounds(350,56,140,65);
			profile_panel.setLayout(null);
			
			profile_label = new JLabel("<HTML>Profil<HR></HR></HTML>");
			profile_label.setBounds(5, 0, 96, 60);
			profile_label.setFont(new Font("Arial", Font.BOLD, 26));
			profile_panel.add(profile_label);
                        
                        
                        profile_text = new JTextField[11];
                        profile_text[0]= new JTextField();
                        profile_text[0].setBounds(20,160,220,60);
                        profile_text[1]= new JTextField();
                        profile_text[1].setBounds(280,160,220,60);
                        profile_text[2]= new JTextField();
                        profile_text[2].setBounds(20,260,220,60);
                        profile_text[3]= new JTextField();
                        profile_text[3].setBounds(280,260,220,60);
                        profile_text[4]= new JTextField();
                        profile_text[4].setBounds(20,360,220,60);
                        profile_text[5]= new JTextField();
                        profile_text[5].setBounds(280,360,220,60);
                        profile_text[6]= new JTextField();
                        profile_text[6].setBounds(20,460,220,60);
                        profile_text[7]= new JTextField();
                        profile_text[7].setBounds(280,460,220,60);
                        profile_text[8]= new JTextField();
                        profile_text[8].setBounds(540,160,220,60);
                        profile_text[9]= new JTextField();
                        profile_text[9].setBounds(540,260,220,60);
                        profile_text[10]= new JTextField();
                        profile_text[10].setBounds(540,360,220,60);
                        
                        profile_labels = new JLabel[11];
                        profile_labels[0]= new JLabel("Nom :");
                        profile_labels[0].setBounds(50,120,200,40);
                        profile_labels[0].setFont(new Font("Arial", Font.BOLD, 19));
			profile_labels[1]= new JLabel("Prenom :");
                        profile_labels[1].setBounds(310,120,200,40);
                        profile_labels[1].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[2]= new JLabel("CIN :");
                        profile_labels[2].setBounds(50,225,200,40);
                        profile_labels[2].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[3]= new JLabel("adresse :");
                        profile_labels[3].setBounds(310,225,200,40);
                        profile_labels[3].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[4]= new JLabel("ville :");
                        profile_labels[4].setBounds(50,325,200,40);
                        profile_labels[4].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[5]= new JLabel("code postal :");
                        profile_labels[5].setBounds(310,325,200,40);
                        profile_labels[5].setFont(new Font("Arial", Font.BOLD, 19));
			profile_labels[6]= new JLabel("GSM :");
                        profile_labels[6].setBounds(50,425,200,40);
                        profile_labels[6].setFont(new Font("Arial", Font.BOLD, 19));
			profile_labels[7]= new JLabel("email :");
                        profile_labels[7].setBounds(310,425,200,40);
                        profile_labels[7].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[8]= new JLabel("password :");
                        profile_labels[8].setBounds(570,120,200,40);
                        profile_labels[8].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[9]= new JLabel("Nationalite :");
                        profile_labels[9].setBounds(570,225,200,40);
                        profile_labels[9].setFont(new Font("Arial", Font.BOLD, 19));
                        profile_labels[10]= new JLabel("Date de naissance :");
                        profile_labels[10].setBounds(570,325,200,40);
                        profile_labels[10].setFont(new Font("Arial", Font.BOLD, 19));
           
                        try{
                            int i=0;
                            String sql= "select nom,prenom,cin,adresse,ville,code_postal,gsm,email,password,nationalite,datenaissance "
                                + "from info_etudiant " +
                                "where id=?";
                            PreparedStatement st=connection.prepareStatement(sql);
                            st.setInt(1,id);
                            ResultSet rst=st.executeQuery();
                            while(rst.next()){
                                while(i<11){
                                    profile_text[i].setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 17));
                                    profile_text[i].setColumns(10);
                                    profile_text[i].setText(rst.getString(i+1));
                                    i++;
                                }
                            }
                            rst.close();
                            st.close();
                        }catch(Exception e3){
                            e3.printStackTrace();
                        }
                        
                        modifier = new JButton("Modifier");
                        modifier.setBounds(540,460,220,60);
                        modifier.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    String sql="UPDATE info_etudiant SET email=?,password=?,cin=?,datenaissance=?,gsm=?,nom=?,prenom=?,code_postal=?,adresse=?,ville=?,nationalite=? WHERE id=?";
                                    PreparedStatement st=connection.prepareStatement(sql);
                                    st.setString(1,profile_text[7].getText());
                                    st.setString(2,profile_text[8].getText());
                                    st.setString(3,profile_text[2].getText());
                                    st.setString(4,profile_text[10].getText());
                                    st.setInt(5,Integer.parseInt(profile_text[6].getText()));
                                    st.setString(6,profile_text[0].getText());
                                    st.setString(7,profile_text[1].getText());
                                    st.setInt(8,Integer.parseInt(profile_text[5].getText()));
                                    st.setString(9,profile_text[3].getText());
                                    st.setString(10,profile_text[4].getText());
                                    st.setString(11,profile_text[9].getText());
                                    st.setInt(12,id);
                                    st.executeUpdate();
                                    st.close();
                                    JOptionPane.showMessageDialog(null,"Les modifications ont été enregistrées");
                                }
                                catch(Exception e1){
                                    e1.printStackTrace();
                                }
                            }
                        });
                        modifier.setFont(new Font("Arial", Font.BOLD, 12));
                        changer_photo = new JButton("Changer photo");
                        changer_photo.setBounds(580,43,140,51);
                        changer_photo.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                JFileChooser browseImageFile = new JFileChooser();
                                FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
                                browseImageFile.addChoosableFileFilter(fnef);
                                int showOpenDialogue = browseImageFile.showOpenDialog(null);
                                if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
                                    File selectedImageFile = browseImageFile.getSelectedFile();
                                    String selectedImagePath = selectedImageFile.getAbsolutePath();
                                    ImageIcon imageicon = new ImageIcon(selectedImagePath);
                                    Image image = imageicon.getImage().getScaledInstance(student.getWidth(), student.getHeight(), Image.SCALE_SMOOTH);
                                    student.setIcon(new ImageIcon(image));
                                    try{
                                        String sql = "UPDATE info_etudiant SET picture=? where id=?";
                                        PreparedStatement st=connection.prepareStatement(sql);
                                        st.setString(1,selectedImagePath);
                                        st.setInt(2,id);
                                        st.executeUpdate();
                                        st.close();
                                    }catch(Exception e1){
                                        e1.printStackTrace();
                                    }
                                    JOptionPane.showMessageDialog(null,"La photo a été modifiée");
                                }
                            }
                        });
                        changer_photo.setFont(new Font("Arial", Font.BOLD, 12));
		}
                /*submit panel*/{
                        submit_header= new JPanel();
			submit_header.setBackground(new Color(232, 230, 241));
			submit_header.setBounds(350,0,140,65);
			submit_header.setLayout(null);
			
			submit_header1 = new JLabel("");
			submit_header1.setBounds(10, 0, 76, 65);
			submit_header1.setIcon(new ImageIcon("img/submit.png"));
			submit_header.add(submit_header1);
			
			submit_panel= new JPanel();
			submit_panel.setBackground(new Color(232, 230, 241));
			submit_panel.setBounds(350,56,140,60);
			submit_panel.setLayout(null);
			
			submit_label = new JLabel("<HTML>Dépôt<HR></HR></HTML>");
			submit_label.setBounds(5, 0, 96, 60);
			submit_label.setFont(new Font("Arial", Font.BOLD, 26));
			submit_panel.add(submit_label);
                        
                        scrollpane = new JScrollPane();
                        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        sujet = new JTextArea();
                        sujet.setBounds(6, 140, 380, 50);
                        sujet.setForeground(Color.BLACK);
                        sujet.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                        scrollpane.setBounds(6, 140, 380, 50);
                        scrollpane.getViewport().setBackground(Color.WHITE);
                        
                        scrollpane1 = new JScrollPane();
                        scrollpane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollpane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        theme = new JTextArea();
                        theme.setBounds(393, 140, 380, 50);
                        theme.setForeground(Color.BLACK);
                        theme.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                        scrollpane1.setBounds(393, 140, 390, 50);
                        scrollpane1.getViewport().setBackground(Color.WHITE);
                        
                        scrollpane2 = new JScrollPane();
                        scrollpane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollpane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        description = new JTextArea();
                        description.setBounds(10, 220, 770, 200);
                        description.setForeground(Color.BLACK);
                        description.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                        scrollpane2.setBounds(10, 220, 770, 200);
                        scrollpane2.getViewport().setBackground(Color.WHITE);
                        
                        scrollpane3 = new JScrollPane();
                        scrollpane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollpane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        abstrait = new JTextArea();
                        abstrait.setBounds(10, 450, 770, 80);
                        abstrait.setForeground(Color.BLACK);
                        abstrait.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                        scrollpane3.setBounds(10, 450, 770, 80);
                        scrollpane3.getViewport().setBackground(Color.WHITE);
                        
                        sujet_label = new JLabel("Sujet :");
                        sujet_label.setBounds(150,110,300,30);
                        sujet_label.setFont(new Font("Arial", Font.BOLD, 19));
                        
                        theme_label = new JLabel("thème :");
                        theme_label.setBounds(537,110,300,30);
                        theme_label.setFont(new Font("Arial", Font.BOLD, 19));
                        
                        description_label = new JLabel("Description : ( moins de 600 mots )");
                        description_label.setBounds(150,190,400,30);
                        description_label.setFont(new Font("Arial", Font.BOLD, 19));
                        
                        abstrait_label = new JLabel("Abstrait :");
                        abstrait_label.setBounds(150,420,400,30);
                        abstrait_label.setFont(new Font("Arial", Font.BOLD, 19));
                        
                        try{
                            String sql="Select sujet,theme,description,abstrait from info_sujet where id_etudiant=?";
                            PreparedStatement st=connection.prepareStatement(sql);
                            st.setInt(1,id);
                            ResultSet rst=st.executeQuery();
                            while(rst.next()){
                                sujet.setText(rst.getString(1));
                                theme.setText(rst.getString(2));
                                description.setText(rst.getString(3));
                                abstrait.setText(rst.getString(4));
                            }
                            rst.close();
                            st.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                        cv = new JButton("Deposez votre CV");
                        cv.setBounds(580, 43, 200, 51);
                        cv.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                JFileChooser browseFile = new JFileChooser();
                                FileNameExtensionFilter fnf = new FileNameExtensionFilter("FILES", "pdf", "docx", "doc");
                                browseFile.addChoosableFileFilter(fnf);
                                int showOpenDialogue = browseFile.showOpenDialog(null);
                                if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
                                    File selectedFile = browseFile.getSelectedFile();
                                    String selectedPath = selectedFile.getAbsolutePath();
                                    try{
                                        String sql1 = "SELECT id_etudiant from info_sujet WHERE id_etudiant=?";
                                        PreparedStatement stm = connection.prepareStatement(sql1);
                                        stm.setInt(1,id);
                                        ResultSet rst = stm.executeQuery();
                                        if(rst.next()==false){
                                            try{
                                                String sql="INSERT INTO info_sujet(id_etudiant,cv) VALUES(?,?)";
                                                PreparedStatement st=connection.prepareStatement(sql);
                                                st.setInt(1, id);
                                                st.setString(2,selectedPath);
                                                st.executeUpdate();
                                                st.close();
                                            }catch(Exception e1){
                                                e1.printStackTrace();
                                            }
                                            JOptionPane.showMessageDialog(null,"CV ajouté");
                                        }
                                        else{
                                            try{
                                                String sql="UPDATE info_sujet SET cv=? WHERE id_etudiant=?";
                                                PreparedStatement st=connection.prepareStatement(sql);
                                                st.setString(1,selectedPath);
                                                st.setInt(2,id);
                                                st.executeUpdate();
                                                st.close();
                                            }catch(Exception e1){
                                                e1.printStackTrace();
                                            }
                                            JOptionPane.showMessageDialog(null,"CV ajouté");
                                        }
                                    }
                                    catch(Exception e1){
                                        e1.printStackTrace();
                                    }}
                                }
                        });
                        cv.setFont(new Font("Arial", Font.BOLD, 15));
                        
                        cv_label= new JLabel("");
                        cv_label.setBounds(540,10,230,30);
                        try {
                            String sql= "select cv from info_sujet where id_etudiant=? ";
                            PreparedStatement st=connection.prepareStatement(sql);
                            st.setInt(1,id);
                            ResultSet rst=st.executeQuery();
                            while(rst.next())   cv_label.setText(rst.getString(1));
                            st.close();
                            rst.close();
                        }catch(Exception e3) {
                            e3.printStackTrace();
                        }
                        cv.setFont(new Font("Arial", Font.PLAIN, 10));
                        
                        modifier_cv = new JButton("modifier");
                        modifier_cv.setBounds(70,45,170,51);
                        modifier_cv.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    String sql1 = "SELECT id_etudiant from info_sujet where id_etudiant=?";
                                    PreparedStatement stm = connection.prepareStatement(sql1);
                                    stm.setInt(1,id);
                                    ResultSet rst = stm.executeQuery();
                                    if(rst.next()==false){
                                        try{
                                            String sql="INSERT INTO info_sujet(id_etudiant,sujet,theme,description,abstrait) VALUES(?,?,?,?,?)";
                                            PreparedStatement st=connection.prepareStatement(sql);
                                            st.setInt(1,id);
                                            st.setString(2,sujet.getText());
                                            st.setString(3,theme.getText());
                                            st.setString(4,description.getText());
                                            st.setString(5,abstrait.getText());;
                                            st.executeUpdate();
                                            st.close();
                                            JOptionPane.showMessageDialog(null,"Les modifications ont été enregistrées");
                                        }
                                        catch(Exception e1){
                                            e1.printStackTrace();
                                        }
                                    }
                                    else{
                                        try{
                                            String sql="UPDATE info_sujet SET sujet=?,theme=?,description=?,abstrait=? WHERE id_etudiant=?";
                                            PreparedStatement st=connection.prepareStatement(sql);
                                            st.setString(1,sujet.getText());
                                            st.setString(2,theme.getText());
                                            st.setString(3,description.getText());
                                            st.setString(4,abstrait.getText());;
                                            st.setInt(5,id);
                                            st.executeUpdate();
                                            st.close();
                                            JOptionPane.showMessageDialog(null,"Les modifications ont été enregistrées");
                                        }
                                        catch(Exception e1){
                                            e1.printStackTrace();
                                        }
                                    }
                                }catch(Exception e1){
                                    e1.printStackTrace();
                                }
                            }
                        });
                        modifier_cv.setFont(new Font("Arial", Font.BOLD, 15));
                }
                /*suivi panel*/{
                    suivie_panel= new JPanel();
                    suivie_panel.setBackground(new Color(8, 229, 163));
                    suivie_panel.setBounds(350,0,140,65);
                    suivie_panel.setLayout(null);
			
                    suivie_label = new JLabel("");
                    suivie_label.setBounds(10, 0, 76, 65);
                    suivie_label.setIcon(new ImageIcon("img/dossier.png"));
                    suivie_panel.add(suivie_label);
			
                    suivie_panel1= new JPanel();
                    suivie_panel1.setBackground(new Color(8, 229, 163));
                    suivie_panel1.setBounds(350,56,140,60);
                    suivie_panel1.setLayout(null);
			
                    suivie_label1 = new JLabel("<HTML>Suivi<HR></HR></HTML>");
                    suivie_label1.setBounds(5, 0, 96, 60);
                    suivie_label1.setFont(new Font("Arial", Font.BOLD, 26));
                    suivie_panel1.add(suivie_label1);
                    
                    model1 = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    model1.addColumn("Nom");
                    model1.addColumn("Prenom");
                    model1.addColumn("Nom_encadrant");
                    model1.addColumn("Prenom_encadrant");
                    model1.addColumn("Etat");
                    table_affect = new JTable();
                    table_affect.setBounds(60,200,660,300);
                    table_affect.setModel(model1); 
                    table_affect.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table_affect.setFillsViewportHeight(true);
                    affectation = new JScrollPane();
                    affectation.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    affectation.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    affectation.getViewport().setBackground(Color.WHITE);
                    affectation.setBounds(60,200,660,300);
                    affectation.getViewport().add(table_affect);
                    try {
                        String sql="SELECT info_etudiant.nom,info_etudiant.prenom,info_ens.nom,info_ens.prenom FROM info_etudiant JOIN info_ens ON info_etudiant.id_encadrant=info_ens.id";
                        PreparedStatement pstm = connection.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();
                        while(rs.next()){
                            model1.addRow(new Object[]{rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),"Accepté(e)"});
                        }
                        rs.close();
                        pstm.close();        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    chercher_nom = new JTextField();
                    chercher_nom.setBounds(70,120,250,50);
                    chercher_nom.setFont(new Font("Arial", Font.BOLD, 19));
                    
                    chercher_nom_affect = new JButton("Chercher par ID");
                    chercher_nom_affect.setBounds(330,120,200,50);
                    chercher_nom_affect.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                model1.setRowCount(0);
                                try {
                                    String sql="SELECT info_etudiant.nom,info_etudiant.prenom,info_ens.nom,info_ens.prenom FROM info_etudiant JOIN info_ens ON info_etudiant.id_encadrant=info_ens.id WHERE info_etudiant.id=?";
                                    PreparedStatement pstm = connection.prepareStatement(sql);
                                    pstm.setInt(1,Integer.parseInt(chercher_nom.getText()));
                                    ResultSet rs = pstm.executeQuery();
                                    if(rs.next()==false){
                                        model1.addRow(new Object[]{getinfo_etudiant(Integer.parseInt(chercher_nom.getText()))[0], getinfo_etudiant(Integer.parseInt(chercher_nom.getText()))[1],"","","Rejeté(e)"});
                                    }else{
                                        model1.addRow(new Object[]{rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),"Accepté(e)"});
                                    }
                                    rs.close();
                                    pstm.close();        
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                    });
                    chercher_nom_affect.setFont(new Font("Arial", Font.BOLD, 15));
                    full_list = new JButton("liste complete");
                    full_list.setBounds(540,120,170,50);
                    full_list.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                chercher_nom.setText("");
                                model1.setRowCount(0);
                                try {
                                    String sql="SELECT info_etudiant.nom,info_etudiant.prenom,info_ens.nom,info_ens.prenom FROM info_etudiant JOIN info_ens ON info_etudiant.id_encadrant=info_ens.id";
                                    PreparedStatement pstm = connection.prepareStatement(sql);
                                    ResultSet rs = pstm.executeQuery();
                                    while(rs.next()){
                                        model1.addRow(new Object[]{rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),"Accepté(e)"});
                                    }
                                    rs.close();
                                    pstm.close();        
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                    });
                    full_list.setFont(new Font("Arial", Font.BOLD, 15));
                }
                /*avancements panel*/{
                    avancements_panel= new JPanel();
                    avancements_panel.setBackground(new Color(140, 229, 8));
                    avancements_panel.setBounds(350,0,140,65);
                    avancements_panel.setLayout(null);
			
                    avancements_label = new JLabel("");
                    avancements_label.setBounds(10, 0, 76, 65);
                    avancements_label.setIcon(new ImageIcon("img/check-list.png"));
                    avancements_panel.add(avancements_label);
			
                    avancements_panel1= new JPanel();
                    avancements_panel1.setBackground(new Color(140, 229, 8));
                    avancements_panel1.setBounds(310,56,170,60);
                    avancements_panel1.setLayout(null);
			
                    avancements_label1 = new JLabel("<HTML>Avancements<HR></HR></HTML>");
                    avancements_label1.setBounds(0, 0, 160, 60);
                    avancements_label1.setFont(new Font("Arial", Font.BOLD, 23));
                    avancements_panel1.add(avancements_label1); 
                    
                    model = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if(column==0||column==1||column==2||column==4) return false;
                            else return true;
                        }
                    };
                    model.addColumn("nom_etudiant");
                    model.addColumn("prenom_etudiant");
                    model.addColumn("Nom_encadrant");
                    model.addColumn("Avancement");
                    model.addColumn("DATE");
                    table = new JTable();
                    table.setBounds(60,140,660,370);
                    table.setModel(model); 
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table.setFillsViewportHeight(true);
                    pg = new JScrollPane();
                    pg.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    pg.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    pg.getViewport().setBackground(Color.WHITE);
                    pg.setBounds(60,140,660,370);
                    pg.getViewport().add(table);
                    try {
                        PreparedStatement pstm = connection.prepareStatement("SELECT info_etudiant.nom,info_etudiant.prenom,info_ens.nom,avancements.avancement,avancements.date FROM avancements JOIN info_etudiant ON info_etudiant.id=avancements.id_etudiant JOIN info_ens ON info_ens.id=avancements.id_encadrant WHERE avancements.id_etudiant=?");
                        pstm.setInt(1,id);
                        ResultSet Rs = pstm.executeQuery();
                        while(Rs.next()){
                            model.addRow(new Object[]{Rs.getString(1), Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getDate(5)});
                        }
                        Rs.close();
                        pstm.close();        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    ajouter_avancement_panel = new JPanel();
                    ajouter_avancement_panel.setBounds(730,150,50,50);
                    ajouter_avancement_panel.setBackground(new Color(140, 229, 8));
                    
                    ajouter_avancement_label = new JLabel("");
                    ajouter_avancement_label.setBounds(0,0,50,50);
                    ajouter_avancement_label.addMouseListener(new MouseAdapter(){
                        @Override
                        public void mouseClicked(MouseEvent e){
                            try{
                                Date date=new Date();
                                String year =""+(date.getYear()+1900)+"-"+"0"+(date.getMonth()+1)+"-"+ (date.getDate());
                                PreparedStatement pstm = connection.prepareStatement("SELECT info_etudiant.nom,info_etudiant.prenom,info_ens.nom FROM info_etudiant JOIN info_ens ON info_etudiant.id_encadrant=info_ens.id WHERE info_etudiant.id=?");
                                pstm.setInt(1,id);
                                ResultSet Rs = pstm.executeQuery();
                                Rs.next();
                                model.addRow(new Object[]{Rs.getString(1),Rs.getString(2),Rs.getString(3),"",year});
                                Rs.close();
                                pstm.close(); 
                            }catch(Exception e2){
                                e2.printStackTrace();
                            }
                        }
                    });
                    ajouter_avancement_label.setIcon(new ImageIcon("img/plus.png"));
                    ajouter_avancement_panel.add(ajouter_avancement_label);
                    
                    check_avancement_panel = new JPanel();
                    check_avancement_panel.setBounds(730,201,50,50);
                    check_avancement_panel.setBackground(new Color(140, 229, 8));
                    
                    check_avancement_label = new JLabel("");
                    check_avancement_label.setBounds(0,0,50,50);
                    check_avancement_label.addMouseListener(new MouseAdapter(){
                        @Override
                        public void mouseClicked(MouseEvent e){
                            CellEditor editing_cell = table.getCellEditor();
                            editing_cell.stopCellEditing();
                            try{
                                String sql="INSERT INTO avancements VALUES(?,?,?,?)";
                                PreparedStatement stm = connection.prepareStatement(sql);
                                stm.setInt(1,id);
                                stm.setInt(2,getEncadrantID(id));
                                stm.setString(3,model.getValueAt(model.getRowCount()-1,3).toString());
                                stm.setString(4,model.getValueAt(model.getRowCount()-1,4).toString());
                                stm.executeUpdate();
                                stm.close();
                                JOptionPane.showMessageDialog(null,"Les modifications ont été enregistrées");
                            }catch(Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
                    check_avancement_label.setIcon(new ImageIcon("img/check.png"));
                    check_avancement_panel.add(check_avancement_label);
                }
                /*contact panel*/ {
                    contact_panel= new JPanel();
                    contact_panel.setBackground(new Color(28,28,255));
                    contact_panel.setBounds(350,0,140, 65);
                    contact_panel.setLayout(null);
			
                    contact_label = new JLabel("");
                    contact_label.setBounds(10, 0, 76, 65);
                    contact_label.setIcon(new ImageIcon("img/contact-form.png"));
                    contact_panel.add(contact_label);
			
                    contact_panel1= new JPanel();
                    contact_panel1.setBackground(new Color(28,28,255));
                    contact_panel1.setBounds(350,56,140, 60);
                    contact_panel1.setLayout(null);
			
                    contact_label1 = new JLabel("<HTML>Contact<HR></HR></HTML>");
                    contact_label1.setBounds(0, 0, 96, 60);
                    contact_label1.setFont(new Font("Arial", Font.BOLD, 26));
                    contact_panel1.add(contact_label1);
                    
                    sujet_text = new JTextField();
                    sujet_text.setBounds(55,150,700,50);
                    sujet_text.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                    
                    sujet_contact_label = new JLabel("Sujet :");
                    sujet_contact_label.setBounds(70,100,200,50);
                    sujet_contact_label.setFont(new Font("Arial", Font.BOLD, 19));
                    
                    message_label = new JLabel("Message :");
                    message_label.setBounds(70,200,200,50);
                    message_label.setFont(new Font("Arial", Font.BOLD, 19));
                    
                    contact = new JScrollPane();
                    contact.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    contact.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    message = new JTextArea();
                    message.setBounds(55, 250, 700, 200);
                    message.setForeground(Color.BLACK);
                    message.enableInputMethods(true);
                    message.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                    contact.setBounds(55, 250, 700, 200);
                    contact.getViewport().setBackground(Color.WHITE);
                    //contact.getViewport().add(message);
                    
                    envoyer= new JButton("Envoyer");
                    envoyer.setBounds(55,480,200,50);
                    envoyer.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e) {
                        Properties properties = new Properties();
                        properties.put("mail.smtp.auth","true");
                        properties.put("mail.smtp.starttls.enable","true");
                        properties.put("mail.smtp.host","smtp.gmail.com");
                        properties.put("mail.smtp.port", "587");
                        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
                        Session session = Session.getDefaultInstance(properties,new Authenticator(){
                            @Override 
                            protected PasswordAuthentication getPasswordAuthentication(){
                                return new PasswordAuthentication(getEmail(id),getPassword(id));
                            }
                        });
                        try {
                            MimeMessage message1 = new MimeMessage(session);
                            message1.setFrom(new InternetAddress(getEmail(id)));
                            message1.addRecipient(Message.RecipientType.TO,new InternetAddress(getinfo_encadrant(id)[2]) );
                            message1.setSubject(sujet_text.getText());
                            message1.setText(message.getText());
                            Transport.send(message1);
                            sujet_text.setText("");
                            message.setText("");
                            JOptionPane.showMessageDialog(null, "message envoyé");
                        }catch(Exception e1){
                            e1.printStackTrace();
                        }
                        }
                    });
                    envoyer.setFont(new Font("Arial", Font.BOLD, 15));
                    
                }
                /*taches panel*/{
                    taches_panel= new JPanel();
                    taches_panel.setBackground(new Color(243,165,47));
                    taches_panel.setBounds(350,0,140, 65);
                    taches_panel.setLayout(null);
			
                    taches_label = new JLabel("");
                    taches_label.setBounds(10, 0, 76, 65);
                    taches_label.setIcon(new ImageIcon("img/task.png"));
                    taches_panel.add(taches_label);
			
                    taches_panel1= new JPanel();
                    taches_panel1.setBackground(new Color(243,165,47));
                    taches_panel1.setBounds(350,56,140, 60);
                    taches_panel1.setLayout(null);
			
                    taches_label1 = new JLabel("<HTML>Tâches<HR></HR></HTML>");
                    taches_label1.setBounds(0, 0, 100, 60);
                    taches_label1.setFont(new Font("Arial", Font.BOLD, 26));
                    taches_panel1.add(taches_label1);
                    
                    model2 = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            if(column==3)return true;
                            else return false;
                        }
                    };
                    model2.addColumn("nom_etudiant");
                    model2.addColumn("prenom_etudiant");
                    model2.addColumn("nom_encadrant");
                    model2.addColumn("Tache");
                    model2.addColumn("Date_depot");
                    model2.addColumn("Date_limite");
                    taches_table = new JTable();
                    taches_table.setBounds(60,140,660,370);
                    taches_table.setModel(model2); 
                    taches_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    taches_table.setFillsViewportHeight(true);
                    taches = new JScrollPane();
                    taches.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    taches.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    taches.getViewport().setBackground(Color.WHITE);
                    taches.setBounds(60,140,660,370);
                    taches.getViewport().add(taches_table);
                    try {
                        PreparedStatement stm = connection.prepareStatement("SELECT info_etudiant.nom,info_etudiant.prenom,info_ens.nom,taches.tache,taches.date_depot,taches.date_limite FROM taches JOIN info_etudiant ON info_etudiant.id=taches.id_etudiant JOIN info_ens ON info_ens.id=taches.id_encadrant WHERE taches.id_etudiant=?");
                        stm.setInt(1,id);
                        ResultSet Rs = stm.executeQuery();
                        while(Rs.next()){
                            model2.addRow(new Object[]{Rs.getString(1), Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getDate(5),Rs.getDate(6)});
                        }
                        Rs.close();
                        stm.close();        
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                /*FAQ panel*/{
                    FAQ_panel= new JPanel();
                    FAQ_panel.setBackground(new Color(255,28,36));
                    FAQ_panel.setBounds(350,0,140, 65);
                    FAQ_panel.setLayout(null);
			
                    FAQ_label = new JLabel("");
                    FAQ_label.setBounds(10, 0, 76, 65);
                    FAQ_label.setIcon(new ImageIcon("img/faq.png"));
                    FAQ_panel.add(FAQ_label);
			
                    FAQ_panel1= new JPanel();
                    FAQ_panel1.setBackground(new Color(255,28,36));
                    FAQ_panel1.setBounds(350,0,140, 60);
                    FAQ_panel1.setLayout(null);
			
                    FAQ_label1 = new JLabel("<HTML>FAQ<HR></HR></HTML>");
                    FAQ_label1.setBounds(6, 0, 100, 60);
                    FAQ_label1.setFont(new Font("Arial", Font.BOLD, 26));
                    FAQ_panel1.add(FAQ_label1);
                    
                    about = new JLabel("A propos de l'application :");
                    about.setBounds(60,120,300,50);
                    about.setFont(new Font("Arial", Font.BOLD, 19));
                    
                    about_app = new JScrollPane();
                    about_app.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    about_app.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    about_text = new JTextArea();
                    about_text.setBounds(10, 170, 770, 300);
                    about_text.setForeground(Color.BLACK);
                    about_text.setEditable(false);
                    about_text.setFont(new Font("Tw Cen MT Condensed", Font.BOLD | Font.ITALIC, 18));
                    about_app.setBounds(10, 170, 770, 300);
                    about_app.getViewport().setBackground(Color.WHITE);
                    about_app.getViewport().add(about_text);
                    
                    about_text.setText("\nA quoi sert cette application?\n\n"
                            + "Cette application est pour les etudiants qui veullent s'inscrire au service doctorat de l'ENSIAS. \nElle est aussi mise à la disposition des étudiant accepté et leurs encadrants"
                            +",pour faciliter leurs travaux collaboratifs.\n"
                            + "Quels sonts les services fournis par l'application?\n\n"
                            + "Profile :\n"
                            + "Les prochains doctorants peuvent consulter leurs propre information dans cette section"
                            + ",comme il peuvent modifier leur\n email,password,etc...\n"
                            + "\nDépôt :\n"
                            + "Cette section est réservée pour le dépôt des information qui concernent le sujet et le cv.\n"
                            + "\nSuivie :\n"
                            + "Cette section est réservée au prochains doctorants pour consulter l'état de leur condidature(retenue ou refusée).\n"
                            + "\nLes prochaines Sections sont réservées aux candidats retenus :\n\n"
                            + "\nAvancements :\n"
                            + "Seuls les doctorants dont la candidature est retenue peuvent acceder à cette section pour ajouter leurs \navancements"
                            + "afin que leur encadrant puisse suivre leurs travaux.\n"
                            + "\nTâches :\n"
                            + "Grâce à cette section les doctorants peuvent vérifier les tâches affectées de la part de leur prof encadrant.\n"
                            + "\nLabo :\n"
                            + "Cette section est pour réservé le labo d'un département \n"
                            + "NB : Un étudiant ne peut fair qu'une seule réservation par jour.\n\n");
                    
                }
                /*labo panel*/{
                    labo_panel= new JPanel();
                    labo_panel.setBackground(new Color(255, 144, 244));
                    labo_panel.setBounds(350,0,140, 65);
                    labo_panel.setLayout(null);
			
                    labo_label = new JLabel("");
                    labo_label.setBounds(10, 0, 76, 65);
                    labo_label.setIcon(new ImageIcon("img/chemistry.png"));
                    labo_panel.add(labo_label);
			
                    labo_panel1= new JPanel();
                    labo_panel1.setBackground(new Color(255, 144, 244));
                    labo_panel1.setBounds(350,0,140, 60);
                    labo_panel1.setLayout(null);
			
                    labo_label1 = new JLabel("<HTML>Labo<HR></HR></HTML>");
                    labo_label1.setBounds(6, 0, 100, 60);
                    labo_label1.setFont(new Font("Arial", Font.BOLD, 26));
                    labo_panel1.add(labo_label1);
                    
                    String[] departement= {"GL","IWIM","ISEM","BI","IA","IDF","SSI","iel"};
                    combobox = new JComboBox(departement);
                    combobox.setBounds(100,120,250,50);
                    
                    model3 = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    model3.addColumn("departement");
                    model3.addColumn("DATE");
                    model3.addColumn("Places disponibles");
                    model3.addColumn("Temps");
                    labo_table = new JTable();
                    labo_table.setBounds(60,200,660,200);
                    labo_table.setModel(model3); 
                    labo_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    labo_table.setFillsViewportHeight(true);
                    labo_app = new JScrollPane();
                    labo_app.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    labo_app.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    labo_app.getViewport().setBackground(Color.WHITE);
                    labo_app.setBounds(60,200,660,200);
                    labo_app.getViewport().add(labo_table);
                    
                    refresh_labo = new JButton("rafraîchir");
                    refresh_labo.setBounds(360,120,170,50);
                    refresh_labo.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            model3.setRowCount(0);
                            LocalDateTime myDateObj = LocalDateTime.now();
                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String format = myDateObj.format(myFormatObj);
                            String hour = ""+format.charAt(11)+""+format.charAt(12);
                            int time3 =  Integer.parseInt(hour)+1;
                            if(time3<=17){
                            Date date = new Date();
                            String year =""+(date.getYear()+1900)+"-"+"0"+(date.getMonth()+1)+"-"+ (date.getDate());
                            int i =8;
                            String time;
                            while(i<18){
                                String str = String.format("%02d", i);
                                time=str+":00:00";
                                int places=getnbrPlaces(year,time,combobox.getSelectedItem().toString()); 
                                if(places==0) model3.addRow(new Object[]{combobox.getSelectedItem(),year,2,time});
                                else model3.addRow(new Object[]{combobox.getSelectedItem(),year,2-places,time});
                                i++;
                            }
                            }else{
                                JOptionPane.showMessageDialog(null,"Il n'y a plus de réservations pour aujourd'hui");
                            }
                        }
                    });
                    refresh_labo.setFont(new Font("Arial", Font.BOLD, 15));
                    
                    reserver = new JButton("Réserver");
                    reserver.setBounds(100,410,170,50);
                    reserver.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            int i = labo_table.getSelectedRow();
                            LocalDateTime myDateObj = LocalDateTime.now();
                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String format = myDateObj.format(myFormatObj);
                            String hour = ""+format.charAt(11)+""+format.charAt(12);
                            int time =  Integer.parseInt(hour)+1;
                            if(a_reserver(id,model3.getValueAt(i,1).toString())==false&&time<=17){
                            if(Integer.parseInt(model3.getValueAt(i, 2).toString())!=0) {
                                model3.setValueAt(Integer.parseInt(model3.getValueAt(i, 2).toString())-1, i, 2);
                                try{
                                    String sql="INSERT INTO labo VALUES(?,?,?,?,?) ";
                                    PreparedStatement stm=connection.prepareStatement(sql);
                                    stm.setInt(1,id);
                                    stm.setInt(2,getEncadrantID(id));
                                    stm.setString(3,model3.getValueAt(i, 0).toString());
                                    stm.setString(4,model3.getValueAt(i,1).toString());
                                    stm.setString(5,model3.getValueAt(i,3).toString());
                                    stm.executeUpdate();
                                    stm.close();
                                }catch(Exception e1){
                                    JOptionPane.showMessageDialog(null, "Vous avez déja fait cette réservation");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Pas de places vides: veuillez choisir un temps different");
                            }
                            }else{
                                JOptionPane.showMessageDialog(null, "vous ne pouvez pas faire plus d'une réservation par jour");
                            }
                        }
                    });
                    reserver.setFont(new Font("Arial", Font.BOLD, 15));
                    
                    mes_reservations = new JButton("mes réservation");
                    mes_reservations.setBounds(280,410,200,50);
                    mes_reservations.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            model3.setRowCount(0);
                            try{
                                String sql="SELECT departement,date,temps FROM labo WHERE id_etudiant=?";
                                PreparedStatement st = connection.prepareStatement(sql);
                                st.setInt(1,id);
                                ResultSet rst = st.executeQuery();
                                while(rst.next()){
                                    int places=getnbrPlaces(rst.getDate(2).toString(),rst.getTime(3).toString(),rst.getString(1)); 
                                    model3.addRow(new Object[]{rst.getString(1),rst.getDate(2).toString(),places,rst.getTime(3)});
                                }
                                st.close();
                                rst.close();
                            }catch(Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
                    mes_reservations.setFont(new Font("Arial", Font.BOLD, 15));
                    
                    annuler = new JButton("Annuler");
                    annuler.setBounds(490,410,200,50);
                    annuler.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            int i = labo_table.getSelectedRow();
                            Date date = new Date();
                            String year =""+(date.getYear()+1900)+"-"+"0"+(date.getMonth()+1)+"-"+ (date.getDate());
                            LocalDateTime myDateObj = LocalDateTime.now();
                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String format = myDateObj.format(myFormatObj);
                            String hour = ""+format.charAt(11)+""+format.charAt(12);
                            String hour1= model3.getValueAt(i, 3).toString();
                            int time =  Integer.parseInt(hour)+1;
                            int time1 = Integer.parseInt(""+hour1.charAt(0)+""+hour1.charAt(1))+1;
                            if(time<=time1&&year.equals(model3.getValueAt(i, 1).toString())){
                                try{
                                    String sql = "DELETE FROM labo WHERE id_etudiant=? AND date=? and temps=? and departement=?";
                                    PreparedStatement st = connection.prepareStatement(sql);
                                    st.setInt(1,id);
                                    st.setString(2,model3.getValueAt(i, 1).toString());
                                    st.setString(3,model3.getValueAt(i, 3).toString());
                                    st.setString(4,model3.getValueAt(i, 0).toString());
                                    st.executeUpdate();
                                    st.close();
                                    JOptionPane.showMessageDialog(null,"annulation avec succés"); 
                                }catch(Exception e1){
                                    e1.printStackTrace();
                                }
                            }else{
                               JOptionPane.showMessageDialog(null,"you can only cancel a reservetion one hour before its time"); 
                            }
                        }
                    });
                    annuler.setFont(new Font("Arial", Font.BOLD, 15));
                    
                }
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                                int i=0;
				welcome.removeAll();
				revalidate();
				repaint();
				welcome.add(panel_7);
				welcome.add(header);
				welcome.add(profile_panel);
                                while(i<11){
                                    welcome.add(profile_text[i]);
                                    welcome.add(profile_labels[i]);
                                    i++;
                                }
                                welcome.add(modifier);
                                welcome.add(changer_photo);
                                welcome.setBounds(0,108,786,554);
				header.setBounds(350, 0, 140, 65);
				profile_panel.setBounds(350, 56, 140, 60);
                                changer_photo.setBounds(580, 43, 140, 51);
				panel_3.setVisible(false);
				panel_3_1.setVisible(false);
				panel_3_1_1.setVisible(false);
				panel_4.setVisible(false);
				panel_2.setVisible(false);
				panel_6.setVisible(false);
				panel_7.setVisible(true);
                                profile_panel.setBackground(new Color(244,255,81));
                                panel_7.setBackground(new Color(244,255,81));
                                header.setBackground(new Color(244,255,81));
                                welcome.setBackground(new Color(244,255,81));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_3.setBackground(new Color(224,224,224));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                panel_3.setBackground(new Color(128,128,128));
			}
		});
		panel_3_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                                welcome.removeAll();
				revalidate();
				repaint();
                                welcome.add(scrollpane);
                                welcome.add(scrollpane1);
                                welcome.add(scrollpane2);
                                welcome.add(scrollpane3);
                                scrollpane.getViewport().add(sujet);
                                scrollpane1.getViewport().add(theme);
                                scrollpane2.getViewport().add(description);
                                scrollpane3.getViewport().add(abstrait);
				welcome.add(panel_7);
                                welcome.add(submit_header);
                                welcome.add(submit_panel);
                                welcome.add(sujet_label);
                                welcome.add(theme_label);
                                welcome.add(description_label);
                                welcome.add(abstrait_label);
                                welcome.add(cv);
                                welcome.add(modifier_cv);
                                welcome.add(cv_label);
                                welcome.setBounds(0,108,786,554);
                                submit_header.setBounds(350, 0, 140, 65);
				submit_panel.setBounds(350, 56, 140, 60);
				panel_3.setVisible(false);
				panel_3_1.setVisible(false);
				panel_3_1_1.setVisible(false);
				panel_4.setVisible(false);
				panel_2.setVisible(false);
				panel_6.setVisible(false);
				panel_7.setVisible(true);
                                submit_panel.setBackground(new Color(161,252,255));
                                submit_header.setBackground(new Color(161,252,255));
                                welcome.setBackground(new Color(161,252,255));
                                panel_7.setBackground(new Color(161,252,255));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_3_1.setBackground(new Color(224,224,224));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                panel_3_1.setBackground(new Color(128,128,128));
			}
		});
		panel_3_1_1.addMouseListener(new MouseAdapter() {
                        @Override
			public void mouseClicked(MouseEvent e) {
                            welcome.removeAll();
                            revalidate();
                            repaint();
                            welcome.add(suivie_panel);
                            welcome.add(suivie_panel1);
                            welcome.add(panel_7);
                            welcome.add(affectation);
                            welcome.add(chercher_nom);
                            welcome.add(chercher_nom_affect);
                            welcome.add(full_list);
                            welcome.setBounds(0,108,786,554);
                            suivie_panel.setBounds(350, 0, 140, 65);
                            suivie_panel1.setBounds(350, 56, 140, 60);
                            panel_3.setVisible(false);
                            panel_3_1.setVisible(false);
                            panel_3_1_1.setVisible(false);
                            panel_4.setVisible(false);
                            panel_2.setVisible(false);
                            panel_6.setVisible(false);
                            panel_7.setVisible(true);
                            welcome.setBackground(new Color(8, 229, 163));
                            panel_7.setBackground(new Color(8, 229, 163));
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_3_1_1.setBackground(new Color(224,224,224));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                panel_3_1_1.setBackground(new Color(128,128,128));
			}
		});
		panel_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				welcome.removeAll();
				revalidate();
				repaint();
				welcome.add(panel_5);
				welcome.add(panel_8);
				welcome.add(panel_7);
				welcome.add(homescreen);
                                welcome.setBounds(0,108,786,554);
				homescreen.setBounds(350, 0, 140, 76);
				panel_5.setBounds(0,139,800,415);
				Wallpaper.setBounds(0, 0, 800, 415);
				panel_8.setBounds(200, 77, 423, 60);
                                panel_12.setBounds(100,50,122,119);
                                panel_14.setBounds(270,50,122,119);
                                panel_16.setBounds(440,50,122,119);
                                panel_18.setBounds(100,219,122,119);
                                panel_20.setBounds(270,219,122,119);
                                panel_22.setBounds(440,219,122,119);
                                panel_24.setBounds(610,50,122,119);
                                panel_24.setVisible(true);
                                panel_26.setBounds(610,219,122,119);
                                panel_26.setVisible(true);
                                panel_3.setVisible(false);
                                panel_3_1.setVisible(false);
                                panel_3_1_1.setVisible(false);
                                panel_4.setVisible(false);
                                panel_2.setVisible(false);
                                panel_6.setVisible(false);
                                panel_7.setVisible(true);
                                panel_7.setBackground(new Color(232, 230, 241));
                                welcome.setBackground(new Color(232, 230, 241));
                                panel_13.setBackground(new Color(241,255,28));
				panel_15.setBackground(new Color(161,251,255));
				panel_17.setBackground(new Color(8,229,163));
				panel_19.setBackground(new Color(140, 229, 8));
				panel_21.setBackground(new Color(243,165,47));
				panel_23.setBackground(new Color(255,28,36));
				panel_25.setBackground(new Color(28,28,255));
				panel_27.setBackground(new Color(255,144,244));
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_11.setBackground(new Color(224,224,224));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                panel_11.setBackground(new Color(128,128,128));
			}
		});
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                                JOptionPane.showMessageDialog(null, "Déconnecté");
				dispose();
                                Login home = new Login();
                                home.frame.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_4.setBackground(new Color(224,224,224));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                panel_4.setBackground(new Color(128,128,128));
			}
		});
		welcome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                                welcome.setBounds(0,108,786,554);
				homescreen.setBounds(350, 0, 140, 76);
				panel_5.setBounds(0,139,800,415);
				Wallpaper.setBounds(0, 0, 800, 415);
				panel_8.setBounds(200, 77, 423, 60);
				header.setBounds(350, 0, 140, 65);
				profile_panel.setBounds(350, 56, 140, 60);
                                submit_header.setBounds(350, 0, 140, 65);
				submit_panel.setBounds(350, 56, 140, 60);
                                changer_photo.setBounds(580, 43, 140, 51);
				panel_12.setBounds(100,50,122,119);
				panel_14.setBounds(270,50,122,119);
				panel_16.setBounds(440,50,122,119);
				panel_18.setBounds(100,219,122,119);
				panel_20.setBounds(270,219,122,119);
				panel_22.setBounds(440,219,122,119);
				panel_24.setBounds(610,50,122,119);
				panel_24.setVisible(true);
				panel_26.setBounds(610,219,122,119);
				panel_26.setVisible(true);
				panel_3.setVisible(false);
				panel_3_1.setVisible(false);
				panel_3_1_1.setVisible(false);
				panel_4.setVisible(false);
				panel_2.setVisible(false);
				panel_6.setVisible(false);
				panel_7.setVisible(true);
			}
		});
		panel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					welcome.setBounds(0,108,786,554);
					homescreen.setBounds(350, 0, 140, 76);
					panel_5.setBounds(0,139,800,415);
					Wallpaper.setBounds(0, 0, 800, 415);
					panel_8.setBounds(200, 77, 423, 60);
					header.setBounds(350, 0, 140, 65);
                                        submit_header.setBounds(350, 0, 140, 65);
					profile_panel.setBounds(350, 56, 140, 60);
                                        submit_panel.setBounds(350, 56, 140, 60);
                                        changer_photo.setBounds(580, 43, 140, 51);
					panel_12.setBounds(100,50,122,119);
					panel_14.setBounds(270,50,122,119);
					panel_16.setBounds(440,50,122,119);
					panel_18.setBounds(100,219,122,119);
					panel_20.setBounds(270,219,122,119);
					panel_22.setBounds(440,219,122,119);
					panel_24.setBounds(610,50,122,119);
					panel_24.setVisible(true);
					panel_26.setBounds(610,219,122,119);
					panel_26.setVisible(true);
                                        
					panel_3.setVisible(false);
					panel_3_1.setVisible(false);
					panel_3_1_1.setVisible(false);
					panel_4.setVisible(false);
					panel_2.setVisible(false);
                                        panel_9.setVisible(false);
					panel_6.setVisible(false);
					panel_7.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		panel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					welcome.setBounds(218, 108, 568, 554);
					homescreen.setBounds(240, 0, 140, 76);
					panel_8.setBounds(97, 77, 423, 60);
					panel_5.setBounds(0, 139, 568, 415);
					header.setBounds(240, 0, 140, 65);
					profile_panel.setBounds(240, 56, 140, 60);
                                        submit_header.setBounds(240, 0, 140, 65);
					submit_panel.setBounds(240, 56, 140, 60);
                                        changer_photo.setBounds(418, 43, 140, 51);
					panel_12.setBounds(50,50,122,119);
					panel_14.setBounds(210,50,122,119);
					panel_16.setBounds(370,50,122,119);
					panel_18.setBounds(50,219,122,119);
					panel_20.setBounds(210,219,122,119);
					panel_22.setBounds(370,219,122,119);
                                        panel_2.add(panel_3);
                                        panel_2.add(panel_3_1);
                                        panel_2.add(panel_3_1_1);
                                        panel_2.add(panel_4);
                                        panel_2.add(panel_11);
                                        panel_2.add(panel_6);
                                        panel_2.add(panel_10);
                                        panel.add(panel_9);
                                        panel.add(panel_2);
					panel_24.setVisible(false);
					panel_26.setVisible(false);
					panel_3.setVisible(true);
					panel_3_1.setVisible(true);
					panel_3_1_1.setVisible(true);
					panel_4.setVisible(true);
					panel_2.setVisible(true);
					panel_6.setVisible(true);
					panel_7.setVisible(false);
                                        panel_9.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		panel_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				Desktop browser = Desktop.getDesktop();
		        try{
		            browser.browse(new java.net.URI("http://ensias.um5.ac.ma/"));
		        }
		        catch(Exception e2){
		        	e2.printStackTrace();
		        }
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_10.setBackground(new Color(224,224,224));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                panel_10.setBackground(new Color(128,128,128));
			}
		});
		panel_13.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            int i=0;
                            welcome.removeAll();
                            revalidate();
                            repaint();
                            welcome.add(panel_7);
                            welcome.add(header);
                            welcome.add(profile_panel);
                            while(i<11){
                                welcome.add(profile_text[i]);
                                welcome.add(profile_labels[i]);
                                i++;
                            }
                            welcome.add(modifier);
                            welcome.add(changer_photo); 
                            welcome.setBounds(0,108,786,554);
                            header.setBounds(350, 0, 140, 65);
                            profile_panel.setBounds(350, 56, 140, 60);
                            changer_photo.setBounds(580, 43, 140, 51);
                            panel_3.setVisible(false);
                            panel_3_1.setVisible(false);
                            panel_3_1_1.setVisible(false);
                            panel_4.setVisible(false);
                            panel_2.setVisible(false);
                            panel_6.setVisible(false);
                            panel_7.setVisible(true);
                            header.setBackground(new Color(244,255,81));
                            panel_7.setBackground(new Color(244,255,81));
                            profile_panel.setBackground(new Color(244,255,81));
                            welcome.setBackground(new Color(244,255,81));
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_13.setBackground(new Color(244,255,191));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_13.setBackground(new Color(241,255,28));
			}
		});
		panel_15.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            welcome.removeAll();
				revalidate();
				repaint();
                                welcome.add(scrollpane);
                                welcome.add(scrollpane1);
                                welcome.add(scrollpane2);
                                welcome.add(scrollpane3);
                                scrollpane.getViewport().add(sujet);
                                scrollpane1.getViewport().add(theme);
                                scrollpane2.getViewport().add(description);
                                scrollpane3.getViewport().add(abstrait);
				welcome.add(panel_7);
                                welcome.add(submit_header);
                                welcome.add(submit_panel);
                                welcome.add(sujet_label);
                                welcome.add(theme_label);
                                welcome.add(description_label);
                                welcome.add(abstrait_label);
                                welcome.add(cv);
                                welcome.add(modifier_cv);
                                welcome.add(cv_label);
                                welcome.setBounds(0,108,786,554);
                                submit_header.setBounds(350, 0, 140, 65);
                                submit_panel.setBounds(350, 56, 140, 60);
                                panel_3.setVisible(false);
                                panel_3_1.setVisible(false);
                                panel_3_1_1.setVisible(false);
                                panel_4.setVisible(false);
                                panel_2.setVisible(false);
                                panel_6.setVisible(false);
                                panel_7.setVisible(true);
                                submit_panel.setBackground(new Color(161,252,255));
                                submit_header.setBackground(new Color(161,252,255));
                                welcome.setBackground(new Color(161,252,255));
                                panel_7.setBackground(new Color(161,252,255));
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_15.setBackground(new Color(215,253,255));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_15.setBackground(new Color(161,251,255));
			}
		});
		panel_17.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            welcome.removeAll();
                            revalidate();
                            repaint();
                            welcome.add(suivie_panel);
                            welcome.add(suivie_panel1);
                            welcome.add(panel_7);
                            welcome.add(affectation);
                            welcome.add(chercher_nom);
                            welcome.add(chercher_nom_affect);
                            welcome.add(full_list);
                            welcome.setBounds(0,108,786,554);
                            suivie_panel.setBounds(350, 0, 140, 65);
                            suivie_panel1.setBounds(350, 56, 140, 60);
                            panel_12.setBounds(100,50,122,119);
                            panel_14.setBounds(270,50,122,119);
                            panel_16.setBounds(440,50,122,119);
                            panel_18.setBounds(100,219,122,119);
                            panel_20.setBounds(270,219,122,119);
                            panel_22.setBounds(440,219,122,119);
                            panel_24.setBounds(610,50,122,119);
                            panel_24.setVisible(true);
                            panel_26.setBounds(610,219,122,119);
                            panel_26.setVisible(true);
                            panel_3.setVisible(false);
                            panel_3_1.setVisible(false);
                            panel_3_1_1.setVisible(false);
                            panel_4.setVisible(false);
                            panel_2.setVisible(false);
                            panel_6.setVisible(false);
                            panel_7.setVisible(true);
                            welcome.setBackground(new Color(8, 229, 163));
                            panel_7.setBackground(new Color(8, 229, 163));
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_17.setBackground(new Color(201,255,226));
				
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_17.setBackground(new Color(8,229,163));
				
			}
		});
		panel_19.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                        if(isaccepted(id)==true){
                            welcome.removeAll();
                            revalidate();
                            repaint();
                            welcome.add(avancements_panel);
                            welcome.add(avancements_panel1);
                            welcome.add(panel_7);
                            welcome.add(pg);
                            welcome.add(ajouter_avancement_panel);
                            welcome.add(check_avancement_panel);
                            welcome.setBounds(0,108,786,554);
                            avancements_panel.setBounds(350, 0, 140, 65);
                            avancements_panel1.setBounds(310, 56, 170, 60);
                            panel_3.setVisible(false);
                            panel_3_1.setVisible(false);
                            panel_3_1_1.setVisible(false);
                            panel_4.setVisible(false);
                            panel_2.setVisible(false);
                            panel_6.setVisible(false);
                            panel_7.setVisible(true);
                            welcome.setBackground(new Color(140,229,8));
                            panel_7.setBackground(new Color(140,229,8));
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_19.setBackground(new Color(203, 255, 122));
				
				
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_19.setBackground(new Color(140, 229, 8));
			}
		});
		panel_21.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            if(isaccepted(id)==true){
                                welcome.removeAll();
                                revalidate();
                                repaint();
                                welcome.setBounds(0,108,786,554);
                                welcome.add(panel_7);
                                welcome.add(taches_panel);
                                welcome.add(taches_panel1);
                                welcome.add(taches);
                                welcome.setBackground(new Color(243,165,47));
                                panel_7.setBackground(new Color(243,165,47));
                                taches_panel.setBounds(350, 0, 140, 65);
                                taches_panel1.setBounds(350, 56, 140, 60);
                                panel_3.setVisible(false);
                                panel_3_1.setVisible(false);
                                panel_3_1_1.setVisible(false);
                                panel_4.setVisible(false);
                                panel_2.setVisible(false);
                                panel_6.setVisible(false);
                                panel_7.setVisible(true);
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                            else{    
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_21.setBackground(new Color(255,209,138));
                            }
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
                                setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_21.setBackground(new Color(255,209,138));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_21.setBackground(new Color(243,165,47));
			}
		});
		panel_23.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                                welcome.removeAll();
                                revalidate();
                                repaint();
                                welcome.setBounds(0,108,786,554);
                                welcome.add(FAQ_panel);
                                welcome.add(FAQ_panel1);
                                welcome.add(panel_7);
                                welcome.add(about);
                                welcome.add(about_app);
                                about_app.getViewport().add(about_text);
                                FAQ_panel.setBounds(350, 0, 140, 65);
                                FAQ_panel1.setBounds(358, 56, 140, 60);
                                panel_3.setVisible(false);
                                panel_3_1.setVisible(false);
                                panel_3_1_1.setVisible(false);
                                panel_4.setVisible(false);
                                panel_2.setVisible(false);
                                panel_6.setVisible(false);
                                panel_7.setVisible(true);
                                welcome.setBackground(new Color(255,28,36));
                                panel_7.setBackground(new Color(255,28,36));
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_23.setBackground(new Color(255,135,139));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_23.setBackground(new Color(255,28,36));
			}
		});
		panel_25.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            if(isaccepted(id)==true){
                                welcome.removeAll();
                                revalidate();
                                repaint();
                                welcome.add(contact_panel);
                                welcome.add(contact_panel1);
                                welcome.add(panel_7);
                                welcome.add(sujet_text);
                                welcome.add(sujet_contact_label);
                                welcome.add(contact);
                                welcome.add(message_label);
                                welcome.add(envoyer);
                                contact.getViewport().add(message);
                                contact.enableInputMethods(true);
                                message.enableInputMethods(true);
                                welcome.setBounds(0,108,786,554);
                                contact_panel.setBounds(350, 0, 140, 65);
                                contact_panel1.setBounds(344, 56, 140, 60);
                                panel_7.setBackground(new Color(28,28,255));
                                welcome.setBackground(new Color(28,28,255));
                            }
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_25.setBackground(new Color(161,161,255));
				
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_25.setBackground(new Color(28,28,255));
			}
		});
		panel_27.addMouseListener(new MouseAdapter() {
                        @Override 
                        public void mouseClicked(MouseEvent e){
                            if(isaccepted(id)==true){
                                welcome.removeAll();
                                revalidate();
                                repaint();
                                welcome.add(labo_panel);
                                welcome.add(labo_panel1);
                                welcome.add(panel_7);
                                welcome.add(refresh_labo);
                                welcome.add(labo_app);
                                welcome.add(combobox);
                                welcome.add(reserver);
                                welcome.add(annuler);
                                welcome.add(mes_reservations);
                                welcome.setBounds(0,108,786,554);
                                labo_panel.setBounds(350, 0, 140, 65);
                                labo_panel1.setBounds(350, 56, 140, 60);
                                panel_3.setVisible(false);
                                panel_3_1.setVisible(false);
                                panel_3_1_1.setVisible(false);
                                panel_4.setVisible(false);
                                panel_2.setVisible(false);
                                panel_6.setVisible(false);
                                panel_7.setVisible(true);
                                welcome.setBackground(new Color(255, 144, 244));
                                panel_7.setBackground(new Color(255, 144, 244));
                                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
			@Override 
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				panel_27.setBackground(new Color(255,198,249));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				panel_27.setBackground(new Color(255,144,244));
			}
		});
	}
    }

