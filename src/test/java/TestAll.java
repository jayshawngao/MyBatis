import static org.junit.Assert.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayshawn.dao.DepartmentMapper;
import com.jayshawn.dao.EmployeeMapper;
import com.jayshawn.model.Department;
import com.jayshawn.model.Employee;
import com.jayshawn.model.Employee.EmpStatus;

public class TestAll {

	private SqlSession session;
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() throws IOException{
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		session = sqlSessionFactory.openSession();
	}
	


	@Test
	public void test() throws IOException {
		// 全局配置文件地址，classpath路径风格
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 根据配置文件新建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 由SqlSessionFactory得到SqlSession， 实际操作都由SqlSession完成
		SqlSession session = sqlSessionFactory.openSession();
		try {
			  // 第一个参数需要填写映射文件中配置的namespace+要执行sql语句的id
			  Employee employee = session.selectOne("com.jayshawn.model.EmployeeMapper.selectEmp", 1);
			  System.out.println(employee);
			} finally {
			  session.close();
		}
	}
	
	@Test
	public void testSelect() throws IOException{
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(11);
			System.out.println(employee);
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testAdd(){
		try {
			session.close();
			session = sqlSessionFactory.openSession(ExecutorType.BATCH);
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setName("BBB");
			Department department = new Department();
			department.setId(1);
			employee.setDepartment(department);
			for(int i=0;i<100;i++){
				mapper.addEmp(employee);
			}
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testDelete(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setId(14);
			mapper.deleteEmpById(employee);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testUpdate(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setId(1);
			employee.setName("AAA");
			mapper.updateEmp(employee);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testForEach(){
		try {
			Employee employee1 = new Employee();
			employee1.setName("EEE");
			Employee employee2 = new Employee();
			employee2.setName("FFF");
			List<Employee> employees = Arrays.asList(employee1,employee2);
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			mapper.addAllEmp(employees);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetAllEmp(){
		try {
			Page<Employee> page = PageHelper.startPage(1, 3);
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			// 此时employees其实是Page对象,Page继承自ArrayList
			// public class Page<E> extends ArrayList<E> implements Closeable
			List<Employee> employees = mapper.getAllEmp();
			System.out.println(employees.getClass().getName());
			System.out.println("当前页码： "+page.getPageNum());
			System.out.println("总记录数： "+page.getTotal());
			System.out.println("每页的记录数： "+page.getPageSize());
			System.out.println("总页码： "+page.getPages());
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetAllEmp2(){
		try {
			Page<Employee> page = PageHelper.startPage(1, 3);
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			List<Employee> employees = mapper.getAllEmp();
			// 使用PageInfo可以获得和分页有关的更详细的信息，包括是否有上一页下一页，跳转到上一页下一页
			PageInfo<Employee> pageInfo = new PageInfo<>(employees);
			
			System.out.println("当前页码： "+pageInfo.getPageNum());
			System.out.println("总记录数： "+pageInfo.getTotal());
			System.out.println("每页的记录数： "+pageInfo.getPageSize());
			System.out.println("总页码： "+pageInfo.getPages());
			System.out.println("是否有下一页： "+pageInfo.isHasNextPage());
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetMapById(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Map<String, Object> map = mapper.getMapById(1);
			System.out.println(map);
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetMap(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Map<Integer, Employee> map = mapper.getMap();
			System.out.println(map.size());
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetEmpDep(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpDep(11);
			System.out.println(employee);
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetEmpByStep(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpByStep(11);
			System.out.println(employee.getClass().getName());
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetDepEmp(){
		try {
			DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
			Department department = mapper.getDepEmp(1);
			System.out.println(department.getEmployees().get(0));
		} finally {
			session.close();
		}
	}
	
	@Test 
	public void testGetEmpByDepId(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			List<Employee> employees = mapper.getEmpByDepId(1);
			System.out.println(employees.get(0));;
			
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetDepByStep(){
		try {
			DepartmentMapper mapper = session.getMapper(DepartmentMapper.class);
			Department department = mapper.getDepByStep(1);
			System.out.println(department.getClass().getName());
			System.out.println(department.getEmployees().get(0));
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetEmpByConditionIf(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setId(1);
			List<Employee> employees = mapper.getEmpByConditionIf(employee);
			System.out.println(employees.size());
			
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetEmpByTrim(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setId(1);
			List<Employee> employees = mapper.getEmpByTrim(employee);
			System.out.println(employees.size());
			
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetEmpByChoose(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setId(1);
			List<Employee> employees = mapper.getEmpByChoose(employee);
			System.out.println(employees.size());
			
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testUpdateEmpBySet(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setId(1);
			employee.setName("ASS");
			mapper.updateEmpBySet(employee);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testGetEmpByForEach(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			List<Employee> employees = mapper.getEmpByForEach(Arrays.asList(1,2));
			System.out.println(employees.size());
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testFirstLevelCache(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			DepartmentMapper mapper2 = session.getMapper(DepartmentMapper.class);
			Employee e1 = mapper.getEmpById(1);
			Department department = new Department();
			department.setId(1);
			department.setName("开发开发");
			mapper2.updateDepById(department);
			Employee e2 = mapper.getEmpById(1);
			
			assertEquals(e1, e2);
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testEnum(){
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = new Employee();
			employee.setName("BBB");
			Department department = new Department();
			department.setId(1);
			employee.setDepartment(department);
			employee.setEmpStatus(EmpStatus.A);
			mapper.addEmpStatus(employee);
			session.commit();
		} finally {
			session.close();
		}
	}
	
 

}
