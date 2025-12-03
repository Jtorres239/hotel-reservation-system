import java.util.*;

public class HotelApp {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HotelSystem system = new HotelSystem();
        system.seedData(); // create default admin, clerk, rooms

        while (true) {
            System.out.println("\n=== Hotel System ===");
            System.out.println("1. Login");
            System.out.println("2. Register as Guest");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    login(system);
                    break;
                case "2":
                    registerGuest(system);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void login(HotelSystem system) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = system.authenticate(username, password);
        if (user == null) {
            System.out.println("Invalid login.");
            return;
        }

        if (user instanceof Guest) {
            guestMenu(system, (Guest) user);
        } else if (user instanceof Clerk) {
            clerkMenu(system, (Clerk) user);
        } else if (user instanceof Admin) {
            adminMenu(system, (Admin) user);
        }
    }

    private static void registerGuest(HotelSystem system) {
        System.out.print("Choose username: ");
        String username = scanner.nextLine();
        System.out.print("Choose password: ");
        String password = scanner.nextLine();
        System.out.print("Full name: ");
        String name = scanner.nextLine();

        system.createGuest(username, password, name);
        System.out.println("Guest account created. You can now log in.");
    }

    private static void guestMenu(HotelSystem system, Guest guest) {
        while (true) {
            System.out.println("\n=== Guest Menu (" + guest.getUsername() + ") ===");
            System.out.println("1. View / edit profile");
            System.out.println("2. Search available rooms");
            System.out.println("3. Make a new reservation");
            System.out.println("4. View / change my reservations");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    editProfile(guest);
                    break;
                case "2":
                    system.listRooms();
                    break;
                case "3":
                    makeReservation(system, guest);
                    break;
                case "4":
                    editReservation(system, guest);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void editProfile(User user) {
        System.out.println("\nCurrent name: " + user.getName());
        System.out.print("Enter new name (leave blank to keep): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) user.setName(name);

        System.out.println("Current username: " + user.getUsername());
        System.out.print("Enter new password (leave blank to keep): ");
        String pw = scanner.nextLine();
        if (!pw.isEmpty()) user.setPassword(pw);

        System.out.println("Profile updated.");
    }

    private static void makeReservation(HotelSystem system, Guest guest) {
        system.listRooms();
        System.out.print("Enter room id to reserve: ");
        String roomIdStr = scanner.nextLine();

        int roomId;
        try {
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
            return;
        }

        Room room = system.findRoom(roomId);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.print("Enter dates (e.g. 2025-11-10 to 2025-11-12): ");
        String dates = scanner.nextLine();
        system.createReservation(guest, room, dates);
        System.out.println("Reservation created.");
    }

    private static void editReservation(HotelSystem system, Guest guest) {
        List<Reservation> myRes = system.getReservationsForGuest(guest);
        if (myRes.isEmpty()) {
            System.out.println("You have no reservations.");
            return;
        }

        System.out.println("\nYour reservations:");
        for (int i = 0; i < myRes.size(); i++) {
            System.out.println((i + 1) + ". " + myRes.get(i));
        }

        System.out.print("Select reservation to change (number): ");
        String idxStr = scanner.nextLine();
        int idx;
        try {
            idx = Integer.parseInt(idxStr) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid.");
            return;
        }

        if (idx < 0 || idx >= myRes.size()) {
            System.out.println("Invalid.");
            return;
        }

        Reservation r = myRes.get(idx);
        System.out.println("Current dates: " + r.getDates());
        System.out.print("Enter new dates: ");
        String newDates = scanner.nextLine();
        r.setDates(newDates);
        System.out.println("Reservation updated.");
    }

    private static void clerkMenu(HotelSystem system, Clerk clerk) {
        while (true) {
            System.out.println("\n=== Clerk Menu (" + clerk.getUsername() + ") ===");
            System.out.println("1. View / edit my profile");
            System.out.println("2. View all rooms");
            System.out.println("3. Add a room");
            System.out.println("4. Modify a room");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    editProfile(clerk);
                    break;
                case "2":
                    system.listRooms();
                    break;
                case "3":
                    addRoom(system);
                    break;
                case "4":
                    modifyRoom(system);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addRoom(HotelSystem system) {
        System.out.print("Room id (number): ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Type (e.g. Single, Double): ");
        String type = scanner.nextLine();
        System.out.print("Price per night: ");
        double price = Double.parseDouble(scanner.nextLine());

        Room r = new Room(id, type, price);
        system.addRoom(r);
        System.out.println("Room added.");
    }

    private static void modifyRoom(HotelSystem system) {
        system.listRooms();
        System.out.print("Enter room id to modify: ");
        int id = Integer.parseInt(scanner.nextLine());

        Room r = system.findRoom(id);
        if (r == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println("Current: " + r);
        System.out.print("New type (blank to keep): ");
        String type = scanner.nextLine();
        if (!type.isEmpty()) r.setType(type);

        System.out.print("New price (blank to keep): ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            double price = Double.parseDouble(priceStr);
            r.setPrice(price);
        }

        System.out.println("Room updated: " + r);
    }

    private static void adminMenu(HotelSystem system, Admin admin) {
        while (true) {
            System.out.println("\n=== Admin Menu (" + admin.getUsername() + ") ===");
            System.out.println("1. Create clerk account");
            System.out.println("2. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createClerk(system);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createClerk(HotelSystem system) {
        System.out.print("Clerk username: ");
        String username = scanner.nextLine();
        String defaultPassword = "password";
        System.out.print("Clerk name: ");
        String name = scanner.nextLine();

        system.createClerk(username, defaultPassword, name);
        System.out.println("Clerk created with default password: " + defaultPassword);
    }
}

// ===== Supporting classes =====

abstract class User {
    private String username;
    private String password;
    private String name;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}

class Guest extends User {
    public Guest(String username, String password, String name) {
        super(username, password, name);
    }
}

class Clerk extends User {
    public Clerk(String username, String password, String name) {
        super(username, password, name);
    }
}

class Admin extends User {
    public Admin(String username, String password, String name) {
        super(username, password, name);
    }
}

class Room {
    private int id;
    private String type;
    private double price;

    public Room(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public int getId() { return id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Room " + id + " (" + type + "), $" + price + " per night";
    }
}

class Reservation {
    private Guest guest;
    private Room room;
    private String dates;

    public Reservation(Guest guest, Room room, String dates) {
        this.guest = guest;
        this.room = room;
        this.dates = dates;
    }

    public Guest getGuest() { return guest; }

    public Room getRoom() { return room; }

    public String getDates() { return dates; }

    public void setDates(String dates) { this.dates = dates; }

    @Override
    public String toString() {
        return "Room " + room.getId() + " on " + dates;
    }
}

class HotelSystem {
    private List<Guest> guests = new ArrayList<>();
    private List<Clerk> clerks = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void seedData() {
        admins.add(new Admin("admin", "admin", "Admin User"));
        clerks.add(new Clerk("clerk1", "password", "Front Desk Clerk"));
        rooms.add(new Room(101, "Single", 80.0));
        rooms.add(new Room(102, "Double", 100.0));
        rooms.add(new Room(201, "Suite", 150.0));
    }

    public User authenticate(String username, String password) {
        for (Admin a : admins)
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) return a;

        for (Clerk c : clerks)
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) return c;

        for (Guest g : guests)
            if (g.getUsername().equals(username) && g.getPassword().equals(password)) return g;

        return null;
    }

    public Guest createGuest(String username, String password, String name) {
        Guest g = new Guest(username, password, name);
        guests.add(g);
        return g;
    }

    public Clerk createClerk(String username, String password, String name) {
        Clerk c = new Clerk(username, password, name);
        clerks.add(c);
        return c;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void listRooms() {
        System.out.println("\nAvailable rooms:");
        for (Room r : rooms) {
            System.out.println(r.getId() + ": " + r);
        }
    }

    public Room findRoom(int id) {
        for (Room r : rooms)
            if (r.getId() == id) return r;
        return null;
    }

    public void createReservation(Guest guest, Room room, String dates) {
        reservations.add(new Reservation(guest, room, dates));
    }

    public List<Reservation> getReservationsForGuest(Guest guest) {
        List<Reservation> res = new ArrayList<>();
        for (Reservation r : reservations)
            if (r.getGuest() == guest) res.add(r);
        return res;
    }
}
