package com.lancelot.springframework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lancelot.springframework.util.Assert;
import com.lancelot.springframework.util.StringUtils;

/**
 * {@link AliasRegistry} 接口的简单实现
 * @author Lancelot Chen 
 * @date 2020年5月7日 下午2:43:42
 * @Copyright：Lancelot Chen个人所有
 * @version 1.0 
 */
public class SimpleAliasRegistry implements AliasRegistry {

	private final Map<String, String> aliasMap = new ConcurrentHashMap<>(16);
	
	@Override
	public void registerAlias(String name, String alias) {
		Assert.hasText(name, "'name' must not be empty");
		Assert.hasText(alias, "'alias' must not be empty");
		synchronized (this.aliasMap) {
			if (alias.equals(name)) {
				this.aliasMap.remove(alias);
			} else {
				String registeredName = this.aliasMap.get(alias);
				if (registeredName != null) {
					if (registeredName.equals(name)) {
						// 该别名已经存在， 不需要注册
						return;
					}
					if (!allowAliasOverriding()) {
						throw new IllegalStateException("Cannot register alias '" + alias + "' for name '" +
								name + "': It is already registered for name '" + registeredName + "'.");
					}
				}
				checkForAliasCircle(name, alias);
				this.aliasMap.put(alias, name);
			}
		}
		
	}

	/**
	 * 检查给定名称是否已经指向另一个方向的给定别名作为别名，
	 * 在前面捕获循环引用并抛出相应的IllegalStateException
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午4:09:46 
	 * @param name
	 * @param alias
	 */
	protected void checkForAliasCircle(String name, String alias) {
		if (hasAlias(alias, name)) {
			throw new IllegalStateException("Cannot register alias '" + alias +
					"' for name '" + name + "': Circular reference - '" +
					name + "' is a direct or indirect alias for '" + alias + "' already");
		}
	}

	/**
	 * 若别名已经使用过， 是否允许覆盖
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午3:58:46 
	 * @return
	 */
	protected boolean allowAliasOverriding() {
		return true;
	}

	/**
	 * 判断给定的名称是否注册了给定的别名
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午4:19:25 
	 * @param name
	 * @param alias
	 * @return
	 */
	public boolean hasAlias(String name, String alias) {
		for (Map.Entry<String, String> entry : this.aliasMap.entrySet()) {
			String registeredName = entry.getValue();
			if (registeredName.equals(name)) {
				String registeredAlias = entry.getKey();
				return (registeredAlias.equals(alias) || hasAlias(registeredAlias, alias));
			}
		}
		
		return false;
	}
	
	@Override
	public void removeAlias(String alias) {
		synchronized (this.aliasMap) {
			String name = this.aliasMap.remove(alias);
			if (name == null) {
				throw new IllegalStateException("No alias '" + alias + "' registered");
			}
		}
		
	}

	@Override
	public boolean isAlias(String name) {
		return this.aliasMap.containsKey(name);
	}

	@Override
	public String[] getAliases(String name) {
		List<String> result = new ArrayList<>();
		synchronized (this.aliasMap) {
			retrieveAliases(name, result);
		}
		return StringUtils.toStringArray(result);
	}

	/**
	 * 
	 * @author Lancelot Chen
	 * @date 2020年5月7日 下午4:28:01 
	 * @param name
	 * @param result
	 */
	private void retrieveAliases(String name, List<String> result) {
		this.aliasMap.forEach((alias, registeredName) -> {
			if (registeredName.equals(name)) {
				result.add(alias);
				retrieveAliases(alias, result);
			}
		});
	}
	
	
	

}
