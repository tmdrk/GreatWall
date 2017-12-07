package com.test.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.test.entity.User;

/**
 * 反射工具类
 * @ClassName: ReflectionUtils 
 * @author zhoujie
 * @date 2017年3月24日 上午9:48:13
 */
public abstract class ReflectionUtils {

	/**
	 * 字段缓存
	 */
	private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<Class<?>, Field[]>(256);
	
	/**
	 * 方法缓存
	 */
	private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<Class<?>, Method[]>(256);
	
	/**
	 * 获得ClassLoader
	 * 
	 * @return
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
		}
		if (cl == null) {
			cl = ReflectionUtils.class.getClassLoader();
			if (cl == null) {
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
				}
			}
		}
		return cl;
	}

	
	/**
	 * 查找指定clas的字段
	 * 
	 * @param clazz
	 * @param name
	 * 
	 * @return
	 */
	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	/**
	 * 获得指定类型的字段
	 * 
	 * @param clazz
	 * @param name
	 * @param type
	 * @return
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = getDeclaredFields(searchType);
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * 设置字段值
	 * 
	 * @param field
	 * @param target
	 * @param value
	 */
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException("无法设置 " + ex.getClass().getName() + "字段【" + field.getName() + "】值:  " + ex.getMessage());
		}
	}

	/**
	 * 获得字段值
	 * 
	 * @param field
	 * @param target
	 * @return
	 */
	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException("无法获得 " + ex.getClass().getName() + "字段【" + field.getName() + "】值:  " + ex.getMessage());
		}
	}

	/**
	 * 查找方法
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class<?>[0]);
	}

	/**
	 * 查找指定参数的方法
	 * 
	 * @param clazz
	 * @param name
	 * @param paramTypes
	 * @return
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType));
			for (Method method : methods) {
				if (name.equals(method.getName()) &&
						(paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 获得所有方法
	 * 
	 * @param clazz
	 * @return
	 */
	private static Method[] getDeclaredMethods(Class<?> clazz) {
		Method[] result = declaredMethodsCache.get(clazz);
		if (result == null) {
			Method[] declaredMethods = clazz.getDeclaredMethods();
			List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
			if (defaultMethods != null) {
				result = new Method[declaredMethods.length + defaultMethods.size()];
				System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
				int index = declaredMethods.length;
				for (Method defaultMethod : defaultMethods) {
					result[index] = defaultMethod;
					index++;
				}
			}
			else {
				result = declaredMethods;
			}
			declaredMethodsCache.put(clazz, result);
		}
		return result;
	}
	
	/**
	 * 从接口中获得方法
	 */
	private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
		List<Method> result = null;
		for (Class<?> ifc : clazz.getInterfaces()) {
			for (Method ifcMethod : ifc.getMethods()) {
				if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
					if (result == null) {
						result = new LinkedList<Method>();
					}
					result.add(ifcMethod);
				}
			}
		}
		return result;
	}

	/**
	 * 执行空参方法
	 * 
	 * @param method
	 * @param target
	 * @return
	 */
	public static Object invokeMethod(Method method) {
		return invokeMethod(method, new Object[0]);
	}

	/**
	 * 执行指定参数的方法
	 * 
	 * @param method
	 * @param target
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Method method, Object... args) {
		try {
			if(Modifier.isStatic(method.getModifiers())){
				//静态方法
				return method.invoke(null,args);
			}else{
				//非静态方法
				return method.invoke(method.getDeclaringClass().newInstance(),args);
			}
		}
		catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message else.
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an IllegalStateException else.
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>Rethrows the underlying exception cast to an {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws RuntimeException the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}. Should
	 * only be called if no checked exception is expected to be thrown by the
	 * target method.
	 * <p>Rethrows the underlying exception cast to an {@link Exception} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * @param ex the exception to rethrow
	 * @throws Exception the rethrown exception (in case of a checked exception)
	 */
	public static void rethrowException(Throwable ex) throws Exception {
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * 方法是否存在指定异常
	 * 
	 * @param method
	 * @param exceptionType
	 * @return
	 */
	public static boolean declaresException(Method method, Class<?> exceptionType) {
		Class<?>[] declaredExceptions = method.getExceptionTypes();
		for (Class<?> declaredException : declaredExceptions) {
			if (declaredException.isAssignableFrom(exceptionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字段是否是public static final
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isPublicStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
	}

	/**
	 * 是否是equals方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isEqualsMethod(Method method) {
		if (method == null || !method.getName().equals("equals")) {
			return false;
		}
		Class<?>[] paramTypes = method.getParameterTypes();
		return (paramTypes.length == 1 && paramTypes[0] == Object.class);
	}

	/**
	 * 是否是hashCode方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isHashCodeMethod(Method method) {
		return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
	}

	/**
	 * 是否是toString方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isToStringMethod(Method method) {
		return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
	}

	/**
	 * 是否是Object(是java.lang.Object)对象中的方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isObjectMethod(Method method) {
		if (method == null) {
			return false;
		}
		try {
			Object.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 设置字段accessible
	 * 
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 设置方法accessible
	 * 
	 * @param method
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) ||
				!Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 设置构造器accessible
	 * 
	 * @param ctor
	 */
	public static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) ||
				!Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}

	/**
	 * 获得所有字段
	 * 
	 * @param clazz
	 * @return
	 */
	private static Field[] getDeclaredFields(Class<?> clazz) {
		Field[] result = declaredFieldsCache.get(clazz);
		if (result == null) {
			result = clazz.getDeclaredFields();
			declaredFieldsCache.put(clazz, result);
		}
		return result;
	}

}
