package security;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.StudentDTO;
import dto.UserDTO;
import mapper.ProfessorMapper;
import mapper.StudentMapper;
import mapper.UserMapper;
import model.Professor;
import model.Student;
import model.User;
import service.UserBeanLocal;

/**
 * REST endpoint for user authentication.
 * @author Jela Babic
 *
 */
@Path("/authentication")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class AuthenticationEndpoint {
	private Logger logger = Logger.getLogger(AuthenticationEndpoint.class.getName());

	@Inject
	private UserBeanLocal authenticationBean;
	
	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {
		
		logger.log(Level.INFO, "authenticateUser()");
		try {

			// Authenticate the user using the credentials provided
			authenticationBean.login(username, password);

			// Issue a token for the user
			String token = authenticationBean.issueToken(username);
			
			// Get authenticated user from db
			User loggedUser = authenticationBean.getUser(username);
			
			StudentMapper stude = StudentMapper.INSTANCE;
			ProfessorMapper prof = ProfessorMapper.INSTANCE;
			UserMapper usr = UserMapper.INSTANCE;
			if( loggedUser instanceof Student) {
				StudentDTO dto = stude.toDto((Student)loggedUser);
				return Response.status(Response.Status.OK).header("token", token).entity(dto).build();
			} else if( loggedUser instanceof Professor) {
				return Response.status(Response.Status.OK).header("token", token).entity(prof.toDTO((Professor)loggedUser)).build();
			} else {
				UserDTO dto = usr.toDto(loggedUser);
				dto.setRole("ADMIN");
				return Response.status(Response.Status.OK).header("token", token).entity(dto).build();
			}
			
			// Return the token on the response
			

		} catch (Exception e) {
			logger.log(Level.SEVERE, "authenticateUser() - FORBIDDEN user access");
			e.printStackTrace();
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}
	
	@POST
	@Path("/registry")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(@FormParam("username") String username, 
			   				   @FormParam("role") String role, 
							   @FormParam("password") String password,
							   @FormParam("name") String name,
							   @FormParam("lastname") String lastname, 
							   @FormParam("email") String email) {
		logger.log(Level.INFO, "authenticateUser() - Create new user");
		
		try {
			authenticationBean.registry(username, password, role, name, lastname, email);
//			String token = authenticationBean.issueToken(username);
//			return Response.status(Response.Status.OK).header("token", token).build();

			return Response.ok().build();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "authenticateUser() - Failed user creation!");
			e.printStackTrace();
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}
}
