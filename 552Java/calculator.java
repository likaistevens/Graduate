package session04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
    final version
    * have fixed bugs that "." and "+/-"  not work     in sep.25
 * @author kaili
 */
public class calculator41 extends JFrame{
    public double p;
    public double q;
    public double result;
    public int s;
    public StringBuilder sb = new StringBuilder();
    public calculator41(int w, int h){
   
    String[] str = new String[]{
    "0","1","2","3","4","5","6","7","8","9","AC","+/-","%","/","x","-","+",".","="
    };
    setTitle ("calculator");
    setSize(w,h);

    JPanel P = new JPanel();
    getContentPane().add(P);
    P.setLayout(null);

    JTextArea ta = new JTextArea();
    //ta.setLayout(new GridLayout());
    Font f1=new Font ("Times",Font.BOLD,40);
    ta.setFont(f1);
    P.add(ta);
    ta.setBounds(5,10,230,40);
    //jf.add(BorderLayout.NORTH,ta);
    //JPanel p2 = new JPanel();
    //p2.setLayout(null);
    
    for (int i=0; i<19; i++){
        String name=str[i];
        JButton[] b =new JButton[19];
        b[i] = new JButton(str[i]+"");
        
        //JButton b = new JButton(str[i]+" ");
        Font f2=new Font ("Times",Font.BOLD,25);
        b[i].setFont(f2);
        
        if (i==0){
            b[i].setBounds(0,220,120,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            sb.append("0");
                ta.setText(sb.toString());
            }
            });
        }
        else if (i==10){                    // AC
            b[i].setBounds(0,60,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            sb=new StringBuilder();
            p=0.00;
            q=0.00;
            ta.setText("  ");
            }
            });
        }   
        else if (i==11){                    // +/-         not work
            b[i].setBounds(60,60,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
            sb.append("-");
            ta.setText(sb.toString());
            }
            });
        }   
        else if (i==12){                    // %
            b[i].setBounds(120,60,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            ta.setText("%");
            s=12;
            p = Double.parseDouble(sb.toString());
            sb=new StringBuilder();
            }
            });
        }
        else if (i==13){                    // ÷
            b[i].setBounds(180,60,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            ta.setText("/");
            s=13;
            p = Double.parseDouble(sb.toString());
            sb=new StringBuilder();
            }
            });
        }   
        else if (i==14){                    // ×
            b[i].setBounds(180,100,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            ta.setText("x");
            s=14;
            p = Double.parseDouble(sb.toString());
            sb=new StringBuilder();
            }
            });
        }
        else if (i==15){                    // -
            b[i].setBounds(180,140,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            ta.setText("-");
            s=15;
            p = Double.parseDouble(sb.toString());
            sb=new StringBuilder();
            }
            });
        }
        else if (i==16){                    // + add
            b[i].setBounds(180,180,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            ta.setText("+");
            s=16;
            p = Double.parseDouble(sb.toString());
            sb=new StringBuilder();
            }
            });
        }
        else if (i==17){                    // .        not work
            b[i].setBounds(120,220,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
            sb.append(".");
            ta.setText(sb.toString());
            }
            });
        }
        else if (i==18){                    // =
            b[i].setBounds(180,220,60,40);
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println(p+"="+q+"="+s);
                if (s==12){
                q = Double.parseDouble(sb.toString());
                result=p%q;
                }
                else if (s==13){
                q = Double.parseDouble(sb.toString());
                result=p/q;
                }
                else if (s==14){
                q = Double.parseDouble(sb.toString());
                result=p*q;
                }
                else if (s==15){
                q = Double.parseDouble(sb.toString());
                result=p-q;
                }
                else if (s==16){
                q = Double.parseDouble(sb.toString());
                result=p+q;
                //System.out.println(result);
                }
                s=18;
                ta.setText(result+"");
                sb=new StringBuilder();
                p=0.00;
                q=0.00;
            }
            });
        }
        
        else{ 
            b[i].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            sb.append(name);
            ta.setText(sb.toString());
            }
            });
            int m=((i-1)%3)*60;
            int n=(5-(i+2)/3)*40+20;
            b[i].setBounds(m,n,60,40);
        }
    P.add(b[i]);
     /*if (s==18){
        result=p+q;
        ta.setText(result.toString);
    }*/
    }
// jf.add(BorderLayout.CENTER, p2);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    }
    
public static void main(String[] args){
    new calculator41 (240,300);
}  
    
    
}
