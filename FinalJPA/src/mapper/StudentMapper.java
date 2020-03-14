package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import dto.StudentDTO;
import model.Student;

@Mapper
public interface StudentMapper {
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	@Mappings({ @Mapping(target = "id", source = "userId"),
				@Mapping(target = "username", source = "username"),
				@Mapping(target = "password", source = "pass"),
				@Mapping(target = "espb", source = "espb"), 
				@Mapping(target = "semester", source = "semester"),
				@Mapping(target = "nbi", source = "nbi"), 
				@Mapping(target = "email", source = "email"),
				@Mapping(target = "studyProgram", source = "studyProgram"),
				@Mapping(target = "role", source = "role"),
				@Mapping(target = "image", source = "image"),
				@Mapping(target = "pdf", source = "pdf"),
				@Mapping(target = "topicName", source = "topicOfStudentLabors")
	})
	public StudentDTO toDto(Student student);
	
	@Mappings({ @Mapping(target = "image", source = "image"), 
				@Mapping(target = "pdf", source = "pdf"),
				@Mapping(target = "topicOfStudentLabors", ignore = true), 
				@Mapping(source = "id", target = "userId"),
				@Mapping(target = "username", source = "username"),
				@Mapping(source = "password", target = "pass"), 
				@Mapping(target = "espb", source = "espb"), 
				@Mapping(target = "semester", source = "semester"),
				@Mapping(target = "nbi", source = "nbi"), 
				@Mapping(target = "email", source = "email"),
				@Mapping(target = "studyProgram", source = "studyProgram"),
				@Mapping(target = "role", source = "role")})
	public Student fromDTo(StudentDTO student);

}
