import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Profile extends JFrame {
    String newusername="";
    Profile(String username) {
        Font f = new Font("Arial Rounded MT Bold", Font.BOLD, 35);
        Font f2 = new Font("Candara", Font.PLAIN, 20);

        JLabel title = new JLabel("Profile Settings", JLabel.CENTER);
        title.setFont(f);

        JLabel l1 = new JLabel("Select Field to Update:");
        JComboBox<String> box = new JComboBox<>(new String[]{"Username", "Password", "Phone", "Email"});

        JLabel l2 = new JLabel("Enter New Value:");
        JTextField t1 = new JTextField(15);

        JButton b1 = new JButton("Update");
        JButton b2 = new JButton("Back");
        b1.setForeground(new Color(217, 234, 252));
        b2.setForeground(new Color(27, 33, 227));

        b1.setBackground(new Color(27, 33, 227));
        b2.setBackground(new Color(247, 245, 220));
        l1.setFont(f2);
        box.setFont(f2);
        l2.setFont(f2);
        t1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        title.setBounds(250, 20, 300, 40);
        l1.setBounds(200, 100, 200, 30);
        box.setBounds(400, 100, 200, 30);
        l2.setBounds(200, 160, 200, 30);
        t1.setBounds(400, 160, 200, 30);
        b1.setBounds(250, 220, 120, 40);
        b2.setBounds(400, 220, 120, 40);

        c.add(title);
        c.add(l1);
        c.add(box);
        c.add(l2);
        c.add(t1);
        c.add(b1);
        c.add(b2);

        b1.addActionListener(
                a->{
                    String s1=box.getSelectedItem().toString().toLowerCase();
                    String s2=t1.getText();

                    //part1 check if to be updated value field is empty
                    if(s2.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Cannot be empty");
                        return;
                    }
                    else{

                        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                        try(Connection con=DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
                            String sql="update users set "+s1+" =? where username=?";
                            try(PreparedStatement pst=con.prepareStatement(sql)){
                                pst.setString(1,s2);
                                pst.setString(2,username);
                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Updated successfully");
                                t1.setText("");
                            }
                        }
                        catch (Exception e){
                            JOptionPane.showMessageDialog(null,e.getMessage());
                            return;
                        }
                        if(s1.equals("username")){
                            dispose();
                            new Profile(s2);
                            try(Connection con=DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
                                String sql="update transactions set "+s1+" =? where username=?";
                                try(PreparedStatement pst=con.prepareStatement(sql)){
                                    pst.setString(1,s2);
                                    pst.setString(2,username);
                                    pst.executeUpdate();

                                }
                            }
                            catch (Exception e){
                                JOptionPane.showMessageDialog(null,e.getMessage());
                            }
                        }
                    }
                }
        );

        b2.addActionListener(
                a->{
                    new Home(username);
                }
        );
        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Profile Settings");

        // Set background and gradient
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(233, 236, 245);  // Dark Blue
                Color color2 = new Color(150, 210, 250);  // Light Blue
//                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight()); // Fill the panel with gradient
            }
        };

        // Set the custom gradient panel as the content pane
        setContentPane(gradientPanel);

        // Make sure to add components after the gradient is set
        gradientPanel.setLayout(null);
        gradientPanel.add(l1);
        gradientPanel.add(box);
        gradientPanel.add(t1);
        gradientPanel.add(title);
        gradientPanel.add(b1);
        gradientPanel.add(b2);
        gradientPanel.add(l2);
    }

    public static void main(String[] args) {
        new Profile("Darshu");
    }
}

