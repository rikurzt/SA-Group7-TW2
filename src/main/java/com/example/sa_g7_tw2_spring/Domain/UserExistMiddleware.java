package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.AccountVO;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;

public class UserExistMiddleware extends MiddlewareAuth {

    @Override
    public boolean auth(LoginDataVO vo, AccountVO accountVO) {
        // 模擬驗證使用者不存在
        System.out.println(accountVO.getAccount() == null);
        if (accountVO.getAccount()==null) {
            return false;
        }

        return super.auth(vo,accountVO);
    }
}
