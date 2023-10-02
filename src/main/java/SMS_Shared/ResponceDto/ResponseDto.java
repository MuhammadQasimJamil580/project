package SMS_Shared.ResponceDto;

import SMS_Shared.DTO.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * This class is used to create custom Response.
 * The type of data variable will change.
 * @param <T> T can be any return type in which we want the response.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T>{
    private T data;
    private String message;
    private String statusCode;
}
