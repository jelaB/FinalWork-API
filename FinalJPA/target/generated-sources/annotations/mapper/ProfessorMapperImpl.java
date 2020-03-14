package mapper;

import dto.ProfessorDTO;
import java.util.Arrays;
import javax.annotation.Generated;
import model.Professor;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-17T00:29:00+0200",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 1.2.100.v20160418-1457, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class ProfessorMapperImpl implements ProfessorMapper {

    @Override
    public ProfessorDTO toDTO(Professor student) {
        if ( student == null ) {
            return null;
        }

        ProfessorDTO professorDTO = new ProfessorDTO();

        professorDTO.setPassword( student.getPass() );
        professorDTO.setRole( student.getRole() );
        professorDTO.setId( student.getUserId() );
        professorDTO.setEmail( student.getEmail() );
        professorDTO.setUsername( student.getUsername() );
        byte[] image = student.getImage();
        if ( image != null ) {
            professorDTO.setImage( Arrays.copyOf( image, image.length ) );
        }
        professorDTO.setLastname( student.getLastname() );
        professorDTO.setName( student.getName() );

        return professorDTO;
    }

    @Override
    public Professor fromDTO(ProfessorDTO student) {
        if ( student == null ) {
            return null;
        }

        Professor professor = new Professor();

        professor.setRole( student.getRole() );
        professor.setPass( student.getPassword() );
        professor.setUserId( student.getId() );
        professor.setEmail( student.getEmail() );
        professor.setUsername( student.getUsername() );
        professor.setLastname( student.getLastname() );
        professor.setName( student.getName() );

        return professor;
    }
}
