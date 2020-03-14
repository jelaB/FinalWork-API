package mapper;

import dto.StudentDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import model.Student;
import model.TopicOfStudentLabor;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-17T00:27:08+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        byte[] image = student.getImage();
        if ( image != null ) {
            studentDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        studentDTO.setRole( student.getRole() );
        studentDTO.setStudyProgram( student.getStudyProgram() );
        studentDTO.setEspb( student.getEspb() );
        studentDTO.setPassword( student.getPass() );
        byte[] pdf = student.getPdf();
        if ( pdf != null ) {
            studentDTO.setPdf( Arrays.copyOf( pdf, pdf.length ) );
        }
        studentDTO.setNbi( student.getNbi() );
        List<TopicOfStudentLabor> list = student.getTopicOfStudentLabors();
        if ( list != null ) {
            studentDTO.setTopicName( new ArrayList<TopicOfStudentLabor>( list ) );
        }
        else {
            studentDTO.setTopicName( null );
        }
        studentDTO.setSemester( student.getSemester() );
        studentDTO.setId( student.getUserId() );
        studentDTO.setEmail( student.getEmail() );
        studentDTO.setUsername( student.getUsername() );
        studentDTO.setName( student.getName() );
        studentDTO.setLastname( student.getLastname() );

        return studentDTO;
    }

    @Override
    public Student fromDTo(StudentDTO student) {
        if ( student == null ) {
            return null;
        }

        Student student1 = new Student();

        byte[] image = student.getImage();
        if ( image != null ) {
            student1.setImage( Arrays.copyOf( image, image.length ) );
        }
        student1.setRole( student.getRole() );
        student1.setPass( student.getPassword() );
        student1.setStudyProgram( student.getStudyProgram() );
        student1.setUserId( student.getId() );
        student1.setEspb( student.getEspb() );
        byte[] pdf = student.getPdf();
        if ( pdf != null ) {
            student1.setPdf( Arrays.copyOf( pdf, pdf.length ) );
        }
        student1.setNbi( student.getNbi() );
        student1.setSemester( student.getSemester() );
        student1.setEmail( student.getEmail() );
        student1.setUsername( student.getUsername() );
        student1.setLastname( student.getLastname() );
        student1.setName( student.getName() );

        return student1;
    }
}
