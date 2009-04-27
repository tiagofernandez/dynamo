package dynamo.core.interceptor;

/**
 * MyService.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class MyServiceImpl
			implements MyService {

	@JpaEntityValidation(targets = { MyFavoriteEntity.class })
	public void oneTwoThreeFour(MyFavoriteEntity myFavoriteEntity) {
		// by Ramones
	}

	@JpaEntityValidation(targets = { MyHibernateEntity.class }, 
		customValidators = { MyEmailValidator.class }, 
		exception = MyEmailValidatorException.class)
	public void customValidation(MyHibernateEntity myHibernateEntity) {
		// Sponsored by Hibernate Validator
	}

	@JpaEntityValidation(targets = { MyPreferredEntity.class })
	public void keepRunning(MyPreferredEntity myPreferredEntity) {
		// Nike
	}

	@JpaEntityValidation(targets = MyOtherEntity.class)
	public void keepWalking(MyOtherEntity myOtherEntity) {
		// Johnny Walker
	}

	@JpaEntityValidation(targets = MyEntity.class)
	public void justDoIt(MyEntity myEntity) {
		// Actually don't do anything :-)
	}

	@JpaEntityValidation(targets = MyEntity.class, exception = MyException.class)
	public void justDoItAgain(MyEntity myEntity) {
		// Just keep doing nothing...
	}
	
	@JpaEntityValidation(targets = MyRegularEntity.class)
	public void roundOneFight(MyRegularEntity myRegularEntity) {
		// Good times...
	}
	
	@JpaEntityValidation(targets = { MyFavoriteEntity.class, MyPreferredEntity.class }, exception = MyException.class)
	public void sitAndWait(MyFavoriteEntity myFavoriteEntity, MyPreferredEntity myPreferredEntity) {
		// Don't ask me why
	}

	@JpaEntityValidation(targets = MySpecialEntity.class)
	public void sitTight(MySpecialEntity mySpecialEntity) {
		// And cross fingers!
	}
	
	@JpaEntityValidation(targets = MyDifferentEntity.class, exception = MyException.class)
	public void yetAnotherService(MyDifferentEntity myDifferentEntity) {
		// Nothing to declare
	}
	
	@JpaEntityValidation(targets = MyExtendedEntity.class)
	public void yetAnotherTest(MyExtendedEntity myExtendedEntity) {
		// Nothing!
	}

}