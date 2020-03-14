package mapper;

import dto.TopicDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import model.Topic;
import model.TopicOfStudentLabor;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-17T00:27:08+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class TopicMapperImpl implements TopicMapper {

    @Override
    public TopicDTO toDTO(Topic topic) {
        if ( topic == null ) {
            return null;
        }

        TopicDTO topicDTO = new TopicDTO();

        topicDTO.setProfName( topic.getProfessorBean() );
        List<TopicOfStudentLabor> list = topic.getTopicOfStudentLabors();
        if ( list != null ) {
            topicDTO.setStudent( new ArrayList<TopicOfStudentLabor>( list ) );
        }
        else {
            topicDTO.setStudent( null );
        }
        topicDTO.setTopicName( topic.getTopicName() );
        topicDTO.setId( topic.getTopicId() );
        topicDTO.setFieldOfInteresting( topic.getFieldOfInteresting() );
        topicDTO.setTopicDescription( topic.getDescription() );

        return topicDTO;
    }

    @Override
    public Topic fromDTO(TopicDTO topicDTO) {
        if ( topicDTO == null ) {
            return null;
        }

        Topic topic = new Topic();

        topic.setTopicId( topicDTO.getId() );
        topic.setTopicName( topicDTO.getTopicName() );
        topic.setDescription( topicDTO.getTopicDescription() );
        topic.setFieldOfInteresting( topicDTO.getFieldOfInteresting() );

        return topic;
    }
}
