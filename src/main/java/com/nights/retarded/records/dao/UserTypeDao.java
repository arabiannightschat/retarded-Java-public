package com.nights.retarded.records.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nights.retarded.records.model.UserType;

public interface UserTypeDao extends JpaRepository<UserType, String>{

	@Query(value="delete from records_user_type where open_id = :openId " , nativeQuery = true)
	@Modifying
	void deleteByOpenId(String openId);
	
	@Query(value = "select rt.type_id as typeId, rt.name, rt.icon, rt.type, rt.is_default as isDefault, rt.default_ix as defaultIx from records_user_type rut\n" + 
			"left join records_type rt on rt.type_id = rut.type_id\n" + 
			"where rut.open_id = :openId and rt.type = :type\n" + 
			"order by rut.ix", nativeQuery = true)
	List<Map<String,Object>> getUserRecordsType(String openId,int type);

	@Query(value = "select rt.type_id as typeId, rt.name, rt.icon, rt.type, rt.is_default as isDefault, rt.default_ix as defaultIx from records_type rt\n" + 
			"left join records_user_type rut on rut.type_id = rt.type_id and rut.open_id = :openId\n" + 
			"where rt.is_default = 1\n" + 
			"and rt.type = :type\n" + 
			"and open_id is null\n" + 
			"order by default_ix" , nativeQuery = true)
	List<Map<String,Object>> getUnusedDefaultType(String openId, int type);
}