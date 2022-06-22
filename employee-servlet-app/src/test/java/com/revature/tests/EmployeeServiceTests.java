package com.revature.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class EmployeeServiceTests {

	private EmployeeService eserv;
	private EmployeeDao mockdao;
	
	@Before
	public void setup() {
		mockdao = mock(EmployeeDao.class);
		eserv = new EmployeeService(mockdao);
	}
	
	@After
	public void teardown() {
		mockdao = null;
		eserv = null;
	}
	
	@Test
	public void testConfirmLoginSuccess() {
		// 1. Create fake employee list (dumb data)
		Employee e1 = new Employee(3, "John", "Smith", "johnnyboy", "password");
		List<Employee> employees = new LinkedList<Employee>();
		employees.add(e1);
		employees.add(new Employee(4, "Dude", "Weed", "LMAO", "password"));
		employees.add(new Employee(5, "Ronald", "Jeremy", "rj", "password"));
		employees.add(new Employee(6, "Rudolph", "Rednose", "r31nd33r", "password"));
		// 2. Mock edao's findAll()
		when(mockdao.findAll()).thenReturn(employees);
		// 3. Capture actual and expected output
		Employee actual = eserv.confirmLogin("johnnyboy", "password");
		Employee expected = e1;
		// 4. Assert equality
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConfirmLoginFailureBadPassword() {
		Employee e1 = new Employee(3, "John", "Smith", "johnnyboy", "password");
		List<Employee> employees = new LinkedList<Employee>();
		employees.add(e1);
		employees.add(new Employee(4, "Dude", "Weed", "LMAO", "password"));
		employees.add(new Employee(5, "Ronald", "Jeremy", "rj", "password"));
		employees.add(new Employee(6, "Rudolph", "Rednose", "r31nd33r", "password"));
		// 2. Mock edao's findAll()
		when(mockdao.findAll()).thenReturn(employees);
		// 3. Capture actual and expected output
		Employee actual = eserv.confirmLogin("johnnyboy", "p4ssword");
		Employee expected = new Employee();
		// 4. Assert equality
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConfirmLoginFailureBadUsername() {
		Employee e1 = new Employee(3, "John", "Smith", "johnnyboy", "password");
		List<Employee> employees = new LinkedList<Employee>();
		employees.add(e1);
		employees.add(new Employee(4, "Dude", "Weed", "LMAO", "password"));
		employees.add(new Employee(5, "Ronald", "Jeremy", "rj", "password"));
		employees.add(new Employee(6, "Rudolph", "Rednose", "r31nd33r", "password"));
		// 2. Mock edao's findAll()
		when(mockdao.findAll()).thenReturn(employees);
		// 3. Capture actual and expected output
		Employee actual = eserv.confirmLogin("jDawg", "password");
		Employee expected = new Employee();
		// 4. Assert equality
		assertEquals(expected, actual);
	}
}
