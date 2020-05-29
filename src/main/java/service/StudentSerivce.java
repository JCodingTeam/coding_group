package service;

import model.Student;

/**
 * @author lqq
 * @date 2020/3/29
 */
public class StudentSerivce extends BaseService {

    Student student = new Student();

    @Override
    Student show() {
        return student;
    }
}
