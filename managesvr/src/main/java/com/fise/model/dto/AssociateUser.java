package com.fise.model.dto;

import java.util.List;

public class AssociateUser {
	private int id;
	private String name;
	private List<UserRelation> useRelation;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserRelation> getUseRelation() {
		return useRelation;
	}
	public void setUseRelation(List<UserRelation> useRelation) {
		this.useRelation = useRelation;
	}
	
	
	
}
