package engine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizWithID {
    private int id;
    private String title;
    private String text;
    private List<String> options = new ArrayList<>();

    public QuizWithID(Quiz quiz) {
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
