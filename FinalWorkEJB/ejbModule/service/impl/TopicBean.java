package service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import dto.ProfessorDTO;
import dto.TopicDTO;
import mapper.ProfessorMapper;
import mapper.TopicMapper;
import model.Commission;
import model.Professor;
import model.Student;
import model.Topic;
import model.TopicOfStudentLabor;
import service.TopicBeanLocal;

/**
 * Session Bean implementation class ProfessorBean
 */
@Stateless
@Local
public class TopicBean implements TopicBeanLocal {

	private Logger logger = Logger.getLogger(TopicBean.class.getName());
	private static final String SAVE_FOLDER = "D:\\";
	private static int index = 1;
	
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	UserBean userBean;

	/**
	 * Default constructor.
	 */
	public TopicBean() {
	}

	@Override
	public boolean addTopic(String topicName, String fieldOfInteresting, String description, int profId) {
		try {
			logger.log(Level.INFO, "Creating new topic by professor");
			Professor p = em.find(Professor.class, profId);
			Topic newTopic = new Topic();
			newTopic.setTopicName(topicName);
			newTopic.setFieldOfInteresting(fieldOfInteresting);
			newTopic.setDescription(description);
			newTopic.setProfessorBean(p);
			em.persist(newTopic);
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed creating new topic!", e);
			return false;
		}
	}

	@Override
	public List<TopicDTO> getReservedTopics(int id) {
		try {
			TopicMapper top= TopicMapper.INSTANCE;
			logger.log(Level.INFO, "Retrieving all reservated topics of professor: getReservedTopics()");
			
			List<Topic> mentorOfTopics = new ArrayList<>();
			Query q = em.createNamedQuery("Topic.findByMentor");
			q.setParameter("mentor", em.find(Professor.class, id));
			mentorOfTopics.addAll(q.getResultList());
			
			List<TopicDTO> mentorOfTopicsDTO = new ArrayList<>();
			for( Topic x : mentorOfTopics) {
				mentorOfTopicsDTO.add(top.toDTO(x));
			}
			return mentorOfTopicsDTO;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed retrieving all reservated topics: getReservedTopics()", e);
			return null;
		}
	}
	
	@Override
	public List<TopicDTO> getTopics() {
		try {
			TopicMapper top= TopicMapper.INSTANCE;
			logger.log(Level.INFO, "Retreiving all topics: getTopics() ");
			
			List<Topic> allTopics = new ArrayList<>();
			Query q = em.createNamedQuery("Topic.findAll");
			allTopics.addAll(q.getResultList());
			
			List<TopicDTO> allTopicsDTO = new ArrayList<>();
			for( Topic t: allTopics) {
				allTopicsDTO.add(top.toDTO(t));
			}
			
			return allTopicsDTO;
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TopicDTO> search(String query, boolean registered) {
		try {
			TopicMapper top= TopicMapper.INSTANCE;
			logger.log(Level.INFO, "Search for topics with: " + query + " in search(query)");
			Long numberQuery = null;
			
			String searchQuery = "SELECT t FROM Topic t where "
					+ "t.topicName like :query or "
					+ "t.fieldOfInteresting like :query or "
					+ "t.professorBean.name like :query or "
					+ "t.professorBean.lastname like :query";
			
			if(registered) {
				searchQuery = "SELECT t FROM Topic t "
						+ "INNER join t.topicOfStudentLabors sl WHERE t.topicId = sl.topicBean.topicId and t.professorBean =:mentor"
						+ "and"
						+ "t.topicName like :query or "
						+ "t.fieldOfInteresting like :query or "
						+ "t.professorBean.name like :query or "
						+ "t.professorBean.lastname like :query";
			}
			
			if (query.matches("[0-9]+")){
				numberQuery = Long.parseLong(query);
				searchQuery += " or t.topicId like :query"; 
			}
			
			Query q = em.createQuery(searchQuery);
			
			if(numberQuery != null) {
				q.setParameter("query", "%" + numberQuery + "%");
			} else {
				q.setParameter("query", "%" + query + "%");
			}

			List<Topic> topics = q.getResultList();
			List<TopicDTO> topicsDTO = new ArrayList<>();

			if(topics != null) {
				for(Topic t : topics) {
					topicsDTO.add(top.toDTO(t));
				}
			}
			return topicsDTO;
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TopicDTO> searchReservedTopics( String query, int id) {
		try {
			TopicMapper top= TopicMapper.INSTANCE;
			logger.log(Level.INFO, "Search for topics with: " + query + " in searchReservedTopics(query)");
			Long numberQuery = null;
			
			String searchQuery = "SELECT t FROM Topic t "
						+ "join t.topicOfStudentLabors sl "
						+ "WHERE t.topicId = sl.topicBean.topicId "
						+ "and t.professorBean =:mentor"
						+ "and"
						+ "t.topicName like :query or "
						+ "t.fieldOfInteresting like :query or "
						+ "sl.student.studentName like :query or "
						+ "sl.student.studentLastname like :query ";
			
			if (query.matches("[0-9]+")){
				numberQuery = Long.parseLong(query);
				searchQuery += " or tp.topicId like :query"; 
			} 
			
			Query q = em.createQuery(searchQuery);
			
			if(numberQuery != null) {
				q.setParameter("query", "%" + numberQuery + "%");
			} else {
				q.setParameter("query", "%" + query + "%");
			}
			q.setParameter("mentor", em.find(Professor.class, id));
			List<Topic> topics = q.getResultList();
			
			List<TopicDTO> topicsDTO = new ArrayList<>();
			
			return topicsDTO;
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TopicDTO getTopicWithId(int id) {
		try {
			logger.log(Level.INFO, "Find topic with specified id: " + id);

			TopicMapper top= TopicMapper.INSTANCE;
			Query q = em.createNamedQuery("Topic.getTopic");
			q.setParameter("id", id);
			Topic fromDb = (Topic) q.getSingleResult();
			if ( fromDb !=  null) {
				return top.toDTO(fromDb);
			} else {
				logger.log(Level.SEVERE, "No topic with id " + id + " in database. Returned null.");
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void getPdf(int id) {
		logger.log(Level.INFO, "Pdf retrieving!");
		Student user = em.find(Student.class, id);
		List<TopicOfStudentLabor> topic = user.getTopicOfStudentLabors();
		byte[] buffer = topic.get(0).getFinalDocumentPdf();
		
		try {
			String fileLocation = SAVE_FOLDER + "test" + index +".pdf";
			index++;

			OutputStream fout=new FileOutputStream(fileLocation);
			
			while(buffer!=null && buffer.length > 0) {
	        fout.write(buffer);
			}
			fout.flush();
	        fout.close();

	    } catch (FileNotFoundException e) {
	    	logger.log(Level.SEVERE, "File not found");
	    	e.printStackTrace();
		} catch(IOException ed) {
		    logger.log(Level.SEVERE, "IO Exception");
		    ed.printStackTrace();
	    }
	}

	@Override
	public List<ProfessorDTO> getProfessors(int id) {
		try {
			logger.log(Level.INFO, "getProfessors");
			
			ProfessorMapper map = ProfessorMapper.INSTANCE;
			
			Query q = em.createNamedQuery("Professor.findAll");
			List<Professor> p = q.getResultList();
			Iterator i = p.iterator();
			while(i.hasNext()) {
				Professor cur = (Professor) i.next();
				if( cur.getUserId() == id) {
					i.remove();
				}
			}
			
			List<ProfessorDTO> pD = new ArrayList<>();
			for( Professor x : p ) {
				pD.add(map.toDTO(x));
			}
			return pD;
		} catch( Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "getProfessors error");
			return null;
		}
	}

	@Override
	public boolean createCommission(Professor p, Topic t) {
		try { // for every professor create one commission entry
			logger.log(Level.INFO, "create commission");
			
			  Hibernate.initialize(p.getCommissions());
		      Hibernate.initialize(p.getTopics());
		      Hibernate.initialize(t.getTopicOfStudentLabors());
			TopicOfStudentLabor studentTop = t.getTopicOfStudentLabors().get(0);
		      Hibernate.initialize(studentTop.getCommissions());

			
			
			logger.log(Level.INFO, "create commission");
			Commission firstC = new Commission();
			firstC.setProfessorBean(p);
			firstC.setTopicOfStudentLaborBean(studentTop);
			em.persist(firstC);
			p.addCommission(firstC);
			em.merge(p);
			studentTop.addCommission(firstC);
			em.merge(studentTop);
			
//			logger.log(Level.INFO, "create commission: second");
//			Commission secondC = new Commission();
//			secondC.setTopicOfStudentLaborBean(studentTop);
//			secondC.setProfessorBean(secondP);
//			em.persist(secondC);
//			secondP.addCommission(secondC);
//			studentTop.addCommission(firstC);
//			
//			logger.log(Level.INFO, "create commission: mentor");
//			Commission mentorC = new Commission();
//			mentorC.setProfessorBean(mentorP);
//			mentorC.setTopicOfStudentLaborBean(studentTop);

			return true;
		} catch( Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "create commission failed");
			return false;
		}
	}

	@Override
	public Topic getTopicById(int id) {
		try {
			return em.find(Topic.class, id);
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Professor getProfessorById(int id) {
		try {
			return em.find(Professor.class, id);
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
//	@Override
//	public List<Student> getMentorStudents() {
//		try {
//			logger.log(Level.INFO, "Retrieving all students: getMentorStudents()");
//			List<Student> studentsOfProfessor = new ArrayList<>();
//			Query q = em.createQuery("select s.student from TopicOfStudent s INNER JOIN Topic t ON s.topic=t.topic_id");
//			studentsOfProfessor.addAll(q.getResultList());
//			return studentsOfProfessor;
//		} catch (Exception e) {
//			logger.log(Level.SEVERE, "Failed retrieving students: getMentorStudents()", e);
//			return null;
//		}
//	}

}
