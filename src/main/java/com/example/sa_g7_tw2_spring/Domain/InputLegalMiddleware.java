package com.example.sa_g7_tw2_spring.Domain;

import com.example.sa_g7_tw2_spring.ValueObject.AccountVO;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.ValueObject.UserVO;
import org.springframework.util.ObjectUtils;

public class InputLegalMiddleware extends MiddlewareAuth {


    @Override
    public boolean auth(LoginDataVO vo, AccountVO accountVO) {
        // 模擬驗證輸入不合法
        System.out.println(vo.getAccount()==null || vo.getPassword()==null);
        if (vo.getAccount()==null || vo.getPassword()==null) {

            return false;
        }

        return super.auth(vo,accountVO);
    }
}
