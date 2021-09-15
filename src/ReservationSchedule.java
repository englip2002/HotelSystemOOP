import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationSchedule implements Reportable, Comparable<LocalDate>{
	private LocalDate startDate;
	private LocalDate endDate;
	private int daysBetween;
	
	public ReservationSchedule(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReservationSchedule) {
			return (startDate.equals( ((ReservationSchedule)obj ).getStartDate() )) && 
					 (endDate.equals( ((ReservationSchedule)obj ).getEndDate() ));
		}
		return false;
	}
	
	@Override
	public int compareTo(LocalDate o) {
		// Negative = schedule is in the past (earlier than o)
		// 0 = schedule is active (o is inside the schedule)
		// Positive = schedule is in future (later than o)
		
		if (endDate.compareTo(o) < 0) {
			return -1;
		}
		else if (startDate.compareTo(o) > 0) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String generateReport() {
		return "     [ " + startDate + " - " + endDate + " ] (" + daysBetween + " nights)\n";
	}
	
	public String generateTableRow() {
		return startDate.toString() + " | " + endDate.toString() + " | ";
	}
	
	//Getters
	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public int getDaysBetween() {
		return daysBetween;
	}
}
