package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import dto.ProfessorDTO;
import model.Professor;

@Mapper
public interface ProfessorMapper {
	ProfessorMapper INSTANCE = Mappers.getMapper(ProfessorMapper.class);
	
	@Mappings({	@Mapping(target = "id", source = "userId"),
				@Mapping(target = "username", source = "username"),
				@Mapping(target = "password", source = "pass"), 
				@Mapping(target = "email", source = "email"),
				@Mapping(target = "role", source = "role")})
	public ProfessorDTO toDTO(Professor student);
	
	@Mappings({	@Mapping(target = "image", ignore = true), 
				@Mapping(target = "commissions", ignore = true), 
				@Mapping(target = "topics", ignore = true), 
				@Mapping(source = "id", target = "userId"),
				@Mapping(target = "username", source = "username"),
				@Mapping(source = "password", target = "pass"), 
				@Mapping(target = "email", source = "email"),
				@Mapping(target = "role", source = "role")})
	public Professor fromDTO(ProfessorDTO student);
}
