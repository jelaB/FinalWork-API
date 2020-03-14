package dto;

import java.util.List;

import model.TopicOfStudentLabor;

public class StudentDTO {

	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String lastname;
	private int espb;
	private String nbi;
	private int semester;
	private String studyProgram;
	private String role;
	private byte[] image;
	private byte[] pdf;
	private List<TopicOfStudentLabor> topicOfStudentLabors;

	public StudentDTO() {
	}

	public int getEspb() {
		return this.espb;
	}

	public void setEspb(int espb) {
		this.espb = espb;
	}

	public String getNbi() {
		return this.nbi;
	}

	public void setNbi(String nbi) {
		this.nbi = nbi;
	}

	public int getSemester() {
		return this.semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getStudyProgram() {
		return this.studyProgram;
	}

	public void setStudyProgram(String studyProgram) {
		this.studyProgram = studyProgram;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	
	public String getTopicName() {
		if(!topicOfStudentLabors.isEmpty() && topicOfStudentLabors.get(0) != null) {
			return topicOfStudentLabors.get(0).getTopicBean().getTopicName();
		} else {
			return "";
		}
	}
	
	public void setTopicName( List<TopicOfStudentLabor> t) {
		this.topicOfStudentLabors = t;
	}
}
