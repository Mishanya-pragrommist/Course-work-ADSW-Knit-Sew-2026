package misha.bondarenko.enums;

import lombok.Getter;

/**
 * Одиниці вимірювання на складі (метри, міліметри, грами, поштучно тощо)
 */
@Getter
public enum MeasureUnit {
    MM("міліметри"),
    CM("сантиметри"),
    METERS("метри"),
    SQUIRM("квадратний метр"),
    GR("грами"),
    UNIT("поштучно"),
    KG("кілограм"),
    SKEIN("моток");

    private final String description;
    MeasureUnit(String description) {
        this.description = description;
    }

}
