 package security;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import model.Professor;
import model.Student;
import model.User;
import service.UserBeanLocal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	private Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());

	private static final String REALM = "example";
	private static final String AUTHENTICATION_SCHEME = "basic";
	
	private static final String ROLE_ADMIN = "ADMIN";	
	private static final String ROLE_STUDENT = "STUDENT";
	private static final String ROLE_PROFESSOR = "PROFESSOR";

	@Inject
	UserBeanLocal authenticationBean;

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.log(Level.INFO, "authorization filter");

		// Get the Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Validate the Authorization header
		if (!isTokenBasedAuthentication(authorizationHeader)) {
			abortWithUnauthorized(requestContext);
			return;
		}

		// Extract the token from the Authorization header
		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
		
		// Validate the token
		User tokenOwner = authenticationBean.checkToken(token);
		if (tokenOwner == null) {
			abortWithUnauthorized(requestContext);
		}
		
		// Get the resource class which matches with the requested URL
		// Extract the roles declared by it
		Class<?> resourceClass = resourceInfo.getResourceClass();
		List<Role> classRoles = extractRoles(resourceClass);

		// Get the resource method which matches with the requested URL
		// Extract the roles declared by it
		Method resourceMethod = resourceInfo.getResourceMethod();
		List<Role> methodRoles = extractRoles(resourceMethod);

		try {
			logger.log(Level.INFO, "role check");
			// Check if the user is allowed to execute the method
			// The method annotations override the class annotations
			if (methodRoles.isEmpty()) {
				checkPermissions(classRoles, tokenOwner);
			} else {
				checkPermissions(methodRoles, tokenOwner);
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "no permission");
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
		}

	}

	// Extract the roles from the annotated element
	private List<Role> extractRoles(AnnotatedElement annotatedElement) {
		if (annotatedElement == null) {
			return new ArrayList<Role>();
		} else {
			Secured secured = annotatedElement.getAnnotation(Secured.class);
			if (secured == null) {
				return new ArrayList<Role>();
			} else {
				Role[] allowedRoles = secured.value();
				return Arrays.asList(allowedRoles);
			}
		}
	}

	private void checkPermissions(List<Role> allowedRoles, User tokenOwner) throws Exception {
		logger.log(Level.INFO, "check permission");
		if( tokenOwner instanceof Student && !allowedRoles.contains(Role.STUDENT)) {
			throw new Exception();
		}
		else if( tokenOwner instanceof Professor && !allowedRoles.contains(Role.PROFESSOR)) {
			throw new Exception();
		} 
//		else if( !allowedRoles.contains(Role.ADMIN)) {
//			throw new Exception();
//		}

	}

	private boolean isTokenBasedAuthentication(String authorizationHeader) {

		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Bearer" plus a
		// whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME+ " ");
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		logger.log(Level.SEVERE, "abort with unauthorized");

		// Abort the filter chain with a 401 status code response
		// The WWW-Authenticate header is sent along with the response
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
				.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());
	}

}
