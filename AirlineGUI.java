package airlineinventory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class AirlineGUI extends JFrame {
    private PassengerStack passengerStack;
    private CargoStack cargoStack; 
    
    private JTextField txtId, txtName, txtAddress, txtContact, txtAge;
    private JComboBox<String> cmbGender;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JLabel lblUnitsLoaded, lblTotalWeight, lblLastLoadedTop, lblFreeSlots, lblPreviewId, lblPreviewDest, lblPreviewPos, lblPreviewWeight;
    private JPanel inventoryStackContainer;

    private JLabel lblStatus;
    private JPanel panelDynamicContent;
    CardLayout cardLayout;

    public AirlineGUI() {
        passengerStack = new PassengerStack(50);
        cargoStack = new CargoStack(50);
        
        cargoStack.push(new CargoItem("CG-097", "Karachi", 212.8, "Ayesha Khan"));
        cargoStack.push(new CargoItem("CG-098", "Doha", 88.2, "Ali Raza"));
        cargoStack.push(new CargoItem("CG-099", "Lahore", 175.8, "Sara Ahmed"));
        cargoStack.push(new CargoItem("CG-100", "Muscat", 64.8, "Bilal Ahmed"));
        cargoStack.push(new CargoItem("CG-101", "Dubai", 150.5, "Usman Khan"));

        setTitle("Airline Inventory System");
        setSize(1180, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bgDark = new Color(11, 19, 36);          
        Color panelDark = new Color(20, 32, 56);       
        Color borderDark = new Color(55, 80, 122);     
        Color textLight = new Color(241, 245, 249); 
        Color accentGold = new Color(212, 175, 55);    

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(bgDark);
        setContentPane(mainPanel);

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        panelHeader.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel lblTitle = new JLabel("  Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        panelHeader.add(lblTitle, BorderLayout.WEST);

        JPanel headerRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        headerRightPanel.setOpaque(false);
        
        JLabel lblDate = new JLabel("📅 Thu, Jul 17, 2025");
        lblDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDate.setForeground(accentGold);
        lblDate.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderDark, 1),
            new EmptyBorder(6, 12, 6, 12)
        ));
        headerRightPanel.add(lblDate);
        panelHeader.add(headerRightPanel, BorderLayout.EAST);

        mainPanel.add(panelHeader, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        panelDynamicContent = new JPanel(cardLayout);
        panelDynamicContent.setOpaque(false);

        JPanel panelDashboard = buildDashboardPanel(panelDark, borderDark, accentGold);
        JPanel panelFlights = buildFlightsPanel(panelDark, borderDark, accentGold);
        JPanel panelPassengers = buildPassengersPanel(panelDark, borderDark, textLight, accentGold);
        JPanel panelInventory = buildInventoryPanel(panelDark, borderDark, accentGold);
        JPanel panelReport = buildReportsPanel(panelDark, borderDark, accentGold);
        JPanel panelAbout = buildAboutPanel(panelDark, borderDark, accentGold);

        panelDynamicContent.add(panelDashboard, "HOME");
        panelDynamicContent.add(panelFlights, "FLIGHTS");
        panelDynamicContent.add(panelPassengers, "PASSENGERS");
        panelDynamicContent.add(panelInventory, "INVENTORY");
        panelDynamicContent.add(panelReport, "REPORT");
        panelDynamicContent.add(panelAbout, "ABOUT");

        mainPanel.add(panelDynamicContent, BorderLayout.CENTER);

        JPanel panelNav = new JPanel();
        panelNav.setLayout(new GridLayout(7, 1, 0, 4));
        panelNav.setPreferredSize(new Dimension(190, 0));
        panelNav.setBackground(panelDark);
        panelNav.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderDark, 1),
            new EmptyBorder(10, 8, 10, 8)
        ));

        JButton btnHome = createNavButtonActive("🏠  Home", borderDark, accentGold);
        JButton btnFlights = createNavButton("✈️  Flights", panelDark);
        JButton btnPass = createNavButton("👥  Passengers", panelDark);
        JButton btnInv = createNavButton("📦  Inventory", panelDark);
        JButton btnRep = createNavButton("📊  Reports", panelDark);
        JButton btnAbt = createNavButton("⚙️  About", panelDark);
        JButton btnExt = createNavButton("🚪  Exit", panelDark);

        btnHome.addActionListener(e -> { cardLayout.show(panelDynamicContent, "HOME"); setActiveNav(panelNav, btnHome, panelDark, borderDark, accentGold); lblTitle.setText("  Dashboard"); });
        btnFlights.addActionListener(e -> { cardLayout.show(panelDynamicContent, "FLIGHTS"); setActiveNav(panelNav, btnFlights, panelDark, borderDark, accentGold); lblTitle.setText("  Dashboard  /  Flights"); });
        btnPass.addActionListener(e -> { cardLayout.show(panelDynamicContent, "PASSENGERS"); setActiveNav(panelNav, btnPass, panelDark, borderDark, accentGold); lblTitle.setText("  Dashboard  /  Passengers"); });
        btnInv.addActionListener(e -> { cardLayout.show(panelDynamicContent, "INVENTORY"); setActiveNav(panelNav, btnInv, panelDark, borderDark, accentGold); lblTitle.setText("  Dashboard  /  Inventory"); updateInventoryUI(); });
        btnRep.addActionListener(e -> { cardLayout.show(panelDynamicContent, "REPORT"); setActiveNav(panelNav, btnRep, panelDark, borderDark, accentGold); lblTitle.setText("  Dashboard  /  Reports"); });
        btnAbt.addActionListener(e -> { cardLayout.show(panelDynamicContent, "ABOUT"); setActiveNav(panelNav, btnAbt, panelDark, borderDark, accentGold); lblTitle.setText("  Dashboard  /  About"); });
        btnExt.addActionListener(e -> System.exit(0));

        panelNav.add(btnHome);
        panelNav.add(btnFlights);
        panelNav.add(btnPass);
        panelNav.add(btnInv);
        panelNav.add(btnRep);
        panelNav.add(btnAbt);
        panelNav.add(btnExt);

        JPanel sidebarWrapper = new JPanel(new BorderLayout(0, 10));
        sidebarWrapper.setOpaque(false);
        
        JPanel brandPanel = new JPanel(new GridLayout(2, 1));
        brandPanel.setOpaque(false);
        JLabel lblBrand = new JLabel("✈️ AirlineInventory");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblBrand.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel("System");
        lblSub.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSub.setForeground(accentGold);
        brandPanel.add(lblBrand);
        brandPanel.add(lblSub);
        brandPanel.setBorder(new EmptyBorder(0, 5, 10, 0));

        sidebarWrapper.add(brandPanel, BorderLayout.NORTH);
        sidebarWrapper.add(panelNav, BorderLayout.CENTER);

        mainPanel.add(sidebarWrapper, BorderLayout.WEST);

        JPanel panelStatus = new JPanel(new BorderLayout());
        panelStatus.setOpaque(false);
        panelStatus.setBorder(BorderFactory.createEmptyBorder(8, 5, 0, 5));

        lblStatus = new JLabel("● System Status: Online");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStatus.setForeground(new Color(74, 222, 128));
        panelStatus.add(lblStatus, BorderLayout.WEST);

        JLabel lblVersion = new JLabel("Airline Inventory System v1.0      Fly Higher... Together ✈️");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblVersion.setForeground(accentGold);
        panelStatus.add(lblVersion, BorderLayout.EAST);

        mainPanel.add(panelStatus, BorderLayout.SOUTH);

        cardLayout.show(panelDynamicContent, "HOME");
        updateInventoryUI();
    }

    private JPanel buildAboutPanel(Color panelDark, Color borderDark, Color accentGold) {
        JPanel panelAbout = new JPanel(new BorderLayout(15, 15));
        panelAbout.setOpaque(false);

        JPanel topHeader = new JPanel(new GridLayout(2, 1, 0, 2));
        topHeader.setOpaque(false);
        JLabel lblHead = new JLabel("⚙️ About System");
        lblHead.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHead.setForeground(accentGold);
        JLabel lblSubhead = new JLabel("System architecture, version details, and institutional lab credentials.");
        lblSubhead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubhead.setForeground(new Color(180, 195, 220));
        topHeader.add(lblHead);
        topHeader.add(lblSubhead);
        panelAbout.add(topHeader, BorderLayout.NORTH);

        JPanel centerCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(28, 45, 77), 0, getHeight(), panelDark);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 50), 0, getHeight() / 2.0f, new Color(255, 255, 255, 5));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 16, 16));

                g2.setColor(borderDark);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }
        };
        centerCard.setOpaque(false);
        centerCard.setBorder(new EmptyBorder(30, 35, 30, 35));

        JPanel contentStack = new JPanel();
        contentStack.setLayout(new BoxLayout(contentStack, BoxLayout.Y_AXIS));
        contentStack.setOpaque(false);

        JLabel lblLogo = new JLabel("✈️ Airline Inventory System");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogo.setForeground(accentGold);
        contentStack.add(lblLogo);
        contentStack.add(Box.createVerticalStrut(8));

        JLabel lblVer = new JLabel("Version 1.0 (Stable Build)");
        lblVer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVer.setForeground(new Color(150, 170, 200));
        contentStack.add(lblVer);
        contentStack.add(Box.createVerticalStrut(25));

        contentStack.add(createAboutRow("Project Objective", "Developed as an Open Ended Lab (OEL) project demonstrating advanced Data Structures & Algorithms (DSA), specifically LIFO Stack implementations for passenger manifests and cargo hold management."));
        contentStack.add(Box.createVerticalStrut(15));
        contentStack.add(createAboutRow("Academic Department", "Department of Software Engineering"));
        contentStack.add(Box.createVerticalStrut(10));
        contentStack.add(createAboutRow("Institution", "MUET SZAB Campus Khairpur Mir's"));
        contentStack.add(Box.createVerticalStrut(25));

        JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        badgeRow.setOpaque(false);
        badgeRow.add(createTechBadge("Java Swing GUI"));
        badgeRow.add(createTechBadge("Object-Oriented Design"));
        badgeRow.add(createTechBadge("Custom 3D Graphics"));
        badgeRow.add(createTechBadge("LIFO Stack Architecture"));
        contentStack.add(badgeRow);

        centerCard.add(contentStack, BorderLayout.CENTER);
        panelAbout.add(centerCard, BorderLayout.CENTER);

        return panelAbout;
    }

    private JPanel createAboutRow(String title, String desc) {
        JPanel row = new JPanel(new GridLayout(2, 1, 0, 2));
        row.setOpaque(false);
        JLabel lblT = new JLabel("• " + title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblT.setForeground(Color.WHITE);
        
        JLabel lblD = new JLabel("   " + desc);
        lblD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblD.setForeground(new Color(180, 195, 220));
        
        row.add(lblT);
        row.add(lblD);
        return row;
    }

    private JLabel createTechBadge(String text) {
        JLabel badge = new JLabel(" " + text + " ");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.BLACK);
        badge.setBackground(new Color(234, 202, 98));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return badge;
    }

    private JPanel buildReportsPanel(Color panelDark, Color borderDark, Color accentGold) {
        JPanel panelReport = new JPanel(new BorderLayout(15, 15));
        panelReport.setOpaque(false);

        JPanel topHeader = new JPanel(new GridLayout(2, 1, 0, 2));
        topHeader.setOpaque(false);
        JLabel lblHead = new JLabel("📊 System Reports & Analytics");
        lblHead.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHead.setForeground(accentGold);
        JLabel lblSubhead = new JLabel("Comprehensive airline operations metrics, data structure evaluations, and audit logs.");
        lblSubhead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubhead.setForeground(new Color(180, 195, 220));
        topHeader.add(lblHead);
        topHeader.add(lblSubhead);
        panelReport.add(topHeader, BorderLayout.NORTH);

        JPanel centerGrid = new JPanel(new GridLayout(2, 2, 15, 15));
        centerGrid.setOpaque(false);

        JPanel cardDSA = createShinyRoundedCard("Data Structure Performance (LIFO Stack)", panelDark, borderDark);
        cardDSA.setLayout(new BorderLayout());
        JPanel dsaContent = new JPanel(new GridLayout(4, 1, 0, 4));
        dsaContent.setOpaque(false);
        dsaContent.setBorder(new EmptyBorder(8, 10, 8, 10));
        dsaContent.add(createReportMetricRow("Assigned Structure", "Dynamic Array Stack (LIFO)"));
        dsaContent.add(createReportMetricRow("Max Capacity Allocation", "50 Units"));
        dsaContent.add(createReportMetricRow("Time Complexity (Push/Pop)", "O(1) Optimal"));
        dsaContent.add(createReportMetricRow("Memory Integrity Status", "No Leaks Detected (Optimal)"));
        cardDSA.add(dsaContent, BorderLayout.CENTER);

        JPanel cardFleet = createShinyRoundedCard("Fleet & Flight Operations Summary", panelDark, borderDark);
        cardFleet.setLayout(new BorderLayout());
        JPanel fleetContent = new JPanel(new GridLayout(4, 1, 0, 4));
        fleetContent.setOpaque(false);
        fleetContent.setBorder(new EmptyBorder(8, 10, 8, 10));
        fleetContent.add(createReportMetricRow("Total Active Flights", "24 Scheduled Routes"));
        fleetContent.add(createReportMetricRow("Passenger Manifest Total", "156 Checked-in Passengers"));
        fleetContent.add(createReportMetricRow("Cargo Hold Utilization", "690.5 kg Total Payload"));
        fleetContent.add(createReportMetricRow("On-Time Performance Index", "98.4% Efficiency"));
        cardFleet.add(fleetContent, BorderLayout.CENTER);

        JPanel cardSecurity = createShinyRoundedCard("System Security & Access Logs", panelDark, borderDark);
        cardSecurity.setLayout(new BorderLayout());
        JPanel secContent = new JPanel(new GridLayout(4, 1, 0, 4));
        secContent.setOpaque(false);
        secContent.setBorder(new EmptyBorder(8, 10, 8, 10));
        secContent.add(createReportMetricRow("Authentication Protocol", "Secured Admin Session"));
        secContent.add(createReportMetricRow("Active User Role", "System Administrator (Level 1)"));
        secContent.add(createReportMetricRow("Database Connection", "Local Heap Memory Buffer"));
        secContent.add(createReportMetricRow("Last Security Audit", "Jul 17, 2025 (Passed)"));
        cardSecurity.add(secContent, BorderLayout.CENTER);

        JPanel cardActions = createShinyRoundedCard("Report Action & Export Center", panelDark, borderDark);
        cardActions.setLayout(new BorderLayout());
        
        JPanel actionButtonsContainer = new JPanel();
        actionButtonsContainer.setLayout(new BoxLayout(actionButtonsContainer, BoxLayout.Y_AXIS));
        actionButtonsContainer.setOpaque(false);
        actionButtonsContainer.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton btnExportPdf = createCompactShinyPillButton("📥 Export Daily Audit Report (PDF)", new Color(234, 202, 98), new Color(181, 142, 28));
        JButton btnRunDiagnostics = createCompactShinyPillButton("🔄 Run Memory & Stack Diagnostics", new Color(45, 108, 179), new Color(24, 63, 110));
        JButton btnClearLogs = createCompactShinyPillButton("🧹 Purge Temporary Session Logs", new Color(207, 72, 72), new Color(138, 41, 41));

        btnExportPdf.addActionListener(e -> JOptionPane.showMessageDialog(this, "Daily audit report successfully exported to system root folder!", "Export Success", JOptionPane.INFORMATION_MESSAGE));
        btnRunDiagnostics.addActionListener(e -> JOptionPane.showMessageDialog(this, "Stack memory diagnostics completed. All 50 blocks operating at peak performance.", "Diagnostics Passed", JOptionPane.INFORMATION_MESSAGE));
        btnClearLogs.addActionListener(e -> JOptionPane.showMessageDialog(this, "Temporary logs purged successfully.", "Purge Complete", JOptionPane.INFORMATION_MESSAGE));

        actionButtonsContainer.add(btnExportPdf);
        actionButtonsContainer.add(Box.createVerticalStrut(8));
        actionButtonsContainer.add(btnRunDiagnostics);
        actionButtonsContainer.add(Box.createVerticalStrut(8));
        actionButtonsContainer.add(btnClearLogs);

        cardActions.add(actionButtonsContainer, BorderLayout.CENTER);

        centerGrid.add(cardDSA);
        centerGrid.add(cardFleet);
        centerGrid.add(cardSecurity);
        centerGrid.add(cardActions);

        panelReport.add(centerGrid, BorderLayout.CENTER);
        return panelReport;
    }

    private JPanel createReportMetricRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel lbl = new JLabel("• " + label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(180, 195, 220));
        row.add(lbl, BorderLayout.WEST);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 12));
        val.setForeground(Color.WHITE);
        row.add(val, BorderLayout.EAST);
        return row;
    }

    private JPanel buildInventoryPanel(Color panelDark, Color borderDark, Color accentGold) {
        JPanel panelInv = new JPanel(new BorderLayout(15, 15));
        panelInv.setOpaque(false);

        JPanel topHeader = new JPanel(new GridLayout(2, 1, 0, 2));
        topHeader.setOpaque(false);
        JLabel lblHead = new JLabel("Aircraft Cargo Inventory");
        lblHead.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHead.setForeground(accentGold);
        JLabel lblSubhead = new JLabel("Cargo Hold A - Container units are loaded and unloaded last-in, first-out.");
        lblSubhead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubhead.setForeground(new Color(180, 195, 220));
        topHeader.add(lblHead);
        topHeader.add(lblSubhead);
        panelInv.add(topHeader, BorderLayout.NORTH);

        JPanel centerSplit = new JPanel(new BorderLayout(15, 0));
        centerSplit.setOpaque(false);

        JPanel leftContainer = new JPanel(new BorderLayout(0, 12));
        leftContainer.setOpaque(false);

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 10, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 80));

        lblUnitsLoaded = new JLabel("5 / 50");
        lblTotalWeight = new JLabel("690.5 kg");
        lblLastLoadedTop = new JLabel("CG-101");
        lblFreeSlots = new JLabel("45");

        statsRow.add(createMiniStatCard("UNITS LOADED", lblUnitsLoaded, panelDark, borderDark, accentGold));
        statsRow.add(createMiniStatCard("TOTAL WEIGHT", lblTotalWeight, panelDark, borderDark, accentGold));
        statsRow.add(createMiniStatCard("LAST LOADED", lblLastLoadedTop, panelDark, borderDark, accentGold));
        statsRow.add(createMiniStatCard("FREE SLOTS", lblFreeSlots, panelDark, borderDark, accentGold));

        leftContainer.add(statsRow, BorderLayout.NORTH);

        JPanel stackCard = createShinyRoundedCard("Cargo Hold A - Container Stack", panelDark, borderDark);
        stackCard.setLayout(new BorderLayout(0, 10));
        stackCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15), stackCard.getBorder()));

        JLabel lblStackCap = new JLabel("Stack capacity: 50");
        lblStackCap.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStackCap.setForeground(new Color(150, 170, 200));
        lblStackCap.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel stackCardTop = new JPanel(new BorderLayout());
        stackCardTop.setOpaque(false);
        JLabel lblStackTitle = new JLabel("Cargo Hold A - Container Stack");
        lblStackTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStackTitle.setForeground(Color.WHITE);
        stackCardTop.add(lblStackTitle, BorderLayout.WEST);
        stackCardTop.add(lblStackCap, BorderLayout.EAST);

        inventoryStackContainer = new JPanel();
        inventoryStackContainer.setLayout(new BoxLayout(inventoryStackContainer, BoxLayout.Y_AXIS));
        inventoryStackContainer.setOpaque(false);

        JScrollPane scrollStack = new JScrollPane(inventoryStackContainer);
        scrollStack.getViewport().setBackground(panelDark);
        scrollStack.setBorder(BorderFactory.createLineBorder(borderDark));

        JPanel stackWrapper = new JPanel(new BorderLayout(0, 8));
        stackWrapper.setOpaque(false);
        stackWrapper.add(stackCardTop, BorderLayout.NORTH);
        stackWrapper.add(scrollStack, BorderLayout.CENTER);

        stackCard.add(stackWrapper, BorderLayout.CENTER);
        leftContainer.add(stackCard, BorderLayout.CENTER);
        centerSplit.add(leftContainer, BorderLayout.CENTER);

        JPanel rightContainer = new JPanel(new GridLayout(2, 1, 0, 15));
        rightContainer.setPreferredSize(new Dimension(300, 0));
        rightContainer.setOpaque(false);

        JPanel previewCard = createShinyRoundedCard("LAST LOADED ITEM", panelDark, borderDark);
        previewCard.setLayout(new GridLayout(4, 1, 0, 5));
        previewCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15), previewCard.getBorder()));

        lblPreviewId = new JLabel("CG-101");
        lblPreviewId.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPreviewId.setForeground(accentGold);
        
        lblPreviewWeight = new JLabel("150.5 kg");
        lblPreviewWeight.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPreviewWeight.setForeground(Color.WHITE);

        JPanel idWeightRow = new JPanel(new BorderLayout());
        idWeightRow.setOpaque(false);
        idWeightRow.add(lblPreviewId, BorderLayout.WEST);
        idWeightRow.add(lblPreviewWeight, BorderLayout.EAST);
        previewCard.add(idWeightRow);

        lblPreviewDest = new JLabel("Dubai");
        lblPreviewPos = new JLabel("#5 of 50");
        
        previewCard.add(createPreviewRow("Destination", lblPreviewDest));
        previewCard.add(createPreviewRow("Position", lblPreviewPos));

        rightContainer.add(previewCard);

        JPanel formCard = createShinyRoundedCard("Load cargo", panelDark, borderDark);
        formCard.setLayout(new GridLayout(8, 1, 0, 4));
        formCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15), formCard.getBorder()));

        JTextField txtCargoId = createTextField(borderDark);
        txtCargoId.setText("CG-102");
        JTextField txtCargoDest = createTextField(borderDark);
        JTextField txtCargoWeight = createTextField(borderDark);

        formCard.add(createFieldLabel("Item ID"));
        formCard.add(txtCargoId);
        formCard.add(createFieldLabel("Destination"));
        formCard.add(txtCargoDest);
        formCard.add(createFieldLabel("Weight (kg)"));
        formCard.add(txtCargoWeight);

        JButton btnPushCargo = createCompactShinyPillButton("Load (push)", new Color(234, 202, 98), new Color(181, 142, 28));
        JButton btnPopCargo = createCompactShinyPillButton("Unload (pop)", new Color(28, 45, 77), new Color(15, 25, 45));

        formCard.add(Box.createVerticalStrut(2));
        formCard.add(btnPushCargo);
        formCard.add(btnPopCargo);

        btnPushCargo.addActionListener(e -> {
            try {
                String id = txtCargoId.getText().trim();
                String dest = txtCargoDest.getText().trim();
                double weight = Double.parseDouble(txtCargoWeight.getText().trim());

                if (id.isEmpty() || dest.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all cargo fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CargoItem newCargo = new CargoItem(id, dest, weight, "System Operator");
                if (cargoStack.push(newCargo)) {
                    updateInventoryUI();
                    txtCargoId.setText("CG-" + (100 + cargoStack.size() + 1));
                    txtCargoDest.setText("");
                    txtCargoWeight.setText("");
                    lblStatus.setText("● Cargo " + id + " pushed onto hold successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Cargo Hold Stack Overflow (Max 50)", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric weight.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPopCargo.addActionListener(e -> {
            CargoItem popped = cargoStack.pop();
            if (popped != null) {
                updateInventoryUI();
                lblStatus.setText("● Unloaded top cargo: ID " + popped.getCargoId());
            } else {
                JOptionPane.showMessageDialog(this, "Cargo Hold Stack Underflow (Empty)", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        rightContainer.add(formCard);
        centerSplit.add(rightContainer, BorderLayout.EAST);
        panelInv.add(centerSplit, BorderLayout.CENTER);

        return panelInv;
    }

    private void updateInventoryUI() {
        if (inventoryStackContainer == null) return;
        inventoryStackContainer.removeAll();

        List<CargoItem> items = cargoStack.getElements(); 
        int totalUnits = items.size();
        double totalW = 0;

        for (CargoItem item : items) {
            totalW += item.getWeight();
        }

        if (lblUnitsLoaded != null) lblUnitsLoaded.setText(totalUnits + " / 50");
        if (lblTotalWeight != null) lblTotalWeight.setText(String.format("%.1f kg", totalW));
        if (lblFreeSlots != null) lblFreeSlots.setText(String.valueOf(50 - totalUnits));

        if (!items.isEmpty()) {
            CargoItem top = items.get(0); 
            if (lblLastLoadedTop != null) lblLastLoadedTop.setText(top.getCargoId());
            if (lblPreviewId != null) lblPreviewId.setText(top.getCargoId());
            if (lblPreviewWeight != null) lblPreviewWeight.setText(top.getWeight() + " kg");
            if (lblPreviewDest != null) lblPreviewDest.setText(top.getDestination());
            if (lblPreviewPos != null) lblPreviewPos.setText("#" + totalUnits + " of 50");
        } else {
            if (lblLastLoadedTop != null) lblLastLoadedTop.setText("None");
            if (lblPreviewId != null) lblPreviewId.setText("---");
            if (lblPreviewWeight != null) lblPreviewWeight.setText("0.0 kg");
            if (lblPreviewDest != null) lblPreviewDest.setText("---");
            if (lblPreviewPos != null) lblPreviewPos.setText("#0 of 50");
        }

        int pos = totalUnits;
        for (CargoItem item : items) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBorder(new EmptyBorder(6, 10, 6, 10));

            JLabel lblPosId = new JLabel(String.format("#%02d    %s    %s", pos, item.getCargoId(), item.getDestination()));
            lblPosId.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblPosId.setForeground(Color.WHITE);
            row.add(lblPosId, BorderLayout.WEST);

            JPanel rightInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            rightInfo.setOpaque(false);

            JLabel lblW = new JLabel(item.getWeight() + " kg");
            lblW.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblW.setForeground(new Color(212, 175, 55));
            rightInfo.add(lblW);

            if (pos == totalUnits) {
                JLabel lblTopBadge = new JLabel(" TOP ");
                lblTopBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
                lblTopBadge.setForeground(Color.BLACK);
                lblTopBadge.setBackground(new Color(212, 175, 55));
                lblTopBadge.setOpaque(true);
                lblTopBadge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
                rightInfo.add(lblTopBadge);
            }

            row.add(rightInfo, BorderLayout.EAST);
            inventoryStackContainer.add(row);
            pos--;
        }

        inventoryStackContainer.revalidate();
        inventoryStackContainer.repaint();
    }

    private JPanel createMiniStatCard(String title, JLabel valueLabel, Color panelDark, Color borderDark, Color accentGold) {
        JPanel card = new JPanel(new GridLayout(2, 1)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(28, 45, 77), 0, getHeight(), panelDark);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 40), 0, getHeight() / 2.0f, new Color(255, 255, 255, 2));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 12, 12));

                g2.setColor(borderDark);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 12, 12));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblT.setForeground(new Color(150, 170, 200));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(accentGold);

        card.add(lblT);
        card.add(valueLabel);
        return card;
    }

    private JPanel createPreviewRow(String label, JLabel valLabel) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(150, 170, 200));
        row.add(lbl, BorderLayout.WEST);

        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        valLabel.setForeground(Color.WHITE);
        row.add(valLabel, BorderLayout.EAST);
        return row;
    }

    private JPanel buildFlightsPanel(Color panelDark, Color borderDark, Color accentGold) {
        JPanel panelFlights = new JPanel(new BorderLayout(15, 15));
        panelFlights.setOpaque(false);

        JPanel topHeader = new JPanel(new GridLayout(2, 1, 0, 2));
        topHeader.setOpaque(false);
        JLabel lblHead = new JLabel("✈️ Active Flights Schedule");
        lblHead.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHead.setForeground(accentGold);
        JLabel lblSubhead = new JLabel("Real-time flight monitoring, aircraft assignments, and departure statuses.");
        lblSubhead.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubhead.setForeground(new Color(180, 195, 220));
        topHeader.add(lblHead);
        topHeader.add(lblSubhead);
        panelFlights.add(topHeader, BorderLayout.NORTH);

        JPanel tableCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(28, 45, 77), 0, getHeight(), panelDark);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 50), 0, getHeight() / 2.0f, new Color(255, 255, 255, 5));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 16, 16));

                g2.setColor(borderDark);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }
        };
        tableCard.setOpaque(false);
        tableCard.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] columns = {"Flight No", "Route", "Departure", "Arrival", "Aircraft", "Status"};
        Object[][] flightData = {
            {"F6", "Karachi to Lahore", "08:00 AM", "10:30 AM", "Boeing 737-800", "On Time"},
            {"F12", "Islamabad to Dubai", "02:15 PM", "06:00 PM", "Airbus A320", "Boarding"},
            {"F22", "Lahore to Islamabad", "07:00 PM", "08:00 PM", "ATR 72-600", "Scheduled"},
            {"F30", "Peshawar to Karachi", "09:30 AM", "11:45 AM", "Boeing 737-400", "On Time"},
            {"F45", "Karachi to Jeddah", "11:00 PM", "03:30 AM", "Boeing 777-300ER", "Delayed"},
            {"F50", "Multan to Sharjah", "01:00 PM", "04:15 PM", "Airbus A321", "On Time"}
        };

        DefaultTableModel flightTableModel = new DefaultTableModel(flightData, columns);
        JTable flightTable = new JTable(flightTableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(20, 32, 56) : new Color(16, 26, 46));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };

        flightTable.setRowHeight(36);
        flightTable.setBackground(new Color(11, 19, 36));
        flightTable.setForeground(Color.WHITE);
        flightTable.setGridColor(borderDark);
        flightTable.setSelectionBackground(accentGold);
        flightTable.setSelectionForeground(Color.BLACK);
        
        flightTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        flightTable.getTableHeader().setBackground(new Color(28, 45, 77));
        flightTable.getTableHeader().setForeground(accentGold);
        flightTable.getTableHeader().setPreferredSize(new Dimension(0, 38));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < flightTable.getColumnCount(); i++) {
            if (i != 1) {
                flightTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.getViewport().setBackground(new Color(11, 19, 36));
        scrollPane.setBorder(BorderFactory.createLineBorder(borderDark));

        tableCard.add(scrollPane, BorderLayout.CENTER);
        panelFlights.add(tableCard, BorderLayout.CENTER);

        return panelFlights;
    }

    private JPanel buildDashboardPanel(Color panelDark, Color borderDark, Color accentGold) {
        JPanel dashboard = new JPanel(new BorderLayout(15, 15));
        dashboard.setOpaque(false);

        JPanel bannerCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(28, 45, 77), 0, getHeight(), panelDark);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 50), 0, getHeight() / 2.0f, new Color(255, 255, 255, 5));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 16, 16));

                g2.setColor(borderDark);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }
        };
        bannerCard.setOpaque(false);
        bannerCard.setBorder(new EmptyBorder(25, 30, 25, 30));
        bannerCard.setPreferredSize(new Dimension(0, 160));

        JPanel bannerText = new JPanel(new GridLayout(3, 1, 0, 5));
        bannerText.setOpaque(false);
        JLabel b1 = new JLabel("Welcome to");
        b1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        b1.setForeground(Color.WHITE);
        JLabel b2 = new JLabel("Airline Inventory System");
        b2.setFont(new Font("Segoe UI", Font.BOLD, 26));
        b2.setForeground(accentGold);
        JLabel b3 = new JLabel("Manage Flights, Passengers, Inventory, Reports, and System Info.");
        b3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b3.setForeground(new Color(180, 195, 220));
        bannerText.add(b1);
        bannerText.add(b2);
        bannerText.add(b3);
        bannerCard.add(bannerText, BorderLayout.WEST);

        dashboard.add(bannerCard, BorderLayout.NORTH);

        JPanel centerContainer = new JPanel(new BorderLayout(15, 15));
        centerContainer.setOpaque(false);

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 12, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 95));

        statsRow.add(createStatCard("Total Flights", "24", new Color(212, 175, 55), panelDark, borderDark));
        statsRow.add(createStatCard("Total Passengers", "156", new Color(74, 119, 185), panelDark, borderDark));
        statsRow.add(createStatCard("Total Inventory", "320", new Color(46, 160, 100), panelDark, borderDark));
        statsRow.add(createStatCard("Total Reports", "12", new Color(207, 72, 72), panelDark, borderDark));

        centerContainer.add(statsRow, BorderLayout.NORTH);

        JPanel bottomSplit = new JPanel(new GridLayout(1, 2, 15, 0));
        bottomSplit.setOpaque(false);

        JPanel qaPanel = createShinyRoundedCard("Quick Actions", panelDark, borderDark);
        qaPanel.setLayout(new BorderLayout());
        
        JPanel qaGrid = new JPanel(new GridLayout(2, 3, 8, 8));
        qaGrid.setOpaque(false);
        qaGrid.setBorder(new EmptyBorder(8, 12, 8, 12));

        JButton qa1 = createCompactShinyActionButton("Manage Flights");
        JButton qa2 = createCompactShinyActionButton("Manage Passengers");
        JButton qa3 = createCompactActionButtonCard("Manage Inventory");
        JButton qa4 = createCompactActionButtonCard("View Reports");
        JButton qa5 = createCompactActionButtonCard("About System");
        JButton qa6 = createCompactActionButtonCard("Exit System");

        qa1.addActionListener(e -> { cardLayout.show(panelDynamicContent, "FLIGHTS"); });
        qa2.addActionListener(e -> { cardLayout.show(panelDynamicContent, "PASSENGERS"); });
        qa3.addActionListener(e -> { cardLayout.show(panelDynamicContent, "INVENTORY"); updateInventoryUI(); });
        qa4.addActionListener(e -> { cardLayout.show(panelDynamicContent, "REPORT"); });
        qa5.addActionListener(e -> { cardLayout.show(panelDynamicContent, "ABOUT"); });
        qa6.addActionListener(e -> System.exit(0));

        qaGrid.add(qa1); qaGrid.add(qa2); qaGrid.add(qa3);
        qaGrid.add(qa4); qaGrid.add(qa5); qaGrid.add(qa6);

        qaPanel.add(qaGrid, BorderLayout.CENTER);
        qaPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12), qaPanel.getBorder()));

        bottomSplit.add(qaPanel);

        JPanel raPanel = createShinyRoundedCard("Recent Activity", panelDark, borderDark);
        raPanel.setLayout(new BoxLayout(raPanel, BoxLayout.Y_AXIS));
        raPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        raPanel.add(createActivityRow("New Flight Added: PK-702", "Jul 17, 2025  09:42 AM", accentGold));
        raPanel.add(Box.createVerticalStrut(10));
        raPanel.add(createActivityRow("Passenger Booked: Ayesha Khan", "Jul 17, 2025  09:35 AM", accentGold));
        raPanel.add(Box.createVerticalStrut(10));
        raPanel.add(createActivityRow("Inventory Updated: Seat 12A", "Jul 17, 2025  09:20 AM", accentGold));
        raPanel.add(Box.createVerticalStrut(10));
        raPanel.add(createActivityRow("Report Generated", "Jul 17, 2025  08:50 AM", accentGold));

        bottomSplit.add(raPanel);

        centerContainer.add(bottomSplit, BorderLayout.CENTER);
        dashboard.add(centerContainer, BorderLayout.CENTER);

        return dashboard;
    }

    private JPanel createStatCard(String title, String value, Color iconColor, Color panelDark, Color borderDark) {
        JPanel card = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(28, 45, 77), 0, getHeight(), panelDark);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 40), 0, getHeight() / 2.0f, new Color(255, 255, 255, 2));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 14, 14));

                g2.setColor(borderDark);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 14, 14));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        // SHINY GLOSSY ICON ELLIPSE / OVAL
        JPanel iconBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Base gradient
                GradientPaint gp = new GradientPaint(0, 0, iconColor.brighter(), 0, getHeight(), iconColor.darker());
                g2.setPaint(gp);
                g2.fillOval(0, 0, getWidth(), getHeight());

                // Glossy highlight shine on top half
                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 140), 0, getHeight() / 2.0f, new Color(255, 255, 255, 10));
                g2.setPaint(shine);
                g2.fillOval(2, 2, getWidth() - 4, getHeight() / 2);

                // Subtle border rim
                g2.setColor(new Color(255, 255, 255, 60));
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                
                g2.dispose();
            }
        };
        iconBox.setPreferredSize(new Dimension(50, 50));
        iconBox.setOpaque(false);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblT.setForeground(new Color(180, 195, 220));
        JLabel lblV = new JLabel(value);
        lblV.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblV.setForeground(Color.WHITE);
        textPanel.add(lblT);
        textPanel.add(lblV);

        card.add(iconBox, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createShinyRoundedCard(String title, Color panelDark, Color borderDark) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(28, 45, 77), 0, getHeight(), panelDark);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 40), 0, getHeight() / 2.0f, new Color(255, 255, 255, 2));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 16, 16));

                g2.setColor(borderDark);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        
        JLabel lblHeader = new JLabel(title);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBorder(new EmptyBorder(0, 0, 8, 0));
        card.add(lblHeader, BorderLayout.NORTH);

        return card;
    }

    private JButton createCompactShinyActionButton(String text) {
        JButton btn = new JButton("<html><center>" + text + "</center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(234, 202, 98), 0, getHeight(), new Color(181, 142, 28));
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 100), 0, getHeight() / 2.0f, new Color(255, 255, 255, 10));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 8, 8));

                g2.setColor(new Color(0, 0, 0, 60));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setMaximumSize(new Dimension(160, 36));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createCompactActionButtonCard(String text) {
        JButton btn = new JButton("<html><center>" + text + "</center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 48, 80), 0, getHeight(), new Color(15, 25, 45));
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 40), 0, getHeight() / 2.0f, new Color(255, 255, 255, 2));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 8, 8));

                g2.setColor(new Color(55, 80, 122));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setMaximumSize(new Dimension(160, 36));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // --- ELLIPTICAL / PILL SHAPED BUTTONS FOR PASSENGER PANEL ---
    private JButton createCompactShinyPillButton(String text, Color topColor, Color bottomColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Fully rounded capsule / elliptical pill shape (arc radius = height)
                GradientPaint gp = new GradientPaint(0, 0, topColor, 0, height, bottomColor);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, width, height, height, height));

                // Glossy shine highlight on top half
                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 120), 0, height / 2.0f, new Color(255, 255, 255, 10));
                g2.setPaint(shine);
                g2.fill(new RoundRectangle2D.Float(1, 1, width - 2, height / 2.0f, height, height));

                // Outer border rim
                g2.setColor(new Color(0, 0, 0, 80));
                g2.draw(new RoundRectangle2D.Float(0, 0, width - 1, height - 1, height, height));

                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setPreferredSize(new Dimension(160, 32));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createActivityRow(String action, String time, Color accentGold) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        JLabel lblAct = new JLabel(" •  " + action);
        lblAct.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAct.setForeground(Color.WHITE);
        row.add(lblAct, BorderLayout.WEST);

        JLabel lblTime = new JLabel(time);
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTime.setForeground(accentGold);
        row.add(lblTime, BorderLayout.EAST);
        return row;
    }

    private JPanel buildPassengersPanel(Color panelDark, Color borderDark, Color textLight, Color accentGold) {
        JPanel panelCenter = new JPanel(new BorderLayout(10, 10));
        panelCenter.setBackground(panelDark);
        panelCenter.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderDark, 1),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JPanel panelFormWrapper = new JPanel(new BorderLayout());
        panelFormWrapper.setOpaque(false);
        
        JLabel lblFormTitle = new JLabel("👥 Passenger Details");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFormTitle.setForeground(accentGold);
        panelFormWrapper.add(lblFormTitle, BorderLayout.NORTH);

        JPanel panelFormGrid = new JPanel(new GridLayout(3, 4, 10, 8));
        panelFormGrid.setOpaque(false);
        panelFormGrid.setBorder(new EmptyBorder(8, 0, 8, 0));

        panelFormGrid.add(createLabel("Passenger ID:", textLight));
        txtId = createTextField(borderDark);
        panelFormGrid.add(txtId);

        panelFormGrid.add(createLabel("Full Name:", textLight));
        txtName = createTextField(borderDark);
        panelFormGrid.add(txtName);

        panelFormGrid.add(createLabel("Address:", textLight));
        txtAddress = createTextField(borderDark);
        panelFormGrid.add(txtAddress);

        panelFormGrid.add(createLabel("Contact:", textLight));
        txtContact = createTextField(borderDark);
        panelFormGrid.add(txtContact);

        panelFormGrid.add(createLabel("Age:", textLight));
        txtAge = createTextField(borderDark);
        panelFormGrid.add(txtAge);

        panelFormGrid.add(createLabel("Gender:", textLight));
        cmbGender = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        cmbGender.setBackground(new Color(11, 19, 36));
        cmbGender.setForeground(Color.WHITE);
        panelFormGrid.add(cmbGender);

        panelFormWrapper.add(panelFormGrid, BorderLayout.CENTER);
        panelCenter.add(panelFormWrapper, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"P_ID", "P_Name", "P_Address", "P_Contact", "Age", "Gender"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setBackground(new Color(11, 19, 36));
        table.setForeground(Color.WHITE);
        table.setGridColor(borderDark);
        table.setSelectionBackground(accentGold);
        table.setSelectionForeground(Color.BLACK);
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(panelDark);
        table.getTableHeader().setForeground(accentGold);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(11, 19, 36));
        scrollPane.setBorder(BorderFactory.createLineBorder(borderDark));
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        JPanel panelRight = new JPanel();
        panelRight.setLayout(new GridLayout(6, 1, 0, 8));
        panelRight.setPreferredSize(new Dimension(170, 0));
        panelRight.setOpaque(false);
        panelRight.setBorder(new EmptyBorder(0, 10, 0, 0));

        JButton btnPush = createCompactShinyPillButton("+ Push", new Color(234, 202, 98), new Color(181, 142, 28));
        JButton btnPop = createCompactShinyPillButton("↓ Pop", new Color(45, 108, 179), new Color(24, 63, 110));
        JButton btnUpdate = createCompactShinyPillButton("✎ Update", new Color(46, 160, 100), new Color(26, 92, 57));
        JButton btnDelete = createCompactShinyPillButton("🗑 Delete", new Color(207, 72, 72), new Color(138, 41, 41));
        JButton btnClear = createCompactShinyPillButton("🧹 Clear All", new Color(156, 163, 175), new Color(100, 110, 125));
        JButton btnRefresh = createCompactShinyPillButton("↻ Refresh", new Color(74, 119, 185), new Color(40, 71, 117));

        panelRight.add(btnPush);
        panelRight.add(btnPop);
        panelRight.add(btnUpdate);
        panelRight.add(btnDelete);
        panelRight.add(btnClear);
        panelRight.add(btnRefresh);

        panelCenter.add(panelRight, BorderLayout.EAST);

        btnPush.addActionListener(e -> {
            try {
                String id = txtId.getText().trim();
                String name = txtName.getText().trim();
                String address = txtAddress.getText().trim();
                String contact = txtContact.getText().trim();
                String age = txtAge.getText().trim();
                String gender = cmbGender.getSelectedItem().toString();

                if (id.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty() || age.isEmpty() || gender.equals("Select Gender")) {
                    JOptionPane.showMessageDialog(this, "Please fill in all passenger fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PassengerItem newItem = new PassengerItem(id, name, address, contact, age, gender);
                if (passengerStack.push(newItem)) {
                    refreshTable();
                    clearFields();
                    lblStatus.setText("● Pushed Passenger " + id + " onto Stack.");
                } else {
                    JOptionPane.showMessageDialog(this, "Stack Overflow! Capacity reached.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPop.addActionListener(e -> {
            PassengerItem removed = passengerStack.pop();
            if (removed != null) {
                refreshTable();
                lblStatus.setText("● Popped top passenger: " + removed.getPassengerId());
            } else {
                JOptionPane.showMessageDialog(this, "Stack Underflow! No passengers left.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Passenger ID in the form to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (passengerStack.removeById(id)) {
                refreshTable();
                clearFields();
                lblStatus.setText("● Deleted Passenger ID: " + id);
            } else {
                JOptionPane.showMessageDialog(this, "Passenger ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String address = txtAddress.getText().trim();
            String contact = txtContact.getText().trim();
            String age = txtAge.getText().trim();
            String gender = cmbGender.getSelectedItem().toString();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Passenger ID to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (passengerStack.update(id, name, address, contact, age, gender)) {
                refreshTable();
                clearFields();
                lblStatus.setText("● Updated Passenger ID: " + id);
            } else {
                JOptionPane.showMessageDialog(this, "Passenger ID not found in stack.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> {
            while (!passengerStack.isEmpty()) {
                passengerStack.pop();
            }
            refreshTable();
            lblStatus.setText("● Cleared all stack entries.");
        });

        btnRefresh.addActionListener(e -> {
            refreshTable();
            lblStatus.setText("● View refreshed.");
        });

        return panelCenter;
    }

    private JButton createNavButton(String text, Color panelDark) {
        JButton btn = new JButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(panelDark);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(new Color(203, 213, 225));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createNavButtonActive(String text, Color borderDark, Color accentGold) {
        JButton btn = new JButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(borderDark);
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accentGold),
            BorderFactory.createEmptyBorder(6, 8, 6, 12)
        ));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void setActiveNav(JPanel navPanel, JButton activeBtn, Color panelDark, Color borderDark, Color accentGold) {
        for (Component comp : navPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton b = (JButton) comp;
                b.setBackground(panelDark);
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setForeground(new Color(203, 213, 225));
                b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            }
        }
        activeBtn.setBackground(borderDark);
        activeBtn.setOpaque(true);
        activeBtn.setContentAreaFilled(true);
        activeBtn.setForeground(Color.WHITE);
        activeBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accentGold),
            BorderFactory.createEmptyBorder(6, 8, 6, 12)
        ));
    }

    private JLabel createLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(color);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        return lbl;
    }

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(150, 170, 200));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return lbl;
    }

    private JTextField createTextField(Color borderDark) {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(11, 19, 36));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(borderDark));
        return tf;
    }

    private void refreshTable() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        List<PassengerItem> items = passengerStack.getElements();
        for (PassengerItem p : items) {
            tableModel.addRow(new Object[]{
                p.getPassengerId(),
                p.getFullName(),
                p.getAddress(),
                p.getContact(),
                p.getAge(),
                p.getGender()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtAge.setText("");
        cmbGender.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JDialog loginDialog = new JDialog((Frame) null, "System Login - Airline Inventory", true);
            loginDialog.setSize(380, 220);
            loginDialog.setLocationRelativeTo(null);
            loginDialog.setLayout(new GridLayout(4, 2, 10, 10));
            loginDialog.getContentPane().setBackground(new Color(20, 32, 56));

            JLabel lblUser = new JLabel("  Username:");
            lblUser.setForeground(Color.WHITE);
            lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
            JTextField txtUser = new JTextField();
            txtUser.setBackground(new Color(11, 19, 36));
            txtUser.setForeground(Color.WHITE);
            txtUser.setCaretColor(Color.WHITE);

            JLabel lblPass = new JLabel("  Password:");
            lblPass.setForeground(Color.WHITE);
            lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
            JPasswordField txtPass = new JPasswordField();
            txtPass.setBackground(new Color(11, 19, 36));
            txtPass.setForeground(Color.WHITE);
            txtPass.setCaretColor(Color.WHITE);

            JButton btnLogin = new JButton("Login") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, new Color(234, 202, 98), 0, getHeight(), new Color(181, 142, 28));
                    g2.setPaint(gp);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                    GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 100), 0, getHeight() / 2.0f, new Color(255, 255, 255, 10));
                    g2.setPaint(shine);
                    g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 8, 8));

                    g2.setColor(new Color(0, 0, 0, 60));
                    g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8));
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            btnLogin.setForeground(Color.BLACK);
            btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnLogin.setFocusPainted(false);
            btnLogin.setContentAreaFilled(false);
            btnLogin.setBorderPainted(false);
            btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JButton btnCancel = new JButton("Exit") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, new Color(207, 72, 72), 0, getHeight(), new Color(138, 41, 41));
                    g2.setPaint(gp);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                    GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 80), 0, getHeight() / 2.0f, new Color(255, 255, 255, 10));
                    g2.setPaint(shine);
                    g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() / 2.0f, 8, 8));

                    g2.setColor(new Color(0, 0, 0, 60));
                    g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8));
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnCancel.setFocusPainted(false);
            btnCancel.setContentAreaFilled(false);
            btnCancel.setBorderPainted(false);
            btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnLogin.addActionListener(e -> {
                String username = txtUser.getText().trim();
                String password = new String(txtPass.getPassword()).trim();

                if (username.equals("admin") && password.equals("admin123")) {
                    loginDialog.dispose();
                    new AirlineGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(loginDialog, "Invalid Username or Password!\nTry: admin / admin123", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancel.addActionListener(e -> System.exit(0));

            loginDialog.add(lblUser);
            loginDialog.add(txtUser);
            loginDialog.add(lblPass);
            loginDialog.add(txtPass);
            loginDialog.add(new JLabel(""));
            loginDialog.add(btnLogin);
            loginDialog.add(new JLabel(""));
            loginDialog.add(btnCancel);

            loginDialog.setVisible(true);
        });
    }
}