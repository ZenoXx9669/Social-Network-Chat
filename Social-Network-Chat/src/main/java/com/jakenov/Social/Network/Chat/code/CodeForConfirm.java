package com.jakenov.Social.Network.Chat.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@AllArgsConstructor
@Getter
@Setter
public class CodeForConfirm {
    private short code;
    public CodeForConfirm(){
        code = getConfirmCode();
    }

    private short getConfirmCode(){
        Random random = new Random();
        short num;
        do {
            num = (short) Math.abs(random.nextInt());
        } while (num <= 9999);
        this.code = num;
        return this.code;
    }
}
