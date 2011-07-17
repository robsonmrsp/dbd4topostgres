/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrameDBD4ToPostgres.java
 *
 * Created on 10/07/2011, 10:16:09
 */
package dbd4topostgres;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import dbd4topostgres.model.DBDesignerParser;

/**
 *
 * @author frank
 */
public class FrameDBD4ToPostgres extends javax.swing.JFrame {

    private Preferences preferences;
    private File diretorioCorrenteSaida = null;
    private File diretorioCorrenteEntrada = null;
    private String txtFileName = null;
    BufferedImage imagemBackground = null;
    BufferedImage iconeSistema = null;

    /** Creates new form FrameDBD4ToPostgres */
    public FrameDBD4ToPostgres() {
        // create a Preferences instance (somewhere later in the code)
        this.preferences = Preferences.userNodeForPackage(this.getClass());        
        //      
        // Setar Background e Icone do sistema
        try {
            this.imagemBackground = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("dbd4topostgres/resources/x_centopeia.png"));
            this.iconeSistema = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("dbd4topostgres/resources/x_centopeia_icone.png"));
        } catch (IOException e) {
        }


        this.initComponents();
        this.setIconImage(this.iconeSistema);
        this.setTitle("DBD4ToPostgres");
        this.cmdGerarScript.setEnabled(false);
        //
        //


        // Ultimo look and feel

        String lastLookAndFell = this.preferences.get("LAST_LOOKANDFELL", "");
        if (lastLookAndFell == null || lastLookAndFell.trim().equals("")) {
            lastLookAndFell = "Moderate";
        }
        ActionEvent acao = new ActionEvent(new JRadioButton("LookAndFellAbstrato"), ActionEvent.ACTION_PERFORMED, lastLookAndFell);
        this.lookAndFellActionPerfomed(acao);

        // Abri ultimo arquivo
        this.openLastModel();




    }

    private void openLastModel() {
        // Seleciona o ultimo arquivo aberto
        try {


            String lastInputDir = this.preferences.get("LAST_INPUT_DIR", "");
            if (lastInputDir != null && (!lastInputDir.trim().equals(""))) {

                File file = new File(lastInputDir);
                this.diretorioCorrenteEntrada = file.getParentFile();


                //
                this.openModel(file);
            }

        } catch (Exception e) {
            //
        }


    }

    private void openModel(File file) throws Exception {
        String nomeArquivo = null;


        nomeArquivo = file.getPath();
        DBDesignerParser dbDesignerParser = new DBDesignerParser(file.getPath());

        // Carregas a lista de tabelas do modelo
        Collection<String> tables = dbDesignerParser.getTables();
        // Ordena em ordem alfabética
        ArrayList arrayListTable = new ArrayList(tables);
        Collections.sort(arrayListTable);
        tables = arrayListTable;
        // Monta lista de tabelas
        Vector vectorTables = new Vector();

        for (String table : tables) {
            FrameDBD4ToPostgres.DBDesignerTableItem id = new FrameDBD4ToPostgres.DBDesignerTableItem(table);
            vectorTables.add(id);
        }

        this.listSelecaoTabelas.setListData(vectorTables);
        // Atualiza a dimensão da lista de tabelas


        // --------------------------------------------------
        // Carrega a tabela de Tipos de Colunas
        HashMap<String, String> mapColumnsTypes = dbDesignerParser.getColumnsDataTypes();
        // Ordena lista
        ArrayList arrayListColumnsTypes = new ArrayList(mapColumnsTypes.values());
        Collections.sort(arrayListColumnsTypes);
        //

        // Mapeia os dados para Object[][]
        // inicialmente o valores das 2 colunas serão iguais.

        Object[][] arrayTipoColunas = new Object[arrayListColumnsTypes.size()][2];

        for (int i = 0; i < arrayListColumnsTypes.size(); i++) {

            arrayTipoColunas[i][0] = arrayListColumnsTypes.get(i);
            arrayTipoColunas[i][1] = arrayListColumnsTypes.get(i);

        }
        // Move valores para a lista na tela
        DefaultTableModel modelTiposCampos = (DefaultTableModel) this.tableConverterCampos.getModel();
        String[] jTableColumnNames = {" DBDesigner", "Script"};
        modelTiposCampos.setDataVector(arrayTipoColunas, jTableColumnNames);


        //arrayTipoColunas.
        //------------------------------------

        this.cmdGerarScript.setEnabled(true);
        //
        this.txtFileName = nomeArquivo;
        this.setTitle("DBD4ToPostgres - " + nomeArquivo);
        //
        //org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dbd4topostgres.Dbd4topostgresApp.class).getContext().getResourceMap(FrameDBD4ToPostgres.class);
        //Image image = (Image) resourceMap.getIcon("teste");
        //image.setAccelerationPriority(TOP_ALIGNMENT);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupLookAndFeel = new javax.swing.ButtonGroup();
        buttonGroupObjectIdentification = new javax.swing.ButtonGroup();
        panelPrincipal = new javax.swing.JPanel();
        panelSelecaoOpcoes = new javax.swing.JPanel();
        panelChkOpcoes = new javax.swing.JPanel();
        chkDropTable = new javax.swing.JCheckBox();
        chkCreateTable = new javax.swing.JCheckBox();
        chkCreateView = new javax.swing.JCheckBox();
        chkAddForeignKey = new javax.swing.JCheckBox();
        chkAddForeignKeyWithRelationName = new javax.swing.JCheckBox();
        chkAddAlternateKey = new javax.swing.JCheckBox();
        chkCreateSequence = new javax.swing.JCheckBox();
        chkAddComments = new javax.swing.JCheckBox();
        chkStandardInserts = new javax.swing.JCheckBox();
        panelCmdsOpcoes = new javax.swing.JPanel();
        cmdSelecionarTodasOpcoes = new javax.swing.JButton();
        cmdLimparOpcoes = new javax.swing.JButton();
        cmdGerarScript = new javax.swing.JButton();
        panelConfiguracaoExtras = new javax.swing.JPanel();
        panelOwner = new javax.swing.JPanel();
        lblOwner = new javax.swing.JLabel();
        txtOwner = new javax.swing.JTextField();
        panelObjectIdentification = new javax.swing.JPanel();
        chkObjectIdentification = new javax.swing.JCheckBox();
        radioWithOID = new javax.swing.JRadioButton();
        radioWithoutOID = new javax.swing.JRadioButton();
        panelSelecaoTabelas = new javax.swing.JPanel();
        scrollPaneSelecaoTabelas = new javax.swing.JScrollPane();
        listSelecaoTabelas = new javax.swing.JList();
        CheckListCellRenderer renderer = new CheckListCellRenderer();
        this.listSelecaoTabelas.setCellRenderer(renderer);
        this.listSelecaoTabelas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        CheckListener lst = new CheckListener(this.listSelecaoTabelas);
        this.listSelecaoTabelas.addMouseListener(lst);
        this.listSelecaoTabelas.addKeyListener(lst);
        lblTabelasModelo = new javax.swing.JLabel();
        panelResultadoGeracaoScript = new javax.swing.JPanel();
        scrollPaneResultadoGeracaoScript = new RTextScrollPane();
        txtAreaResultadoGeracaoScript = new RSyntaxTextArea();
        ((RSyntaxTextArea) this.txtAreaResultadoGeracaoScript).setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);

        panelConverterCampos = new javax.swing.JPanel();
        scroolPaneConverterCampos = new javax.swing.JScrollPane();
        tableConverterCampos = new javax.swing.JTable();
        lblTraduzirTipo = new javax.swing.JLabel();
        menuBarPrincipal = new javax.swing.JMenuBar();
        javax.swing.JMenu menuArquivo = new javax.swing.JMenu();
        cmdAbrirModelo = new javax.swing.JMenuItem();
        cmdSalvarScript = new javax.swing.JMenuItem();
        separatorSair = new javax.swing.JPopupMenu.Separator();
        cmdSair = new javax.swing.JMenuItem();
        menuAparencia = new javax.swing.JMenu();
        rdLFAutumn = new javax.swing.JRadioButtonMenuItem();
        rdLFBusiness = new javax.swing.JRadioButtonMenuItem();
        rdLFBusinessBlackSteel = new javax.swing.JRadioButtonMenuItem();
        rdLFBusinessBlueSteel = new javax.swing.JRadioButtonMenuItem();
        rdLFChallengerDeep = new javax.swing.JRadioButtonMenuItem();
        rdLFCreme = new javax.swing.JRadioButtonMenuItem();
        rdLFCremeCoffee = new javax.swing.JRadioButtonMenuItem();
        rdLFEmeraldDusk = new javax.swing.JRadioButtonMenuItem();
        rdLFMagma = new javax.swing.JRadioButtonMenuItem();
        rdLFMistAqua = new javax.swing.JRadioButtonMenuItem();
        rdLFMistSilver = new javax.swing.JRadioButtonMenuItem();
        rdLFModerate = new javax.swing.JRadioButtonMenuItem();
        rdLFNebula = new javax.swing.JRadioButtonMenuItem();
        rdLFNebulaBrickWall = new javax.swing.JRadioButtonMenuItem();
        rdLFOfficeBlue2007 = new javax.swing.JRadioButtonMenuItem();
        rdLFOfficeSilver2007 = new javax.swing.JRadioButtonMenuItem();
        rdLFRaven = new javax.swing.JRadioButtonMenuItem();
        rdLFRavenGraphite = new javax.swing.JRadioButtonMenuItem();
        rdLFRavenGraphiteGlass = new javax.swing.JRadioButtonMenuItem();
        rdLFSahara = new javax.swing.JRadioButtonMenuItem();
        javax.swing.JMenu menuAjuda = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImages(null);
        setName("Form"); // NOI18N
        setState(JFrame.NORMAL);

        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 20, 10));
        panelPrincipal.setMinimumSize(panelPrincipal.getPreferredSize());
        panelPrincipal.setName("panelPrincipal"); // NOI18N
        panelPrincipal.setPreferredSize(new java.awt.Dimension(700, 500));
        panelPrincipal.setRequestFocusEnabled(false);
        panelPrincipal.setLayout(new java.awt.BorderLayout(5, 5));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dbd4topostgres.Dbd4topostgresApp.class).getContext().getResourceMap(FrameDBD4ToPostgres.class);
        panelSelecaoOpcoes.setFont(resourceMap.getFont("panelSelecaoOpcoes.font")); // NOI18N
        panelSelecaoOpcoes.setMinimumSize(panelSelecaoOpcoes.getPreferredSize());
        panelSelecaoOpcoes.setName("panelSelecaoOpcoes"); // NOI18N
        panelSelecaoOpcoes.setPreferredSize(new java.awt.Dimension(700, 150));
        panelSelecaoOpcoes.setLayout(new java.awt.BorderLayout());

        panelChkOpcoes.setMinimumSize(panelChkOpcoes.getPreferredSize());
        panelChkOpcoes.setName("panelChkOpcoes"); // NOI18N
        panelChkOpcoes.setLayout(new java.awt.GridLayout(3, 3));

        chkDropTable.setText(resourceMap.getString("chkDropTable.text")); // NOI18N
        chkDropTable.setToolTipText(resourceMap.getString("chkDropTable.toolTipText")); // NOI18N
        chkDropTable.setName("chkDropTable"); // NOI18N
        chkDropTable.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkDropTable);

        chkCreateTable.setText(resourceMap.getString("chkCreateTable.text")); // NOI18N
        chkCreateTable.setToolTipText(resourceMap.getString("chkCreateTable.toolTipText")); // NOI18N
        chkCreateTable.setName("chkCreateTable"); // NOI18N
        chkCreateTable.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkCreateTable);

        chkCreateView.setText(resourceMap.getString("chkCreateView.text")); // NOI18N
        chkCreateView.setToolTipText(resourceMap.getString("chkCreateView.toolTipText")); // NOI18N
        chkCreateView.setName("chkCreateView"); // NOI18N
        chkCreateView.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkCreateView);

        chkAddForeignKey.setText(resourceMap.getString("chkAddForeignKey.text")); // NOI18N
        chkAddForeignKey.setToolTipText(resourceMap.getString("chkAddForeignKey.toolTipText")); // NOI18N
        chkAddForeignKey.setName("chkAddForeignKey"); // NOI18N
        chkAddForeignKey.setPreferredSize(new java.awt.Dimension(80, 20));
        chkAddForeignKey.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkAddForeignKeyStateChanged(evt);
            }
        });
        panelChkOpcoes.add(chkAddForeignKey);

        chkAddForeignKeyWithRelationName.setText(resourceMap.getString("chkAddForeignKeyWithRelationName.text")); // NOI18N
        chkAddForeignKeyWithRelationName.setToolTipText(resourceMap.getString("chkAddForeignKeyWithRelationName.toolTipText")); // NOI18N
        chkAddForeignKeyWithRelationName.setName("chkAddForeignKeyWithRelationName"); // NOI18N
        chkAddForeignKeyWithRelationName.setPreferredSize(new java.awt.Dimension(80, 20));
        chkAddForeignKeyWithRelationName.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkAddForeignKeyWithRelationNameStateChanged(evt);
            }
        });
        panelChkOpcoes.add(chkAddForeignKeyWithRelationName);

        chkAddAlternateKey.setText(resourceMap.getString("chkAddAlternateKey.text")); // NOI18N
        chkAddAlternateKey.setToolTipText(resourceMap.getString("chkAddAlternateKey.toolTipText")); // NOI18N
        chkAddAlternateKey.setName("chkAddAlternateKey"); // NOI18N
        chkAddAlternateKey.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkAddAlternateKey);

        chkCreateSequence.setText(resourceMap.getString("chkCreateSequence.text")); // NOI18N
        chkCreateSequence.setToolTipText(resourceMap.getString("chkCreateSequence.toolTipText")); // NOI18N
        chkCreateSequence.setName("chkCreateSequence"); // NOI18N
        chkCreateSequence.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkCreateSequence);

        chkAddComments.setText(resourceMap.getString("chkAddComments.text")); // NOI18N
        chkAddComments.setToolTipText(resourceMap.getString("chkAddComments.toolTipText")); // NOI18N
        chkAddComments.setName("chkAddComments"); // NOI18N
        chkAddComments.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkAddComments);

        chkStandardInserts.setText(resourceMap.getString("chkStandardInserts.text")); // NOI18N
        chkStandardInserts.setToolTipText(resourceMap.getString("chkStandardInserts.toolTipText")); // NOI18N
        chkStandardInserts.setName("chkStandardInserts"); // NOI18N
        chkStandardInserts.setPreferredSize(new java.awt.Dimension(80, 20));
        panelChkOpcoes.add(chkStandardInserts);

        panelSelecaoOpcoes.add(panelChkOpcoes, java.awt.BorderLayout.CENTER);

        panelCmdsOpcoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 100));
        panelCmdsOpcoes.setMinimumSize(panelCmdsOpcoes.getPreferredSize());
        panelCmdsOpcoes.setName("panelCmdsOpcoes"); // NOI18N
        panelCmdsOpcoes.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        cmdSelecionarTodasOpcoes.setBackground(resourceMap.getColor("cmdSelecionarTodasOpcoes.background")); // NOI18N
        cmdSelecionarTodasOpcoes.setIcon(resourceMap.getIcon("cmdSelecionarTodasOpcoes.icon")); // NOI18N
        cmdSelecionarTodasOpcoes.setMnemonic('T');
        cmdSelecionarTodasOpcoes.setText(resourceMap.getString("cmdSelecionarTodasOpcoes.text")); // NOI18N
        cmdSelecionarTodasOpcoes.setToolTipText(resourceMap.getString("cmdSelecionarTodasOpcoes.toolTipText")); // NOI18N
        cmdSelecionarTodasOpcoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 5, 12, 5));
        cmdSelecionarTodasOpcoes.setContentAreaFilled(false);
        cmdSelecionarTodasOpcoes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cmdSelecionarTodasOpcoes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cmdSelecionarTodasOpcoes.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdSelecionarTodasOpcoes.setMaximumSize(new java.awt.Dimension(100, 15));
        cmdSelecionarTodasOpcoes.setMinimumSize(new java.awt.Dimension(100, 15));
        cmdSelecionarTodasOpcoes.setName("cmdSelecionarTodasOpcoes"); // NOI18N
        cmdSelecionarTodasOpcoes.setPreferredSize(new java.awt.Dimension(100, 15));
        cmdSelecionarTodasOpcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSelecionarTodasOpcoesActionPerformed(evt);
            }
        });
        panelCmdsOpcoes.add(cmdSelecionarTodasOpcoes);

        cmdLimparOpcoes.setBackground(resourceMap.getColor("cmdLimparOpcoes.background")); // NOI18N
        cmdLimparOpcoes.setIcon(resourceMap.getIcon("cmdLimparOpcoes.icon")); // NOI18N
        cmdLimparOpcoes.setMnemonic('L');
        cmdLimparOpcoes.setText(resourceMap.getString("cmdLimparOpcoes.text")); // NOI18N
        cmdLimparOpcoes.setToolTipText(resourceMap.getString("cmdLimparOpcoes.toolTipText")); // NOI18N
        cmdLimparOpcoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 5, 12, 5));
        cmdLimparOpcoes.setContentAreaFilled(false);
        cmdLimparOpcoes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cmdLimparOpcoes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cmdLimparOpcoes.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdLimparOpcoes.setMaximumSize(new java.awt.Dimension(100, 15));
        cmdLimparOpcoes.setMinimumSize(new java.awt.Dimension(100, 15));
        cmdLimparOpcoes.setName("cmdLimparOpcoes"); // NOI18N
        cmdLimparOpcoes.setPreferredSize(new java.awt.Dimension(100, 15));
        cmdLimparOpcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLimparOpcoesActionPerformed(evt);
            }
        });
        panelCmdsOpcoes.add(cmdLimparOpcoes);

        cmdGerarScript.setBackground(resourceMap.getColor("cmdGerarScript.background")); // NOI18N
        cmdGerarScript.setIcon(resourceMap.getIcon("cmdGerarScript.icon")); // NOI18N
        cmdGerarScript.setMnemonic('L');
        cmdGerarScript.setText(resourceMap.getString("cmdGerarScript.text")); // NOI18N
        cmdGerarScript.setToolTipText(resourceMap.getString("cmdGerarScript.toolTipText")); // NOI18N
        cmdGerarScript.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 5, 12, 5));
        cmdGerarScript.setContentAreaFilled(false);
        cmdGerarScript.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cmdGerarScript.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cmdGerarScript.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdGerarScript.setMaximumSize(new java.awt.Dimension(100, 15));
        cmdGerarScript.setMinimumSize(new java.awt.Dimension(100, 15));
        cmdGerarScript.setName("cmdGerarScript"); // NOI18N
        cmdGerarScript.setPreferredSize(new java.awt.Dimension(100, 15));
        cmdGerarScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGerarScriptActionPerformed(evt);
            }
        });
        panelCmdsOpcoes.add(cmdGerarScript);

        panelSelecaoOpcoes.add(panelCmdsOpcoes, java.awt.BorderLayout.EAST);

        panelConfiguracaoExtras.setMinimumSize(panelConfiguracaoExtras.getPreferredSize());
        panelConfiguracaoExtras.setName("panelConfiguracaoExtras"); // NOI18N
        panelConfiguracaoExtras.setPreferredSize(new java.awt.Dimension(906, 40));
        panelConfiguracaoExtras.setLayout(new java.awt.GridLayout(1, 0));

        panelOwner.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelOwner.setMinimumSize(panelOwner.getPreferredSize());
        panelOwner.setName("panelOwner"); // NOI18N
        panelOwner.setPreferredSize(new java.awt.Dimension(200, 20));
        panelOwner.setLayout(null);

        lblOwner.setText(resourceMap.getString("lblOwner.text")); // NOI18N
        lblOwner.setName("lblOwner"); // NOI18N
        panelOwner.add(lblOwner);
        lblOwner.setBounds(20, 10, 36, 20);

        txtOwner.setColumns(20);
        txtOwner.setName("txtOwner"); // NOI18N
        panelOwner.add(txtOwner);
        txtOwner.setBounds(80, 10, 150, 20);

        panelConfiguracaoExtras.add(panelOwner);

        panelObjectIdentification.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelObjectIdentification.setMinimumSize(panelObjectIdentification.getPreferredSize());
        panelObjectIdentification.setName("panelObjectIdentification"); // NOI18N
        panelObjectIdentification.setOpaque(false);
        panelObjectIdentification.setPreferredSize(new java.awt.Dimension(450, 20));
        panelObjectIdentification.setLayout(null);

        chkObjectIdentification.setText(resourceMap.getString("chkObjectIdentification.text")); // NOI18N
        chkObjectIdentification.setToolTipText(resourceMap.getString("chkObjectIdentification.toolTipText")); // NOI18N
        chkObjectIdentification.setName("chkObjectIdentification"); // NOI18N
        chkObjectIdentification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkObjectIdentificationActionPerformed(evt);
            }
        });
        panelObjectIdentification.add(chkObjectIdentification);
        chkObjectIdentification.setBounds(10, 10, 80, 23);

        buttonGroupObjectIdentification.add(radioWithOID);
        radioWithOID.setText(resourceMap.getString("radioWithOID.text")); // NOI18N
        radioWithOID.setEnabled(false);
        radioWithOID.setName("radioWithOID"); // NOI18N
        panelObjectIdentification.add(radioWithOID);
        radioWithOID.setBounds(200, 10, 90, 23);

        buttonGroupObjectIdentification.add(radioWithoutOID);
        radioWithoutOID.setSelected(true);
        radioWithoutOID.setText(resourceMap.getString("radioWithoutOID.text")); // NOI18N
        radioWithoutOID.setEnabled(false);
        radioWithoutOID.setName("radioWithoutOID"); // NOI18N
        panelObjectIdentification.add(radioWithoutOID);
        radioWithoutOID.setBounds(90, 10, 100, 23);

        panelConfiguracaoExtras.add(panelObjectIdentification);

        panelSelecaoOpcoes.add(panelConfiguracaoExtras, java.awt.BorderLayout.SOUTH);

        panelPrincipal.add(panelSelecaoOpcoes, java.awt.BorderLayout.NORTH);

        panelSelecaoTabelas.setMinimumSize(panelSelecaoTabelas.getPreferredSize());
        panelSelecaoTabelas.setName("panelSelecaoTabelas"); // NOI18N
        panelSelecaoTabelas.setPreferredSize(new java.awt.Dimension(200, 350));
        panelSelecaoTabelas.setLayout(new java.awt.BorderLayout());

        scrollPaneSelecaoTabelas.setBackground(resourceMap.getColor("scrollPaneSelecaoTabelas.background")); // NOI18N
        scrollPaneSelecaoTabelas.setMaximumSize(new java.awt.Dimension(32767, 250));
        scrollPaneSelecaoTabelas.setName("scrollPaneSelecaoTabelas"); // NOI18N

        listSelecaoTabelas.setAlignmentX(1.0F);
        listSelecaoTabelas.setAlignmentY(2.0F);
        listSelecaoTabelas.setDragEnabled(true);
        listSelecaoTabelas.setFixedCellWidth(500);
        listSelecaoTabelas.setName("listSelecaoTabelas"); // NOI18N
        scrollPaneSelecaoTabelas.setViewportView(listSelecaoTabelas);

        panelSelecaoTabelas.add(scrollPaneSelecaoTabelas, java.awt.BorderLayout.CENTER);

        lblTabelasModelo.setFont(resourceMap.getFont("lblTabelasModelo.font")); // NOI18N
        lblTabelasModelo.setForeground(resourceMap.getColor("lblTabelasModelo.foreground")); // NOI18N
        lblTabelasModelo.setText(resourceMap.getString("lblTabelasModelo.text")); // NOI18N
        lblTabelasModelo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lblTabelasModelo.setName("lblTabelasModelo"); // NOI18N
        lblTabelasModelo.setOpaque(true);
        lblTabelasModelo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panelSelecaoTabelas.add(lblTabelasModelo, java.awt.BorderLayout.NORTH);

        panelPrincipal.add(panelSelecaoTabelas, java.awt.BorderLayout.WEST);

        panelResultadoGeracaoScript.setMinimumSize(panelResultadoGeracaoScript.getPreferredSize());
        panelResultadoGeracaoScript.setName("panelResultadoGeracaoScript"); // NOI18N
        panelResultadoGeracaoScript.setPreferredSize(new java.awt.Dimension(500, 300));
        panelResultadoGeracaoScript.setLayout(new java.awt.BorderLayout());

        scrollPaneResultadoGeracaoScript.setMinimumSize(scrollPaneResultadoGeracaoScript.getPreferredSize());
        scrollPaneResultadoGeracaoScript.setName("scrollPaneResultadoGeracaoScript"); // NOI18N
        scrollPaneResultadoGeracaoScript.setPreferredSize(new java.awt.Dimension(500, 350));

        txtAreaResultadoGeracaoScript.setColumns(20);
        txtAreaResultadoGeracaoScript.setRows(5);
        txtAreaResultadoGeracaoScript.setName("txtAreaResultadoGeracaoScript"); // NOI18N
        txtAreaResultadoGeracaoScript.setOpaque(false);
        scrollPaneResultadoGeracaoScript.setViewportView(txtAreaResultadoGeracaoScript);

        panelResultadoGeracaoScript.add(scrollPaneResultadoGeracaoScript, java.awt.BorderLayout.CENTER);

        panelPrincipal.add(panelResultadoGeracaoScript, java.awt.BorderLayout.CENTER);

        panelConverterCampos.setMinimumSize(panelConverterCampos.getPreferredSize());
        panelConverterCampos.setName("panelConverterCampos"); // NOI18N
        panelConverterCampos.setPreferredSize(new java.awt.Dimension(200, 500));
        panelConverterCampos.setLayout(new java.awt.BorderLayout());

        scroolPaneConverterCampos.setBackground(resourceMap.getColor("scroolPaneConverterCampos.background")); // NOI18N
        scroolPaneConverterCampos.setMinimumSize(scroolPaneConverterCampos.getPreferredSize());
        scroolPaneConverterCampos.setName("scroolPaneConverterCampos"); // NOI18N

        tableConverterCampos.setAutoCreateRowSorter(true);
        tableConverterCampos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                " DBDesigner", "Script"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableConverterCampos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableConverterCampos.setDragEnabled(true);
        tableConverterCampos.setName("tableConverterCampos"); // NOI18N
        scroolPaneConverterCampos.setViewportView(tableConverterCampos);

        panelConverterCampos.add(scroolPaneConverterCampos, java.awt.BorderLayout.CENTER);

        lblTraduzirTipo.setFont(resourceMap.getFont("lblTraduzirTipo.font")); // NOI18N
        lblTraduzirTipo.setForeground(resourceMap.getColor("lblTraduzirTipo.foreground")); // NOI18N
        lblTraduzirTipo.setText(resourceMap.getString("lblTraduzirTipo.text")); // NOI18N
        lblTraduzirTipo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        lblTraduzirTipo.setName("lblTraduzirTipo"); // NOI18N
        lblTraduzirTipo.setOpaque(true);
        lblTraduzirTipo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panelConverterCampos.add(lblTraduzirTipo, java.awt.BorderLayout.NORTH);

        panelPrincipal.add(panelConverterCampos, java.awt.BorderLayout.EAST);

        getContentPane().add(panelPrincipal, java.awt.BorderLayout.CENTER);

        menuBarPrincipal.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        menuBarPrincipal.setFont(resourceMap.getFont("menuBarPrincipal.font")); // NOI18N
        menuBarPrincipal.setMargin(new java.awt.Insets(0, 10, 0, 10));
        menuBarPrincipal.setName("menuBarPrincipal"); // NOI18N
        menuBarPrincipal.setPreferredSize(new java.awt.Dimension(700, 30));

        menuArquivo.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(resourceMap.getColor("menuArquivo.border.outsideBorder.lineColor"), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 5))); // NOI18N
        menuArquivo.setForeground(resourceMap.getColor("menuArquivo.foreground")); // NOI18N
        menuArquivo.setText(resourceMap.getString("menuArquivo.text")); // NOI18N
        menuArquivo.setFont(resourceMap.getFont("menuArquivo.font")); // NOI18N
        menuArquivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuArquivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuArquivo.setIconTextGap(10);
        menuArquivo.setName("menuArquivo"); // NOI18N
        menuArquivo.setPreferredSize(new java.awt.Dimension(80, 30));

        cmdAbrirModelo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        cmdAbrirModelo.setText(resourceMap.getString("cmdAbrirModelo.text")); // NOI18N
        cmdAbrirModelo.setToolTipText(resourceMap.getString("cmdAbrirModelo.toolTipText")); // NOI18N
        cmdAbrirModelo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        cmdAbrirModelo.setName("cmdAbrirModelo"); // NOI18N
        cmdAbrirModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAbrirModeloActionPerformed(evt);
            }
        });
        menuArquivo.add(cmdAbrirModelo);

        cmdSalvarScript.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        cmdSalvarScript.setText(resourceMap.getString("cmdSalvarScript.text")); // NOI18N
        cmdSalvarScript.setToolTipText(resourceMap.getString("cmdSalvarScript.toolTipText")); // NOI18N
        cmdSalvarScript.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        cmdSalvarScript.setName("cmdSalvarScript"); // NOI18N
        cmdSalvarScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSalvarScriptActionPerformed(evt);
            }
        });
        menuArquivo.add(cmdSalvarScript);

        separatorSair.setName("separatorSair"); // NOI18N
        separatorSair.setOpaque(true);
        menuArquivo.add(separatorSair);

        cmdSair.setText(resourceMap.getString("cmdSair.text")); // NOI18N
        cmdSair.setToolTipText(resourceMap.getString("cmdSair.toolTipText")); // NOI18N
        cmdSair.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        cmdSair.setName("cmdSair"); // NOI18N
        cmdSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSairActionPerformed(evt);
            }
        });
        menuArquivo.add(cmdSair);

        menuBarPrincipal.add(menuArquivo);

        menuAparencia.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(resourceMap.getColor("menuAparencia.border.outsideBorder.lineColor"), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 5))); // NOI18N
        menuAparencia.setForeground(resourceMap.getColor("menuAparencia.foreground")); // NOI18N
        menuAparencia.setText(resourceMap.getString("menuAparencia.text")); // NOI18N
        menuAparencia.setToolTipText(resourceMap.getString("menuAparencia.toolTipText")); // NOI18N
        menuAparencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuAparencia.setName("menuAparencia"); // NOI18N
        menuAparencia.setPreferredSize(new java.awt.Dimension(80, 30));

        buttonGroupLookAndFeel.add(rdLFAutumn);
        rdLFAutumn.setText(resourceMap.getString("rdLFAutumn.text")); // NOI18N
        rdLFAutumn.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFAutumn.setName("rdLFAutumn"); // NOI18N
        rdLFAutumn.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFAutumn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFAutumnActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFAutumn);

        buttonGroupLookAndFeel.add(rdLFBusiness);
        rdLFBusiness.setText(resourceMap.getString("rdLFBusiness.text")); // NOI18N
        rdLFBusiness.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFBusiness.setName("rdLFBusiness"); // NOI18N
        rdLFBusiness.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFBusiness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFBusinessActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFBusiness);

        buttonGroupLookAndFeel.add(rdLFBusinessBlackSteel);
        rdLFBusinessBlackSteel.setText(resourceMap.getString("rdLFBusinessBlackSteel.text")); // NOI18N
        rdLFBusinessBlackSteel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFBusinessBlackSteel.setName("rdLFBusinessBlackSteel"); // NOI18N
        rdLFBusinessBlackSteel.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFBusinessBlackSteel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFBusinessBlackSteelActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFBusinessBlackSteel);

        buttonGroupLookAndFeel.add(rdLFBusinessBlueSteel);
        rdLFBusinessBlueSteel.setText(resourceMap.getString("rdLFBusinessBlueSteel.text")); // NOI18N
        rdLFBusinessBlueSteel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFBusinessBlueSteel.setName("rdLFBusinessBlueSteel"); // NOI18N
        rdLFBusinessBlueSteel.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFBusinessBlueSteel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFBusinessBlueSteelActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFBusinessBlueSteel);

        buttonGroupLookAndFeel.add(rdLFChallengerDeep);
        rdLFChallengerDeep.setText(resourceMap.getString("rdLFChallengerDeep.text")); // NOI18N
        rdLFChallengerDeep.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFChallengerDeep.setName("rdLFChallengerDeep"); // NOI18N
        rdLFChallengerDeep.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFChallengerDeep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFChallengerDeepActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFChallengerDeep);

        buttonGroupLookAndFeel.add(rdLFCreme);
        rdLFCreme.setText(resourceMap.getString("rdLFCreme.text")); // NOI18N
        rdLFCreme.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFCreme.setName("rdLFCreme"); // NOI18N
        rdLFCreme.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFCreme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFCremeActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFCreme);

        buttonGroupLookAndFeel.add(rdLFCremeCoffee);
        rdLFCremeCoffee.setText(resourceMap.getString("rdLFCremeCoffee.text")); // NOI18N
        rdLFCremeCoffee.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFCremeCoffee.setName("rdLFCremeCoffee"); // NOI18N
        rdLFCremeCoffee.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFCremeCoffee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFCremeCoffeeActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFCremeCoffee);

        buttonGroupLookAndFeel.add(rdLFEmeraldDusk);
        rdLFEmeraldDusk.setText(resourceMap.getString("rdLFEmeraldDusk.text")); // NOI18N
        rdLFEmeraldDusk.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFEmeraldDusk.setName("rdLFEmeraldDusk"); // NOI18N
        rdLFEmeraldDusk.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFEmeraldDusk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFEmeraldDuskActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFEmeraldDusk);

        buttonGroupLookAndFeel.add(rdLFMagma);
        rdLFMagma.setText(resourceMap.getString("rdLFMagma.text")); // NOI18N
        rdLFMagma.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFMagma.setName("rdLFMagma"); // NOI18N
        rdLFMagma.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFMagma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFMagmaActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFMagma);

        buttonGroupLookAndFeel.add(rdLFMistAqua);
        rdLFMistAqua.setText(resourceMap.getString("rdLFMistAqua.text")); // NOI18N
        rdLFMistAqua.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFMistAqua.setName("rdLFMistAqua"); // NOI18N
        rdLFMistAqua.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFMistAqua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFMistAquaActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFMistAqua);

        buttonGroupLookAndFeel.add(rdLFMistSilver);
        rdLFMistSilver.setText(resourceMap.getString("rdLFMistSilver.text")); // NOI18N
        rdLFMistSilver.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFMistSilver.setName("rdLFMistSilver"); // NOI18N
        rdLFMistSilver.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFMistSilver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFMistSilverActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFMistSilver);

        buttonGroupLookAndFeel.add(rdLFModerate);
        rdLFModerate.setText(resourceMap.getString("rdLFModerate.text")); // NOI18N
        rdLFModerate.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFModerate.setName("rdLFModerate"); // NOI18N
        rdLFModerate.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFModerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFModerateActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFModerate);

        buttonGroupLookAndFeel.add(rdLFNebula);
        rdLFNebula.setText(resourceMap.getString("rdLFNebula.text")); // NOI18N
        rdLFNebula.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFNebula.setName("rdLFNebula"); // NOI18N
        rdLFNebula.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFNebula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFNebulaActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFNebula);

        buttonGroupLookAndFeel.add(rdLFNebulaBrickWall);
        rdLFNebulaBrickWall.setText(resourceMap.getString("rdLFNebulaBrickWall.text")); // NOI18N
        rdLFNebulaBrickWall.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFNebulaBrickWall.setName("rdLFNebulaBrickWall"); // NOI18N
        rdLFNebulaBrickWall.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFNebulaBrickWall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFNebulaBrickWallActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFNebulaBrickWall);

        buttonGroupLookAndFeel.add(rdLFOfficeBlue2007);
        rdLFOfficeBlue2007.setText(resourceMap.getString("rdLFOfficeBlue2007.text")); // NOI18N
        rdLFOfficeBlue2007.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFOfficeBlue2007.setName("rdLFOfficeBlue2007"); // NOI18N
        rdLFOfficeBlue2007.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFOfficeBlue2007.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFOfficeBlue2007ActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFOfficeBlue2007);

        buttonGroupLookAndFeel.add(rdLFOfficeSilver2007);
        rdLFOfficeSilver2007.setText(resourceMap.getString("rdLFOfficeSilver2007.text")); // NOI18N
        rdLFOfficeSilver2007.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFOfficeSilver2007.setName("rdLFOfficeSilver2007"); // NOI18N
        rdLFOfficeSilver2007.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFOfficeSilver2007.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFOfficeSilver2007ActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFOfficeSilver2007);

        buttonGroupLookAndFeel.add(rdLFRaven);
        rdLFRaven.setText(resourceMap.getString("rdLFRaven.text")); // NOI18N
        rdLFRaven.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFRaven.setName("rdLFRaven"); // NOI18N
        rdLFRaven.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFRaven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFRavenActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFRaven);

        buttonGroupLookAndFeel.add(rdLFRavenGraphite);
        rdLFRavenGraphite.setText(resourceMap.getString("rdLFRavenGraphite.text")); // NOI18N
        rdLFRavenGraphite.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFRavenGraphite.setName("rdLFRavenGraphite"); // NOI18N
        rdLFRavenGraphite.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFRavenGraphite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFRavenGraphiteActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFRavenGraphite);

        buttonGroupLookAndFeel.add(rdLFRavenGraphiteGlass);
        rdLFRavenGraphiteGlass.setText(resourceMap.getString("rdLFRavenGraphiteGlass.text")); // NOI18N
        rdLFRavenGraphiteGlass.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFRavenGraphiteGlass.setName("rdLFRavenGraphiteGlass"); // NOI18N
        rdLFRavenGraphiteGlass.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFRavenGraphiteGlass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFRavenGraphiteGlassActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFRavenGraphiteGlass);

        buttonGroupLookAndFeel.add(rdLFSahara);
        rdLFSahara.setText(resourceMap.getString("rdLFSahara.text")); // NOI18N
        rdLFSahara.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        rdLFSahara.setName("rdLFSahara"); // NOI18N
        rdLFSahara.setPreferredSize(new java.awt.Dimension(200, 25));
        rdLFSahara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdLFSaharaActionPerformed(evt);
            }
        });
        menuAparencia.add(rdLFSahara);

        menuBarPrincipal.add(menuAparencia);

        menuAjuda.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(resourceMap.getColor("menuAjuda.border.outsideBorder.lineColor"), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 5))); // NOI18N
        menuAjuda.setForeground(resourceMap.getColor("menuAjuda.foreground")); // NOI18N
        menuAjuda.setText(resourceMap.getString("menuAjuda.text")); // NOI18N
        menuAjuda.setFont(resourceMap.getFont("menuAjuda.font")); // NOI18N
        menuAjuda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuAjuda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuAjuda.setIconTextGap(10);
        menuAjuda.setName("menuAjuda"); // NOI18N
        menuAjuda.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(dbd4topostgres.Dbd4topostgresApp.class).getContext().getActionMap(FrameDBD4ToPostgres.class, this);
        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setToolTipText(resourceMap.getString("aboutMenuItem.toolTipText")); // NOI18N
        aboutMenuItem.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        menuAjuda.add(aboutMenuItem);

        menuBarPrincipal.add(menuAjuda);

        setJMenuBar(menuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkAddForeignKeyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkAddForeignKeyStateChanged
        // TODO add your handling code here:
        if (!this.chkAddForeignKey.isSelected()) {
            this.chkAddForeignKeyWithRelationName.setSelected(false);
        }
}//GEN-LAST:event_chkAddForeignKeyStateChanged

    private void chkAddForeignKeyWithRelationNameStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkAddForeignKeyWithRelationNameStateChanged
        // TODO add your handling code here:
        if (this.chkAddForeignKeyWithRelationName.isSelected()) {
            this.chkAddForeignKey.setSelected(true);
        }
}//GEN-LAST:event_chkAddForeignKeyWithRelationNameStateChanged

    private void cmdSelecionarTodasOpcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelecionarTodasOpcoesActionPerformed

        this.chkAddAlternateKey.setSelected(true);
        this.chkAddForeignKey.setSelected(true);
        this.chkAddForeignKeyWithRelationName.setSelected(true);
        this.chkCreateSequence.setSelected(true);
        this.chkCreateTable.setSelected(true);
        this.chkCreateView.setSelected(true);
        this.chkDropTable.setSelected(true);
        this.chkAddComments.setSelected(true);
        this.chkStandardInserts.setSelected(true);
    }//GEN-LAST:event_cmdSelecionarTodasOpcoesActionPerformed

    private void cmdLimparOpcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLimparOpcoesActionPerformed
        this.chkAddAlternateKey.setSelected(false);
        this.chkAddForeignKey.setSelected(false);
        this.chkAddForeignKeyWithRelationName.setSelected(false);
        this.chkCreateSequence.setSelected(false);
        this.chkCreateTable.setSelected(false);
        this.chkCreateView.setSelected(false);
        this.chkDropTable.setSelected(false);
        this.chkAddComments.setSelected(false);
        this.chkStandardInserts.setSelected(false);
}//GEN-LAST:event_cmdLimparOpcoesActionPerformed

    private void cmdGerarScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGerarScriptActionPerformed
        // TODO add your handling code here:
        if (this.txtFileName.length() > 0) {
            try {
                DBDesignerParser DBDesignerParser = new DBDesignerParser(this.txtFileName);
                HashSet setTabelasSelecionadas = new HashSet();
                FrameDBD4ToPostgres.DBDesignerTableItem idTabela = null;
                for (int i = 0; i < this.listSelecaoTabelas.getModel().getSize(); i++) {
                    idTabela = (FrameDBD4ToPostgres.DBDesignerTableItem) this.listSelecaoTabelas.getModel().getElementAt(i);
                    if (idTabela.isSelected()) {
                        setTabelasSelecionadas.add(idTabela.getName());
                    }
                }
                //this.txtAreaResultadoGeracaoScript.setText("");


                //
                // OID = Cria string
                String descricaoOID = null;
                if (this.chkObjectIdentification.isSelected()) {
                    if (this.radioWithOID.isSelected()) {
                        descricaoOID = "WITH (OIDS=TRUE)";
                    } else {
                        descricaoOID = "WITH (OIDS=FALSE)";
                    }
                }
                //
                //
                StringBuilder scriptSql = new StringBuilder();
                if (this.chkCreateTable.isSelected() || this.chkAddComments.isSelected() || (this.chkCreateTable.isSelected() && this.chkDropTable.isSelected())) {
                    // Obtem o nome de colunas da lista de colunas da tela
                    // Move valores para a lista na tela
                    DefaultTableModel modelTiposCampos = (DefaultTableModel) this.tableConverterCampos.getModel();
                    Vector vetorCampos = modelTiposCampos.getDataVector();
                    // Convert para lista de campos para hashmap
                    HashMap<String, String> mapCamposTraducao = new HashMap();
                    String campoOriginal = null;
                    String campoScript = null;
                    for (int i = 0; i < vetorCampos.size(); i++) {
                        campoOriginal = (String) ((Vector) vetorCampos.elementAt(i)).elementAt(0);
                        campoOriginal = campoOriginal.replaceAll(" ", "");
                        //
                        campoScript = (String) ((Vector) vetorCampos.elementAt(i)).elementAt(1);
                        campoScript = campoScript.replaceAll(" ", "");
                        //
                        mapCamposTraducao.put(campoOriginal, campoScript);
                    }
                    // Executa chamada Create Tables
                    //
                    scriptSql.append(DBDesignerParser.sqlCreateTable(setTabelasSelecionadas, mapCamposTraducao, this.txtOwner.getText(), descricaoOID, this.chkCreateTable.isSelected(), this.chkAddComments.isSelected(), this.chkDropTable.isSelected()));
                    scriptSql.append("\r\n");
                }
                if (this.chkCreateView.isSelected() || (this.chkCreateView.isSelected() &&this.chkDropTable.isSelected())) {
                    scriptSql.append(DBDesignerParser.sqlCreateView(setTabelasSelecionadas, this.txtOwner.getText(), this.chkCreateView.isSelected(), this.chkDropTable.isSelected()));
                    scriptSql.append("\r\n");
                }
                if (this.chkAddForeignKey.isSelected()) {
                    scriptSql.append(DBDesignerParser.sqlCreateForeingKey(setTabelasSelecionadas, this.chkAddForeignKeyWithRelationName.isSelected()));
                    scriptSql.append("\r\n");
                }
                if (this.chkAddAlternateKey.isSelected() || (this.chkAddAlternateKey.isSelected() && this.chkDropTable.isSelected())) {
                    scriptSql.append(DBDesignerParser.sqlCreateAlternatingKey(setTabelasSelecionadas, this.chkAddAlternateKey.isSelected(), this.chkDropTable.isSelected()));
                    scriptSql.append("\r\n");
                }
                if (this.chkCreateSequence.isSelected() || (this.chkCreateSequence.isSelected() && this.chkDropTable.isSelected())) {
                    // Cria a sequencia
                    scriptSql.append(DBDesignerParser.sqlCreateSequence(setTabelasSelecionadas, this.txtOwner.getText(), this.chkCreateSequence.isSelected(), this.chkDropTable.isSelected()));
                    scriptSql.append("\r\n");
                    if (this.chkCreateSequence.isSelected()) {
                        // Insere a sequencia como default value
                        scriptSql.append(DBDesignerParser.sqlSetDefault(setTabelasSelecionadas));
                        scriptSql.append("\r\n");
                    }
                }
                //if (this.chkSetDefautValue.isSelected()) {
                //    scriptSql.append(dbDesignerParser.sqlSetDefault(setTabelasSelecionadas));
                //   scriptSql.append("\r\n");
                // }

                if (this.chkStandardInserts.isSelected()) {
                    scriptSql.append(DBDesignerParser.sqlCreateTableStandardInserts(setTabelasSelecionadas));
                    scriptSql.append("\r\n");
                }

                this.txtAreaResultadoGeracaoScript.setText(scriptSql.toString());
                this.txtAreaResultadoGeracaoScript.setCaretPosition(0);

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar sql." + e1.getMessage());
            }
        }
}//GEN-LAST:event_cmdGerarScriptActionPerformed

    private void chkObjectIdentificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkObjectIdentificationActionPerformed
        if (this.chkObjectIdentification.isSelected()) {
            this.radioWithOID.setEnabled(true);
            this.radioWithoutOID.setEnabled(true);
        } else {
            this.radioWithOID.setEnabled(false);
            this.radioWithoutOID.setEnabled(false);
        }
}//GEN-LAST:event_chkObjectIdentificationActionPerformed

    private void cmdAbrirModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAbrirModeloActionPerformed
        // Seleciona o ultimo arquivo aberto
        JFileChooser fc = null;
        //
        if (this.diretorioCorrenteEntrada != null) {
            fc = new JFileChooser(this.diretorioCorrenteEntrada);
        } else {
            String lastInputDir = this.preferences.get("LAST_INPUT_DIR", "");
            if (lastInputDir != null && (!lastInputDir.trim().equals(""))) {
                fc = new JFileChooser(lastInputDir);
            } else {
                fc = new JFileChooser();
            }

        }
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileFilter fileFilter = new FileNameExtensionFilter("DB Designer Model 4 XML", "xml", "XML");
        fc.addChoosableFileFilter(fileFilter);

        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);


        fc.setFileFilter(fileFilter);
        int res = fc.showDialog(this, "Abrir Dbdesigner4 Model");
        this.diretorioCorrenteEntrada = fc.getCurrentDirectory();



        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();

                //
                this.preferences.put("LAST_INPUT_DIR", file.getAbsolutePath());
                //
                this.openModel(file);

            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "Arquivo não encontrado");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo. Verifique se o arquivo foi gerado pelo DBDesigner 4.");
            }


        } else {
            JOptionPane.showMessageDialog(this, "Voce nao selecionou nenhum arquivo.");
        }

    }//GEN-LAST:event_cmdAbrirModeloActionPerformed

    private void cmdSalvarScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSalvarScriptActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            JFileChooser fc = null;
            //
            //
            if (this.diretorioCorrenteSaida != null) {
                fc = new JFileChooser(this.diretorioCorrenteSaida);
            } else {
                String lastOutputDir = this.preferences.get("LAST_OUTPUT_DIR", "");
                if (lastOutputDir != null && (!lastOutputDir.trim().equals(""))) {
                    fc = new JFileChooser(lastOutputDir);
                } else {
                    if (this.diretorioCorrenteEntrada != null) {
                        fc = new JFileChooser(this.diretorioCorrenteEntrada.getPath());
                    } else {
                        fc = new JFileChooser();
                    }
                }

            }
            //

            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter fileFilter = new FileNameExtensionFilter("Script SQL", "sql");
            fc.addChoosableFileFilter(fileFilter);

            fc.setAcceptAllFileFilterUsed(false);
            fc.setMultiSelectionEnabled(false);


            fc.setFileFilter(fileFilter);
            int res = fc.showSaveDialog(this);

            this.diretorioCorrenteSaida = fc.getCurrentDirectory();


            if (res == JFileChooser.APPROVE_OPTION) {

                File file = fc.getSelectedFile();
                //
                this.preferences.put("LAST_OUTPUT_DIR", this.diretorioCorrenteSaida.getAbsolutePath());
                //

                String nomeArquivo = file.getPath();
                if (!nomeArquivo.endsWith(".sql")) {
                    nomeArquivo = nomeArquivo + ".sql";
                    file = new File(nomeArquivo);
                }
                int opcaoGravar = JOptionPane.YES_OPTION;
                if (file.exists()) {
                    //default icon, custom title
                    opcaoGravar = JOptionPane.showConfirmDialog(this, "O arquivo já existe. Deseja sobrecrever?", "Arquivo existente", JOptionPane.YES_NO_OPTION);

                }
                if (opcaoGravar == JOptionPane.YES_OPTION) {

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(this.txtAreaResultadoGeracaoScript.getText().getBytes());
                    fos.close();
                }


            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }//GEN-LAST:event_cmdSalvarScriptActionPerformed

    private void cmdSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cmdSairActionPerformed

    private void lookAndFellActionPerfomed(java.awt.event.ActionEvent evt) {
        try {
            String look = evt.getActionCommand();

            if (look.equals("Autumn")) {
                if (!this.rdLFAutumn.isSelected()) {
                    this.rdLFAutumn.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceAutumnLookAndFeel");
                }
            } else if (look.equals("BusinessBlackSteel")) {
                if (!this.rdLFBusinessBlackSteel.isSelected()) {
                    this.rdLFBusinessBlackSteel.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
                }
            } else if (look.equals("BusinessBlueSteel")) {
                if (!this.rdLFBusinessBlueSteel.isSelected()) {
                    this.rdLFBusinessBlueSteel.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
                }
            } else if (look.equals("Business")) {
                if (!this.rdLFBusiness.isSelected()) {
                    this.rdLFBusiness.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
                }

            } else if (look.equals("ChallengerDeep")) {
                if (!this.rdLFChallengerDeep.isSelected()) {
                    this.rdLFChallengerDeep.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel");
                }

            } else if (look.equals("CremeCoffee")) {
                if (!this.rdLFCremeCoffee.isSelected()) {
                    this.rdLFCremeCoffee.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel");
                }
            } else if (look.equals("Creme")) {
                if (!this.rdLFCreme.isSelected()) {
                    this.rdLFCreme.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceCremeLookAndFeel");
                }
            } else if (look.equals("EmeraldDusk")) {
                if (!this.rdLFEmeraldDusk.isSelected()) {
                    this.rdLFEmeraldDusk.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel");
                }
            } else if (look.equals("Magma")) {
                if (!this.rdLFMagma.isSelected()) {
                    this.rdLFMagma.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceMagmaLookAndFeel");
                }
            } else if (look.equals("MistAqua")) {
                if (!this.rdLFMistAqua.isSelected()) {
                    this.rdLFMistAqua.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel");
                }
            } else if (look.equals("MistSilver")) {
                if (!this.rdLFMistSilver.isSelected()) {
                    this.rdLFMistSilver.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceMistSilverLookAndFeel");
                }
            } else if (look.equals("Moderate")) {
                if (!this.rdLFModerate.isSelected()) {
                    this.rdLFModerate.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceModerateLookAndFeel");
                }
            } else if (look.equals("NebulaBrickWall")) {
                if (!this.rdLFNebulaBrickWall.isSelected()) {
                    this.rdLFNebulaBrickWall.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceNebulaBrickWallLookAndFeel");
                }
            } else if (look.equals("Nebula")) {
                if (!this.rdLFNebula.isSelected()) {
                    this.rdLFNebula.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceNebulaLookAndFeel");
                }
            } else if (look.equals("OfficeBlue2007")) {
                if (!this.rdLFOfficeBlue2007.isSelected()) {
                    this.rdLFOfficeBlue2007.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel");
                }
            } else if (look.equals("OfficeSilver2007")) {
                if (!this.rdLFOfficeSilver2007.isSelected()) {
                    this.rdLFOfficeSilver2007.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel");
                }
            } else if (look.equals("RavenGraphiteGlass")) {
                if (!this.rdLFRavenGraphiteGlass.isSelected()) {
                    this.rdLFRavenGraphiteGlass.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
                }
            } else if (look.equals("RavenGraphite")) {
                if (!this.rdLFRavenGraphite.isSelected()) {
                    this.rdLFRavenGraphite.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
                }
            } else if (look.equals("Raven")) {
                if (!this.rdLFRaven.isSelected()) {
                    this.rdLFRaven.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenLookAndFeel");
                }

            } else if (look.equals("Sahara")) {
                if (!this.rdLFSahara.isSelected()) {
                    this.rdLFSahara.doClick();
                } else {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceSaharaLookAndFeel");
                }

            }
            SwingUtilities.updateComponentTreeUI(this);
            //this.pack();


            this.preferences.put("LAST_LOOKANDFELL", look);
            // Setar Background
            ((RSyntaxTextArea) this.txtAreaResultadoGeracaoScript).setBackgroundImage((Image) this.imagemBackground);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed

        (new FrameAjuda()).setVisible(true);
}//GEN-LAST:event_aboutMenuItemActionPerformed

    private void rdLFAutumnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFAutumnActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFAutumnActionPerformed

    private void rdLFBusinessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFBusinessActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFBusinessActionPerformed

    private void rdLFBusinessBlackSteelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFBusinessBlackSteelActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFBusinessBlackSteelActionPerformed

    private void rdLFBusinessBlueSteelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFBusinessBlueSteelActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFBusinessBlueSteelActionPerformed

    private void rdLFChallengerDeepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFChallengerDeepActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFChallengerDeepActionPerformed

    private void rdLFCremeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFCremeActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFCremeActionPerformed

    private void rdLFCremeCoffeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFCremeCoffeeActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFCremeCoffeeActionPerformed

    private void rdLFEmeraldDuskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFEmeraldDuskActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFEmeraldDuskActionPerformed

    private void rdLFMagmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFMagmaActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFMagmaActionPerformed

    private void rdLFMistAquaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFMistAquaActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFMistAquaActionPerformed

    private void rdLFMistSilverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFMistSilverActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFMistSilverActionPerformed

    private void rdLFModerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFModerateActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFModerateActionPerformed

    private void rdLFNebulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFNebulaActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFNebulaActionPerformed

    private void rdLFNebulaBrickWallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFNebulaBrickWallActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFNebulaBrickWallActionPerformed

    private void rdLFOfficeBlue2007ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFOfficeBlue2007ActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFOfficeBlue2007ActionPerformed

    private void rdLFOfficeSilver2007ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFOfficeSilver2007ActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFOfficeSilver2007ActionPerformed

    private void rdLFRavenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFRavenActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFRavenActionPerformed

    private void rdLFRavenGraphiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFRavenGraphiteActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFRavenGraphiteActionPerformed

    private void rdLFRavenGraphiteGlassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFRavenGraphiteGlassActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFRavenGraphiteGlassActionPerformed

    private void rdLFSaharaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdLFSaharaActionPerformed
        this.lookAndFellActionPerfomed(evt);
    }//GEN-LAST:event_rdLFSaharaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Set cross-platform Java L&F (also called "Metal"
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            //UIManager.setLookAndFeel(javax.swing.plaf.metal.MetalLookAndFeel);


            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    new FrameDBD4ToPostgres().setVisible(true);
                }
            });
        } catch (Exception e) {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupLookAndFeel;
    private javax.swing.ButtonGroup buttonGroupObjectIdentification;
    private javax.swing.JCheckBox chkAddAlternateKey;
    private javax.swing.JCheckBox chkAddComments;
    private javax.swing.JCheckBox chkAddForeignKey;
    private javax.swing.JCheckBox chkAddForeignKeyWithRelationName;
    private javax.swing.JCheckBox chkCreateSequence;
    private javax.swing.JCheckBox chkCreateTable;
    private javax.swing.JCheckBox chkCreateView;
    private javax.swing.JCheckBox chkDropTable;
    private javax.swing.JCheckBox chkObjectIdentification;
    private javax.swing.JCheckBox chkStandardInserts;
    private javax.swing.JMenuItem cmdAbrirModelo;
    private javax.swing.JButton cmdGerarScript;
    private javax.swing.JButton cmdLimparOpcoes;
    private javax.swing.JMenuItem cmdSair;
    private javax.swing.JMenuItem cmdSalvarScript;
    private javax.swing.JButton cmdSelecionarTodasOpcoes;
    private javax.swing.JLabel lblOwner;
    private javax.swing.JLabel lblTabelasModelo;
    private javax.swing.JLabel lblTraduzirTipo;
    private javax.swing.JList listSelecaoTabelas;
    private javax.swing.JMenu menuAparencia;
    private javax.swing.JMenuBar menuBarPrincipal;
    private javax.swing.JPanel panelChkOpcoes;
    private javax.swing.JPanel panelCmdsOpcoes;
    private javax.swing.JPanel panelConfiguracaoExtras;
    private javax.swing.JPanel panelConverterCampos;
    private javax.swing.JPanel panelObjectIdentification;
    private javax.swing.JPanel panelOwner;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelResultadoGeracaoScript;
    private javax.swing.JPanel panelSelecaoOpcoes;
    private javax.swing.JPanel panelSelecaoTabelas;
    private javax.swing.JRadioButton radioWithOID;
    private javax.swing.JRadioButton radioWithoutOID;
    private javax.swing.JRadioButtonMenuItem rdLFAutumn;
    private javax.swing.JRadioButtonMenuItem rdLFBusiness;
    private javax.swing.JRadioButtonMenuItem rdLFBusinessBlackSteel;
    private javax.swing.JRadioButtonMenuItem rdLFBusinessBlueSteel;
    private javax.swing.JRadioButtonMenuItem rdLFChallengerDeep;
    private javax.swing.JRadioButtonMenuItem rdLFCreme;
    private javax.swing.JRadioButtonMenuItem rdLFCremeCoffee;
    private javax.swing.JRadioButtonMenuItem rdLFEmeraldDusk;
    private javax.swing.JRadioButtonMenuItem rdLFMagma;
    private javax.swing.JRadioButtonMenuItem rdLFMistAqua;
    private javax.swing.JRadioButtonMenuItem rdLFMistSilver;
    private javax.swing.JRadioButtonMenuItem rdLFModerate;
    private javax.swing.JRadioButtonMenuItem rdLFNebula;
    private javax.swing.JRadioButtonMenuItem rdLFNebulaBrickWall;
    private javax.swing.JRadioButtonMenuItem rdLFOfficeBlue2007;
    private javax.swing.JRadioButtonMenuItem rdLFOfficeSilver2007;
    private javax.swing.JRadioButtonMenuItem rdLFRaven;
    private javax.swing.JRadioButtonMenuItem rdLFRavenGraphite;
    private javax.swing.JRadioButtonMenuItem rdLFRavenGraphiteGlass;
    private javax.swing.JRadioButtonMenuItem rdLFSahara;
    private javax.swing.JScrollPane scrollPaneResultadoGeracaoScript;
    private javax.swing.JScrollPane scrollPaneSelecaoTabelas;
    private javax.swing.JScrollPane scroolPaneConverterCampos;
    private javax.swing.JPopupMenu.Separator separatorSair;
    private javax.swing.JTable tableConverterCampos;
    private javax.swing.JTextArea txtAreaResultadoGeracaoScript;
    private javax.swing.JTextField txtOwner;
    // End of variables declaration//GEN-END:variables

    class CheckListCellRenderer extends JCheckBox
            implements ListCellRenderer {

        protected Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);

        public CheckListCellRenderer() {
            setOpaque(true);
            setBorder(this.m_noFocusBorder);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());

            setBackground(isSelected ? list.getSelectionBackground()
                    : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground()
                    : list.getForeground());

            FrameDBD4ToPostgres.DBDesignerTableItem data = (FrameDBD4ToPostgres.DBDesignerTableItem) value;
            setSelected(data.isSelected());

            setFont(list.getFont());
            setBorder(cellHasFocus
                    ? UIManager.getBorder("List.focusCellHighlightBorder")
                    : this.m_noFocusBorder);

            return this;
        }
    }

    class DBDesignerTableItem {

        protected String m_name;
        protected boolean m_selected;

        public DBDesignerTableItem(String name) {
            this.m_name = name;
            this.m_selected = false;
        }

        public String getName() {
            return this.m_name;
        }

        public void setSelected(boolean selected) {
            this.m_selected = selected;
        }

        public void invertSelected() {
            this.m_selected = (!this.m_selected);
        }

        public boolean isSelected() {
            return this.m_selected;
        }

        @Override
        public String toString() {
            return this.m_name;
        }
    }

    class CheckListener implements MouseListener, KeyListener {

        protected JList listaTabelas;

        public CheckListener(JList listaTabelas) {
            this.listaTabelas = listaTabelas;
        }

        public void mouseClicked(MouseEvent e) {
            doCheck();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == ' ') {
                doCheck();
            }
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

        protected void doCheck() {
            int index[] = this.listaTabelas.getSelectedIndices();
            if (index.length == 0) {
                return;
            }
            FrameDBD4ToPostgres.DBDesignerTableItem data = null;

            for (int i = 0; i < index.length; i++) {
                data = (FrameDBD4ToPostgres.DBDesignerTableItem) this.listaTabelas.getModel().getElementAt(index[i]);
                data.invertSelected();
            }
            this.listaTabelas.repaint();
        }
    }
}
