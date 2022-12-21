package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.AccountVO;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import com.example.sa_g7_tw2_spring.ValueObject.ValueObject;
import org.springframework.util.ObjectUtils;

public abstract class MiddlewareAuth {
    //套chain of responsibility

    private MiddlewareAuth next;
    public MiddlewareAuth setNext(MiddlewareAuth next) {
        this.next = next;
        return this;
    }
    public boolean auth(LoginDataVO vo, AccountVO vo2) {
        if (ObjectUtils.isEmpty(next)) {
            return true;
        }
        return next.auth(vo,vo2);
    }

}
