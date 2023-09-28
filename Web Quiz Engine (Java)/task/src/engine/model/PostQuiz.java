package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostQuiz {
    private String title;
    private String text;
    private List<String> options;
    private List<Integer> answers;

//    public void addOption(String option) {
//        options.add(option);
//    }
}
