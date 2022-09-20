package com.example.authsample.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("account")
public class Account {

	@CsvBindByName(column = "ID", required = false)
	@Id
	private Long id;

	private String userid;

	private String name;

	private String password;

	private String role;


	private String companyname;

	private String remark;

	private String department;

}
