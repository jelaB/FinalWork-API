package mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import dto.TopicDTO;
import model.Topic;

@Mapper
public interface TopicMapper {

	TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

	@Mappings({ 
		@Mapping(source = "topicId", target = "id"), 
		@Mapping(target = "topicName", source = "topicName"),
		@Mapping(target = "topicDescription", source = "description"),
		@Mapping(target = "fieldOfInteresting", source = "fieldOfInteresting"),
		@Mapping(target = "student", source = "topicOfStudentLabors"),
		@Mapping(target = "profName", source = "professorBean")
	})
	TopicDTO toDTO(Topic topic);
	
	@Mappings({ 
		@Mapping(target = "professorBean", ignore = true), @Mapping(target = "topicOfStudentLabors", ignore = true), 
		@Mapping(source = "id", target = "topicId"), 
		@Mapping(target = "topicName", source = "topicName"),
		@Mapping(source = "topicDescription", target = "description"),
		@Mapping(target = "fieldOfInteresting", source = "fieldOfInteresting")
	})
	Topic fromDTO(TopicDTO topicDTO);
	// {
	// if (id == null) {
	// return null;
	// }
	// Topic topic = new Topic();
	// topic.setTopicId(id);
	// return topic;
	// }

}