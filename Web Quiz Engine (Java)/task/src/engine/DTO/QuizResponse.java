package engine.DTO;

import engine.model.Quiz;
import engine.model.QuizOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizResponse {
    private int id;
    private String title;
    private String text;
    private List<String> options = new ArrayList<>();

    public QuizResponse(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.text = quiz.getText();

        List<QuizOption> sortedOptionsList = quiz.getOptions().stream().sorted().toList();
        for (QuizOption option: sortedOptionsList) {
            this.options.add(option.getOption());
        }
    }

    public void addOption(String option) {
        options.add(option);
    }

}
