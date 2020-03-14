package mapper;

import dto.UserDTO;
import java.util.Arrays;
import javax.annotation.Generated;
import model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-09-17T00:27:08+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User student) {
        if ( student == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setPass( student.getPass() );
        userDTO.setName( student.getName() );
        userDTO.setId( student.getUserId() );
        userDTO.setEmail( student.getEmail() );
        userDTO.setLastname( student.getLastname() );
        userDTO.setUsername( student.getUsername() );
        byte[] image = student.getImage();
        if ( image != null ) {
            userDTO.setImage( Arrays.copyOf( image, image.length ) );
        }

        return userDTO;
    }

    @Override
    public User fromDTo(UserDTO student) {
        if ( student == null ) {
            return null;
        }

        User user = new User();

        user.setPass( student.getPass() );
        user.setName( student.getName() );
        user.setUserId( student.getId() );
        user.setEmail( student.getEmail() );
        user.setLastname( student.getLastname() );
        user.setUsername( student.getUsername() );

        return user;
    }
}
