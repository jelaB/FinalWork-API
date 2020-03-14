package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student extends User {
	private static final long serialVersionUID = 1L;

	private int espb;

	private String nbi;

	private int semester;
	
	private String role;

	@Column(name="study_program")
	private String studyProgram;
	
	private byte[] pdf;

	//bi-directional many-to-one association to TopicOfStudentLabor
	@OneToMany(mappedBy="studentBean", fetch = FetchType.EAGER)
	private List<TopicOfStudentLabor> topicOfStudentLabors;

	public Student() {
		super();
	}
	
	public Student(String username, String pass) {
		super(username, pass);
	}
	
	public Student(String username, String pass, int espb, String nbi, int semester, String studyProgram) {
		super(username, pass);
		this.espb = espb;
		this.nbi = nbi;
		this.semester = semester;
		this.studyProgram = studyProgram;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<TopicOfStudentLabor> getTopicOfStudentLabors() {
		return this.topicOfStudentLabors;
	}

	public void setTopicOfStudentLabors(List<TopicOfStudentLabor> topicOfStudentLabors) {
		this.topicOfStudentLabors = topicOfStudentLabors;
	}
	
	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public TopicOfStudentLabor addTopicOfStudentLabor(TopicOfStudentLabor topicOfStudentLabor) {
		getTopicOfStudentLabors().add(topicOfStudentLabor);
		topicOfStudentLabor.setStudentBean(this);

		return topicOfStudentLabor;
	}

	public TopicOfStudentLabor removeTopicOfStudentLabor(TopicOfStudentLabor topicOfStudentLabor) {
		getTopicOfStudentLabors().remove(topicOfStudentLabor);
		topicOfStudentLabor.setStudentBean(null);

		return topicOfStudentLabor;
	}

}