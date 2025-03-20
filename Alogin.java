import javax.swing.*;
import java.awt.*;

class Alogin extends JFrame {
    Alogin() {
        Font f = new Font("Arial Rounded MT Bold", Font.BOLD, 40);
        Font f2 = new Font("Candara", Font.PLAIN, 22);

        JLabel title = new JLabel("Admin Login", JLabel.CENTER);
        JLabel l1 = new JLabel("Enter Username");
        JTextField t1 = new JTextField(10 );
        JLabel l2 = new JLabel("Enter Password");
        JPasswordField p1 = new JPasswordField(10);
        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        b1.setForeground(new Color(217, 234, 252));
        b2.setForeground(new Color(27, 33, 227));

        b1.setBackground(new Color(27, 33, 227));
        b2.setBackground(new Color(247, 245, 220));

        title.setFont(f);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        p1.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        title.setBounds(250, 30, 300, 50);
        l1.setBounds(250, 100, 300, 30);
        t1.setBounds(250, 140, 300, 30);
        l2.setBounds(250, 200, 300, 30);
        p1.setBounds(250, 240, 300, 30);
        b1.setBounds(300, 300, 200, 40);
        b2.setBounds(300, 360, 200, 40);

        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(p1);
        c.add(b1);
        c.add(b2);

        b1.addActionListener(
                a->{
                    String password=new String(p1.getPassword());
                    if(t1.getText().equals("admin") && password.equals("pass")){
                        new Adashboard();
                        dispose();
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
        setTitle("Admin Login");
        //set background and gradient
        JPanel gradientPanel=new JPanel(){
            @Override
            protected  void paintComponent(Graphics  g){
                super.paintComponent(g);
                Graphics2D g2d=(Graphics2D) g;
                Color color1 = new Color(233, 236, 245);  // Dark Blue
                Color color2 = new Color(150, 210, 250);  // Light Blue
                GradientPaint gradient=new GradientPaint(0,0,color1,0,getHeight(),color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
        };
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);
        gradientPanel.add(l1);
        gradientPanel.add(l2);
        gradientPanel.add(t1);
        gradientPanel.add(p1);
        gradientPanel.add(b1);
        gradientPanel.add(b2);
    }

    public static void main(String[] args) {
        new Alogin();
    }
}
