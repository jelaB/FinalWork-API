package dto;

import java.util.List;

import model.Commission;
import model.Student;
import model.Topic;

public class TopicOfStudentLaborDTO {

	private int id;
	private byte[] finalDocumentPdf;
	private List<Commission> commissions;
	private Student studentBean;
	private Topic topicBean;

	public TopicOfStudentLaborDTO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int topicofstudentlaborid) {
		this.id = topicofstudentlaborid;
	}

	public byte[] getFinalDocumentPdf() {
		return finalDocumentPdf;
	}

	public void setFinalDocumentPdf(byte[] finalDocumentPdf) {
		this.finalDocumentPdf = finalDocumentPdf;
	}

	public String getCommissions() {
		String members = "";
		if( commissions != null && !commissions.isEmpty()) {
			for( Commission c : commissions) {
				members += c.getProfessorBean().getName() + " " + c.getProfessorBean().getLastname() + "#";
			}
			return members;
		} else {
			return "";
		}
	}

	public void setCommissions(List<Commission> commissions) {
		this.commissions = commissions;
	}

	public String getStudentName() {
		if( studentBean != null) {
			return studentBean.getName() + " " + studentBean.getLastname();
		} 
		return "";
	}

	public void setStudentName(Student studentBean) {
		this.studentBean = studentBean;
	}

	public String getTopicName() {
		if( topicBean != null) {
			return topicBean.getTopicName();
		} 
		return "";
	}

	public void setTopicName(Topic topicBean) {
		this.topicBean = topicBean;
	}
//	
//	public String getProfessor() {
//		return topicBean.getProfessor().getName() + topicBean.getProfessor().getLastname();
//	}
//	
//	public void setProfessor( Topic topic) {
//		this.topicBean = topic;
//	}
	
	
}


