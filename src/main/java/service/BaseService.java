package service;

import model.User;

/**
 * @author lqq
 * @date 2020/3/29
 */
public class BaseService {

    User user = new User();

    User show() {
        return user;
    }
}
