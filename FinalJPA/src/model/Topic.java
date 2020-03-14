
package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the topic database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Topic.findAll", query = "SELECT t FROM Topic t"),
	@NamedQuery(name = "Topic.getTopic", query = "SELECT t FROM Topic t where t.topicId=:id"),
	@NamedQuery(name = "Topic.findByMentor", query = "SELECT t FROM Topic t INNER JOIN t.topicOfStudentLabors sl WHERE t.topicId = sl.topicBean.topicId and t.professorBean =:mentor"),
	@NamedQuery(name = "Topic.findAvailable", query = "SELECT t FROM Topic t WHERE t.topicId NOT IN (SELECT t from Topic t INNER JOIN t.topicOfStudentLabors sl WHERE t.topicId = sl.topicBean.topicId)") })
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="topic_id")
	private int topicId;

	private String description;

	@Column(name="field_of_interesting")
	private String fieldOfInteresting;

	@Column(name="topic_name")
	private String topicName;

	//bi-directional many-to-one association to Professor
	@ManyToOne
	@JoinColumn(name="professor")
	private Professor professorBean;

	//bi-directional many-to-one association to TopicOfStudentLabor
	@OneToMany(mappedBy="topicBean", fetch=FetchType.EAGER)
	private List<TopicOfStudentLabor> topicOfStudentLabors;

	public Topic() {
	}

	public int getTopicId() {
		return this.topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFieldOfInteresting() {
		return this.fieldOfInteresting;
	}

	public void setFieldOfInteresting(String fieldOfInteresting) {
		this.fieldOfInteresting = fieldOfInteresting;
	}

	public String getTopicName() {
		return this.topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Professor getProfessorBean() {
		return this.professorBean;
	}

	public void setProfessorBean(Professor professorBean) {
		this.professorBean = professorBean;
	}

	public List<TopicOfStudentLabor> getTopicOfStudentLabors() {
		return this.topicOfStudentLabors;
	}

	public void setTopicOfStudentLabors(List<TopicOfStudentLabor> topicOfStudentLabors) {
		this.topicOfStudentLabors = topicOfStudentLabors;
	}

	public TopicOfStudentLabor addTopicOfStudentLabor(TopicOfStudentLabor topicOfStudentLabor) {
		getTopicOfStudentLabors().add(topicOfStudentLabor);
		topicOfStudentLabor.setTopicBean(this);

		return topicOfStudentLabor;
	}

	public TopicOfStudentLabor removeTopicOfStudentLabor(TopicOfStudentLabor topicOfStudentLabor) {
		getTopicOfStudentLabors().remove(topicOfStudentLabor);
		topicOfStudentLabor.setTopicBean(null);

		return topicOfStudentLabor;
	}

}