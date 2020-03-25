package calendar;

public class Contact {
	
	String firstName, lastName, company, phone, email;
	public Contact(String firstName, String lastName, String company, String phone, String email) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
		this.phone = phone;
		this.email = email;
	}
	public String toString() {
		return firstName + " " + lastName + "\n" + company + "\n" + email + "\n" + phone;
	}
	public String toAppend() {
		return firstName + "," + lastName + "," + company + "," + phone + "," + email;
	}
}
