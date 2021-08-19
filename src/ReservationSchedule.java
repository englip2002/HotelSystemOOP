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
