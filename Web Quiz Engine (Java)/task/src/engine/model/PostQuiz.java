package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostQuiz {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Text is mandatory")
    private String text;
    private List<String> options;
    private List<Integer> answer;

}
