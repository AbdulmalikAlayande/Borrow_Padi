package com.example.loanapplication.ModelMappingExercises;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ModelMapping {

	public void propertyMappings(MyClassDTO classDTO, MyClass myClass){
		ModelMapper mapper = new ModelMapper();
		TypeMap<MyClassDTO, MyClass> typeMap = mapper.createTypeMap(MyClassDTO.class, MyClass.class);
//		typeMap.addMappings();
	}
}
