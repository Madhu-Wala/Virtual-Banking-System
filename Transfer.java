import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Transfer extends JFrame {
    double fetchbalance(String username){
        double balance=0.0;
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        try(Connection con= DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
            String sql="select balance from users where username=?";
            try (PreparedStatement pst=con.prepareStatement(sql)) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return balance;
    }
    void updatebalance(String username,double total){
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        try(Connection con=DriverManager.getConnection(url,"DB_username","DB_password")){ //enter your username and password
            String sql="update users set balance=? where username=?";
            try(PreparedStatement pst=con.prepareStatement(sql)){
                pst.setDouble(1,total);
                pst.setString(2,username);
                pst.executeUpdate();

            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    Transfer(String username) {
        Font f = new Font("Arial Rounded MT Bold", Font.BOLD, 30);
        Font f2 = new Font("Candara", Font.PLAIN, 18);

        JLabel title = new JLabel("Transfer Funds", JLabel.CENTER);
        JLabel l1 = new JLabel("Receiver:");
        JTextField t1 = new JTextField(10);

        JLabel l2 = new JLabel("Amount:");
        JTextField t2 = new JTextField(10);

        JButton b1 = new JButton("Transfer");
        JButton b2 = new JButton("Back");

        title.setFont(f);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        b1.setForeground(new Color(217, 234, 252));
        b2.setForeground(new Color(27, 33, 227));

        b1.setBackground(new Color(27, 33, 227));
        b2.setBackground(new Color(247, 245, 220));

        Container c = getContentPane();
        c.setLayout(null);

        int labelX = 200, fieldX = 400, yStart = 80, width = 150, height = 30, gap = 40;

        title.setBounds(250, 20, 300, 40);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(fieldX, yStart, width, height);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(fieldX, yStart + gap, width, height);

        b1.setBounds(250, yStart + 2 * gap, 120, 40);
        b2.setBounds(400, yStart + 2 * gap, 120, 40);

        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
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
                    String receiver=t1.getText();
                    String s2=t2.getText();//transfer amt
                    if(receiver.isEmpty() || s2.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Field cannot be empty");
                        t1.setText("");
                        t2.setText("");
                        return;
                    }

                    //Round 1 check senders balance and deduct amount
                    double balance=fetchbalance(username);
                    double amount=Double.parseDouble(s2);
                    double total;
                    if(amount>balance){
                        JOptionPane.showMessageDialog(null,"Insufficient balance");
                        return;
                    }

                    total=balance-amount;

                    //Round 2 update senders balance
                    updatebalance(username,total);
                    updatepassbook(username,"Transfer to "+receiver,-amount,total);
                    //Round 3 Check balance of receiver and add amount
                    balance=fetchbalance(receiver);
                    total=balance+amount;

                    //Round 4 update receivers balance
                    updatebalance(receiver,total);
                    updatepassbook(receiver,"Transfer from "+username,amount,total);
                    JOptionPane.showMessageDialog(null,"Transaction successful");
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Transfer Funds");

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
        gradientPanel.add(l1);
        gradientPanel.add(t1);
        gradientPanel.add(t2);
        gradientPanel.add(b1);
        gradientPanel.add(b2);
        gradientPanel.add(l2);
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
        new Transfer("Madhu");
    }
}

