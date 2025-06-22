package com.example.security1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.example.security1.dto.Manager;

@Service
public class ManagerService {

	private static List<Manager> managerList = new ArrayList<>(List.of(new Manager(1,"AAA"),new Manager(2,"BBB")));

	public List<Manager> getEmployees() {
		// TODO Auto-generated method stub
		return managerList;
	}

	public void addManagers(List<Manager> managers) {
		managerList.addAll(managers);
		
	}
	
	
	
}
