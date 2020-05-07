package com.lancelot.springframework.core;

/**
 * 管理 aliases 的通用接口，作为
 * {@link com.lancelot.springframework.beans.factory.support.BeanDefinitionRegistry}.
 * 的超类接口
 * 
 * @author Lancelot Chen 
 * @date 2020年5月7日 上午11:37:10
 * @Copyright：Lancelot Chen个人所有
 * @version 1.0 
 * @Description: TODO
 */
public interface AliasRegistry {
	
	/**
	 * 为某个bean注册一个别名
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午1:30:22 
	 * @param name bean的名称
	 * @param alias 别名
	 * @throws IllegalStateException 如果别名已经被使用了且不允许被重写的时候抛出异常
	 */
	void registerAlias(String name, String alias);
	
	/**
	 * 从当前注册器中移除别名 
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午1:33:26 
	 * @param alias 别名
	 * @throws IllegalStateException 如果不存在这样的别名抛出异常
	 */
	void removeAlias(String alias);
	
	/**
	 * 检验一个 name 是否是别名
	 * (TODO 与实际注册组件的名称相反)
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午1:35:55 
	 * @param name 需要check的名称
	 * @return
	 */
	boolean isAlias(String name);
	
	/**
	 * 返回给出 name 对应的所有别名
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午1:42:11 
	 * @param name
	 * @return 返回所有别名的数组 or 空数组
	 */
	String[] getAliases(String name);
	
}
