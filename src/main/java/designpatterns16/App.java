package designpatterns16;

import designpatterns16.design_patterns.composite.ProductCategory;
import designpatterns16.design_patterns.composite.ProductLeaf;
import designpatterns16.design_patterns.state.UsableState;
import designpatterns16.items.Device;
import designpatterns16.items.Item;
import designpatterns16.items.Medicine;
import designpatterns16.items.Serum;
import designpatterns16.items.Vaccine;
import designpatterns16.items.Consumable;
import designpatterns16.Stockmonitor.CriticalStockObserver;
import designpatterns16.Stockmonitor.ExpirationAlarmObserver;
import designpatterns16.Stockmonitor.Inventory;
import designpatterns16.Stockmonitor.StockItem;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Hospital Inventory Management System
 * 
 * This system uses THREE design patterns for managing hospital inventory:
 * 
 * 1. COMPOSITE PATTERN - Product Category Hierarchy
 *    - Organizes inventory items (medicines, serums, vaccines) into hierarchical categories
 *    - Example: All Products > Medicines > Antibiotics > Amoxicillin
 * 
 * 2. OBSERVER PATTERN - Stock Monitoring & Alerts
 *    - Monitors inventory stock levels and expiration dates
 *    - Automatically sends alerts when stock is low or items are expiring
 * 
 * 3. STATE PATTERN - Medical Device State Management
 *    - Manages the states of medical devices in inventory (MRI, X-Ray, etc.)
 *    - Tracks device usage, maintenance needs, and availability
 * 
 * ALL patterns work together in a unified INVENTORY MANAGEMENT SYSTEM
 */
public class App 
{
    private static Inventory inventory;
    private static List<Device> devices;
    private static ProductCategory rootCategory;
    private static CriticalStockObserver stockObserver;
    private static ExpirationAlarmObserver expirationObserver;
    private static Scanner scanner;
    private static SimpleDateFormat dateFormat;

    public static void main( String[] args )
    {
        // Terminal encoding'i UTF-8 olarak ayarla
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setProperty("file.encoding", "UTF-8");
            java.lang.reflect.Field charsetField = java.nio.charset.Charset.class.getDeclaredField("defaultCharset");
            charsetField.setAccessible(true);
            charsetField.set(null, java.nio.charset.Charset.forName("UTF-8"));
        } catch (Exception e) {
            // Encoding ayarı başarısız olursa devam et
        }
        
        scanner = new Scanner(System.in, "UTF-8");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Sistem başlatma
        initializeSystem();
        
        clearScreen();
        printHeader("HOSPITAL INVENTORY SYSTEM");
        System.out.println();
        
        // Ana menü döngüsü
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput("Your choice: ");
            System.out.println();
            
            switch (choice) {
                case 1:
                    manageInventory();
                    break;
                case 2:
                    runDemo();
                    break;
                case 0:
                    running = false;
                    printSeparator();
                    System.out.println("Exiting system... Goodbye!");
                    printSeparator();
                    break;
                default:
                    printError("Invalid choice! Please enter a number between 0-2.");
                    pause();
            }
        }
        
        scanner.close();
    }

    private static void initializeSystem() {
        inventory = new Inventory();
        devices = new ArrayList<>();
        rootCategory = new ProductCategory("All Products");
        inventory.setRootCategory(rootCategory);
        
        // Observer'ları oluştur
        stockObserver = new CriticalStockObserver(10);
        expirationObserver = new ExpirationAlarmObserver(30);
        
        // Örnek veriler ekle
        addSampleData();
    }

    private static void addSampleData() {
        // Sample products
        Medicine paracetamol = new Medicine("Paracetamol", "Painkiller", 5.50, "Tablet");
        Medicine ibuprofen = new Medicine("Ibuprofen", "Painkiller", 8.75, "Capsule");
        Serum saline = new Serum("Saline Solution", "IV Fluid", 15.00, 500.0);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 60);
        Date expiration = cal.getTime();
        
        StockItem stock1 = new StockItem(paracetamol, 20, expiration);
        StockItem stock2 = new StockItem(ibuprofen, 15, expiration);
        StockItem stock3 = new StockItem(saline, 10, expiration);
        
        stock1.addObserver(stockObserver);
        stock1.addObserver(expirationObserver);
        stock2.addObserver(stockObserver);
        stock2.addObserver(expirationObserver);
        stock3.addObserver(stockObserver);
        stock3.addObserver(expirationObserver);
        
        inventory.addStock(stock1);
        inventory.addStock(stock2);
        inventory.addStock(stock3);
        
        // Sample device
        Device mri = new Device("MRI Machine");
        devices.add(mri);
    }

    // Yardımcı formatlama metodları
    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    private static void printHeader(String title) {
        printSeparator();
        System.out.println("  " + title);
        printSeparator();
    }
    
    private static void printSeparator() {
        System.out.println("========================================");
    }
    
    private static void printSubSeparator() {
        System.out.println("----------------------------------------");
    }
    
    private static void printError(String message) {
        System.out.println(">>> ERROR: " + message);
        System.out.println();
    }
    
    private static void printSuccess(String message) {
        System.out.println(">>> SUCCESS: " + message);
        System.out.println();
    }
    
    private static void printInfo(String message) {
        System.out.println(">>> " + message);
        System.out.println();
    }
    
    private static void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        System.out.println();
    }

    private static void showMainMenu() {
        printHeader("MAIN MENU");
        System.out.println("  1. Manage Inventory");
        System.out.println("  2. Demo (Show Design Patterns)");
        System.out.println("  0. Exit");
        printSubSeparator();
    }

    private static void manageInventory() {
        boolean back = false;
        while (!back) {
            printHeader("INVENTORY MANAGEMENT");
            System.out.println("  STOCK OPERATIONS:");
            System.out.println("    1. View All Stock");
            System.out.println("    2. Add New Stock");
            System.out.println("    3. Reduce Stock");
            System.out.println("    4. Search Product");
            System.out.println();
            System.out.println("  CATEGORY OPERATIONS:");
            System.out.println("    5. Show Categories");
            System.out.println("    6. Search by Category");
            System.out.println();
            System.out.println("  DEVICE OPERATIONS:");
            System.out.println("    7. View Devices");
            System.out.println("    8. Add Device");
            System.out.println("    9. Use Device");
            System.out.println("    10. Stop Using Device");
            System.out.println("    11. Send to Maintenance");
            System.out.println("    12. Finish Maintenance");
            System.out.println();
            System.out.println("  REPORTS:");
            System.out.println("    13. Show All Reports");
            System.out.println();
            System.out.println("  0. Back");
            printSubSeparator();
            
            int choice = getIntInput("Your choice: ");
            System.out.println();
            
            switch (choice) {
                case 1:
                    listStock();
                    pause();
                    break;
                case 2:
                    addStock();
                    pause();
                    break;
                case 3:
                    reduceStock();
                    pause();
                    break;
                case 4:
                    searchByProduct();
                    pause();
                    break;
                case 5:
                    printHeader("CATEGORY STRUCTURE (Composite Pattern)");
                    inventory.displayCategoryStructure();
                    System.out.println();
                    pause();
                    break;
                case 6:
                    searchByCategory();
                    pause();
                    break;
                case 7:
                    listDevices();
                    pause();
                    break;
                case 8:
                    addDevice();
                    pause();
                    break;
                case 9:
                    beginDeviceUse();
                    pause();
                    break;
                case 10:
                    endDeviceUse();
                    pause();
                    break;
                case 11:
                    sendDeviceForMaintenance();
                    pause();
                    break;
                case 12:
                    completeDeviceMaintenance();
                    pause();
                    break;
                case 13:
                    viewReports();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    printError("Invalid choice! Please enter a number between 0-13.");
                    pause();
            }
        }
    }

    private static void manageDevices() {
        boolean back = false;
        while (!back) {
            printHeader("DEVICE MANAGEMENT");
            System.out.println("  1. Device List");
            System.out.println("  2. Add New Device");
            System.out.println("  3. Start Device Use");
            System.out.println("  4. End Device Use");
            System.out.println("  5. Send Device for Maintenance");
            System.out.println("  6. Complete Device Maintenance");
            System.out.println("  0. Main Menu");
            printSubSeparator();
            
            int choice = getIntInput("Your choice: ");
            System.out.println();
            
            switch (choice) {
                case 1:
                    listDevices();
                    pause();
                    break;
                case 2:
                    addDevice();
                    pause();
                    break;
                case 3:
                    beginDeviceUse();
                    pause();
                    break;
                case 4:
                    endDeviceUse();
                    pause();
                    break;
                case 5:
                    sendDeviceForMaintenance();
                    pause();
                    break;
                case 6:
                    completeDeviceMaintenance();
                    pause();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    printError("Invalid choice! Please enter a number between 0-6.");
                    pause();
            }
        }
    }

    private static void manageCategories() {
        boolean back = false;
        while (!back) {
            printHeader("CATEGORY MANAGEMENT");
            System.out.println("  1. Show Category Structure");
            System.out.println("  2. Add New Category");
            System.out.println("  3. Add Product to Category");
            System.out.println("  0. Main Menu");
            printSubSeparator();
            
            int choice = getIntInput("Your choice: ");
            System.out.println();
            
            switch (choice) {
                case 1:
                    printHeader("CATEGORY STRUCTURE");
                    inventory.displayCategoryStructure();
                    System.out.println();
                    pause();
                    break;
                case 2:
                    addCategory();
                    pause();
                    break;
                case 3:
                    addProductToCategory();
                    pause();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    printError("Invalid choice! Please enter a number between 0-3.");
                    pause();
            }
        }
    }

    private static void viewReports() {
        printHeader("REPORTS");
        System.out.println("  1. All Stock Report");
        System.out.println("  2. Critical Stock Alerts");
        System.out.println("  3. Expiration Date Alerts");
        System.out.println("  0. Main Menu");
        printSubSeparator();
        
        int choice = getIntInput("Your choice: ");
        System.out.println();
        
        switch (choice) {
            case 1:
                printStockReport();
                pause();
                break;
            case 2:
                printCriticalStockAlerts();
                pause();
                break;
            case 3:
                printExpirationAlerts();
                pause();
                break;
            case 0:
                break;
            default:
                printError("Invalid choice! Please enter a number between 0-3.");
                pause();
        }
    }

    // Envanter metodları
    private static void listStock() {
        List<StockItem> items = inventory.getAllStockItems();
        if (items.isEmpty()) {
            printHeader("STOCK LIST");
            printInfo("No products in stock.");
            return;
        }
        
        printHeader("STOCK LIST");
        printSubSeparator();
        System.out.printf("%-3s | %-25s | %-10s | %-15s | %-15s%n", 
                         "No", "Product Name", "Quantity", "Expiration", "Total Value");
        printSubSeparator();
        
        for (int i = 0; i < items.size(); i++) {
            StockItem item = items.get(i);
            String expiration = item.getExpirationDate() != null ? 
                               dateFormat.format(item.getExpirationDate()) : "None";
            System.out.printf("%-3d | %-25s | %-10d | %-15s | %-15.2f TL%n",
                            (i + 1), item.getName(), item.getStock(), expiration, item.getTotalValue());
        }
        printSubSeparator();
    }

    private static void addStock() {
        printHeader("ADD STOCK");
        System.out.println("Select Product Type:");
        System.out.println("  1. Medicine");
        System.out.println("  2. Serum");
        System.out.println("  3. Vaccine");
        printSubSeparator();
        
        int type = getIntInput("Choice (1-3): ");
        
        if (type < 1 || type > 3) {
            printError("Invalid type selection!");
            return;
        }
        
        String name = getStringInput("Product Name: ");
        // Automatically set product type based on selection
        String productType = "";
        if (type == 1) {
            productType = "Medicine";
        } else if (type == 2) {
            productType = "Serum";
        } else if (type == 3) {
            productType = "Vaccine";
        }
        
        double price = getDoubleInput("Unit Price (TL): ");
        
        Consumable product = null;
        if (type == 1) {
            String drugForm = getStringInput("Drug Form (Tablet/Capsule/Syrup): ");
            product = new Medicine(name, productType, price, drugForm);
        } else if (type == 2) {
            double capacity = getDoubleInput("Capacity (ml): ");
            product = new Serum(name, productType, price, capacity);
        } else if (type == 3) {
            double dosage = getDoubleInput("Dosage (ml): ");
            product = new Vaccine(name, productType, price, dosage);
        }
        
        int quantity = getIntInput("Quantity: ");
        System.out.print("Expiration Date (dd/MM/yyyy) or leave empty: ");
        String dateStr = scanner.nextLine().trim();
        Date expiration = null;
        if (!dateStr.isEmpty()) {
            try {
                expiration = dateFormat.parse(dateStr);
            } catch (Exception e) {
                printError("Invalid date format! Please enter in dd/MM/yyyy format.");
                return;
            }
        }
        
        StockItem stockItem = new StockItem(product, quantity, expiration);
        stockItem.addObserver(stockObserver);
        stockItem.addObserver(expirationObserver);
        inventory.addStock(stockItem);
        
        printSuccess("Stock added successfully!");
    }

    private static void reduceStock() {
        listStock();
        List<StockItem> items = inventory.getAllStockItems();
        if (items.isEmpty()) {
            return;
        }
        
        int index = getIntInput("Product number to reduce: ") - 1;
        if (index < 0 || index >= items.size()) {
            printError("Invalid selection!");
            return;
        }
        
        StockItem item = items.get(index);
        int amount = getIntInput("Amount to reduce: ");
        
        try {
            item.reduceStock(amount);
            printSuccess("Stock reduced successfully!");
        } catch (IllegalArgumentException e) {
            printError("Error: " + e.getMessage());
        }
    }

    private static void searchByProduct() {
        String name = getStringInput("Product name to search: ");
        List<StockItem> results = inventory.getStockByProduct(name);
        
        if (results.isEmpty()) {
            printInfo("Product not found.");
        } else {
            printHeader("SEARCH RESULTS");
            for (StockItem item : results) {
                System.out.println(item.toString());
            }
            System.out.println();
        }
    }

    private static void searchByCategory() {
        String categoryName = getStringInput("Category name to search: ");
        List<StockItem> results = inventory.getStockByCategory(categoryName);
        
        if (results.isEmpty()) {
            printInfo("Category not found or no stock available.");
        } else {
            printHeader("SEARCH RESULTS");
            for (StockItem item : results) {
                System.out.println(item.toString());
            }
            System.out.println();
        }
    }

    // Cihaz metodları
    private static void listDevices() {
        if (devices.isEmpty()) {
            printHeader("DEVICE LIST");
            printInfo("No devices found.");
            return;
        }
        
        printHeader("DEVICE LIST");
        printSubSeparator();
        System.out.printf("%-3s | %-25s | %-25s | %-15s%n", 
                         "No", "Device Name", "State", "Use Count");
        printSubSeparator();
        
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            String stateName = device.getState().getClass().getSimpleName();
            System.out.printf("%-3d | %-25s | %-25s | %-15d%n",
                            (i + 1), device.getName(), stateName, device.getUseCount());
        }
        printSubSeparator();
    }

    private static void addDevice() {
        String name = getStringInput("Device Name: ");
        Device device = new Device(name);
        devices.add(device);
        printSuccess("Device added successfully!");
    }

    private static void beginDeviceUse() {
        listDevices();
        if (devices.isEmpty()) return;
        
        int index = getIntInput("Device number: ") - 1;
        if (index >= 0 && index < devices.size()) {
            devices.get(index).beginUse();
            System.out.println();
        } else {
            printError("Invalid selection!");
        }
    }

    private static void endDeviceUse() {
        listDevices();
        if (devices.isEmpty()) return;
        
        int index = getIntInput("Device number: ") - 1;
        if (index >= 0 && index < devices.size()) {
            devices.get(index).endUse();
            System.out.println();
        } else {
            printError("Invalid selection!");
        }
    }

    private static void sendDeviceForMaintenance() {
        listDevices();
        if (devices.isEmpty()) return;
        
        int index = getIntInput("Device number: ") - 1;
        if (index >= 0 && index < devices.size()) {
            devices.get(index).sendForMaintenance();
            System.out.println();
        } else {
            printError("Invalid selection!");
        }
    }

    private static void completeDeviceMaintenance() {
        listDevices();
        if (devices.isEmpty()) return;
        
        int index = getIntInput("Device number: ") - 1;
        if (index >= 0 && index < devices.size()) {
            devices.get(index).completeMaintenance();
            System.out.println();
        } else {
            printError("Invalid selection!");
        }
    }

    // Kategori metodları
    private static void addCategory() {
        String name = getStringInput("Category Name: ");
        ProductCategory category = new ProductCategory(name);
        
        System.out.println("Enter 0 to add to root category, or enter parent category name:");
        String parentName = scanner.nextLine().trim();
        
        if (parentName.equals("0") || parentName.isEmpty()) {
            rootCategory.add(category);
        } else {
            // Basit implementasyon - gerçek uygulamada daha gelişmiş olabilir
            rootCategory.add(category);
        }
        
        printSuccess("Category added successfully!");
    }

    private static void addProductToCategory() {
        listStock();
        List<StockItem> items = inventory.getAllStockItems();
        if (items.isEmpty()) return;
        
        int index = getIntInput("Product number: ") - 1;
        if (index < 0 || index >= items.size()) {
            printError("Invalid selection!");
            return;
        }
        
        String categoryName = getStringInput("Category name: ");
        // Basit implementasyon
        printSuccess("Product added to category successfully!");
    }

    // Rapor metodları
    private static void printStockReport() {
        printHeader("ALL STOCK REPORT");
        listStock();
        double totalValue = inventory.getAllStockItems().stream()
            .mapToDouble(StockItem::getTotalValue)
            .sum();
        printSubSeparator();
        System.out.printf("TOTAL STOCK VALUE: %,.2f TL%n", totalValue);
        printSubSeparator();
    }

    private static void printCriticalStockAlerts() {
        printHeader("CRITICAL STOCK ALERTS");
        List<StockItem> items = inventory.getAllStockItems();
        boolean found = false;
        
        printSubSeparator();
        for (StockItem item : items) {
            if (item.getStock() <= 10) {
                System.out.printf("  [ALERT] %-25s - Remaining: %-5d units (Critical level: 10)%n", 
                                item.getName(), item.getStock());
                found = true;
            }
        }
        printSubSeparator();
        
        if (!found) {
            printInfo("No critical stock alerts. All products have sufficient stock.");
        }
    }

    private static void printExpirationAlerts() {
        printHeader("EXPIRATION DATE ALERTS");
        List<StockItem> items = inventory.getAllStockItems();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 30);
        Date threshold = cal.getTime();
        boolean found = false;
        
        printSubSeparator();
        for (StockItem item : items) {
            if (item.getExpirationDate() != null && item.getExpirationDate().before(threshold)) {
                long daysLeft = (item.getExpirationDate().getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
                System.out.printf("  [ALERT] %-25s - Expiration: %-12s (Days left: %d)%n", 
                                item.getName(), dateFormat.format(item.getExpirationDate()), daysLeft);
                found = true;
            }
        }
        printSubSeparator();
        
        if (!found) {
            printInfo("No upcoming expiration date alerts.");
        }
    }

    // Yardımcı metodlar
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static void runDemo() {
        System.out.println("========================================");
        System.out.println("RUNNING DEMO SCENARIO");
        System.out.println("========================================\n");

        // 1. COMPOSITE PATTERN DEMO - Category Structure
        demonstrateCompositePattern();

        // 2. STATE PATTERN DEMO - Device State Management
        demonstrateStatePattern();

        // 3. OBSERVER PATTERN DEMO - Stock Alerts
        demonstrateObserverPattern();

        System.out.println("\n========================================");
        System.out.println("DEMO COMPLETED");
        System.out.println("========================================\n");
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void demonstrateCompositePattern() {
        System.out.println("--- COMPOSITE PATTERN DEMO ---");
        System.out.println("Creating product categories...\n");

        // Root category
        ProductCategory allProducts = new ProductCategory("All Products");

        // Medicines category
        ProductCategory medicines = new ProductCategory("Medicines");
        ProductCategory antibiotics = new ProductCategory("Antibiotics");
        ProductCategory painkillers = new ProductCategory("Painkillers");

        // Serums category
        ProductCategory serums = new ProductCategory("Serums");

        // Vaccines category
        ProductCategory vaccines = new ProductCategory("Vaccines");

        // Create products
        Medicine paracetamol = new Medicine("Paracetamol", "Painkiller", 5.50, "Tablet");
        Medicine ibuprofen = new Medicine("Ibuprofen", "Painkiller", 8.75, "Capsule");
        Medicine amoxicillin = new Medicine("Amoxicillin", "Antibiotic", 12.00, "Tablet");
        Serum saline = new Serum("Saline Solution", "IV Fluid", 15.00, 500.0);
        Vaccine covidVaccine = new Vaccine("COVID-19 Vaccine", "Vaccine", 25.00, 0.5);

        // Build category structure
        allProducts.add(medicines);
        allProducts.add(serums);
        allProducts.add(vaccines);

        medicines.add(antibiotics);
        medicines.add(painkillers);

        antibiotics.add(new ProductLeaf(amoxicillin));
        painkillers.add(new ProductLeaf(paracetamol));
        painkillers.add(new ProductLeaf(ibuprofen));
        serums.add(new ProductLeaf(saline));
        vaccines.add(new ProductLeaf(covidVaccine));

        // Display category structure
        System.out.println("Category Structure:");
        allProducts.display();

        System.out.println("\nAll products in 'Medicines' category:");
        List<designpatterns16.items.Item> medicineItems = medicines.getAllItems();
        for (designpatterns16.items.Item item : medicineItems) {
            System.out.println("  - " + item.getName());
        }

        System.out.println();
    }

    private static void demonstrateStatePattern() {
        System.out.println("--- STATE PATTERN DEMO ---");
        System.out.println("Demonstrating device state management...\n");

        // Create device
        Device mriMachine = new Device("MRI Machine");
        System.out.println("Device: " + mriMachine.getName());
        System.out.println("Initial state: " + mriMachine.getState().getClass().getSimpleName() + "\n");

        // Start use
        System.out.println("1. Starting device use:");
        mriMachine.beginUse();
        System.out.println("   State: " + mriMachine.getState().getClass().getSimpleName() + "\n");

        // A few more uses (to reach maintenance threshold)
        for (int i = 0; i < 4; i++) {
            mriMachine.beginUse();
            mriMachine.endUse();
        }

        // End use (will require maintenance)
        System.out.println("2. Ending use (after 5th use):");
        mriMachine.endUse();
        System.out.println("   State: " + mriMachine.getState().getClass().getSimpleName() + "\n");

        // Send for maintenance
        System.out.println("3. Sending device for maintenance:");
        mriMachine.sendForMaintenance();
        System.out.println("   State: " + mriMachine.getState().getClass().getSimpleName() + "\n");

        // Complete maintenance
        System.out.println("4. Completing maintenance:");
        mriMachine.completeMaintenance();
        System.out.println("   State: " + mriMachine.getState().getClass().getSimpleName() + "\n");
    }

    private static void demonstrateObserverPattern() {
        System.out.println("--- OBSERVER PATTERN DEMO ---");
        System.out.println("Demonstrating stock alert system...\n");

        // Create inventory
        Inventory inventory = new Inventory();

        // Create observers
        CriticalStockObserver stockObserver = new CriticalStockObserver(10); // Alert below 10
        ExpirationAlarmObserver expirationObserver = new ExpirationAlarmObserver(30); // Alert within 30 days

        // Create products
        Medicine paracetamol = new Medicine("Paracetamol", "Painkiller", 5.50, "Tablet");
        Serum saline = new Serum("Saline Solution", "IV Fluid", 15.00, 500.0);

        // Create expiration dates
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 20); // 20 days later
        Date nearExpiration = cal.getTime();

        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 60); // 60 days later
        Date farExpiration = cal.getTime();

        // Create stock items
        StockItem paracetamolStock = new StockItem(paracetamol, 15, farExpiration);
        StockItem salineStock = new StockItem(saline, 5, nearExpiration);

        // Add observers
        paracetamolStock.addObserver(stockObserver);
        paracetamolStock.addObserver(expirationObserver);
        salineStock.addObserver(stockObserver);
        salineStock.addObserver(expirationObserver);

        // Add to inventory
        inventory.addStock(paracetamolStock);
        inventory.addStock(salineStock);

        System.out.println("Initial stock status:");
        System.out.println("  Paracetamol: " + paracetamolStock.getStock() + " units");
        System.out.println("  Saline Solution: " + salineStock.getStock() + " units\n");

        // Reduce stock (drop to critical level)
        System.out.println("1. Reducing Paracetamol stock by 10 units:");
        paracetamolStock.reduceStock(10);
        System.out.println("   Remaining stock: " + paracetamolStock.getStock() + " units\n");

        // Reduce stock (drop below critical level)
        System.out.println("2. Reducing Paracetamol stock by 5 more units:");
        paracetamolStock.reduceStock(5);
        System.out.println("   Remaining stock: " + paracetamolStock.getStock() + " units\n");

        // Alert for product with approaching expiration date
        System.out.println("3. Checking Saline Solution stock (expiration date approaching):");
        salineStock.reduceStock(1); // Trigger observers
        System.out.println();
    }
}
