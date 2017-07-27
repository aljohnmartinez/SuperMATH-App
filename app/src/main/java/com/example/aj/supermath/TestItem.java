package com.example.aj.supermath;

import java.io.Serializable;

/**
 * Created by AJ on 7/8/2016.
 */
public class TestItem implements Serializable {
    String question, choice1, choice2, choice3, choice4, answer;

    public TestItem (String question, String choice1, String choice2, String choice3, String choice4, String answer) {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
    }
}
