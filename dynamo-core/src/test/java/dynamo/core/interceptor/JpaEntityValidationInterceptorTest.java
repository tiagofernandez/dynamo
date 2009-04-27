package dynamo.core.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.testng.annotations.Test;

/**
 * JpaEntityValidationInterceptorTestCase.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class JpaEntityValidationInterceptorTest {

	//private static Log logger = LogFactory.getLog(JpaEntityValidationInterceptorTest.class);
	
	private MyService myService = new MyServiceImpl();

	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkColumnLength() throws Throwable {
		callMethod("keepWalking", new Object[] { new MyOtherEntity() });
	}

	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkColumnNullCatchJpaEntityInvalidException() throws Throwable {
		callMethod("justDoIt", new Object[] { new MyEntity() });
	}

	@Test(expectedExceptions = { MyException.class })
	public void checkColumnNullCatchExternalException() throws Throwable {
		callMethod("justDoItAgain", new Object[] { new MyEntity() });
	}

	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkColumnNullOnSuperclass() throws Throwable {
		callMethod("yetAnotherTest", new Object[] { new MyExtendedEntity() });
	}

	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkBasicOptional() throws Throwable {
		callMethod("keepRunning", new Object[] { new MyPreferredEntity() });
	}

	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkManyToOneOptional() throws Throwable {
		callMethod("oneTwoThreeFour", new Object[] { new MyFavoriteEntity() });
	}

	@Test(expectedExceptions = { MyException.class })
	public void checkOneToOneOptional() throws Throwable {
		callMethod("yetAnotherService", new Object[] { new MyDifferentEntity() });
	}
	
	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkJoinColumnNull() throws Throwable {
		callMethod("sitTight", new Object[] { new MySpecialEntity() });
	}
	
	@Test(expectedExceptions = { JpaEntityInvalidException.class })
	public void checkJoinColumnsNull() throws Throwable {
		callMethod("roundOneFight", new Object[] { new MyRegularEntity() });
	}

	@Test(expectedExceptions = { MyException.class })
	public void checkMultipleEntities() throws Throwable {
		callMethod("sitAndWait", new Object[] { new MyFavoriteEntity(), new MyPreferredEntity() });
	}

	@Test(expectedExceptions = { MyEmailValidatorException.class })
	public void checkCustomValidator() throws Throwable {
		callMethod("customValidation", new Object[] { new MyHibernateEntity() });
	}
	
	private void callMethod(String methodName, Object[] args) throws Throwable {
		JpaEntityValidationInterceptor interceptor = new JpaEntityValidationInterceptor();
		interceptor.invoke(createMethodInvocation(myService, methodName, args));
	}

	private MethodInvocation createMethodInvocation(Object object, String methodName, Object[] args) throws Throwable {
		Class<?>[] classArgs = null;
		if (args != null) {
			List<Class<?>> list = new ArrayList<Class<?>>();
			for (int i = 0; i < args.length; i++) {
				list.add(args[i].getClass());
			}
			classArgs = list.toArray(new Class[] {});
		}
		Method method = object.getClass().getMethod(methodName, classArgs);
		return new MyMethodInvocation(method, args);
	}

}