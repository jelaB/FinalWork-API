package service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.ProfessorDTO;
import dto.StudentDTO;
import mapper.ProfessorMapper;
import mapper.StudentMapper;
import model.Professor;
import model.Student;
import model.User;
import service.UserBeanLocal;
import utilities.PasswordAuthentication;

/**
 * Session Bean implementation class UserAuthentication
 */
@Stateless
@LocalBean
public class UserBean implements UserBeanLocal {
	
	private Logger logger = Logger.getLogger(UserBean.class.getName());

	@PersistenceContext
	EntityManager em;
	
	private static final String ROLE_PROFESSOR = "PROFESSOR";
	private static final String ROLE_STUDENT = "STUDENT";

	/**
     * Default constructor. 
     */
    public UserBean() {
        // TODO Auto-generated constructor stub
    }

	@SuppressWarnings("deprecation")
	@Override
	public boolean login(String username, String password) {
		PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
		logger.log(Level.INFO, "login()");

		User user = getUser(username);
		logger.log(Level.INFO, "Username: " + username);
		logger.log(Level.INFO, "Token: " + user.getPass());
		return user != null && passwordAuthentication.authenticate(password, user.getPass());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean registry(String username, String password, String role, String name, String lastname, String email) {
		PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
		logger.log(Level.INFO, "registry()");
		
		switch(role) {
			case ROLE_PROFESSOR: {
				logger.log(Level.INFO, "Professor registration");
				Professor professor = new Professor(username, passwordAuthentication.hash(password));
				professor.setRole(ROLE_PROFESSOR);
				professor.setName(name);
				professor.setLastname(lastname);
				professor.setEmail(email);
				try {
					em.persist(professor);
					return true;
				} catch( Exception e) {
					logger.log(Level.SEVERE, "Professor registration failed!");
					e.printStackTrace();
					return false;
				}
			}
			case ROLE_STUDENT: {
				logger.log(Level.INFO, "Student registration");
				Student student = new Student(username, passwordAuthentication.hash(password)); 
				student.setRole(ROLE_STUDENT);
				student.setName(name);
				student.setLastname(lastname);
				student.setEmail(email);
				try {
					em.persist(student);
					return true;
				} catch( Exception e) {
					logger.log(Level.SEVERE, "Student registration failed!");
					e.printStackTrace();
					return false;
				}
			}
			default: {
				logger.log(Level.SEVERE, "Registration failed!");
				return false;
			}
		}
	}
	

	@Override
	public User getUser(String username) {
		try {
			Query q = em.createNamedQuery("User.findUser");
			q.setParameter("username", username);
			User user = (User) q.getSingleResult();
			return user;
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String issueToken(String username) {
		User user = getUser(username);
		return user.getPass();
	}

	@Override
	public User checkToken(String token) {
		try {
			Query q = em.createNamedQuery("User.checkToken");
			q.setParameter("pass", token);
			return (User) q.getSingleResult();
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ProfessorDTO getProfessor(String username) {
		ProfessorMapper top= ProfessorMapper.INSTANCE;
		User user = getUser(username);

		Query q = em.createNamedQuery("Professor.getProfessor");
		q.setParameter("id", user.getUserId());
		if(q.getResultList() != null && !q.getResultList().isEmpty()) {
			Professor persisted = (Professor)q.getResultList().get(0);
			return top.toDTO(persisted);
		} else {
			return top.toDTO((Professor)q.getSingleResult());
		}
		
	}
	
	@Override
	public StudentDTO getStudent( String username) {
		StudentMapper top= StudentMapper.INSTANCE;
		User user = getUser(username);
		
		Query q = em.createNamedQuery("Student.getStudent");
		q.setParameter("id", user.getUserId());
		if(q.getResultList() != null && !q.getResultList().isEmpty()) {
			Student s = (Student)q.getResultList().get(0);
			return top.toDto(s);
		} else {
			Student s = (Student) q.getSingleResult();
			return top.toDto(s);
		}
	}

	@Override
	public User setImage(byte[] image, int id) {
		try {
			logger.log(Level.INFO, "setImage()");

			User user = em.find(User.class, id);
			user.setImage(image);
			em.merge(user);
			return user;
		} catch( Exception e) {
			logger.log(Level.SEVERE, "setImage() failed");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User getUserWithId(int id) {
		try {
			return em.find(User.class, id);
		} catch( Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
