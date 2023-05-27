package pt.itsector.sb.demo.springbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDtoRes {

    private long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String nationality;
    @JsonFormat(timezone="Europe/Lisbon")
    private Date creationon;
    private ZonedDateTime updateon;

}
