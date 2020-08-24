package org.git.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将数据换成elenemt识别的树的数据结构
 */
public class ElTree {
	public static void main(String[] args) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map map=new HashMap();
		map.put("id","100");map.put("pid","0");map.put("name","父1");list.add(map);
		map=new HashMap();map.put("id","101");map.put("pid","100");map.put("name","父1子1");list.add(map);
		map=new HashMap();map.put("id","200");map.put("pid","0");map.put("name","父2");list.add(map);
		map=new HashMap();map.put("id","201");map.put("pid","200");map.put("name","父2子1");list.add(map);
		map=new HashMap();map.put("id","20101");map.put("pid","201");map.put("name","父2子1孙1");list.add(map);
		map=new HashMap();map.put("id","201");map.put("pid","200");map.put("name","父2子2");list.add(map);
		ElTree et=new ElTree();
		List<Map<String,Object>> resData=et.Transformation(list,"id","pid","name");
	}
	/**
	 * 获取所有根节点
	 * @param list
	 * @param id_col_name
	 * @param pid_col_name
	 * @param name_col
	 * @return
	 */
	public  List<Map<String,Object>> Transformation(List<Map<String,Object>> list,String id_col_name,String pid_col_name,String name_col){
		List<Map<String,Object>> rootList=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			String pid=(String)map.get(pid_col_name);
			int zt=0;
			for(Map<String,Object> map1:list){
				String id=(String)map1.get(id_col_name);
				if(id.equals(pid)){
					zt=1;
					break;
				}
			}
			if(zt==0){//根节点
				map.put("label",map.get(name_col));
				rootList.add(map);
			}
		}
		Transformation(list,rootList,id_col_name,pid_col_name,name_col);
		System.out.println(rootList);
		return rootList;
	}
	/**
	 * 开始对根节点递归
	 */
	public  void Transformation(List<Map<String,Object>> list,List<Map<String,Object>> rootlist,String id_col_name,String pid_col_name,String name_col){
		for(Map<String,Object> rootmap:rootlist){
			String rootid=(String)rootmap.get(id_col_name);
			List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map:list){
				String pid=(String)map.get(pid_col_name);
				if(pid.equals(rootid)){
					map.put("label",map.get(name_col));
					children.add(map);
				}
			}
			Transformation(list,children,id_col_name,pid_col_name,name_col);
			if(children.size()>0) {
				rootmap.put("children", children);
			}
		}
	}
}
