package rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.ProfessorDTO;
import dto.TopicDTO;
import mapper.ProfessorMapper;
import model.Professor;
import model.Topic;
import model.User;
import security.Role;
import security.Secured;
import service.TopicBeanLocal;
import service.UserBeanLocal;

/**
 * REST Endpoint for PROFESSOR Role actions.
 * @author Jela Babic
 *
 */
@RequestScoped
@Secured({Role.ADMIN, Role.PROFESSOR})
@Path("/topics")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ProfessorResource {
	
	@Inject
	private TopicBeanLocal professorBean;
	@Inject
	private UserBeanLocal userBean;
	
	@GET
	@Path("/find-topic/{id}")
	@Secured({Role.ADMIN, Role.PROFESSOR, Role.STUDENT})
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response getTopic(@PathParam("id") int id) {
		return Response.status(200).entity(professorBean.getTopicWithId(id)).build();
	}
	
	@GET
	@Path("/test")
//	@Secured({Role.ADMIN, Role.PROFESSOR})
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public String test() {
		return "Radi";
	}

	@GET
	@Path("/all")
	@Secured({Role.ADMIN, Role.PROFESSOR, Role.STUDENT})
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getAllTopics() {
		return Response.status(200).entity(professorBean.getTopics()).build();
	}
	
	@GET
	@Path("/all/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getTopicWithId(@PathParam(value = "id") int id) {
		return Response.status(200).entity(professorBean.getTopicWithId(id)).build();
	}

	@GET
	@Secured({Role.STUDENT, Role.PROFESSOR})
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response searchTopics(@QueryParam(value = "query") String query) {
		return Response.status(200).entity(professorBean.search(query, false)).build();
	}

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addTopic(@FormParam("name") String topicName,
			@FormParam("field") String fieldOfInteresting, @FormParam("dec") String description, @FormParam("profID") int profId) {
		return Response.ok().entity(professorBean.addTopic(topicName, fieldOfInteresting, description, profId)).build();
	}

	@GET
	@Path("/reserved")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getReservedTopics(@QueryParam(value = "id") int id) {
		return Response.status(200).entity(professorBean.getReservedTopics(id)).build();
	}

	@GET
	@Path("/reserved-search")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response searchReservedTopics(@QueryParam(value = "query") String query, @QueryParam(value = "id") int id) {
		return Response.status(200).entity(professorBean.searchReservedTopics(query, id)).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public List<TopicDTO> getMentorTopics(@QueryParam(value = "mentorName") String mentorName) {
		return null;
	}
	
	@GET
	@Path("/download/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getPdfFile(@PathParam("id") int id) throws FileNotFoundException, IOException {
		professorBean.getPdf(id);
		return Response.ok().build();
	}
	
	@GET
	@Path("/find/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getStudent( @PathParam(value="id") int id) {
		ProfessorMapper stude = ProfessorMapper.INSTANCE;
		User loggedUser = userBean.getUserWithId(id);
		return Response.status(200).entity(stude.toDTO((Professor)loggedUser)).build();
	}
	
	@GET
	@Path("/professors")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getProfessors( @QueryParam(value = "id") int id) {
		List<ProfessorDTO> professorsDto = professorBean.getProfessors(id);
		return Response.status(200).entity(professorsDto).build();
	}
	
	@POST
	@Path("/commission")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response setCommission( @FormParam("topicId") int topicId,
							   	   @FormParam("first") int first,
							   	   @FormParam("second") int second,
							   	   @FormParam("mentor") int mentor) {
		
		Topic t = professorBean.getTopicById( topicId);
		Professor firstP = professorBean.getProfessorById( first);
		Professor secondP =  professorBean.getProfessorById(  second);
		Professor mentorP =  professorBean.getProfessorById(  mentor);
		
		boolean firstC = professorBean.createCommission(firstP,t);
		boolean secondC = professorBean.createCommission(secondP, t);
		boolean mentorC = professorBean.createCommission(mentorP, t);
		
		
		if( firstC && secondC && mentorC) {
			Response.ok().build();
		}
		return Response.status(500).build();
	}
	// @POST
	// @Produces({MediaType.APPLICATION_JSON})
	// @Consumes({MediaType.APPLICATION_JSON})
	// public List<Student> getMentorStudents(@QueryParam(value="mentorName")
	// String mentorName) {
	// return null;
	// }
}
