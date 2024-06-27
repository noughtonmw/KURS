package course.courseWork.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class TagExceptionHandler {
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(TagNotFoundException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        400L,
                        "У вас нет такого тэга"
                )
        );
    }
}
