package engine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "option")
public class QuizOption implements Comparable<QuizOption> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer index;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private String option;

    public QuizOption(Integer index, Quiz quiz, String option) {
        this.index = index;
        this.quiz = quiz;
        this.option = option;
    }

    @Override
    public int compareTo(QuizOption quizOption) {
        return this.index.compareTo(quizOption.getIndex());
    }
}
