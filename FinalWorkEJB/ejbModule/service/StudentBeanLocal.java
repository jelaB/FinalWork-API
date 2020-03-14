package service;

import java.util.List;

import javax.ejb.Local;

import dto.TopicDTO;
import dto.TopicOfStudentLaborDTO;
import model.Student;
import model.Topic;
import model.TopicOfStudentLabor;

@Local
public interface StudentBeanLocal {
	public TopicOfStudentLaborDTO getStudentTopic( int id);
	public TopicOfStudentLabor assignTopic( TopicOfStudentLabor topic, boolean update);
	public TopicOfStudentLabor getTopicForStudentID( int studentId);
	/**
	 * Save finished document of student's final labor.
	 * @param topic
	 * @return
	 */
	public Student getStudent( int id);
	public boolean hasTopic(int id);
	public Topic getTopic( int id);
	public List<TopicDTO> getAvailableTopics();
}
