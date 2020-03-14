package service;

import javax.ejb.Local;

import dto.ProfessorDTO;
import dto.StudentDTO;
import model.User;

@Local
public interface UserBeanLocal {

	public boolean login(String username, String password);
	public boolean registry(String username, String password, String role, String name, String lastname, String email);
	public ProfessorDTO getProfessor(String username);
	public StudentDTO getStudent(String username);
	public User getUser(String username);
	public String issueToken(String username);
	public User checkToken(String token);
	public User setImage(byte[] image, int id);
	public User getUserWithId(int id);
}
