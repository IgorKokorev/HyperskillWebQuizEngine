package engine.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostAnswersRequest {
    private List<Integer> answer;
}
