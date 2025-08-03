import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Withdraw extends JFrame
{
    Withdraw(String username)
    {
        Font f = new Font("Arial Rounded MT Bold", Font.BOLD, 40);
        Font f2 = new Font("Candara", Font.PLAIN, 22);

        JLabel title = new JLabel("Withdraw Money", JLabel.CENTER);
        JLabel label = new JLabel("Enter Amount:");
        JTextField t1 = new JTextField(10);
        JButton b1 = new JButton("Withdraw");
        JButton b2 = new JButton("Back");

        b1.setForeground(new Color(217, 234, 252));
        b2.setForeground(new Color(27, 33, 227));

        b1.setBackground(new Color(27, 33, 227));
        b2.setBackground(new Color(247, 245, 220));

        title.setFont(f);
        label.setFont(f2);
        t1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        title.setBounds(200, 30, 400, 50);
        label.setBounds(250, 120, 300, 30);
        t1.setBounds(250, 160, 300, 30);
        b1.setBounds(300, 220, 200, 40);
        b2.setBounds(300, 280, 200, 40);

        c.add(title);
        c.add(label);
        c.add(t1);
        c.add(b1);
        c.add(b2);

        b2.addActionListener(
                a->{
                    new Home(username);
                    dispose();
                }
        );
        b1.addActionListener(
                a->{
                    double total;
                    double wamount;
                    double balance=0.0;
                    double wlimit=0.0;
                    //Part1: connect to database and get balance and wimit
                    String url = "jdbc:oracle:thin:@localhost:1521:orcl";
                    try(Connection con=DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
                        String sql="select balance,wlimit from users where username=?";
                        try (PreparedStatement pst=con.prepareStatement(sql)) {
                            pst.setString(1, username);
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                balance = rs.getDouble("balance");
                                wlimit=rs.getDouble("wlimit");
                            }
                        }
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }

                    //part 2: check if null or wamount > balance and wamount<wlimit
                    String s1=t1.getText();
                    if(s1.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Withdrawal amount field cannot be empty");
                    }
                    else{
                        wamount=Double.parseDouble(s1);
                        if(wamount>balance){
                            JOptionPane.showMessageDialog(null,"Insufficient balance");
                        }
                        else if(wamount>wlimit){
                            JOptionPane.showMessageDialog(null,"Withdrawal Limit exceeded");

                        }
                        else{
                            total=balance-wamount;

                            //Part 3: do actual changes in database
                            try(Connection con=DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
                                String sql="update users set balance=? where username=?";
                                try(PreparedStatement pst=con.prepareStatement(sql)){
                                    pst.setDouble(1,total);
                                    pst.setString(2,username);
                                    pst.executeUpdate();
                                    JOptionPane.showMessageDialog(null,"Successfully withdrawn ");
                                    t1.setText("");
                                    updatepassbook(username,"Withdrawn",-wamount,total);
                                }
                            }
                            catch(Exception e){
                                JOptionPane.showMessageDialog(null,e.getMessage());
                            }
                        }
                    }
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Withdraw Money");

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
        gradientPanel.add(title);
        gradientPanel.add(t1);
        gradientPanel.add(label);
        gradientPanel.add(b1);
        gradientPanel.add(b2);

    }
    void updatepassbook(String username,String desc,double amount,double total){
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        try(Connection con=DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
            String sql="insert into transactions (username,description,amount,balance) values (?,?,?,?)";
            try(PreparedStatement pst=con.prepareStatement(sql)){
                pst.setString(1,username);
                pst.setString(2,desc);
                pst.setDouble(3,amount);
                pst.setDouble(4,total);
                pst.executeUpdate();

            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Withdraw("Darshu");
    }
}

