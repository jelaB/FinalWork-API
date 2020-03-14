package mapper;

import dto.TopicOfStudentLaborDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import model.Commission;
import model.Student;
import model.Topic;
import model.TopicOfStudentLabor;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-17T00:27:07+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class TopicOfStudentLaborMapperImpl implements TopicOfStudentLaborMapper {

    @Override
    public TopicOfStudentLaborDTO toDTO(TopicOfStudentLabor topic) {
        if ( topic == null ) {
            return null;
        }

        TopicOfStudentLaborDTO topicOfStudentLaborDTO = new TopicOfStudentLaborDTO();

        topicOfStudentLaborDTO.setTopicName( topic.getTopicBean() );
        List<Commission> list = topic.getCommissions();
        if ( list != null ) {
            topicOfStudentLaborDTO.setCommissions( new ArrayList<Commission>( list ) );
        }
        else {
            topicOfStudentLaborDTO.setCommissions( null );
        }
        topicOfStudentLaborDTO.setStudentName( topic.getStudentBean() );
        byte[] finalDocumentPdf = topic.getFinalDocumentPdf();
        if ( finalDocumentPdf != null ) {
            topicOfStudentLaborDTO.setFinalDocumentPdf( Arrays.copyOf( finalDocumentPdf, finalDocumentPdf.length ) );
        }

        return topicOfStudentLaborDTO;
    }

    @Override
    public TopicOfStudentLabor fromDTO(TopicOfStudentLaborDTO topic) {
        if ( topic == null ) {
            return null;
        }

        TopicOfStudentLabor topicOfStudentLabor = new TopicOfStudentLabor();

        topicOfStudentLabor.setTopicBean( topicOfStudentLaborDTOToTopic( topic ) );
        byte[] finalDocumentPdf = topic.getFinalDocumentPdf();
        if ( finalDocumentPdf != null ) {
            topicOfStudentLabor.setFinalDocumentPdf( Arrays.copyOf( finalDocumentPdf, finalDocumentPdf.length ) );
        }
        topicOfStudentLabor.setTopicofstudentlaborid( topic.getId() );

        return topicOfStudentLabor;
    }

    @Override
    public Topic toTopic(String string) {
        if ( string == null ) {
            return null;
        }

        Topic topic = new Topic();

        return topic;
    }

    @Override
    public Student toStudent(String string) {
        if ( string == null ) {
            return null;
        }

        Student student = new Student();

        return student;
    }

    protected Topic topicOfStudentLaborDTOToTopic(TopicOfStudentLaborDTO topicOfStudentLaborDTO) {
        if ( topicOfStudentLaborDTO == null ) {
            return null;
        }

        Topic topic = new Topic();

        topic.setTopicName( topicOfStudentLaborDTO.getTopicName() );

        return topic;
    }
}
