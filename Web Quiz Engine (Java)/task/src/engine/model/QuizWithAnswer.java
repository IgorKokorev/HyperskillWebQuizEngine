package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizWithAnswer {
    private String title;
    private String text;
    private List<String> options = new ArrayList<>();
    private int answer;

    public void addOption(String option) {
        options.add(option);
    }
}
