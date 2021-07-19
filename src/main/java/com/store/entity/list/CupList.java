package com.store.entity.list;

import java.util.List;

import com.store.entity.Cup;

public class CupList {
	
	private List<Cup> listCups;

	public CupList() {}
	
	public CupList(List<Cup> listCups) {
		this.listCups = listCups;
	}
	
	public List<Cup> getListCups() {
		return listCups;
	}

	public void setListCups(List<Cup> listCups) {
		this.listCups = listCups;
	}
	
}
