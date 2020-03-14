package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import dto.TopicOfStudentLaborDTO;
import model.Student;
import model.Topic;
import model.TopicOfStudentLabor;

@Mapper
public interface TopicOfStudentLaborMapper {

	TopicOfStudentLaborMapper INSTANCE = Mappers.getMapper(TopicOfStudentLaborMapper.class);
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "studentName", source = "studentBean"),
		@Mapping(target = "topicName", source = "topicBean"),
		@Mapping(target = "commissions", source = "commissions")
	})
	TopicOfStudentLaborDTO toDTO(TopicOfStudentLabor topic);
	
	@Mappings({
		@Mapping(target = "commissions", ignore = true), @Mapping(target = "studentBean", ignore = true), @Mapping(source = "id", target = "topicofstudentlaborid"),
		@Mapping(source = "topic", target = "topicBean"),
		@Mapping(source = "finalDocumentPdf", target = "finalDocumentPdf")
	})
	TopicOfStudentLabor fromDTO(TopicOfStudentLaborDTO topic);

	@Mappings({ @Mapping(target = "description", ignore = true), @Mapping(target = "fieldOfInteresting", ignore = true),
			@Mapping(target = "professorBean", ignore = true), @Mapping(target = "topicId", ignore = true),
			@Mapping(target = "topicName", ignore = true), @Mapping(target = "topicOfStudentLabors", ignore = true) })
	Topic toTopic(String string);

	@Mappings({ @Mapping(target = "image", ignore = true), @Mapping(target = "email", ignore = true), @Mapping(target = "lastname", ignore = true), @Mapping(target = "name", ignore = true), @Mapping(target = "pass", ignore = true), @Mapping(target = "username", ignore = true), @Mapping(target = "espb", ignore = true), @Mapping(target = "nbi", ignore = true),
			@Mapping(target = "semester", ignore = true), 
			@Mapping(target = "userId", ignore = true), @Mapping(target = "studyProgram", ignore = true),
			@Mapping(target = "topicOfStudentLabors", ignore = true) })
	Student toStudent(String string);
}
