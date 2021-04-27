package model;

public class Reservation implements Model {
	private String username;
	private String movietitle;
	private String date;
	private String timeslot;
	private String location;
	private String seatID;
	
	public Reservation(String username, String movietitle, String date, String timeslot, String location, String seatID) {
		this.username = username;
		this.movietitle = movietitle;
		this.date = date;
		this.timeslot = timeslot;
		this.location = location;
		this.seatID = seatID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMovietitle() {
		return movietitle;
	}

	public void setMovietitle(String movietitle) {
		this.movietitle = movietitle;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSeatID() {
		return seatID;
	}

	public void setSeatID(String seatID) {
		this.seatID = seatID;
	}
}
