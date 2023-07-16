package com.example.taobao20.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taobao20.bean.Shopinfo;

import java.util.ArrayList;
import java.util.List;


public class ShopinfoDao {
	private MyHelper helper;
	public ShopinfoDao(Context context) {
		helper = new MyHelper(context); // 创建Dao时, 创建Helper
	}
	public void insert(Shopinfo shopinfo) {
		SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库对象
            // 用来装载要插入的数据的 Map<列名, 列的值>
		ContentValues values = new ContentValues();
		values.put("icon", shopinfo.getIcon());
		values.put("dianjia", shopinfo.getDianjia());
		values.put("name", shopinfo.getName());
		values.put("price", shopinfo.getPrice());
		values.put("sum", shopinfo.getSum());
		values.put("number", shopinfo.getNumber());
		values.put("flag", shopinfo.getFlag());
		long id = db.insert("account", null, values);
		shopinfo.setId(id);
		db.close();
	}
     //根据id 删除数据
	public int delete(long id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		// 按条件删除指定表中的数据, 返回受影响的行数
		int count = db.delete("account", "_id=?", new String[] { id + "" });
		db.close();
		return count;
	}
     //更新数据
	public int update(Shopinfo shopinfo) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues(); // 要修改的数据
		values.put("icon", shopinfo.getIcon());
		values.put("dianjia", shopinfo.getDianjia());
		values.put("name", shopinfo.getName());
		values.put("price", shopinfo.getPrice());
		values.put("sum", shopinfo.getSum());
		values.put("number", shopinfo.getNumber());
		values.put("flag", shopinfo.getFlag());
		int count = db.update("account", values, "_id=?",
				new String[] { shopinfo.getId() + "" }); // 更新并得到行数
		db.close();
		return count;
	}
    //查询所有数据倒序排列
	public List<Shopinfo> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("account", null, null, null, null, null,
				"dianjia DESC");
		List<Shopinfo> list = new ArrayList<Shopinfo>();
		while (c.moveToNext()) {
			@SuppressLint("Range") long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
			int icon=c.getInt(1);
			String dianjia = c.getString(2);
			String name = c.getString(3);
			double price = c.getDouble(4);
			double sum = c.getDouble(5);
			Integer number = c.getInt(6);
			int flag = c.getInt(7);
			list.add(new Shopinfo(id,icon,dianjia, name, price,sum,number,flag));
		}
		c.close();
		db.close();
		return list;
	}

	//查询所有数据倒序排列
	public Shopinfo queryone(String name) {
		SQLiteDatabase db = helper.getReadableDatabase();
		//查询 name = 张三 并且 age > 23 的数据  并按照id 降序排列
		Cursor c = db.query("account",null,"name=?",new String[]{name},null,null,"name desc");
//		Cursor c = db.query("account", null, null, null, null, null,
//				"dianjia DESC");
		Shopinfo res ;
		if (c.moveToNext()) {
			@SuppressLint("Range") long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
			int icon=c.getInt(1);
			String dianjia = c.getString(2);
			String name1 = c.getString(3);
			double price = c.getDouble(4);
			double sum = c.getDouble(5);
			Integer number = c.getInt(6);
			int flag = c.getInt(7);
			res = new Shopinfo(id,icon,dianjia, name1, price,sum,number,flag);
		} else {
			res = null;
		}
		c.close();
		db.close();
		return res;
	}

	//查询所有数据倒序排列
	public Double querySum() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("account", null, null, null, null, null,
				"dianjia DESC");
		Double p = 0.0;
		while (c.moveToNext()) {
			@SuppressLint("Range") long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
			Double sum = c.getDouble(5);
			p+=sum;
		}
		c.close();
		db.close();
		return p;
	}

}


