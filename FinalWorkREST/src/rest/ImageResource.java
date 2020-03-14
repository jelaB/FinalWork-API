package rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import mapper.ProfessorMapper;
import mapper.StudentMapper;
import model.Professor;
import model.Student;
import model.User;
import security.Role;
import security.Secured;
import service.TopicBeanLocal;
import service.UserBeanLocal;

/**
 * @author Jela
 *
 */
@RequestScoped
@Path("/image")
@Secured({Role.ADMIN, Role.STUDENT, Role.PROFESSOR})
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ImageResource {
	
	private Logger logger = Logger.getLogger(ImageResource.class.getName());

	@Inject
	private UserBeanLocal userBean;
	
	@Inject
	private TopicBeanLocal professorBean;
	
	@POST
	@Path("/upload/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addImage( MultipartFormDataInput input,
							  @PathParam(value = "id") int id) throws IOException {
		
		logger.log(Level.INFO, "addImage()");
		
//		 List<InputPart> jsonpart = input.getFormDataMap().get("jsondata");
//	     List<InputPart> imgpart = input.getFormDataMap().get("photo");
//	     String jsonStr = input.getFormDataPart("jsondata", String.class,null);

	     InputStream uploadedInputStream = input.getFormDataPart("photo", InputStream.class,null);
	    
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		byte[] buffer = new byte[10240];

        int bytes = 0;
        long file_size = 0;
        while ((bytes = uploadedInputStream.read(buffer)) != -1) {
			output.write(buffer, 0, bytes);
        	file_size += bytes;
        }

		User response = userBean.setImage(output.toByteArray(), id);
		if( response != null) {
			if( response instanceof Student) {
				StudentMapper stude = StudentMapper.INSTANCE;
				return Response.status(Response.Status.OK).header("token", response.getPass()).entity(stude.toDto((Student)response)).build();
			} else {
				ProfessorMapper prof = ProfessorMapper.INSTANCE;
				return Response.status(Response.Status.OK).header("token", response.getPass()).entity(prof.toDTO((Professor)response)).build();
			}
			
		} else {
			logger.log(Level.SEVERE, "addImage() FAILED");
			return Response.status(500).build();
		}
	}
	
	@GET
	@Path("/remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response removeImage( @PathParam(value = "id") int id) throws IOException {
		logger.log(Level.INFO, "removeImage()");

		User response = userBean.setImage(null, id);
		if( response != null) {
			if( response instanceof Student) {
				StudentMapper stude = StudentMapper.INSTANCE;
				return Response.status(Response.Status.OK).header("token", response.getPass()).entity(stude.toDto((Student)response)).build();
			} else {
				ProfessorMapper prof = ProfessorMapper.INSTANCE;
				return Response.status(Response.Status.OK).header("token", response.getPass()).entity(prof.toDTO((Professor)response)).build();
			}
			
		} else {
			logger.log(Level.SEVERE, "removeImage() FAILED");
			return Response.status(500).build();
		}
	}
}
