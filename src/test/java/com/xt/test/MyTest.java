package com.xt.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.xt.entity.Dept;
import com.xt.entity.Dog;
import com.xt.entity.Emp;
import com.xt.mapper.DeptMapper;
import com.xt.mapper.DogMapper;
import com.xt.mapper.EmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test () {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.findByEmpno(7369);
        System.out.println(emp);
//        Emp emp = new Emp();
//        emp.setEmpno(7350);
//        emp.setEname("Sanae");
////        mapper.insert(emp);
//        emp.setEname("Tom");
////        mapper.update(emp);
//        mapper.delete(7350);
        sqlSession.close();
    }

    @Test
    public void testSelectAll () {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
//        List<Emp> list = mapper.selectAll();
//        for (Emp emp : list) {
//            System.out.println(emp);
//        }

        Map<String, Emp> map = mapper.selectAll2();
        System.out.println(map);
        sqlSession.close();
    }

    @Test
    public void testSelectCondition () {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("empno", 7369);
        map.put("sal", 1000);
        List<Emp> emps = mapper.selectEmpByEmpnoAndSal3(map);
        for (Emp emp : emps) {
            System.out.println(emp);
        }
        sqlSession.close();
    }

    @Test
    public void testSelectMap () {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Map<Object, Object> map = mapper.selectEmpByEmpnoReturnMap(7369);
        map.forEach((k, v) -> {
            System.out.println(k + " ==> " + v);
        });
        sqlSession.close();
    }

    @Test
    public void testDog() throws IOException {

        //获取与数据库相关的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取对应的映射接口对象
        DogMapper mapper = sqlSession.getMapper(DogMapper.class);
        //执行具体的sql语句
        Dog dog = mapper.selectById(1);
        System.out.println(dog);
        //关闭会话
        sqlSession.close();
    }

    @Test
    public void test01() {

        //获取与数据库相关的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取对应的映射接口对象
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.selectByEmpno(7369);
        System.out.println(emp);
        //关闭会话
        sqlSession.close();
    }

    @Test
    public void test02() throws IOException {
        //获取与数据库相关的会话
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取对应的映射接口对象
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = mapper.selectByDeptno(10);
        System.out.println(dept);
        //关闭会话
        sqlSession.close();
    }

    @Test
    public void test03() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.selectEmpByStep(7369);
        System.out.println(emp);
        sqlSession.close();
    }

    @Test
    public void test04() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = mapper.selectDeptByStep(10);
        System.out.println(dept.getDname());
        System.out.println(dept.getEmps());
        sqlSession.close();
    }

    @Test
    public void test05() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = new Emp();
        emp.setEmpno(7369);
        emp.setEname("SMITH");
        emp.setSal(1000.0);
        Emp emp2 = mapper.selectEmpByCondition(emp);
        System.out.println(emp2);
        sqlSession.close();
    }

    @Test
    public void test06(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        List<Emp> list = mapper.selectEmpByDeptnos(Arrays.asList(10, 20));
        for (Emp emp : list) {
            System.out.println(emp);
        }
        sqlSession.close();
    }

    // 一级缓存失效情况
    @Test
    public void test07() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.selectByEmpno(7369);
        System.out.println(emp);
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        EmpMapper mapper2 = sqlSession2.getMapper(EmpMapper.class);
        Emp emp2 = mapper2.selectByEmpno(7369);
        System.out.println(emp2);
        sqlSession.close();
        sqlSession2.close();
    }

    @Test
    public void test08(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        //获取对应的映射接口对象
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        EmpMapper mapper2 = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.selectByEmpno(7369);
        System.out.println(emp);
        System.out.println("==================");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        emp.setEname("Teacher");
        Integer update = mapper2.update(emp);
        System.out.println(update);
        sqlSession.clearCache();
        System.out.println("==================");
        emp = mapper.selectByEmpno(7369);
        System.out.println(emp);
        sqlSession.close();

    }

    // 二级缓存
    @Test
    public void test09(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        EmpMapper mapper2 = sqlSession2.getMapper(EmpMapper.class);
        Emp emp = mapper.selectByEmpno(7369);
        System.out.println(emp);
        sqlSession.close();
        System.out.println("====================");
        Emp emp1 = mapper2.selectByEmpno(7369);
        System.out.println(emp1);
        sqlSession2.close();
    }

    @Test
    public void test10(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.selectByEmpno(7369);
        System.out.println(emp);
        sqlSession.close();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        EmpMapper mapper2= sqlSession2.getMapper(EmpMapper.class);
        Emp emp2 = mapper2.selectByEmpno(7369);
        System.out.println(emp2);
        Emp emp3 = mapper2.selectByEmpno(7369);
        System.out.println(emp3);

        Emp emp4 = mapper2.selectByEmpno(7499);
        System.out.println(emp4);
        // 此时一级缓存有，不用查数据库，因为sqlSession还未关闭，二级缓存没有命中
        Emp emp5 = mapper2.selectByEmpno(7499);
        System.out.println(emp5);
        sqlSession2.close();
    }

    @Test
    public void testDataSource () throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        DruidDataSource dataSource = (DruidDataSource) context.getBean("dataSource");
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }
}
