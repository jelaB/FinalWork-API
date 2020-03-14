package dto;

import java.util.List;

import model.Professor;
import model.TopicOfStudentLabor;

public class TopicDTO {

	private int id;

	private String topicName;

	private String fieldOfInteresting;

	private String topicDescription;

	private Professor professor;
	
	private List<TopicOfStudentLabor> student;

	public TopicDTO() {
	}

	public TopicDTO(int topicId, String topicName, String fieldOfInteresting, String topicDescription
			) {
		this.id = topicId;
		this.topicName = topicName;
		this.fieldOfInteresting = fieldOfInteresting;
		this.topicDescription = topicDescription;
//		this.professor = professor;
//		this.student = student;
	}

	public int getId() {
		return id;
	}

	public void setId(int topicId) {
		this.id = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getFieldOfInteresting() {
		return fieldOfInteresting;
	}

	public void setFieldOfInteresting(String fieldOfInteresting) {
		this.fieldOfInteresting = fieldOfInteresting;
	}

	public String getTopicDescription() {
		return topicDescription;
	}

	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}

	public String getProfName() {
		if( professor != null && !professor.getName().isEmpty() && !professor.getLastname().isEmpty()) {
			return professor.getName() + " " + professor.getLastname();
		} else {
			return "";
		}
	}

	public void setProfName(Professor professor) {
		this.professor = professor;
	}

	public String getStudent() {
		if( !student.isEmpty() && student.get(0) != null) {
			return student.get(0).getStudentBean().getUserId() + "#" + student.get(0).getStudentBean().getName() + " " + student.get(0).getStudentBean().getLastname();
		} else {
			return "";
		}
	}
//	
//	public List<TopicOfStudentLabor> getAssignedStudents() {
//		return student;
//	}
//
	public void setStudent(List<TopicOfStudentLabor> student) {
		this.student = student;
	}
	
	

}
