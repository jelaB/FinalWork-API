package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.TopicDTO;
import dto.TopicOfStudentLaborDTO;
import mapper.TopicMapper;
import mapper.TopicOfStudentLaborMapper;
import model.Student;
import model.Topic;
import model.TopicOfStudentLabor;
import service.StudentBeanLocal;

/**
 * Session Bean implementation class StudentBean
 */
@Stateless
@Local
public class StudentBean implements StudentBeanLocal {

	private Logger logger = Logger.getLogger(StudentBean.class.getName());

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public StudentBean() {
	}

	@Override
	public TopicOfStudentLaborDTO getStudentTopic(int id) {
		try {
			logger.log(Level.INFO, "Get topic of student: getStudentTopic");
			TopicOfStudentLaborMapper top = TopicOfStudentLaborMapper.INSTANCE;
			TopicOfStudentLabor topic = em.find(TopicOfStudentLabor.class, id);
			return top.toDTO(topic);
		} catch (Exception e) {
			logger.log(null, "Get topics, ERROR", e);
			return null;
		}
	}

	@Override
	public TopicOfStudentLabor assignTopic(TopicOfStudentLabor topic, boolean update) {
		logger.log(Level.INFO, "Assign topic to student: assignTopic");

		try {
			if(update) {
				em.merge(topic);
				return topic;
			} else {
				em.persist(topic);
				return topic;
			}
		} catch (Exception e) {
			logger.log(null, "Reserving topic for student, ERROR!", e);
			return null;
		}
	}

	public Student getStudent(int id) {
		try {
			logger.log(Level.INFO, "Get student: getStudent");
			Student student = em.find(Student.class, id);
			return student;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Topic getTopic(int id) {
		try {
			logger.log(Level.INFO, "Get topic: getTopic");
			return em.find(Topic.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TopicDTO> getAvailableTopics() {
		try {
			logger.log(Level.INFO, "Get all available topics: getAvailableTopics");
			TopicMapper top= TopicMapper.INSTANCE;
			Query q = em.createNamedQuery("Topic.findAvailable");

			List<Topic> available = new ArrayList<>();
			List<TopicDTO> availableDTO = new ArrayList<>();
			available.addAll(q.getResultList());
			if( !available.isEmpty()) {
				logger.log(Level.SEVERE, "No avaiable topics: getAvailableTopics");
			}
			for( Topic t : available) {
				availableDTO.add(top.toDTO(t));
			}
			return availableDTO;
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean hasTopic(int id) {
		try {
			Query q = em.createNamedQuery("TopicOfStudentLabor.studentHasTopic");
			Student srch = em.find(Student.class, id);
			q.setParameter("student", srch);
			if ( q.getSingleResult() != null) {
				return true;
			} else {
				return false;
			}
		} catch( Exception e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public TopicOfStudentLabor getTopicForStudentID( int studentId) {
		try{
			Student studentBean = em.find(Student.class, studentId);
			List<TopicOfStudentLabor> topic = studentBean.getTopicOfStudentLabors();
			return topic.get(0);
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
