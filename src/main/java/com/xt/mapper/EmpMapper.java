package com.xt.mapper;

import com.xt.entity.Emp;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmpMapper {
    Emp findByEmpno(Integer empno);
    int update(Emp emp);
    int delete(Integer empno);
    int insert(Emp emp);
    Map<Object, Object> selectEmpByEmpnoReturnMap(Integer empno);

    List<Emp> selectAll();
    @MapKey("ename")
    Map<String, Emp> selectAll2();
    List<Emp> selectEmpByEmpnoAndSal(Emp emp);

    List<Emp> selectEmpByEmpnoAndSal2(@Param("empno") Integer empno, @Param("sal") Double sal);
    List<Emp> selectEmpByEmpnoAndSal3(Map<String, Object> map);

    Emp selectByEmpno(Integer empno);

    Emp selectEmpByStep(Integer empno);
    List<Emp> selectEmpsByDeptno(Integer deptno);
    Emp selectEmpByCondition(Emp emp);
    List<Emp> selectEmpByDeptnos(@Param("deptnos") List<Integer> deptnos);
}
