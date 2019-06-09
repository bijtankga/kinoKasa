/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formatki;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Marcin
 */
public class Rezerwacje extends javax.swing.JFrame {

    /**
     * Creates new form Rezerwacje
     */
    
    private EntityManagerFactory emf;
    private kino.Seans seans;
    private DefaultListModel<kino.Rezerwacja> rezerwacjeListModel;
    private List<List<JToggleButton>> przyciski;
    private List<kino.Miejsce> miejsca;
    
    public Rezerwacje(kino.Rezerwacja rezerwacja)
    {
        this( ((kino.Miejsce)new ArrayList(rezerwacja.getMiejsceCollection()).get(0)).getSeans());
        
        rezerwacjeList.setSelectedValue(rezerwacja, rootPaneCheckingEnabled);
    }
    
    public Rezerwacje(kino.Seans seans) {
        initComponents();
        klientPanel.setVisible(false);
        
        this.seans = seans;
        miejsca = new ArrayList<>();
        przyciski = new ArrayList<>();
        
        dataLabel.setText( new SimpleDateFormat("HH:mm").format(seans.getData()) );
        godzinaLabel.setText( new SimpleDateFormat("dd-MM-yyyy").format(seans.getData()) );
        if(seans.getD3())
            typLabel.setText( "3D");
        else
            typLabel.setText( "2D");
        tytulLabel.setText( seans.getFilm().getNazwa() );
        rezyserLabel.setText( seans.getFilm().getRezyser() );
        rokLabel.setText( ""+seans.getFilm().getRok() );
        
        zaladujSale();
        
        rezerwacjeListModel = new DefaultListModel<>();
        rezerwacjeList.setModel(rezerwacjeListModel);
        getRezerwacje();
    }
    
    private EntityManager getEntityManager() {
            if (emf==null) emf=Persistence.createEntityManagerFactory("kinoPU2");
            return emf.createEntityManager();
    }
    
    private void zaladujSale()
    {
        EntityManager em=getEntityManager();
        TypedQuery<kino.Miejsce> q;
        miejscaPanel.removeAll();
        przyciski.clear();
        try {
            q = em.createNamedQuery("Miejsce.findBySeans", kino.Miejsce.class);
            q.setParameter("seans",seans);
            
             this.miejsca = q.getResultList();
        }
        catch(PersistenceException ex)
        {
            Logger.getLogger(Seanse.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null,"Błąd połączenia z bazą danych");
        }
        finally {
            em.close();
        }
        
        miejsca.sort( (m1, m2) -> {
            if (m1.getRzad() == m2.getRzad())
            {
                if (m1.getNr() > m2.getNr())
                {
                    return 1;
                }
            }
            return 0;
        });

        //salaPanel.setLayout(new GridLayout(300, 300));
        for(kino.Miejsce miejsce : miejsca)
        {
            if (miejsce .getNr() == 1)
            {
                przyciski.add(new ArrayList<>());
                JLabel lb = new JLabel(""+miejsce.getRzad());
                lb.setPreferredSize(new Dimension(50,50));
                lb.setHorizontalTextPosition(JLabel.CENTER);
                lb.setHorizontalAlignment(JLabel.CENTER);
                miejscaPanel.add(lb);
            }
            JToggleButton przycisk = new JToggleButton( ""+miejsce.getNr());
            przycisk.setPreferredSize(new Dimension(50, 50));
            
            przycisk.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    toggleButtonActionPerformed(evt);
                }
            });
            
            if (miejsce.getRezerwacja() != null)
            {
                przycisk.setEnabled(false);
            }
            
            miejscaPanel.add(przycisk);
            przyciski.get(miejsce.getRzad() - 1).add(przycisk);
        }
        miejscaPanel.setLayout(new GridLayout( przyciski.size(), przyciski.get(0).size() + 1 ));
        
        this.pack();
        
        wyzerujPanelCeny();
    }
    
    private void getRezerwacje()
    {
        EntityManager em=getEntityManager();
        TypedQuery<kino.Rezerwacja> q;
        rezerwacjeListModel.clear();
        try {
            q = em.createNamedQuery("Rezerwacja.findBySeans", kino.Rezerwacja.class);
            q.setParameter("seans",seans);
            
            for(kino.Rezerwacja rezerwacja : q.getResultList())
            {
                rezerwacjeListModel.addElement(rezerwacja);
            }
        }
        catch(PersistenceException ex)
        {
            Logger.getLogger(Seanse.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null,"Błąd połączenia z bazą danych");
        }
        finally {
            em.close();
        }
    }
    
    private void wyzerujPanelCeny()
    {
        cenaLabel.setText("0");
        normalneLabel.setText("0");
        ulgoweLabel.setText("0");
        
        biletySlider.setValue(0);
        biletySlider.setMaximum(0);
    }
    
    private void toggleButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        JToggleButton przycisk = (JToggleButton)evt.getSource();
        
        if (przycisk.getBackground() == Color.GREEN)
        {
            przycisk.setBackground(Color.ORANGE);
        }
        else if(przycisk.getBackground() == Color.ORANGE)
        {
            przycisk.setBackground(Color.GREEN);
        }
        if(przycisk.isSelected())
        {
            biletySlider.setMaximum( biletySlider.getMaximum()+1);
        }
        else
        {
            biletySlider.setMaximum( biletySlider.getMaximum()-1);
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

        rezerwacjePanel = new javax.swing.JPanel();
        rezerwacjeLabel = new javax.swing.JLabel();
        odswiezButton = new javax.swing.JButton();
        skasujButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        rezerwacjeList = new javax.swing.JList<>();
        odznaczButton = new javax.swing.JButton();
        cenaPanel = new javax.swing.JPanel();
        cenaTextLabel = new javax.swing.JLabel();
        biletySlider = new javax.swing.JSlider();
        normalneTextLabel = new javax.swing.JLabel();
        ulgoweTextLabel = new javax.swing.JLabel();
        normalneLabel = new javax.swing.JLabel();
        wykupButton = new javax.swing.JButton();
        ulgoweLabel = new javax.swing.JLabel();
        cenaLabel = new javax.swing.JLabel();
        legendaPanel = new javax.swing.JPanel();
        zajeteLegendaTButton = new javax.swing.JToggleButton();
        wolneLegendaTButton = new javax.swing.JToggleButton();
        wybraneLegendaTButton = new javax.swing.JToggleButton();
        zarezerwowaneLegendaTButton = new javax.swing.JToggleButton();
        odrezerwowaneLegendaTButton = new javax.swing.JToggleButton();
        salaPanel = new javax.swing.JPanel();
        ekranPanel = new javax.swing.JPanel();
        ekranLabel = new javax.swing.JLabel();
        miejscaPanel = new javax.swing.JPanel();
        klientPanel = new javax.swing.JPanel();
        imieOpisLabel = new javax.swing.JLabel();
        nazwiskoOpisLabel = new javax.swing.JLabel();
        emailOpisLabel = new javax.swing.JLabel();
        imieLabel = new javax.swing.JLabel();
        nazwiskoLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        seansLabel = new javax.swing.JPanel();
        dataOpisLabel = new javax.swing.JLabel();
        godzinaOpisLabel = new javax.swing.JLabel();
        godzinaLabel = new javax.swing.JLabel();
        dataLabel = new javax.swing.JLabel();
        tytulOpisLabel = new javax.swing.JLabel();
        tytulLabel = new javax.swing.JLabel();
        typOpisLabel = new javax.swing.JLabel();
        typLabel = new javax.swing.JLabel();
        rezyserOpis = new javax.swing.JLabel();
        rezyserLabel = new javax.swing.JLabel();
        rokLabel = new javax.swing.JLabel();
        rokOpisLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        zamknijMItem = new javax.swing.JMenuItem();
        zamknijOknoMItem = new javax.swing.JMenuItem();
        actionMenu = new javax.swing.JMenu();
        odswiezMItem = new javax.swing.JMenuItem();
        szuakjMItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        filmyMItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rezerwacje i Wykup Biletów");
        setResizable(false);

        rezerwacjePanel.setPreferredSize(new java.awt.Dimension(350, 522));

        rezerwacjeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rezerwacjeLabel.setText("Rezerwacje");

        odswiezButton.setText("Odśwież");
        odswiezButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odswiezButtonActionPerformed(evt);
            }
        });

        skasujButton.setText("Skasuj");
        skasujButton.setEnabled(false);
        skasujButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skasujButtonActionPerformed(evt);
            }
        });

        rezerwacjeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        rezerwacjeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                rezerwacjeListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(rezerwacjeList);

        odznaczButton.setText("Odznacz");
        odznaczButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odznaczButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rezerwacjePanelLayout = new javax.swing.GroupLayout(rezerwacjePanel);
        rezerwacjePanel.setLayout(rezerwacjePanelLayout);
        rezerwacjePanelLayout.setHorizontalGroup(
            rezerwacjePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rezerwacjePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rezerwacjePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rezerwacjePanelLayout.createSequentialGroup()
                        .addComponent(rezerwacjeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(odswiezButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rezerwacjePanelLayout.createSequentialGroup()
                        .addComponent(skasujButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(odznaczButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        rezerwacjePanelLayout.setVerticalGroup(
            rezerwacjePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rezerwacjePanelLayout.createSequentialGroup()
                .addGroup(rezerwacjePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(odswiezButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rezerwacjeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rezerwacjePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(odznaczButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skasujButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        cenaPanel.setPreferredSize(new java.awt.Dimension(250, 0));

        cenaTextLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cenaTextLabel.setText("Cena:");

        biletySlider.setMaximum(0);
        biletySlider.setMinorTickSpacing(1);
        biletySlider.setPaintTicks(true);
        biletySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                biletySliderStateChanged(evt);
            }
        });

        normalneTextLabel.setText("Normalne:");

        ulgoweTextLabel.setText("Ulgowe:");

        normalneLabel.setText("0");

        wykupButton.setText("Wykup");
        wykupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wykupButtonActionPerformed(evt);
            }
        });

        ulgoweLabel.setText("0");

        cenaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cenaLabel.setText("0");

        javax.swing.GroupLayout cenaPanelLayout = new javax.swing.GroupLayout(cenaPanel);
        cenaPanel.setLayout(cenaPanelLayout);
        cenaPanelLayout.setHorizontalGroup(
            cenaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cenaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cenaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(cenaPanelLayout.createSequentialGroup()
                        .addComponent(normalneTextLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(normalneLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ulgoweTextLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ulgoweLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(wykupButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cenaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(biletySlider, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(cenaPanelLayout.createSequentialGroup()
                            .addComponent(cenaTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cenaLabel))))
                .addContainerGap())
        );
        cenaPanelLayout.setVerticalGroup(
            cenaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cenaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cenaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(normalneTextLabel)
                    .addComponent(ulgoweTextLabel)
                    .addComponent(normalneLabel)
                    .addComponent(ulgoweLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(biletySlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cenaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cenaTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cenaLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wykupButton, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addContainerGap())
        );

        legendaPanel.setLayout(new java.awt.GridLayout(1, 0));

        zajeteLegendaTButton.setText("ZAJĘTE");
        zajeteLegendaTButton.setEnabled(false);
        zajeteLegendaTButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendaTButtonActionPerformed(evt);
            }
        });
        legendaPanel.add(zajeteLegendaTButton);

        wolneLegendaTButton.setText("WOLNE");
        wolneLegendaTButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendaTButtonActionPerformed(evt);
            }
        });
        legendaPanel.add(wolneLegendaTButton);

        wybraneLegendaTButton.setSelected(true);
        wybraneLegendaTButton.setText("WYBRANE");
        wybraneLegendaTButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendaTButtonActionPerformed(evt);
            }
        });
        legendaPanel.add(wybraneLegendaTButton);

        zarezerwowaneLegendaTButton.setBackground(java.awt.Color.green);
        zarezerwowaneLegendaTButton.setSelected(true);
        zarezerwowaneLegendaTButton.setText("REZERWACJA");
        zarezerwowaneLegendaTButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendaTButtonActionPerformed(evt);
            }
        });
        legendaPanel.add(zarezerwowaneLegendaTButton);

        odrezerwowaneLegendaTButton.setBackground(java.awt.Color.orange);
        odrezerwowaneLegendaTButton.setText("ODREZERWOWANE");
        odrezerwowaneLegendaTButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legendaTButtonActionPerformed(evt);
            }
        });
        legendaPanel.add(odrezerwowaneLegendaTButton);

        ekranPanel.setBackground(new java.awt.Color(102, 102, 102));

        ekranLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ekranLabel.setForeground(new java.awt.Color(255, 255, 255));
        ekranLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ekranLabel.setText("EKRAN");

        javax.swing.GroupLayout ekranPanelLayout = new javax.swing.GroupLayout(ekranPanel);
        ekranPanel.setLayout(ekranPanelLayout);
        ekranPanelLayout.setHorizontalGroup(
            ekranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ekranPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ekranLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addContainerGap())
        );
        ekranPanelLayout.setVerticalGroup(
            ekranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ekranPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ekranLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        miejscaPanel.setLayout(new java.awt.GridLayout(3, 7));

        javax.swing.GroupLayout salaPanelLayout = new javax.swing.GroupLayout(salaPanel);
        salaPanel.setLayout(salaPanelLayout);
        salaPanelLayout.setHorizontalGroup(
            salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(miejscaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salaPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ekranPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        salaPanelLayout.setVerticalGroup(
            salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ekranPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(miejscaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        klientPanel.setPreferredSize(new java.awt.Dimension(250, 0));

        imieOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imieOpisLabel.setText("Imie:");

        nazwiskoOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nazwiskoOpisLabel.setText("Nazwisko:");

        emailOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailOpisLabel.setText("Email:");

        imieLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        imieLabel.setText("jLabel4");

        nazwiskoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nazwiskoLabel.setText("jLabel5");

        emailLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailLabel.setText("jLabel6");

        javax.swing.GroupLayout klientPanelLayout = new javax.swing.GroupLayout(klientPanel);
        klientPanel.setLayout(klientPanelLayout);
        klientPanelLayout.setHorizontalGroup(
            klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(klientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(klientPanelLayout.createSequentialGroup()
                        .addComponent(imieOpisLabel)
                        .addGap(48, 48, 48)
                        .addComponent(imieLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                    .addGroup(klientPanelLayout.createSequentialGroup()
                        .addGroup(klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nazwiskoOpisLabel)
                            .addComponent(emailOpisLabel))
                        .addGap(18, 18, 18)
                        .addGroup(klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nazwiskoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        klientPanelLayout.setVerticalGroup(
            klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(klientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imieOpisLabel)
                    .addComponent(imieLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nazwiskoOpisLabel)
                    .addComponent(nazwiskoLabel))
                .addGap(18, 18, 18)
                .addGroup(klientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailOpisLabel)
                    .addComponent(emailLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dataOpisLabel.setText("Data:");

        godzinaOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        godzinaOpisLabel.setText("Godzina:");

        godzinaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dataLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tytulOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tytulOpisLabel.setText("Tytuł:");

        tytulLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        typOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        typOpisLabel.setText("Typ:");

        typLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        rezyserOpis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rezyserOpis.setText("Reżyser:");

        rezyserLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        rokLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        rokOpisLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rokOpisLabel.setText("Rok:");

        javax.swing.GroupLayout seansLabelLayout = new javax.swing.GroupLayout(seansLabel);
        seansLabel.setLayout(seansLabelLayout);
        seansLabelLayout.setHorizontalGroup(
            seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seansLabelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seansLabelLayout.createSequentialGroup()
                        .addGroup(seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(seansLabelLayout.createSequentialGroup()
                                .addComponent(tytulOpisLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tytulLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(seansLabelLayout.createSequentialGroup()
                                .addComponent(dataOpisLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(godzinaLabel)
                                .addGap(81, 81, 81)
                                .addComponent(godzinaOpisLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dataLabel)
                                .addGap(84, 84, 84)
                                .addComponent(typOpisLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(typLabel)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(seansLabelLayout.createSequentialGroup()
                        .addComponent(rezyserOpis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rezyserLabel)
                        .addGap(106, 106, 106)
                        .addComponent(rokOpisLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rokLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        seansLabelLayout.setVerticalGroup(
            seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seansLabelLayout.createSequentialGroup()
                .addGroup(seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataOpisLabel)
                    .addComponent(godzinaLabel)
                    .addComponent(godzinaOpisLabel)
                    .addComponent(dataLabel)
                    .addComponent(typOpisLabel)
                    .addComponent(typLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tytulOpisLabel)
                    .addComponent(tytulLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(seansLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rezyserOpis)
                    .addComponent(rezyserLabel)
                    .addComponent(rokOpisLabel)
                    .addComponent(rokLabel)))
        );

        fileMenu.setText("File");

        zamknijMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        zamknijMItem.setText("Zamknij Program");
        zamknijMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zamknijMItemActionPerformed(evt);
            }
        });
        fileMenu.add(zamknijMItem);

        zamknijOknoMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        zamknijOknoMItem.setText("Zamknij Okno");
        zamknijOknoMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zamknijOknoMItemActionPerformed(evt);
            }
        });
        fileMenu.add(zamknijOknoMItem);

        jMenuBar1.add(fileMenu);

        actionMenu.setText("Action");

        odswiezMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        odswiezMItem.setText("Odśwież");
        odswiezMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odswiezMItemActionPerformed(evt);
            }
        });
        actionMenu.add(odswiezMItem);

        szuakjMItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        szuakjMItem.setText("Szukaj");
        szuakjMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                szuakjMItemActionPerformed(evt);
            }
        });
        actionMenu.add(szuakjMItem);

        jMenuBar1.add(actionMenu);

        viewMenu.setText("View");

        filmyMItem.setText("Filmy");
        filmyMItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filmyMItemActionPerformed(evt);
            }
        });
        viewMenu.add(filmyMItem);

        jMenuBar1.add(viewMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(rezerwacjePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(klientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(cenaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(legendaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(salaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(seansLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rezerwacjePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(seansLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(legendaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cenaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(klientPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void skasujButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skasujButtonActionPerformed
        // TODO add your handling code here:
        EntityManager em = getEntityManager();
        kino.Rezerwacja rezerwacja = rezerwacjeList.getSelectedValue();

        try
        {
            em.getTransaction().begin();
            rezerwacja = em.find(rezerwacja.getClass(), rezerwacja.getId());
            em.remove(rezerwacja);
            em.getTransaction().commit();
            rezerwacjeListModel.removeElement(rezerwacja);
            skasujButton.setEnabled(false);
        }
        catch (Exception ex)
        {
            if (em.getTransaction().isActive())
            em.getTransaction().rollback();
            Logger.getLogger(Rezerwacje.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            em.close();
        }
        skasujButton.setEnabled(false);
        wykupButton.setEnabled(false);
        
        zaladujSale();
        getRezerwacje();
    }//GEN-LAST:event_skasujButtonActionPerformed

    private void rezerwacjeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_rezerwacjeListValueChanged
        // TODO add your handling code here:
        if(rezerwacjeList.getSelectedValue() == null)
        {
            klientPanel.setVisible(false);
            return;
        }
        else
        {
            int ilosc = 0;
            for(kino.Miejsce miejsce : miejsca)
            {
                JToggleButton przycisk = przyciski.get(miejsce.getRzad() - 1).get(miejsce.getNr() - 1);
                przycisk.setEnabled(true);
                przycisk.setSelected(false);
                przycisk.setBackground(null);
                kino.Rezerwacja rez = miejsce.getRezerwacja();
                if(rez != null)
                {
                    if ( rezerwacjeList.getSelectedValue().getId() == rez.getId())
                    {
                        przycisk.setBackground(Color.green);
                        przycisk.setSelected(true);
                        ilosc++;
                    }
                    else
                    {
                        przycisk.setEnabled(false);
                    }
                }
            }

            normalneLabel.setText(""+ilosc);
            ulgoweLabel.setText("0");
            biletySlider.setMaximum(ilosc);
            biletySlider.setValue(0);
            cenaLabel.setText(( seans.getCena().multiply(new BigDecimal(ilosc))).setScale(2).toString() );
            
            if (rezerwacjeList.getSelectedValue().getKlient().getId() == 1)
                klientPanel.setVisible(false);
            else
            {
                imieLabel.setText(rezerwacjeList.getSelectedValue().getKlient().getImie());
                nazwiskoLabel.setText(rezerwacjeList.getSelectedValue().getKlient().getNazwisko());
                emailLabel.setText(rezerwacjeList.getSelectedValue().getKlient().getEmail());
                klientPanel.setVisible(true);
            }
        }
        if(!rezerwacjeList.getSelectedValue().getKupione())
        {
            skasujButton.setEnabled(true);
            wykupButton.setEnabled(true);
        }
        else
        {
            wykupButton.setEnabled(false);
            skasujButton.setEnabled(false);
        }
    }//GEN-LAST:event_rezerwacjeListValueChanged

    private void wykupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wykupButtonActionPerformed
        // TODO add your handling code here:
        
        EntityManager em = getEntityManager();
        kino.Rezerwacja rezerwacja = rezerwacjeList.getSelectedValue();
        
        try {
            em.getTransaction().begin();
            
            List<kino.Miejsce> lista = new ArrayList<>();
            for(kino.Miejsce m : miejsca)
            {
                if( przyciski.get(m.getRzad()-1).get(m.getNr()-1).isSelected())
                {
                    lista.add( em.find(kino.Miejsce.class, m.getId()));
                }
            }
            if(lista.size() == 0)
            {
                throw new Exception();
            }
            
            if(rezerwacja == null)
            {
                rezerwacja = new kino.Rezerwacja();
                rezerwacja.setMiejsceCollection(lista);
                rezerwacja.setKlient( em.find(kino.Klient.class, 1));
                rezerwacja.setKupione(true);
                
                em.persist(rezerwacja);
            }
            else
            {
                rezerwacja = em.find(kino.Rezerwacja.class, rezerwacja.getId());
                rezerwacja.setKupione(true);
                rezerwacja.setMiejsceCollection(lista);
            }
            
            System.out.print("DRUKOWANIE BILETU");
            em.getTransaction().commit();
        }
        catch (PersistenceException ex)
        {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Seanse.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null,"Błąd w przetwarzaniu tranzakcji");
        }
        catch (Exception ex)
        {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            JOptionPane.showMessageDialog(null,"Proszę wybrać miejsce");
        }
        finally {
            em.close();
        }
        
        zaladujSale();
        getRezerwacje();
    }//GEN-LAST:event_wykupButtonActionPerformed

    private void odznaczButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odznaczButtonActionPerformed
        // TODO add your handling code here:
        rezerwacjeList.clearSelection();
        
        for(kino.Miejsce miejsce : miejsca)
        {
            JToggleButton przycisk = przyciski.get(miejsce.getRzad() -1).get(miejsce.getNr() -1);
            przycisk.setSelected(false);
            przycisk.setBackground(null);
            
            if(miejsce.getRezerwacja() != null)
            {
                przycisk.setEnabled(false);
            }
        }
        wyzerujPanelCeny();
        skasujButton.setEnabled(false);
        wykupButton.setEnabled(true);
        klientPanel.setVisible(false);
    }//GEN-LAST:event_odznaczButtonActionPerformed

    private void biletySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_biletySliderStateChanged
        // TODO add your handling code here:
        normalneLabel.setText(""+ (biletySlider.getMaximum() - biletySlider.getValue()));
        ulgoweLabel.setText(""+biletySlider.getValue());
        Double cena = seans.getCena().doubleValue() * (biletySlider.getMaximum() - biletySlider.getValue() + biletySlider.getValue() * 0.5);
        cenaLabel.setText( ""+cena );
    }//GEN-LAST:event_biletySliderStateChanged

    private void legendaTButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legendaTButtonActionPerformed
        // TODO add your handling code here:
        
        JToggleButton przycisk = (JToggleButton)evt.getSource();
        if(przycisk.isSelected())
            przycisk.setSelected(false);
        else
            przycisk.setSelected(true);
    }//GEN-LAST:event_legendaTButtonActionPerformed

    private void odswiezButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odswiezButtonActionPerformed
        // TODO add your handling code here:
        
        zaladujSale();
        getRezerwacje();
    }//GEN-LAST:event_odswiezButtonActionPerformed

    private void odswiezMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odswiezMItemActionPerformed
        zaladujSale();
        getRezerwacje();
    }//GEN-LAST:event_odswiezMItemActionPerformed

    private void zamknijMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zamknijMItemActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_zamknijMItemActionPerformed

    private void zamknijOknoMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zamknijOknoMItemActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_zamknijOknoMItemActionPerformed

    private void szuakjMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_szuakjMItemActionPerformed
        // TODO add your handling code here:
        
        new RezerwacjeSzukaj().setVisible(true);
    }//GEN-LAST:event_szuakjMItemActionPerformed

    private void filmyMItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filmyMItemActionPerformed
        // TODO add your handling code here:
        new Filmy().setVisible(true);
    }//GEN-LAST:event_filmyMItemActionPerformed

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
            java.util.logging.Logger.getLogger(Seanse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Seanse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Seanse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Seanse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EntityManager em=Persistence.createEntityManagerFactory("kinoPU2").createEntityManager();
                kino.Rezerwacja film;
                TypedQuery<kino.Rezerwacja> q;
                try{
                    q = em.createNamedQuery("Rezerwacja.findById", kino.Rezerwacja.class);
                    q.setParameter("id", 2);
                    film = (kino.Rezerwacja)q.getResultList().toArray()[0];
                }
                finally{
                    em.close();
                }
                
                new Rezerwacje(film).setVisible(true);
                
            }
        });
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu actionMenu;
    private javax.swing.JSlider biletySlider;
    private javax.swing.JLabel cenaLabel;
    private javax.swing.JPanel cenaPanel;
    private javax.swing.JLabel cenaTextLabel;
    private javax.swing.JLabel dataLabel;
    private javax.swing.JLabel dataOpisLabel;
    private javax.swing.JLabel ekranLabel;
    private javax.swing.JPanel ekranPanel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailOpisLabel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem filmyMItem;
    private javax.swing.JLabel godzinaLabel;
    private javax.swing.JLabel godzinaOpisLabel;
    private javax.swing.JLabel imieLabel;
    private javax.swing.JLabel imieOpisLabel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel klientPanel;
    private javax.swing.JPanel legendaPanel;
    private javax.swing.JPanel miejscaPanel;
    private javax.swing.JLabel nazwiskoLabel;
    private javax.swing.JLabel nazwiskoOpisLabel;
    private javax.swing.JLabel normalneLabel;
    private javax.swing.JLabel normalneTextLabel;
    private javax.swing.JToggleButton odrezerwowaneLegendaTButton;
    private javax.swing.JButton odswiezButton;
    private javax.swing.JMenuItem odswiezMItem;
    private javax.swing.JButton odznaczButton;
    private javax.swing.JLabel rezerwacjeLabel;
    private javax.swing.JList<kino.Rezerwacja> rezerwacjeList;
    private javax.swing.JPanel rezerwacjePanel;
    private javax.swing.JLabel rezyserLabel;
    private javax.swing.JLabel rezyserOpis;
    private javax.swing.JLabel rokLabel;
    private javax.swing.JLabel rokOpisLabel;
    private javax.swing.JPanel salaPanel;
    private javax.swing.JPanel seansLabel;
    private javax.swing.JButton skasujButton;
    private javax.swing.JMenuItem szuakjMItem;
    private javax.swing.JLabel typLabel;
    private javax.swing.JLabel typOpisLabel;
    private javax.swing.JLabel tytulLabel;
    private javax.swing.JLabel tytulOpisLabel;
    private javax.swing.JLabel ulgoweLabel;
    private javax.swing.JLabel ulgoweTextLabel;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JToggleButton wolneLegendaTButton;
    private javax.swing.JToggleButton wybraneLegendaTButton;
    private javax.swing.JButton wykupButton;
    private javax.swing.JToggleButton zajeteLegendaTButton;
    private javax.swing.JMenuItem zamknijMItem;
    private javax.swing.JMenuItem zamknijOknoMItem;
    private javax.swing.JToggleButton zarezerwowaneLegendaTButton;
    // End of variables declaration//GEN-END:variables
}
