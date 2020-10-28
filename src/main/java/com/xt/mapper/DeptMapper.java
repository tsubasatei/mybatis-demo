package com.xt.mapper;

import com.xt.entity.Dept;

public interface DeptMapper {
    Dept selectByDeptno(Integer deptno);
    Dept selectDeptByStep(Integer deptno);
    Dept selectDeptByDeptno(Integer deptno);
}
