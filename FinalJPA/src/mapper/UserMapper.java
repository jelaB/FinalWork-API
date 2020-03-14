package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import dto.UserDTO;
import model.User;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;


@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	static final String ROLE_PROFESSOR = "PROFESSOR";
	static final String ROLE_STUDENT = "STUDENT";
	static final String ROLE_ADMIN = "ADMIN";

	
	@Mappings({ @Mapping(source = "userId", target = "id"), 
				@Mapping(target = "role", ignore = true),
				@Mapping(source = "lastname", target = "lastname"),
				@Mapping(source = "username", target = "username"),
				@Mapping(source = "name", target = "name"), 
				@Mapping(source = "pass", target = "pass"), 
				@Mapping(source = "email", target = "email")
	})
	public UserDTO toDto(User student);
	
	@Mappings({ @Mapping(target = "userId", source = "id"), 
				@Mapping(target = "image", ignore = true), 
				@Mapping(target = "lastname", source = "lastname"),
				@Mapping(target = "username", source = "username"),
				@Mapping(target = "name", source = "name"), 
				@Mapping(target = "pass", source = "pass"), 
				@Mapping(target = "email", source = "email")
})
	public User fromDTo(UserDTO student);
}
