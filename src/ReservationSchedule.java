import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationSchedule {
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
		else {
			return false;
		}
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
