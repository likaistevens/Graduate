package session05;
/**
 * This game named "Gomoku" or "Five in a Row"
 * All rules of this game are Implemented in this program.
 * @author kaili
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Game extends JFrame{
    public void buildMenus(){
        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("File");
        JMenuItem mi= new JMenuItem("New");
        setLocationRelativeTo(null);
        m.add(mi);
        mi.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    System.out.println("hello");
                    repaint();
                }
                }
        );
        
         mi= new JMenuItem("Rules");
        mi.addActionListener(new ActionListener(){   
                public void actionPerformed(ActionEvent e){
                String rules="Players alternate turns placing a stone of their color on an empty intersection. "
                        +"\n"+ "The winner is the first player to form an unbroken chain "
                        + "of five stones horizontally, vertically, or diagonally."
                        +"\n"+"Engjoy Your Game!";
                JOptionPane.showMessageDialog(null, rules,"Gomoku",JOptionPane.YES_NO_OPTION);  
                }
                }
        );
        m.add(mi);
        
        mi = new JMenuItem("Quit");
        mi.addActionListener(new ActionListener(){   
                public void actionPerformed(ActionEvent e){
                    new TipWindow();
                    //System.exit(0);
                }
                }
        );
        m.add(mi);
        
       
        mb.add(m);
        setJMenuBar(mb);
    }
    
    public Game(){                        //  构造方法 。构造方法=pulic+类名 。 用来初始化
        super("chess");
        //setTitle("chess");
        setSize(740,785);
        buildMenus();
        GameBoard b = new GameBoard();

        Container c= getContentPane();
        c.add(BorderLayout.CENTER,b);
        b.setBackground(Color.blue);

        setVisible(true);
        }

    public static void main(String[] args){
        Game g =  new Game();
    }
}

class GameBoard extends JPanel{
    static int c=-1;
    int DX,DY;
    int[][] a = new int[1000][1000];
    public GameBoard(){
    MoveListener m = new MoveListener();
    addMouseListener(m);
  //  addMouseMotionListener(m);
    }

    public void paint(Graphics g){
        //g.clearRect();
       // super.paint(g);
        Image image = new ImageIcon("image/s.jpg").getImage();
        g.drawImage(image,0,0,740,785, this);
        
        for (int ax = 0; ax<19; ax++){
             g.drawLine(10,10+ax*40,730,10+ax*40);
            }
        for (int ay = 0; ay<19; ay++){
             g.drawLine(10+ay*40,10,10+ay*40,730);
            }
        
    }
    
   private class MoveListener implements MouseListener,MouseMotionListener{
     //   private int lastX,lastY;

        @Override
        public void mouseClicked(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
            Graphics g = getGraphics();   
            DX=(e.getX()+20)/40+5;
            DY=(e.getY()+20)/40+5;
            
            if(a[DX][DY]!=1&&a[DX][DY]!=-1){
                a[DX][DY]=c;
                if(c==-1){
                    g.setColor(Color.BLACK);
                    g.fillOval((DX-5)*40+10-18,(DY-5)*40+10-18,36,36);  
                }
                if(c==1){
                    g.setColor(Color.WHITE);
                    g.fillOval((DX-5)*40+10-18,(DY-5)*40+10-18,36,36);
                }
            gameover(DX,DY);
           
            c=-1*c;
            }
            else{
                 a[DX][DY]=0;
          
            //JOptionPane.showMessageDialog(null, "There are already chess pieces here！","WARNING",JOptionPane.ERROR_MESSAGE);

            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        //remove the piece from the original board location
        //add to the new location
        //board[i][j]..
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
        @Override
        public void mouseDragged(MouseEvent e) {
//            Graphics g = getGraphics(); 
//            g.setXORMode(Color.WHITE);
//            g.drawOval(lastX, lastY, 35, 35);
//            g.drawOval(e.getX(), e.getY(), 35, 35);
//            lastX = e.getX();
//            lastY = e.getY();
        }
        @Override
        public void mouseMoved(MouseEvent e) {
        }

    public void gameover(int DX,int DY){
           int countx=0,county=0;
           int countxy1=0,countxy2=0;
           int x=DX-1;
           int y=DY-1;
           
           while(a[x][DY]==c){      //  horizon
              countx++;
               x-=1;
           }
           x=DX+1;
           while(a[x][DY]==c){
              countx++;
               x+=1;
           }
           
           while(a[DX][y]==c){      //  vertical
              county++;
               y-=1;
           }
           y=DY+1;
           while(a[DX][y]==c){
              county++;
               y+=1;
           }
           
           x=DX-1;                  //   \
           y=DY-1;
           while(a[x][y]==c){
              countxy1++;
              x-=1; 
              y-=1;
           }
           x=DX+1;
           y=DY+1;
           while(a[x][y]==c){
              countxy1++;
               x+=1;
               y+=1;
           }
           
           x=DX-1;                  //  /
           y=DY+1;
            while(a[x][y]==c){
              countxy2++;
              x-=1; 
              y+=1;
           }
           x=DX+1;
           y=DY-1;
           while(a[x][y]==c){
              countxy2++;
               x+=1;
               y-=1;
           }
        //   System.out.println(x+"-"+y+"-"+DX+"-"+DY+"-"+countx+"-"+county+"-"+countxy1+"-"+countxy2);

            if(countx>=4||county>=4||countxy1>=4||countxy2>=4){
                if(c==-1)
                JOptionPane.showMessageDialog(null, "Black Win!","Game Over",JOptionPane.PLAIN_MESSAGE);
                else
                JOptionPane.showMessageDialog(null, "White Win!","Game Over",JOptionPane.PLAIN_MESSAGE);
            }
    }  
 
     
    }
       
}

class TipWindow extends JFrame{
    public TipWindow(){
    JFrame f = new JFrame();
    setTitle("Warining");
    f.setLayout(null);
    setSize(300,200);
    setLocationRelativeTo(null);
    JLabel label = new JLabel("Are you sure to quit this game?");
    Font f1 = new Font("Times",Font.BOLD,20);
    label.setFont(f1);
    JButton b1 = new JButton("YES");
    JButton b2 = new JButton("Cancel");
    JPanel p = new JPanel();
    getContentPane().add(p);
    p.setLayout(null);
    p.add(b1);
    p.add(b2);
    p.add(label);
    label.setBounds(10,10,300,100);
    b1.setBounds(25,100,100,50);
    b2.setBounds(175,100,100,50);

    b1.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        System.exit(0);
        }
    });
    b2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        dispose();
        }
    });
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

    
