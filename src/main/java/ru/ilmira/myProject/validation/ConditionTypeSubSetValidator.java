package ru.ilmira.myProject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.ilmira.myProject.persist.model.EUserCondition;

import java.util.Arrays;

public class ConditionTypeSubSetValidator implements ConstraintValidator<ConditionTypeSubset, EUserCondition> {

    private EUserCondition[] subset;

    @Override
    public void initialize(ConditionTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(EUserCondition value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset)
                .contains(value);
    }
}
