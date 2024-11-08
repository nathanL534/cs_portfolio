


import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


class Diagram extends JFrame implements MouseListener{
    JFrame frame = new JFrame();
    private ArrayList<Point> redPoints;
    private ArrayList<Point> bluePoints;
    private Toolkit kit;
    private String color ="red";
    private Function func;
    private Perceptron perceptron;

    public Diagram(){
        redPoints = new ArrayList<>();
        bluePoints = new ArrayList<>();


        setSize(400, 400);
        kit = getToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation((screenSize.width - getWidth())/2, (screenSize.height - getHeight())/2);
        setTitle("Graphics on a JPanel");
        JPanel panel = new DPanel();
        add(panel, BorderLayout.CENTER);
        JButton button = new JButton();
        JButton slopeDrawer = new JButton();
        JButton clear = new JButton();
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       
        button.setText("Color");
        slopeDrawer.setText("slope");
        clear.setText("clear");

        buttonPanel.add(slopeDrawer);
        buttonPanel.add(button);
        buttonPanel.add(clear);
        
        add(buttonPanel, BorderLayout.NORTH);
        button.setBounds(200, 100, 100, 50);
        button.addActionListener(e -> changeColor());
        slopeDrawer.addActionListener(e -> drawSlope());
        clear.addActionListener(e -> clear());
        

        panel.setLayout (null);
        panel.addMouseListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private class DPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.PINK);
            g.fillOval(0, 0, 5, 5);
            g.fillOval(400, 400, 5, 5);
            for (Point p : redPoints) {
                paintPoint(g, (int)p.getX(), (int)p.getY(), Color.RED);
    
            }
            for( Point p : bluePoints){
                paintPoint(g, (int) p.getX(), (int) p.getY(), Color.BLUE);
            }
            if(func != null){
                g.setColor(Color.YELLOW);
                int x1 = 0;
                int y1 =(int) func.getYIntercept();
                int x2 = 400;
                int y2 =(int) (400*func.getSlope() + func.getYIntercept());
                System.out.println(y1);
                for(double weight: perceptron.getWeights()){
                    System.out.println(weight);
                    
                }
                
                // g.drawLine(x1, y1, x2, y2);
                g.drawLine(x1, y1, x2, y2);
            }

        }
        public void paintPoint(Graphics g, int x, int y, Color pointColor){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(pointColor);
            g2d.setStroke(new BasicStroke(5));
            g2d.fillOval(x, y, 5, 5);
    
        }
        


    }
    public void drawSlope(){
        if(func == null){
            begin();
        }
        perceptron.check();
        
        repaint();
        
    }
    public void clear(){
        func = null;
        redPoints.clear();
        bluePoints.clear();
        repaint();

    }
    public void changeColor(){
        if(color.equals("blue")){
            color = "red";

        }
        else
            color = "blue";


    }
    @Override
    public void paintComponents(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponents(g);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (color.equals("red")) {
            redPoints.add(new Point(e.getX(), e.getY()));
        } else {
            bluePoints.add(new Point(e.getX(), e.getY()));
        }
        
       
        repaint();
        
        // TODO Auto-generated method stub
       
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    public void begin(){
        perceptron = new Perceptron();
        perceptron.input(bluePoints, redPoints);
        perceptron.printPoints();
        perceptron.calculate();
        this.func = perceptron.getFunction();

        
        repaint();

        
    }
    public static void main(String[] args) {
        Diagram myFrame = new Diagram();
        myFrame.setVisible(true);
        



    }
    
}