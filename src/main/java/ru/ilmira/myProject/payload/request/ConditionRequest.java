package ru.ilmira.myProject.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;
import ru.ilmira.myProject.persist.model.EUserCondition;
import ru.ilmira.myProject.validation.ConditionTypeSubset;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionRequest {

    @NotNull
    @Min(value = 1)
    private Long userId;

    @NotNull
    @ConditionTypeSubset(anyOf = {EUserCondition.ACTIVE, EUserCondition.NOT_ACTIVE, EUserCondition.BANNED})
    private EUserCondition condition;
}
