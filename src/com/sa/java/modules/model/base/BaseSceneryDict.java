package com.sa.java.modules.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSceneryDict<M extends BaseSceneryDict<M>> extends Model<M> implements IBean {

	public void setZoneId(java.lang.Long zoneId) {
		set("zoneId", zoneId);
	}

	public java.lang.Long getZoneId() {
		return get("zoneId");
	}

	public void setType(java.lang.String type) {
		set("type", type);
	}

	public java.lang.String getType() {
		return get("type");
	}

	public void setScenery(java.lang.String scenery) {
		set("scenery", scenery);
	}

	public java.lang.String getScenery() {
		return get("scenery");
	}

	public void setSceneryId(java.lang.String sceneryId) {
		set("sceneryId", sceneryId);
	}

	public java.lang.String getSceneryId() {
		return get("sceneryId");
	}

	public void setCountry(java.lang.String country) {
		set("country", country);
	}

	public java.lang.String getCountry() {
		return get("country");
	}

	public void setCountryId(java.lang.String countryId) {
		set("countryId", countryId);
	}

	public java.lang.String getCountryId() {
		return get("countryId");
	}

	public void setProvince(java.lang.String province) {
		set("province", province);
	}

	public java.lang.String getProvince() {
		return get("province");
	}

	public void setProvinceId(java.lang.String provinceId) {
		set("provinceId", provinceId);
	}

	public java.lang.String getProvinceId() {
		return get("provinceId");
	}

	public void setCity(java.lang.String city) {
		set("city", city);
	}

	public java.lang.String getCity() {
		return get("city");
	}

	public void setCityId(java.lang.String cityId) {
		set("cityId", cityId);
	}

	public java.lang.String getCityId() {
		return get("cityId");
	}

	public void setComment(java.lang.String comment) {
		set("comment", comment);
	}

	public java.lang.String getComment() {
		return get("comment");
	}

}
