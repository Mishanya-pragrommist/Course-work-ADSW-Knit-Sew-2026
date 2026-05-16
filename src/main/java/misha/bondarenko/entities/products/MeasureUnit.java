package misha.bondarenko.entities.products;

import lombok.Getter;

/**
 * Одиниці вимірювання на складі (метри, міліметри, грами, поштучно тощо)
 */
@Getter
public enum MeasureUnit {
    MM("міліметри"),
    CM("сантиметри"),
    M("метри"),
    SQUIRM("квадратний метр"),
    GR("грами"),
    UNIT("поштучно"),
    SKEIN("моток");

    private final String description;
    MeasureUnit(String description) {
        this.description = description;
    }

}
