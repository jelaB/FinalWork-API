package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.QueryParam;

import dto.ProfessorDTO;
import dto.TopicDTO;
import model.Professor;
import model.Topic;

@Local
public interface TopicBeanLocal{
	
	/**
	 * @return
	 */
	public List<TopicDTO> getTopics();
	
	/**
	 * Topic(topic_id, topic_name, field_of_interesting:null,
	 * topic_description:null)
	 */
	public boolean addTopic(@QueryParam(value = "topicName") String topicName,
			@QueryParam(value = "field") String fieldOfInteresting,
			@QueryParam(value = "description") String description,
			int profId);

	/**
	 * Returns all topics of professor which is reserved topics for student
	 * labors
	 */
	public List<TopicDTO> getReservedTopics(int id);
	public List<TopicDTO> search(String query, boolean registered);
	public List<TopicDTO> searchReservedTopics(String query, int id);
	public List<ProfessorDTO> getProfessors(int id);
	public TopicDTO getTopicWithId(int id);
	public Topic getTopicById(int id);
	public Professor getProfessorById(int id);
	public void getPdf(int id) throws FileNotFoundException, IOException;
	public boolean createCommission(Professor p, Topic t);
}
