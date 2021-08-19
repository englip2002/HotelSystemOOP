import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DriverReservation {
	public static void main(String[] args) {

		// CONSTANTS
		int MAX_RESERVATION_DAYS = 365;
		
		// Variable declaration and definition
		
		/* Price cincai write one, ltr chg */
		RoomType[] roomTypes = {
				new RoomType("Single Room", 1, 0, 150.00), 
				new RoomType("Double Room", 2, 0, 185.00), 
				new RoomType("Suite", 0, 1, 200.00), 
				new RoomType("Deluxe Suite", 2, 0, 230.00), 
				new RoomType("Family Room", 3, 0, 280.00), 
				new RoomType("Family Suite", 2, 1, 315.00)
		};
		
		/*Floor declaration written by Thongsx, ltr shall chg*/
		Block[] blocks = {
				new Block(1, 10), new Block(2, 10)
		};
		Floor[] floors = {
				new Floor(1, 10), new Floor(2, 10), new Floor(3, 10), new Floor(4, 10), new Floor(5, 10), new Floor(6, 10)
		};
		for (int i = 0; i < floors.length; i++) {
			for (int j = 0; j < floors[i].getMaxNumberOfRooms(); j++) {
				floors[i].addRoom(new Room(floors[i].getFloorNumber() * 100 + j + 1, roomTypes[i]));
			}
		}
		for (Block b: blocks) {
			for (int i = 1; i < 7; i++) {
				int numberOfRooms = 10;
				Floor f = new Floor(i, numberOfRooms);
				for (int j = 0; j < numberOfRooms; j++) {
					f.addRoom(new Room(i*100+j+1, roomTypes[i-1]));
				}
				b.addFloor(f);
			}
		}
		
		// Reservation Process
		Scanner scanner = new Scanner(System.in);

		// Input start and end date
		LocalDate startDate = getDateInput(scanner, "Enter reservation start date (YYYY-MM-DD): ");
		while (!startDate.isAfter(LocalDate.now())) {
			System.out.println("Start date must be at least 1 day later than today's date! Please re-enter");
			startDate = getDateInput(scanner, "Enter reservation start date (YYYY-MM-DD): ");
		}
		LocalDate endDate = getDateInput(scanner, "Enter reservation end date (YYYY-MM-DD): ");
		ReservationSchedule schedule = new ReservationSchedule(startDate, endDate);
		while (!endDate.isAfter(startDate) || schedule.getDaysBetween() > MAX_RESERVATION_DAYS) {
			if (!endDate.isAfter(startDate)) 
				System.out.println("End Date must be at least 1 day later than Start Date! Please re-enter. ");
			else if (schedule.getDaysBetween() > MAX_RESERVATION_DAYS)
				System.out.println("Sorry, but you can only reserve up to a maximum of " + MAX_RESERVATION_DAYS + " days. Please re-enter. ");
			endDate = getDateInput(scanner, "Enter reservation end date (YYYY-MM-DD): ");
			schedule = new ReservationSchedule(startDate, endDate);
		}
		
		
		// For each room type:
		int[] availableRoomCounts = new int[roomTypes.length];
		for (int i = 0; i < roomTypes.length; i++) {
			// Calculate the amount of available rooms in the given date range
			int total = 0;
			for (Block b: blocks) {
				for (int f = 0; f < b.getNumberOfFloors(); f++) {
					for (int r = 0; r < b.getFloors()[f].getNumberOfRooms(); r++) {
						Room room = b.getFloors()[f].getRooms()[r];
						if (room.getRoomType().equals(roomTypes[i]) && room.validateReservationSchedule(schedule)) {
							total++;
						}
					}
				}
			}
			availableRoomCounts[i] = total;
		}
		
		int[] reservedRoomTypeAmounts = new int[roomTypes.length];
		boolean continueChooseRoomType = true;
		do {
			//Choose room type
			System.out.println("+----------------------------------------------------------------------------------+");
			System.out.println("| No |    Room Type    | Twin Beds | King Beds | Price Per Night | Available Rooms |");
			System.out.println("+----------------------------------------------------------------------------------+");
			// For each room type:
			for (int i = 0; i < availableRoomCounts.length; i++) {		
				// Display room type info
				System.out.println("| " + String.format("%2d", (i+1)) + " | "
						+ String.format("%-15s", roomTypes[i].getName()) + " | "
						+ String.format("%9d", roomTypes[i].getNumberOfTwinBeds()) + " | "
						+ String.format("%9d", roomTypes[i].getNumberOfKingBeds()) + " | "
						+ "RM" + String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | "
						+ String.format("%15d", availableRoomCounts[i]) + " |");
			}
			System.out.println("+----------------------------------------------------------------------------------+");
			int roomChoice, reserveAmount;
			do {
				roomChoice = getIntegerInput(scanner, "Choose a room type to reserve (Enter number): ") - 1;
				if (availableRoomCounts[roomChoice] == 0) {
					System.out.println("Sorry, but we currently don't have any available rooms for this room type. Please choose another room type. ");
				}
			} while (roomChoice < 0 || roomChoice > roomTypes.length || availableRoomCounts[roomChoice] == 0);
			
			do {
				reserveAmount = getIntegerInput(scanner, "Enter the amount of " + roomTypes[roomChoice].getName() + " to be reserved: ");
				if (reserveAmount < 0)
					System.out.println("Invalid input. Please re-enter. ");
				else if (reserveAmount < 1) 
					System.out.println("You need to reserve at least one room. ");
				else if (reserveAmount > availableRoomCounts[roomChoice]) {
					System.out.println("Sorry, but we only have " + availableRoomCounts[roomChoice] 
							+ " rooms available for this room type. Please enter a smaller amount. ");
				}
			} while (reserveAmount < 0 || reserveAmount > availableRoomCounts[roomChoice]);
			
			reservedRoomTypeAmounts[roomChoice] += reserveAmount;
			availableRoomCounts[roomChoice] -= reserveAmount;

			System.out.println("\n                  < Reservation Summary >");
			System.out.println("            [ " + startDate + " - " + endDate + " ] : (" + schedule.getDaysBetween() + " nights)");
			System.out.println("+-----------------------------------------------------------+");
			System.out.println("|    Room Type    | Amount | Price Per Night |   Subtotal   |");
			System.out.println("+-----------------------------------------------------------+");
			double total = 0;
			for (int i = 0; i < roomTypes.length; i++) {
				if (reservedRoomTypeAmounts[i] != 0) {
					System.out.println("| " 
							+ String.format("%-15s", roomTypes[i].getName()) + " | "
							+ String.format("%6d", reservedRoomTypeAmounts[i]) + " | "
							+ "RM" + String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | "
							+ "RM" + String.format("%10.2f", reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight()) + " |");
					total += reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight() * schedule.getDaysBetween();
				}
			}
			System.out.println("+-----------------------------------------------------------+");
			System.out.println("|                                      TOTAL | RM" + String.format("%10.2f", total) + " |");
			System.out.println("+-----------------------------------------------------------+\n");
			
			boolean invalidInput;
			do {
				invalidInput = false;
				System.out.print("Do you want to reserve more rooms? (Y/N): ");
				String inputContinue = scanner.nextLine();
				if (inputContinue.length() != 1)
					invalidInput = true;
				else {
					char c = Character.toUpperCase(inputContinue.charAt(0));
					if (c != 'Y' && c != 'N')
						invalidInput = true;
					else
						continueChooseRoomType = (c == 'Y');
				}
				if (invalidInput)
					System.out.println("Invalid input! Please re-enter. ");
			} while (invalidInput);
		} while (continueChooseRoomType);
		
		System.out.println("EOP");;
		scanner.close();
	}

	static LocalDate getDateInput(Scanner scanner, String question) {
		LocalDate d = null;
		boolean invalidDate = true;
		do {
			try {
				System.out.print(question);
				String inputDate = scanner.nextLine();
				d = LocalDate.parse(inputDate);
				invalidDate = false;
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format, please re-enter. ");
			} catch (Exception e) {
				System.out.println("Something went wrong, please re-enter. ");
			}
		} while (invalidDate);
		return d;
	}
	
	static int getIntegerInput(Scanner scanner, String question) {
		int output = 0;
		boolean invalidInput;
		do {
			invalidInput = false;
			System.out.print(question);
			String inputStr = scanner.nextLine().replaceAll("\\s+", "");
			if (inputStr.length() == 0)
				invalidInput = true;
			else {
				try {
					output = Integer.parseInt(inputStr);
				}
				catch (NumberFormatException e) {
					invalidInput = true;
				}
			}
			if (invalidInput)
				System.out.println("Invalid input! Please re-enter. ");
		} while (invalidInput);
		return output;
	}

}
