/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Alibimunka
 */
public class Alaprajzkepnelkul extends javax.swing.JFrame {
    
    private int mode = 0;
    
    /**
     * mode 0 = init
     * mode 1 = felvitel
     * mode 2 = lekérdezés
     */
    
    int x,y;
    
    public class termek {
        private int elerheto;
        private Color color;
        
//        black - nem elerheto
//        red - polc
//        gray - út
//        ligthgray - bejárat
//        darkgray - kijárat
//        white - fal
        
        private String nev;
        private char karakter;
        
//        ' ' - nem elerheto
//        p - polc
//        u - út
//        b - bejárat
//        k - kijárat
//        ' ' - fal

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public char getKarakter() {
            return karakter;
        }

        public void setKarakter(char karakter) {
            this.karakter = karakter;
        }

        public int getElerheto() {
            return elerheto;
        }

        public void setElerheto(int elerheto) {
            this.elerheto = elerheto;
        }

        public String getNev() {
            return nev;
        }

        public void setNev(String nev) {
            this.nev = nev;
        }
        termek(String szoveg, int szam, Color szin, char betu){
            this.elerheto = szam;
            this.nev=szoveg;
            this.color=szin;
            this.karakter=betu;
        }
        termek(){
            this.elerheto = 0;
            this.nev = "nincs adat";
            this.color=Color.black;
            this.karakter=' ';
        }
    }
    
    termek[][] adatok = new termek[25][25];
    
    
    
    /**
     * Creates new form Alaprajz
     * @throws java.io.FileNotFoundException
     */
    public Alaprajzkepnelkul() throws FileNotFoundException, IOException {
        initComponents();
        

        for (int i=0;i<25;i++){
            for (int j=0;j<25;j++){
                adatok[i][j]= new termek();
            }
        }
        
    }

    class MyPanel extends JPanel {

        private int squareX = 600;
        private int squareY = 600;
        private Color color;

        public void setColor(Color color) {
            this.color = color;
        }

        public void setKarakter(char karakter) {
            this.karakter = karakter;
        }
        private final Color colorborder=Color.black;
        private char karakter;
        private final int squareW = 20;
        private final int squareH = 20;

        public MyPanel() {
            
            setOpaque ( true );
            
            setDoubleBuffered( true );
            
            setBorder(BorderFactory.createLineBorder(colorborder));

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int xcor = MouseInfo.getPointerInfo().getLocation().x - HatterPanel.getLocationOnScreen().x;
                    int ycor = MouseInfo.getPointerInfo().getLocation().y - HatterPanel.getLocationOnScreen().y;
                    x = xcor/20;
                    y = ycor/20;
                    System.out.println(x*20+":"+y*20+","+x+":"+y);
                    switch (mode) {
                        case 1:  {
                            int tipus = jComboBoxTipus.getSelectedIndex();
                            System.out.println(tipus);
                            switch (tipus){
                                case 0 : {  //Termék
                                    adatok[x][y] = new Alaprajzkepnelkul.termek(BeTextField.getText(),1,Color.red,'p');
                                    color = adatok[x][y].getColor();
                                    karakter = adatok[x][y].getKarakter();
                                    break;
                                }
                                case 1 : {  //Út
                                    adatok[x][y] = new Alaprajzkepnelkul.termek(BeTextField.getText(),1,Color.gray,'u');
                                    color = adatok[x][y].getColor();
                                    karakter = adatok[x][y].getKarakter();
                                    break;
                                }
                                case 2 : {  //Bejárat
                                    adatok[x][y] = new Alaprajzkepnelkul.termek(BeTextField.getText(),1,Color.lightGray,'b');
                                    color = adatok[x][y].getColor();
                                    karakter = adatok[x][y].getKarakter();
                                    break;
                                }
                                case 3 : {  //Kijárat
                                    adatok[x][y] = new Alaprajzkepnelkul.termek(BeTextField.getText(),1,Color.darkGray,'k');
                                    color = adatok[x][y].getColor();
                                    karakter = adatok[x][y].getKarakter();
                                    break;
                                }
                                case 4 : {  //Fal
                                    adatok[x][y] = new Alaprajzkepnelkul.termek(BeTextField.getText(),1,Color.white,' ');
                                    color = adatok[x][y].getColor();
                                    karakter = adatok[x][y].getKarakter();
                                    break;
                                }
                                default : {
                                    adatok[x][y] = new Alaprajzkepnelkul.termek(BeTextField.getText(),1,Color.white,' ');
                                    color = adatok[x][y].getColor();
                                    karakter = adatok[x][y].getKarakter();
                                    break;
                                }
                            }
                            moveSquare(e.getX()/20*20,e.getY()/20*20);
                            break;
                        }
                        case 2:  {
                            if (adatok[x][y].getElerheto()==1) KiTextField.setText(adatok[x][y].getNev());
                            else KiTextField.setText("Nincs adat");
                            break;
                        }
                        default: System.out.println("Not a month!"); break;
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    //moveSquare(e.getX(),e.getY());
                }
            });

        }

        public void moveSquare(int x, int y) {
            int OFFSET = 1;
            //repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
            squareX=x;
            squareY=y;
            paintImmediately(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
        }


        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500,500);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            g.fillRect(squareX,squareY,squareW,squareH);
            g.setColor(colorborder);
            g.drawRect(squareX,squareY,squareW,squareH);
            g.drawString(String.valueOf(karakter),squareX+squareW/2,squareY+squareH/2);
        }  
        
        public void nomorepaint(Graphics g){
            super.paint(g);
        }
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FelvitelButton = new javax.swing.JButton();
        LekerButton = new javax.swing.JButton();
        BeTextField = new javax.swing.JTextField();
        KiTextField = new javax.swing.JTextField();
        ErrorLabel = new javax.swing.JLabel();
        ModButton = new javax.swing.JButton();
        ModTextField = new javax.swing.JTextField();
        TorlesButton = new javax.swing.JButton();
        KeresesButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        KerY = new javax.swing.JTextField();
        KerX = new javax.swing.JTextField();
        KeresesTextField = new javax.swing.JTextField();
        ImportButton = new javax.swing.JButton();
        ExportButton = new javax.swing.JButton();
        FilenameTextField = new javax.swing.JTextField();
        HatterPanel = new MyPanel();
        HatterLabel = new javax.swing.JLabel();
        jComboBoxTipus = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        FelvitelButton.setText("Felvitel");
        FelvitelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FelvitelButtonActionPerformed(evt);
            }
        });

        LekerButton.setText("Lekérés");
        LekerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LekerButtonActionPerformed(evt);
            }
        });

        BeTextField.setText("Termék neve");

        ModButton.setText("Módosítás");
        ModButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModButtonActionPerformed(evt);
            }
        });

        TorlesButton.setText("Törlés");
        TorlesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TorlesButtonActionPerformed(evt);
            }
        });

        KeresesButton.setText("Keresés");
        KeresesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KeresesButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("x:");

        jLabel2.setText("y:");

        ImportButton.setText("Import");
        ImportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportButtonActionPerformed(evt);
            }
        });

        ExportButton.setText("Export");
        ExportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportButtonActionPerformed(evt);
            }
        });

        FilenameTextField.setText("teszt.txt");

        javax.swing.GroupLayout HatterPanelLayout = new javax.swing.GroupLayout(HatterPanel);
        HatterPanel.setLayout(HatterPanelLayout);
        HatterPanelLayout.setHorizontalGroup(
            HatterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HatterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HatterPanelLayout.setVerticalGroup(
            HatterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HatterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        jComboBoxTipus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Termék", "Út", "Bejárat", "Kijárat", "Fal" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ImportButton)
                        .addGap(18, 18, 18)
                        .addComponent(ExportButton)
                        .addGap(41, 41, 41)
                        .addComponent(FilenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(HatterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(ErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(KiTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                        .addComponent(ModTextField))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboBoxTipus, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TorlesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ModButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LekerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(FelvitelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(KeresesButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(KeresesTextField)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(KerY))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(KerX, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(HatterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ImportButton)
                            .addComponent(ExportButton)
                            .addComponent(FilenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FelvitelButton)
                            .addComponent(BeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTipus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LekerButton)
                            .addComponent(KiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ModButton)
                            .addComponent(ModTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TorlesButton)
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(KeresesButton)
                            .addComponent(KeresesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(KerX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(KerY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                        .addComponent(ErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(185, 185, 185))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void FelvitelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FelvitelButtonActionPerformed
        // TODO add your handling code here:
        mode = 1;
    }//GEN-LAST:event_FelvitelButtonActionPerformed

    private void LekerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LekerButtonActionPerformed
        // TODO add your handling code here:
        mode = 2;
    }//GEN-LAST:event_LekerButtonActionPerformed

    private void ModButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModButtonActionPerformed
        // TODO add your handling code here:
        adatok[x][y].setNev(ModTextField.getText());
    }//GEN-LAST:event_ModButtonActionPerformed

    private void TorlesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TorlesButtonActionPerformed
        // TODO add your handling code here:
        adatok[x][y].setElerheto(0);
    }//GEN-LAST:event_TorlesButtonActionPerformed

    private void KeresesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KeresesButtonActionPerformed
        // TODO add your handling code here:
        String keresett = KeresesTextField.getText();
        int bX=-1;
        int bY=-1;
        for (int i=0;i<25;i++){
            for (int j=0;j<25;j++){
                if (adatok[i][j].getNev() == null ? keresett == null : adatok[i][j].getNev().equals(keresett)){
                    KerX.setText(Integer.toString(i));
                    KerY.setText(Integer.toString(j));
                }
                if (adatok[i][j].getKarakter()=='b'){
                    bX=i;
                    bY=j;
                }
            }
        }
        /**/
                
                    
    }//GEN-LAST:event_KeresesButtonActionPerformed

    private void ImportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportButtonActionPerformed
        BufferedReader br = null;
        ((MyPanel)HatterPanel).moveSquare(600,600);
        ((MyPanel)HatterPanel).nomorepaint(((MyPanel)HatterPanel).getGraphics());
        try {
            // TODO add your handling code here:
            File file = new File(FilenameTextField.getText());
            br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null){
                String[] parts = s.split("\t");
                Integer[] szamok = new Integer[3];
                for (Integer i=0;i<3;i++){
                    szamok[i]=Integer.parseInt(parts[i]);
                }
                adatok[szamok[0]][szamok[1]].setElerheto(szamok[2]);
                adatok[szamok[0]][szamok[1]].setNev(parts[3]);
                adatok[szamok[0]][szamok[1]].setColor(Color.decode(parts[4]));
                adatok[szamok[0]][szamok[1]].setKarakter(parts[5].charAt(0));
                ((MyPanel)HatterPanel).setColor(adatok[szamok[0]][szamok[1]].getColor());
                ((MyPanel)HatterPanel).setKarakter(adatok[szamok[0]][szamok[1]].getKarakter());
                ((MyPanel)HatterPanel).moveSquare(szamok[0]*20,szamok[1]*20);
                
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ImportButtonActionPerformed

    private void ExportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportButtonActionPerformed
        
        BufferedWriter writer = null;
        try {
            // TODO add your handling code here:
            writer = new BufferedWriter(new FileWriter(FilenameTextField.getText()));
            for (int i=0;i<25;i++){
                for (int j=0;j<25;j++){
                    if (adatok[i][j].getElerheto()==1){
                        String szovegki="";
                        szovegki=i+"\t"+j+"\t"+1+"\t"+adatok[i][j].getNev()+"\t"+
                                adatok[i][j].getColor().hashCode()+"\t"+adatok[i][j].getKarakter()+"\n";
                        writer.write(szovegki);
                    }
                }
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ExportButtonActionPerformed
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Alaprajzkepnelkul().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Alaprajzkepnelkul.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BeTextField;
    private javax.swing.JLabel ErrorLabel;
    private javax.swing.JButton ExportButton;
    private javax.swing.JButton FelvitelButton;
    private javax.swing.JTextField FilenameTextField;
    private javax.swing.JLabel HatterLabel;
    private javax.swing.JPanel HatterPanel;
    private javax.swing.JButton ImportButton;
    private javax.swing.JTextField KerX;
    private javax.swing.JTextField KerY;
    private javax.swing.JButton KeresesButton;
    private javax.swing.JTextField KeresesTextField;
    private javax.swing.JTextField KiTextField;
    private javax.swing.JButton LekerButton;
    private javax.swing.JButton ModButton;
    private javax.swing.JTextField ModTextField;
    private javax.swing.JButton TorlesButton;
    private javax.swing.JComboBox<String> jComboBoxTipus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
