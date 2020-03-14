package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findUser", query = "SELECT u FROM User u WHERE u.username=:username"),
	@NamedQuery(name = "User.checkToken", query = "SELECT u FROM User u WHERE u.pass=:pass ")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private int userId;

	@Column(name = "email", unique = false, nullable = true)
	private String email;

	@Column(name = "image", unique = false, nullable = true)
	@Lob
	private byte[] image;
	
	@Column(name = "lastname", unique = false, nullable = true)
	private String lastname;

	@Column(name = "name", unique = false, nullable = true)
	private String name;

	@Column(name = "pass", unique = false, nullable = false)
	private String pass;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	public User() {
	}
	
	public User(String username, String pass) {
		super();
		this.pass = pass;
		this.username = username;
	}

	public User(String email, String lastname, String name, String pass, String role, String username, byte[] ar) {
		super();
		this.email = email;
		this.lastname = lastname;
		this.name = name;
		this.pass = pass;
		this.username = username;
		this.image = ar;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}