import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Nlogin extends JFrame {
    Nlogin() {
        Font f = new Font("Arial Rounded MT Bold", Font.BOLD, 30);
        Font f2 = new Font("Candara", Font.PLAIN, 20);
        JLabel l1 = new JLabel("Set Username");
        JTextField t1 = new JTextField(10);

        JLabel l2 = new JLabel("Set Password");
        JTextField t2 = new JTextField(10);

        JLabel l3 = new JLabel("Confirm Password");
        JTextField t3 = new JTextField(10);

        JLabel l4 = new JLabel("Phone");
        JTextField t4 = new JTextField(15);

        JLabel l5 = new JLabel("Email");
        JTextField t5 = new JTextField(20);

        JLabel l6 = new JLabel("Gender");
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"male", "female", "other"});

        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        b1.setForeground(new Color(217, 234, 252));
        b2.setForeground(new Color(27, 33, 227));

        b1.setBackground(new Color(27, 33, 227));
        b2.setBackground(new Color(247, 245, 220));
        JLabel title = new JLabel("Signup", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        l3.setFont(f2);
        t3.setFont(f2);
        l4.setFont(f2);
        t4.setFont(f2);
        l5.setFont(f2);
        t5.setFont(f2);
        l6.setFont(f2);
        genderBox.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        int labelX = 200, fieldX = 400, yStart = 80, width = 150, height = 30, gap = 40;

        title.setBounds(300, 10, 200, 40);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(fieldX, yStart, width, height);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(fieldX, yStart + gap, width, height);

        l3.setBounds(labelX, yStart + 2 * gap, width, height);
        t3.setBounds(fieldX, yStart + 2 * gap, width, height);

        l4.setBounds(labelX, yStart + 3 * gap, width, height);
        t4.setBounds(fieldX, yStart + 3 * gap, width, height);

        l5.setBounds(labelX, yStart + 4 * gap, width, height);
        t5.setBounds(fieldX, yStart + 4 * gap, width, height);

        l6.setBounds(labelX, yStart + 5 * gap, width, height);
        genderBox.setBounds(fieldX, yStart + 5 * gap, width, height);

        b1.setBounds(250, yStart + 6 * gap, 120, 40);
        b2.setBounds(400, yStart + 6 * gap, 120, 40);

        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
        c.add(l3);
        c.add(t3);
        c.add(l4);
        c.add(t4);
        c.add(l5);
        c.add(t5);
        c.add(l6);
        c.add(genderBox);
        c.add(b1);
        c.add(b2);

        b1.addActionListener(
                a -> {
                    if (t2.getText().equals(t3.getText())) {
                        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                        try (Connection con = DriverManager.getConnection(url,"C##MAJAKAAM","majajava123")) {
                            String sql = "INSERT INTO users(username,password,phone,email,gender) VALUES(?, ? , ?, ?, ?)";
                            try (PreparedStatement pst = con.prepareStatement(sql)) {
                                pst.setString(1,t1.getText());
                                pst.setString(2,t2.getText());
                                pst.setString(3,t4.getText());
                                pst.setString(4,t5.getText());
                                pst.setString(5,genderBox.getSelectedItem().toString());

                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Signup Successful");
                                new Home(t1.getText());
                                dispose();
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords do not match");
                    }
                }
        );
        b2.addActionListener(
                a->{
                    new Landing();
                    dispose();
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Signup");

        //set background and gradient
        JPanel gradientPanel=new JPanel(){
          @Override
          protected void paintComponent(Graphics g){
              super.paintComponent(g);
              Graphics2D g2d=(Graphics2D) g;
              Color color1=new Color(233,236,245);
              Color color2=new Color(150,210,250);
              GradientPaint gradient=new GradientPaint(0,0,color1,0,getHeight(),color2);
              g2d.setPaint(gradient);
              g2d.fillRect(0, 0, getWidth(), getHeight());
          }
        };
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);
        gradientPanel.add(t1);
        gradientPanel.add(t2);
        gradientPanel.add(t3);
        gradientPanel.add(t4);
        gradientPanel.add(t5);
        gradientPanel.add(l1);
        gradientPanel.add(l2);
        gradientPanel.add(l3);
        gradientPanel.add(l4);
        gradientPanel.add(l5);
        gradientPanel.add(l6);
        gradientPanel.add(genderBox);
        gradientPanel.add(b1);
        gradientPanel.add(b2);
        gradientPanel.add(title);


    }

    public static void main(String[] args) {
        new Nlogin();
    }
}
