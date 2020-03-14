package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the commission database table.
 * 
 */
@Entity
@NamedQuery(name="Commission.findAll", query="SELECT c FROM Commission c")
public class Commission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="commission_id")
	private int commissionId;

	//bi-directional many-to-one association to Professor
	@ManyToOne
	@JoinColumn(name="professor")
	private Professor professorBean;

	//bi-directional many-to-one association to TopicOfStudentLabor
	@ManyToOne
	@JoinColumn(name="topic_of_student_labor")
	private TopicOfStudentLabor topicOfStudentLaborBean;

	public Commission() {
	}

	public int getCommissionId() {
		return this.commissionId;
	}

	public void setCommissionId(int commissionId) {
		this.commissionId = commissionId;
	}

	public Professor getProfessorBean() {
		return this.professorBean;
	}

	public void setProfessorBean(Professor professorBean) {
		this.professorBean = professorBean;
	}

	public TopicOfStudentLabor getTopicOfStudentLaborBean() {
		return this.topicOfStudentLaborBean;
	}

	public void setTopicOfStudentLaborBean(TopicOfStudentLabor topicOfStudentLaborBean) {
		this.topicOfStudentLaborBean = topicOfStudentLaborBean;
	}

}