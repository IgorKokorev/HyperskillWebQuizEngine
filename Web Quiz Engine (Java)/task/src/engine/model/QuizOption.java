package engine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quiz_option")
public class QuizOption implements Comparable<QuizOption> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer index;

    private String option;

    public QuizOption(Integer index, String option) {
        this.index = index;
        this.option = option;
    }

    @Override
    public int compareTo(QuizOption quizOption) {
        return this.index.compareTo(quizOption.getIndex());
    }
}
