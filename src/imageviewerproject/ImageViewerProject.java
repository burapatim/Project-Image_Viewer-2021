package imageviewerproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageViewerProject {

    public static void main(String[] args) {
        MyFrame mf = new MyFrame();
    }

}

class MyFrame extends JFrame implements ComponentListener, MouseListener, ActionListener {

    MyFrameHelp mfH = new MyFrameHelp();
    JFileChooser fchOpen = new JFileChooser("C:/picture New");
    JMenuBar mnBar = new JMenuBar();
    JMenu mnFile = new JMenu("File");
    JMenu mnP = new JMenu(" + ");
    JMenu mnD = new JMenu(" - ");
    JMenuItem mnIHelp = new JMenuItem("About Image Viewer");
    JMenu mnHelp = new JMenu("Help");
    JMenuItem mnIOpen = new JMenuItem("Open");
    MyPanel mp = new MyPanel();
    int fWidth = 800, fHeight = 600;
    static String path;

    public MyFrame() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + File.separator + "iconP.png"));
        setTitle("IMAGE Viewer");
        setBounds(0, 0, fWidth, fHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //EXIT_ON_CLOSE คือ เมื่อกด x ตรงมุมขวาบนจะปิดทั้งหมดของโปรแกรม แต่ถ้าใช้ HIDE_ON_CLOSE จะแค่ซ่อนหน้าต่างเท่านั้น
        setLayout(null);
        setLocationRelativeTo(null);

        addComponentListener(this);      //เป็นListenerที่มี method componentResized() ใช้ตรวจจับการเปลี่ยนขนาดของ component

        mnBar.setBounds(0, 0, this.getWidth(), 30);                                    // สร้าง MenuBar
        mnBar.add(mnFile);
        mnBar.add(mnP);                                                                           //  นำ Menu + ไปใส่ใน MenuBar
        mnBar.add(mnD);                                                                                  //  นำ Menu - ไปใส่ใน MenuBar
        mnBar.add(mnHelp);
        mnFile.add(mnIOpen);
        mnHelp.add(mnIHelp);

        mnP.addMouseListener(this);                                                         //MenuItem ต้องผูกกับ MouseListenner เท่านั้น                   
        mnD.addMouseListener(this);                                                         //MenuItem ต้องผูกกับ MouseListenner เท่านั้น                   

        mnIOpen.addActionListener(this);                                                            //Menu ต้องผูกกับ ActionListener เท่านั้น                   
        mnIHelp.addActionListener(this);                                                            //Menu ต้องผูกกับ ActionListener เท่านั้น                   
        add(mnBar);
        add(mp);
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {                                                //เมื่อ ComponentResized เปลี่ยนขนาดจะทำอะไร
        mp.setSize(this.getWidth(), this.getHeight());                                                          //เมื่อ Frame เปลี่ยนขนาด Panel จะเปลี่ยน
        mnBar.setSize(this.getWidth(), 30);                                                                       // เมืื่อ Frame เปลี่ยนขนาด ความกว้างของ MenuBar จะเปลี่ยนตาม
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == mnP) {                                                                                     // เมื่อกดปุ่ม mnB หรือปุ่ม + 
            mp.imgB = mp.imgB + mp.img.getWidth(mp) * 5 / 100;                                           // ขนาดของ ความกว้างจะ บวกเพิ่มคิดเป็น  5 %
            mp.imgL = mp.imgL + mp.img.getHeight(mp) * 5 / 100;                                           // ขนาดของ ความสูงจะ  บวกเพิ่มคิดเป็น  5 %
            repaint();                                                                                                          //วาดรูปใหม่
        }
        if (e.getSource() == mnD) {                                                                                       // เมื่อกดปุ่ม mnB หรือปุ่ม -
            mp.imgB = mp.imgB - mp.img.getWidth(mp) * 5 / 100;                                              // ขนาดของ ความกว้างจะ ลดลงคิดเป็น  5 %
            mp.imgL = mp.imgL - mp.img.getHeight(mp) * 5 / 100;                                              // ขนาดของ ความสูงจะ  ลดลงคิดเป็น  5 %
            repaint();                                                                                                            //วาดรูปใหม่
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mnIOpen) {                                                                     // เมื่อกดปุ่ม mnIOpen (Open) 
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Picture", "png", "gif", "bmp", "jpg");              //ให้จำกัดว่าจะใช้ชนิดไฟล์ แบบไหนบ้าง ใส่ไว้ในตัวแปร filter
            fchOpen.setFileFilter(filter);     //เอาฟิวเตอร์ไปใส่

            fchOpen.setDialogTitle("Open");                                     //set title 
            int retureVal = fchOpen.showOpenDialog(null);                       //ให้แสดงอยู่ตรงกลาง
            if (retureVal == 0) {
                try {
                    File file = fchOpen.getSelectedFile();                              // นำไฟล์ที่เลือก ไปเก็บไว้ในตัวแปร File
                    System.out.println(file);
                    path = file.toString();                                                 // นำ Part ของไฟล์ที่เลือกเปลี่ยนเป็น String  แล้วนำไปเก็บไว้ในตัวแปร path
                    mp.img = Toolkit.getDefaultToolkit().createImage(MyFrame.path);     //นำรูปภาพไปเก็บไว้ในตัวแปร img 
                    setTitle(file.getName());                               //เอาชื่อของไฟล์ที่เลือกไปแสดงบน title bar
                    repaint();                       //วาดใหม่  จะมีการวาดใหม่ อยู่ 2กรณีคือ เมื่อ component มีการเปลี่ยนแปลง หรือใช้คำสั่ง repaint();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else if (retureVal == 1) {
                System.out.println("File is not Collect");

            }

        }
        if (e.getSource() == mnIHelp) {                                                    //ถ้ากดปุ่มmenuItem ใน Help
            mfH.setVisible(true);                                                                       //แสดงเฟรมที่ สร้างไว้ขึ้นมา
            System.out.println("sfdsf");
        }
    }

}

class MyPanel extends JPanel {        //การจะเรียกใช้ method paintComponent() เพื่อวาดรูปบน panel ก็จะต้องสร้าง class panel เพื่อ override method paintComponent

    int fWidth = 800 - 14, fHeight = 600 - 35;
    Image img = Toolkit.getDefaultToolkit().createImage("c:/picture New/background.png");                                                       //นำ Path รูปภาพมาใส่ไว้ในตัวแปร
    Image ImgBg = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + File.separator + "background.png");
    int imgx, imgy, imgxw, imgyh, imgB, imgL;

    public MyPanel() {
        setBounds(0, 30, fWidth, fHeight - 30);
        setLayout(null);
        setBackground(Color.red);

    }

    @Override
    protected void paintComponent(Graphics g) {                                             //จะ override method เพื่อวาดรูปบน component ใด ต้องสร้าง class ที่สืบทอด component นั้น 
        if (img.getWidth(this) != -1) {                                  //เอาค่าความกว้างของรูปต้นฉบับมาใช้งานโดย this คคือ object ของพื้นที่ที่ใช้วาดรูป มีปัญหาครั้งแรก จะเช็คขนาดของรูปไม่ได้ จะคืนค่าเป็น -1                                                      
            imgx = (this.getWidth() / 2) - ((img.getWidth(this) + imgB) / 2);               //ทำให้รูปภาพอยู่ตรงกลาง
            imgy = (this.getHeight() / 2) - ((img.getHeight(this) + imgL) / 2);             //ทำให้รูปภาพอยู่ตรงกลาง
            g.drawImage(ImgBg, 0, 0, this.getWidth(), this.getHeight(), 0, 0, 258, 216, this);          // วาดรูป ("รูปที่ต้องการ , จุดเริ่มต้น , จุดเริ่มต้น , จุดเริ่มต้น + ความกว้าง , จุดเริ่มต้น + ความสูง , 0 , 0 , ความกว้างเดิม , ความสูงเดิม")
            g.drawImage(img, imgx, imgy, imgx + img.getWidth(this) + imgB, imgy + img.getHeight(this) + imgL, 0, 0, img.getWidth(this), img.getHeight(this), this);

        }
    }
}

class MyFrameHelp extends JFrame {                                              //สร้างเฟรมอีกเฟรมนึงขึ้นมา
    Panel2 pnl = new Panel2();
    JTextArea txtAr = new JTextArea();
     Image Img;
    public MyFrameHelp() {
        add(pnl);
        setTitle("About Image Viewer");
        setBounds(0, 0, 400, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);    //กำหนดให้เมื่อกดปิด frame นี้ให้แค่ซ่อนหน้าต่างไม่ต้องปิดทั้งโปรแกรม

        txtAr.setBounds(200, 50, 150, 300);
        txtAr.setBorder(BorderFactory.createRaisedBevelBorder());
        txtAr.setFont(new Font("Tahoma", 1, 15));
        txtAr.setText("โปรแกรมนี้เป็น\nโปรแกรมที่เกี่ยว\nกับรูปภาพ \nจะสามารถนำรูปภาพ\nเข้ามาได้และแสดง\nกลางหน้าต่าง\nของโปรแกรม\n และ สามมารถย่อ\nหรือขยายได้ ซึ้ง\nมีประโยชน์ต่อ\nผู้ใช้อย่างมาก");
        Image Img;
        pnl.add(txtAr);
        repaint();

    }

 
}
class Panel2  extends JPanel{
Image Img;
    public Panel2()  {
        setBounds(0, 0, 400, 400);
        setLayout(null);
        
    }
      protected void paintComponent(Graphics g) {
         Img = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + File.separator + "mini.jpg");
        g.drawImage(Img, 20, 20, 180, 380, 0, 0, 1568, 1044, this);
        repaint();
    }
    
}
