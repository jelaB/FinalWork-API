package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the professor database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Professor.findAll", query="SELECT p FROM Professor p"),
	@NamedQuery(name = "Professor.getProfessor", query = "SELECT p from Professor p where p.userId=:id")})
public class Professor extends User {
	private static final long serialVersionUID = 1L;
	
	private String role;

	//bi-directional many-to-one association to Commission
	@OneToMany(mappedBy="professorBean", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<Commission> commissions;

	//bi-directional many-to-one association to Topic
	@OneToMany(mappedBy="professorBean",  cascade = { CascadeType.ALL })
	private List<Topic> topics;

	public Professor() {
		super();
	}
	
	public Professor( String username, String pass) {
		super(username, pass);
	}

	public List<Commission> getCommissions() {
		return this.commissions;
	}

	public void setCommissions(List<Commission> commissions) {
		this.commissions = commissions;
	}

	public Commission addCommission(Commission commission) {
		getCommissions().add(commission);
		commission.setProfessorBean(this);

		return commission;
	}

	public Commission removeCommission(Commission commission) {
		getCommissions().remove(commission);
		commission.setProfessorBean(null);

		return commission;
	}

	public List<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Topic addTopic(Topic topic) {
		getTopics().add(topic);
		topic.setProfessorBean(this);

		return topic;
	}

	public Topic removeTopic(Topic topic) {
		getTopics().remove(topic);
		topic.setProfessorBean(null);

		return topic;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}