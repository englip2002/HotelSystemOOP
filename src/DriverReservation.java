import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DriverReservation {
	public static void main(String[] args) {

		// Constants
		int MAX_RESERVATION_DAYS = 365;

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
		
		// Manual room creation
		int floorsPerBlock = 10;
		int roomsPerFloor = 10;
		Block[] blocks = { new Block("A", floorsPerBlock), new Block("B", floorsPerBlock) };
		for (Block b : blocks) {
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
			b.addFloors(floors);
		}

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
			System.out.println("+----------------------------------+");
			System.out.println("|      Hotel Reservation Menu      |");
			System.out.println("+----------------------------------+");
			System.out.println("| 1 - View Profile                 |");
			System.out.println("| 2 - Make a Reservation           |");
			System.out.println("| 3 - View Previous Reservations   |");
			System.out.println("| 4 - Cancel Previous Reservation  |");
			System.out.println("| 5 - Logout                       |");
			System.out.println("+----------------------------------+");

			menuOpt = getIntegerInput(scanner, "Enter your option: ");
			while (menuOpt < 1 || menuOpt > 4) {
				System.out.println("Invalid menu option. Please re-enter. ");
				menuOpt = getIntegerInput(scanner, "Enter your option: ");
			}
			
			switch (menuOpt) {
			case 1: {
			//View customer profile (seng wai)
				break;
			}
			// Make a Reservation
			case 2: {
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
					for (Block b : blocks) {
						for (int f = 0; f < b.getNumberOfFloors(); f++) {
							for (int r = 0; r < b.getFloors()[f].getNumberOfRooms(); r++) {
								Room room = b.getFloors()[f].getRooms()[r];
								if (room.getRoomType().equals(roomTypes[i])
										&& room.validateReservationSchedule(schedule)) {
									total++;
								}
							}
						}
					}
					availableRoomCounts[i] = total;
				}

				// Display and Choose Room Types
				int[] reservedRoomTypeAmounts = new int[roomTypes.length];
				boolean continueChooseRoomType = true;
				do { // Room Type loop
					System.out.println("\n" + 
							"+----------------------------------------------------------------------------------+");
					System.out.println(
							"| No |    Room Type    | Twin Beds | King Beds | Price Per Night | Available Rooms |");
					System.out.println(
							"+----------------------------------------------------------------------------------+");
					// For each room type:
					for (int i = 0; i < availableRoomCounts.length; i++) {
						// Display room type info
						System.out.println("| " + String.format("%2d", (i + 1)) + " | "
								+ String.format("%-15s", roomTypes[i].getName()) + " | "
								+ String.format("%9d", roomTypes[i].getNumberOfTwinBeds()) + " | "
								+ String.format("%9d", roomTypes[i].getNumberOfKingBeds()) + " | " + "RM"
								+ String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | "
								+ String.format("%15d", availableRoomCounts[i]) + " |");
					}
					System.out.println(
							"+----------------------------------------------------------------------------------+");
					int roomChoice, reserveAmount;

					do { // Choose a room type
						roomChoice = getIntegerInput(scanner, "Choose a room type to reserve (Enter number): ") - 1;
						if (availableRoomCounts[roomChoice] == 0) {
							System.out.println(
									"Sorry, but we currently don't have any available rooms for this room type. Please choose another room type. ");
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
					System.out.println("\n               < Room Reservation Summary >");
					System.out.println("            [ " + startDate + " - " + endDate + " ] : ("
							+ schedule.getDaysBetween() + " nights)");
					System.out.println("+-----------------------------------------------------------+");
					System.out.println("|    Room Type    | Amount | Price Per Night |   Subtotal   |");
					System.out.println("+-----------------------------------------------------------+");
					double total = 0;
					for (int i = 0; i < roomTypes.length; i++) {
						if (reservedRoomTypeAmounts[i] != 0) {
							System.out
									.println("| " + String.format("%-15s", roomTypes[i].getName()) + " | "
											+ String.format("%6d", reservedRoomTypeAmounts[i]) + " | " + "RM"
											+ String.format("%13.2f", roomTypes[i].getPricePerNight()) + " | " + "RM"
											+ String.format("%10.2f",
													reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight())
											+ " |");
							total += reservedRoomTypeAmounts[i] * roomTypes[i].getPricePerNight()
									* schedule.getDaysBetween();
						}
					}
					// Display total
					System.out.println("+-----------------------------------------------------------+");
					System.out.println("|                                      TOTAL | RM"
							+ String.format("%10.2f", total) + " |");
					System.out.println("+-----------------------------------------------------------+\n");

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
				} while (continueChooseRoomType);

				// Find rooms that are not reserved on the chosen days
				System.out.println("\nYour rooms: ");;
				ArrayList<Room> reservedRooms = new ArrayList<Room>();
				for (int i = 0; i < roomTypes.length; i++) {	//For each room type
					for (int j = 0; j < reservedRoomTypeAmounts[i]; j++) {	// Get vacant rooms for n times
						nextRoom: for (Block b: blocks) {
							for (Floor f: b.getFloors()) {
								for (Room r: f.getRooms()) {
									if (roomTypes[i].equals(r.getRoomType()) && r.validateReservationSchedule(schedule)) {
										r.addReservationSchedule(schedule);
										reservedRooms.add(r);
										System.out.println("\tBlock " + b.getBlockIdentifier() + 
												" Floor " + f.getFloorNumber() + " Room " + r.getRoomNumber() + 
												" (" + r.getRoomType() + ")");
										break nextRoom;
									}
								}
							}
						}
					}
				}
				
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
				Reservation reservation = new Reservation(cust, schedule, reservedRooms, foodOrder, payment);
				// cust.addReservation(reservation);
				break;
			}

			// View Previous Reservations
			case 3: {
				// Waiting for seng wai's Customer Class
				break;
			}
			
			// Cancel Previous Reservation
			case 4: {
				// Waiting for seng wai's Customer Class
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
}
