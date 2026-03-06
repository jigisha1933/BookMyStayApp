// UseCase2RoomInitialization.java

abstract class Room {
    private int numberOfBeds;
    private double size; // in square meters
    private double price; // per night

    public Room(int numberOfBeds, double size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    // Abstract method for room type
    public abstract String getRoomType();

    // Display room details
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price per night: $" + price);
    }
}

// Concrete room types
class SingleRoom extends Room {
    public SingleRoom() { super(1, 15.0, 50.0); }
    @Override
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 25.0, 80.0); }
    @Override
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(4, 50.0, 200.0); }
    @Override
    public String getRoomType() { return "Suite Room"; }
}

// Main class
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        // Static availability
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Initialize rooms
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        System.out.println("Welcome to Book My Stay App\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailability + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailability + "\n");

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailability + "\n");

        System.out.println("Application terminated.");
    }
}