package com.example.sa_g7_tw2_spring.Domain.Chain;

import com.example.sa_g7_tw2_spring.ValueObject.AccountVO;
import com.example.sa_g7_tw2_spring.ValueObject.LoginDataVO;
import com.example.sa_g7_tw2_spring.utils.Iterator.MD5;

public class PasswordCorrectMiddleware extends  MiddlewareAuth{
    @Override
    public boolean auth(LoginDataVO vo, AccountVO accountVO) {
        System.out.println(!MD5.encoding(vo.getPassword()).equals(accountVO.getPassword()));
        System.out.println(MD5.encoding(vo.getPassword()));
        System.out.println(accountVO.getPassword());
        // 模擬驗證使用者不存在
        if (!MD5.encoding(vo.getPassword()).equals(accountVO.getPassword())) {
            return false;
        }

        return super.auth(vo,accountVO);
    }
}
