/**
 * 
 */
package rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import dto.TopicOfStudentLaborDTO;
import mapper.StudentMapper;
import mapper.TopicOfStudentLaborMapper;
import model.Student;
import model.Topic;
import model.TopicOfStudentLabor;
import model.User;
import security.Role;
import security.Secured;
import service.StudentBeanLocal;
import service.UserBeanLocal;

/**
 * @author Jela
 *
 */
@RequestScoped
@Path("/exams")
@Secured({Role.ADMIN, Role.STUDENT})
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class StudentResource {

	@Inject
	private UserBeanLocal userBean;
	@Inject
	private StudentBeanLocal studentBean;
	
	private static final String SAVE_FOLDER = "Documents";


	@POST
	@Path("/submit")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addLaborFile( MultipartFormDataInput input, @QueryParam("id") int studentTopicId) throws IOException {
		String fileLocation = SAVE_FOLDER + "/test.pdf";

		TopicOfStudentLabor studentTopic = studentBean.getTopicForStudentID(studentTopicId);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		OutputStream out = new FileOutputStream(new File(fileLocation));

		byte[] buffer = new byte[10240];
		


		try {
			InputStream result;
			if (input.getParts().size() == 1) {
				InputPart filePart = input.getParts().iterator().next();
				result = filePart.getBody(InputStream.class, null);
			} else {
				result = input.getFormDataPart("file", InputStream.class, null);
			}
			
			if (result == null) {
				throw new IllegalArgumentException("Can't find a valid 'file' part in the multipart request");
			}

			for (int length = 0; (length =  result.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
	        	out.write(buffer, 0, length);

			}
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while reading multipart request", e);
		}

		studentTopic.setFinalDocumentPdf(output.toByteArray());
		TopicOfStudentLabor topic = studentBean.assignTopic(studentTopic, true);
		return Response.status(200).entity(TopicOfStudentLaborMapper.INSTANCE.toDTO(topic)).build();
	}

	@POST
	@Path("/assignTopic")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response assigneTopic(@FormParam("topicId") int topicId, @FormParam("studentId") int studentId) {
		
		// uzmi studentov rad
		// promeni mu temu
		// azuriraj
		// inace 
		// naoravi nov rad
		if ( studentBean.hasTopic(studentId)) {
			TopicOfStudentLabor studentTopic = studentBean.getTopicForStudentID(studentId);
			Topic newTopic = studentBean.getTopic(topicId);
			studentTopic.setTopicBean(newTopic);
			studentTopic.setFinalDocumentPdf(null);
			studentTopic = studentBean.assignTopic(studentTopic, true);
			TopicOfStudentLaborDTO dtoStudentTopic = studentBean.getStudentTopic(studentTopic.getTopicofstudentlaborid());
			return Response.status(200).entity(dtoStudentTopic).build();
		} else {
			Student student = studentBean.getStudent(studentId);
			Topic topic = studentBean.getTopic(topicId);
			TopicOfStudentLabor studentTopic = new TopicOfStudentLabor();
			studentTopic.setStudentBean(student);
			studentTopic.setTopicBean(topic);
			studentTopic = studentBean.assignTopic(studentTopic, false);
			TopicOfStudentLaborDTO dtoStudentTopic = studentBean.getStudentTopic(studentTopic.getTopicofstudentlaborid());
			return Response.status(200).entity(dtoStudentTopic).build();
		}
	}

	@GET
	@Path("/availableTopics")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getAvailableTopics() {
		return Response.status(200).entity(studentBean.getAvailableTopics()).build();
	}

	@GET
	@Path("/find/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getStudent( @PathParam(value="id") int id) {
		StudentMapper stude = StudentMapper.INSTANCE;
		User loggedUser = userBean.getUserWithId(id);
		return Response.status(200).entity(stude.toDto((Student)loggedUser)).build();
	}
	
	@GET
	@Path("/getMyTopic/{id}")
	@Secured({Role.PROFESSOR, Role.STUDENT})
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getMyTopic( @PathParam(value = "id") int id) {
		TopicOfStudentLaborMapper map = TopicOfStudentLaborMapper.INSTANCE;
		TopicOfStudentLabor studentTopic = studentBean.getTopicForStudentID(id);
		if( studentTopic != null) {
			TopicOfStudentLaborDTO dto = map.toDTO(studentTopic);
			return Response.status(Response.Status.ACCEPTED).entity(dto).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}

