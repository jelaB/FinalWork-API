package utilities;

public class Credentials {

	private String username;
	private String passwordToken;
	
	public Credentials(String username, String passwordToken) {
		super();
		this.username = username;
		this.passwordToken = passwordToken;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordToken() {
		return passwordToken;
	}
	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}
	
	
	
}
