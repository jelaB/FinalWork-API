package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the topic_of_student_labor database table.
 * 
 */
@Entity
@Table(name="topic_of_student_labor")
@NamedQueries({@NamedQuery(name="TopicOfStudentLabor.findAll", query="SELECT t FROM TopicOfStudentLabor t"),
	@NamedQuery(name = "TopicOfStudentLabor.studentHasTopic", query = "SELECT t FROM TopicOfStudentLabor t WHERE t.studentBean=:student"),
	@NamedQuery(name = "TopicsOfStudentLabor.byTopic", query = "SELECT t FROM TopicOfStudentLabor t WHERE t.topicBean=:topic")
})

public class TopicOfStudentLabor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int topicofstudentlaborid;

	@Lob
	@Column(name="final_document_pdf")
	private byte[] finalDocumentPdf;

	//bi-directional many-to-one association to Commission
	@OneToMany(mappedBy="topicOfStudentLaborBean", fetch = FetchType.EAGER)
	private List<Commission> commissions;

	//bi-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="student")
	private Student studentBean;

	//bi-directional many-to-one association to Topic
	@ManyToOne
	@JoinColumn(name="topic")
	private Topic topicBean;

	public TopicOfStudentLabor() {
	}

	public int getTopicofstudentlaborid() {
		return this.topicofstudentlaborid;
	}

	public void setTopicofstudentlaborid(int topicofstudentlaborid) {
		this.topicofstudentlaborid = topicofstudentlaborid;
	}

	public byte[] getFinalDocumentPdf() {
		return this.finalDocumentPdf;
	}

	public void setFinalDocumentPdf(byte[] finalDocumentPdf) {
		this.finalDocumentPdf = finalDocumentPdf;
	}

	public List<Commission> getCommissions() {
		return this.commissions;
	}

	public void setCommissions(List<Commission> commissions) {
		this.commissions = commissions;
	}

	public Commission addCommission(Commission commission) {
		getCommissions().add(commission);
		commission.setTopicOfStudentLaborBean(this);

		return commission;
	}

	public Commission removeCommission(Commission commission) {
		getCommissions().remove(commission);
		commission.setTopicOfStudentLaborBean(null);

		return commission;
	}

	public Student getStudentBean() {
		return this.studentBean;
	}

	public void setStudentBean(Student studentBean) {
		this.studentBean = studentBean;
	}

	public Topic getTopicBean() {
		return this.topicBean;
	}

	public void setTopicBean(Topic topicBean) {
		this.topicBean = topicBean;
	}

}