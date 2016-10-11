package model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import db.WeatherOpenHelper;

public class WeatherDB {
	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "cool_weather";
	
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION =1;
	
	private static WeatherDB WeatherDB;
	private SQLiteDatabase db ;
	
	/*
	 * �����췽��˽�л�
	 */
	private WeatherDB (Context context){
		WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	/*
	 * ��ȡCoolWeatherDB��ʵ��
	 */
	public synchronized static WeatherDB getInstance (Context context){
		if(WeatherDB == null){
			WeatherDB = new WeatherDB( context);
		}
		return WeatherDB;
	}
	/*
	 * �����ݿ��ȡȫ�����е�ʡ����Ϣ
	 */
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province =new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				list.add(province);
				
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	/*
	 * ��cityʵ���洢�����ݿ�
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	/**
	 * �����ݿ��ȡĳʡ�µ����г�����Ϣ
	 */
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("code")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("name")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
				
			if(cursor!=null){
				cursor.close();
			}
			
		}
		return list;
	}
	
	/**
	 * ��Counrtyʵ���洢�����ݿ�
	 */
	public void saveCountry(Country country){
		if(country !=null){
			ContentValues values = new ContentValues();
			values.put("country_name", country.getCountryName());
			values.put("country_code",country.getCountryCode());
			values.put("city_id", country.getCityId());
			db.insert("Country", null, values);
		}
	}
	
	/**
	 * �����ݿ��ȡĳ���������е�����Ϣ
	 */
	public List<Country> loadCountries(int cityId){
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.query("Country", null, "cityid=?", new String []{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("name")));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("code")));
				country.setCityId(cityId);
				list.add(country);
			}while(cursor.moveToNext());
			
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
}








