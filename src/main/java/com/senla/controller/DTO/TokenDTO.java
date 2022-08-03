package com.senla.controller.DTO;

import com.senla.model.Token;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String login;
    private String value;

    public static TokenDTO toTokenDTO(Token token) {
        return new TokenDTO(token.getGuest().getLogin(), token.getValue());
    }

}
