package nl.hu.cisq1.lingo.trainer.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class NewGameDTO {
    @NotNull
    private Long gameId;
}
