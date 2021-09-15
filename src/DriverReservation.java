import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DriverReservation {
	
	
	public static void main(String[] args) {
		// Variable declaration and definition
		Scanner scanner = new Scanner(System.in);

		RoomType[] roomTypes = { 
				new RoomType("Single Room", 1, 0, 100.00), 
				new RoomType("Double Room", 2, 0, 160.00),
				new RoomType("Family Room", 3, 0, 210.00), 
				new RoomType("Normal Suite", 0, 1, 175.00), 
				new RoomType("Deluxe Suite", 1, 1, 245.00),
				new RoomType("Premium Suite", 2, 1, 295.00) 
			};
		
		Block block = initializeRooms(roomTypes);

		/*
		 * 
		 * Login
		 * 
		 */
		// Assume customer login successful
		Customer cust = new Customer();
		

		// Main Menu
		int menuOpt = 0;
		while (menuOpt != 5) {
			System.out.print("\033[H\033[2J");
	        System.out.flush();
			System.out.println(
					  "+----------------------------------+\n"
					+ "|      Hotel Reservation Menu      |\n"
					+ "+----------------------------------+\n"
					+ "| 1 - View Profile                 |\n"
					+ "| 2 - Make a Reservation           |\n"
					+ "| 3 - View Reservations            |\n"
					+ "| 4 - Cancel Reservation           |\n"
					+ "| 5 - Logout                       |\n"
					+ "+----------------------------------+");

			menuOpt = getIntegerInput(scanner, "Enter your option: ");
			while (menuOpt < 1 || menuOpt > 5) {
				System.out.println("Invalid menu option. Please re-enter. ");
				menuOpt = getIntegerInput(scanner, "Enter your option: ");
			}
			
			if (menuOpt != 5) {	// Clear Screen
				System.out.print("\033[H\033[2J");
		        System.out.flush();
			}
			
			switch (menuOpt) {
			case 1: {
			//View customer profile (seng wai)
				break;
			}
			// Make a Reservation
			case 2: {
				Reservation reservation = makeReservation(scanner, cust, roomTypes, block);
				cust.addReservation(reservation);
				break;
			}

			// View Previous Reservations
			case 3: {
				viewReservations(scanner, cust);
				break;
			}
			
			// Cancel Previous Reservation
			case 4: {
				cancelReservations(scanner, cust, block);
				break;
			}
			
			// Logout
			case 5: {
				// Print logout msg
				break;
			}
			
			default:
				break;
			}
		}

		System.out.println("EOP");
		scanner.close();
	}
	
	// Main Functions
	public static Reservation makeReservation(Scanner scanner, Customer cust, RoomType[] roomTypes, Block block) {
		
		// Constants
		int MAX_RESERVATION_DAYS = 365;
		
		// Title
		System.out.println("\n            ( Make Reservation )\n");
		
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
				System.out.println("Sorry, but you can only reserve up to a maximum of " + MAX_RESERVATION_DAYS
						+ " days. Please re-enter. ");
			endDate = getDateInput(scanner, "Enter reservation end date (YYYY-MM-DD): ");
			schedule = new ReservationSchedule(startDate, endDate);
		}

		// For each room type in the chosen date range:
		int[] availableRoomCounts = new int[roomTypes.length];
		for (int i = 0; i < roomTypes.length; i++) {
			// Calculate the amount of available rooms
			int total = 0;
			for (int f = 0; f < block.getNumberOfFloors(); f++) {
				for (int r = 0; r < block.getFloors()[f].getNumberOfRooms(); r++) {
					Room room = block.getFloors()[f].getRooms()[r];
					if (room.getRoomType().equals(roomTypes[i])
							&& room.validateReservationSchedule(schedule)) {
						total++;
					}
				}
			}
			
			availableRoomCounts[i] = total;
		}

		// Display and Choose Room Types
		int[] reservedRoomTypeAmounts = new int[roomTypes.length];
		boolean continueChooseRoomType = true;
		do { // Room Type loop
			System.out.println("\n"
					+ "+----------------------------------------------------------------------------------+\n"
					+ "| No |    Room Type    | Twin Beds | King Beds | Price Per Night | Available Rooms |\n"
					+ "+----------------------------------------------------------------------------------+");
			// For each room type:
			for (int i = 0; i < availableRoomCounts.length; i++) {
				// Display room type info
				System.out.println("| " 
						+ String.format("%2d", (i + 1)) + " | "
						+ String.format("%-15s", roomTypes[i].getName()) + " | "
						+ String.format("%9d", roomTypes[i].getNumberOfTwinBeds()) + " | "
						+ String.format("%9d", roomTypes[i].getNumberOfKingBeds()) + " | " + "RM"
						+ String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | "
						+ String.format("%15d", availableRoomCounts[i]) + " |");
			}
			System.out.println("+----------------------------------------------------------------------------------+");
			int roomChoice, reserveAmount;

			
			do { // Choose a room type
				roomChoice = getIntegerInput(scanner, "Choose a room type to reserve (Enter number): ") - 1;
				if (roomChoice < 0 || roomChoice > roomTypes.length) {
					System.out.println("Invalid input! Please re-enter. ");
				}
				else if (availableRoomCounts[roomChoice] == 0) {
					System.out.println("Sorry, but we currently don't have any available rooms for this room type. Please choose another room type. ");
				}
			} while (roomChoice < 0 || roomChoice > roomTypes.length || availableRoomCounts[roomChoice] == 0);

			
			do { // Enter reserve amount
				reserveAmount = getIntegerInput(scanner,
						"Enter the amount of " + roomTypes[roomChoice].getName() + " to be reserved: ");
				if (reserveAmount < 0)
					System.out.println("Invalid input. Please re-enter. ");
				else if (reserveAmount < 1)
					System.out.println("You need to reserve at least one room. ");
				else if (reserveAmount > availableRoomCounts[roomChoice]) {
					System.out.println("Sorry, but we only have " + availableRoomCounts[roomChoice]
							+ " rooms available for this room type. Please enter a smaller amount. ");
				}
			} while (reserveAmount < 1 || reserveAmount > availableRoomCounts[roomChoice]);

			reservedRoomTypeAmounts[roomChoice] += reserveAmount;
			availableRoomCounts[roomChoice] -= reserveAmount;

			
			// Display summary at end of loop
			System.out.println("\n               < Room Reservation Summary >\n"
					+ "          [ " + startDate + " - " + endDate + " ] : (" 
							+ schedule.getDaysBetween() + " nights)\n"
					+ "+-----------------------------------------------------------+\n"
					+ "|    Room Type    | Amount | Price Per Night |   Subtotal   |\n"
					+ "+-----------------------------------------------------------+");
			double total = 0;
			for (int i = 0; i < roomTypes.length; i++) {
				if (reservedRoomTypeAmounts[i] != 0) {
					System.out.println("| "
									+ String.format("%-15s", roomTypes[i].getName()) + " | "
									+ String.format("%6d", reservedRoomTypeAmounts[i]) + " | " + "RM"
									+ String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | " + "RM"
									+ String.format("%10.2f",reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight())
									+ " |");
					total += reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight();
				}
			}
			// Display total
			System.out.println(
					  "+-----------------------------------------------------------+\n"
					+ "|                                      TOTAL | RM" + String.format("%10.2f", total) + " |\n"
					+ "+-----------------------------------------------------------+\n"
					+ String.format("|                   GRAND TOTAL (%3d nights) | RM%10.2f |\n", schedule.getDaysBetween(), total * schedule.getDaysBetween())
					+ "+-----------------------------------------------------------+\n"
					);

			
			// Ask to continue reserve more rooms or not
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
			
			// If 'Y' then choose more room type to reserve, else proceed
		} while (continueChooseRoomType);

		// Find vacant rooms on the chosen schedule
		ArrayList<Room> reservedRooms = getVacantRooms(block, roomTypes, schedule, reservedRoomTypeAmounts);
		
		/*
		 * 
		 * Pick Food Order (eng lip)
		 * 
		 */
		// Assume food orders are made
		FoodOrder foodOrder = new FoodOrder();

		/*
		 * 
		 * Payment (shu wei)
		 * 
		 */
		// Assume payment is made
		Payment payment = new PaymentByCard(100);
		Reservation reservation = new Reservation(cust, schedule, reservedRooms, foodOrder.getOrderID(), payment);
		
		System.out.println("\nThe following is the summary of your reservation: ");
		System.out.println(reservation.generateReport());;
		System.out.print("< Press any key to continue >");
		scanner.nextLine();
		
		return reservation;
	}
	
	public static void viewReservations(Scanner scanner, Customer cust) {
		
		System.out.println("\n                ( View Reservations )\n");
		
		ArrayList<Reservation> reservations = cust.getReservationList();
		if (reservations.size() == 0) {
			System.out.println("          < You have no previous reservations! >");
			return;
		}
		
		int viewOpt;
		do {
			printReservationsTable(reservations);
			do {
				viewOpt = getIntegerInput(scanner, "Enter No. to view the details (0 to exit): ");
				if (viewOpt < 0 || viewOpt > reservations.size()) {
					System.out.println("Invalid input! Please re-enter");
				}
			} while(viewOpt < 0 || viewOpt > reservations.size());
			
			if (viewOpt == 0)
				return;
			
			viewOpt -= 1;
			
			System.out.println(reservations.get(viewOpt).generateReport());
			System.out.print("< Press any key to continue >");
			scanner.nextLine();
			
			// Print Food Order Report
			
			// Print Payment Report
			
		} while (viewOpt != 0);
	}
	
	public static void cancelReservations(Scanner scanner, Customer cust, Block block) {
		System.out.println("\n                ( Cancel Reservations )\n");
		
		ArrayList<Reservation> reservations = cust.getReservationList();
		if (reservations.size() == 0) {
			System.out.println("          < You have no previous reservations! >");
			return;
		}
		
		int menuOpt;
		printReservationsTable(reservations);
		do {
			menuOpt = getIntegerInput(scanner, "Enter No. to cancel the reservation (0 to exit): ");
			if (menuOpt < 0 || menuOpt > reservations.size()) {
				System.out.println("Invalid input! Please re-enter");
			}
		} while(menuOpt < 0 || menuOpt > reservations.size());
		
		if (menuOpt == 0)
			return;
		
		menuOpt -= 1;
		String cancelID = reservations.get(menuOpt).getReservationID();
		System.out.println("Are you sure you want to cancel the reservation (" + cancelID + ")?");
		System.out.println("[ THIS ACTION CANNOT BE REVERSED! ]");
		System.out.print("(Type 'YES' to cancel) > ");
		String inputYN = scanner.nextLine().toUpperCase();
		if (inputYN.equals("YES")) {
			cust.cancelReservation(reservations.get(menuOpt).getReservationID());
			System.out.println("( Cancellation completed. Reservation " + cancelID + " is now cancelled. )");
		}
		else {
			System.out.println("< Cancellation stopped. Returning to main menu. >");
		}
		
		System.out.print("< Press any key to continue >");
		scanner.nextLine();
	}
	
	
	// Side Functions
	
	public static LocalDate getDateInput(Scanner scanner, String question) {
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
			}
		} while (invalidDate);
		return d;
	}

	public static int getIntegerInput(Scanner scanner, String question) {
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
				} catch (NumberFormatException e) {
					invalidInput = true;
				}
			}
			if (invalidInput)
				System.out.println("Invalid input! Please re-enter. ");
		} while (invalidInput);
		return output;
	}
	
	public static ArrayList<Room> getVacantRooms(Block block, RoomType[] roomTypes, ReservationSchedule schedule, int[] reservedRoomTypeAmounts) {
		ArrayList<Room> reservedRooms = new ArrayList<Room>();
		for (int i = 0; i < roomTypes.length; i++) {	//For each room type
			for (int j = 0; j < reservedRoomTypeAmounts[i]; j++) {	// Get vacant rooms for n times
				nextRoom: 
				for (Floor f: block.getFloors()) {
					for (Room r: f.getRooms()) {
						if (roomTypes[i].equals(r.getRoomType()) && r.validateReservationSchedule(schedule)) {
							r.addReservationSchedule(schedule);
							reservedRooms.add(r);
							break nextRoom;
						}
					}
				}
			
			}
		}
		return reservedRooms;
	}
	
	public static Block initializeRooms(RoomType[] roomTypes) {
		// Manual room creation
		int floorsPerBlock = 10;
		int roomsPerFloor = 10;
		Block block = new Block("A", floorsPerBlock);
		
		Floor[] floors = new Floor[floorsPerBlock];
		for (int i = 0; i < floorsPerBlock; i++) {
			floors[i] = new Floor(i+1, roomsPerFloor);
			for (int j = 0; j < roomsPerFloor; j++) {
				int k = 0;
				switch(i) {
				case 0, 1: { k = 0; break; }
				case 2, 3: { k = 1; break; }
				case 4, 5: { k = 2; break; }
				case 6, 7: {k = 3; break; }
				case 8: { k = 4; break; }
				case 9: {k = 5; break; }
				}
				floors[i].addRoom(new Room((i+1)*100+(j+1), roomTypes[k]));
			}
		}
		block.addFloors(floors);
		return block;
	}
	
	public static void initializeReservations(Customer cust, RoomType[] roomTypes, Block block) {
		
		ArrayList<Room> r2 = new ArrayList<>();
		r2.add(new Room(999, roomTypes[0]));
		cust.addReservation(new Reservation(
				cust, new ReservationSchedule(LocalDate.of(2021, 10, 5), LocalDate.of(2021, 10, 10)), 
				r2, "", new PaymentByCard(100)));
	}
	
	public static void printReservationsTable(ArrayList<Reservation> reservations) {
		String tableLine = "+-----+----------+------------+------------+-----------+\n";
		System.out.print(tableLine
					+ String.format("| %-3s | %-8s | %-10s | %-10s | %-9s |\n", 
							"No.", "ID", "Start Date", "End Date", "Status")
					+ tableLine
				);
		
		for (int i = 0; i < reservations.size(); i++) {
			System.out.printf("| %3d |", i+1);
			System.out.println(reservations.get(i).generateTableRow());
		}
		System.out.println(tableLine);

	}
	
}
