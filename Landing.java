import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Landing extends JFrame {
    Landing() {
        Font f = new Font("futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        JLabel l1 = new JLabel("Virtual Banking System", JLabel.CENTER);
        JButton b1 = new JButton("Admin");
        JButton b2 = new JButton("Existing Customer");
        JButton b3 = new JButton("New Customer");

        l1.setFont(f);
        b1.setFont(f2);
        b2.setFont(f2);
        b3.setFont(f2);

        //background color
        b1.setBackground(new Color(27, 153, 250));
        b2.setBackground(new Color(27, 153, 250));
        b3.setBackground(new Color(27, 153, 250));

        //foreground
        b1.setForeground(Color.white);
        b2.setForeground(Color.white);
        b3.setForeground(Color.white);

        Container c = getContentPane();
        c.setLayout(null);

        l1.setBounds(150, 50, 500, 50);
        b1.setBounds(300, 150, 200, 50);
        b2.setBounds(300, 230, 200, 50);
        b3.setBounds(300, 310, 200, 50);

        c.add(l1);
        c.add(b1);
        c.add(b2);
        c.add(b3);

        b1.addActionListener(
                a->{
                    new Alogin();
                    dispose();
                }
        );
        b2.addActionListener(
                a->{
                    new Elogin();
                    dispose();
                }
        );
        b3.addActionListener(
                a->{
                    new Nlogin();
                    dispose();
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Landing Page");

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
        gradientPanel.add(b1);
        gradientPanel.add(b2);
        gradientPanel.add(b3);
    }


    public static void main(String[] args) {
        Landing a = new Landing();
    }
}
